/*
 * CustomerController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.ChorbiService;
import services.CreditCardService;
import services.ManagerService;
import domain.Chorbi;
import domain.CreditCard;
import domain.Manager;
import domain.SuperUser;

@Controller
@RequestMapping("/creditCard")
public class CreditCardController extends AbstractController {
	
	// Services -------------------------------------------------------
	@Autowired
	private ChorbiService chorbiService;
	@Autowired
	private ManagerService managerService;
	@Autowired
	private CreditCardService creditService;

	// Constructors -----------------------------------------------------------

	public CreditCardController() {
		super();
	}

	// Actions ---------------------------------------------------------------		
	@RequestMapping(value = "/creditCard", method = RequestMethod.GET)
	public ModelAndView creditCard() {
			ModelAndView result;
			int uaId=LoginService.getPrincipal().getId();
			SuperUser su= chorbiService.findByUserAccountId(uaId);
			if(su==null){
				su=managerService.findByUserAccountId(uaId);
			}
			CreditCard res = su.getCreditCard();
			if(res==null){
				res=creditService.create(su);
			}
			
			result = new ModelAndView("creditCard/creditCard");
			result.addObject("card", res);
			return result;
	}
	
	@RequestMapping(value = "/creditCard", method = RequestMethod.POST, params = "save")
	public ModelAndView creditCard(CreditCard card, BindingResult binding) {
		ModelAndView result;
		try{
			CreditCard creditCard = creditService.reconstruct(card, binding);
			try{
				CreditCard res=creditService.save(creditCard);
				int id = LoginService.getPrincipal().getId();
				SuperUser owner = chorbiService.findByUserAccountId(id);
				if(owner!=null){
					Chorbi c = chorbiService.findOne(owner.getId());
					c.setCreditCard(res);
					chorbiService.save(c);
				}else{
					Manager m = managerService.findByUserAccountId(id);
					m.setCreditCard(res);
					managerService.save(m);
				}
				result = new ModelAndView("creditCard/display");
				result.addObject("card", res);
				result.addObject("message", "creditcard.success");
			}catch(Throwable oops){
				result = new ModelAndView("creditCard/creditCard");
				result.addObject("card", card);
				result.addObject("message", "creditcard.incomplete");
			}
		}catch(Throwable opps){
			result = new ModelAndView("creditCard/creditCard");
			result.addObject("card", card);
			result.addObject("message", "creditcard.incomplete");
		}
		return result;
	}
}
