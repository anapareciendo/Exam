package forms;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;

import domain.Coordinates;
import domain.CreditCard;

@Access(AccessType.PROPERTY)
public class ChorbiForm {

	private String username;
	private String password1;
	private String password2;
	
	private String name;
	private String surname;
	private String email;
	private String phone;
	
	private String picture;
	private Integer kindRelationship;
	private Date birthDate;
	private Integer genre;
	
	private Coordinates coordinates;
	private CreditCard creditCard;
	
	private String country;
	private String city;
	private String state;
	private String province;
	
	private String[] conditions={};
	
	public ChorbiForm() {
		super();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword1() {
		return password1;
	}

	public void setPassword1(String password1) {
		this.password1 = password1;
	}

	public String getPassword2() {
		return password2;
	}

	public void setPassword2(String password2) {
		this.password2 = password2;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public Integer getKindRelationship() {
		return kindRelationship;
	}

	public void setKindRelationship(Integer kindRelationship) {
		this.kindRelationship = kindRelationship;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public Integer getGenre() {
		return genre;
	}

	public void setGenre(Integer genre) {
		this.genre = genre;
	}

	public String[] getConditions() {
		return conditions;
	}

	public void setConditions(String[] conditions) {
		this.conditions = conditions;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public Coordinates getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(Coordinates coordinates) {
		this.coordinates = coordinates;
	}

	public CreditCard getCreditCard() {
		return creditCard;
	}

	public void setCreditCard(CreditCard creditCard) {
		this.creditCard = creditCard;
	}
	
	
	
	
}