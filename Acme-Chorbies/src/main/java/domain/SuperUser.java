package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public abstract class SuperUser extends Actor{

	//----------------------Attributes-------------------------

	//---------------------Relationships--------------------------
	private Collection<Chirp> sendChirps;
	private CreditCard creditCard;
	
	@Valid
	@OneToOne(optional=true)
	public CreditCard getCreditCard() {
		return creditCard;
	}
	public void setCreditCard(CreditCard creditCard) {
		this.creditCard = creditCard;
	}
	
	@NotNull
	@Valid
	@OneToMany(mappedBy="sender")
	public Collection<Chirp> getSendChirps() {
		return sendChirps;
	}
	public void setSendChirps(Collection<Chirp> sendChirps) {
		this.sendChirps = sendChirps;
	}
	
	//Utility Methods
	public String toString(){
		return this.getName()+" "+this.getSurname();
	}
	
}
