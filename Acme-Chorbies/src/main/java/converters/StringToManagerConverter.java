package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.ManagerRepository;
import domain.Manager;

@Component
@Transactional
public class StringToManagerConverter implements Converter<String, Manager>{

	@Autowired
	ManagerRepository managerRepository;

	@Override
	public Manager convert(String text) {
		Manager result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = managerRepository.findOne(id);
		} catch (Exception oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}
}
