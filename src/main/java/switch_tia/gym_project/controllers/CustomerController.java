package switch_tia.gym_project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import switch_tia.gym_project.UTILITIES.configurations.AuthenticationRequest;
import switch_tia.gym_project.UTILITIES.configurations.RegisterRequest;
import switch_tia.gym_project.repositories.CustomerRepository;
import switch_tia.gym_project.services.CustomerService;
import switch_tia.gym_project.services.JwtService;

@RestController
@RequestMapping ("/customer")
public class CustomerController {

    @Autowired
    CustomerService cs;

    @Autowired
    CustomerRepository cr;

    @Autowired
    JwtService jwtService;

    @PostMapping ("/registerAdmin")
    public ResponseEntity registerAdmin (@RequestBody RegisterRequest regRequest){
        try {
            return new ResponseEntity (cs.registerAdmin(regRequest), HttpStatus.OK);
        } catch (RuntimeException e) {
            String x = e.getClass().getSimpleName();
            return new ResponseEntity(x, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping ("/registerInstructor")
    public ResponseEntity registerInstructor (@RequestBody RegisterRequest regRequest){
        try {
            return new ResponseEntity (cs.registerInstructor(regRequest), HttpStatus.OK);
        } catch (RuntimeException e) {
            String x = e.getClass().getSimpleName();
            return new ResponseEntity(x, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping ("/registerCustomer")
    public ResponseEntity registerCustomer (@RequestBody RegisterRequest regRequest){
        try {
            return new ResponseEntity (cs.registerCustomer(regRequest), HttpStatus.OK);
        } catch (RuntimeException e) {
            String x = e.getClass().getSimpleName();
            return new ResponseEntity(x, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping ("/authenticate")
    public ResponseEntity authenticate (@RequestBody AuthenticationRequest authRequest){
        try {
            return new ResponseEntity (cs.authenticate(authRequest), HttpStatus.OK);
        } catch (RuntimeException e) {
            String x = e.getClass().getSimpleName();
            return new ResponseEntity(x, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping ("/upgradeRole")
    @PreAuthorize ("hasAuthority ('ADMIN')")
    public ResponseEntity upgradeRole (@RequestParam ("email") String email){
        try {
            return new ResponseEntity (cs.upgradeRole(email), HttpStatus.OK);
        } catch (RuntimeException e) {
            String x = e.getClass().getSimpleName();
            return new ResponseEntity(x, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping ("/downgradeRole")
    @PreAuthorize ("hasAuthority ('ADMIN')")
    public ResponseEntity downgradeRole (@RequestParam ("email") String email){
        try {
            return new ResponseEntity (cs.downgradeRole(email), HttpStatus.OK);
        } catch (RuntimeException e) {
            String x = e.getClass().getSimpleName();
            return new ResponseEntity (x, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping ("/getAllCustomer")
    @PreAuthorize ("hasAnyAuthority('ADMIN','INSTRUCTOR')")
    public ResponseEntity getAllUsers () {
        return new ResponseEntity (cr.findAll(), HttpStatus.OK);
    }

    @GetMapping ("/getCustomer")
    @PreAuthorize ("hasAnyAuthority('ADMIN','INSTRUCTOR')")
    public ResponseEntity getUser (@RequestParam ("email") String email) {
        try {
            return new ResponseEntity (cs.getCustomer (email), HttpStatus.OK);
        } catch (RuntimeException e) {
            String x = e.getClass().getSimpleName();
            return new ResponseEntity (x, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping ("/modifyEmail")
    @PreAuthorize ("hasAnyAuthority('ADMIN','INSTRUCTOR')")
    public ResponseEntity modifyEmail (@RequestParam ("email") String email, @RequestParam ("newEmail") String newEmail){
        try {
            return new ResponseEntity (cs.modifyEmail (email, newEmail), HttpStatus.OK);
        } catch (RuntimeException e) {
            String x = e.getClass().getSimpleName();
             return new ResponseEntity (x, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping ("/modifyPassword")
    @PreAuthorize ("hasAuthority('CUSTOMER')")
    public ResponseEntity modifyPassword (HttpServletRequest servletRequest, @RequestParam ("password") String password, @RequestParam ("newPassword") String newPassword){
        try {
            String email =jwtService.extractEmailFromRequest(servletRequest);
            return new ResponseEntity (cs.modifyPassword (email, password, newPassword), HttpStatus.OK);
        } catch (RuntimeException e) {
            String x = e.getClass().getSimpleName();
             return new ResponseEntity (x, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping ("/deleteCustomer")
    @PreAuthorize ("hasAuthority('ADMIN')")
    public ResponseEntity deleteUser (@RequestParam ("email") String email) {
        try {
            return new ResponseEntity (cs.deleteCustomer(email), HttpStatus.OK);
        } catch (RuntimeException e) {
            String x = e.getClass().getSimpleName();
            return new ResponseEntity (x, HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping ("/activeCourse")
    @PreAuthorize ("hasAnyAuthority('ADMIN','INSTRUCTOR','CUSTOMER')")
    public ResponseEntity activeCourse (HttpServletRequest servletRequest, @RequestParam ("courseCode") Integer courseCode) {
        try {
            String email =jwtService.extractEmailFromRequest(servletRequest);
            return new ResponseEntity (cs.activeCourse(email, courseCode), HttpStatus.OK);
        } catch (RuntimeException e) {
            String ex = e.getClass().getSimpleName();
            return new ResponseEntity (ex, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping ("/purchasedProd")
    @PreAuthorize ("hasAnyAuthority('ADMIN','INSTRUCTOR','CUSTOMER')")
    public ResponseEntity purchasedProd(HttpServletRequest servletRequest, @RequestParam ("productCode") Integer productCode, @RequestParam ("purchasedQnt")int purchasedQnt) {
        try {
            String email =jwtService.extractEmailFromRequest(servletRequest);
            return new ResponseEntity (cs.purchasedProd (email, productCode, purchasedQnt), HttpStatus.OK);
        } catch (RuntimeException e) {
            String ex = e.getClass().getSimpleName();
            return new ResponseEntity (ex, HttpStatus.BAD_REQUEST);
        }
    }

    
}
