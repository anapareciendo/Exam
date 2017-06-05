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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.ChorbiService;
import services.ConfigService;
import services.SearchTemplateService;
import domain.Chorbi;
import domain.SearchTemplate;
import forms.TemplateForm;

@Controller
@RequestMapping("/template")
public class SearchTemplateController extends AbstractController {
	
	// Services -------------------------------------------------------
	@Autowired
	private SearchTemplateService searchTemplateService;
	@Autowired
	private ChorbiService chorbiService;
	@Autowired
	private ConfigService configService;

	// Constructors -----------------------------------------------------------

	public SearchTemplateController() {
		super();
	}

	@RequestMapping(value="/search", method = RequestMethod.GET)
	public ModelAndView search() {
		ModelAndView result;
		TemplateForm res;
		SearchTemplate chorbi = chorbiService.findByUserAccountId(LoginService.getPrincipal().getId()).getSearchTemplate();
		if(chorbi==null){
			res = new TemplateForm();
		}else{
			int aux = chorbi.getGenre().getValue();
			System.out.println(aux);
			res = new TemplateForm(chorbi.getKindRelationship().getValue(), chorbi.getAproximateAge(), 
					chorbi.getGenre().getValue(), chorbi.getKeyword(), chorbi.getCoordinates().getCountry(), 
					chorbi.getCoordinates().getCity(), chorbi.getCoordinates().getState(), 
					chorbi.getCoordinates().getProvince());
		}
		
		result = new ModelAndView("template/template");
		result.addObject("template", res);
		
		return result;
		
	}
	
	@RequestMapping(value = "/template", method = RequestMethod.POST, params = "search")
	public ModelAndView user(TemplateForm template, BindingResult binding) {
		ModelAndView result;
		SearchTemplate search;
		try{
			search = searchTemplateService.reconstruct(template, binding);
			if (!binding.hasErrors()) {
				try{
					Set<Chorbi> chorbis = new HashSet<Chorbi>();
					chorbis.addAll(searchTemplateService.searchTemplate(search.getKindRelationship(), search.getGenre(), template.getAproximateAge(), template.getCountry(), template.getCity(), template.getState(), template.getProvince(), template.getKeyword()));
					
					search.getResults().clear();
					search.getResults().addAll(chorbis);
					
					searchTemplateService.save(search);
					
					Chorbi chorbi = chorbiService.findByUserAccountId(LoginService.getPrincipal().getId());
					if(chorbi.getCreditCard()==null){
						result = new ModelAndView("template/template");
						result.addObject("template", template);
						result.addObject("message", "template.creditCard.error");
					}else{
						int years=(chorbi.getCreditCard().getExpirationYear()+2000)-1970;
						int month=chorbi.getCreditCard().getExpirationMonth();
						long exp = years*31540000000l+month*2628000000l;
						long finaleC = exp-Calendar.getInstance().getTime().getTime();
						if(finaleC<0){
							result = new ModelAndView("template/template");
							result.addObject("template", template);
							result.addObject("message", "template.creditCard.error");
						}else{
							chorbis.addAll(search.getResults());
							result = new ModelAndView("chorbi/list");
							result.addObject("chorbi", chorbis);
						}
					}
				}catch(Throwable oops){
					result = new ModelAndView("template/template");
					result.addObject("template", template);
					result.addObject("message", "template.error");
				}
			} else {
				result = new ModelAndView("template/template");
				result.addObject("template", template);
				result.addObject("message", "template.binding");
			}
		}catch(Throwable opps){
			result = new ModelAndView("template/template");
			result.addObject("template", template);
			result.addObject("message", "template.binding");
		}

		
		return result;
	}
	
	@RequestMapping(value="/result", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		TemplateForm res;
		Chorbi chorbi =chorbiService.findByUserAccountId(LoginService.getPrincipal().getId());
		SearchTemplate search = chorbi.getSearchTemplate();
		List<Chorbi> chorbis = new ArrayList<Chorbi>();
		
		if(search==null){
			res = new TemplateForm();
			result = new ModelAndView("template/template");
			result.addObject("template", res);
			result.addObject("message", "template.void");
		}else if(chorbi.getCreditCard()==null){
			res = new TemplateForm(search.getKindRelationship().getValue(), search.getAproximateAge(), 
					search.getGenre().getValue(), search.getKeyword(), search.getCoordinates().getCountry(), 
					search.getCoordinates().getCity(), search.getCoordinates().getState(), 
					search.getCoordinates().getProvince());
			result = new ModelAndView("template/template");
			result.addObject("template", res);
			result.addObject("message", "template.creditCard.error");
		}else{ 
			long st = search.getMoment().getTime(); //ms del SearchTemplate
			long now = Calendar.getInstance().getTime().getTime(); //ms Curren Date
			long finale = now-st; //ms desde que se uso el SearchTemplate
			long cache = configService.find().getCache()*3600000l; //3600000ms->1h
			
			if(finale>cache){
				int aux = chorbi.getGenre().getValue();
				System.out.println(aux);
				res = new TemplateForm(search.getKindRelationship().getValue(), search.getAproximateAge(), 
					search.getGenre().getValue(), search.getKeyword(), search.getCoordinates().getCountry(), 
					search.getCoordinates().getCity(), search.getCoordinates().getState(), 
					search.getCoordinates().getProvince());
				result = new ModelAndView("template/template");
				result.addObject("template", res);
				result.addObject("message", "template.expired");
			}else{
				int years=(chorbi.getCreditCard().getExpirationYear()+2000)-1970;
				int month=chorbi.getCreditCard().getExpirationMonth();
				long exp = years*31540000000l+month*2628000000l;
				long finaleC = exp-Calendar.getInstance().getTime().getTime();
				if(finaleC<0){
					res = new TemplateForm(search.getKindRelationship().getValue(), search.getAproximateAge(), 
							search.getGenre().getValue(), search.getKeyword(), search.getCoordinates().getCountry(), 
							search.getCoordinates().getCity(), search.getCoordinates().getState(), 
							search.getCoordinates().getProvince());
					result = new ModelAndView("template/template");
					result.addObject("template", res);
					result.addObject("message", "template.creditCard.error");
				}else{
					chorbis.addAll(search.getResults());
					result = new ModelAndView("chorbi/list");
					result.addObject("chorbi", chorbis);
				}
			}
		}

		return result;
	}
	
}
