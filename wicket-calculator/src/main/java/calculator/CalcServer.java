package calculator;

import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.handler.MovedContextHandler;
import org.mortbay.jetty.nio.SelectChannelConnector;
import org.mortbay.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CalcServer {
	private static final Logger log = LoggerFactory.getLogger(CalcServer.class);

	public static void main(String[] args) throws Exception
	{
		Server server = new Server();
		WebAppContext web = new WebAppContext();
		
		String contextPath = "/calculator";
		web.setContextPath(contextPath);
		web.setWar("src/main/webapp");
		
		server.addHandler(web);
		server.addHandler(new MovedContextHandler(server, "/", contextPath));

		SelectChannelConnector httpConn = new SelectChannelConnector();
		httpConn.setPort(8080);
		server.setConnectors(new Connector[] { httpConn });

		server.start();
		log.info("Ready at http://localhost:8080" + contextPath);

		server.join();
	}
}