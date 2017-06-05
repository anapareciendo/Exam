package useCases;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import services.EventService;
import utilities.AbstractTest;
import domain.Event;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ListOfAvailableEventTest extends AbstractTest {

	/*
	 * List of events that are going to be organised in less than one month and
	 * have seats available.
	 * 
	 * List every event that was registered in the system. (Past events must be
	 * greyed out; events that are going to be organised in less than one month
	 * and have seats available must also be somewhat highlighted; the rest of
	 * events must be displayed normally.)
	 * 
	 * -El orden de los parámetros es: Error esperado
	 * 
	 * Cobertura del test: Solo hay test positivo, ya que no es necesario estar
	 * autenticado en el sistema para realizar esta acción.
	 */

	@Autowired
	private EventService eventService;

	@Test
	public void driver() {
		final Object testingData[][] = { { null }, };

		for (int i = 0; i < testingData.length; i++)
			this.template((Class<?>) testingData[i][0]);
	}

	protected void template(final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {

			Collection<Event> events = new ArrayList<Event>();
			events.addAll(eventService
					.eventOrganisedLessMonthAndSeatsAvailable());

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}
