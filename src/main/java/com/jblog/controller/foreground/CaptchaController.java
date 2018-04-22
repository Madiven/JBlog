package com.jblog.controller.foreground;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.jblog.common.bean.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Controller
@RequestMapping("captcha")
public class CaptchaController {

	@Autowired
	private Producer captchaProducer;
	@RequestMapping("/getCaptchaCode")
	public void getCaptchaCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		
		response.setDateHeader("Expires", 0);  
		response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");  
		response.addHeader("Cache-Control", "post-check=0, pre-check=0");  
		response.setHeader("Pragma", "no-cache");  
		response.setContentType("image/jpeg");
		
		String capText = captchaProducer.createText();
		session.setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);
		System.out.println("生成验证码文本===="+capText);

		BufferedImage image = captchaProducer.createImage(capText);
		ServletOutputStream out = response.getOutputStream();
		ImageIO.write(image, "jpg", out);
		try {
			out.flush();
		} finally {
			out.close();
		}
	}

	@RequestMapping("/checkCaptchaCode")
	@ResponseBody
	public Result checkCaptchaCode(
			HttpServletRequest request, 
			HttpServletResponse response,
			@RequestParam("captchaCode") String captchaCode){
			
			String generateCode =(String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
			if(captchaCode.equalsIgnoreCase(generateCode)) {
				return new Result();
			} else {
				return new Result(Result.FAIL);
			}
	}
}
