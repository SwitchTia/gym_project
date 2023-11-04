package switch_tia.gym_project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import switch_tia.gym_project.entities.ActiveCourse;

public interface ActiveCourseRepository extends JpaRepository <ActiveCourse, Integer> {
    
    ActiveCourse findByCourseCode (int courseCode);
    boolean existsByCourseCode (int courseCode);
}
