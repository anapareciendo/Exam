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
import services.EventService;
import services.ManagerService;
import utilities.AbstractTest;
import domain.Event;
import domain.Manager;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CreateEventTest extends AbstractTest {

	/*
	 * Create an event - Manager
	 *
	 * -El orden de los parámetros es: Usuario (Manager) que se va a autenticar, Error esperado
	 * 
	 * Cobertura del test:
	 * -El usuario autenticado existe y se puede crear el nuevo evento(test positivo)
	 * -El usuario no está autenticado y no se puede crear un nuevo evento(test negativo)
	 * -El usuario autenticado existe pero introduce un día erróneo (un día despues de lo que se puede) (test negativo)
	 * -El usuario autenticado existe e introduce un día erróneo (día negativo) (test negativo)
	 * -El usuario autenticado existe pero introduce un mes erróneo (un mes despues de lo que se puede) (test negativo)
	 * -El usuario autenticado existe pero introduce un mes erróneo (mes negativo )(test negativo)
	 * -El usuario autenticado existe pero introduce una hora errónea (una hora despues de lo que se puede) (test negativo)
	 * -El usuario autenticado existe pero introduce los minutos erróneos (un minuto despues de lo que se puede) (test negativo)
	 * -El usuario autenticado existe pero introduce los minutos errónos (minutos negativos) (test negativo)
	 */
	@Autowired
	private EventService eventService;
	
	@Autowired
	private ManagerService managerService;

	
	private List<Manager> managers;
	
	@Before
    public void setup() {
		this.managers= new ArrayList<Manager>();
		this.managers.addAll(this.managerService.findAll());
		
		Collections.shuffle(this.managers);
	}
	@Test
	public void driver() {
		final Object testingData[][] = {
				{this.managers.get(0).getUserAccount().getUsername(),"titulo",2,4,2019,12,34,"descripcion","http://hola.com",3, null },
				{null,"titulo",2,4,2019,12,34,"descripcion","http://hola.com",3,IllegalArgumentException.class},
				{this.managers.get(0).getUserAccount().getUsername(),"titulo",32,4,2019,12,34,"descripcion","http://hola.com",3, IllegalArgumentException.class},
				{this.managers.get(0).getUserAccount().getUsername(),"titulo",-2,4,2019,12,34,"descripcion","http://hola.com",3, IllegalArgumentException.class},
				{this.managers.get(0).getUserAccount().getUsername(),"titulo",2,13,2019,12,34,"descripcion","http://hola.com",3, IllegalArgumentException.class},
				{this.managers.get(0).getUserAccount().getUsername(),"titulo",2,-5,2019,12,34,"descripcion","http://hola.com",3, IllegalArgumentException.class},
				{this.managers.get(0).getUserAccount().getUsername(),"titulo",2,4,2002,25,34,"descripcion","http://hola.com",3, IllegalArgumentException.class},
				{this.managers.get(0).getUserAccount().getUsername(),"titulo",2,4,2002,-3,34,"descripcion","http://hola.com",3,IllegalArgumentException.class},
				{this.managers.get(0).getUserAccount().getUsername(),"titulo",2,4,2002,12,61,"descripcion","http://hola.com",3, IllegalArgumentException.class},
				{this.managers.get(0).getUserAccount().getUsername(),"titulo",2,4,2002,12,-4,"descripcion","http://hola.com",3, IllegalArgumentException.class}
				};

		for (int i = 0; i < testingData.length; i++)
			this.template((String) testingData[i][0],
					(String) testingData[i][1],
					(int) testingData[i][2],
					(int) testingData[i][3],
					(int) testingData[i][4],
					(int) testingData[i][5],
					(int) testingData[i][6],
					(String) testingData[i][7],
					(String) testingData[i][8],
					(int) testingData[i][9],
					(Class<?>) testingData[i][10]);
	}

	protected void template(String username,String titulo, int dia, int mes, int anio, int hora, int minutos, String descripcion, 
			String picture, int sitios, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			
			Event e= eventService.create(managerService.findByUserAccountId(LoginService.getPrincipal().getId()));
			e.setTitle(titulo);
			e.setDay(dia);
			e.setMonth(mes);
			e.setYear(anio);
			e.setHour(hora);
			e.setMinutes(minutos);
			e.setDescription(descripcion);
			e.setPicture(picture);
			e.setSeatsOffered(sitios);
			eventService.save(e);
			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}
