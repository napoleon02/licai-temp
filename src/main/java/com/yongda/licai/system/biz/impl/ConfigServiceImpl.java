package com.yongda.licai.system.biz.impl;

import com.xiaoleilu.hutool.collection.CollUtil;
import com.yongda.licai.enums.YesOrNoEnum;
import com.yongda.licai.system.biz.ConfigService;
import com.yongda.licai.system.dal.mapper.ConfigDOMapper;
import com.yongda.licai.system.dal.model.ConfigDO;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 系统配置接口实现
 * 作者：徐承恩
 * 邮箱：771247770@qq.com
 * 日期：2017/12/13-下午2:02
 */
@Service(value = "configService")
public class ConfigServiceImpl implements ConfigService {

    @Resource(name = "configDOMapper")
    private ConfigDOMapper configDOMapper;

    @Cacheable(value = "CONFIG_CACHE")
    @Override
    public String getByKey(String key) {
        ConfigDO configDO = new ConfigDO();
        configDO.setConfigKey(key);
        ConfigDO result = configDOMapper.selectOne(configDO);
        return null == result ? null : result.getConfigValue();
    }

    @Cacheable(value = "CONFIG_CACHE", unless = "#result==null")
    @Override
    public List<ConfigDO> getAll() {
        Example example = new Example(ConfigDO.class);
        example.orderBy("createTime").asc();
        return configDOMapper.selectByExample(example);
    }

    @CacheEvict(value = "CONFIG_CACHE", allEntries = true)
    @Override
    public void add(ConfigDO configDO) {
        configDO.setId(null);
        configDO.setIsDefault(YesOrNoEnum.NO.getCode());
        configDO.setCreateTime(new Date());
        configDO.setUpdateTime(new Date());
        configDOMapper.insertSelective(configDO);
    }

    @CacheEvict(value = "CONFIG_CACHE", allEntries = true)
    @Override
    public void edit(ConfigDO configDO) {
        configDO.setUpdateTime(new Date());
        configDOMapper.updateByPrimaryKeySelective(configDO);
    }

    @Override
    public ConfigDO getByKey(String key, List<String> ignoreIds) {
        if (CollUtil.isEmpty(ignoreIds)) {
            ConfigDO configDO = new ConfigDO();
            configDO.setConfigKey(key);
            return configDOMapper.selectOne(configDO);
        } else {
            Example example = new Example(ConfigDO.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("configKey", key);
            criteria.andNotIn("id", ignoreIds);
            return configDOMapper.selectOneByExample(example);
        }
    }

    @Override
    public ConfigDO getById(String id) {
        return configDOMapper.selectByPrimaryKey(id);
    }

    @CacheEvict(value = "CONFIG_CACHE", allEntries = true)
    @Override
    public void deleteById(String id) {
        configDOMapper.deleteByPrimaryKey(id);
    }

}
