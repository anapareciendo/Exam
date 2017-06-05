package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Broadcast extends DomainEntity {

	//----------------------Attributes-------------------------
	private String title;
	private String text;
	
	@NotNull
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	@NotNull
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}

	//---------------------Relationships--------------------------
}
