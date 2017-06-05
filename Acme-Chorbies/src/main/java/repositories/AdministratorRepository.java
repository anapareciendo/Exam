package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Administrator;
import domain.Chorbi;
import domain.Manager;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, Integer> {

	@Query("select a from Administrator a where a.userAccount.id = ?1")
	Administrator findByUserAccountId(int id);
	
	//---LEVEL C.1---
	
		//A listing with the number of chorbies per city
		@Query("select count(c) from Chorbi c group by c.coordinates.city")
		Collection<Integer> numChorbiesPerCity();
		
		//A listing with the number of chorbies per country
		@Query("select count(c) from Chorbi c group by c.coordinates.country")
		Collection<Integer> numChorbiesPerCountry();
		
		//The minimum ages of the chorbies
		@Query("select max(c.age) from Chorbi c")
		Integer minAgeChorbies();
		
		//The maximum ages of the chorbies
		@Query("select min(c.age) from Chorbi c")
		Integer maxAgeChorbies();
		
		//The average ages of the chorbies
		@Query("select avg(c.age) from Chorbi c")
		Double avgAgeChorbies();
		
//		The ratio of chorbies who have not registered a credit card or have registered an invalid credit card
		@Query("select count(c) from Chorbi c where c.creditCard is null")
		Integer chorbisWithCreditCard();
		
		@Query("select count(c) from Chorbi c where c.creditCard is not null")
		Integer chorbisWithoutCreditCard();
		
//		The ratios of chorbies who search for activities, friendship, and love.
		@Query("select count(c) from Chorbi c where c.searchTemplate is not null and c.searchTemplate.kindRelationship=1 and c.banned=false")
		Integer chorbisWhoSearchActivities();
		@Query("select count(c) from Chorbi c where c.searchTemplate is not null and c.searchTemplate.kindRelationship=2 and c.banned=false")
		Integer chorbisWhoSearchFriendship();
		@Query("select count(c) from Chorbi c where c.searchTemplate is not null and c.searchTemplate.kindRelationship=3 and c.banned=false")
		Integer chorbisWhoSearchLove();
		@Query("select count(c) from Chorbi c where c.banned=false")
		Integer chorbisNotBannedRatio();
		
	//---LEVEL C.2---
		
		//A listing of managers sorted by the number of events that they organise
		@Query("select m from Manager m order by m.events.size")
		Collection<Manager> listManagersOrderByEvents();
		
		//A listing of managers that includes the amount that they due in fees
		@Query("select m from Manager m order by m.amount")
		Collection<Manager> listManagersOrderByAmount();
		
		//A listing of chorbies sorted by the number of events to which they have registered
		@Query("select c from Chorbi c order by c.events.size DESC")
		Collection<Chorbi> listChorbiesOrderyByEvents();
		
		//A listing of chorbies that includes the amount that they due in fees
		@Query("select sum(m.amount) from Chorbi c join c.monthlyFee m group by c")
		Collection<Double> listChorbiesOrderByAmount();
		
	//---LEVEL B.1---
		
		//List of chorbies, sorted by the number of likes the have got
		@Query("select c from Chorbi c order by c.receivedLikes.size DESC")
		Collection<Chorbi> chorbiesSortedByLikes();
		
		//The minimum number of likes per chorbi
		@Query("select min(c.receivedLikes.size) from Chorbi c")
		Integer minLikesPerChorbi();
		
		//The maximum number of likes per chorbi
		@Query("select max(c.receivedLikes.size) from Chorbi c")
		Integer maxLikesPerChorbi();
		
		//The avg number of likes per chorbi
		@Query("select avg(c.receivedLikes.size) from Chorbi c")
		Double avgLikesPerChorbi();
		
	//---LEVEL B.2---
		
		//The minimum, the maximum, and the average number of stars per chorbi
		@Query("select min(l.stars) from Chorbi c join c.receivedLikes l group by c")
		Collection<Integer> minStars();
		
		@Query("select max(l.stars) from Chorbi c join c.receivedLikes l group by c")
		Collection<Integer> maxStars();
		
		@Query("select avg(l.stars) from Chorbi c join c.receivedLikes l group by c")
		Collection<Double> avgStars();
		
		//The list of chorbies, sorted by the average number of stars that they've got
		@Query("select c from Chorbi c join c.receivedLikes l order by l.stars DESC")
		Collection<Chorbi> chorbiesOrderByStars();
		
	//---LEVEL A---
		
		//The minimum number of chirps that a chorbi receives from other chorbies
		@Query("select min(c.receivedChirps.size) from Chorbi c")
		Integer minChirpsReceived();
		
		//The maximum number of chirps that a chorbi receives from other chorbies
		@Query("select max(c.receivedChirps.size) from Chorbi c")
		Integer maxChirpsReceived();
		
		//The avg number of chirps that a chorbi receives from other chorbies
		@Query("select avg(c.receivedChirps.size) from Chorbi c")
		Double avgChirpsReceived();
		
		//The minimum number of chirps that a chorbi send to other chorbies
		@Query("select min(c.sendChirps.size) from Chorbi c")
		Integer minChirpsSend();
		
		//The maximum number of chirps that a chorbi send to other chorbies
		@Query("select max(c.sendChirps.size) from Chorbi c")
		Integer maxChirpsSend();
		
		//The avg number of chirps that a chorbi send to other chorbies
		@Query("select avg(c.sendChirps.size) from Chorbi c")
		Double avgChirpsSend();
		
		//The chorbies who have got more chirps
		@Query("select c from Chorbi c where c.receivedChirps.size > (select avg(c.receivedChirps.size) from Chorbi c)")
		Collection<Chorbi> chorbiesMoreChirpsReceived();
		
		//The chorbies who have sent more chirps
		@Query("select c from Chorbi c where c.sendChirps.size > (select avg(c.sendChirps.size) from Chorbi c)")
		Collection<Chorbi> chorbiesMoreChirpsSent();
	
}