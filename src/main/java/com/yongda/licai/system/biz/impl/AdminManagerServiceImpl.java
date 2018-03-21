package com.yongda.licai.system.biz.impl;

import com.xiaoleilu.hutool.util.RandomUtil;
import com.yongda.licai.enums.YesOrNoEnum;
import com.yongda.licai.system.biz.AdminManagerService;
import com.yongda.licai.system.dal.mapper.AdminDOMapper;
import com.yongda.licai.system.dal.model.AdminDO;
import com.yongda.licai.utils.PwdUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 作者：徐承恩
 * 邮箱：771247770@qq.com
 * 日期：2018/1/29-上午10:11
 */
@Service(value = "adminManagerService")
public class AdminManagerServiceImpl implements AdminManagerService {

    @Resource(name = "adminDOMapper")
    private AdminDOMapper adminDOMapper;

    @Override
    public AdminDO getByAccount(String account) {
        AdminDO adminDO = new AdminDO();
        adminDO.setAccount(account);
        return adminDOMapper.selectOne(adminDO);
    }

    @Override
    public void create(AdminDO adminDO) {
        String password = adminDO.getPassword();
        String uuid = RandomUtil.randomUUID();
        String newPwd = PwdUtils.buildPwd(password, uuid);
        adminDO.setPassword(newPwd);
        adminDO.setSalt(uuid);
        adminDO.setIsDisable(YesOrNoEnum.NO.getCode());
        adminDO.setCreateTime(new Date());
        adminDO.setUpdateTime(new Date());
        adminDOMapper.insertSelective(adminDO);
    }

    @Override
    public void disable(String id, String flag) {
        AdminDO adminDO = new AdminDO();
        adminDO.setId(id);
        adminDO.setIsDisable(flag);
        adminDO.setUpdateTime(new Date());
        adminDOMapper.updateByPrimaryKeySelective(adminDO);
    }

    @Override
    public void resetPwd(AdminDO adminDO) {
        String password = adminDO.getPassword();
        String uuid = RandomUtil.randomUUID();
        String newPwd = PwdUtils.buildPwd(password, uuid);
        adminDO.setPassword(newPwd);
        adminDO.setSalt(uuid);
        adminDO.setUpdateTime(new Date());
        adminDOMapper.updateByPrimaryKeySelective(adminDO);
    }
}
