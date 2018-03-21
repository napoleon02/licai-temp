package com.yongda.licai.system.biz;

import com.yongda.licai.system.dal.model.ConfigDO;

import java.util.List;

/**
 * 配置业务接口
 * 作者：徐承恩
 * 邮箱：771247770@qq.com
 * 日期：2017/12/13-下午2:00
 */
public interface ConfigService {

    /**
     * 根据key获取配置值
     *
     * @param key 键名
     * @return 值
     */
    String getByKey(String key);

    /**
     * 获取所有配置
     *
     * @return List
     */
    List<ConfigDO> getAll();

    /**
     * 添加配置
     *
     * @param configDO 实体
     */
    void add(ConfigDO configDO);

    /**
     * 修改配置
     *
     * @param configDO 实体
     */
    void edit(ConfigDO configDO);

    /**
     * 根据key获取配置信息
     *
     * @param key       键名
     * @param ignoreIds 忽略的ID列表
     * @return 实体
     */
    ConfigDO getByKey(String key, List<String> ignoreIds);

    /**
     * 根据ID获取配置信息
     *
     * @param id 主键ID
     * @return 实体
     */
    ConfigDO getById(String id);

    /**
     * 根据主键ID删除
     *
     * @param id 主键ID
     */
    void deleteById(String id);

}
