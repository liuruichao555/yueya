package com.yueya.util;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * 用户等级工具类
 * 
 * @author liuruichao
 * 
 */
public final class CustomerLevelUtil {
	private final static HashMap<Integer, String> map = new LinkedHashMap<Integer, String>();
	static {
		map.put(5, "落木萧萧");
		map.put(15, "春衫细薄");
		map.put(30, "花落花开");
		map.put(50, "何以笙箫");
		map.put(100, "山有木兮");
		map.put(200, "心悦君兮");
		map.put(500, "绿水无忧");
		map.put(1000, "为雪白头");
		map.put(2000, "繁花似锦");
		map.put(3000, "淡云流水");
		map.put(6000, "长夜未央");
		map.put(10000, "十里红妆");
		map.put(18000, "夕颜落花");
		map.put(30000, "恋影翩纤");
		map.put(60000, "夕颜落花");
		map.put(100000, "与子携手");
		map.put(300000, "风际纸鸢");
		map.put(500000, "闲听天籁");
	}

	/**
	 * 根据用户积分获得称号
	 * 
	 * @param points
	 * @return
	 */
	public static String getCustomerLevel(Integer points) {
		String result = "";
		for (Integer key : map.keySet()) {
			if (points >= key) {
				result = map.get(key);
				continue;
			} else {
				if(result.equals(""))
					result = map.get(key);
				break;
			}

		}
		return result;
	}
}
