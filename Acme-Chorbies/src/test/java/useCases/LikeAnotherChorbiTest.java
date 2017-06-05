package useCases;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import security.LoginService;
import services.ChorbiService;
import services.LikesService;
import utilities.AbstractTest;
import domain.Chorbi;
import domain.Likes;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class LikeAnotherChorbiTest extends AbstractTest{
	
	/* *----Like another chorbi; a like may be cancelled at any time.-----*
	  -El orden de los parámetros es: Usuario que se va a autenticar,
	  momento en el que se crea el me gusta, comentario del me gusta, error esperado
	  
	  Cobertura del test:
	  		//El usuario autenticado es un chorbi (test positivo)
			//El usuario no está autenticado(test negativo)
				
	 */
	
	@Autowired
	private LikesService likesService;
	
	@Autowired
	private ChorbiService chorbiService;
	
	private List<Chorbi> chorbies;
	
	@Before
    public void setup() {
		this.chorbies= new ArrayList<Chorbi>();
		this.chorbies.addAll(this.chorbiService.findNotBanned());
		
		Collections.shuffle(this.chorbies);
	}
	
	@Test
	public void driver() {
		final Object testingData[][] = {
			{
				this.chorbies.get(0).getUserAccount().getUsername(),Calendar.getInstance().getTime(),"Prueba", null
			}, {
				"", Calendar.getInstance().getTime(), "Fallida", IllegalArgumentException.class
			},
		};

		for (int i = 0; i < testingData.length; i++)
			this.template((String) testingData[i][0],
					(Date) testingData[i][1],
					(String) testingData[i][2],
					(Class<?>) testingData[i][3]);
	}
	

	protected void template(final String username, Date moment, String comment, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			Chorbi liker = chorbiService.findByUserAccountId(LoginService.getPrincipal().getId());
			Likes likes = likesService.create(liker, chorbies.get(0));
			likesService.save(likes);
	
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}
