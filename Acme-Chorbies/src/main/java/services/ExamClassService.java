package services;

import java.util.Calendar;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.Validator;

import repositories.ExamClassRepository;
import security.LoginService;
import domain.Administrator;
import domain.Event;
import domain.ExamClass;

@Service
@Transactional
public class ExamClassService {

	//Managed repository
	@Autowired
	private ExamClassRepository	examClassRepository;


	//Validator
	@Autowired
	private Validator validator;

	//Supporting services
	@Autowired
	private AdministratorService adminService;

	//Constructors
	public ExamClassService() {
		super();
	}

	//Simple CRUD methods
	public ExamClass create(final Administrator admin, final Event event) {
		Assert.notNull(admin, "The admin cannot be null.");
		Assert.notNull(event, "The event cannot be null.");
		ExamClass res;
		res = new ExamClass();
		res.setAdmin(admin);
		res.setEvent(event);
		res.setMoment(Calendar.getInstance().getTime());
		res.setCanceled(false);
		return res;
	}

	public Collection<ExamClass> findAll() {
		final Collection<ExamClass> res = this.examClassRepository.findAll();
		return res;
	}

	public ExamClass findOne(final int examClassId) {
		final ExamClass res = this.examClassRepository.findOne(examClassId);
		return res;
	}

	public ExamClass save(final ExamClass examClass) {
		Assert.notNull(examClass, "The examClass to save cannot be null.");
		
		Assert.isTrue(examClass.getAdmin().getUserAccount().equals(LoginService.getPrincipal()), "You are not the owner of this examClass");
		
		Assert.notNull(examClass.getMoment(), "The examClass to save cannot have 'moment' null.");
		
		final ExamClass res = this.examClassRepository.save(examClass);
		res.getAdmin().getExamClasses().add(res);
		res.getEvent().getExamClasses().add(res);
		res.setMoment(Calendar.getInstance().getTime());
		
		return res;
	}

	public void delete(final ExamClass examClass) {
		Assert.notNull(examClass, "The examClass to delete cannot be null.");
		Assert.isTrue(this.examClassRepository.exists(examClass.getId()));
		
		Administrator principal = adminService.findByUserAccountId(LoginService.getPrincipal().getId());
		Assert.isTrue(principal.getExamClasses().contains(examClass),"You are not the owner of this examClass");
				
		examClassRepository.delete(examClass);
	}
	
	//----------Other Methods------------------------
	public Collection<ExamClass> findNotCanceled(){
	
		return examClassRepository.findNotCanceled();
	}
	
	public Collection<ExamClass> findMyNotCanceled(int eventId){
		
		return examClassRepository.findMyNotCanceled(eventId);
	}

	
	
	
}