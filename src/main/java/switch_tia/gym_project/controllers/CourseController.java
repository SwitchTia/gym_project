package switch_tia.gym_project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.config.Task;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import switch_tia.gym_project.entities.Course;
import switch_tia.gym_project.repositories.CourseRepository;
import switch_tia.gym_project.services.CourseService;

@RestController
@RequestMapping ("/course")
public class CourseController {
    
    @Autowired
    CourseRepository cr;

    @Autowired
    CourseService cs;

    @PostMapping ("/saveCourse")
    @PreAuthorize ("hasAnyAuthority ('ADMIN','INSTRUCTOR')")
    public ResponseEntity saveCourse (@RequestBody Course c){
        try {
            return new ResponseEntity (cs.saveCourse (c), HttpStatus.OK);
        } catch (RuntimeException e) {
            String ex = e.getClass().getSimpleName();
            return new ResponseEntity (ex, HttpStatus.BAD_REQUEST);
        }
    }
    
    @DeleteMapping ("/deleteCourse")
    @PreAuthorize ("hasAnyAuthority ('ADMIN','INSTRUCTOR')")
    public ResponseEntity deleteCourse (@RequestParam ("courseCode") int courseCode){
        try {
            return new ResponseEntity (cs.deleteCourse (courseCode), HttpStatus.OK);
        } catch (RuntimeException e) {
            String ex = e.getClass().getSimpleName();
            return new ResponseEntity (ex, HttpStatus.BAD_REQUEST);
        }
    }
    
}
