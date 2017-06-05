package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = { @Index(columnList = "amount")})
public class Manager extends SuperUser{

	//----------------------Attributes-------------------------
	private String company;
	private String VATNumber;
	private double amount;
	
	@NotBlank
	@SafeHtml
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	@NotBlank
	@SafeHtml
	public String getVATNumber() {
		return VATNumber;
	}
	public void setVATNumber(String vATNumber) {
		VATNumber = vATNumber;
	}
	
	@Min(value=0)
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}

	//---------------------Relationships--------------------------
	private Collection<Event> events;
	private Broadcast broadcast;
	
	@Valid
	@OneToOne(optional=true)
	public Broadcast getBroadcast() {
		return broadcast;
	}
	public void setBroadcast(Broadcast broadcast) {
		this.broadcast = broadcast;
	}
	
	@NotNull
	@Valid
	@OneToMany(mappedBy="manager")
	public Collection<Event> getEvents() {
		return events;
	}
	public void setEvents(Collection<Event> events) {
		this.events = events;
	}
	
	//Utility Methods
	public String toString(){
		return this.getName()+" "+this.getSurname();
	}
	
}
