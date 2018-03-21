package com.yongda.licai.system.biz.impl;

import com.yongda.licai.system.biz.PaymentService;
import com.yongda.licai.system.dal.mapper.PaymentDOMapper;
import com.yongda.licai.system.dal.model.PaymentDO;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;

/**
 * 实现
 * 作者：徐承恩
 * 邮箱：771247770@qq.com
 * 日期：2018/2/6-下午4:14
 */
@Service(value = "paymentService")
public class PaymentServiceImpl implements PaymentService {

    @Resource(name = "paymentDOMapper")
    private PaymentDOMapper paymentDOMapper;

    @Override
    public PaymentDO findByOrderNo(String orderNo) {
        Example example = new Example(PaymentDO.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("orderNo", orderNo);
        return paymentDOMapper.selectOneByExample(example);
    }
}
