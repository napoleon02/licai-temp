package com.yongda.licai.system.web.interceptor;

import com.xiaoleilu.hutool.json.JSONUtil;
import com.xiaoleilu.hutool.util.StrUtil;
import com.yongda.licai.annotation.LoginRequire;
import com.yongda.licai.system.web.response.ResponseEnum;
import com.yongda.licai.system.web.response.ResponseWraper;
import com.yongda.licai.utils.WebUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

/**
 * 前台登录状态校验拦截器
 * 作者：徐承恩
 * 邮箱：771247770@qq.com
 * 日期：2018/1/31-下午2:53
 */
public class HomeLoginedInterceptor implements HandlerInterceptor {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object target) throws Exception {
        Class<?> targetClass = target.getClass();
        if (targetClass.isAssignableFrom(HandlerMethod.class)) {
            LoginRequire loginRequire = ((HandlerMethod) target).getMethodAnnotation(LoginRequire.class);
            if (null != loginRequire && !loginRequire.ignore()) {
                String token = request.getHeader("x-token");

                if (StrUtil.isBlank(token)) {
                    token = request.getParameter("x-token");
                }

                //如果token为空视为未登录
                if (StrUtil.isBlank(token)) {
                    ResponseWraper wraper = ResponseWraper.newInstance(ResponseEnum.UNLOGIN);
                    WebUtils.write(response, JSONUtil.toJsonStr(wraper));
                    return false;
                }

                //token不存在视为未登录
                if (!redisTemplate.hasKey(token)) {
                    ResponseWraper wraper = ResponseWraper.newInstance(ResponseEnum.UNLOGIN);
                    WebUtils.write(response, JSONUtil.toJsonStr(wraper));
                    return false;
                }

                //token延续一个小时
                redisTemplate.expire(token, 3600, TimeUnit.SECONDS);
                Object user = redisTemplate.opsForValue().get(token);
                request.setAttribute("x-home-user", user);
                request.setAttribute("x-home-token", token);
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
