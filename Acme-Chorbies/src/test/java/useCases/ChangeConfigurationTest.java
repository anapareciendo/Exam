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

import services.AdministratorService;
import services.ConfigService;
import utilities.AbstractTest;
import domain.Administrator;
import domain.Config;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ChangeConfigurationTest extends AbstractTest {

	/*
	 * Change configuration - Administrator
	 *
	 * -El orden de los parámetros es: Usuario (Admin) que se va a autenticar, Error esperado
	 * 
	 * Cobertura del test:
	 * -El usuario autenticado existe (test positivo)
	 * -El usuario no está autenticado (test negativo)
	 */
	
	@Autowired
	private ConfigService configService;
	
	@Autowired
	private AdministratorService adminService;
	
	

	private List<Administrator> admins;
	
	@Before
    public void setup() {
		this.admins = new ArrayList<Administrator>(this.adminService.findAll());
		
		Collections.shuffle(this.admins);
	}
	
	@Test
	public void driver() {
		final Object testingData[][] = {
				{this.admins.get(0).getUserAccount().getUsername(),null },
				{"", IllegalArgumentException.class}
				};

		for (int i = 0; i < testingData.length; i++)
			this.template((String) testingData[i][0],
					(Class<?>) testingData[i][1]);
	}

	protected void template(String username, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			
			Config config = configService.find();
			config.setCache(30);
			config.setFee(3.6);
			config.setRegistrationFee(5.3);
			
			configService.save(config);
			
			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}
