package telran.spring.data.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import telran.spring.data.entities.SubjectEntity;

public interface SubjectRepository extends JpaRepository<SubjectEntity, Long>{
	@Query("select subject from SubjectEntity subjects where id in"
    		+ " (select subject.id from MarkEntity"
    		+ " group by subject.id having count(mark) < :marksThreshold)")
	List<SubjectEntity> worstSubject(int marksThreshold);

}
