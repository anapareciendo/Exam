package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.ExamClass;


@Component
@Transactional
public class ExamClassToStringConverter implements Converter<ExamClass, String>{

	@Override
	public String convert(ExamClass examClass) {
		String result;

		if (examClass == null)
			result = null;
		else
			result = String.valueOf(examClass.getId());

		return result;
	}
}
