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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import forms.Dashboard;

@Controller
@RequestMapping("/dashboard")
public class DashboardController extends AbstractController {
	
	// Services -------------------------------------------------------
	@Autowired
	private AdministratorService adminServices;
	
	// Constructors -----------------------------------------------------------

	public DashboardController() {
		super();
	}

	@RequestMapping(value="/dashboard", method = RequestMethod.GET)
	public ModelAndView search() {
		ModelAndView result;
		
		Dashboard res= adminServices.dashboard();
		result = new ModelAndView("administrator/dashboard");
		result.addObject("dashboard", res);
		
		return result;
		
	}
	
}