package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.SearchTemplateRepository;
import domain.SearchTemplate;

@Component
@Transactional
public class StringToSearchTemplateConverter implements Converter<String, SearchTemplate>{

	@Autowired
	SearchTemplateRepository searchTemplateRepository;

	@Override
	public SearchTemplate convert(String text) {
		SearchTemplate result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = searchTemplateRepository.findOne(id);
		} catch (Exception oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}
}
