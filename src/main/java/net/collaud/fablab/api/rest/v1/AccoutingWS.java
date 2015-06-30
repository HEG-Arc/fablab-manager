package net.collaud.fablab.api.rest.v1;

import net.collaud.fablab.api.annotation.JavascriptAPIConstant;
import net.collaud.fablab.api.rest.v1.criteria.PeriodSearchCriteria;
import net.collaud.fablab.api.rest.v1.model.BaseModel;
import net.collaud.fablab.api.rest.v1.model.DataModel;
import net.collaud.fablab.api.service.UserPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Gaetan Collaud <gaetancollaud@gmail.com> Collaud <gaetancollaud@gmail.com>
 */
@RestController()
@RequestMapping("/v1/accounting")
@JavascriptAPIConstant("ACCOUNTING_API")
public class AccoutingWS {

	@Autowired
	private UserPaymentService paymentService;

	@RequestMapping(value = "search", method = RequestMethod.POST)
	public BaseModel search(@Validated @RequestBody PeriodSearchCriteria search) {
		return new DataModel(paymentService.getPaymentEntries(search));
	}

}
