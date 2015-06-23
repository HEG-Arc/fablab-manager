package net.collaud.fablab.api.dao;

import java.util.List;
import java.util.Optional;
import net.collaud.fablab.api.data.GroupEO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * This is the DAO interface for a <tt>Group</tt>.
 *
 * @author Fabien Vuilleumier
 */
public interface GroupRepository extends JpaRepository<GroupEO, Integer> {

    @Override
    @Query("SELECT DISTINCT g "
            + " FROM GroupEO g "
            + " LEFT JOIN FETCH g.roles")
    List<GroupEO> findAll();

    @Query("SELECT g "
            + " FROM GroupEO g "
            + " LEFT JOIN FETCH g.roles "
            + " WHERE g.id=:id")
    Optional<GroupEO> findOneDetails(@Param("id") Integer id);
}
