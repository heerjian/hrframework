package com.hframework.test.web;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.Configuration;
import org.eclipse.jetty.webapp.WebAppClassLoader;
import org.eclipse.jetty.webapp.WebAppContext;

/**
 * 
 * @author: heerjian
 * @date: 2018年1月9日 下午9:31:32
 */
public class JettyStart {
	private static final int DEFAULT_PORT = 8080;
	private static final String DEFAULT_CONTEXT = "/";
	private static final String DEFAULT_WEBAPP = "src/main/webapp";
	private static final String WINDOWS_WEBDEFAULT_PATH = "/com/hframework/test/web/webdefault-windows.xml";

	private int port = DEFAULT_PORT;
	private String context = DEFAULT_CONTEXT;
	private String webapp = DEFAULT_WEBAPP;
	private List<String> classPaths = new ArrayList<String>();

	private Server server;

	public JettyStart() {
		super();
		classPaths.add("target/classes");
		classPaths.add("target/test-classes");
	}

	public JettyStart(int port, String context) {
		this();
		this.port = port;
		this.context = context;
	}

	public JettyStart(int port, String context, String webapp) {
		this();
		this.port = port;
		this.context = context;
		this.webapp = webapp;
	}

	public void start(boolean isJoin) throws Exception {
		server = new Server(getPort());

		WebAppContext webapp = new WebAppContext(getWebapp(), getContext());
		// 修改webdefault.xml，解决Windows下Jetty Lock住静态文件的问题.
		webapp.setDefaultsDescriptor(WINDOWS_WEBDEFAULT_PATH);

		// This webapp will use jsps and jstl. We need to enable the
		// AnnotationConfiguration in order to correctly
		// set up the jsp container
		Configuration.ClassList classlist = Configuration.ClassList.setServerDefault(server);
		classlist.addBefore("org.eclipse.jetty.webapp.JettyWebXmlConfiguration",
				"org.eclipse.jetty.annotations.AnnotationConfiguration");

		// Set the ContainerIncludeJarPattern so that jetty examines these
		// container-path jars for tlds, web-fragments etc.
		// If you omit the jar that contains the jstl .tlds, the jsp engine will
		// scan for them instead.
		webapp.setAttribute("org.eclipse.jetty.server.webapp.ContainerIncludeJarPattern", ".*/[^/]*\\.jar$");

		server.setHandler(webapp);

		server.start();
		if (isJoin)
			server.join();
	}

	public void restart() throws Exception {
		if (server == null) {
			start(false);
			return;
		}

		WebAppContext context = (WebAppContext) server.getHandler();
		context.stop();

		WebAppClassLoader classLoader = new WebAppClassLoader(context);
		for (String cp : classPaths) {
			classLoader.addClassPath(cp);
		}
		context.setClassLoader(classLoader);

		context.start();
	}

	public void addClassPaths(String... classPaths) {
		for (String cp : classPaths) {
			if (StringUtils.isEmpty(cp))
				continue;
			this.classPaths.add(cp);
		}
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getWebapp() {
		return webapp;
	}

	public void setWebapp(String webapp) {
		this.webapp = webapp;
	}
}
