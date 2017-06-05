package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = { @Index(columnList = "banned, genre, name, surname")})
public class Chorbi extends SuperUser{

	//----------------------Attributes-------------------------
	private String picture;
	private KindRelationship kindRelationship;
	private Date birthDate;
	private long age;
	private Genre genre;
	private Coordinates coordinates;
	private boolean banned;
	
	public boolean getBanned() {
		return banned;
	}
	public void setBanned(boolean banned) {
		this.banned = banned;
	}
	@NotBlank
	@URL
	@SafeHtml
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	
	@Valid
	@NotNull
	public KindRelationship getKindRelationship() {
		return kindRelationship;
	}
	public void setKindRelationship(KindRelationship kindRelationship) {
		this.kindRelationship = kindRelationship;
	}
	
	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern="dd/MM/yyyy")
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	
	@Valid
	@NotNull
	public Genre getGenre() {
		return genre;
	}
	public void setGenre(Genre genre) {
		this.genre = genre;
	}
	
	@NotNull
	@Valid
	public Coordinates getCoordinates() {
		return coordinates;
	}
	public void setCoordinates(Coordinates coordinates) {
		this.coordinates = coordinates;
	}

	
	@Min(18)
	public long getAge() {
		return age;
	}
	public void setAge(long age) {
		this.age = age;
	}

	//---------------------Relationships--------------------------
	private Collection<Chirp> receivedChirps;
	private Collection<Likes> makeLikes;
	private Collection<Likes> receivedLikes;
	private SearchTemplate searchTemplate;
	private Collection<Event> events;
	private Collection<MonthlyFee> monthlyFee;
	
	@Valid
	@OneToOne(optional=true)
	public SearchTemplate getSearchTemplate() {
		return searchTemplate;
	}
	public void setSearchTemplate(SearchTemplate searchTemplate) {
		this.searchTemplate = searchTemplate;
	}
	
	@NotNull
	@Valid
	@OneToMany(mappedBy="recipient")
	public Collection<Chirp> getReceivedChirps() {
		return receivedChirps;
	}
	public void setReceivedChirps(Collection<Chirp> receivedChirps) {
		this.receivedChirps = receivedChirps;
	}
	
	@NotNull
	@Valid
	@OneToMany(mappedBy="liker")
	public Collection<Likes> getMakeLikes() {
		return makeLikes;
	}
	public void setMakeLikes(Collection<Likes> makeLikes) {
		this.makeLikes = makeLikes;
	}
	
	@NotNull
	@Valid
	@OneToMany(mappedBy="liked")
	public Collection<Likes> getReceivedLikes() {
		return receivedLikes;
	}
	public void setReceivedLikes(Collection<Likes> receivedLikes) {
		this.receivedLikes = receivedLikes;
	}
	
//	@Valid
//	@NotNull
//	@ManyToMany(
//			targetEntity=Event.class, 
//			cascade={javax.persistence.CascadeType.PERSIST, javax.persistence.CascadeType.MERGE}
//			)
//	@JoinTable(
//			name="chorbi_event",
//			joinColumns=@JoinColumn(name="chorbi_id"),
//			inverseJoinColumns=@JoinColumn(name="event_id")
//			)
	
	@Valid
	@NotNull
	@ManyToMany(
			cascade={javax.persistence.CascadeType.PERSIST, javax.persistence.CascadeType.MERGE},
			mappedBy="chorbies",
			targetEntity=Event.class
			)
	
	public Collection<Event> getEvents() {
		return events;
	}
	public void setEvents(Collection<Event> events) {
		this.events = events;
	}
	
	@NotNull
	@Valid
	@OneToMany(mappedBy="chorbi")
	public Collection<MonthlyFee> getMonthlyFee() {
		return monthlyFee;
	}
	public void setMonthlyFee(Collection<MonthlyFee> monthlyFee) {
		this.monthlyFee = monthlyFee;
	}
	//Utility Methods
	public String toString(){
		return this.getName()+" "+this.getSurname();
	}
	
}
