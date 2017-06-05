package controllers;

import java.util.Calendar;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.ChorbiService;
import services.EventService;
import domain.Event;


@Controller
@RequestMapping("/event")
public class EventController extends AbstractController {

	@Autowired
	private EventService	eventService;
	@Autowired
	private ChorbiService chorbiService;

	public EventController() {
		super();
	}
	
	@RequestMapping(value = "/listAvailable", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Event> event;
		event = eventService.eventOrganisedLessMonthAndSeatsAvailable();
//		event = eventService.findAll();
		//TODO
		
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

		return result;
	}
	
	@RequestMapping(value = "/listAll", method = RequestMethod.GET)
	public ModelAndView listAll() {
		ModelAndView result;
		Collection<Event> event;
		event = eventService.findAll();
		
		result = new ModelAndView("event/list/all");
		result.addObject("requestURI", "event/listAll.do");
		result.addObject("event", event);
		Calendar date = Calendar.getInstance();
		result.addObject("month", date.get(Calendar.MONTH)+1);
		result.addObject("year", date.get(Calendar.YEAR));
		result.addObject("day", date.get(Calendar.DAY_OF_MONTH));
		result.addObject("allEvents", true);
		if(event.isEmpty()){
			result.addObject("isEmpty", true);
		}

		return result;
	}
	
}