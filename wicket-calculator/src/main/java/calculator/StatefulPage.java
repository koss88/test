package calculator;

import java.math.BigDecimal;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.version.undo.Change;

public class StatefulPage extends BasePage {
	protected BigDecimal buf = BigDecimal.ZERO;
	protected BigDecimal input = BigDecimal.ZERO;
	protected Operation op;
	
	public StatefulPage() {
		add(new CalcForm("form"));
	}
	class CalcForm extends Form {
		public CalcForm(String id) {
			super(id);
			add(new Label("display", new PropertyModel(StatefulPage.this, "buf")));
			add(new TextField("input", new PropertyModel(StatefulPage.this, "input")));
			add(new Button("clear") {
				@Override
				public void onSubmit() {
					addStateChange(new CalcChange());
					buf = BigDecimal.ZERO;
					input = BigDecimal.ZERO;
					op = null;
				}
			});
			add(new Button("eq") {
				@Override
				public void onSubmit() {
					apply();
				}
			});
			add(new OpButton("add", Operation.add));
			add(new OpButton("sub", Operation.sub));
			add(new OpButton("mult", Operation.mult));
			add(new OpButton("div", Operation.div));
		}
	}
	
	void apply() {
		addStateChange(new CalcChange());
		if (op == null)
			buf = input;
		else
			buf = op.eval(buf, input);
	}
	
	class OpButton extends Button {
		private Operation myOp;
		public OpButton(String id, Operation myOp) {
			super(id);
			this.myOp = myOp;
		}
		@Override
		public void onSubmit() {
			apply();
			addStateChange(new CalcChange());
			op = myOp;
		}
	}
	private class CalcChange extends Change {
		final BigDecimal oldBuf = buf;
		final Operation oldOp = op;

		@Override
		public void undo() {
			buf = oldBuf;
			op = oldOp;
		}
	}
}
