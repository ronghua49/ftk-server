package com.risepu.ftk.web;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/api/v1")
public class CaptchaController implements InitializingBean {

	private final int OFFSET = 538309;

	@Value("${captcha.image.candicate:0123456789}")
	private String imageCaptchaCandicate;

	@Value("${captcha.image.length:4}")
	private int imageCaptchaLength;

	private char[] imageCaptchaChars;

	private boolean captchaEnabled;

	@Override
	public void afterPropertiesSet() throws Exception {
		imageCaptchaChars = imageCaptchaCandicate.toCharArray();
	}

	@RequestMapping("captcha")
	public void captcha(HttpServletResponse response) {
		String code = generateImageCaptcha();
		//		setSessionParam(NewConstants.SESSION_VERIFICATION_CODE, code);

		write(code, response);
	}

	private String generateImageCaptcha() {
		if (captchaEnabled) {
			Random random = newRandom();
			char[] result = new char[imageCaptchaLength];
			for (int i = 0; i < imageCaptchaLength; i++) {
				result[i] = imageCaptchaChars[random.nextInt(imageCaptchaChars.length)];
			}
			return new String(result);
		} else {
			return "0000";
		}
	}

	protected Random newRandom() {
		try {
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
			random.setSeed(System.currentTimeMillis() + OFFSET);
			return random;
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException(e);
		}
	}

	private void write(String code, HttpServletResponse response) {
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");

		int width = 70, height = 40;

		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_INDEXED);
		Graphics2D g = image.createGraphics();
		// 设定背景色
		g.setColor(getRandColor(200, 250));
		g.fillRect(0, 0, width, height);
		g.setFont(new Font("Courier", Font.BOLD, 30));
		g.setColor(getRandColor(160, 200));

		Random random = new Random();
		for (int i = 0; i < 155; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(12);
			int yl = random.nextInt(12);
			g.drawLine(x, y, x + xl, y + yl);
		}
		for (int i = 0; i < 4; i++) {
			g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
		}
		g.drawString(code, 0, 30);

		shear(g, width, height, Color.white);

		try (ServletOutputStream out = response.getOutputStream()) {
			ImageIO.write(image, "jpg", out);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Color getRandColor(int fc, int bc) {
		Random random = new Random();
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}

	private void shear(Graphics g, int w1, int h1, Color color) {
		shearX(g, w1, h1, color);
		shearY(g, w1, h1, color);
	}

	private void shearX(Graphics g, int w1, int h1, Color color) {
		Random random = new Random();
		int period = random.nextInt(2);
		int frames = 1;
		int phase = random.nextInt(2);
		for (int i = 0; i < h1; i++) {
			double d = (period >> 1) * Math.sin((double) i / (double) period + (6.2831853071795862D * phase) / frames);
			g.copyArea(0, i, w1, 1, (int) d, 0);
		}
	}

	private void shearY(Graphics g, int w1, int h1, Color color) {
		Random random = new Random();
		int period = random.nextInt(40) + 10; // 50;
		int frames = 20;
		int phase = 7;
		for (int i = 0; i < w1; i++) {
			double d = (period >> 1) * Math.sin((double) i / (double) period + (6.2831853071795862D * phase) / frames);
			g.copyArea(i, 0, 1, h1, 0, (int) d);
		}
	}
}
