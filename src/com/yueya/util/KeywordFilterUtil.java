package com.yueya.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public final class KeywordFilterUtil {
	/**
	 * 根节点
	 */
	private static TreeNode rootNode = new TreeNode();

	/**
	 * 关键词缓存
	 */
	private static ByteBuffer keywordBuffer = ByteBuffer.allocate(1024);

	/**
	 * 关键词编码
	 */
	private static String charset = "utf-8";

	static {
		InputStream in = null;
		StringBuffer sbu = new StringBuffer();
		try {
			in = KeywordFilterUtil.class.getClassLoader().getResourceAsStream(
					"words.txt");
			byte buffer[] = new byte[1024];
			int len = -1;
			while ((len = in.read(buffer)) != -1) {
				sbu.append(new String(buffer, 0, len, "utf-8"));
			}
			String words[] = sbu.toString().split("-");
			List<String> list = new ArrayList<String>();
			for (String str : words) {
				list.add(str);
			}
			createKeywordTree(list);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (in != null)
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}

	/**
	 * 创建DFA
	 * 
	 * @param keywordList
	 * @throws UnsupportedEncodingException
	 */
	public static void createKeywordTree(List<String> keywordList)
			throws UnsupportedEncodingException {
		for (String keyword : keywordList) {
			if (keyword == null)
				continue;
			keyword = keyword.trim();
			byte[] bytes = keyword.getBytes(charset);
			TreeNode tempNode = rootNode;
			for (int i = 0; i < bytes.length; i++) {
				int index = bytes[i] & 0xff;
				TreeNode node = tempNode.getSubNode(index);
				if (node == null) {
					node = new TreeNode();
					tempNode.setSubNode(index, node);
				}
				tempNode = node;
				if (i == bytes.length - 1) {
					tempNode.setKeywordEnd(true);
				}
			}
		}
	}

	/**
	 * 替换敏感词为*
	 * @param text
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String searchKeyword(String text)
			throws UnsupportedEncodingException {
		return searchKeyword(text.getBytes(charset));
	}

	public static String searchKeyword(byte[] bytes) {
		StringBuilder words = new StringBuilder();
		if (bytes == null || bytes.length == 0) {
			return words.toString();
		}
		TreeNode tempNode = rootNode;
		int rollback = 0;
		int position = 0;
		while (position < bytes.length) {
			int index = bytes[position] & 0xFF;
			keywordBuffer.put(bytes[position]);
			tempNode = tempNode.getSubNode(index);
			if (tempNode == null) {
				position = position - rollback;
				rollback = 0;
				tempNode = rootNode;
				keywordBuffer.clear();
			} else if (tempNode.isKeywordEnd()) {
				keywordBuffer.flip();
				for (int i = 0; i <= rollback; i++) {
					bytes[position - i] = 42;
				}
				keywordBuffer.limit(keywordBuffer.capacity());
				rollback = 1;
			} else {
				rollback++;
			}
			position++;
		}
		String result = null;
		try {
			result = new String(bytes, "utf-8");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}

class TreeNode {
	private static final int NODE_LEN = 256;

	/**
	 * true 关键词的终结 ； false 继续
	 */
	private boolean end = false;

	private List<TreeNode> subNodes = new ArrayList<TreeNode>(NODE_LEN);

	public TreeNode() {
		for (int i = 0; i < NODE_LEN; i++) {
			subNodes.add(i, null);
		}
	}

	/**
	 * 向指定位置添加节点树
	 * 
	 * @param index
	 * @param node
	 */
	public void setSubNode(int index, TreeNode node) {
		subNodes.set(index, node);
	}

	public TreeNode getSubNode(int index) {
		return subNodes.get(index);
	}

	public boolean isKeywordEnd() {
		return end;
	}

	public void setKeywordEnd(boolean end) {
		this.end = end;
	}
}