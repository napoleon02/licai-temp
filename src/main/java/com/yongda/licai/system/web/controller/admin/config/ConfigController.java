package com.yongda.licai.system.web.controller.admin.config;

import com.xiaoleilu.hutool.util.StrUtil;
import com.yongda.licai.system.biz.ConfigService;
import com.yongda.licai.system.dal.model.ConfigDO;
import com.yongda.licai.system.web.controller.BaseController;
import com.yongda.licai.system.web.response.ResponseWraper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * 系统配置控制器
 * 作者：徐承恩
 * 邮箱：771247770@qq.com
 * 日期：2018/1/26-上午11:01
 */
@Controller
@RequestMapping(value = "/admin/config")
public class ConfigController extends BaseController {

    private static final Logger log = LoggerFactory.getLogger(ConfigController.class);

    @Resource(name = "configService")
    private ConfigService configService;

    /**
     * 查询系统配置页面
     */
    @GetMapping(value = "/queryConfig")
    public String queryConfig() {
        return "/admin/config/queryConfig.html";
    }

    /**
     * 查询逻辑
     */
    @PostMapping(value = "/queryConfig")
    @ResponseBody
    public ResponseWraper doQueryConfig() {
        try {
            List<ConfigDO> list = configService.getAll();
            return ResponseWraper.ok().setData(list);
        } catch (Exception e) {
            log.error("查询系统配置异常：", e);
            return ResponseWraper.error();
        }
    }

    /**
     * 添加配置
     */
    @GetMapping(value = "/add")
    public String add(ModelMap modelMap) {
        modelMap.put("isEdit", false);
        return "/admin/config/add.html";
    }

    /**
     * 添加逻辑
     */
    @PostMapping(value = "/add")
    @ResponseBody
    public ResponseWraper doAdd(ConfigDO configDO) {
        try {
            String configKey = configDO.getConfigKey();
            String val = configService.getByKey(configKey);
            if (StrUtil.isBlank(val)) {
                configService.add(configDO);
            } else {
                return ResponseWraper.other("配置标识重复");
            }
            return ResponseWraper.ok();
        } catch (Exception e) {
            log.error("添加系统配置异常：", e);
            return ResponseWraper.error();
        }
    }

    /**
     * 修改配置
     */
    @GetMapping(value = "/edit")
    public String edit(@RequestParam String id, ModelMap modelMap) {
        ConfigDO config = configService.getById(id);
        modelMap.put("config", config);
        modelMap.put("isEdit", true);
        return "/admin/config/add.html";
    }

    /**
     * 修改逻辑
     */
    @PostMapping(value = "/edit")
    @ResponseBody
    public ResponseWraper doEdit(ConfigDO configDO) {
        try {
            ConfigDO config = configService
                    .getByKey(configDO.getConfigKey(), Collections.singletonList(configDO.getId()));
            if (null == config) {
                configService.edit(configDO);
            } else {
                return ResponseWraper.other("配置标识重复");
            }
            return ResponseWraper.ok();
        } catch (Exception e) {
            log.error("修改系统配置异常：", e);
            return ResponseWraper.error();
        }
    }

    /**
     * 删除配置
     */
    @PostMapping(value = "/delete")
    @ResponseBody
    public ResponseWraper delete(@RequestParam String id) {
        try {
            configService.deleteById(id);
            return ResponseWraper.ok();
        } catch (Exception e) {
            log.error("删除系统配置异常：", e);
            return ResponseWraper.error();
        }
    }
}
