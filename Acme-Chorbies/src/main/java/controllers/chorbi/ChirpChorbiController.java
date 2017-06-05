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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.BroadcastService;
import services.ChirpService;
import services.ChorbiService;
import controllers.AbstractController;
import domain.Broadcast;
import domain.Chirp;
import domain.Chorbi;

@Controller
@RequestMapping("/chirp/chorbi")
public class ChirpChorbiController extends AbstractController {
	
	// Services -------------------------------------------------------
	@Autowired
	private ChirpService chirpService;
	@Autowired
	private BroadcastService broadcastService;
	@Autowired
	private ChorbiService chorbiService;

	// Constructors -----------------------------------------------------------

	public ChirpChorbiController() {
		super();
	}
	
	@RequestMapping(value="/received", method = RequestMethod.GET)
	public ModelAndView listReceived() {
		ModelAndView result;
		
		Collection<Chirp> chirps = chirpService.findMyReceivedChirps(LoginService.getPrincipal().getId());

		result = new ModelAndView("chirp/list");
		result.addObject("chirps", chirps);
		result.addObject("requestUri", "/chirp/chorbi/received.do");
		
		result.addObject("received", true);

		return result;
	}
	
	@RequestMapping(value="/broadcast", method = RequestMethod.GET)
	public ModelAndView broadcastsList() {
		ModelAndView result;
		
		
		List<Broadcast> casts = new ArrayList<Broadcast>();
		casts.addAll(broadcastService.findMyBroadcast());
		Broadcast cast = broadcastService.findBroadcastManager();
		if(cast.getId()!=0){
			casts.add(cast);
		}
		
		result = new ModelAndView("chirp/broadcast");
		result.addObject("broadcast", casts);
		result.addObject("requestUri", "/chirp/chorbi/broadcast.do");
		
		return result;
	}
	
	@RequestMapping(value="/sent", method = RequestMethod.GET)
	public ModelAndView listSent() {
		ModelAndView result;
		
		
		Collection<Chirp> chirps = chirpService.findMySentChirps(LoginService.getPrincipal().getId());
		
		result = new ModelAndView("chirp/list");
		result.addObject("chirps", chirps);
		result.addObject("requestUri", "/chirp/chorbi/sent.do");

		return result;
	}
	
	@RequestMapping(value="/chirp", method = RequestMethod.GET)
	public ModelAndView like(@RequestParam int chorbiId) {
		ModelAndView result;
		
		Chorbi recipient = chorbiService.findOne(chorbiId);
		Chorbi sender = chorbiService.findByUserAccountId(LoginService.getPrincipal().getId());
		
		Chirp res = chirpService.create(sender, recipient);
		
		result = new ModelAndView("chirp/chirp");
		result.addObject("chirp", res);
		result.addObject("mode", "send");

		return result;
	}
	
	@RequestMapping(value="/reply", method = RequestMethod.GET)
	public ModelAndView reply(@RequestParam int chirpId) {
		ModelAndView result;
		
		Chirp res = chirpService.findOne(chirpId);
		res.setText("");
		
		result = new ModelAndView("chirp/chirp");
		result.addObject("chirp", res);
		result.addObject("mode", "reply");

		return result;
	}
	
	@RequestMapping(value="/forward", method = RequestMethod.GET)
	public ModelAndView forward(@RequestParam int chirpId) {
		ModelAndView result;
		
		Chirp res = chirpService.findOne(chirpId);
		Collection<Chorbi> chorbies = chorbiService.findNotBanned();
		
		result = new ModelAndView("chirp/chirp");
		result.addObject("chirp", res);
		result.addObject("mode", "forward");
		result.addObject("chorbi", chorbies);

		return result;
	}
	
	@RequestMapping(value="/send", method = RequestMethod.POST, params="send")
	public ModelAndView sendChirp(Chirp chirp, BindingResult binding) {
		ModelAndView result;
		Chirp res = chirpService.reconstruct(chirp, binding);
		
		if(!binding.hasErrors()){
			try{
				chirpService.save(res);
				Collection<Chorbi> chorbis = chorbiService.findNotBanned();
				result = new ModelAndView("chorbi/list");
				result.addObject("chorbi", chorbis);
				result.addObject("message", "chorbi.commit.success");
			}catch (Throwable opps){
				result = new ModelAndView("chirp/chirp");
				result.addObject("chirp", chirp);
				result.addObject("message","chirp.commit.incomplete");
				result.addObject("mode", "send");
			}
		}else{
			result = new ModelAndView("chirp/chirp");
			result.addObject("chirp", chirp);
			result.addObject("message","chirp.commit.error");
			result.addObject("mode", "send");
		}
		
		
		return result;
	}
	
