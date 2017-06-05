package controllers.chorbi;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.ChorbiService;
import services.EventService;
import controllers.AbstractController;
import domain.Event;


@Controller
@RequestMapping("/event/chorbi")
public class EventChorbiController extends AbstractController {

	@Autowired
	private ChorbiService chorbiService;
	@Autowired
	private EventService eventService;

	public EventChorbiController() {
		super();
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Event> event = chorbiService.findByUserAccountId(LoginService.getPrincipal().getId()).getEvents();
		
		result = new ModelAndView("event/list");
		result.addObject("requestURI", "event/chorbi/list.do");
		result.addObject("event", event);
		result.addObject("own", true);
		if(event.isEmpty()){
			result.addObject("isEmpty", true);
		}

		return result;
	}
	
	@RequestMapping(value = "/unregister", method = RequestMethod.GET)
	public ModelAndView unregister(@RequestParam int eventId) {
		ModelAndView result;
		
		result = new ModelAndView("event/list");
		try{
			eventService.unregister(eventId);
			result.addObject("message", "event.unregister.success");
		}catch(Throwable oops){
			result.addObject("message", "event.commit.error");
		}
		Collection<Event> event = chorbiService.findByUserAccountId(LoginService.getPrincipal().getId()).getEvents();
		
		result.addObject("requestURI", "event/chorbi/list.do");
		result.addObject("event", event);
		result.addObject("own", true);
		if(event.isEmpty()){
			result.addObject("isEmpty", true);
		}
		
		return result;
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView register(@RequestParam int eventId) {
		ModelAndView result;
		
		result = new ModelAndView("event/list");
		
		try{
			eventService.register(eventId);
			result.addObject("message", "event.register.success");
		}catch(Throwable oops){
			result.addObject("message", "event.commit.error");
		}
		
		Collection<Event> event;
		event = eventService.eventOrganisedLessMonthAndSeatsAvailable();
		
		result = new ModelAndView("event/list/available");
		result.addObject("requestURI", "event/listAvailable.do");
		result.addObject("event", event);
		result.addObject("available", true);
		result.addObject("edit",true);
		try{
			if(chorbiService.findByUserAccountId(LoginService.getPrincipal().getId())!=null){
				result.addObject("all", true);
			}
		}catch(Throwable oops){}
		if(event.isEmpty()){
			result.addObject("isEmpty", true);
		}
		result.addObject("message", "event.register.success");
		
		return result;
	}
	
}