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
import services.BroadcastService;
import services.ManagerService;
import domain.Broadcast;
import domain.Manager;

@Controller
@RequestMapping("/managerr")
public class ManagerController extends AbstractController {
	
	// Services -------------------------------------------------------
	@Autowired
	private ManagerService managerService;
	@Autowired
	private BroadcastService broadcastService;
	
	// Constructors -----------------------------------------------------------

	public ManagerController() {
		super();
	}

	// Actions
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display() {
		ModelAndView result;
		Manager manager = managerService.findByUserAccountId(LoginService.getPrincipal().getId());
		result = new ModelAndView("manager/display");
		result.addObject("manager", manager);
		return result;
	}
	
	@RequestMapping(value = "/pay", method = RequestMethod.GET)
	public ModelAndView pay() {
		ModelAndView result;
		Manager manager = managerService.findByUserAccountId(LoginService.getPrincipal().getId());
		try{
			manager.setAmount(0);
			Manager res= managerService.save(manager);
			result = new ModelAndView("manager/display");
			result.addObject("manager", res);
			result.addObject("message", "manager.pay.success");
		}catch(Throwable oops){
			result = new ModelAndView("manager/display");
			result.addObject("manager", manager);
			result.addObject("message", "manager.pay.error");
		}
		return result;
	}
	
	@RequestMapping(value="/broadcast", method = RequestMethod.GET)
	public ModelAndView broadcast() {
		ModelAndView result;
		Broadcast bm = managerService.findByUserAccountId(LoginService.getPrincipal().getId()).getBroadcast();
		if(bm==null){
			bm = broadcastService.create();
		}
		
		result = new ModelAndView("broadcast/broadcast");
		result.addObject("broadcast", bm);

		return result;
	}
	
	@RequestMapping(value="/broadcast", method = RequestMethod.POST, params="send")
	public ModelAndView broadcast(Broadcast broadcast, BindingResult binding) {
		ModelAndView result;

		try{
			Broadcast res=broadcastService.save(broadcast);
			broadcastService.reNew(res);
			result = new ModelAndView("broadcast/broadcast");
			result.addObject("broadcast", res);
			result.addObject("message", "broadcast.success");
			
		}catch(Throwable oops){
			result = new ModelAndView("broadcast/broadcast");
			result.addObject("broadcast", broadcast);
			result.addObject("message", "broadcast.error");
		}
		return result;
	}
}
