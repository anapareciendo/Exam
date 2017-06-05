package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Broadcast;

@Repository
public interface BroadcastRepository extends JpaRepository<Broadcast, Integer> {

	@Query("select e.broadcast from Event e join e.chorbies c where c.userAccount.id=?1")
	Collection<Broadcast> findMyBroadcast(int id);
}