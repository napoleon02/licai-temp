package com.yongda.licai.system.job.handle.impl;

import com.yongda.licai.enums.OnlineStatusEnum;
import com.yongda.licai.enums.ProductStatusEnum;
import com.yongda.licai.system.dal.mapper.ProductDOMapper;
import com.yongda.licai.system.dal.model.ProductDO;
import com.yongda.licai.system.job.handle.MinuteJobHandle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 产品状态变更为立即抢购状态处理器
 * 作者：徐承恩
 * 邮箱：771247770@qq.com
 * 日期：2018/2/3-下午2:59
 */
@Component(value = "productStatusToDoBuyHandle")
public class ProductStatusToDoBuyHandle implements MinuteJobHandle {

    private static final Logger log = LoggerFactory.getLogger(ProductStatusToDoBuyHandle.class);

    @Resource(name = "productDOMapper")
    private ProductDOMapper productDOMapper;

    @Async(value = "myExecutor")
    @Override
    public void execute(Date date) {
        try {
            ProductDO productDO = new ProductDO();
            productDO.setStatus(ProductStatusEnum.DO_BUY.getCode());
            productDO.setUpdateTime(new Date());

            Example example = new Example(ProductDO.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("onlineStatus", OnlineStatusEnum.UP.getCode());
            criteria.andEqualTo("status", ProductStatusEnum.BEGIN.getCode());
            criteria.andLessThanOrEqualTo("bidBeginDate", date);
            productDOMapper.updateByExampleSelective(productDO, example);
        } catch (Exception e) {
            log.error("将产品状态变更为立即抢购状态异常：", e);
        }

    }
}
