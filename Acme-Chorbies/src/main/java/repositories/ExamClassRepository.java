package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.ExamClass;

@Repository
public interface ExamClassRepository extends JpaRepository<ExamClass, Integer> {

	@Query("select ec from ExamClass ec where ec.canceled = 0")
	Collection<ExamClass> findNotCanceled();
	
	@Query("select ec from ExamClass ec where ec.canceled = 0 and ec.event=?1")
	Collection<ExamClass> findMyNotCanceled(int eventId);
}