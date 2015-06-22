package net.collaud.fablab.api.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import net.collaud.fablab.api.dao.MachineTypeRepository;
import net.collaud.fablab.api.data.MachineTypeEO;
import net.collaud.fablab.api.data.PriceMachineEO;
import net.collaud.fablab.api.security.Roles;
import net.collaud.fablab.api.service.MachineTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Fabien Vuilleumier
 */
@Service
@Transactional
@Secured({Roles.MACHINE_MANAGE})
public class MachineTypeServiceImpl implements MachineTypeService {

    @Autowired
    private MachineTypeRepository machineTypeDao;

    @Override
    @Secured({Roles.MACHINE_VIEW})
    public List<MachineTypeEO> findAll() {
        return new ArrayList(new HashSet(machineTypeDao.findAll()));
    }

    @Override
    @Secured({Roles.MACHINE_VIEW})
    public Optional<MachineTypeEO> getById(Integer id) {
        return Optional.ofNullable(machineTypeDao.findOne(id));
    }

    @Override
    @Secured({Roles.MACHINE_MANAGE})
    public MachineTypeEO save(MachineTypeEO machineType) {
        if (machineType.getId() == null) {
            machineType.setId(0);
        }
        if (machineType.getId() > 0) {
            MachineTypeEO old = machineTypeDao.findOne(machineType.getId());
            old.setName(machineType.getName());
            old.setTechnicalname(machineType.getName());
            old.setRestricted(machineType.isRestricted());
            return machineTypeDao.saveAndFlush(old);
        } else {
            return machineTypeDao.saveAndFlush(machineType);
        }
    }

    @Override
    @Secured({Roles.MACHINE_MANAGE})
    public void remove(Integer id) {
        machineTypeDao.delete(id);
    }

    @Override
    @Secured({Roles.MACHINE_MANAGE})
    public void softRemove(Integer id) {
        MachineTypeEO current = machineTypeDao.findOne(id);
        current.setActive(false);
        machineTypeDao.saveAndFlush(current);
    }

    @Override
    public List<PriceMachineEO> getPrices(Integer id) {
       return machineTypeDao.getPrices(id);
    }

    @Override
    public MachineTypeEO getId(String technicalname) {
        return machineTypeDao.getId(technicalname);
    }
}
