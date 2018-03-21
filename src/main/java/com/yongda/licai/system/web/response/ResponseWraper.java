package com.yongda.licai.system.web.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 响应模型
 * 作者：徐承恩
 * 邮箱：771247770@qq.com
 * 日期：2017/12/13-下午5:03
 */
@ApiModel
public class ResponseWraper<T> {

    @ApiModelProperty(value = "响应编码")
    private final String code;

    @ApiModelProperty(value = "响应消息")
    private final String msg;

    @ApiModelProperty(value = "响应数据")
    private T data;

    @ApiModelProperty(value = "是否调用成功")
    private boolean success;

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }

    public ResponseWraper<T> setData(T data) {
        this.data = data;
        return this;
    }

    public boolean getSuccess() {
        return success;
    }

    private ResponseWraper(String code, String msg) {
        this.code = code;
        this.msg = msg;
        this.success = ResponseEnum.SUCCESS.getCode().equals(code);
    }

    /**
     * 创建一个实例
     *
     * @param responseEnum 响应枚举
     * @return ResponseWraper
     */
    public static ResponseWraper newInstance(ResponseEnum responseEnum) {
        if (null == responseEnum) {
            responseEnum = ResponseEnum.SUCCESS;
        }
        return new ResponseWraper(responseEnum.getCode(), responseEnum.getMsg());
    }

    /**
     * 创建一个实例
     *
     * @param code    响应码
     * @param message 响应消息
     * @return ResponseWraper
     */
    public static ResponseWraper newInstance(String code, String message) {
        return new ResponseWraper(code, message);
    }

    /**
     * 成功
     *
     * @return ResponseWraper
     */
    public static ResponseWraper ok() {
        return new ResponseWraper(ResponseEnum.SUCCESS.getCode(), ResponseEnum.SUCCESS.getMsg());
    }

    /**
     * 失败
     *
     * @return ResponseWraper
     */
    public static ResponseWraper fail() {
        return new ResponseWraper(ResponseEnum.FAIL.getCode(), ResponseEnum.FAIL.getMsg());
    }

    /**
     * 异常
     *
     * @return ResponseWraper
     */
    public static ResponseWraper error() {
        return new ResponseWraper(ResponseEnum.ERROR.getCode(), ResponseEnum.ERROR.getMsg());
    }

    /**
     * 其它
     *
     * @param message 消息
     * @return ResponseWraper
     */
    public static ResponseWraper other(String message) {
        return new ResponseWraper(ResponseEnum.FAIL.getCode(), message);
    }
}
