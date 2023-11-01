package switch_tia.gym_project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import switch_tia.gym_project.UTILITIES.configurations.AuthenticationRequest;
import switch_tia.gym_project.UTILITIES.configurations.RegisterRequest;
import switch_tia.gym_project.repositories.UserRepository;
import switch_tia.gym_project.services.UserService;
import switch_tia.gym_project.services.JwtService;

@RestController
@RequestMapping ("/user")
public class UserController {

    @Autowired
    UserService us;

    @Autowired
    UserRepository ur;

    @Autowired
    JwtService jwtService;

    @PostMapping ("/registerAdmin")
    public ResponseEntity registerAdmin (@RequestBody RegisterRequest regRequest){
        try {
            return new ResponseEntity (us.registerAdmin(regRequest), HttpStatus.OK);
        } catch (RuntimeException e) {
            String ex = e.getClass().getSimpleName();
            return new ResponseEntity(ex, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping ("/registerInstructor")
    public ResponseEntity registerInstructor (@RequestBody RegisterRequest regRequest){
        try {
            return new ResponseEntity (us.registerInstructor(regRequest), HttpStatus.OK);
        } catch (RuntimeException e) {
            String ex = e.getClass().getSimpleName();
            return new ResponseEntity(ex, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping ("/registerCustomer")
    public ResponseEntity registerCustomer (@RequestBody RegisterRequest regRequest){
        try {
            return new ResponseEntity (us.registerCustomer(regRequest), HttpStatus.OK);
        } catch (RuntimeException e) {
            String ex = e.getClass().getSimpleName();
            return new ResponseEntity(ex, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping ("/authenticate")
    public ResponseEntity authenticate (@RequestBody AuthenticationRequest authRequest){
        try {
            return new ResponseEntity (us.authenticate(authRequest), HttpStatus.OK);
        } catch (RuntimeException e) {
            String ex = e.getClass().getSimpleName();
            return new ResponseEntity(ex, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping ("/upgradeRole")
    @PreAuthorize ("hasAuthority ('ADMIN')")
    public ResponseEntity upgradeRole (@RequestParam ("email") String email){
        try {
            return new ResponseEntity (us.upgradeRole(email), HttpStatus.OK);
        } catch (RuntimeException e) {
            String ex = e.getClass().getSimpleName();
            return new ResponseEntity(ex, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping ("/downgradeRole")
    @PreAuthorize ("hasAuthority ('ADMIN')")
    public ResponseEntity downgradeRole (@RequestParam ("email") String email){
        try {
            return new ResponseEntity (us.downgradeRole(email), HttpStatus.OK);
        } catch (RuntimeException e) {
            String ex = e.getClass().getSimpleName();
            return new ResponseEntity(ex, HttpStatus.BAD_REQUEST);
        }
    }

    
}
