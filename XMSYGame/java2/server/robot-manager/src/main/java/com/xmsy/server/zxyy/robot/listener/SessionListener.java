package com.xmsy.server.zxyy.robot.listener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class SessionListener implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		se.getSession().setMaxInactiveInterval(10);
		se.getSession().setAttribute("token", "4444");

	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		Object token=se.getSession().getAttribute("token");
		System.out.println(token);
		// TODO Auto-generated method stub

	}

}
