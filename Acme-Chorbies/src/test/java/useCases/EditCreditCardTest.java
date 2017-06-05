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
import services.CreditCardService;
import services.ManagerService;
import utilities.AbstractTest;
import domain.CreditCard;
import domain.Manager;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class EditCreditCardTest extends AbstractTest {

	/*
	 * Edit creditcard - Manager and Chorbi
	 *
	 * -El orden de los parámetros es: Usuario que se va a autenticar, Error esperado
	 * 
	 * Cobertura del test:
	 * -El usuario autenticado existe y puede editar su creditCard(test positivo)
	 * -El usuario no está autenticado y no puede editar su creditCard(test negativo)
	 */
	@Autowired
	private CreditCardService creditCardService;
	
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
			
			Manager manager = managerService.findByUserAccountId(LoginService.getPrincipal().getId());
			CreditCard creditCard = manager.getCreditCard();
			creditCard.setCvv(117);
			creditCardService.save(creditCard);
			
			manager.setCreditCard(creditCard);
			managerService.save(manager);
			
			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}
