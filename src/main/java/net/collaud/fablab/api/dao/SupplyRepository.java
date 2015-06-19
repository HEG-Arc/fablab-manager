package net.collaud.fablab.api.dao;

import java.util.List;
import net.collaud.fablab.api.data.SupplyEO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
/**
 *This is the DAO interface for a <tt>Supply</tt>.
 * @author Fabien Vuilleumier
 */
public interface SupplyRepository extends JpaRepository<SupplyEO, Integer>{

    @Query("SELECT DISTINCT s "
        + " FROM SupplyEO s  " 
        + " LEFT JOIN FETCH s.supplyType "
        + " LEFT JOIN FETCH s.supplyUnity u ")
    @Override
    List<SupplyEO> findAll();
    
    @Query("SELECT s "
        + " FROM SupplyEO s "
        + " LEFT JOIN FETCH s.supplyType "
        + " LEFT JOIN FETCH s.supplyUnity "
        + " WHERE s.id=:id")
    Optional<SupplyEO> findOneDetails(@Param("id")Integer id);
    
    @Query("SELECT s "
        + " FROM SupplyEO s "
        + " LEFT JOIN FETCH s.supplyType "
        + " LEFT JOIN FETCH s.supplyUnity "
        + " WHERE s.quantityStock>0")
    List<SupplyEO> stock();
}
