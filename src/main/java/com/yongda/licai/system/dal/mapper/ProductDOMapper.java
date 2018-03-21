package com.yongda.licai.system.dal.mapper;

import com.github.pagehelper.Page;
import com.yongda.licai.system.dal.model.ProductDO;
import com.yongda.licai.system.dal.params.BaseProductParams;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

@Repository
public interface ProductDOMapper extends Mapper<ProductDO> {
    /**
     * 条件查询产品
     */
    Page<ProductDO> queryAllProduct(BaseProductParams baseProductParams);

    /**
     * @Author:nepoleon
     * @Descriptions: 根据产品状态[上架||下架]查询所需产品
     * @Param:status 产品状态
     * @Date: 2018/2/1 13:17
     */
    List<ProductDO> queryAllProductByOnlineStatus(String status) throws SQLException;

    /**
     * 根据ID查询（带行锁）
     *
     * @param id 主键
     * @return ProductDO
     */
    ProductDO findByIdWithForUpdate(String id);

    /**
     * 更新销售金额
     *
     * @param id     主键
     * @param amount 金额
     * @return Integer
     */
    int updateSellNumber(@Param("id") String id, @Param("amount") BigDecimal amount);

}