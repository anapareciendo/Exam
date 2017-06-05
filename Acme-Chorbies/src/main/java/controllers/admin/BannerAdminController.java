/*
 * CustomerController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers.admin;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.BannerService;
import controllers.AbstractController;
import domain.Banner;

@Controller
@RequestMapping("/banner/admin")
public class BannerAdminController extends AbstractController {
	
	// Services -------------------------------------------------------
	@Autowired
	private BannerService bannerService;

	// Constructors -----------------------------------------------------------

	public BannerAdminController() {
		super();
	}
	
	@RequestMapping(value="/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Banner> banners = bannerService.findAll();
		
		result = new ModelAndView("banner/list");
		result.addObject("requestUri", "/banner/admin/list.do");
		result.addObject("banner",banners);

		return result;
	}

	@RequestMapping(value="/hide", method = RequestMethod.GET)
	public ModelAndView hide(@RequestParam int bannerId) {
		ModelAndView result;
		Banner banner = bannerService.findOne(bannerId);
		Collection<Banner> banners;
		
		try{
			Banner res = bannerService.hide(banner);
			bannerService.save(res);
			banners = bannerService.findAll();
			result = new ModelAndView("redirect:list.do");
			
		}catch(Throwable oops){
			banners = bannerService.findAll();
			result = new ModelAndView("banner/list");
			result.addObject("requestUri", "/banner/admin/list.do");
			result.addObject("banner", banners);
			result.addObject("message", "banner.commit.error");
		}

		
		return result;
	}
	
	@RequestMapping(value="/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam int bannerId) {
		ModelAndView result;
		Banner banner = bannerService.findOne(bannerId);
		Collection<Banner> banners;
		
		try{
			Banner res = bannerService.show(banner);
			bannerService.save(res);
			banners = bannerService.findAll();
			result =new ModelAndView("redirect:list.do");
			
		}catch(Throwable oops){
			banners = bannerService.findAll();
			result = new ModelAndView("banner/list");
			result.addObject("requestUri", "/banner/admin/list.do");
			result.addObject("banner", banners);
			result.addObject("message", "banner.commit.error");
		}

		
		return result;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int bannerId) {
		ModelAndView result;
		
		try{
			Banner banner = bannerService.findOne(bannerId);
			result = new ModelAndView("banner/edit");
			result.addObject("banner", banner);
		}catch(Throwable oops){
			result = new ModelAndView("welcome/index");
			result.addObject("message","banner.commit.error");

		}
		return result;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Banner banner, BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = new ModelAndView("banner/edit");
			result.addObject("banner", banner);
		} else {
			try {
				bannerService.save(banner);				
				result = new ModelAndView("banner/list");
				Collection<Banner> banners = bannerService.findAll();
				result.addObject("banner", banners);
				result.addObject("message","banner.commit.ok");
			} catch (Throwable oops) {
				result = new ModelAndView("banner/edit");
				result.addObject("banner", banner);		
				result.addObject("message", "banner.commit.error");
			}
		}

		return result;
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		
		try{
			Banner banner = bannerService.create();
			result = new ModelAndView("banner/edit");
			result.addObject("banner", banner);
		}catch(Throwable oops){
			result = new ModelAndView("welcome/index");
			result.addObject("message","banner.commit.error");

		}
		return result;
	}
	
}