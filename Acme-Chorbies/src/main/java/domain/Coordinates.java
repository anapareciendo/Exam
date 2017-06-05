package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import javax.persistence.Index;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

@Embeddable
@Access(AccessType.PROPERTY)
@Table(indexes = { @Index(columnList = "country, city, state, province")})
public class Coordinates {
	
	private String country;
	private String city;
	private String state;
	private String province;
	
	public Coordinates() {
		super();
	}

	public Coordinates(String country, String city, String state,
			String province) {
		super();
		this.country = country;
		this.city = city;
		this.state = state;
		this.province = province;
	}
	


	@NotBlank
	@SafeHtml
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
	@NotBlank
	@SafeHtml
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	@SafeHtml
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	@SafeHtml
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	
	

}
