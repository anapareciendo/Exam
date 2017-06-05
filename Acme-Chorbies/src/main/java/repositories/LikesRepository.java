package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Likes;

@Repository
public interface LikesRepository extends JpaRepository<Likes, Integer> {

	
}