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

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.ChorbiService;
import services.CreditCardService;
import domain.Chorbi;
import domain.CreditCard;
import forms.ChorbiForm;

@Controller
@RequestMapping("/chorbi")
public class ChorbiController extends AbstractController {
	
	// Services -------------------------------------------------------
	@Autowired
	private ChorbiService chorbiService;
	@Autowired
	private CreditCardService creditService;

	// Constructors -----------------------------------------------------------

	public ChorbiController() {
		super();
	}

	// Action-1 ---------------------------------------------------------------		

	@RequestMapping(value="/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Chorbi> chorbis = chorbiService.findNotBanned();
		
		result = new ModelAndView("chorbi/list");
		result.addObject("chorbi", chorbis);

		return result;
	}
	
	@RequestMapping(value="/listMyLikes", method = RequestMethod.GET)
	public ModelAndView listMyLikes(@RequestParam int chorbiId) {
		ModelAndView result;
		Collection<Chorbi> chorbis = chorbiService.findMyLikes(chorbiId);
		
		result = new ModelAndView("chorbi/list");
		result.addObject("chorbi", chorbis);

		return result;
	}
	
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display() {
		ModelAndView result;
		Chorbi chorbi;
		try{
		Integer chorbiId= LoginService.getPrincipal().getId();
		chorbi = chorbiService.findByUserAccountId(chorbiId);
		result = new ModelAndView("chorbi/display");
		result.addObject("chorbi", chorbi);
		}catch(Throwable oops){
			
			result= new ModelAndView("hacker/hackers");

		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
			ModelAndView result;
			
			Chorbi chorbi= chorbiService.findByUserAccountId(LoginService.getPrincipal().getId());
			

			result = new ModelAndView("chorbi/edit");
			result.addObject("chorbi", chorbi);
			return result;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(ChorbiForm chor, BindingResult binding) {
		ModelAndView result;
		Chorbi chorbi;
		chorbi= chorbiService.reconstructEdit(chor, binding);

		if (!binding.hasErrors()) {
			try{
				Chorbi edit= new Chorbi();
				
				edit.setName(chorbi.getName());
				edit.setSurname(chorbi.getSurname());
				edit.setEmail(chorbi.getEmail());
				edit.setPhone(chorbi.getPhone());
				edit.setPicture(chorbi.getPicture());
				edit.setKindRelationship(chorbi.getKindRelationship());
				edit.setBirthDate(chorbi.getBirthDate());
				edit.setGenre(chorbi.getGenre());
				
				result = new ModelAndView("chorbi/display");
				result.addObject("chorbi", edit);
			}catch(Throwable oops){
				result = new ModelAndView("chorbi/edit");
				result.addObject("chorbi", chorbi);
				result.addObject("message", "chorbi.error");
			}
		} else {
			result = new ModelAndView("chorbi/edit");
			result.addObject("chorbi", chorbi);
			result.addObject("message", "chorbi.binding");
		}
		return result;
		
	}
	
	@RequestMapping(value = "/creditCard", method = RequestMethod.GET)
	public ModelAndView creditCard() {
			ModelAndView result;
			
			Chorbi chorbi= chorbiService.findByUserAccountId(LoginService.getPrincipal().getId());
			CreditCard res = chorbi.getCreditCard();
			if(res==null){
				res=creditService.create(chorbi);
			}
			
			result = new ModelAndView("chorbi/creditCard");
			result.addObject("card", res);
			return result;
	}
	
	@RequestMapping(value = "/creditCard", method = RequestMethod.POST, params = "save")
	public ModelAndView creditCard(CreditCard card, BindingResult binding) {
		ModelAndView result;
		CreditCard creditCard = creditService.reconstruct(card, binding);
		if (!binding.hasErrors()) {
			try{
				CreditCard res=creditService.save(creditCard);
				result = new ModelAndView("chorbi/creditCard");
				result.addObject("card", res);
				result.addObject("message", "chorbi.card.success");
			}catch(Throwable oops){
				result = new ModelAndView("chorbi/creditCard");
				result.addObject("card", card);
				result.addObject("message", "chorbi.card.error");
			}
		} else {
			result = new ModelAndView("chorbi/creditCard");
			result.addObject("card", card);
			result.addObject("message", "chorbi.card.error");
		}
		return result;
	}
}
