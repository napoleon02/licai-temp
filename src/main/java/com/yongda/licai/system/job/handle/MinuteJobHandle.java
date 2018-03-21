package com.yongda.licai.system.job.handle;

import java.util.Date;

/**
 * 按每分钟频率执行处理器接口
 * 作者：徐承恩
 * 邮箱：771247770@qq.com
 * 日期：2018/2/3-下午2:55
 */
public interface MinuteJobHandle {

    /**
     * 执行
     *
     * @param date 当前时间
     */
    void execute(Date date);

}
