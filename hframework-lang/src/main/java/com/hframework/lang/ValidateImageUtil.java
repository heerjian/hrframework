package com.hframework.lang;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 
* @ClassName: ValidateImageUtil 
* @Description: 验证码工具类
* @author    jian
*
 */
public class ValidateImageUtil {

	private static char code[] = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
			'k', 'm', 'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y',
			'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'L', 'M',
			'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '2',
			'3', '4', '5', '6', '7', '8', '9' };
	private static final int WIDTH = 50;
	private static final int HEIGHT = 20;
	private static final int LENGTH = 4;
    /**
     * 
    * @Title: getImage 
    * @Description: 获取验证码缓冲图像，并把验证码记录到session中
    * @param @param request
    * @param @return    
    * @return BufferedImage    
    * @throws
     */
	public static BufferedImage getImage(HttpServletRequest request) {
		BufferedImage image = new BufferedImage(WIDTH, HEIGHT,
				BufferedImage.TYPE_INT_RGB);
		Font mFont = new Font("Arial", Font.TRUETYPE_FONT, 18);
		Graphics g = image.getGraphics();
		Random rd = new Random();

		// 设置背景颜色
		g.setColor(new Color(rd.nextInt(55) + 200, rd.nextInt(55) + 200, rd
				.nextInt(55) + 200));
		g.fillRect(0, 0, WIDTH, HEIGHT);

		// 设置字体
		g.setFont(mFont);

		// 画边框
		g.setColor(Color.black);
		g.drawRect(0, 0, WIDTH - 1, HEIGHT - 1);

		// 随机产生的验证码
		String result = "";
		for (int i = 0; i < LENGTH; ++i) {
			result += code[rd.nextInt(code.length)];
		}
		HttpSession session = request.getSession();
		session.setAttribute("code", result);

		// 画验证码
		for (int i = 0; i < result.length(); i++) {
			g.setColor(new Color(rd.nextInt(200), rd.nextInt(200), rd
					.nextInt(200)));
			g.drawString(result.charAt(i) + "", 12 * i + 1, 16);
		}

		// 随机产生2个干扰线
		for (int i = 0; i < 2; i++) {
			g.setColor(new Color(rd.nextInt(200), rd.nextInt(200), rd
					.nextInt(200)));
			int x1 = rd.nextInt(WIDTH);
			int x2 = rd.nextInt(WIDTH);
			int y1 = rd.nextInt(HEIGHT);
			int y2 = rd.nextInt(HEIGHT);
			g.drawLine(x1, y1, x2, y2);
		}

		// 释放图形资源
		g.dispose();
		return image;
	}
}
