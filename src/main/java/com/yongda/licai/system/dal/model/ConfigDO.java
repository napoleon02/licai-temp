package com.yongda.licai.system.dal.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "T_CONFIG")
public class ConfigDO implements Serializable {

    private static final long serialVersionUID = -6570920223362334787L;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select sys_guid() from dual")
    private String id;

    @Column(name = "CONFIG_KEY")
    private String configKey;

    @Column(name = "CONFIG_VALUE")
    private String configValue;

    @Column(name = "CONFIG_NAME")
    private String configName;

    @Column(name = "MEMO")
    private String memo;

    @Column(name = "IS_DEFAULT")
    private String isDefault;

    @Column(name = "CREATE_TIME")
    private Date createTime;

    @Column(name = "UPDATE_TIME")
    private Date updateTime;

    /**
     * @return ID
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * @return CONFIG_KEY
     */
    public String getConfigKey() {
        return configKey;
    }

    /**
     * @param configKey
     */
    public void setConfigKey(String configKey) {
        this.configKey = configKey == null ? null : configKey.trim();
    }

    /**
     * @return CONFIG_VALUE
     */
    public String getConfigValue() {
        return configValue;
    }

    /**
     * @param configValue
     */
    public void setConfigValue(String configValue) {
        this.configValue = configValue == null ? null : configValue.trim();
    }

    /**
     * @return CONFIG_NAME
     */
    public String getConfigName() {
        return configName;
    }

    /**
     * @param configName
     */
    public void setConfigName(String configName) {
        this.configName = configName == null ? null : configName.trim();
    }

    /**
     * @return MEMO
     */
    public String getMemo() {
        return memo;
    }

    /**
     * @param memo
     */
    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
    }

    /**
     * @return IS_DEFAULT
     */
    public String getIsDefault() {
        return isDefault;
    }

    /**
     * @param isDefault
     */
    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault == null ? null : isDefault.trim();
    }

    /**
     * @return CREATE_TIME
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return UPDATE_TIME
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}