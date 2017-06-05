package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AdministratorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Administrator;
import domain.Chorbi;
import domain.ExamClass;
import domain.Manager;
import forms.Dashboard;

@Service
@Transactional
public class AdministratorService {

	//Managed repository
	@Autowired
	private AdministratorRepository	administratorRepository;

	//Supporting services

	//Constructors
	public AdministratorService() {
		super();
	}

	//Simple CRUD methods
	public Administrator create(final UserAccount ua) {
		Administrator res;
		res = new Administrator();
		res.setUserAccount(ua);
		res.setExamClasses(new ArrayList<ExamClass>());
		return res;
	}

	public Collection<Administrator> findAll() {
		final Collection<Administrator> res = this.administratorRepository.findAll();
		return res;
	}

	public Administrator findOne(final int adminId) {
		final Administrator res = this.administratorRepository.findOne(adminId);
		return res;
	}

	public Administrator save(final Administrator admin) {
		Assert.notNull(admin, "The administrator to save cannot be null.");
		final Administrator res = this.administratorRepository.save(admin);
		administratorRepository.flush();
		return res;
	}

	public void delete(final Administrator admin) {
		final UserAccount ua = LoginService.getPrincipal();
		Assert.notNull(ua);
		final Authority a = new Authority();
		a.setAuthority(Authority.ADMIN);
		Assert.isTrue(ua.getAuthorities().contains(a), "You must to be a admin to delete an actor.");

		Assert.notNull(admin, "The adminstrator to delete cannot be null.");
		Assert.isTrue(this.administratorRepository.exists(admin.getId()));

		this.administratorRepository.delete(admin);
	}

	//Utilites methods
	public Administrator findByUserAccountId(final int id) {
		Assert.notNull(id);
		return this.administratorRepository.findByUserAccountId(id);
	}

	//Para el dashboard
	private void isAdministrator(){
		UserAccount ua = LoginService.getPrincipal();
		Assert.notNull(ua);
		Authority a = new Authority();
		a.setAuthority(Authority.ADMIN);
		Assert.isTrue(ua.getAuthorities().contains(a), "You must to be a administrator for this action.");
	 }
	
	//---LEVEL C.1---
	
		//A listing with the number of chorbies per city
		public Collection<Integer> numChorbiesPerCity(){
			this.isAdministrator();
			return this.administratorRepository.numChorbiesPerCity();
		}
		
		//A listing with the number of chorbies per country
		public Collection<Integer> numChorbiesPerCountry(){
			this.isAdministrator();
			return this.administratorRepository.numChorbiesPerCountry();
		}
		
		//The minimum ages of the chorbies
		public Integer minAgeChorbies(){
			this.isAdministrator();
			return this.administratorRepository.minAgeChorbies();
		}

		//The maximum ages of the chorbies
		public Integer maxAgeChorbies(){
			this.isAdministrator();
			return this.administratorRepository.maxAgeChorbies();
		}
		
		//The avg ages of the choribes
		public Double avgAgeChorbies(){
			this.isAdministrator();
			return this.administratorRepository.avgAgeChorbies();
		}
		
//		The ratio of chorbies who have not registered a credit card or have registered an invalid credit card
		public Double ratioChorbiesCreditCard(){
			this.isAdministrator();
			Double res = 0.0;
			Integer not=administratorRepository.chorbisWithCreditCard();
			if(not>0){
				Integer yes=administratorRepository.chorbisWithoutCreditCard();
				res=yes/(double)not;
			}
			return res;
		}
		
//		The ratios of chorbies who search for activities
		public Double ratioChorbisWhoSearchActivities(){
			Double res = this.cero();
			if(res>0.0){
				res=administratorRepository.chorbisWhoSearchActivities()/res;
			}
			return res;
		}
		
//		The ratios of chorbies who search for friendship
		public Double ratioChorbisWhoSearchFriendship(){
			Double res = this.cero();
			if(res>0.0){
				res=administratorRepository.chorbisWhoSearchFriendship()/res;
			}
			return res;
		}
		
//		The ratios of chorbies who search for love.
		public Double ratioChorbisWhoSearchLove(){
			Double res = this.cero();
			if(res>0.0){
				res=administratorRepository.chorbisWhoSearchLove()/res;
			}
			return res;
		}
		
		private Double cero(){
			this.isAdministrator();
			return (double)administratorRepository.chorbisNotBannedRatio();
		}
		
		//---LEVEL C.2---
		//A listing of managers sorted by the number of events that they organise
		public Collection<Manager> listManagersOrderByEvents(){
			return administratorRepository.listManagersOrderByEvents();
		}
		
		//A listing of managers that includes the amount that they due in fees
		public Collection<Manager> listManagersOrderByAmount(){
			return administratorRepository.listManagersOrderByAmount();
		}
		
		//A listing of chorbies sorted by the number of events to which they have registered
		public Collection<Chorbi> listChorbiesOrderyByEvents(){
			return administratorRepository.listChorbiesOrderyByEvents();
		}
		
		//A listing of chorbies that includes the amount that they due in fees
		public Collection<Double> listChorbiesOrderByAmount(){
			return this.administratorRepository.listChorbiesOrderByAmount();
		}

		//---LEVEL B.1---
		
