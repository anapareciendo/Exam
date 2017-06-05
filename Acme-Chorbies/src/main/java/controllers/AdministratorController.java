/*
 * AdministratorController.java
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

import services.ConfigService;
import services.MonthlyFeeService;
import domain.Config;

@Controller
@RequestMapping("/administrator")
public class AdministratorController extends AbstractController {

	//Services
	@Autowired
	private ConfigService configService;
	@Autowired
	private MonthlyFeeService monthlyFeeService;
	
	// Constructors -----------------------------------------------------------

	public AdministratorController() {
		super();
	}
	
	//Config
	@RequestMapping(value = "/config", method = RequestMethod.GET)
	public ModelAndView config() {
		ModelAndView result;
		
		Config config = configService.find();
		
		result = new ModelAndView("administrator/config");
		result.addObject("config", config);

		return result;
	}
	
	@RequestMapping(value = "/config", method = RequestMethod.POST, params = "save")
	public ModelAndView replyChirp(Config config, BindingResult binding) {
		ModelAndView result;
		
		Config res = configService.reconstruct(config, binding);
		
		if(!binding.hasErrors()){
			try{
				configService.save(res);
				
				result = new ModelAndView("administrator/config");
				result.addObject("config", res);
				result.addObject("message", "admin.commit.success");
			}catch(Throwable oops){
				result = new ModelAndView("administrator/config");
				result.addObject("config", res);
				result.addObject("message", "admin.commit.error");
			}
		}else{
			result = new ModelAndView("administrator/config");
			result.addObject("config", res);
			result.addObject("message", "admin.commit.error");
		}
		
		return result;
	}
	
	@RequestMapping(value = "/monthlyFee", method = RequestMethod.GET)
	public ModelAndView monthlyFee() {
		ModelAndView result;
		
		Boolean fee=monthlyFeeService.generateFees();
		
		Config config = configService.find();
		result = new ModelAndView("administrator/config");
		result.addObject("config", config);
		if(fee){
			result.addObject("message", "admin.monthly.fee.success");
		}else{
			result.addObject("message", "admin.monthly.fee.void");
		}

		return result;
	}


}
