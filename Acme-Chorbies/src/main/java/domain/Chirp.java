package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Chirp extends DomainEntity {

	//----------------------Attributes-------------------------
	private Date moment;
	private String subject;
	private String text;
	private Collection<String> attachments;
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="dd/MM/yyyy hh:mm")
	public Date getMoment() {
		return moment;
	}
	public void setMoment(Date moment){
		this.moment=moment;
	}
	
	@NotBlank
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject){
		this.subject=subject;
	}
	
	@NotNull
	public String getText() {
		return text;
	}
	public void setText(String text){
		this.text=text;
	}
	
	@ElementCollection
	@NotNull
	public Collection<String> getAttachments() {
		return attachments;
	}
	
	public void setAttachments(Collection<String> attachments) {
		this.attachments = attachments;
	}


	//---------------------Relationships--------------------------
	private SuperUser sender;
	private Chorbi recipient;

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public SuperUser getSender() {
		return sender;
	}
	public void setSender(SuperUser sender) {
		this.sender = sender;
	}
	
	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Chorbi getRecipient() {
		return recipient;
	}
	public void setRecipient(Chorbi recipient) {
		this.recipient = recipient;
	}
	
	
}
