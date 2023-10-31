package switch_tia.gym_project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import switch_tia.gym_project.entities.Course;

public interface CourseRepository extends JpaRepository <Course, Integer>{
    Course findByCourseCode (int courseCode);
    boolean existsByCourseCode (int courseCode);
}
