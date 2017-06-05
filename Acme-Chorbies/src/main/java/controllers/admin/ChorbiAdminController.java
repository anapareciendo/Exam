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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ChorbiService;
import controllers.AbstractController;
import domain.Chorbi;

@Controller
@RequestMapping("/chorbi/admin")
public class ChorbiAdminController extends AbstractController {
	
	// Services -------------------------------------------------------
	@Autowired
	private ChorbiService chorbiService;

	// Constructors -----------------------------------------------------------

	public ChorbiAdminController() {
		super();
	}
	
	@RequestMapping(value="/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Chorbi> chorbis = chorbiService.findAll();
		
		result = new ModelAndView("chorbi/list");
		result.addObject("chorbi", chorbis);

		return result;
	}

	@RequestMapping(value="/banned", method = RequestMethod.GET)
	public ModelAndView listBanned(@RequestParam int chorbiId) {
		ModelAndView result;
		Chorbi chorbi = chorbiService.findOne(chorbiId);
		Collection<Chorbi> chorbis;
		result = new ModelAndView("chorbi/list");
		try{
			Chorbi res= chorbiService.ban(chorbi);
			chorbiService.save(res);
			chorbis = chorbiService.findAll();
		}catch(Throwable oops){
			chorbis = chorbiService.findAll();
			result.addObject("message", "chorbi.banned.error");
		}
		result.addObject("chorbi", chorbis);
		return result;
	}
	
	@RequestMapping(value="/unbanned", method = RequestMethod.GET)
	public ModelAndView listUnBanned(@RequestParam int chorbiId) {
		ModelAndView result;
		Chorbi chorbi = chorbiService.findOne(chorbiId);
		Collection<Chorbi> chorbis;
		result = new ModelAndView("chorbi/list");
		try{
			Chorbi res = chorbiService.unban(chorbi);
			chorbiService.save(res);
			chorbis = chorbiService.findAll();
		}catch(Throwable oops){
			chorbis = chorbiService.findAll();
			result.addObject("message", "chorbi.banned.error");
		}
		result.addObject("chorbi", chorbis);
		return result;
	}
	
}