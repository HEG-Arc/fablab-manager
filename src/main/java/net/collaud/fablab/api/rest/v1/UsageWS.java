package net.collaud.fablab.api.rest.v1;

import javax.annotation.PostConstruct;
import net.collaud.fablab.api.annotation.JavascriptAPIConstant;
import net.collaud.fablab.api.data.UsageEO;
import net.collaud.fablab.api.rest.v1.base.ReadWriteRestWebservice;
import net.collaud.fablab.api.exceptions.FablabException;
import net.collaud.fablab.api.service.UsageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *This is the WS class for a <tt>Usage</tt>.
* @author Fabien Vuilleumier
*/
@RestController()
@RequestMapping("/v1/usage")
@JavascriptAPIConstant("USAGE_API")
public class UsageWS extends ReadWriteRestWebservice<UsageEO, UsageService>{

    @Autowired
    private UsageService usageService;

    @PostConstruct
    public void postConstruct(){
        super.setService(usageService);
    }
    @Override
    public void softRemove(Integer id) throws FablabException {
        usageService.softRemove(id);
    }
}

