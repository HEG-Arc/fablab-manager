package net.collaud.fablab.api.rest.v1;

import javax.annotation.PostConstruct;
import net.collaud.fablab.api.annotation.JavascriptAPIConstant;
import net.collaud.fablab.api.data.RevisionEO;
import net.collaud.fablab.api.exceptions.FablabException;
import net.collaud.fablab.api.rest.v1.base.ReadWriteRestWebservice;
import net.collaud.fablab.api.rest.v1.base.SoftRemoveWebService;
import net.collaud.fablab.api.service.RevisionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This is the WS class for a <tt>Revision</tt>.
 *
 * @author Fabien Vuilleumier
 */
@RestController()
@RequestMapping("/v1/revision")
@JavascriptAPIConstant("REVISION_API")
public class RevisionWS extends ReadWriteRestWebservice<RevisionEO, RevisionService> implements SoftRemoveWebService {

    @Autowired
    private RevisionService revisionService;

    @PostConstruct
    public void postConstruct() {
        super.setService(revisionService);
    }

    @Override
    public void softRemove(Integer id) throws FablabException {
        revisionService.softRemove(id);
    }
}