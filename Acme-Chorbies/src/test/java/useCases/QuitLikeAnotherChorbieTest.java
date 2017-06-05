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

import services.LikesService;
import utilities.AbstractTest;
import domain.Likes;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class QuitLikeAnotherChorbieTest extends AbstractTest{
	
	/* *----Browse the list of chorbies who have registered to the system.-----*
	  -El orden de los parámetros es: Usuario que se va a autenticar, Likes que se va a borrar, error esperado
	  
	  Cobertura del test:
	  		//El usuario autenticado existe y es propietario de ese likes (liker)(test positivo)
			//El usuario no está autenticado y no puede borrar (test negativo)
				
	 */
	
	@Autowired
	private LikesService likesService;
	
	private List<Likes> likes;
	
	@Before
    public void setup() {
		this.likes = new ArrayList<Likes>(this.likesService.findAll());
		Collections.shuffle(this.likes);
		
	}
	
	@Test
	public void driver() {
		final Object testingData[][] = {
			{
				this.likes.get(0).getLiker().getUserAccount().getUsername(), this.likes.get(0), null
			}, {
				"", this.likes.get(0),IllegalArgumentException.class
			},
		};

		for (int i = 0; i < testingData.length; i++)
			this.template((String) testingData[i][0], (Likes)testingData[i][1], (Class<?>) testingData[i][2]);
	}
	

	protected void template(final String username, final Likes delete, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			
			likesService.delete(delete);
				
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}