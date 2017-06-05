package services;

import java.util.Calendar;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.LikesRepository;
import security.LoginService;
import security.UserAccount;
import domain.Chorbi;
import domain.Likes;

@Service
@Transactional
public class LikesService {

	//Managed repository
	@Autowired
	private LikesRepository	likesRepository;


	//Validator
	@Autowired
	private Validator validator;

	//Supporting services
	@Autowired
	private ChorbiService chorbiService;

	//Constructors
	public LikesService() {
		super();
	}

	//Simple CRUD methods
	public Likes create(final Chorbi liker, final Chorbi liked) {
		Likes res;
		res = new Likes();
		res.setLiked(liked);
		res.setLiker(liker);
		res.setMoment(Calendar.getInstance().getTime());
		return res;
	}

	public Collection<Likes> findAll() {
		final Collection<Likes> res = this.likesRepository.findAll();
		return res;
	}

	public Likes findOne(final int likesId) {
		final Likes res = this.likesRepository.findOne(likesId);
		return res;
	}

	public Likes save(final Likes likes) {
		Assert.notNull(likes, "The like to save cannot be null.");
		
		Assert.notNull(likes.getMoment(), "The like to save cannot have 'moment' null.");
		Assert.notNull(likes.getLiked(), "The like to save cannot have 'liked' null.");
		Assert.notNull(likes.getLiker(), "The like to save cannot have 'liker' null.");
		
		//TODO
//		likes.setStars(0);
		
		final Likes res = this.likesRepository.save(likes);
		
		res.getLiked().getReceivedLikes().add(res);
		res.getLiker().getMakeLikes().add(res);
		res.setMoment(Calendar.getInstance().getTime());
		
		
		return res;
	}

	public void delete(final Likes likes) {
		Assert.notNull(likes, "The likes to delete cannot be null.");
		Assert.isTrue(this.likesRepository.exists(likes.getId()));
		

		final UserAccount ua = LoginService.getPrincipal();
		Assert.isTrue(likes.getLiker().getUserAccount().equals(ua),"You are not the owner of the likes" );
		
		this.likesRepository.delete(likes);
	}

	//----------------Other Methods---------------
	public Likes reconstruct(Likes likes, BindingResult binding) {
		String ms = likes.getComment().replaceAll("([+][0-9]{2,})[ ]*([(][0-9]{3}[)])?[ ]*([0-9][ -]*){4,}", "***").replaceAll("([0-9a-zA-Z][_&$#]*){4,}[@][a-zA-Z]{3,}[.][a-zA-Z]{2,}", "***");
		Chorbi liker = chorbiService.findByUserAccountId(LoginService.getPrincipal().getId());
		Likes res = this.create(liker, likes.getLiked());
		res.setComment(ms);
		res.setStars(likes.getStars());
		
		validator.validate(res, binding);
		
		return res;
	}



}