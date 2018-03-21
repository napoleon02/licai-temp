package com.yongda.licai.thirdparty.member;

import com.xiaoleilu.hutool.http.HttpRequest;
import com.xiaoleilu.hutool.http.HttpResponse;
import com.xiaoleilu.hutool.http.HttpStatus;
import com.xiaoleilu.hutool.http.HttpUtil;
import com.xiaoleilu.hutool.json.JSONUtil;
import com.xiaoleilu.hutool.util.StrUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 会员客户端
 * 作者：徐承恩
 * 邮箱：771247770@qq.com
 * 日期：2018/1/30-下午4:18
 */
public class MemberClient {

    private static final Logger log = LoggerFactory.getLogger(MemberClient.class);

    /**
     * 主机地址
     */
    private String host;

    /**
     * 公钥
     */
    private String publicKey;

    /**
     * 构造方法
     *
     * @param host      主机地址
     * @param publicKey 公钥
     */
    public MemberClient(String host, String publicKey) {
        this.host = StrUtil.appendIfMissing(host, "/", "/");
        this.publicKey = publicKey;
        log.info("创建会员系统客户端：host[{}],publicKey[{}]", host, publicKey);
    }

    /**
     * 执行
     *
     * @param request 请求模型
     * @param <T>     响应模型的泛型
     * @return 响应模型
     * @throws Exception 异常
     */
    public <T extends BaseResponse> T execute(BaseRequest<T> request) throws Exception {
        log.info("调用会员系统请求参数：{}", JSONUtil.toJsonStr(request));
        String result = this.doRequest(request);
        log.info("调用会员系统响应结果：{}", result);
        return JSONUtil.toBean(result, request.getTargetClass());
    }

    /**
     * 处理请求
     *
     * @param request 请求模型
     * @return json字符串
     * @throws Exception 异常
     */
    private String doRequest(BaseRequest request) throws Exception {
        String biz = request.getBiz();
        HttpRequest httpRequest = HttpUtil.createPost(host + biz);
        httpRequest.form(request.getParams(publicKey));
        httpRequest.timeout(1000 * 10);
        HttpResponse response = httpRequest.execute();
        if (response.getStatus() == HttpStatus.HTTP_OK) {
            return response.body();
        } else {
            throw new Exception("请求会员系统异常:" + response.toString());
        }
    }

}
