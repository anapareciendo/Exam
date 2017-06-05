package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = { @Index(columnList = "moment")})
public class MonthlyFee extends DomainEntity {

	//----------------------Attributes-------------------------
	private Date moment;
	private double amount;
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="dd/MM/yyyy hh:mm")
	public Date getMoment() {
		return moment;
	}
	public void setMoment(Date moment){
		this.moment=moment;
	}
	
	@Min(0)
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	
	//---------------------Relationships--------------------------
	private Chorbi chorbi;

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Chorbi getChorbi() {
		return chorbi;
	}
	public void setChorbi(Chorbi chorbi) {
		this.chorbi = chorbi;
	}
	
	
}