		//List of chorbies, sorted by the number of likes the have got
		public Collection<Chorbi> chorbiesSortedByLikes(){
			this.isAdministrator();
			return this.administratorRepository.chorbiesSortedByLikes();
		}
		
		//The minimum number of likes per chorbi
		public Integer minLikesPerChorbi(){
			this.isAdministrator();
			return this.administratorRepository.minLikesPerChorbi();
		}
		
		//The maximum number of likes per chorbi
		public Integer maxLikesPerChorbi(){
			this.isAdministrator();
			return this.administratorRepository.maxLikesPerChorbi();
		}
		
		//The avg number of likes per chorbi
		public Double avgLikesPerChorbi(){
			this.isAdministrator();
			return this.administratorRepository.avgLikesPerChorbi();
		}
		
		//---LEVEL B.2---
		//The minimum, the maximum, and the average number of stars per chorbi
		public Collection<Integer> minStars(){
			return administratorRepository.minStars();
		}
		
		public Collection<Integer> maxStars(){
			return administratorRepository.maxStars();
		}
		
		public Collection<Double> avgStars(){
			return administratorRepository.avgStars();
		}
		
		//The list of chorbies, sorted by the average number of stars that they've got
		public Collection<Chorbi> chorbiesOrderByStars(){
			return administratorRepository.chorbiesOrderByStars();
		}
		
		//---LEVEL A---
		
		//The minimum number of chirps that a chorbi receives from other chorbies
		public Integer minChirpsReceived(){
			this.isAdministrator();
			return this.administratorRepository.minChirpsReceived();
		}
		
		//The maximum number of chirps that a chorbi receives from other chorbies
		public Integer maxChirpsReceived(){
			this.isAdministrator();
			return this.administratorRepository.maxChirpsReceived();
		}
		
		//The avg number of chirps that a chorbi receives from other chorbies
		public Double avgChirpsReceived(){
			this.isAdministrator();
			return this.administratorRepository.avgChirpsReceived();
		}
		
		//The minimum number of chirps that a chorbi send to other chorbies
		public Integer minChirpsSend(){
			this.isAdministrator();
			return this.administratorRepository.minChirpsSend();
		}
			
		//The maximum number of chirps that a chorbi send to other chorbies
		public Integer maxChirpsSend(){
			this.isAdministrator();
			return this.administratorRepository.maxChirpsSend();
		}
			
		//The avg number of chirps that a chorbi send to other chorbies
		public Double avgChirpsSend(){
			this.isAdministrator();
			return this.administratorRepository.avgChirpsSend();
		}
		
		//The chorbies who have got more chirps
		public Collection<Chorbi> chorbiesMoreChirpsReceived(){
			this.isAdministrator();
			return this.administratorRepository.chorbiesMoreChirpsReceived();
		}
		
		//The chorbies who have sent more chirps
		public Collection<Chorbi> chorbiesMoreChirpsSent(){
			this.isAdministrator();
			return this.administratorRepository.chorbiesMoreChirpsSent();
		}
		
		public Dashboard dashboard(){
			this.isAdministrator();
			Dashboard res = new Dashboard();
			
			res.setNumChorbiesPerCity(this.numChorbiesPerCity());
			res.setNumChorbiesPerCountry(this.numChorbiesPerCountry());
			res.setMinAgeChorbies(this.minAgeChorbies());
			res.setMaxAgeChorbies(this.maxAgeChorbies());
			res.setAvgAgeChorbies(this.avgAgeChorbies());
			res.setRatioChorbiesCreditCard(this.ratioChorbiesCreditCard());
			res.setRatioChorbisWhoSearchActivities(this.ratioChorbisWhoSearchActivities());
			res.setRatioChorbisWhoSearchFriendship(this.ratioChorbisWhoSearchFriendship());
			res.setRatioChorbisWhoSearchLove(this.ratioChorbisWhoSearchLove());
			res.setChorbiesSortedByLikes(this.chorbiesSortedByLikes());
			res.setMinLikesPerChorbi(this.minLikesPerChorbi());
			res.setMaxLikesPerChorbi(this.maxLikesPerChorbi());
			res.setAvgLikesPerChorbi(this.avgLikesPerChorbi());
			res.setMinChirpsReceived(this.minChirpsReceived());
			res.setMaxChirpsReceived(this.maxChirpsReceived());
			res.setAvgChirpsReceived(this.avgChirpsReceived());
			res.setMinChirpsSend(this.minChirpsSend());
			res.setMaxChirpsSend(this.maxChirpsSend());
			res.setAvgChirpsSend(this.avgChirpsSend());
			res.setChorbiesMoreChirpsReceived(this.chorbiesMoreChirpsReceived());
			res.setChorbiesMoreChirpsSent(this.chorbiesMoreChirpsSent());
			
			res.setListManagersOrderByEvents(this.listManagersOrderByEvents());
			res.setListManagersOrderByAmount(this.listManagersOrderByAmount());
			res.setListChorbiesOrderyByEvents(this.listChorbiesOrderyByEvents());
			res.setListChorbiesOrderByAmount(this.listChorbiesOrderByAmount());
			
			res.setMinStars(this.minStars());
			res.setMaxStars(this.maxStars());
			res.setAvgStars(this.avgStars());
			res.setChorbiesOrderByStars(this.chorbiesOrderByStars());
			
			return res;
		}
}