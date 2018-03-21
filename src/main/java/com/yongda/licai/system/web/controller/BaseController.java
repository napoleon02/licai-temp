package com.yongda.licai.system.web.controller;

import com.yongda.licai.constant.GlobalConst;
import com.yongda.licai.system.dal.model.AdminDO;
import com.yongda.licai.thirdparty.member.response.MemberInfo;
import com.yongda.licai.utils.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 基层控制器用于其他控制器类继承
 * author：xuchengen
 * date：2017/9/19 0019 - 9:52
 * 当你处于绝望中的时候，向你信任的人，爱的人倾诉，
 * 向家庭、朋友、警察、社区、社会寻求帮助。
 * 珍爱生命，快乐生活。
 */
public abstract class BaseController extends WebUtils {

    /**
     * 获取当前登录的管理员信息
     *
     * @return AdminDO
     */
    public AdminDO getAdmin() {
        HttpSession session = getSession();
        return (AdminDO) session.getAttribute(GlobalConst.ADMIN_SESSION_KEY);
    }

    /**
     * 获取前台会员信息
     *
     * @return MemberInfo
     */
    public MemberInfo getMemberInfoByHome() {
        HttpServletRequest request = getRequest();
        return (MemberInfo) request.getAttribute("x-home-user");
    }

    /**
     * 获取前台token
     *
     * @return String
     */
    public String getTokenByHome() {
        HttpServletRequest request = getRequest();
        return (String) request.getAttribute("x-home-token");
    }
}
