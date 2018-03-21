package com.yongda.licai.system.dal.mapper;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * 产品编号序列
 * 作者：徐承恩
 * 邮箱：771247770@qq.com
 * 日期：2018/1/30-下午6:04
 */
@Repository
public interface SeqMapper {

    /**
     * 获取产品编号
     *
     * @return String
     */
    @Select(value = "SELECT SEQ_PRODUCT_NO.NEXTVAL FROM DUAL")
    String getProductNo();

    /**
     * 获取合同编号
     *
     * @return String
     */
    @Select(value = "SELECT SEQ_CONTRACT_NO.NEXTVAL FROM DUAL")
    String getContractNo();
}
