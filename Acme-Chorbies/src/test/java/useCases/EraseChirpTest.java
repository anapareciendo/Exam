package useCases;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import security.LoginService;
import services.AdministratorService;
import services.ChirpService;
import services.ChorbiService;
import utilities.AbstractTest;
import domain.Administrator;
import domain.Chirp;
import domain.Chorbi;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class EraseChirpTest extends AbstractTest{
	
	/* *----Erase any of the chirps that he or she’s got or sent.-----*
	  -El orden de los parámetros es: Usuario que se va a autenticar, error esperado
	  
	  Cobertura del test:
	  		//El usuario autenticado es un chorbi(test positivo)
			//El usuario no está autenticado(test negativo)
				
	 */
	
	@Autowired
	private ChorbiService chorbiService;
	
	@Autowired
	private AdministratorService adminService;
	
	@Autowired
	private ChirpService chirpService;
	
	
	private List<Administrator> admins;
	private List<Chorbi> chorbies;
	
	@Before
    public void setup() {
		this.admins= new ArrayList<Administrator>();
		this.admins.addAll(this.adminService.findAll());
		
		Collections.shuffle(this.admins);
		
		this.chorbies = new ArrayList<Chorbi>();
		this.chorbies.addAll(this.chorbiService.findAll());
		
	}
	
	@Test
	public void driver() {
		final Object testingData[][] = {
			{
				this.chorbies.get(0).getUserAccount().getUsername(), null
			}, {
				"", IllegalArgumentException.class
			},
		};

		for (int i = 0; i < testingData.length; i++)
			this.template((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}
	

	protected void template(final String username, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			
			
			Chorbi chorbi = chorbiService.findByUserAccountId(LoginService.getPrincipal().getId());
			
			Chirp chirp = ((List<Chirp>) chorbi.getReceivedChirps()).get(0);
						
			chirpService.delete(chirp);
			
			
			
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}
