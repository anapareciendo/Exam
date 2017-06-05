package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ManagerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Chirp;
import domain.Event;
import domain.Manager;

@Service
@Transactional
public class ManagerService {

	//Managed repository
	@Autowired
	private ManagerRepository	managerRepository;


	//Validator
//	@Autowired
//	private Validator validator;
	
	//Supporting services
	@Autowired
	private	ConfigService configService;
	
	//Constructors
	public ManagerService() {
		super();
	}

	//Simple CRUD methods
	public Manager create(final UserAccount ua) {
		Manager res;
		res = new Manager();
		res.setSendChirps(new ArrayList<Chirp>());
		res.setEvents(new ArrayList<Event>());
		res.setUserAccount(ua);
		return res;
	}

	public Collection<Manager> findAll() {
		final Collection<Manager> res = this.managerRepository.findAll();
		return res;
	}

	public Manager findOne(final int managerId) {
		final Manager res = this.managerRepository.findOne(managerId);
		return res;
	}

	public Manager save(final Manager manager) {
		Assert.notNull(manager, "The manager to save cannot be null.");
		
//		Assert.notNull(chorbi.getKindRelationship(), "The chorbi to save cannot have 'kindRelationship' null.");
//		Assert.notNull(chorbi.getBirthDate(), "The chorbi to save cannot have 'BirthDate' null.");
//		Assert.notNull(chorbi.getGenre(), "The chorbi to save cannot have 'Genre' null.");
//		Assert.notNull(chorbi.getBirthDate(), "The chorbi to save cannot have 'BirthDate' null.");
//		Assert.notNull(chorbi.getCoordinates(), "The chorbi to save cannot have 'BirthDate' null.");
	
		
		final Manager res = this.managerRepository.save(manager);
//		managerRepository.flush();
		return res;
	}

	public void delete(final Manager manager) {
		final UserAccount ua = LoginService.getPrincipal();
		Assert.notNull(ua);
		final Authority a = new Authority();
		a.setAuthority(Authority.ADMIN);
		Assert.isTrue(ua.getAuthorities().contains(a), "You must to be a admin to delete an actor.");

		Assert.notNull(manager, "The manager to delete cannot be null.");
		Assert.isTrue(this.managerRepository.exists(manager.getId()));

		Assert.isNull(manager.getSendChirps().isEmpty(), "The manager cannot be delete with send chirps");
		Assert.isNull(manager.getEvents().isEmpty(), "The manager cannot be delete with events");		
		this.managerRepository.delete(manager);
	}

	//Utilites methods
	public Manager findByUserAccountId(final int id) {
		Assert.notNull(id);
		return this.managerRepository.findByUserAccountId(id);
		}

	public void eventFee() {
		Manager manager = this.findByUserAccountId(LoginService.getPrincipal().getId());
		manager.setAmount(manager.getAmount()+configService.find().getFee());
		//TODO
//		manager.setAmount(manager.getAmount()+0);
		this.save(manager);
	}

}