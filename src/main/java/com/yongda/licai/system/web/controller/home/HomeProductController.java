package com.yongda.licai.system.web.controller.home;

import com.github.pagehelper.Page;
import com.xiaoleilu.hutool.collection.CollUtil;
import com.yongda.licai.annotation.LoginRequire;
import com.yongda.licai.enums.OnlineStatusEnum;
import com.yongda.licai.system.biz.OrderManagementService;
import com.yongda.licai.system.biz.ProductServiece;
import com.yongda.licai.system.dal.dto.InvestOrderDto;
import com.yongda.licai.system.dal.dto.ProductDto;
import com.yongda.licai.system.dal.mapper.InvestOrderDOMapper;
import com.yongda.licai.system.dal.model.ProductDO;
import com.yongda.licai.system.dal.params.InvestOrderQueryParams;
import com.yongda.licai.system.web.controller.BaseController;
import com.yongda.licai.system.web.response.ResponseWraper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: napoleon
 * @Description: 客户端产品相关CONTROLLER
 * @Date: 2018/2/1 12:41
 * @Modified by:napoleon
 * @Version: 1.0.0
 */
@Controller
@RequestMapping("/home/product")
@Api(tags = "4.客户端产品相关API", description = "4.客户端产品相关API")
public class HomeProductController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(HomeProductController.class);

    private static final String ROWS = "rows";
    private static final String TOTAL = "total";
    private static final String SYSTIME = "sysTime";
    private static final String TRUE = "true";

    @Resource(name = "productService")
    private ProductServiece productService;

    @Resource(name = "orderManagementService")
    private OrderManagementService orderManagementService;

    @Resource(name = "investOrderDOMapper")
    private InvestOrderDOMapper investOrderDOMapper;

    /**
     * @Author:nepoleon
     * @Descriptions:客户端查询平台产品列表
     * @Param:page 分页数据-当前页
     * @Param:rows 分页数据-每页记录数
     * @Date: 2018/2/1 12:51
     */
    @PostMapping(value = "/list")
    @ResponseBody
    @LoginRequire(ignore = false)
    @ApiOperation(value = "客户端查询平台产品列表", httpMethod = "POST")
    public ResponseWraper queryAllProduct(
            @ApiParam(value = "当前页", required = true)
            @RequestParam(defaultValue = "1") Integer page,
            @ApiParam(value = "每页记录数", required = true)
            @RequestParam(defaultValue = "10") Integer rows
    ) {

        Map<String, Object> map = new HashMap<>();

        try {
            Page<ProductDO> result = productService.queryAllProductByOnlineStatus(OnlineStatusEnum.UP.toString(), page, rows);
            List<ProductDO> list = result.getResult();
            if (CollUtil.isNotEmpty(list)) {
                for (ProductDO productDO : list) {
                    BigDecimal decimal = investOrderDOMapper.queryAlreadyBuyAmount(productDO.getId());
                    productDO.setSellNumber(decimal == null ? new BigDecimal(0) : decimal);
                }
            }
            map.put(ROWS, list);
            map.put(TOTAL, result.getTotal());
            map.put(SYSTIME, new Date());
            return ResponseWraper.ok().setData(map);
        } catch (Exception e) {
            logger.error("客户端查询平台产品列表{}", e);
            return ResponseWraper.error();
        }
    }

    /**
     * @Author:nepoleon
     * @Descriptions:根据产品ID查询产品详情
     * @Param:productId 产品ID
     * @Date: 2018/2/1 14:12
     */
    @PostMapping(value = "/queryProductById")
    @ResponseBody
    @LoginRequire(ignore = false)
    @ApiOperation(value = "根据产品ID查询产品详情", httpMethod = "POST")
    public ResponseWraper queryProductById(
            @ApiParam(value = "产品ID", required = true)
            @RequestParam String productId
    ) {
        try {
            ProductDto productDto = productService.queryProductById(productId);
            BigDecimal decimal = investOrderDOMapper.queryAlreadyBuyAmount(productDto.getId());
            productDto.setSellNumber(decimal == null ? new BigDecimal(0) : decimal);
            Map map = new HashMap();
            map.put("productDto", productDto);
            map.put("sysTime", new Date());
            return ResponseWraper.ok().setData(map);
        } catch (Exception e) {
            logger.error("根据产品ID查询产品详情{}", e);
            return ResponseWraper.error();
        }
    }

    /**
     * @Author:nepoleon
     * @Descriptions:根据产品ID查询该产品下的投标情况[分页查询]
     * @Param:productId 产品ID
     * @Param:page 当前页
     * @Param:rows 每页记录数
     * @Date: 2018/2/1 14:12
     */
    @PostMapping(value = "/queryRecordByProductId")
    @ResponseBody
    @LoginRequire(ignore = false)
    @ApiOperation(value = "根据产品ID查询该产品下的投标情况", httpMethod = "POST")
    public ResponseWraper queryRecordByProductId(
            @ApiParam(value = "产品ID", required = true)
            @RequestParam String productId,
            @ApiParam(value = "当前页", required = true)
            @RequestParam(defaultValue = "1") Integer page,
            @ApiParam(value = "每页记录数", required = true)
            @RequestParam(defaultValue = "10") Integer rows
    ) {

        Map<String, Object> map = new HashMap<>();

        try {
            InvestOrderQueryParams investOrderQueryParams = new InvestOrderQueryParams();
            investOrderQueryParams.setProductId(productId);
            investOrderQueryParams.setPage(page);
            investOrderQueryParams.setRows(rows);
            investOrderQueryParams.setTakeRecord(TRUE);
            Page<InvestOrderDto> orderDtos = orderManagementService.queryAllOrderInfoByPage(investOrderQueryParams);
            map.put(ROWS, orderDtos.getResult());
            map.put(TOTAL, orderDtos.getTotal());
            return ResponseWraper.ok().setData(map);
        } catch (Exception e) {
            logger.error("根据产品ID查询该产品下的投标情况{}", e);
            return ResponseWraper.error();
        }
    }


}
