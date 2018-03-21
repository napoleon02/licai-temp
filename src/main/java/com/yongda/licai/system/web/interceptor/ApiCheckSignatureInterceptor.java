package com.yongda.licai.system.web.interceptor;

import com.yongda.licai.system.biz.ConfigService;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

/**
 * 接口校验签名拦截器
 * 作者：徐承恩
 * 邮箱：771247770@qq.com
 * 日期：2017/12/14-下午1:37
 */
public class ApiCheckSignatureInterceptor implements HandlerInterceptor {

    @Resource(name = "configService")
    private ConfigService configService;

    @Resource(name = "environment")
    private Environment environment;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object target) throws Exception {
        /*Class<?> targetClass = target.getClass();
        //获取当前环境
        String profile = environment.getProperty("profile");
        if (targetClass.isAssignableFrom(HandlerMethod.class) && ("test".equals(profile) || "prod".equals(profile))) {
            CheckSignature checkSignature = ((HandlerMethod) target).getMethodAnnotation(CheckSignature.class);
            if (null != checkSignature) {
                String signature = request.getParameter("signature");
                Enumeration<?> pNames = request.getParameterNames();
                Map<String, Object> request = new HashMap<>();
                while (pNames.hasMoreElements()) {
                    String pName = (String) pNames.nextElement();
                    if ("signature".equals(pName)) {
                        continue;
                    }
                    Object pValue = request.getParameter(pName);
                    request.put(pName, pValue);
                }

                String paramStr = buildParamStr(request, true);
                String _signature = DigestUtil.md5Hex(paramStr + configService.getByKey(ConfigConst.DCCJ_KEY));
                if (!StrUtil.equalsIgnoreCase(signature, _signature)) {
                    ResponseWraper responseWraper = ResponseWraper.newInstance(ResponseEnum.ERR_SIGNATURE).setData(false);
                    WebUtils.write(response, JSONUtil.toJsonStr(responseWraper));
                    return false;
                }

            }
        }*/
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

    /**
     * 构建参数字符串
     *
     * @param params 参数集合
     * @param encode 是否编码
     * @return 签名
     * @throws UnsupportedEncodingException 异常
     */
    private static String buildParamStr(Map<String, Object> params, boolean encode) throws UnsupportedEncodingException {
        Set<String> keysSet = params.keySet();
        Object[] keys = keysSet.toArray();
        Arrays.sort(keys);
        StringBuilder temp = new StringBuilder();
        boolean first = true;
        for (Object key : keys) {
            if (first) {
                first = false;
            } else {
                temp.append("&");
            }
            temp.append(key).append("=");
            Object value = params.get(key);
            String valueString = "";
            if (null != value) {
                valueString = String.valueOf(value);
            }
            temp.append(valueString);
        }
        return encode ? URLEncoder.encode(temp.toString(), "UTF-8").toLowerCase() : temp.toString();
    }
}
