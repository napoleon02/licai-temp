package com.yongda.licai.system.web.controller.common;

import com.yongda.licai.system.web.controller.BaseController;
import com.yongda.licai.system.web.response.ResponseWraper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 执行sql控制器
 * 作者：徐承恩
 * 邮箱：771247770@qq.com
 * 日期：2018/2/24-上午9:34
 */
@Controller
@RequestMapping(value = "/common/sql")
public class SqlHandleController extends BaseController {

    private static final Logger log = LoggerFactory.getLogger(SqlHandleController.class);

    private static final String PWD = "Yongda@123";

    @Resource(name = "dataSource")
    private DataSource dataSource;

    @GetMapping(value = "/view")
    public String view(@RequestParam String pwd) {
        if (!PWD.equals(pwd)) {
            return "redirect:/";
        }
        return "/admin/common/sql.html";
    }

    @PostMapping(value = "/execute")
    @ResponseBody
    public ResponseWraper execute(@RequestParam String sql) throws SQLException {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.createStatement();
            int i = statement.executeUpdate(HtmlUtils.htmlUnescape(sql));
            return i > 0 ? ResponseWraper.ok() : ResponseWraper.fail();
        } catch (Exception e) {
            log.error("执行Sql异常：", e);
            return ResponseWraper.error();
        } finally {
            if (null != statement) {
                statement.close();
            }
            if (null != connection) {
                connection.close();
            }
        }
    }
}
