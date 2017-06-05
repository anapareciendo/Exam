/*
 * CustomerController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers.chorbi;

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
import services.LikesService;
import controllers.AbstractController;
import domain.Chorbi;
import domain.Likes;

@Controller
@RequestMapping("/likes/chorbi")
public class LikesChorbiController extends AbstractController {
	
	// Services -------------------------------------------------------
	@Autowired
	private ChorbiService chorbiService;
	@Autowired
	private LikesService likesService;

	// Constructors -----------------------------------------------------------

	public LikesChorbiController() {
		super();
	}
	
	@RequestMapping(value="/listReceivedLikes", method = RequestMethod.GET)
	public ModelAndView listReceivedLikes() {
		ModelAndView result;
		
		Chorbi chorbi = chorbiService.findByUserAccountId(LoginService.getPrincipal().getId());
		
		Collection<Likes> makeLikes = chorbi.getReceivedLikes();
		
		result = new ModelAndView("likes/list");
		result.addObject("likes", makeLikes);
		result.addObject("make", false);
		result.addObject("requestUri", "/likes/chorbi/listReceivedLikes.do");

		return result;
	}
	
	
	@RequestMapping(value="/listMakeLikes", method = RequestMethod.GET)
	public ModelAndView listMakeLikes() {
		ModelAndView result;
		
		Chorbi chorbi = chorbiService.findByUserAccountId(LoginService.getPrincipal().getId());
		
		Collection<Likes> makeLikes = chorbi.getMakeLikes();
		
		result = new ModelAndView("likes/list");
		result.addObject("likes", makeLikes);
		result.addObject("make", true);
		result.addObject("requestUri", "/likes/chorbi/listMakeLikes.do");

		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam int likesId) {
		ModelAndView result;
		
//		try {
			Likes likes = likesService.findOne(likesId);
			if(likes==null){
				Chorbi chorbi = chorbiService.findByUserAccountId(LoginService.getPrincipal().getId());
				
				Collection<Likes> makeLikes = chorbi.getMakeLikes();
				
				result = new ModelAndView("likes/list");
				result.addObject("likes", makeLikes);
				result.addObject("make", true);
				result.addObject("requestUri", "/likes/chorbi/listMakeLikes.do");
				result.addObject("message", "like.commit.error");
			}else{
				result = new ModelAndView("likes/delete");
				result.addObject("likes", likes);
			}
//		} catch (Throwable oops) {
//		
//			result = new ModelAndView("redirect:listMakeLikes.do");
//			result.addObject("message", "like.commit.error");
//		}

		return result;
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(Likes likes) {
		ModelAndView result;
		
		try {
			Likes likesd = likesService.findOne(likes.getId());
			likesService.delete(likesd);
			
			Chorbi chorbi = chorbiService.findByUserAccountId(LoginService.getPrincipal().getId());
			Collection<Likes> makeLikes = chorbi.getMakeLikes();
			result = new ModelAndView("likes/list");
			result.addObject("requestURI", "likes/list.do");
			result.addObject("likes", makeLikes);
			result.addObject("make", true);
			result.addObject("message", "like.delete.success");
		} catch (Throwable oops) {
		
			
			Chorbi chorbi = chorbiService.findByUserAccountId(LoginService.getPrincipal().getId());
			Collection<Likes> makeLikes = chorbi.getMakeLikes();
			result = new ModelAndView("likes/list");
			result.addObject("requestURI", "likes/list.do");
			result.addObject("likes", makeLikes);
			result.addObject("make", true);
			result.addObject("message", "like.commit.error");
		}

		return result;
	}
	
	@RequestMapping(value="/like", method = RequestMethod.GET)
	public ModelAndView like(@RequestParam int chorbiId) {
		ModelAndView result;
		
		Chorbi liked = chorbiService.findOne(chorbiId);
		
		Likes res = likesService.create(liked, liked);
		
		result = new ModelAndView("likes/like");
		result.addObject("likes", res);

		return result;
	}
	
		@RequestMapping(value="/saylike", method = RequestMethod.POST)
		public ModelAndView sayLike(Likes likes, BindingResult binding) {
			ModelAndView result;
			Likes res = likesService.reconstruct(likes, binding);
			
			if(!binding.hasErrors()){
				try{
					likesService.save(res);
					Collection<Chorbi> chorbis = chorbiService.findNotBanned();
					result = new ModelAndView("chorbi/list");
					result.addObject("chorbi", chorbis);
					result.addObject("message", "like.commit.success");
				}catch (Throwable opps){
					result = new ModelAndView("likes/like");
					result.addObject("likes", res);
					result.addObject("message","like.commit.error");
				}
			}else{
				result = new ModelAndView("likes/like");
				result.addObject("likes", res);
				result.addObject("message","like.commit.incomplete");
			}
			
			
			return result;
		}
	
}
