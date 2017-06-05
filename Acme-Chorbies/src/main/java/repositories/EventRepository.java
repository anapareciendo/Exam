package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Chorbi;
import domain.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer>{

	@Query("select e from Event e where e.manager.userAccount.id=?1")
	Collection<Event> findMyEvents(int uaId);
	
	@Query("select e from Event e where year(current_Date)=e.year and ((month(current_Date)=e.month and day(current_Date)<=e.day) or month(current_Date)<e.month)")
	Collection<Event> eventOrganisedLessMonth();
	
	@Query("select e from Event e where year(current_Date)=e.year and ((month(current_Date)=e.month and day(current_Date)<=e.day) or month(current_Date)<e.month) and e.seatsOffered>e.chorbies.size")
	Collection<Event> eventOrganisedLessMonthAndSeatsAvailable();
		
	@Query("select e from Event e where year(current_Date)>e.year or (year(current_Date)=e.year and month(current_Date)>e.month) or (year(current_Date)=e.year and month(current_Date)=e.month and day(current_Date)>e.day)")
	Collection<Event> pastEvents();
	
	//@Query("select e from Event e where e.seatsOffered>e.chorbies.size")
	//Collection<Event> pastEvents();
	
	@Query("select distinct e.chorbies from Event e where e.manager.userAccount.id=?1")
	Collection<Chorbi> findMyAssistants(int uaId);
}
