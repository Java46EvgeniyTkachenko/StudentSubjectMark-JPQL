package telran.spring.data.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import telran.spring.data.entities.MarkEntity;
import telran.spring.data.proj.*;

public interface MarkRepository extends JpaRepository<MarkEntity, Long>{
	//for sql
	//static final String STUDENTS_MARKS = " from students st join marks ms on st.stid=ms.stid ";
	static final String STUDENTS_MARKS = " from StudentEntity st join MarkEntity ms on st.id=ms.id ";
// for sql	
//	static final String STUDENTS_SUBJECTS_MARKS = STUDENTS_MARKS + " join subjects sb on sb.suid=ms.suid ";
	static final String STUDENTS_SUBJECTS_MARKS = STUDENTS_MARKS + " join SubjectEntity sb on sb.id=ms.id ";
	static final String MIN_INTERVAL = "floor(mark/:interval) * :interval";
	List<MarkProj> findByStudentNameAndSubjectSubject(String name, String subject);
	@Query(value = "select name, subject, mark"
			+ STUDENTS_SUBJECTS_MARKS
			+ "where name=:name",nativeQuery = true)
	List<StudentSubjectMark> findByStudentName(String name);
	/*********************************************************/
//for sql
//	@Query(value="select name, round(avg(mark)) as avgMark " + STUDENTS_MARKS + "group by name", 
//			nativeQuery = true)
//for jpql	
	@Query("select student.name as name, avg(mark) as avgMark " + STUDENTS_MARKS + "group by name")
	List<StudentAvgMark> studentsAvgMarks();
	/*********************************************************/
	//for sql
//	@Query(value="select name " + STUDENTS_MARKS + "group by name having avg(mark) >"
//			+ " (select avg(mark) from marks)", 
//			nativeQuery = true)
	//for jpql
	@Query("select student.name as name from MarkEntity group by student.name having avg(mark) > "
			+ "(select avg(mark) from MarkEntity)")
	List<StudentName> bestStudents();
	/*********************************************************/
	//for sql
//	@Query(value="select name " +STUDENTS_MARKS + "group by name order by avg(mark) desc"
//			+ " limit :nStudents", 
//			nativeQuery = true)
	//for jpql
	@Query("select student.name as name " +STUDENTS_MARKS + "group by name order by avg(mark) desc"
			+ " limit :nStudents")
	List<StudentName> topBestStudents(int nStudents);
	/*********************************************************/
	//for sql
//	@Query(value="select name " +  STUDENTS_SUBJECTS_MARKS + " where subject=:subject "
//			+ "group by name order by avg(mark) desc limit :nStudents", 
//			nativeQuery = true)
	//for jpql
	@Query("select student.name as name " +  STUDENTS_SUBJECTS_MARKS + " where sb.subject=:subject "
			+ "group by name order by avg(mark) desc limit :nStudents") 
	List<StudentName> topBestStudentsSubject(int nStudents, String subject);
	/*********************************************************/
//	@Query(value="select name, subject, mark " +  STUDENTS_SUBJECTS_MARKS + " where subject=:subject "
//			+ "group by name order by avg(mark) asc limit :nStudents", 
//			nativeQuery = true)
	@Query(value="select name, subject, mark from students st join marks ms on st.stid=ms.stid"
			+ " join subjects sb on sb.suid=ms.suid where subject=:subject "
			+ "group by name order by avg(mark) asc limit :nStudents", 
			nativeQuery = true)
	List<StudentSubjectMark> worstStudentsMarks(int nStudents);
	/*********************************************************/
	//for sql
//	@Query(value="select " + MIN_INTERVAL +" as min,"
//			+  MIN_INTERVAL + " + :interval - 1 as max, "
//			+ "count(*) as count from marks group by min, max order by min", nativeQuery = true)
	//for jpql
	@Query("select " + MIN_INTERVAL +" as min,"
			+  MIN_INTERVAL + " + :interval - 1 as max, "
			+ "count(*) as count from MarkEntity group by min, max order by min")
	List<IntervalMarksCount> marksDistribution(int interval);
	
	

}
