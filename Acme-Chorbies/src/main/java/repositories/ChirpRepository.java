package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Chirp;

@Repository
public interface ChirpRepository extends JpaRepository<Chirp, Integer> {

	@Query("select c from Chirp c where c.recipient.userAccount.id=?1")
	Collection<Chirp> findMyReceivedChirps(int uaId);
	
	@Query("select c from Chirp c where c.sender.userAccount.id=?1")
	Collection<Chirp> findMySentChirps(int uaId);
}