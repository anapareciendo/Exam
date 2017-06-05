package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.ConfigRepository;
import domain.Config;

@Component
@Transactional
public class StringToConfigConverter implements Converter<String, Config>{

	@Autowired
	ConfigRepository configRepository;

	@Override
	public Config convert(String text) {
		Config result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = configRepository.findOne(id);
		} catch (Exception oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}
}
