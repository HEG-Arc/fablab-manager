package net.collaud.fablab.api.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import net.collaud.fablab.api.dao.PurchaseRepository;
import net.collaud.fablab.api.data.PurchaseEO;
import net.collaud.fablab.api.security.Roles;
import net.collaud.fablab.api.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
/**
 * This is the service implementation class for a <tt>Purchase</tt>.
 * @author Fabien Vuilleumier
 */
@Service
@Transactional
    @Secured({Roles.SUPPLY_VIEW})
public class PurchaseServiceImpl extends AbstractServiceImpl implements PurchaseService {

    @Autowired
    private PurchaseRepository purchaseDAO;

    @Override
    @Secured({Roles.SUPPLY_VIEW})
    public List<PurchaseEO> findAll() {
        return new ArrayList(new HashSet(purchaseDAO.findAll()));
    }

    @Override
    @Secured({Roles.SUPPLY_VIEW})
    public Optional<PurchaseEO> getById(Integer id) {
        return purchaseDAO.findOneDetails(id);
    }

     @Override
    @Secured({Roles.SUPPLY_VIEW})
    public PurchaseEO save(PurchaseEO purchase) {
        if (purchase.getId() == null) {
            purchase.setId(0);
        }
        if (purchase.getId() > 0) {
            PurchaseEO old = purchaseDAO.findOne(purchase.getId());
            old.setPurchaseDate(purchase.getPurchaseDate());
            old.setQuantity(purchase.getQuantity());
            old.setPurchasePrice(purchase.getPurchasePrice());
            old.setSupply(purchase.getSupply());
            old.setUser(purchase.getUser());
            old.setActive(purchase.isActive());
            return purchaseDAO.saveAndFlush(old);
        } else {
            return purchaseDAO.saveAndFlush(purchase);
        }
    }

    @Override
    @Secured({Roles.SUPPLY_VIEW})
    public void remove(Integer id) {
        purchaseDAO.delete(id);
    }

    @Override
    @Secured({Roles.SUPPLY_VIEW})
    public void softRemove(Integer id) {
        PurchaseEO current = purchaseDAO.findOne(id);
        current.setActive(false);
        purchaseDAO.saveAndFlush(current);
    }
}

