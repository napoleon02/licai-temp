package com.yongda.licai.system.job.handle.impl;

import com.xiaoleilu.hutool.collection.CollUtil;
import com.yongda.licai.enums.OnlineStatusEnum;
import com.yongda.licai.enums.ProductStatusEnum;
import com.yongda.licai.system.dal.mapper.InvestOrderDOMapper;
import com.yongda.licai.system.dal.mapper.ProductDOMapper;
import com.yongda.licai.system.dal.model.ProductDO;
import com.yongda.licai.system.job.handle.MinuteJobHandle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 产品状态变更为流标状态或者计息中状态
 * 作者：徐承恩
 * 邮箱：771247770@qq.com
 * 日期：2018/2/7-上午10:30
 */
@Component(value = "productStatusToLoseHandle")
public class ProductStatusToLoseOrCountHandle implements MinuteJobHandle {

    private static final Logger log = LoggerFactory.getLogger(ProductStatusToLoseOrCountHandle.class);

    @Resource(name = "productDOMapper")
    private ProductDOMapper productDOMapper;

    @Resource(name = "investOrderDOMapper")
    private InvestOrderDOMapper investOrderDOMapper;

    @Async(value = "myExecutor")
    @Override
    public void execute(Date date) {
        try {
            Example example = new Example(ProductDO.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("onlineStatus", OnlineStatusEnum.UP.getCode());
            criteria.andIn("status", Arrays.asList(ProductStatusEnum.DO_BUY.getCode(), ProductStatusEnum.BUY_OVER.getCode()));
            criteria.andLessThanOrEqualTo("bidEndDate", date);
            List<ProductDO> list = productDOMapper.selectByExample(example);

            if (CollUtil.isNotEmpty(list)) {
                ProductDO _productDO;
                for (ProductDO productDO : list) {
                    _productDO = new ProductDO();
                    _productDO.setId(productDO.getId());
                    _productDO.setUpdateTime(date);

                    BigDecimal sellAmount = investOrderDOMapper.queryAlreadyBuyAmount(productDO.getId());
                    if (null == sellAmount) {
                        sellAmount = new BigDecimal("0");
                    }

                    //如果已投资额小于标的总额则流标否则计息
                    if (sellAmount.compareTo(productDO.getAmount()) < 0) {
                        _productDO.setStatus(ProductStatusEnum.LOSE.getCode());
                    } else {
                        _productDO.setStatus(ProductStatusEnum.COUNT.getCode());
                    }
                    productDOMapper.updateByPrimaryKeySelective(_productDO);
                }
            }

        } catch (Exception e) {
            log.error("将产品状态变更为流标状态或者计息中状态异常：", e);
        }
    }
}
