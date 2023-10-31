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
import switch_tia.gym_project.UTILITIES.exceptions.CustomerAlreadyExistsException;
import switch_tia.gym_project.UTILITIES.exceptions.CustomerDoesNotExistException;
import switch_tia.gym_project.UTILITIES.exceptions.CustomerHasAlreadyAdminRoleException;
import switch_tia.gym_project.UTILITIES.exceptions.IncorectDataException;
import switch_tia.gym_project.entities.Customer;
import switch_tia.gym_project.entities.Role;
import switch_tia.gym_project.repositories.CustomerRepository;

@Service
@RequiredArgsConstructor
public class CustomerService {
    
    @Autowired
    CustomerRepository cr;

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    
    public boolean isValidEmail (String email) {
        String regex = "\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\\b";
        return email.matches(regex);
    }

    public AuthenticationResponse registerAdmin (RegisterRequest regRequest){
        Customer c = new Customer();
        c.setFirstname(regRequest.getFirstname().toUpperCase());
        c.setLastname (regRequest.getLastname().toUpperCase());
        c.setEmail(regRequest.getEmail().toLowerCase());
        c.setPassword (passwordEncoder.encode(regRequest.getPassword()));
        c.setRole(Role.ADMIN);
        if (cr.existsByEmail(c.getEmail())){
            throw new CustomerAlreadyExistsException();
        }
        else {
            cr.save(c);
            var jwtToken = jwtService.generateToken(c);
            return new AuthenticationResponse(jwtToken);
        } 
    }

    public AuthenticationResponse registerInstructor (RegisterRequest regRequest){
        Customer c = new Customer();
        c.setFirstname(regRequest.getFirstname().toUpperCase());
        c.setLastname (regRequest.getLastname().toUpperCase());
        c.setEmail(regRequest.getEmail().toLowerCase());
        c.setPassword (passwordEncoder.encode(regRequest.getPassword()));
        c.setRole(Role.INSTRUCTOR);
        if (cr.existsByEmail(c.getEmail())){
            throw new CustomerAlreadyExistsException();
        }
        else {
            cr.save(c);
            var jwtToken = jwtService.generateToken(c);
            return new AuthenticationResponse(jwtToken);
        } 
    }

    public AuthenticationResponse registerCustomer (RegisterRequest regRequest){
        Customer c = new Customer();
        c.setFirstname(regRequest.getFirstname().toUpperCase());
        c.setLastname (regRequest.getLastname().toUpperCase());
        c.setEmail(regRequest.getEmail().toLowerCase());
        c.setPassword (passwordEncoder.encode(regRequest.getPassword()));
        c.setRole(Role.CUSTOMER);
        if (cr.existsByEmail(c.getEmail())){
            throw new CustomerAlreadyExistsException();
        }
        else {
            cr.save(c);
            var jwtToken = jwtService.generateToken(c);
            return new AuthenticationResponse(jwtToken);
        } 
    }

    public AuthenticationResponse authenticate (AuthenticationRequest authRequest) throws RuntimeException{
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
            authRequest.getEmail().toLowerCase(), 
            authRequest.getPassword()));
        Customer c = cr.findByEmail(authRequest.getEmail().toLowerCase());
        var jwtToken = jwtService.generateToken(c);

        return new AuthenticationResponse (jwtToken);
    }

    public Customer upgradeRole (String email) throws RuntimeException {
        Customer c = cr.findByEmail(email);
        if (!isValidEmail(email)){
            throw new IncorectDataException ();
        }
        if (c == null) {
            throw new CustomerDoesNotExistException ();
        }
        if (c.getRole() == Role.ADMIN){
            throw new CustomerHasAlreadyAdminRoleException();
        }
        c.setRole(Role.ADMIN);
        cr.save(c);
        return c;
    } 

    public Customer downgradeRole (String email) throws RuntimeException {
        Customer c = cr.findByEmail(email);
        if (!isValidEmail(email)){
            throw new IncorectDataException ();
        }
        if (c == null) {
            throw new CustomerDoesNotExistException ();
        }
        if (c.getRole() == Role.INSTRUCTOR){
            throw new CustomerHasAlreadyAdminRoleException();
        }
        c.setRole(Role.INSTRUCTOR);
        cr.save(c);
        return c;
    } 
}
