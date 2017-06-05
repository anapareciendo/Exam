package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.LikesRepository;
import domain.Likes;

@Component
@Transactional
public class StringToLikesConverter implements Converter<String, Likes>{

	@Autowired
	LikesRepository likesRepository;

	@Override
	public Likes convert(String text) {
		Likes result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = likesRepository.findOne(id);
		} catch (Exception oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}
}
