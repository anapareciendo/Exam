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
import services.AdministratorService;
import services.ChirpService;
import services.ChorbiService;
import utilities.AbstractTest;
import domain.Administrator;
import domain.Chirp;
import domain.Chorbi;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ChirpAnotherChorbiTest extends AbstractTest{
	
//	  *-----Chirp to another chorbi.-----*
//	  -El orden de los parámetros es: Usuario que se va a autenticar, error esperado
//	  
//	  Cobertura del test:
	  		//El usuario autenticado es un chorbi, que envia a otro chorbi, con asunto y texto y lista de adjuntos(test positivo)
			//El chorbi receptro esta a null (test negativo)
			//EL asunto esta a null (test negativo)
			//El asunto esta en blanco (test negativo)
			//El texto esta a null (test negativo)
			//El texto esta en blanco (test positivo)
			//La lista de adjuntos esta vacia (test positivo)
			//La lista de adjuntos es null (test negativo)
			//El usuario no esta autenticado (test negativo)
			//El usuario autenticado es un admin(test negativo)
	
	@Autowired
	private ChorbiService chorbiService;
	
	@Autowired
	private AdministratorService adminService;
	
	@Autowired
	private ChirpService chirpService;
	
	
	private List<Administrator> admins;
	private List<Chorbi> chorbies;
	private List<String> attchments;
	
	@Before
    public void setup() {
		this.admins= new ArrayList<Administrator>();
		this.admins.addAll(this.adminService.findAll());
		
		Collections.shuffle(this.admins);
		
		this.chorbies = new ArrayList<Chorbi>();
		this.chorbies.addAll(this.chorbiService.findNotBanned());
		
		Collections.shuffle(this.chorbies);
		
		this.attchments = new ArrayList<String>();
		attchments.add("http://hola.png");
		attchments.add("http://adios.png");
		attchments.add("http://muybien.png");
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void driver() {
		final Object testingData[][] = {
			{
				this.chorbies.get(0).getUserAccount().getUsername(), this.chorbies.get(0),"Asunto","Texto",this.attchments, null
			},{
				this.chorbies.get(0).getUserAccount().getUsername(), null,"Asunto","Texto",this.attchments, IllegalArgumentException.class
			},{
				this.chorbies.get(0).getUserAccount().getUsername(),this.chorbies.get(0),null,"Texto",this.attchments, IllegalArgumentException.class
			},{
				this.chorbies.get(0).getUserAccount().getUsername(),this.chorbies.get(0),"","Texto",this.attchments, IllegalArgumentException.class
			},{
				this.chorbies.get(0).getUserAccount().getUsername(),this.chorbies.get(0),"Asunto",null,this.attchments, IllegalArgumentException.class
			},{
				this.chorbies.get(0).getUserAccount().getUsername(),this.chorbies.get(0),"Asunto","",this.attchments, null
			},{
				this.chorbies.get(0).getUserAccount().getUsername(),this.chorbies.get(0),"Asunto","Texto",new ArrayList<String>(), null
			},{
				this.chorbies.get(0).getUserAccount().getUsername(),this.chorbies.get(0),"Asunto","",null, IllegalArgumentException.class
			},{
				null,this.chorbies.get(0),"Asunto","Texto",this.attchments, IllegalArgumentException.class
			},{
				this.admins.get(0).getUserAccount().getUsername(),this.chorbies.get(0),"Asunto","Text",this.attchments, IllegalArgumentException.class
			},
		};

		for (int i = 0; i < testingData.length; i++)
			this.template((String) testingData[i][0],(Chorbi) testingData[i][1], (String) testingData[i][2],(String) testingData[i][3],(List<String>) testingData[i][4], (Class<?>) testingData[i][5]);
	}
	

	protected void template(final String username,final Chorbi recipient, final String subject, final String text, final List<String> attachments, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			
			Chorbi sender = chorbiService.findByUserAccountId(LoginService.getPrincipal().getId());
			
			Chirp chirp=chirpService.create(sender, recipient);
			chirp.setSubject(subject);
			chirp.setText(text);
			chirp.setAttachments(attachments);
			
			chirpService.save(chirp);
			
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}
