package com.yongda.licai.system.web.controller.home;

import com.xiaoleilu.hutool.http.HttpUtil;
import com.xiaoleilu.hutool.json.JSONObject;
import com.xiaoleilu.hutool.json.JSONUtil;
import com.xiaoleilu.hutool.util.MapUtil;
import com.xiaoleilu.hutool.util.RandomUtil;
import com.xiaoleilu.hutool.util.StrUtil;
import com.yongda.licai.annotation.LoginRequire;
import com.yongda.licai.constant.GlobalConst;
import com.yongda.licai.system.web.controller.BaseController;
import com.yongda.licai.system.web.response.ResponseEnum;
import com.yongda.licai.system.web.response.ResponseWraper;
import com.yongda.licai.thirdparty.member.MemberClient;
import com.yongda.licai.thirdparty.member.request.LoginRequest;
import com.yongda.licai.thirdparty.member.response.LoginResponse;
import com.yongda.licai.thirdparty.member.response.MemberInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 前台系统登录控制器
 * 作者：徐承恩
 * 邮箱：771247770@qq.com
 * 日期：2018/1/31-下午1:07
 */
@Controller
@RequestMapping(value = "/home/login")
@Api(tags = "1.用户登录接口", description = "1.用户登录接口")
public class HomeLoginController extends BaseController {

    private static final Logger log = LoggerFactory.getLogger(HomeLoginController.class);

    @Resource(name = "createMemberClient")
    private MemberClient memberClient;

    @Resource(name = "redisTemplate")
    private RedisTemplate<String, Object> redisTemplate;

    @Resource(name = "environment")
    private Environment environment;

    /**
     * 用户登录
     */
    @PostMapping(value = "/doLogin")
    @ResponseBody
    @ApiOperation(value = "用户登录", httpMethod = "POST")
    @LoginRequire(ignore = true)
    public ResponseWraper login(@ApiParam(value = "用户名", required = true)
                                @RequestParam String userName,
                                @ApiParam(value = "密码", required = true)
                                @RequestParam String password) {
        try {
            ValueOperations<String, Object> opsForValue = redisTemplate.opsForValue();

            //判断帐号是否被锁定
            if (redisTemplate.hasKey("LOGIN_LOCK_" + userName)) {
                return ResponseWraper.newInstance(ResponseEnum.LOGIN_LOCK)
                        .setData(MapUtil.builder()
                                .put("lockTime", opsForValue.get("LOGIN_LOCK_" + userName))
                                .put("serverTime", new Date()).map());
            }

            LoginRequest loginRequest = new LoginRequest();
            loginRequest.setAccount(userName);
            loginRequest.setPassword(password);
            LoginResponse loginResponse = memberClient.execute(loginRequest);

            //登录失败
            if (!loginResponse.getSuccess()) {
                Long count = opsForValue.increment("LOGIN_FAIL_COUNT_" + userName, 1);
                if (count == 1) {
                    redisTemplate.expire("LOGIN_FAIL_COUNT_" + userName, 5, TimeUnit.MINUTES);
                } else if (count >= 5) {
                    opsForValue.set("LOGIN_LOCK_" + userName, new Date(), 30, TimeUnit.MINUTES);
                    return ResponseWraper.newInstance(ResponseEnum.LOGIN_LOCK).setData(MapUtil.builder()
                            .put("lockTime", opsForValue.get("LOGIN_LOCK_" + userName))
                            .put("serverTime", new Date()).map());
                }
                return ResponseWraper.other("用户名或密码错误").setData(count);
            }

            //登录成功
            MemberInfo memberInfo = loginResponse.getData();

            String profile = environment.getProperty("profile");

            //生产环境校验白名单
            if (GlobalConst.PROFILE_PROD.equalsIgnoreCase(profile)) {
                //白名单校验
                if (!verifyWhiteList(memberInfo)) {
                    return ResponseWraper.newInstance(ResponseEnum.LOGIN_WHITE);
                }
            }

            String token = RandomUtil.randomUUID();
            Map<String, Object> data = new HashMap<>();
            data.put("userInfo", memberInfo);
            data.put("token", token);

            //清除锁定key
            redisTemplate.delete(Arrays.asList("LOGIN_FAIL_COUNT_" + userName, "LOGIN_LOCK_" + userName));

            //缓存一个小时
            opsForValue.set(token, memberInfo, 3600, TimeUnit.SECONDS);
            return ResponseWraper.ok().setData(data);
        } catch (Exception e) {
            log.error("前台用户登录异常：", e);
            return ResponseWraper.error();
        }
    }

    /**
     * 退出登录
     */
    @PostMapping(value = "/logout")
    @ResponseBody
    @ApiOperation(value = "退出登录", httpMethod = "POST")
    @LoginRequire
    public ResponseWraper logout() {
        try {
            redisTemplate.delete(getTokenByHome());
            return ResponseWraper.ok();
        } catch (Exception e) {
            log.error("前台用户退出登录异常：", e);
            return ResponseWraper.error();
        }
    }

    /**
     * 白名单校验
     *
     * @param memberInfo 会员信息
     * @return boolean
     */
    private boolean verifyWhiteList(MemberInfo memberInfo) {
        String loginName = memberInfo.getLogin_name();
        String name = memberInfo.getName();
        if (StrUtil.isBlank(loginName) || StrUtil.isBlank(name)) {
            return false;
        } else {
            Map<String, Object> params = new HashMap<>();
            params.put("phone", loginName);
            params.put("user_name", name);
            String str = HttpUtil.get(environment.getProperty("member.white.host"), params);

            log.info("登录白名单：请求参数：{}，响应结果：{}", JSONUtil.toJsonStr(params), str);

            JSONObject jsonObject = JSONUtil.parseObj(str);
            Integer code = null;
            Boolean data = null;
            if (jsonObject.containsKey("code")) {
                code = jsonObject.getInt("code");
            }
            if (jsonObject.containsKey("data")) {
                data = jsonObject.getBool("data");
            }
            if (0 == code && data) {
                return true;
            } else {
                return false;
            }
        }
    }
}
