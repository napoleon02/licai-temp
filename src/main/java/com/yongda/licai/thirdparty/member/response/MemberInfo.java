package com.yongda.licai.thirdparty.member.response;

/**
 * 会员信息
 * 作者：徐承恩
 * 邮箱：771247770@qq.com
 * 日期：2018/1/30-下午5:51
 */
public class MemberInfo {

    private String verify_mobile;

    private String token;

    private String idcard_no;

    private String name;

    private String login_name;

    private String head_img;

    private String real_name_level;

    private String member_id;

    public String getVerify_mobile() {
        return verify_mobile;
    }

    public void setVerify_mobile(String verify_mobile) {
        this.verify_mobile = verify_mobile;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getIdcard_no() {
        return idcard_no;
    }

    public void setIdcard_no(String idcard_no) {
        this.idcard_no = idcard_no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin_name() {
        return login_name;
    }

    public void setLogin_name(String login_name) {
        this.login_name = login_name;
    }

    public String getHead_img() {
        return head_img;
    }

    public void setHead_img(String head_img) {
        this.head_img = head_img;
    }

    public String getReal_name_level() {
        return real_name_level;
    }

    public void setReal_name_level(String real_name_level) {
        this.real_name_level = real_name_level;
    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    @Override
    public String toString() {
        return "MemberInfo{" +
                "verify_mobile='" + verify_mobile + '\'' +
                ", token='" + token + '\'' +
                ", idcard_no='" + idcard_no + '\'' +
                ", name='" + name + '\'' +
                ", login_name='" + login_name + '\'' +
                ", head_img='" + head_img + '\'' +
                ", real_name_level='" + real_name_level + '\'' +
                ", member_id='" + member_id + '\'' +
                '}';
    }
}
