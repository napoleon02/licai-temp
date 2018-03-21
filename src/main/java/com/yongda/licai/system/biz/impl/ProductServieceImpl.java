package com.yongda.licai.system.biz.impl;

import com.github.pagehelper.ISelect;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yongda.licai.enums.OnlineStatusEnum;
import com.yongda.licai.enums.ProductStatusEnum;
import com.yongda.licai.system.biz.ProductServiece;
import com.yongda.licai.system.dal.dto.ProductDto;
import com.yongda.licai.system.dal.mapper.InvestOrderDOMapper;
import com.yongda.licai.system.dal.mapper.ProductDOMapper;
import com.yongda.licai.system.dal.mapper.SeqMapper;
import com.yongda.licai.system.dal.model.InvestOrderDO;
import com.yongda.licai.system.dal.model.ProductDO;
import com.yongda.licai.system.dal.params.BaseProductParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(value = "productService")
public class ProductServieceImpl implements ProductServiece {

    private static final Logger logger = LoggerFactory.getLogger(ProductServieceImpl.class);

    @Resource(name = "productDOMapper")
    private ProductDOMapper productDOMapper;

    @Resource(name = "seqMapper")
    private SeqMapper seqMapper;

    @Resource(name = "investOrderDOMapper")
    private InvestOrderDOMapper investOrderDOMapper;

    @Override
    public void addProduct(ProductDO productDo) {
        productDo.setStatus(ProductStatusEnum.BEGIN.getCode());
        productDo.setProductNo(seqMapper.getProductNo());
        productDo.setOnlineStatus(OnlineStatusEnum.WAIT.getCode());
        productDOMapper.insertSelective(productDo);
    }

    /**
     * 查询产品实现
     */
    @Override
    public Page<ProductDO> queryAllProduct(BaseProductParams baseProductParams, Integer page, Integer rows) {
        Page<ProductDO> startPage = PageHelper.startPage(page, rows).doSelectPage(new ISelect() {
            @Override
            public void doSelect() {
                productDOMapper.queryAllProduct(baseProductParams);
            }
        });
        return startPage;
    }

    /**
     * @Author:nepoleon
     * @Descriptions: 根据产品状态[上架||下架]查询所需产品
     * @Param:status 产品状态
     * @Param:page 当前页
     * @Param:rows 每页记录数
     * @Date: 2018/2/1 13:17
     */
    @Override
    public Page<ProductDO> queryAllProductByOnlineStatus(String status, Integer page, Integer rows) {
        return PageHelper.startPage(page, rows).doSelectPage(new ISelect() {
            @Override
            public void doSelect() {
                try {
                    productDOMapper.queryAllProductByOnlineStatus(status);
                } catch (SQLException e) {
                    logger.error("根据产品状态[上架||下架]查询所需产品{}", e.getMessage());
                }
            }
        });
    }

    /**
     * @Author:nepoleon
     * @Descriptions: 根据产品ID查询产品详情
     * @Param:productId 产品ID
     * @Date: 2018/2/1 14:11
     */
    @Override
    public ProductDto queryProductById(String productId) {

        /*查询出该产品已经被认购的金额数*/
        BigDecimal alreadyBuyAmount = investOrderDOMapper.queryAlreadyBuyAmount(productId);
        ProductDO productDO = productDOMapper.selectByPrimaryKey(productId);
        BigDecimal amount = productDO.getAmount() == null ? new BigDecimal("0") : productDO.getAmount();
        /*leaveAmount 为产品的总额度减去已经被认购的金额数*/
        BigDecimal leaveAmount = amount
                .subtract(alreadyBuyAmount == null ? new BigDecimal("0") : alreadyBuyAmount);
        ProductDto productDto = new ProductDto();
        BeanUtils.copyProperties(productDO, productDto);
        productDto.setLeaveAmount(leaveAmount);
        return productDto;

    }

    /**
     * 修改产品状态为上架或下架
     */
    @Override
    public void updateOnlineStatusUpOrDown(ProductDO productDO) {
        productDOMapper.updateByPrimaryKeySelective(productDO);

    }

    /**
     * 产品详情
     */
    @Override
    public ProductDO queryProductByIdM(String id) {
        ProductDO productoDO = new ProductDO();
        productoDO.setId(id);
        return productDOMapper.selectByPrimaryKey(id);
    }

    /**
     * @Author:nepoleon
     * @Descriptions:通过ID删除产品
     * @Param:id 产品ID
     * @Date: 2018/2/5 20:31
     */
    @Override
    public Map deleteProductById(String id) {
        Map map = new HashMap();
        try {
            Example example = new Example(InvestOrderDO.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("productId", id);
            List<InvestOrderDO> investOrderDOS = investOrderDOMapper.selectByExample(example);
            if (investOrderDOS.isEmpty()) {
                productDOMapper.deleteByPrimaryKey(id);
                map.put("success", true);
                map.put("msg", "产品删除成功.");
                return map;
            }
            map.put("success", false);
            map.put("msg", "此产品有客户下单,暂时不能进行删除操作.");
            return map;
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * @Author:nepoleon
     * @Descriptions:通过产品ID更改产品状态[单个还款]
     * @Param:id 产品ID
     * @Date: 2018/2/5 20:45
     */
    @Override
    public Map productRepayById(ProductDO dto) throws SQLException {
        Map map = new HashMap();
        ProductDO productDO = productDOMapper.selectByPrimaryKey(dto.getId());
        if (ProductStatusEnum.EXPIRE.getCode().equals(productDO.getStatus())
                || ProductStatusEnum.REFUND.getCode().equals(productDO.getStatus())) {
            Boolean result = productDOMapper.updateByPrimaryKeySelective(dto) == 1;
            map.put("success", result);
            map.put("msg", "产品状态修改成功.");
            return map;
        }
        map.put("success", false);
        map.put("msg", "此订单状态不是已结束或者还款中的,不允许进行还款操作.");
        return map;
    }


}
