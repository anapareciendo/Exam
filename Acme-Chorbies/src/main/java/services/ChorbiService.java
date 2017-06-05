package services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ChorbiRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;
import domain.Chirp;
import domain.Chorbi;
import domain.Coordinates;
import domain.Event;
import domain.Genre;
import domain.KindRelationship;
import domain.Likes;
import domain.MonthlyFee;
import forms.ChorbiForm;

@Service
@Transactional
public class ChorbiService {

	//Managed repository
	@Autowired
	private ChorbiRepository	chorbiRepository;


	//Validator
	@Autowired
	private Validator validator;
	
	//Supporting services
	@Autowired
	private UserAccountService userAccountService;

	//Constructors
	public ChorbiService() {
		super();
	}

	//Simple CRUD methods
	public Chorbi create(final UserAccount ua) {
		Chorbi res;
		res = new Chorbi();
		res.setSendChirps(new ArrayList<Chirp>());
		res.setMakeLikes(new ArrayList<Likes>());
		res.setReceivedLikes(new ArrayList<Likes>());
		res.setReceivedChirps(new ArrayList<Chirp>());
		res.setMonthlyFee(new ArrayList<MonthlyFee>());
		res.setAge(18);
		res.setBanned(false);
		res.setUserAccount(ua);
		res.setEvents(new ArrayList<Event>());
		return res;
	}

	public Collection<Chorbi> findAll() {
		final Collection<Chorbi> res = this.chorbiRepository.findAll();
		return res;
	}

	public Chorbi findOne(final int chorbiId) {
		final Chorbi res = this.chorbiRepository.findOne(chorbiId);
		return res;
	}

	public Chorbi save(final Chorbi chorbi) {
		Assert.notNull(chorbi, "The chorbi to save cannot be null.");
		
//		Assert.notNull(chorbi.getKindRelationship(), "The chorbi to save cannot have 'kindRelationship' null.");
//		Assert.notNull(chorbi.getBirthDate(), "The chorbi to save cannot have 'BirthDate' null.");
//		Assert.notNull(chorbi.getGenre(), "The chorbi to save cannot have 'Genre' null.");
//		Assert.notNull(chorbi.getBirthDate(), "The chorbi to save cannot have 'BirthDate' null.");
//		Assert.notNull(chorbi.getCoordinates(), "The chorbi to save cannot have 'BirthDate' null.");
		long edad = chorbi.getBirthDate().getTime();
		long actual = Calendar.getInstance().getTimeInMillis();
		long finale = actual-edad;
		long real = finale/31540000000l;//3,154e+10 ms -> 1 año
		Assert.isTrue(real>=18, "You must be over 18 years old");
		chorbi.setAge(real); 
		
		final Chorbi res = this.chorbiRepository.save(chorbi);
		chorbiRepository.flush();
		return res;
	}

	public void delete(final Chorbi chorbi) {
		final UserAccount ua = LoginService.getPrincipal();
		Assert.notNull(ua);
		final Authority a = new Authority();
		a.setAuthority(Authority.ADMIN);
		Assert.isTrue(ua.getAuthorities().contains(a), "You must to be a admin to delete an actor.");

		Assert.notNull(chorbi, "The chorbi to delete cannot be null.");
		Assert.isTrue(this.chorbiRepository.exists(chorbi.getId()));

		Assert.isNull(chorbi.getSendChirps().isEmpty(), "The chorbi cannot be delete with send chirps");
		Assert.isNull(chorbi.getReceivedLikes().isEmpty(), "The chorbi cannot be delete with receive likes");
		Assert.isNull(chorbi.getReceivedChirps().isEmpty(), "The chorbi cannot be delete with receive chirps");
		Assert.isNull(chorbi.getMakeLikes().isEmpty(), "The chorbi cannot be delete with sender make likes");
		
		this.chorbiRepository.delete(chorbi);
	}

	//Utilites methods
	public Chorbi findByUserAccountId(final int id) {
		Assert.notNull(id);
		return this.chorbiRepository.findByUserAccountId(id);
		}

	public Chorbi reconstruct(ChorbiForm actor, BindingResult binding) {
		Chorbi result;
		List<String> cond = Arrays.asList(actor.getConditions());
		if(!actor.getPassword1().isEmpty() && !actor.getPassword2().isEmpty() && actor.getPassword1().equals(actor.getPassword2()) && cond.contains("acepto")){
			UserAccount ua = userAccountService.create();
			
			Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			String hash = encoder.encodePassword(actor.getPassword1(), null);
			
			ua.setUsername(actor.getUsername());
			ua.setPassword(hash);
			
			Authority a = new Authority();
			a.setAuthority(Authority.CHORBI);
			ua.getAuthorities().add(a);
			
			Coordinates coor= new Coordinates(actor.getCountry(), actor.getCity(), actor.getState(), actor.getProvince());
			
			result=this.create(ua);
			
			result.setName(actor.getName());
			result.setSurname(actor.getSurname());
			result.setEmail(actor.getEmail());
			result.setPhone(actor.getPhone());
			result.setPicture(actor.getPicture());
			result.setBirthDate(actor.getBirthDate());
			
			switch(actor.getGenre()){
			case 0: result.setGenre(Genre.WOMEN);
			break;
			case 1: result.setGenre(Genre.MAN);
			break;
			case 2: result.setGenre(Genre.OTHER);
			break;
			}
			switch(actor.getKindRelationship()){
			case 0: result.setKindRelationship(KindRelationship.ACTIVITIES);
			break;
			case 1: result.setKindRelationship(KindRelationship.FRIENDSHIP);
			break;
			case 2: result.setKindRelationship(KindRelationship.LOVE);
			break;
			}
			result.setCoordinates(coor);
			
			validator.validate(result, binding);
		}else{
			result=new Chorbi();
			result.setName("Pass");
			if(!cond.contains("acepto")){
				result.setName("Cond");
			}
		}
		return result;
	} 
	
