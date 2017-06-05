package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import domain.Chorbi;
import domain.Genre;
import domain.KindRelationship;
import domain.SearchTemplate;

@Repository
public interface SearchTemplateRepository extends JpaRepository<SearchTemplate, Integer> {

	@Query("select c from Chorbi c where c.kindRelationship=?1 and c.genre=?2 " +
			"and abs(year(c.birthDate)+?3-year(current_date))<=5" +
			"and c.banned=false")
	Collection<Chorbi> searchTemplate(KindRelationship kind, Genre genre, int age);
	
	@Query("select c from Chorbi c where c.coordinates.country like '%'||:country||'%'" +
			"and c.coordinates.city like '%'||:city||'%' and c.coordinates.state like '%'||:state||'%'" +
			"and c.coordinates.province like '%'||:province||'%' and (c.name like '%'||:keyword||'%' " +
			"or c.surname like '%'||:keyword||'%') and c.banned=false")
	Collection<Chorbi> searchTemplate(@Param("country") String country, @Param("city") String city, 
			@Param("state") String state, @Param("province") String province, @Param("keyword") String keyword);
	
}