package com.yongda.licai.system.web.controller.admin.order;

import com.github.pagehelper.Page;
import com.xiaoleilu.hutool.util.StrUtil;
import com.yongda.licai.system.biz.OrderManagementService;
import com.yongda.licai.system.dal.dto.InvestOrderDto;
import com.yongda.licai.system.dal.model.InvestOrderDO;
import com.yongda.licai.system.dal.params.InvestOrderQueryParams;
import com.yongda.licai.system.web.controller.BaseController;
import com.yongda.licai.system.web.response.ResponseWraper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: napoleon
 * @Description: 管理员后台客户订单管理CONTROLLER
 * @Date: 2018/1/30 9:48
 * @Modified by:
 * @Version: 1.0.0
 */
@Controller
@RequestMapping("/admin/orderManagement")
public class OrderManagementController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(OrderManagementController.class);

    private static final String ROWS = "rows";
    private static final String TOTAL = "total";


    @Resource(name = "orderManagementService")
    private OrderManagementService orderManagementService;


    /**
     * @Author:nepoleon
     * @Descriptions: 初始化订单管理页面
     * @Param:null
     * @Date: 2018/1/30 18:05
     */
    @RequestMapping("/initPage")
    public String initPage() {
        return "/admin/order/orderManager.html";
    }

    /**
     * @Author:nepoleon
     * @Descriptions: 根据筛选条件查询客户订单列表
     * @Param:investOrderQueryParams 页面筛选条件参数
     * @Date: 2018/1/30 13:33
     */
    @GetMapping(value = "/queryAllOrders")
    @ResponseBody
    public ResponseWraper queryAllOrderInfoByPage(InvestOrderQueryParams investOrderQueryParams) {

        Map<String, Object> map = new HashMap<>();

        try {
            Page<InvestOrderDto> page = orderManagementService.queryAllOrderInfoByPage(investOrderQueryParams);
            map.put(ROWS, page.getResult());
            map.put(TOTAL, page.getTotal());
            return ResponseWraper.ok().setData(map);
        } catch (Exception e) {
            logger.error("根据筛选条件查询客户订单列表{}", e);
            return ResponseWraper.error();
        }
    }

    /**
     * @Author:nepoleon
     * @Descriptions: 根据订单ID查看该订单的交易详情
     * @Param:id 订单ID
     * @Date: 2018/1/30 14:30
     */
    @GetMapping(value = "/queryOrderDetailsById")
    public String queryOrderDetailsById(String id, ModelMap modelMap) {
        modelMap.put("detail", orderManagementService.queryOrderDetailsById(id));
        modelMap.put("isEdit", true);
        return "/admin/order/orderDetail.html";
    }

    /**
     * @Author:nepoleon
     * @Descriptions: 根据订单ID更改该笔订单状态[支持的修改状态:退款-REFUNDED,关闭-CLOSED,还款-REPAYSUCCESS]
     * @Param:ids 要更新的订单IDS[支持批量，多个ID之间用逗号进行拼接]
     * @Param:flag 退款-REFUNDED,关闭-CLOSED,还款-REPAYSUCCESS
     * @Date: 2018/2/3 9:30
     */
    @PostMapping("/updateOrderStatus")
    @ResponseBody
    public ResponseWraper updateOrderStatus(
            @RequestParam(name = "ids", required = true) String ids,
            @RequestParam(name = "flag", required = true) String flag) {

        try {
            Boolean result = orderManagementService.updateOrderStatus(ids, flag);

            if (result) {
                Integer size = ids.split(",").length;
                return ResponseWraper.ok()
                        .setData(flag.equals("REFUNDED") ? "成功退款" + size + "条订单" : flag.equals("CLOSED") ? "成功关闭" + size + "条订单" : flag.equals("REPAYSUCCESS") ? "成功还款" + size + "条订单" : "订单状态更新成功");
            } else {
                return ResponseWraper.error().setData("订单状态更新失败");
            }
        } catch (Exception e) {
            logger.error("根据订单ID更改该笔订单状态[支持的修改状态:退款-REFUNDED,关闭-CLOSED,还款-REPAYSUCCESS]{}", e);
            return ResponseWraper.error();
        }
    }

    /**
     * @Author:nepoleon
     * @Descriptions: 初始化订单列表中订单还款页面
     * @Param:orderIds 订单ID[支持多个订单同时还款]
     * @Date: 2018/2/7 16:27
     */
    @GetMapping("/repay")
    public String orderRepayInitPage(String orderIds, ModelMap modelMap) {
        modelMap.put("orderIds", orderIds);
        return "/admin/order/repay.html";
    }

    /**
     * @Author:nepoleon
     * @Descriptions: 后台订单还款单起接口[支持批量还款]{}
     * @Param:investOrderDO 还款订单参数
     * @Date: 2018/2/7 16:30
     */
    @PostMapping("/repay")
    @ResponseBody
    public ResponseWraper orderRepayForOneOrMany(InvestOrderDO investOrderDO) {
        try {
            String ids = investOrderDO.getId();
            if (StrUtil.isBlank(ids)) {
                return ResponseWraper.other("必填参数为空");
            }
            Map map = orderManagementService.orderRepayForOneOrMany(investOrderDO);
            return ResponseWraper.ok().setData(map);
        } catch (Exception e) {
            logger.error("后台订单还款单起接口[支持批量还款]{}", e);
            return ResponseWraper.error();
        }
    }

    /**
     * @Author:nepoleon
     * @Descriptions:对未付款的订单进行关闭同时释放购买的金额
     * @Param:id 要关闭订单的ID
     * @Date: 2018/2/7 19:15
     */
    @PostMapping("/closeOrder")
    @ResponseBody
    public ResponseWraper closeOrder(@RequestParam(name = "id") String id) {
        try {
            orderManagementService.closeOrder(id);
            return ResponseWraper.ok();
        } catch (Exception e) {
            logger.error("对未付款的订单进行关闭同时释放购买的金额异常：", e);
            return ResponseWraper.error();
        }
    }

    /**
     * @Author:nepoleon
     * @Descriptions:对已付款但是没有开始计息的订单进行退款操作同时释放金额
     * @Param:id 要退款订单的ID
     * @Date: 2018/2/7 19:15
     */
    @PostMapping("/refundOrder")
    @ResponseBody
    public ResponseWraper refundOrder(@RequestParam(name = "id") String id) {
        try {
            orderManagementService.refundOrder(id);
            return ResponseWraper.ok().setData("订单退款成功.");
        } catch (Exception e) {
            logger.error("对未付款的订单进行关闭同时释放购买的金额异常：", e);
            return ResponseWraper.error();
        }
    }

}