	public Collection<Chorbi> findMyLikes(final int likedId) {
		Assert.notNull(likedId);
		return this.chorbiRepository.findMyLikes(likedId);
	} 
	
	public Collection<Chorbi> findNotBanned(){
		return this.chorbiRepository.findNotBanned();
	}

	public Collection<Chorbi> findEvent(int eventId){
		return this.chorbiRepository.findEvent(eventId);
	}
	
	public Chorbi ban(Chorbi chorbi) {
		final UserAccount ua = LoginService.getPrincipal();
		Assert.notNull(ua);
		final Authority a = new Authority();
		a.setAuthority(Authority.ADMIN);
		Assert.isTrue(ua.getAuthorities().contains(a), "You must to be a admin to delete an actor.");
		
		Authority b = new Authority();
		b.setAuthority(Authority.BANNED);
		
		chorbi.setBanned(true);
		chorbi.getUserAccount().getAuthorities().clear();
		chorbi.getUserAccount().getAuthorities().add(b);
		
		return chorbi;
	}

	public Chorbi unban(Chorbi chorbi) {
		final UserAccount ua = LoginService.getPrincipal();
		Assert.notNull(ua);
		final Authority a = new Authority();
		a.setAuthority(Authority.ADMIN);
		Assert.isTrue(ua.getAuthorities().contains(a), "You must to be a admin to delete an actor.");
		
		Authority b = new Authority();
		b.setAuthority(Authority.CHORBI);
		
		chorbi.setBanned(false);
		chorbi.getUserAccount().getAuthorities().clear();
		chorbi.getUserAccount().getAuthorities().add(b);
		
		return chorbi;
	}
	
	public Chorbi reconstructEdit(ChorbiForm chorbi, BindingResult binding) {
		UserAccount ua= userAccountService.findOne(LoginService.getPrincipal().getId());
		Chorbi res = this.create(ua);
		Coordinates coor = new Coordinates(chorbi.getCoordinates().getCountry(), 
				chorbi.getCoordinates().getCity(), chorbi.getCoordinates().getState(), 
				chorbi.getCoordinates().getProvince());
		
		res.setCreditCard(chorbi.getCreditCard());
		res.setName(chorbi.getName());
		res.setSurname(chorbi.getSurname());
		res.setEmail(chorbi.getEmail());
		res.setPhone(chorbi.getPhone());
		res.setPicture(chorbi.getPicture());
		res.setBirthDate(chorbi.getBirthDate());
		switch(chorbi.getKindRelationship()){
		case 0: res.setKindRelationship(KindRelationship.ACTIVITIES);
		break;
		case 1: res.setKindRelationship(KindRelationship.FRIENDSHIP);
		break;
		case 2: res.setKindRelationship(KindRelationship.LOVE);
		break;
		}

		switch(chorbi.getGenre()){
		case 0: res.setGenre(Genre.WOMEN);
		break;
		case 1: res.setGenre(Genre.MAN);
		break;
		case 2: res.setGenre(Genre.OTHER);
		break;
		}
		res.setCoordinates(coor);
		
		validator.validate(res, binding);
		
		return res;
	}

	public Chorbi realReconstruct(ChorbiForm chorbi) {
		Chorbi  res = this.findByUserAccountId(LoginService.getPrincipal().getId());
		Coordinates coor = new Coordinates(chorbi.getCoordinates().getCountry(), 
				chorbi.getCoordinates().getCity(), chorbi.getCoordinates().getState(), 
				chorbi.getCoordinates().getProvince());
		
		res.setCreditCard(chorbi.getCreditCard());
		res.setName(chorbi.getName());
		res.setSurname(chorbi.getSurname());
		res.setEmail(chorbi.getEmail());
		res.setPhone(chorbi.getPhone());
		res.setPicture(chorbi.getPicture());
		res.setBirthDate(chorbi.getBirthDate());
		switch(chorbi.getKindRelationship()){
		case 0: res.setKindRelationship(KindRelationship.ACTIVITIES);
		break;
		case 1: res.setKindRelationship(KindRelationship.FRIENDSHIP);
		break;
		case 2: res.setKindRelationship(KindRelationship.LOVE);
		break;
		}

		switch(chorbi.getGenre()){
		case 0: res.setGenre(Genre.WOMEN);
		break;
		case 1: res.setGenre(Genre.MAN);
		break;
		case 2: res.setGenre(Genre.OTHER);
		break;
		}
		res.setCoordinates(coor);
		
		return this.save(res);
	}
}