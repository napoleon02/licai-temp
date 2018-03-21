package com.yongda.licai.system.web.controller.common;

import com.xiaoleilu.hutool.collection.CollUtil;
import com.xiaoleilu.hutool.date.DatePattern;
import com.xiaoleilu.hutool.date.DateUtil;
import com.xiaoleilu.hutool.util.StrUtil;
import com.yongda.licai.constant.GlobalConst;
import com.yongda.licai.system.dal.mapper.InvestOrderDOMapper;
import com.yongda.licai.system.dal.mapper.SeqMapper;
import com.yongda.licai.system.dal.model.InvestOrderDO;
import com.yongda.licai.system.web.response.ResponseWraper;
import com.yongda.licai.thirdparty.member.MemberClient;
import com.yongda.licai.thirdparty.member.request.DcmmLoginRequest;
import com.yongda.licai.thirdparty.member.response.LoginResponse;
import com.yongda.licai.thirdparty.member.response.MemberInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * 同步PDF信息控制器
 * 作者：徐承恩
 * 邮箱：771247770@qq.com
 * 日期：2018/2/27-下午2:38
 */
@Controller
@RequestMapping(value = "/common/pdf")
public class SyncPdfHandleController {

    private static final Logger log = LoggerFactory.getLogger(SyncPdfHandleController.class);

    @Resource(name = "investOrderDOMapper")
    private InvestOrderDOMapper investOrderDOMapper;

    @Resource(name = "seqMapper")
    private SeqMapper seqMapper;

    @Resource(name = "createMemberClient")
    private MemberClient memberClient;

    @GetMapping(value = "/syncPdf")
    @ResponseBody
    public ResponseWraper syncPdf() {
        try {
            Example example = new Example(InvestOrderDO.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andIn("status", Arrays.asList("2", "3", "4", "5", "6", "7"));
            List<InvestOrderDO> investOrderDOS = investOrderDOMapper.selectByExample(example);
            if (CollUtil.isNotEmpty(investOrderDOS)) {
                for (InvestOrderDO investOrderDO : investOrderDOS) {
                    DcmmLoginRequest request = new DcmmLoginRequest();
                    request.setMemberId(investOrderDO.getMemberId());
                    LoginResponse response = memberClient.execute(request);
                    if (response.getSuccess()) {

                        String number = StrUtil.fillBefore(seqMapper.getContractNo(), '0', 6);
                        String format = DateUtil.format(investOrderDO.getPayTime(), DatePattern.PURE_DATE_PATTERN);

                        MemberInfo memberInfo = response.getData();
                        InvestOrderDO _investOrderDO = new InvestOrderDO();
                        _investOrderDO.setId(investOrderDO.getId());
                        _investOrderDO.setIdcardNo(memberInfo.getIdcard_no());
                        _investOrderDO.setContractNo(GlobalConst.CONTRACT_NUMBER_PREFIX + format + number);
                        investOrderDOMapper.updateByPrimaryKeySelective(_investOrderDO);
                    }
                }
            }
            return ResponseWraper.ok().setData(investOrderDOMapper.selectByExample(example));
        } catch (Exception e) {
            log.error("同步订单信息异常：", e);
            return ResponseWraper.error();
        }

    }

}
