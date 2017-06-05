package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Likes;


@Component
@Transactional
public class LikesToStringConverter implements Converter<Likes, String>{

	@Override
	public String convert(Likes likes) {
		String result;

		if (likes == null)
			result = null;
		else
			result = String.valueOf(likes.getId());

		return result;
	}
}
