package com.yongda.licai.system.web.controller;

import com.github.bingoohuang.patchca.color.ColorFactory;
import com.github.bingoohuang.patchca.custom.ConfigurableCaptchaService;
import com.github.bingoohuang.patchca.filter.predefined.*;
import com.github.bingoohuang.patchca.utils.encoder.EncoderHelper;
import com.github.bingoohuang.patchca.word.RandomWordFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.util.Random;

/**
 * 图形验证码控制器
 * 作者：徐承恩
 * 邮箱：771247770@qq.com
 * 日期：2017/12/20-下午4:57
 */
@Controller
@RequestMapping(value = "/captcha")
public class CaptchaController extends BaseController {

    private static final Logger log = LoggerFactory.getLogger(CaptchaController.class);

    @RequestMapping(value = "/genImgCode", method = RequestMethod.GET)
    public void genImgCode() {
        try {
            HttpSession session = getSession();
            HttpServletResponse response = getResponse();

            Random random = new Random();
            ConfigurableCaptchaService cs = new ConfigurableCaptchaService();
            cs.setColorFactory(new ColorFactory() {
                @Override
                public Color getColor(int x) {
                    int[] c = new int[3];
                    int i = random.nextInt(c.length);
                    for (int fi = 0; fi < c.length; fi++) {
                        if (fi == i) {
                            c[fi] = random.nextInt(71);
                        } else {
                            c[fi] = random.nextInt(256);
                        }
                    }
                    return new Color(c[0], c[1], c[2]);
                }
            });

            RandomWordFactory wf = new RandomWordFactory();
            wf.setCharacters("2345678cefgkmnprstuvwxyzABCEFGKMNPQRSTUVWXYZ");
            wf.setMaxLength(4);
            wf.setMinLength(4);
            cs.setWordFactory(wf);

            switch (random.nextInt(5)) {
                case 0:
                    cs.setFilterFactory(new CurvesRippleFilterFactory(cs.getColorFactory()));
                    break;
                case 1:
                    cs.setFilterFactory(new MarbleRippleFilterFactory());
                    break;
                case 2:
                    cs.setFilterFactory(new DoubleRippleFilterFactory());
                    break;
                case 3:
                    cs.setFilterFactory(new WobbleRippleFilterFactory());
                    break;
                case 4:
                    cs.setFilterFactory(new DiffuseRippleFilterFactory());
                    break;
            }
            String token = EncoderHelper.getChallangeAndWriteImage(cs, "png", response.getOutputStream());
            setResponseHeaders(response);
            session.setAttribute("captchaToken", token);
            log.info("当前的SessionID=" + session.getId() + "，验证码=" + token);
        } catch (Exception e) {
            log.error("生成图片验证码异常：", e);
        }
    }

    private void setResponseHeaders(HttpServletResponse response) {
        response.setContentType("image/png");
        response.setHeader("Cache-Control", "no-cache, no-store");
        response.setHeader("Pragma", "no-cache");
        long time = System.currentTimeMillis();
        response.setDateHeader("Last-Modified", time);
        response.setDateHeader("Date", time);
        response.setDateHeader("Expires", time);
    }
}
