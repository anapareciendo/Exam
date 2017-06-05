package repositories;


import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Chorbi;
import domain.MonthlyFee;

@Repository
public interface MonthlyFeeRepository extends JpaRepository<MonthlyFee, Integer> {

	@Query("select distinct mf.chorbi from MonthlyFee mf where year(mf.moment)=year(current_Date) and month(mf.moment)=month(current_Date)")
	Collection<Chorbi> findFeeChorbi();
}