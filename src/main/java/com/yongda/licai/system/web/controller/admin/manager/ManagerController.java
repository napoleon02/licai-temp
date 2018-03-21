package com.yongda.licai.system.web.controller.admin.manager;

import com.github.pagehelper.ISelect;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xiaoleilu.hutool.util.StrUtil;
import com.yongda.licai.enums.YesOrNoEnum;
import com.yongda.licai.system.biz.AdminManagerService;
import com.yongda.licai.system.dal.mapper.AdminDOMapper;
import com.yongda.licai.system.dal.model.AdminDO;
import com.yongda.licai.system.web.controller.BaseController;
import com.yongda.licai.system.web.response.ResponseWraper;
import com.yongda.licai.utils.PwdUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;


/**
 * 管理员控制器
 * 作者：徐承恩
 * 邮箱：771247770@qq.com
 * 日期：2018/1/29-上午9:35
 */
@Controller
@RequestMapping(value = "/admin/manager")
public class ManagerController extends BaseController {

    public static final Logger log = LoggerFactory.getLogger(ManagerController.class);

    @Resource(name = "adminDOMapper")
    private AdminDOMapper adminDOMapper;

    @Resource(name = "adminManagerService")
    private AdminManagerService adminManagerService;

    /**
     * 查询管理员页面
     */
    @GetMapping(value = "/queryManager")
    public String queryManager() {
        return "/admin/manager/queryManager.html";
    }

    /**
     * 查询管理员逻辑
     */
    @PostMapping(value = "/queryManager")
    @ResponseBody
    public ResponseWraper doQueryManager(String name,
                                         String account,
                                         @RequestParam(value = "page", defaultValue = "1") Integer page,
                                         @RequestParam(value = "rows", defaultValue = "20") Integer rows) {
        try {
            Page<AdminDO> result = PageHelper.startPage(page, rows, "create_time DESC").doSelectPage(new ISelect() {
                @Override
                public void doSelect() {
                    Example example = new Example(AdminDO.class);
                    Example.Criteria criteria = example.createCriteria();
                    if (StrUtil.isNotBlank(name)) {
                        criteria.andLike("name", name);
                    }

                    if (StrUtil.isNotBlank(account)) {
                        criteria.andEqualTo("account", account);
                    }

                    adminDOMapper.selectByExample(example);
                }
            });
            Map<String, Object> data = new HashMap<>();
            data.put("rows", result.getResult());
            data.put("total", result.getTotal());
            return ResponseWraper.ok().setData(data);
        } catch (Exception e) {
            log.error("查询管理员列表异常：", e);
            return ResponseWraper.error();
        }
    }

    /**
     * 添加页面
     */
    @GetMapping(value = "/add")
    public String add(ModelMap modelMap) {
        modelMap.put("isEdit", false);
        return "/admin/manager/add.html";
    }

    /**
     * 添加逻辑
     */
    @PostMapping(value = "/add")
    @ResponseBody
    public ResponseWraper doAdd(AdminDO adminDO) {
        try {
            String account = adminDO.getAccount();
            AdminDO _admin = adminManagerService.getByAccount(account);
            if (null == _admin) {
                adminDO.setPassword("88888888");
                adminManagerService.create(adminDO);
            } else {
                return ResponseWraper.other("帐号已经存在");
            }
        } catch (Exception e) {
            log.error("添加管理员异常：", e);
        }
        return ResponseWraper.ok();
    }

    /**
     * 禁用/启用
     */
    @PostMapping(value = "/disable")
    @ResponseBody
    public ResponseWraper disable(@RequestParam String id,
                                  @RequestParam String flag) {
        try {
            String _id = getAdmin().getId();
            if (id.equals(_id)) {
                return ResponseWraper.other("不允许禁用当前登录帐号");
            }
            if (YesOrNoEnum.YES.getCode().equalsIgnoreCase(flag)) {
                adminManagerService.disable(id, YesOrNoEnum.YES.getCode());
            } else if (YesOrNoEnum.NO.getCode().equalsIgnoreCase(flag)) {
                adminManagerService.disable(id, YesOrNoEnum.NO.getCode());
            } else {
                return ResponseWraper.other("参数错误");
            }
            return ResponseWraper.ok();
        } catch (Exception e) {
            log.error("禁用管理员异常：", e);
            return ResponseWraper.error();
        }
    }

    /**
     * 重置密码
     */
    @PostMapping(value = "/resetPwd")
    @ResponseBody
    public ResponseWraper resetPwd(@RequestParam String id) {
        try {
            AdminDO adminDO = new AdminDO();
            adminDO.setId(id);
            adminDO.setPassword("88888888");
            adminManagerService.resetPwd(adminDO);
            return ResponseWraper.ok();
        } catch (Exception e) {
            log.error("重置管理员密码异常：", e);
            return ResponseWraper.error();
        }
    }

    /**
     * 修改密码页面
     */
    @GetMapping(value = "/editPwd")
    public String editPwd() {
        return "/admin/manager/editPwd.html";
    }

    /**
     * 处理修改密码
     */
    @PostMapping(value = "/editPwd")
    @ResponseBody
    public ResponseWraper doEditPwd(@RequestParam String oldPwd,
                                    @RequestParam String newPwd,
                                    @RequestParam String confirmPwd) {
        try {
            String id = getAdmin().getId();
            AdminDO adminDO = adminDOMapper.selectByPrimaryKey(id);
            oldPwd = PwdUtils.buildPwd(oldPwd, adminDO.getSalt());
            if (!confirmPwd.equals(newPwd)) {
                return ResponseWraper.other("新密码两次输入不一致");
            }
            if (!oldPwd.equalsIgnoreCase(adminDO.getPassword())) {
                return ResponseWraper.other("原密码不正确");
            }
            AdminDO _admin = new AdminDO();
            _admin.setId(id);
            _admin.setPassword(newPwd);
            adminManagerService.resetPwd(_admin);
            return ResponseWraper.ok();
        } catch (Exception e) {
            log.error("修改密码异常：", e);
            return ResponseWraper.error();
        }
    }
}
