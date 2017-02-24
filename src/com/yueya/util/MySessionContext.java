package com.yueya.util;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

public final class MySessionContext {
	private final static HashMap<String, Object> mymap = new HashMap<String, Object>();

	public static synchronized void AddSession(HttpSession session) {
		if (session != null)
			mymap.put(session.getId(), session);
	}

	public static synchronized void DelSession(HttpSession session) {
		if (session != null)
			mymap.remove(session.getId());
	}

	public static synchronized HttpSession getSession(String sessionid) {
		if (sessionid == null)
			return null;
		return (HttpSession) mymap.get(sessionid);
	}
}
