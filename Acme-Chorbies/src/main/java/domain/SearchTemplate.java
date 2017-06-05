package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = { @Index(columnList = "kindRelationship, aproximateAge, genre, keyword")})
public class SearchTemplate extends DomainEntity{
	
	//----------------------Attributes-------------------------
	private KindRelationship kindRelationship;
	private Integer aproximateAge;
	private Genre genre;
	private String keyword;
	private Coordinates coordinates;
	private Date moment;
	
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="dd/MM/yyy HH:mm")
	public Date getMoment() {
		return moment;
	}
	public void setMoment(Date moment) {
		this.moment = moment;
	}
	@Valid
	public Coordinates getCoordinates() {
		return coordinates;
	}
	public void setCoordinates(Coordinates coordinates) {
		this.coordinates = coordinates;
	}
	
	@Valid
	@NotNull
	public KindRelationship getKindRelationship() {
		return kindRelationship;
	}
	public void setKindRelationship(KindRelationship kindRelationship) {
		this.kindRelationship = kindRelationship;
	}
	
	@Min(18)
	public Integer getAproximateAge() {
		return aproximateAge;
	}
	public void setAproximateAge(Integer aproximateAge) {
		this.aproximateAge = aproximateAge;
	}
	
	@Valid
	@NotNull
	public Genre getGenre() {
		return genre;
	}
	public void setGenre(Genre genre) {
		this.genre = genre;
	}
	
	@SafeHtml
	@NotNull
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
	//-------------Relationships-----------------------
	private Collection<Chorbi> results;

	@Valid
	@NotNull
	@OneToMany()
	public Collection<Chorbi> getResults() {
		return results;
	}
	public void setResults(Collection<Chorbi> results) {
		this.results = results;
	}
	
	
	
	

}
