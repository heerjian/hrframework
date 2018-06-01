package com.hframework.test.web;


import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 
 * @ClassName: StartMain 
 * @Description: 启动jetty
 * @author: heerjian
 * @date: 2018年1月9日 下午9:31:32
 */
public class StartMain {
	private static Logger log = LoggerFactory.getLogger(StartMain.class);

	public static void main(String[] args) {
		JettyStart start = new JettyStart();
		parseArgs(args, start);

		try {
			start.start(false);
			log.info("启动成功，请访问： http://localhost:" + start.getPort()
					+ start.getContext());
			log.info("输入Enter键重启：");
		} catch (OutOfMemoryError e) {
			log.error("[提示] 启动失败，请尝试设置虚拟机内存 -Xmx768m", e);
			System.exit(-1);
		} catch (Exception e) {
			log.error("[提示] 启动失败", e);
			System.exit(-1);
		}

		try {
			while (true) {
				char c = (char) System.in.read();
				if (c == '\n') {
					start.restart();
					log.info("重启成功，请访问： http://localhost:" + start.getPort()
							+ start.getContext());
					log.info("输入Enter键重启：");
				}
			}
		} catch (Exception e) {
			log.error("[提示] 重启失败", e);
			System.exit(-1);
		}
	}

	protected static void parseArgs(String[] args, JettyStart start) {
		if (args == null || args.length == 0)
			return;

		for (String arg : args) {
			if (StringUtils.isBlank(arg))
				continue;

			if (arg.toLowerCase().startsWith("-p")) {
				start.setPort(Integer.parseInt(arg.substring(2)));
			} else if (arg.toLowerCase().startsWith("-c")) {
				start.setContext(arg.substring(2));
			} else if (arg.toLowerCase().startsWith("-w")) {
				start.setWebapp(arg.substring(2));
			} else if (arg.toLowerCase().startsWith("-c")) {
				start.addClassPaths(arg.substring(2).split(";"));
			}
		}
	}

}