	@RequestMapping(value = "/send", method = RequestMethod.POST, params = "reply")
	public ModelAndView replyChirp(Chirp chirp) {
		ModelAndView result;
		try{
			Chirp res=chirpService.reply(chirpService.findOne(chirp.getId()), chirp.getText());
			chirpService.save(res);
			
			Collection<Chirp> chirps = chirpService.findMyReceivedChirps(LoginService.getPrincipal().getId());
			result = new ModelAndView("chirp/list");
			result.addObject("chirps", chirps);
			result.addObject("requestUri", "/chirp/chorbi/received.do");
			result.addObject("received", true);
			result.addObject("message", "chirp.commit.ok");
			
		}catch(Throwable oops){
			result = new ModelAndView("chirp/chirp");
			result.addObject("chirp", chirp);
			result.addObject("mode", "reply");
			result.addObject("message", "chirp.commit.error");
		}
		return result;
	}
	
	@RequestMapping(value = "/send", method = RequestMethod.POST, params = "forward")
	public ModelAndView forwardChirp(Chirp chirp) {
		ModelAndView result;
		try{
			Chirp res = chirpService.forward(chirp, chirp.getRecipient());
			chirpService.save(res);
			
			Collection<Chirp> chirps = chirpService.findMyReceivedChirps(LoginService.getPrincipal().getId());
			result = new ModelAndView("chirp/list");
			result.addObject("chirps", chirps);
			result.addObject("received", true);
			result.addObject("requestUri", "/chirp/chorbi/received.do");
			result.addObject("message", "chirp.commit.ok");
			
		}catch(Throwable oops){
			Collection<Chorbi> chorbies = chorbiService.findNotBanned();
			result = new ModelAndView("chirp/chirp");
			result.addObject("chirp", chirp);
			result.addObject("mode", "forward");
			result.addObject("chorbi", chorbies);
			result.addObject("message", "chirp.commit.error");
		}
		return result;
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam int chirpId) {
		ModelAndView result;
		Chirp chirp = chirpService.findOne(chirpId);
		if(chirp==null){
			Collection<Chirp> chirps = chirpService.findMyReceivedChirps(LoginService.getPrincipal().getId());
			
			result = new ModelAndView("chirp/list");
			result.addObject("chirps", chirps);
			result.addObject("requestUri", "/chirp/chorbi/received.do");
			result.addObject("received", true);
			result.addObject("message", "chirp.commit.error");
		}else{
			result = new ModelAndView("chirp/delete");
			result.addObject("chirp", chirp);
		}
		return result;
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(Chirp chirp) {
		ModelAndView result;
		
		try {
			Chirp res = chirpService.findOne(chirp.getId());
			chirpService.delete(res);
			
			
			result = new ModelAndView("chirp/list");
			
			Chorbi ppal = chorbiService.findByUserAccountId(LoginService.getPrincipal().getId());
			Collection<Chirp> chirps = null;
			if(res.getRecipient().equals(ppal)){
				result.addObject("received", true);
				chirps = chirpService.findMyReceivedChirps(LoginService.getPrincipal().getId());
			}else{
				chirps = chirpService.findMySentChirps(LoginService.getPrincipal().getId());
			}
			result.addObject("chirps", chirps);
			result.addObject("requestUri", "/chirp/chorbi/received.do");
			
			result.addObject("message", "chirp.delete.success");
		} catch (Throwable oops) {
			Collection<Chirp> chirps = chirpService.findMyReceivedChirps(LoginService.getPrincipal().getId());
			
			result = new ModelAndView("chirp/list");
			result.addObject("chirps", chirps);
			result.addObject("requestUri", "/chirp/chorbi/received.do");
			result.addObject("received", true);
			result.addObject("message", "chirp.commit.error");
		}
		return result;
	}
	
}