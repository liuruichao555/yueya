package com.yueya.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

public final class CommonUtil {
	
	/**
	 * 获得错误字符串信息
	 * @param e
	 * @return
	 */
	public static String getErrStr(Exception e) {
		final Writer result = new StringWriter();
        final PrintWriter printWriter = new PrintWriter(result);
        e.printStackTrace(printWriter);
        return result.toString();
	}
}
