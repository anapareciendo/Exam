package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.ChorbiRepository;
import domain.Chorbi;

@Component
@Transactional
public class StringToChorbiConverter implements Converter<String, Chorbi>{

	@Autowired
	ChorbiRepository chorbiRepository;

	@Override
	public Chorbi convert(String text) {
		Chorbi result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = chorbiRepository.findOne(id);
		} catch (Exception oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}
}
