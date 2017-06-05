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

import services.EventService;
import services.ManagerService;
import utilities.AbstractTest;
import domain.Event;
import domain.Manager;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class EditMyEventTest extends AbstractTest {

	/*
	 * Edit my event - Manager
	 *
	 * -El orden de los parámetros es: Usuario (Manager) que se va a autenticar, Id del evento a editar, Error esperado
	 * 
	 * Cobertura del test:
	 * -El usuario autenticado existe y uno de sus eventos puede editarse(test positivo)
	 * -El usuario no está autenticado y no se puede editar(test negativo)
	 */
	@Autowired
	private EventService eventService;
	
	@Autowired
	private ManagerService managerService;

	private List<Manager> managers;
	private List<Event> events;
	
	@Before
    public void setup() {
		this.managers = new ArrayList<Manager>();
		this.managers.addAll(this.managerService.findAll());
		
		Collections.shuffle(this.managers);
		while(this.managers.get(0).getEvents().isEmpty())
			Collections.shuffle(this.managers);
		
		events = new ArrayList<Event>(this.managers.get(0).getEvents());
	}
	@Test
	public void driver() {
		final Object testingData[][] = {
				{this.managers.get(0).getUserAccount().getUsername(),this.events.get(0).getId(),null },
				{"", this.events.get(0).getId(),IllegalArgumentException.class}
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
			
			Event event = eventService.findOne(eventId);
			
			event.setDescription("Cambiando la descripción");
			eventService.save(event);
			
			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}
