package forms;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;

import domain.Chorbi;
import domain.Manager;

@Access(AccessType.PROPERTY)
public class Dashboard {

	//---LEVEL C---
	private Collection<Integer> numChorbiesPerCity;
	private Collection<Integer> numChorbiesPerCountry;
	private Integer minAgeChorbies;
	private Integer maxAgeChorbies;
	private Double avgAgeChorbies;
	private Double ratioChorbiesCreditCard;
	private Double ratioChorbisWhoSearchActivities;
	private Double ratioChorbisWhoSearchFriendship;
	private Double ratioChorbisWhoSearchLove;
	
	private Collection<Manager> listManagersOrderByEvents;
	private Collection<Manager> listManagersOrderByAmount;
	private Collection<Chorbi> listChorbiesOrderyByEvents;
	private Collection<Double> listChorbiesOrderByAmount;
		
	//---LEVEL B---
	private Collection<Chorbi> chorbiesSortedByLikes;
	private Integer minLikesPerChorbi;
	private Integer maxLikesPerChorbi;
	private Double avgLikesPerChorbi;
	
	private Collection<Integer> minStars;
	private Collection<Integer> maxStars;
	private Collection<Double> avgStars;
	private Collection<Chorbi> chorbiesOrderByStars;
			
	//---LEVEL A---
	private Integer minChirpsReceived;
	private Integer maxChirpsReceived;
	private Double avgChirpsReceived;
	private Integer minChirpsSend;
	private Integer maxChirpsSend;
	private Double avgChirpsSend;
	private Collection<Chorbi> chorbiesMoreChirpsReceived;
	private Collection<Chorbi> chorbiesMoreChirpsSent;
	
	
	public Collection<Integer> getNumChorbiesPerCity() {
		return numChorbiesPerCity;
	}
	public void setNumChorbiesPerCity(Collection<Integer> numChorbiesPerCity) {
		this.numChorbiesPerCity = numChorbiesPerCity;
	}
	public Collection<Integer> getNumChorbiesPerCountry() {
		return numChorbiesPerCountry;
	}
	public void setNumChorbiesPerCountry(Collection<Integer> numChorbiesPerCountry) {
		this.numChorbiesPerCountry = numChorbiesPerCountry;
	}
	public Integer getMinAgeChorbies() {
		return minAgeChorbies;
	}
	public void setMinAgeChorbies(Integer minAgeChorbies) {
		this.minAgeChorbies = minAgeChorbies;
	}
	public Integer getMaxAgeChorbies() {
		return maxAgeChorbies;
	}
	public void setMaxAgeChorbies(Integer maxAgeChorbies) {
		this.maxAgeChorbies = maxAgeChorbies;
	}
	public Double getAvgAgeChorbies() {
		return avgAgeChorbies;
	}
	public void setAvgAgeChorbies(Double avgAgeChorbies) {
		this.avgAgeChorbies = avgAgeChorbies;
	}
	public Double getRatioChorbiesCreditCard() {
		return ratioChorbiesCreditCard;
	}
	public void setRatioChorbiesCreditCard(Double ratioChorbiesCreditCard) {
		this.ratioChorbiesCreditCard = ratioChorbiesCreditCard;
	}
	public Double getRatioChorbisWhoSearchActivities() {
		return ratioChorbisWhoSearchActivities;
	}
	public void setRatioChorbisWhoSearchActivities(
			Double ratioChorbisWhoSearchActivities) {
		this.ratioChorbisWhoSearchActivities = ratioChorbisWhoSearchActivities;
	}
	public Double getRatioChorbisWhoSearchFriendship() {
		return ratioChorbisWhoSearchFriendship;
	}
	public void setRatioChorbisWhoSearchFriendship(
			Double ratioChorbisWhoSearchFriendship) {
		this.ratioChorbisWhoSearchFriendship = ratioChorbisWhoSearchFriendship;
	}
	public Double getRatioChorbisWhoSearchLove() {
		return ratioChorbisWhoSearchLove;
	}
	public void setRatioChorbisWhoSearchLove(Double ratioChorbisWhoSearchLove) {
		this.ratioChorbisWhoSearchLove = ratioChorbisWhoSearchLove;
	}
	public Collection<Chorbi> getChorbiesSortedByLikes() {
		return chorbiesSortedByLikes;
	}
	public void setChorbiesSortedByLikes(Collection<Chorbi> chorbiesSortedByLikes) {
		this.chorbiesSortedByLikes = chorbiesSortedByLikes;
	}
	public Integer getMinLikesPerChorbi() {
		return minLikesPerChorbi;
	}
	public void setMinLikesPerChorbi(Integer minLikesPerChorbi) {
		this.minLikesPerChorbi = minLikesPerChorbi;
	}
	public Integer getMaxLikesPerChorbi() {
		return maxLikesPerChorbi;
	}
	public void setMaxLikesPerChorbi(Integer maxLikesPerChorbi) {
		this.maxLikesPerChorbi = maxLikesPerChorbi;
	}
	public Double getAvgLikesPerChorbi() {
		return avgLikesPerChorbi;
	}
	public void setAvgLikesPerChorbi(Double avgLikesPerChorbi) {
		this.avgLikesPerChorbi = avgLikesPerChorbi;
	}
	public Integer getMinChirpsReceived() {
		return minChirpsReceived;
	}
	public void setMinChirpsReceived(Integer minChirpsReceived) {
		this.minChirpsReceived = minChirpsReceived;
	}
	public Integer getMaxChirpsReceived() {
		return maxChirpsReceived;
	}
	public void setMaxChirpsReceived(Integer maxChirpsReceived) {
		this.maxChirpsReceived = maxChirpsReceived;
	}
	public Double getAvgChirpsReceived() {
		return avgChirpsReceived;
	}
	public void setAvgChirpsReceived(Double avgChirpsReceived) {
		this.avgChirpsReceived = avgChirpsReceived;
	}
	public Integer getMinChirpsSend() {
		return minChirpsSend;
	}
	public void setMinChirpsSend(Integer minChirpsSend) {
		this.minChirpsSend = minChirpsSend;
	}
	public Integer getMaxChirpsSend() {
		return maxChirpsSend;
	}
	public void setMaxChirpsSend(Integer maxChirpsSend) {
		this.maxChirpsSend = maxChirpsSend;
	}
	public Double getAvgChirpsSend() {
		return avgChirpsSend;
	}
	public void setAvgChirpsSend(Double avgChirpsSend) {
		this.avgChirpsSend = avgChirpsSend;
	}
	public Collection<Chorbi> getChorbiesMoreChirpsReceived() {
		return chorbiesMoreChirpsReceived;
	}
	public void setChorbiesMoreChirpsReceived(
			Collection<Chorbi> chorbiesMoreChirpsReceived) {
		this.chorbiesMoreChirpsReceived = chorbiesMoreChirpsReceived;
	}
	public Collection<Chorbi> getChorbiesMoreChirpsSent() {
		return chorbiesMoreChirpsSent;
	}
	public void setChorbiesMoreChirpsSent(Collection<Chorbi> chorbiesMoreChirpsSent) {
		this.chorbiesMoreChirpsSent = chorbiesMoreChirpsSent;
	}
	
