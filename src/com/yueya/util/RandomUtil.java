package com.yueya.util;

import java.util.Random;

/**
 * 随机验证码
 * @author liuruichao
 *
 */
public final class RandomUtil {
	private final static Random random = new Random(System.currentTimeMillis());
	/**
	 * 获得6位验证码
	 * @return
	 */
	public static String getRandomCodeBy6() {
		StringBuffer sbu = new StringBuffer();
		for(int i = 0; i < 6; i++) {
			sbu.append(random.nextInt(10));
		}
		return sbu.toString();
	}
}
