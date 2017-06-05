package useCases;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import security.Authority;
import security.UserAccount;
import services.ChorbiService;
import utilities.AbstractTest;
import domain.Chorbi;
import domain.Coordinates;
import domain.Genre;
import domain.KindRelationship;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class RegisterChorbiTest extends AbstractTest {

	/*
	 * *----Register to the system as a chorbi. As of the time of registering, a
	 * chorbi is not required to provide a credit card. No person under 18 is
	 * allowed to register to the system. -----* 
	 * -El orden de los parámetros es: nombre de usuario, contraseña, nombre, apellidos, email,
	 *teléfono, foto, tipo de relación que busca, fecha de nacimiento, genero y error esperado
	 * 
	 * 
	 * Cobertura del test: 
	 * 		//Todos los atributos correctos por lo tanto se puede registrar y autenticar (test positivo) 
	 * 		//El patrón del atributo teléfono no es correcto (test negativo)
	 */

	@Autowired
	private ChorbiService chorbiService;

	@Test
	public void driver() {
		final Object testingData[][] = {
				{ "chorbiTest", "password", "Aloy", "Ramos", "aloyR@gmail.com",
						"+34122332687", "http://www.guapo.es",
						KindRelationship.FRIENDSHIP, new Date(1991/12/5),
						Genre.WOMEN, null },
				{ "chorbiTest2", "password", "Aloy", "Ramos", "aloyR@gmail.com",
						"412", "http://www.guapo.es",
						KindRelationship.FRIENDSHIP, new Date(1991/12/5),
						Genre.WOMEN, ConstraintViolationException.class}
		};

		for (int i = 0; i < testingData.length; i++)
			this.template((String) testingData[i][0],
					(String) testingData[i][1], (String) testingData[i][2],
					(String) testingData[i][3], (String) testingData[i][4],
					(String) testingData[i][5], (String) testingData[i][6],
					(KindRelationship) testingData[i][7],
					(Date) testingData[i][8], (Genre) testingData[i][9],
					(Class<?>) testingData[i][10]);
	}

	protected void template(String username, String password, String name,
			String surname, String email, String phone, String picture,
			KindRelationship kindRelationship, Date birthDate, Genre genre,
			Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {

			final Collection<Authority> authorities = new ArrayList<Authority>();
			final Authority a = new Authority();
			a.setAuthority(Authority.CHORBI);
			authorities.add(a);

			final UserAccount ua = new UserAccount();
			ua.setUsername(username);
			ua.setPassword(password);
			ua.setAuthorities(authorities);

			final Chorbi c = this.chorbiService.create(ua);
			c.setName(name);
			c.setSurname(surname);
			c.setEmail(email);
			c.setPhone(phone);

			c.setPicture(picture);
			c.setKindRelationship(kindRelationship);
			c.setBirthDate(birthDate);
			c.setGenre(genre);

			Coordinates coordinates = new Coordinates("Huelva", "Huelva",
					"España", "Andalucía");

			c.setCoordinates(coordinates);

			Chorbi save = this.chorbiService.save(c);
			authenticate(save.getUserAccount().getUsername());
			unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}
