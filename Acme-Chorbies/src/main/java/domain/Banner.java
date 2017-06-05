package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = { @Index(columnList = "display")})
public class Banner extends DomainEntity {

	//----------------------Attributes-------------------------
	private String	logo;
	private boolean display;


	@SafeHtml
	@NotNull
	public String getLogo() {
		return this.logo;
	}
	public void setLogo(final String logo) {
		this.logo = logo;
	}
	
	
	public boolean getDisplay() {
		return this.display;
	}
	public void setDisplay(boolean display) {
		this.display = display;
	}
	
	

}
