package com.yongda.licai.system.web.controller.home;

import com.github.pagehelper.Page;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.resolver.font.DefaultFontProvider;
import com.xiaoleilu.hutool.convert.Convert;
import com.xiaoleilu.hutool.date.DatePattern;
import com.xiaoleilu.hutool.date.DateUtil;
import com.yongda.licai.annotation.LoginRequire;
import com.yongda.licai.enums.ProductExpireUnitEnum;
import com.yongda.licai.system.biz.OrderManagementService;
import com.yongda.licai.system.biz.PersonCenterService;
import com.yongda.licai.system.dal.dto.InvestOrderDto;
import com.yongda.licai.system.dal.params.InvestOrderQueryParams;
import com.yongda.licai.system.web.controller.BaseController;
import com.yongda.licai.system.web.response.ResponseWraper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: napoleon
 * @Description: 客户端个人中心CONTROLLER
 * @Date: 2018/1/31 15:12
 * @Modified by:
 * @Version: 1.0.0
 */
@Controller
@RequestMapping("/home/personCenter")
@Api(tags = "3.客户端个人中心相关接口", description = "3.客户端个人中心相关接口")
public class PersonCenterController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(PersonCenterController.class);

    private static final String ROWS = "rows";
    private static final String TOTAL = "total";

    @Resource(name = "personCenterService")
    private PersonCenterService personCenterService;

    @Resource(name = "orderManagementService")
    private OrderManagementService orderManagementService;

    @Resource
    private GroupTemplate groupTemplate;

    /**
     * @Author:nepoleon
     * @Descriptions: 前台个人中心查询该会员下已持有||已兑付的标的
     * @Param:investOrderQueryParams 标的查询参数 memberId-当前登录会员||flag(已持有--HOLD||已兑付--PAID)
     * @Date: 2018/1/31 16:03
     */
    @PostMapping(value = "/orderInfo")
    @ResponseBody
    @LoginRequire(ignore = false)
    @ApiOperation(value = "查询该会员下已持有||已兑付的标的", httpMethod = "POST")
    public ResponseWraper queryOrderInfoByMemberIdAndConditions(
            @ApiParam(value = "列表类型", required = true, allowableValues = "HOLD,PAID", defaultValue = "HOLD")
            @RequestParam(defaultValue = "HOLD") String flag,
            @ApiParam(value = "当前页", required = true)
            @RequestParam(defaultValue = "1") Integer page,
            @ApiParam(value = "每页记录数", required = true)
            @RequestParam(defaultValue = "10") Integer rows
    ) {

        Map<String, Object> map = new HashMap<>();

        try {
            InvestOrderQueryParams params = new InvestOrderQueryParams();
            params.setFlag(flag);
            params.setPage(page);
            params.setRows(rows);
            params.setMemberId(getMemberInfoByHome().getMember_id());
            Page<InvestOrderDto> pager = personCenterService.queryOrderInfoByMemberIdAndConditions(params);
            map.put(ROWS, pager.getResult());
            map.put(TOTAL, pager.getTotal());
            return ResponseWraper.ok().setData(map);
        } catch (Exception e) {
            logger.error("查询该会员下已持有||已兑付的标的列表异常：", e);
            return ResponseWraper.error();
        }
    }

    /**
     * @Author:nepoleon
     * @Descriptions:根据当前登录会员查询其累计收益和持有本息
     * @Param:memberId 当前登录会员ID
     * @Date: 2018/2/6 14:19
     */
    @PostMapping(value = "/queryRealIncomeAndAllIncomeSum")
    @ResponseBody
    @LoginRequire(ignore = false)
    @ApiOperation(value = "根据当前登录会员查询其累计收益和持有本息", httpMethod = "POST")
    public ResponseWraper queryRealIncomeAndAllIncomeSum(
            @ApiParam(value = "当前登录会员ID", required = true)
            @RequestParam String memberId
    ) {
        try {
            Map map = personCenterService.queryRealIncomeAndAllIncomeSum(memberId);
            return ResponseWraper.ok().setData(map);
        } catch (Exception e) {
            logger.error("根据当前登录会员查询其累计收益和持有本息：{}", e);
            return ResponseWraper.error();
        }
    }

    /**
     * 根据订单编号获取理财合同PDF
     */
    @GetMapping(value = "/getContractPdfByOrderId", produces = MediaType.APPLICATION_PDF_VALUE)
    @LoginRequire
    @ApiOperation(value = "根据订单编号获取理财合同PDF", httpMethod = "GET")
    public ResponseEntity<byte[]> getPdfByOrderNo(@RequestParam String orderId,
                                                  @RequestParam(value = "x-token") String token) throws Exception {
        Template template;
        InvestOrderDto investOrderDto = orderManagementService.queryOrderDetailsById(orderId);

        if (null == investOrderDto) {
            template = groupTemplate.getTemplate("/pdf/empty.html");
        } else {
            template = groupTemplate.getTemplate("/pdf/jiekuanhetong.html");
            //绑定模板变量

            Integer limit = investOrderDto.getLimit();
            String unit = investOrderDto.getUnit();
            Integer investDay = 0;
            if (ProductExpireUnitEnum.YEAR.getCode().equals(unit)) {
                investDay = limit * ProductExpireUnitEnum.YEAR.getDay();
            } else if (ProductExpireUnitEnum.MONTH.getCode().equals(unit)) {
                investDay = limit * ProductExpireUnitEnum.MONTH.getDay();
            } else if (ProductExpireUnitEnum.DAY.getCode().equals(unit)) {
                investDay = limit;
            }

            template.binding("name", investOrderDto.getMemberName());
            template.binding("idcard", investOrderDto.getIdcardNo());
            template.binding("phone", investOrderDto.getMemberPhone());
            template.binding("contractNo", investOrderDto.getContractNo());
            template.binding("payTime", DateUtil.format(investOrderDto.getPayTime(), DatePattern.NORM_DATE_PATTERN));
            template.binding("investAmount", investOrderDto.getInvestAmount());
            template.binding("investAmountCN", Convert.digitUppercase(Double.valueOf(investOrderDto.getInvestAmount().toString())));
            template.binding("apr", investOrderDto.getApr());
            template.binding("investDay", investDay);
        }

        String html = template.render();

        DefaultFontProvider defaultFontProvider = new DefaultFontProvider();
        defaultFontProvider.addFont("static/plugins/font/simsun.ttf");

        ConverterProperties converterProperties = new ConverterProperties();
        converterProperties.setFontProvider(defaultFontProvider);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        HtmlConverter.convertToPdf(html, outputStream, converterProperties);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_PDF);
        return new ResponseEntity<>(outputStream.toByteArray(),
                httpHeaders, HttpStatus.CREATED);
    }
}
