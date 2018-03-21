package com.yongda.licai.system.job;

import com.xiaoleilu.hutool.collection.CollUtil;
import com.yongda.licai.system.job.handle.MinuteJobHandle;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 按每分钟频率执行一次的任务
 * 作者：徐承恩
 * 邮箱：771247770@qq.com
 * 日期：2018/2/3-下午2:52
 */
@Component(value = "minuteJob")
public class MinuteJob {

    @Resource
    private List<MinuteJobHandle> minuteJobHandles;

    @Scheduled(cron = "0 0/1 * * * ?")
    public void execute() {
        Date date = new Date();
        if (CollUtil.isNotEmpty(minuteJobHandles)) {
            for (MinuteJobHandle minuteJobHandle : minuteJobHandles) {
                minuteJobHandle.execute(date);
            }
        }
    }

}
