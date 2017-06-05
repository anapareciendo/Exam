package services;

import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.MonthlyFeeRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Chorbi;
import domain.MonthlyFee;

@Service
@Transactional
public class MonthlyFeeService {

	//Managed repository
	@Autowired
	private MonthlyFeeRepository	monthlyFeeRepository;

	//Validator
//	@Autowired
//	private Validator validator;

	//Supporting services
	@Autowired
	private ConfigService configService;
	@Autowired
	private ChorbiService chorbiService;

	//Constructors
	public MonthlyFeeService() {
		super();
	}

	//Simple CRUD methods
	public MonthlyFee create(final Chorbi chorbi) {
		Assert.notNull(chorbi, "The chorbi cannot be null.");
		MonthlyFee res;
		res = new MonthlyFee();
		res.setChorbi(chorbi);
		res.setMoment(Calendar.getInstance().getTime());
		res.setAmount(configService.find().getRegistrationFee());
		return res;
	}

	public Collection<MonthlyFee> findAll() {
		final Collection<MonthlyFee> res = this.monthlyFeeRepository.findAll();
		return res;
	}

	public MonthlyFee findOne(final int monthlyFeeId) {
		final MonthlyFee res = this.monthlyFeeRepository.findOne(monthlyFeeId);
		return res;
	}

	public MonthlyFee save(final MonthlyFee monthlyFee) {
		Assert.notNull(monthlyFee, "The monthly fee to save cannot be null.");
		
		final UserAccount ua = LoginService.getPrincipal();
		Assert.notNull(ua);
		final Authority a = new Authority();
		a.setAuthority(Authority.ADMIN);
		Assert.isTrue(ua.getAuthorities().contains(a), "You must to be an admin to create a monthly fee");
		
		final MonthlyFee res = this.monthlyFeeRepository.save(monthlyFee);
		res.setMoment(Calendar.getInstance().getTime());
		res.getChorbi().getMonthlyFee().add(res);
		return res;
	}

	public void delete(final MonthlyFee monthlyFee) {
		Assert.notNull(monthlyFee, "The monthlyFee to delete cannot be null.");

		final UserAccount ua = LoginService.getPrincipal();
		Assert.notNull(ua);
		final Authority a = new Authority();
		a.setAuthority(Authority.ADMIN);
		Assert.isTrue(ua.getAuthorities().contains(a), "You must to be an admin to delete a monthly fee");
		
		monthlyFeeRepository.delete(monthlyFee);
	}

	//----------Other Methods------------------------
	public Boolean generateFees() {
		Boolean res = true;
		Set<Chorbi> chorbies=new HashSet<Chorbi>();
		chorbies.addAll(chorbiService.findNotBanned());
		chorbies.removeAll(monthlyFeeRepository.findFeeChorbi());
		if(chorbies.isEmpty()){
			res=false;
		}else{
			for(Chorbi c:chorbies){
				MonthlyFee mf = this.create(c);
				this.save(mf);
			}
		}
		return res;
	}
}