package com.yongda.licai.thirdparty.ydpay;

import com.itrus.cryptorole.CryptoException;
import com.itrus.cryptorole.bc.RecipientBcImpl;
import com.itrus.cryptorole.bc.SenderBcImpl;
import com.itrus.cvm.CVM;
import com.itrus.svm.SignerAndEncryptedDigest;
import com.xiaoleilu.hutool.collection.CollUtil;
import com.xiaoleilu.hutool.http.HttpResponse;
import com.xiaoleilu.hutool.http.HttpUtil;
import com.xiaoleilu.hutool.json.JSONUtil;
import com.xiaoleilu.hutool.lang.Base64;
import com.xiaoleilu.hutool.util.StrUtil;
import com.xiaoleilu.hutool.util.URLUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * 永达支付客户端
 * 作者：徐承恩
 * 邮箱：771247770@qq.com
 * 日期：2018/2/1-下午1:16
 */
public class YongDaPayClient {

    private static final Logger log = LoggerFactory.getLogger(YongDaPayClient.class);

    /**
     * cer文件路径
     */
    private String cerPath;

    /**
     * pfx文件路径
     */
    private String pfxPath;

    /**
     * cvm文件路径
     */
    private String cvmPath;

    /**
     * pfx文件密码
     */
    private String pfxPwd;

    /**
     * 合作方ID
     */
    private String partnerId;

    /**
     * 签名方式
     */
    private String signType;

    /**
     * 字符集
     */
    private String charset;

    /**
     * 主机地址
     */
    private String host;

    /**
     * 卖家标识ID
     */
    private String sellerId;

    public String getCvmPath() {
        return cvmPath;
    }

    public YongDaPayClient setCvmPath(String cvmPath) {
        this.cvmPath = cvmPath;
        return this;
    }

    /**
     * 天威发送器
     */
    protected SenderBcImpl sender;

    /**
     * 天威接收器
     */
    protected RecipientBcImpl recipient;

    public String getCerPath() {
        return cerPath;
    }

    public YongDaPayClient setCerPath(String cerPath) {
        this.cerPath = cerPath;
        return this;
    }

    public String getPfxPath() {
        return pfxPath;
    }

    public YongDaPayClient setPfxPath(String pfxPath) {
        this.pfxPath = pfxPath;
        return this;
    }

    public String getPfxPwd() {
        return pfxPwd;
    }

