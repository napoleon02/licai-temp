package com.yongda.licai.system.biz;


import com.github.pagehelper.Page;
import com.yongda.licai.system.dal.dto.ProductDto;
import com.yongda.licai.system.dal.model.ProductDO;
import com.yongda.licai.system.dal.params.BaseProductParams;

import java.sql.SQLException;
import java.util.Map;

/**
 * 产品标业务接口
 *
 * @author 苏建军
 */
public interface ProductServiece {
    /**
     * 生成标信息
     *
     * @param
     * @return
     */
    void addProduct(ProductDO productDo);

    /**
     * 查询标
     */
    Page<ProductDO> queryAllProduct(BaseProductParams baseProductParams, Integer page, Integer rows);

    /**
     * @Author:nepoleon
     * @Descriptions: 根据产品状态[上架||下架]查询所需产品
     * @Param:status 产品状态
     * @Param:page 当前页
     * @Param:rows 每页记录数
     * @Date: 2018/2/1 13:17
     */
    Page<ProductDO> queryAllProductByOnlineStatus(String status, Integer page, Integer rows);

    /**
     * @Author:nepoleon
     * @Descriptions: 根据产品ID查询产品详情
     * @Param:productId 产品ID
     * @Date: 2018/2/1 14:11
     */
    ProductDto queryProductById(String productId);


    void updateOnlineStatusUpOrDown(ProductDO productDO);

    ProductDO queryProductByIdM(String id);

    /**
     * @Author:nepoleon
     * @Descriptions:通过ID删除产品
     * @Param:id 产品ID
     * @Date: 2018/2/5 20:31
     */
    Map deleteProductById(String id) throws SQLException;

    /**
     * @Author:nepoleon
     * @Descriptions:通过产品ID更改产品状态[还款]
     * @Param:dto 产品实体
     * @Date: 2018/2/5 20:45
     */
    Map productRepayById(ProductDO dto) throws SQLException;


}
