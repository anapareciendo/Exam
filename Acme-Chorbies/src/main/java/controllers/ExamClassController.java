package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ExamClassService;
import domain.ExamClass;


@Controller
@RequestMapping("/examclass")
public class ExamClassController extends AbstractController {

	@Autowired
	private ExamClassService	examClassService;
	

	public ExamClassController() {
		super();
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam int eventId) {
		ModelAndView result;
		Collection<ExamClass> ec;
		ec = examClassService.findMyNotCanceled(eventId);
		
		result = new ModelAndView("examclass/list");
		result.addObject("requestURI", "examclass/list.do");
		result.addObject("examClass", ec);

		return result;
	}
	
}