    public YongDaPayClient setPfxPwd(String pfxPwd) {
        this.pfxPwd = pfxPwd;
        return this;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public YongDaPayClient setPartnerId(String partnerId) {
        this.partnerId = partnerId;
        return this;
    }

    public String getSignType() {
        return signType;
    }

    public YongDaPayClient setSignType(String signType) {
        this.signType = signType;
        return this;
    }

    public String getCharset() {
        return charset;
    }

    public YongDaPayClient setCharset(String charset) {
        this.charset = charset;
        return this;
    }

    public String getHost() {
        return host;
    }

    public YongDaPayClient setHost(String host) {
        this.host = host;
        return this;
    }

    public String getSellerId() {
        return sellerId;
    }

    public YongDaPayClient setSellerId(String sellerId) {
        this.sellerId = sellerId;
        return this;
    }

    /**
     * 构建客户端
     */
    public YongDaPayClient build() {
        try {
            sender = new SenderBcImpl();
            recipient = new RecipientBcImpl();
            CVM.config(cvmPath);
            sender.initCertWithKey(pfxPath, pfxPwd);
            recipient.initCertWithKey(pfxPath, pfxPwd);
            CertificateFactory factory = CertificateFactory.getInstance("X.509");
            X509Certificate X509Cert = (X509Certificate) factory.generateCertificate(new FileInputStream(cerPath));
            sender.addRecipientCert(X509Cert);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        log.info("创建永达支付客户端：{}", JSONUtil.toJsonStr(this));
        return this;
    }

    /**
     * 执行
     *
     * @param request 请求模型
     * @param host    主机地址
     * @param <T>     泛型结果模型
     * @return YongDaPayResponse
     */
    public <T extends YongDaPayResponse> T execute(YongDaPayRequest<T> request, String host) {
        Map<String, String> params = buildReqParams(request.getParams());
        log.info("请求支付网关参数：{}", params);

        HttpResponse httpResponse = HttpUtil
                .createPost(StrUtil.isBlank(host) ? this.host : host)
                .form(convertMapType(params)).execute();

        int status = httpResponse.getStatus();
        if (status != 200) {
            throw new RuntimeException("支付请求失败");
        }

        String body = httpResponse.body();

        log.info("支付响应参数：{}", body);
        //is_success=T&_input_charset=UTF-8&result=true&isForwardCashier=false&tradeList=20180207211514427658^101151800931515769257^null^0.01^0.00^0.00^TRADE_FINISHED^20180207211613

        Class<T> clazz = request.getResponseClass();
        T response;
        try {
            response = clazz.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        response.setBody(body);
        response.setRequestParams(params);
        return response;
    }


    /**
     * WEB支付执行
     *
     * @param request 请求参数
     * @param <T>     响应泛型
     * @return YongDaPayResponse
     */
    public <T extends YongDaPayResponse> T pageExecute(YongDaPayRequest<T> request) {
        Map<String, String> params = buildReqParams(request.getParams());
        log.info("请求支付网关参数：{}", params);
        Class<T> clazz = request.getResponseClass();
        T response;
        try {
            response = clazz.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        response.setBody(YongDaPayUtil.buildForm(host, params));
        response.setRequestParams(params);
        return response;
    }

    /**
     * 签名校验
     *
     * @param params 请求参数
     * @return boolean
     */
    public YongDaPayVerifyResult checkSign(Map<String, String> params) {
        YongDaPayVerifyResult result = new YongDaPayVerifyResult(true);
        if (CollUtil.isEmpty(params)) {
            result.setSuccess(false);
            result.addInfo(YongDaPayVerifyResult.exMsg, "参数为空");
        }

        log.info("待验签参数：{}", JSONUtil.toJsonStr(params));

        String sign = params.get("sign");
        String charset = params.get("_input_charset");

        params.remove("sign");
        params.remove("sign_type");

        //忽略掉空值参数
        Map<String, String> _params = ignoreNull(params);

        //构建待签名参数字符串
        String urlParam = URLUtil.decode(HttpUtil.toParams(_params));

        log.info("待验签参数字符串：{}", urlParam);

        try {
            result = verify(urlParam, sign, charset);
        } catch (Exception e) {
            result.setSuccess(false);
            result.addInfo(YongDaPayVerifyResult.exMsg, e.getMessage());
        }
        return result;
    }

    /**
     * 构建请求参数
     *
     * @param params 请求参数Map
     * @return Map
     */
    private Map<String, String> buildReqParams(Map<String, String> params) {
        params.put("partner_id", partnerId);
        params.put("_input_charset", charset);

        log.info("待签名参数：{}", JSONUtil.toJsonStr(params));

        //忽略掉空值参数
        Map<String, String> _params = ignoreNull(params);

        //构建待签名参数字符串
        String urlParam = URLUtil.decode(HttpUtil.toParams(_params));

        log.info("待签名字符串：{}", urlParam);

        //签名
        String sign = doSign(urlParam);

        log.info("签名后字符串：{}", sign);

        params.put("sign", sign);
        params.put("sign_type", signType);

        return valueEncode(params);
    }

    /**
     * 参数值url转码
     *
     * @param params 请求参数Map
     * @return Map
     */
    private static Map<String, String> valueEncode(Map<String, String> params) {
        Map<String, String> result = new HashMap<>();
        if (params == null || params.size() <= 0) {
            return result;
        }
        String charset = params.get("_input_charset");
        for (String key : params.keySet()) {
            String value = params.get(key);
            if (StrUtil.isNotBlank(value)) {
                value = URLUtil.encode(value, charset);
            }
            result.put(key, value);
        }
        return result;
    }

    /**
     * 忽略Null值参数
     *
     * @param params 请求参数Map
     * @return Map
     */
    private Map<String, String> ignoreNull(Map<String, String> params) {
        TreeMap<String, String> map = new TreeMap<>();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (StrUtil.isNotBlank(entry.getValue())) {
                map.put(entry.getKey(), entry.getValue());
            }
        }
        return map;
    }

    /**
     * 签名
     *
     * @param paramStr 待签名字符串
     * @return 签名后字符串
     */
    private String doSign(String paramStr) {
        try {
            return sender.signMessage(paramStr);
        } catch (CryptoException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 转换泛型
     *
     * @param <T> 类型
     * @param map 原集合
     * @return 新集合
     */
    private static <T> Map<String, Object> convertMapType(Map<String, T> map) {
        Map<String, Object> result = new TreeMap<>();

        if ((map == null) || (map.size() <= 0)) {
            return result;
        }

        for (Iterator<Map.Entry<String, T>> iterator = map.entrySet().iterator(); iterator.hasNext(); ) {
            Map.Entry<String, T> entry = iterator.next();
            String key = entry.getKey();
            T value = entry.getValue();
            if (value == null) {
                result.put(key, null);
            } else {
                result.put(key, value.toString());
            }
        }
        return result;
    }

    /**
     * 验签
     *
     * @param text    待签名参数字符串
     * @param sign    签名字符串
     * @param charset 字符集
     * @return YongDaPayVerifyResult
     * @throws Exception
     */
    public YongDaPayVerifyResult verify(String text, String sign, String charset) throws Exception {
        RecipientBcImpl recipient = new RecipientBcImpl();
        byte[] strByte = StrUtil.bytes(text, charset);
        SignerAndEncryptedDigest encryptedDigest = recipient.verifyAndParsePkcs7(strByte, Base64.decode(sign));
        com.itrus.cert.X509Certificate cert = com.itrus.cert.X509Certificate.getInstance(encryptedDigest.getSigner());
        YongDaPayVerifyResult result = new YongDaPayVerifyResult(true);
        result.addInfo("subjectDN", cert.getSubjectDNString());
        //以下代码开始验证证书
        int cvm = CVM.verifyCertificate(cert);
        if (cvm != CVM.VALID) {
            String cvmMsg = "";
            switch (cvm) {
                case CVM.CVM_INIT_ERROR:
                    cvmMsg = "CVM初始化错误，请检查配置文件或给CVM增加支持的CA。";
                    break;
                case CVM.CRL_UNAVAILABLE:
                    cvmMsg = "CRL不可用，未知状态。";
                    break;
                case CVM.EXPIRED:
                    cvmMsg = "证书已过期。";
                    break;
                case CVM.ILLEGAL_ISSUER:
                    cvmMsg = "非法颁发者。";
                    break;
                case CVM.REVOKED:
                    cvmMsg = "证书已吊销。";
                    break;
                case CVM.UNKNOWN_ISSUER:
                    cvmMsg = "不支持的颁发者。请检查cvm.xml配置文件";
                    break;
                case CVM.REVOKED_AND_EXPIRED:
                    cvmMsg = "证书被吊销且已过期。";
                    break;
            }
            result.setSuccess(false);
            result.addInfo(YongDaPayVerifyResult.exMsg, cvmMsg);
        }
        return result;
    }
}
