package calculator;

import org.apache.wicket.protocol.http.WebApplication;

public class CalcApp extends WebApplication {
	@Override
	protected void init() {
		mountBookmarkablePage("stateless", StatelessPage.class);
	}
	@Override
	public Class<StatefulPage> getHomePage() {
		return StatefulPage.class;
	}
}
