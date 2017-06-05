/*
 * WelcomeController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.BannerService;
import services.ChorbiService;
import domain.Banner;
import domain.Chorbi;

@Controller
@RequestMapping("/welcome")
public class WelcomeController extends AbstractController {
	
	// Services -------------------------------------------------------
	@Autowired
	private BannerService bannerService;
	@Autowired
	private ChorbiService chorbiService;

	// Constructors -----------------------------------------------------------

	public WelcomeController() {
		super();
	}

	// Index ------------------------------------------------------------------		

	@RequestMapping(value = "/index")
	public ModelAndView index(@RequestParam(required = false, defaultValue = "Muggle") final String name) {
		ModelAndView result;
		SimpleDateFormat formatter;
		String moment;
		String show;

		formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		moment = formatter.format(new Date());
		
		List<Banner> banners = new ArrayList<Banner>(bannerService.findBannersDisplay());
		if(!banners.isEmpty()){
			Collections.shuffle(banners);
			Banner banner = banners.get(0);
			show = banner.getLogo();
		}else{
			show= "";
		}
		
		Chorbi chorbi;
		try{
			chorbi = chorbiService.findByUserAccountId(LoginService.getPrincipal().getId());
		}catch(Throwable oops){
			chorbi=null;
		}
		
		result = new ModelAndView("welcome/index");
		result.addObject("name", name);
		result.addObject("moment", moment);
		result.addObject("banner", show);

		if(chorbi!=null && chorbi.getBanned()==true){
			result.addObject("message", "welcome.banned");
		}
		
		return result;
	}
}
