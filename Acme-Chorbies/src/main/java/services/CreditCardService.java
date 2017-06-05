
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.CreditCardRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.CreditCard;
import domain.SuperUser;

@Service
@Transactional
public class CreditCardService {

	//Managed repository
	@Autowired
	private CreditCardRepository	creditCardRepository;


	//Validator
	@Autowired
	private Validator validator;
	
	//Supporting services
	@Autowired
	private ChorbiService chorbiServie;
	
	@Autowired
	private ManagerService managerService;

	//Constructors
	public CreditCardService() {
		super();
	}

	//Simple CRUD methods
	public CreditCard create(SuperUser superUser) {
		CreditCard res;
		res = new CreditCard();
		res.setSuperUser(superUser);
		return res;
	}

	public Collection<CreditCard> findAll() {
		final Collection<CreditCard> res = this.creditCardRepository.findAll();
		return res;
	}

	public CreditCard findOne(final int creditCardId) {
		final CreditCard res = this.creditCardRepository.findOne(creditCardId);
		return res;
	}
	
	public CreditCard save(final CreditCard creditCard) {
		Assert.notNull(creditCard, "The config to save cannot be null.");

		final Authority a = new Authority();
		a.setAuthority(Authority.CHORBI);
		final Authority b = new Authority();
		b.setAuthority(Authority.MANAGER);
		final UserAccount ua = LoginService.getPrincipal();
		Assert.isTrue(ua.getAuthorities().contains(a) || ua.getAuthorities().contains(b), "You must to be an Chorbi or a Manager for this action");
		Assert.isTrue(creditCard.getSuperUser().getUserAccount().equals(ua), "You are not the owner of this Credit Card");

		final CreditCard res = this.creditCardRepository.save(creditCard);

		return res;
	}

	public CreditCard reconstruct(CreditCard creditCard, BindingResult binding) {
		
		CreditCard res;
		SuperUser superUser = chorbiServie.findByUserAccountId(LoginService.getPrincipal().getId());
		
		if(superUser == null)
			superUser = managerService.findByUserAccountId(LoginService.getPrincipal().getId());
		
		if(creditCard.getId()==0){
			res=this.create(superUser);
		}else{
			res=this.findOne(creditCard.getId());
		}
		
		res.setBrand(creditCard.getBrand());
		res.setCvv(creditCard.getCvv());
		res.setExpirationMonth(creditCard.getExpirationMonth());
		res.setExpirationYear(creditCard.getExpirationYear());
		res.setHolder(creditCard.getHolder());
		res.setNumber(creditCard.getNumber());

		validator.validate(res, binding);
		
		return res;
	}

}
