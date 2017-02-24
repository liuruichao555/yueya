package com.yueya.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public final class FileUtil {
	public static void writeFile(String filePath, InputStream in) throws Exception {
		File file = new File(filePath);
		FileOutputStream out = new FileOutputStream(file);
		
		byte buffer[] = new byte[1024];
		int len = -1;
		while((len = in.read(buffer)) != -1) {
			out.write(buffer, 0, len);
		}
		
		out.close();
	}
}
