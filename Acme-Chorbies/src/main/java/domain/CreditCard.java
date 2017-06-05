package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.CreditCardNumber;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Access(AccessType.PROPERTY)
public class CreditCard extends DomainEntity{

//-------------Attributes----------------------
	private String holder;
	private Brand brand;
	private String number;
	private int expirationMonth;
	private int expirationYear;
	private int cvv;
	
	
	@NotBlank
	@SafeHtml
	public String getHolder() {
		return holder;
	}
	public void setHolder(String holder) {
		this.holder = holder;
	}
	
	@Valid
	@NotNull
	public Brand getBrand() {
		return brand;
	}
	public void setBrand(Brand brand) {
		this.brand = brand;
	}
	
	@CreditCardNumber
	@SafeHtml
	@NotBlank
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	
	@Range(min = 1, max = 12)
	public int getExpirationMonth() {
		return expirationMonth;
	}
	public void setExpirationMonth(int expirationMonth) {
		this.expirationMonth = expirationMonth;
	}
	
	public int getExpirationYear() {
		return expirationYear;
	}
	public void setExpirationYear(int expirationYear) {
		this.expirationYear = expirationYear;
	}
	
	@Range(min = 100, max = 999)
	public int getCvv() {
		return cvv;
	}
	public void setCvv(int cvv) {
		this.cvv = cvv;
	}
	
	//-----------------------Relationships--------------------------
	private SuperUser superUser;

	@Valid
	@NotNull
	@OneToOne(optional=false)
	public SuperUser getSuperUser() {
		return superUser;
	}
	public void setSuperUser(SuperUser superUser) {
		this.superUser = superUser;
	}
	
	
}
