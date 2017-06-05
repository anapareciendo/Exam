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

import services.ChorbiService;
import services.EventService;
import utilities.AbstractTest;
import domain.Chorbi;
import domain.Event;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class RegisterToAnEventTest extends AbstractTest {

	/*
	 * Register to an event - Chorbi
	 *
	 * -El orden de los parámetros es: Usuario (Chorbi) que se va a autenticar, Id del evento, Error esperado
	 * 
	 * Cobertura del test:
	 * -El usuario autenticado existe(test positivo)
	 * -El usuario no está autenticado(test negativo)
	 */
	@Autowired
	private EventService eventService;
	
	@Autowired
	private ChorbiService chorbiService;

	
	private List<Chorbi> chorbies;
	private List<Event> events;
 	
	@Before
    public void setup() {
		this.chorbies= new ArrayList<Chorbi>(this.chorbiService.findAll());
		this.events = new ArrayList<Event>(eventService.eventOrganisedLessMonthAndSeatsAvailable());
		Collections.shuffle(this.chorbies);
		Collections.shuffle(this.events);
	}
	@Test
	public void driver() {
		final Object testingData[][] = {
				{this.chorbies.get(0).getUserAccount().getUsername(),this.events.get(0).getId(), null },
				{"", this.events.get(0).getId(), IllegalArgumentException.class}
				};

		for (int i = 0; i < testingData.length; i++)
			this.template((String) testingData[i][0],
					(Integer) testingData[i][1],
					(Class<?>) testingData[i][2]);
	}

	protected void template(String username, Integer eventId, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			
			eventService.register(eventId);
			
			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}
