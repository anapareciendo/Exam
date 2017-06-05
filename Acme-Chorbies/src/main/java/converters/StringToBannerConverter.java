package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.BannerRepository;
import domain.Banner;

@Component
@Transactional
public class StringToBannerConverter implements Converter<String, Banner>{

	@Autowired
	BannerRepository bannerRepository;

	@Override
	public Banner convert(String text) {
		Banner result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = bannerRepository.findOne(id);
		} catch (Exception oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}
}
