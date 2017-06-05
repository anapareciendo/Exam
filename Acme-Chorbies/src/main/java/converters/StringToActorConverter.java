package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.AdministratorRepository;
import repositories.ChorbiRepository;
import domain.Actor;

@Component
@Transactional
public class StringToActorConverter implements Converter<String, Actor>{
	
	@Autowired
	AdministratorRepository adminRepository;
	@Autowired
	ChorbiRepository chorbiRepository;
	

	@Override
	public Actor convert(String text) {
		Actor result;
		int id;

		try {
			id = Integer.valueOf(text);
			
			result = adminRepository.findOne(id);
			
			if(result == null){
				result = chorbiRepository.findOne(id);
			}
			
			
		} catch (Exception oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
