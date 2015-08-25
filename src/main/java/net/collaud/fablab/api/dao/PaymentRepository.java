package net.collaud.fablab.api.dao;

import java.util.Date;
import java.util.List;
import net.collaud.fablab.api.data.UserPaymentEO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Gaetan Collaud <gaetancollaud@gmail.com>
 */
@Repository
public interface PaymentRepository extends JpaRepository<UserPaymentEO, Integer> {

    @Query(" SELECT p "
            + " FROM UserPaymentEO p "
            + " WHERE p.datePayment>=:dateAfter AND p.datePayment <=:dateBefore")
    public List<UserPaymentEO> getAllBetween(@Param("dateAfter") Date dateAfter, @Param("dateBefore") Date dateBefore);

    @Query(" SELECT p "
            + " FROM  UserPaymentEO p "
            + " WHERE p.user.id=:userId")
    public List<UserPaymentEO> getByUser(@Param("userId") Integer userId);

}
