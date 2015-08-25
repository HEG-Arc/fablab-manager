package net.collaud.fablab.api.service.impl;

import java.util.List;
import net.collaud.fablab.api.service.*;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import net.collaud.fablab.api.dao.ConfigurationRepository;
import net.collaud.fablab.api.data.ConfigurationEO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Gaetan Collaud <gaetancollaud@gmail.com>
 */
@Service
@Transactional
@Slf4j
public class ConfigurationServiceImpl implements ConfigurationService {

    @Autowired
    private ConfigurationRepository configurationDao;

    @Override
    public List<ConfigurationEO> findAll() {
        return configurationDao.findAll();
    }

    @Override
    public Optional<ConfigurationEO> getById(Integer id) {
        return Optional.ofNullable(configurationDao.findOne(id));
    }

    @Override
    public ConfigurationEO save(ConfigurationEO conf) {
        if (conf.getId() == null) {
            conf.setId(0);
        }
        if (conf.getId() > 0) {
            ConfigurationEO old = configurationDao.findOne(conf.getId());
            old.setValue(conf.getValue());
            return configurationDao.saveAndFlush(old);
        } else {
            return configurationDao.saveAndFlush(conf);
        }
    }

    @Override
    public void remove(Integer id) {
        configurationDao.delete(id);
    }

    @Override
    public void softRemove(Integer id) {
        //not implemented for a configuration
    }

    @Override
    public ConfigurationEO findByKey(String key) {
        return configurationDao.findByKey(key);
    }

}
