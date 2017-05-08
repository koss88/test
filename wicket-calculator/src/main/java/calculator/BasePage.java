package calculator;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.apache.wicket.markup.html.WebPage;

public abstract class BasePage extends WebPage {
	
	enum Operation { 
		add {
			@Override
			BigDecimal eval(BigDecimal a, BigDecimal b) {
				return a.add(b);
			}
		}, sub {
			@Override
			BigDecimal eval(BigDecimal a, BigDecimal b) {
				return a.subtract(b);
			}
		}, mult {
			@Override
			BigDecimal eval(BigDecimal a, BigDecimal b) {
				return a.multiply(b);
			}
		}, div {
			@Override
			BigDecimal eval(BigDecimal a, BigDecimal b) {
				return a.divide(b, 4, RoundingMode.HALF_UP);
			}
		};
		abstract BigDecimal eval (BigDecimal a, BigDecimal b);
	}
}
