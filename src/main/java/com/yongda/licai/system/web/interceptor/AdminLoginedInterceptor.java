package com.yongda.licai.system.web.interceptor;

import com.xiaoleilu.hutool.json.JSONUtil;
import com.yongda.licai.constant.GlobalConst;
import com.yongda.licai.system.dal.model.AdminDO;
import com.yongda.licai.system.web.response.ResponseWraper;
import com.yongda.licai.utils.WebUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 管理员登录状态拦截器
 * 作者：徐承恩
 * 邮箱：771247770@qq.com
 * 日期：2017/12/20-下午4:38
 */
public class AdminLoginedInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object target) throws Exception {
        HttpSession session = WebUtils.getSession();
        AdminDO adminDO = (AdminDO) session.getAttribute(GlobalConst.ADMIN_SESSION_KEY);
        //用户未登录
        if (null == adminDO) {
            if (WebUtils.isAjax()) {
                response.setHeader("x-session-timeout", "TRUE");
                WebUtils.write(response, JSONUtil.toJsonStr(ResponseWraper.other("登录超时")));
            } else {
                response.sendRedirect("/admin/login");
            }
            return false;
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
