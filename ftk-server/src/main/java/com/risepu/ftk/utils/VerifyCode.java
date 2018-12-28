package com.risepu.ftk.utils;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class VerifyCode {
	private static final Logger LOG = LoggerFactory.getLogger(VerifyCode.class);

	/**
	 * @return 验证码
	 */
	public static String genValidCode() {
		long seed = System.currentTimeMillis() + 538309;
		SecureRandom secureRandom = null; // 安全随机类
		try {
			secureRandom = SecureRandom.getInstance("SHA1PRNG");
			secureRandom.setSeed(seed);
		} catch (NoSuchAlgorithmException e) {
			LOG.error(e.getMessage(), e);
		}

		String codeList = "1234567890"; // 验证码数字取值范围
		String srand = ""; // 定义一个验证码字符串变量

		for (int i = 0; i < 6; i++) {
			int code = secureRandom.nextInt(codeList.length() - 1); // 随即生成一个0-9之间的整数
			String rand = codeList.substring(code, code + 1);
			srand += rand; // 将生成的随机数拼成一个六位数验证码
		}
		return srand; // 返回一个六位随机数验证码
	}

	/**
	 * @return 邀请码
	 */
	public static String genInveterCode() {
		long seed = System.currentTimeMillis() + 538308;
		SecureRandom secureRandom = null; // 安全随机类
		try {
			secureRandom = SecureRandom.getInstance("SHA1PRNG");
			secureRandom.setSeed(seed);
		} catch (NoSuchAlgorithmException e) {
			LOG.error(e.getMessage(), e);
		}
		
		String codeList = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"; // 验证码数字取值范围
		String srand = ""; // 定义一个验证码字符串变量
		
		for (int i = 0; i < 6; i++) {
			int code = secureRandom.nextInt(codeList.length() - 1); // 随即生成一个0-9之间的整数
			String rand = codeList.substring(code, code + 1);
			srand += rand; // 将生成的随机数拼成一个六位数验证码
		}
		return srand; // 返回一个六位随机数验证码
	}

}
