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

import services.BroadcastService;
import services.ManagerService;
import utilities.AbstractTest;
import domain.Broadcast;
import domain.Manager;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class SendABroadcastTest extends AbstractTest {

	/*
	 * Send a broadcast - Manager
	 *
	 * -El orden de los parámetros es: Usuario (Manager) que se va a autenticar, Error esperado
	 * 
	 * Cobertura del test:
	 * -El usuario autenticado existe y se puede crear el broadcast(test positivo)
	 * -El usuario no está autenticado y no se puede crear el broadcast (test negativo)
	 */
	@Autowired
	private BroadcastService broadcastService;
	
	@Autowired
	private ManagerService managerService;

	private List<Manager> managers;
	
	
	@Before
    public void setup() {
		this.managers = new ArrayList<Manager>();
		this.managers.addAll(this.managerService.findAll());
		
		Collections.shuffle(this.managers);
	}
	@Test
	public void driver() {
		final Object testingData[][] = {
				{this.managers.get(0).getUserAccount().getUsername(),null },
				{"",IllegalArgumentException.class}
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
			
			Broadcast b = broadcastService.create();
			Broadcast res = broadcastService.save(b);
			broadcastService.reNew(res);
			
			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}
