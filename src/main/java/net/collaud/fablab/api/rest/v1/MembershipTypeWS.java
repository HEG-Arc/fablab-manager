package net.collaud.fablab.api.rest.v1;

import javax.annotation.PostConstruct;
import net.collaud.fablab.api.annotation.JavascriptAPIConstant;
import net.collaud.fablab.api.data.MembershipTypeEO;
import net.collaud.fablab.api.exceptions.FablabException;
import net.collaud.fablab.api.rest.v1.base.ReadWriteRestWebservice;
import net.collaud.fablab.api.rest.v1.base.SoftRemoveWebService;
import net.collaud.fablab.api.service.MembershipTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This is the WS class for a <tt>MembershipType</tt>.
 *
 * @author Fabien Vuilleumier
 */
@RestController()
@RequestMapping("/v1/membershipType")
@JavascriptAPIConstant("MEMBERSHIP_TYPE_API")
public class MembershipTypeWS extends ReadWriteRestWebservice<MembershipTypeEO, MembershipTypeService> implements SoftRemoveWebService {

    @Autowired
    private MembershipTypeService membershipTypeService;

    @PostConstruct
    public void postConstruct() {
        super.setService(membershipTypeService);
    }

    @Override
    public void softRemove(Integer id) throws FablabException {
        membershipTypeService.softRemove(id);
    }
}