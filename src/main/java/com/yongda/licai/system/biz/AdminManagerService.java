package com.yongda.licai.system.biz;

import com.yongda.licai.system.dal.model.AdminDO;

/**
 * 管理员业务接口
 * 作者：徐承恩
 * 邮箱：771247770@qq.com
 * 日期：2018/1/29-上午10:09
 */
public interface AdminManagerService {

    /**
     * 根据帐号获取管理员信息
     *
     * @param account 帐号
     * @return 实体
     */
    AdminDO getByAccount(String account);

    /**
     * 创建管理员
     *
     * @param adminDO 实体
     */
    void create(AdminDO adminDO);

    /**
     * 禁用用户
     *
     * @param id   主键ID
     * @param flag 标志
     */
    void disable(String id, String flag);

    /**
     * 重置密码
     *
     * @param adminDO 实体
     */
    void resetPwd(AdminDO adminDO);
}
