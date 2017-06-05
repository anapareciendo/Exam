package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.Range;

@Entity
@Access(AccessType.PROPERTY)
public class Config extends DomainEntity{

	//----------------------Attributes-------------------------
	private int cache;
	private double fee;
	private double registrationFee;

	//TODO
	@Range(min=0, max=10000)
	public int getCache() {
		return cache;
	}
	public void setCache(int cache) {
		this.cache = cache;
	}

	//TODO
	@Min(0)
	public double getFee() {
		return fee;
	}
	public void setFee(double fee) {
		this.fee = fee;
	}

	//TODO
	@Min(0)
	public double getRegistrationFee() {
		return registrationFee;
	}

	public void setRegistrationFee(double registrationFee) {
		this.registrationFee = registrationFee;
	}
	
}
