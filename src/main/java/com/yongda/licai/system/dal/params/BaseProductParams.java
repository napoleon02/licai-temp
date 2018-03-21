package com.yongda.licai.system.dal.params;

import com.yongda.licai.system.dal.model.ProductDO;

import java.io.Serializable;

public class BaseProductParams extends ProductDO implements Serializable {

    /**
     * 接受前台查询时间字符串
     */
    private static final long serialVersionUID = 1L;

    private String createTimeUp;

    private String createTimeDown;

    private String NewUpTime;

    private String OutUpTime;

    public String getCreateTimeUp() {
        return createTimeUp;
    }

    public void setCreateTimeUp(String createTimeUp) {
        this.createTimeUp = createTimeUp;
    }

    public String getCreateTimeDown() {
        return createTimeDown;
    }

    public void setCreateTimeDown(String createTimeDown) {
        this.createTimeDown = createTimeDown;
    }

    public String getNewUpTime() {
        return NewUpTime;
    }

    public void setNewUpTime(String newUpTime) {
        NewUpTime = newUpTime;
    }

    public String getOutUpTime() {
        return OutUpTime;
    }

    public void setOutUpTime(String outUpTime) {
        OutUpTime = outUpTime;
    }


}
