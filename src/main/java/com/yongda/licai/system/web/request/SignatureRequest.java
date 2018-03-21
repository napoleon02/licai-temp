package com.yongda.licai.system.web.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 签名请求模型
 * 作者：徐承恩
 * 邮箱：771247770@qq.com
 * 日期：2017/12/14-下午2:30
 */
@ApiModel
public class SignatureRequest {

    @ApiModelProperty(value = "时间戳", required = true)
    @NotBlank
    private String timestamp;

    @ApiModelProperty(value = "签名", required = true)
    @NotBlank
    private String signature;

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

}
