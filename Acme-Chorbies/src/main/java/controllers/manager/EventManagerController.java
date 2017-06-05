package controllers.manager;

import java.util.Calendar;
import java.util.Collection;

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
import services.EventService;
import services.ManagerService;
import controllers.AbstractController;
import domain.Event;


@Controller
@RequestMapping("/event/manager")
public class EventManagerController extends AbstractController {

	@Autowired
	private EventService	eventService;
	@Autowired
	private ManagerService managerService;
	@Autowired
	private BroadcastService broadcastService;
	@Autowired
	private ChirpService chirpService;

	public EventManagerController() {
		super();
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Event> event = eventService.findMyEvents();
		
		result = new ModelAndView("event/list");
		result.addObject("requestURI", "event/manager/list.do");
		result.addObject("event", event);
		result.addObject("edit",true);
		if(event.isEmpty()){
			result.addObject("isEmpty", true);
		}
		

		return result;
	}
	
	@RequestMapping(value="/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		
		Event res = eventService.create(managerService.findByUserAccountId(LoginService.getPrincipal().getId()));
		
		result = new ModelAndView("event/create");
		result.addObject("event", res);

		return result;
	}
	
	@RequestMapping(value="/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int eventId) {
		ModelAndView result;
		
		Event res = eventService.findOne(eventId);
		
		result = new ModelAndView("event/edit");
		result.addObject("event", res);

		return result;
	}
	
	@RequestMapping(value="/edit", method = RequestMethod.POST, params="save")
	public ModelAndView saveEvent(Event event, BindingResult binding) {
		ModelAndView result;
		
		try{
			Event res = eventService.reconstruct(event, binding);
			if(!binding.hasErrors()){
				try{
					Calendar date =Calendar.getInstance();
					int day=date.get(Calendar.DAY_OF_MONTH);
					int month=date.get(Calendar.MONTH)+1;
					int year=date.get(Calendar.YEAR);
					int hour=date.get(Calendar.HOUR_OF_DAY);
					int minutes=date.get(Calendar.MINUTE);
					if(event.getId()!=0 || event.getId()==0 && (event.getYear()>year || (event.getYear()==year && event.getMonth()>=month) || (event.getYear()==year && event.getMonth()==month && event.getDay()>day)
							|| (event.getYear()==year && event.getMonth()==month && event.getDay()>day && event.getHour()>hour) 
							|| (event.getYear()==year && event.getMonth()==month && event.getDay()>day && event.getHour()>hour && event.getMinutes()>minutes+10))){
						Event saved=eventService.save(res);
						if(event.getId()==0){
							managerService.eventFee();
						}
						broadcastService.broadcastEditEvent(saved);
						
						result = new ModelAndView("redirect:list.do");
					}else{
						if(event.getId()!=0){
							result = new ModelAndView("event/edit");
						}else{
							result = new ModelAndView("event/create");
						}
						result.addObject("event", event);
						result.addObject("message", "event.error.date");
					}
				}catch (Throwable opps){
					if(event.getId()!=0){
						result = new ModelAndView("event/edit");
					}else{
						result = new ModelAndView("event/create");
					}
					result.addObject("event", event);
					result.addObject("message","event.commit.error");
				}
			}else{
				if(event.getId()!=0){
					result = new ModelAndView("event/edit");
				}else{
					result = new ModelAndView("event/create");
				}
				result.addObject("event", event);
				result.addObject("message","event.commit.incomplete");
			}
		}catch(Throwable oops){
			if(event.getId()!=0){
				result = new ModelAndView("event/edit");
			}else{
				result = new ModelAndView("event/create");
			}
			result.addObject("event", event);
			result.addObject("message","event.commit.incomplete");
		}
		
		return result;
	}
	
	@RequestMapping(value="/edit", method = RequestMethod.POST, params="delete")
	public ModelAndView delete(Event event) {
		ModelAndView result;
		
		try{
			Event res = eventService.findOne(event.getId());
			chirpService.broadcast(res);
			//TODO
			eventService.delete(res);
			result = new ModelAndView("redirect:list.do");
		}catch (Throwable opps){
			result = new ModelAndView("event/edit");
			result.addObject("event", event);
			result.addObject("message","event.commit.error");
		}
		return result;
	}
	
}