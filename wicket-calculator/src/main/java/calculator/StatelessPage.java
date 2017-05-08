package calculator;

import java.math.BigDecimal;
import java.util.Locale;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.HiddenField;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.ComponentPropertyModel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.util.convert.IConverter;
import org.apache.wicket.util.string.Strings;

public class StatelessPage extends BasePage {
	protected BigDecimal buf = BigDecimal.ZERO;
	protected BigDecimal input = BigDecimal.ZERO;
	protected Operation op;
	
	public StatelessPage() {
		add(new CalcForm("form"));
	}
	class CalcForm extends StatelessForm {
		public CalcForm(String id) {
			super(id, new CompoundPropertyModel(StatelessPage.this));
			add(new Label("display", new ComponentPropertyModel("buf")));
			add(new HiddenField("buf"));
			add(new HiddenField("op") {
				@Override
				public IConverter getConverter(Class type) {
					return new IConverter() {
						public Object convertToObject(String value, Locale locale) {
							return Strings.isEmpty(value) ? null : Operation.valueOf(value);
						}
						public String convertToString(Object value, Locale locale) {
							return value.toString();
						}
					};
				}
			});
			add(new TextField("input"));
			add(new Button("clear") {
				@Override
				public void onSubmit() {
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
			op = myOp;
		}
	}
}
