package useCases;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import services.AdministratorService;
import services.ChorbiService;
import services.SearchTemplateService;
import utilities.AbstractTest;
import domain.Administrator;
import domain.Chorbi;
import domain.Coordinates;
import domain.Genre;
import domain.KindRelationship;
import domain.SearchTemplate;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class SearchTemplateTest extends AbstractTest{
	
//		*---Uso del Search Template.-----*
//	  -El orden de los parámetros es: Usuario que se va a autenticar, error esperado
//	  
//	  Cobertura del test:
	  		//El usuario autenticado es un chorbi (test positivo)
			//El usuario no está autenticado(test negativo)
			//El usuario autenticado es un admin (test negativo)
				
	
	@Autowired
	private SearchTemplateService templateService;
	
	@Autowired
	private ChorbiService chorbiService;
	@Autowired
	private AdministratorService adminService;
	
	private List<Chorbi> chorbies;
	private List<Administrator> admins;
	
	@Before
    public void setup() {
		this.chorbies= new ArrayList<Chorbi>();
		this.chorbies.addAll(this.chorbiService.findNotBanned());
		this.admins = new ArrayList<Administrator>();
		this.admins.addAll(this.adminService.findAll());
		
		Collections.shuffle(this.chorbies);
	}
	
	@Test
	public void driver() {
		final Object testingData[][] = {
			{
				this.chorbies.get(0).getUserAccount().getUsername(), null
			}, {
				this.admins.get(0).getUserAccount().getUsername(), IllegalArgumentException.class
			},
		};

		for (int i = 0; i < testingData.length; i++)
			this.template((String) testingData[i][0],(Class<?>) testingData[i][1]);
	}
	

	protected void template(final String username, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			Set<Chorbi> chorbies = new HashSet<Chorbi>();
			SearchTemplate st = templateService.create();
			st.setAproximateAge(25);
			st.setCoordinates(new Coordinates("Spain", "Sevilla", "", ""));
			st.setGenre(Genre.OTHER);
			st.setKeyword("pedro");
			st.setKindRelationship(KindRelationship.LOVE);
			
			chorbies.addAll(templateService.searchTemplate(st.getKindRelationship(), st.getGenre(), st.getAproximateAge(), st.getCoordinates().getCountry(), st.getCoordinates().getCity(), st.getCoordinates().getState(), st.getCoordinates().getProvince(), st.getKeyword()));
			
			st.getResults().addAll(chorbies);
			templateService.save(st);
			
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}