	public Collection<Manager> getListManagersOrderByEvents() {
		return listManagersOrderByEvents;
	}
	public void setListManagersOrderByEvents(
			Collection<Manager> listManagersOrderByEvents) {
		this.listManagersOrderByEvents = listManagersOrderByEvents;
	}
	public Collection<Manager> getListManagersOrderByAmount() {
		return listManagersOrderByAmount;
	}
	public void setListManagersOrderByAmount(
			Collection<Manager> listManagersOrderByAmount) {
		this.listManagersOrderByAmount = listManagersOrderByAmount;
	}
	public Collection<Chorbi> getListChorbiesOrderyByEvents() {
		return listChorbiesOrderyByEvents;
	}
	public void setListChorbiesOrderyByEvents(
			Collection<Chorbi> listChorbiesOrderyByEvents) {
		this.listChorbiesOrderyByEvents = listChorbiesOrderyByEvents;
	}
	public Collection<Double> getListChorbiesOrderByAmount() {
		return listChorbiesOrderByAmount;
	}
	public void setListChorbiesOrderByAmount(
			Collection<Double> listChorbiesOrderByAmount) {
		this.listChorbiesOrderByAmount = listChorbiesOrderByAmount;
	}
	public Collection<Integer> getMinStars() {
		return minStars;
	}
	public void setMinStars(Collection<Integer> minStars) {
		this.minStars = minStars;
	}
	public Collection<Integer> getMaxStars() {
		return maxStars;
	}
	public void setMaxStars(Collection<Integer> maxStars) {
		this.maxStars = maxStars;
	}
	public Collection<Double> getAvgStars() {
		return avgStars;
	}
	public void setAvgStars(Collection<Double> avgStars) {
		this.avgStars = avgStars;
	}
	public Collection<Chorbi> getChorbiesOrderByStars() {
		return chorbiesOrderByStars;
	}
	public void setChorbiesOrderByStars(Collection<Chorbi> chorbiesOrderByStars) {
		this.chorbiesOrderByStars = chorbiesOrderByStars;
	}
	

}