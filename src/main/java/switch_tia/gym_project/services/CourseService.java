package switch_tia.gym_project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import switch_tia.gym_project.UTILITIES.exceptions.CourseAlreadyExistsException;
import switch_tia.gym_project.UTILITIES.exceptions.IncorrectDataException;
import switch_tia.gym_project.entities.Course;
import switch_tia.gym_project.repositories.CourseRepository;

@Service
public class CourseService {

    @Autowired
    CourseRepository cr;

    public Course saveCourse (Course c) throws RuntimeException{
        if (cr.existsByCourseCode(c.getCourseCode())){
            throw new CourseAlreadyExistsException ();
        }
        return cr.save(c);
    }

    public String deleteCourse (int courseCode) throws RuntimeException {
        Course c = cr.findByCourseCode(courseCode);
        if (c == null) {
            throw new IncorrectDataException();
        }
        cr.delete(c);
        String x = "The course has been deleted";
        return x;
    }
    
}
