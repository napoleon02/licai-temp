package com.yongda.licai.system.dal.mapper;

import com.yongda.licai.system.dal.model.PaymentDO;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository(value = "paymentDOMapper")
public interface PaymentDOMapper extends Mapper<PaymentDO> {
}