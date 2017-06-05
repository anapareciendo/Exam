package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ChirpRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Chirp;
import domain.Chorbi;
import domain.Event;
import domain.SuperUser;

@Service
@Transactional
public class ChirpService {

	//Managed repository
	@Autowired
	private ChirpRepository	chirpRepository;


	//Validator
	@Autowired
	private Validator validator;

	//Supporting services
	@Autowired
	private ChorbiService chorbiService;
	@Autowired
	private ManagerService managerService;

	//Constructors
	public ChirpService() {
		super();
	}

	//Simple CRUD methods
	public Chirp create(final SuperUser sender, final Chorbi recipient) {
		Assert.notNull(sender, "The sender cannot be null.");
		Assert.notNull(recipient, "The recipient cannot be null.");
		Chirp res;
		res = new Chirp();
		res.setSender(sender);
		res.setRecipient(recipient);
		res.setMoment(Calendar.getInstance().getTime());
		res.setAttachments(new ArrayList<String>());
		return res;
	}

	public Collection<Chirp> findAll() {
		final Collection<Chirp> res = this.chirpRepository.findAll();
		return res;
	}

	public Chirp findOne(final int chirpId) {
		final Chirp res = this.chirpRepository.findOne(chirpId);
		return res;
	}

	public Chirp save(final Chirp chirp) {
		Assert.notNull(chirp, "The chirp to save cannot be null.");
		
		Assert.isTrue(chirp.getSender().getUserAccount().equals(LoginService.getPrincipal()), "You are not the owner of this chirp");
		
		Assert.notNull(chirp.getMoment(), "The like to save cannot have 'moment' null.");
		Assert.notNull(chirp.getSubject(), "The like to save cannot have 'subject' null.");
		Assert.isTrue(chirp.getSubject()!="", "The like to save cannot have 'subject' blank.");
		Assert.notNull(chirp.getText(), "The like to save cannot have 'text' null.");
		Assert.notNull(chirp.getAttachments(), "The like to save cannot have 'attachments' null.");
		
		final Chirp res = this.chirpRepository.save(chirp);
		res.getRecipient().getReceivedChirps().add(res);
		res.getSender().getSendChirps().add(res);
		res.setMoment(Calendar.getInstance().getTime());
		
		return res;
	}

	public void delete(final Chirp chirp) {
		Assert.notNull(chirp, "The chirp to delete cannot be null.");
		Assert.isTrue(this.chirpRepository.exists(chirp.getId()));
		Chorbi principal = chorbiService.findByUserAccountId(LoginService.getPrincipal().getId());
		Assert.isTrue(principal.getReceivedChirps().contains(chirp) || principal.getSendChirps().contains(chirp),"You are not the owner of this chirp");
		

		final UserAccount ua = LoginService.getPrincipal();
		Assert.isTrue(chirp.getSender().getUserAccount().equals(ua) || chirp.getRecipient().getUserAccount().equals(ua),"You are not the owner of the message");
		
		chirpRepository.delete(chirp);
	}
	
	//----------Other Methods------------------------
	public Collection<Chirp> findMyReceivedChirps(int uaId){
		final UserAccount ua = LoginService.getPrincipal();
		Assert.notNull(ua);
		final Authority a = new Authority();
		a.setAuthority(Authority.CHORBI);
		Assert.isTrue(ua.getAuthorities().contains(a), "You must to be a chorbi for this action.");
		return chirpRepository.findMyReceivedChirps(ua.getId());
	}
	
	public Collection<Chirp> findMySentChirps(int uaId){
		final UserAccount ua = LoginService.getPrincipal();
		Assert.notNull(ua);
		final Authority a = new Authority();
		a.setAuthority(Authority.CHORBI);
		final Authority b = new Authority();
		b.setAuthority(Authority.MANAGER);
		Assert.isTrue(ua.getAuthorities().contains(a) || ua.getAuthorities().contains(b) , "You must to be a chorbi or a manager for this action.");
		return chirpRepository.findMySentChirps(ua.getId());
	}

	public Chirp reconstruct(Chirp chirp, BindingResult binding) {
		String text = chirp.getText().replaceAll("([+][0-9]{2,})[ ]*([(][0-9]{3}[)])?[ ]*([0-9][ -]*){4,}", "***").replaceAll("([0-9a-zA-Z][_&$#]*){4,}[@][a-zA-Z]{3,}[.][a-zA-Z]{2,}", "***");
		String subject = chirp.getSubject().replaceAll("([+][0-9]{2,})[ ]*([(][0-9]{3}[)])?[ ]*([0-9][ -]*){4,}", "***").replaceAll("([0-9a-zA-Z][_&$#]*){4,}[@][a-zA-Z]{3,}[.][a-zA-Z]{2,}", "***");
		int uaId=LoginService.getPrincipal().getId();
		SuperUser sender = chorbiService.findByUserAccountId(uaId);
		if(sender ==null){
			sender=managerService.findByUserAccountId(uaId);
		}
		
		Chirp res = this.create(sender, chirp.getRecipient());
		res.getAttachments().addAll(chirp.getAttachments());
		res.setMoment(Calendar.getInstance().getTime());
		res.setSubject(subject);
		res.setText(text);
		
		validator.validate(res, binding);
		
		return res;
	}
	
	public Chirp reply(Chirp chirp, String text){
		Assert.notNull(chirp, "The chirp cannot be null.");
		Assert.notNull(text, "The text cannot be null.");
		
		Chirp aux = chirpRepository.findOne(chirp.getId());
		SuperUser principal = managerService.findByUserAccountId(LoginService.getPrincipal().getId());
		if(principal == null){
			principal = chorbiService.findByUserAccountId(LoginService.getPrincipal().getId());
			
		}
		
		Assert.isTrue(principal.getSendChirps().contains(aux),"You are not the owner of this chirp");
		Chirp res = this.create(principal, aux.getRecipient());
		
		res.getAttachments().addAll(aux.getAttachments());
		res.setSubject("RE: "+aux.getSubject());
		res.setText(aux.getText()+"<br/>RE :"+text);
		
		return res;
	}
	public Chirp forward(Chirp chirp, Chorbi chorbi){
		Assert.notNull(chirp, "The chirp cannot be null.");
		Assert.notNull(chorbi, "The chorbi cannot be null.");
		Chirp aux = chirpRepository.findOne(chirp.getId());
		
		Chorbi principal = chorbiService.findByUserAccountId(LoginService.getPrincipal().getId());
		Assert.isTrue(principal.getReceivedChirps().contains(aux) || principal.getSendChirps().contains(aux),"You are not the owner of this chirp");
		
		Chirp res = this.create(chorbiService.findByUserAccountId(LoginService.getPrincipal().getId()), chorbi);
		
		res.setText(aux.getText());
		res.setSubject("RE: "+aux.getSubject());
		res.getAttachments().addAll(aux.getAttachments());
		
		return res;
	}

	public void broadcast(Event event) {
		UserAccount ua = LoginService.getPrincipal();
		Assert.notNull(ua);
		Authority a = new Authority();
		a.setAuthority(Authority.MANAGER);
		Assert.isTrue(ua.getAuthorities().contains(a), "You must to be a manager for this action.");
		
		List<Chorbi> chorbies = new ArrayList<Chorbi>();
		chorbies.addAll(chorbiService.findEvent(event.getId()));
		
		for(Chorbi c:chorbies){
			Chirp s = this.create(managerService.findByUserAccountId(ua.getId()), c);
			s.setSubject("Event canceled");
			s.setText("The event '"+event.getTitle()+"' has been canceled");
			this.save(s);
		}
	}
}