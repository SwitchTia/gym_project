package switch_tia.gym_project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import switch_tia.gym_project.UTILITIES.exceptions.CourseAlreadyExistsException;
import switch_tia.gym_project.UTILITIES.exceptions.CourseDoesNotExistException;
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

    @Transactional
    public String deleteCourse (int courseCode) throws RuntimeException {
        Course c = cr.findByCourseCode(courseCode);
        if (c == null) {
            throw new IncorrectDataException();
        }
        cr.delete(c);
        String x = "The course has been deleted";
        return x;
    }

    public Course getCourse (int courseCode) throws RuntimeException {
        Course c = cr.findByCourseCode(courseCode);
        if (!cr.existsByCourseCode(courseCode)){
            throw new CourseDoesNotExistException ();
        }
        return c;
    } 

    public Page <Course> courseSortPage (int pageNr, int pageSize, String sortDirection) {
        Sort sort;
        if ("desc".equalsIgnoreCase(sortDirection)) {
            sort = Sort.by(Sort.Order.desc("courseCode"));
        } else {
            sort = Sort.by(Sort.Order.asc("courseCode"));
        }
        PageRequest pageReq = PageRequest.of(pageNr, pageSize, sort);
        return cr.findAll(pageReq);
    }
    
}
