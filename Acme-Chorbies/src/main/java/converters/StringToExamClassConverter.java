package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.ExamClassRepository;
import domain.ExamClass;

@Component
@Transactional
public class StringToExamClassConverter implements Converter<String, ExamClass>{

	@Autowired
	ExamClassRepository examClassRepository;

	@Override
	public ExamClass convert(String text) {
		ExamClass result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = examClassRepository.findOne(id);
		} catch (Exception oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}
}
