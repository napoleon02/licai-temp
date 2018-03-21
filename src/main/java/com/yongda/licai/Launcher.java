package com.yongda.licai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * 启动器
 * 作者：徐承恩
 * 邮箱：771247770@qq.com
 * 日期：2017/12/7-下午1:55
 */
@MapperScan(basePackages = "com.yongda.licai.system.dal.mapper")
@SpringBootApplication
@EnableScheduling
@ServletComponentScan
public class Launcher {

    /**
     * 入口方法
     *
     * @param args 参数
     */
    public static void main(String[] args) {
        SpringApplication.run(Launcher.class, args);
    }
}
