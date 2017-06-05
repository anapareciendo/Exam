package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Administrator extends Actor{

	//---------------------Relationships--------------------------
	private Collection<ExamClass> examClasses;

	@NotNull
	@Valid
	@OneToMany(mappedBy="admin")
	public Collection<ExamClass> getExamClasses() {
		return examClasses;
	}

	public void setExamClasses(Collection<ExamClass> examClasses) {
		this.examClasses = examClasses;
	}
	
	
	
	
}
