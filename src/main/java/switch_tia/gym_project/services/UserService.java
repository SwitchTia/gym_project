package switch_tia.gym_project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import switch_tia.gym_project.UTILITIES.configurations.AuthenticationRequest;
import switch_tia.gym_project.UTILITIES.configurations.AuthenticationResponse;
import switch_tia.gym_project.UTILITIES.configurations.RegisterRequest;
import switch_tia.gym_project.UTILITIES.exceptions.UserAlreadyExistsException;
import switch_tia.gym_project.UTILITIES.exceptions.UserDoesNotExistException;
import switch_tia.gym_project.UTILITIES.exceptions.UserHasAlreadyAdminRoleException;
import switch_tia.gym_project.UTILITIES.exceptions.IncorectDataException;
import switch_tia.gym_project.entities.User;
import switch_tia.gym_project.entities.Role;
import switch_tia.gym_project.repositories.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
    
    @Autowired
    UserRepository ur;

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    
    public boolean isValidEmail (String email) {
        String regex = "\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\\b";
        return email.matches(regex);
    }

    public AuthenticationResponse registerAdmin (RegisterRequest regRequest){
        User u = new User();
        u.setFirstname(regRequest.getFirstname().toUpperCase());
        u.setLastname (regRequest.getLastname().toUpperCase());
        u.setEmail(regRequest.getEmail().toLowerCase());
        u.setPassword (passwordEncoder.encode(regRequest.getPassword()));
        u.setRole(Role.ADMIN);
        if (ur.existsByEmail(u.getEmail())){
            throw new UserAlreadyExistsException();
        }
        else {
            ur.save(u);
            var jwtToken = jwtService.generateToken(u);
            return new AuthenticationResponse(jwtToken);
        } 
    }

    public AuthenticationResponse registerInstructor (RegisterRequest regRequest){
        User u = new User();
        u.setFirstname(regRequest.getFirstname().toUpperCase());
        u.setLastname (regRequest.getLastname().toUpperCase());
        u.setEmail(regRequest.getEmail().toLowerCase());
        u.setPassword (passwordEncoder.encode(regRequest.getPassword()));
        u.setRole(Role.INSTRUCTOR);
        if (ur.existsByEmail(u.getEmail())){
            throw new UserAlreadyExistsException();
        }
        else {
            ur.save(u);
            var jwtToken = jwtService.generateToken(u);
            return new AuthenticationResponse(jwtToken);
        } 
    }

    public AuthenticationResponse registerCustomer (RegisterRequest regRequest){
        User u = new User();
        u.setFirstname(regRequest.getFirstname().toUpperCase());
        u.setLastname (regRequest.getLastname().toUpperCase());
        u.setEmail(regRequest.getEmail().toLowerCase());
        u.setPassword (passwordEncoder.encode(regRequest.getPassword()));
        u.setRole(Role.CUSTOMER);
        if (ur.existsByEmail(u.getEmail())){
            throw new UserAlreadyExistsException();
        }
        else {
            ur.save(u);
            var jwtToken = jwtService.generateToken(u);
            return new AuthenticationResponse(jwtToken);
        } 
    }

    public AuthenticationResponse authenticate (AuthenticationRequest authRequest) throws RuntimeException{
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
            authRequest.getEmail().toLowerCase(), 
            authRequest.getPassword()));
        User u = ur.findByEmail(authRequest.getEmail().toLowerCase());
        var jwtToken = jwtService.generateToken(u);

        return new AuthenticationResponse (jwtToken);
    }

    public User upgradeRole (String email) throws RuntimeException {
        User u = ur.findByEmail(email);
        if (!isValidEmail(email)){
            throw new IncorectDataException ();
        }
        if (u == null) {
            throw new UserDoesNotExistException ();
        }
        if (u.getRole() == Role.ADMIN){
            throw new UserHasAlreadyAdminRoleException();
        }
        u.setRole(Role.ADMIN);
        ur.save(u);
        return u;
    } 

    public User downgradeRole (String email) throws RuntimeException {
        User u = ur.findByEmail(email);
        if (!isValidEmail(email)){
            throw new IncorectDataException ();
        }
        if (u == null) {
            throw new UserDoesNotExistException ();
        }
        if (u.getRole() == Role.INSTRUCTOR){
            throw new UserHasAlreadyAdminRoleException();
        }
        u.setRole(Role.INSTRUCTOR);
        ur.save(u);
        return u;
    } 
}
