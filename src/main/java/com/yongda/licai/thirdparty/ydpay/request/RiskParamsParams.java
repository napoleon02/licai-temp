package com.yongda.licai.thirdparty.ydpay.request;

/**
 * 风控参数
 * 作者：徐承恩
 * 邮箱：771247770@qq.com
 * 日期：2018/2/1-上午11:12
 */
public class RiskParamsParams {

    /**
     * 商品类目
     */
    private String frms_ware_category;

    /**
     * 商户用户唯一标识
     */
    private String user_id;

    /**
     * 商户用户登陆名
     */
    private String user_info_mercht_userlogin;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 绑定手机号
     */
    private String mobile;

    /**
     * 商户用户分类
     */
    private String user_info_mercht_usertype;

    /**
     * 注册时间
     */
    private String user_info_dt_register;

    /**
     * 注册IP
     */
    private String user_info_register_ip;

    public String getFrms_ware_category() {
        return frms_ware_category;
    }

    public void setFrms_ware_category(String frms_ware_category) {
        this.frms_ware_category = frms_ware_category;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_info_mercht_userlogin() {
        return user_info_mercht_userlogin;
    }

    public void setUser_info_mercht_userlogin(String user_info_mercht_userlogin) {
        this.user_info_mercht_userlogin = user_info_mercht_userlogin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUser_info_mercht_usertype() {
        return user_info_mercht_usertype;
    }

    public void setUser_info_mercht_usertype(String user_info_mercht_usertype) {
        this.user_info_mercht_usertype = user_info_mercht_usertype;
    }

    public String getUser_info_dt_register() {
        return user_info_dt_register;
    }

    public void setUser_info_dt_register(String user_info_dt_register) {
        this.user_info_dt_register = user_info_dt_register;
    }

    public String getUser_info_register_ip() {
        return user_info_register_ip;
    }

    public void setUser_info_register_ip(String user_info_register_ip) {
        this.user_info_register_ip = user_info_register_ip;
    }

    @Override
    public String toString() {
        return "RiskParamsParams{" +
                "frms_ware_category='" + frms_ware_category + '\'' +
                ", user_id='" + user_id + '\'' +
                ", user_info_mercht_userlogin='" + user_info_mercht_userlogin + '\'' +
                ", email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                ", user_info_mercht_usertype='" + user_info_mercht_usertype + '\'' +
                ", user_info_dt_register='" + user_info_dt_register + '\'' +
                ", user_info_register_ip='" + user_info_register_ip + '\'' +
                '}';
    }
}
