package switch_tia.gym_project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import switch_tia.gym_project.UTILITIES.configurations.AuthenticationRequest;
import switch_tia.gym_project.UTILITIES.configurations.AuthenticationResponse;
import switch_tia.gym_project.UTILITIES.configurations.RegisterRequest;
import switch_tia.gym_project.UTILITIES.exceptions.CardBalanceNotEnoughException;
import switch_tia.gym_project.UTILITIES.exceptions.CourseDoesNotExistException;
import switch_tia.gym_project.UTILITIES.exceptions.CourseLimitExceededException;
import switch_tia.gym_project.UTILITIES.exceptions.CustomerAlreadyExistsException;
import switch_tia.gym_project.UTILITIES.exceptions.CustomerDoesNotExistException;
import switch_tia.gym_project.UTILITIES.exceptions.CustomerHasAlreadyAdminRoleException;
import switch_tia.gym_project.UTILITIES.exceptions.IncorrectDataException;
import switch_tia.gym_project.UTILITIES.exceptions.ProductQuantityExceededLimitException;
import switch_tia.gym_project.UTILITIES.exceptions.ProductDoesNotExistException;
import switch_tia.gym_project.UTILITIES.exceptions.ProductNotInTheCartException;
import switch_tia.gym_project.entities.ActiveCourse;
import switch_tia.gym_project.entities.Course;
import switch_tia.gym_project.entities.Customer;
import switch_tia.gym_project.entities.Product;
import switch_tia.gym_project.entities.PurchasedProd;
import switch_tia.gym_project.entities.ProdInCart;
import switch_tia.gym_project.entities.Role;
import switch_tia.gym_project.repositories.ActiveCourseRepository;
import switch_tia.gym_project.repositories.CourseRepository;
import switch_tia.gym_project.repositories.CustomerRepository;
import switch_tia.gym_project.repositories.ProductRepository;
import switch_tia.gym_project.repositories.PurchasedProdRepository;
import switch_tia.gym_project.repositories.ProdInCartRepository;

@Service
@RequiredArgsConstructor
public class CustomerService {
    
    @Autowired
    CustomerRepository cr;

    @Autowired
    ProductRepository pr;

    @Autowired
    CourseRepository courseRep;

    @Autowired
    ActiveCourseRepository acr;

    @Autowired
    ProdInCartRepository prodIncartRep;

    @Autowired
    PurchasedProdRepository ppr;

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
        Customer u = cr.findByEmail(authRequest.getEmail().toLowerCase());
        var jwtToken = jwtService.generateToken(u);

        return new AuthenticationResponse (jwtToken);
    }

    public Customer upgradeRole (String email) throws RuntimeException {
        Customer c = cr.findByEmail(email);
        if (!isValidEmail(email)){
            throw new IncorrectDataException ();
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
            throw new IncorrectDataException ();
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

    public Customer getCustomer (String email) throws RuntimeException {
        Customer c = cr.findByEmail(email);
        if (!isValidEmail(email)){
            throw new IncorrectDataException ();
        }
        return (c);
    }

    public Customer modifyEmail (String email, String newEmail){
        Customer c = cr.findByEmail(email);
        if (!isValidEmail(email)){
            throw new IncorrectDataException ();
        }
        if (c == null){
            throw new CustomerDoesNotExistException();
        }
        c.setEmail(newEmail);
        cr.save (c);
        return c;
    }

    public Customer modifyPassword (String email, String password, String newPassword){
        Customer c = cr.findByEmail(email);
        if (!isValidEmail(email)){
            throw new IncorrectDataException ();
        }
        if (c == null){
            throw new CustomerDoesNotExistException();
        }
        c.setPassword(newPassword);
        cr.save (c);
        return c;
    }

    public String deleteCustomer (String email) throws RuntimeException{
        Customer c = cr.findByEmail(email);
        if (!isValidEmail(email)){
            throw new IncorrectDataException ();
        }
        cr.delete (c);
        String x = "The customer " + email + " has been deleted";
        return x;
    }

    @Transactional
    public Customer activateCourse (String email, Integer courseCode) throws RuntimeException {
        Customer c = cr.findByEmail(email);
        Course course = courseRep.findByCourseCode(courseCode);
        if (!isValidEmail(email)){
            throw new IncorrectDataException();
        }
        if (c == null){
            throw new CustomerDoesNotExistException();
        }
        if (course == null){
            throw new CourseDoesNotExistException();
        }
        if (c.getActiveCourseList().size() >= 5)  {
            throw new CourseLimitExceededException ();
        }

        ActiveCourse ac = new ActiveCourse();
        ac.setCouserName(course.getCourseName());
        ac.setCourseCode(course.getCourseCode());
        ac.setCoursePrice(course.getCoursePrice());
        
        ac = acr.save(ac);

        c.getActiveCourseList().add(ac); 

        return c = cr.save(c);
    }

    public Customer addProdToCart (String email, Integer productCode, int purchasedQnt) throws RuntimeException{
        Customer c = cr.findByEmail(email);
        Product p = pr.findByProductCode(productCode);
        ProdInCart prodInCart = prodIncartRep.findByCAndP(c, p);
        if (!isValidEmail(email)){
            throw new IncorrectDataException ();
        }
        if (c == null){
            throw new CustomerDoesNotExistException();
        }
        if (p == null){
            throw new ProductDoesNotExistException();
        }
        if (p.getProductAvQnt() < purchasedQnt){
            throw new ProductQuantityExceededLimitException ();
        }
        if (prodInCart != null) {
            if (p.getProductAvQnt() > 0) {
                if (c.getCart().contains (prodInCart)) {
                    prodInCart.setProductQnt (prodInCart.getProductQnt() + purchasedQnt);
                    p.setProductAvQnt(p.getProductAvQnt() - purchasedQnt);
                    prodInCart = prodIncartRep.save (prodInCart);
                }    
            }
        }
        prodInCart = new ProdInCart(null, p.getProductName(), p.getProductCode(), purchasedQnt, p.getProductAvQnt(), p.getProductPrice(), p.getProductType(), p, c);
        prodInCart.setProductAvQnt(p.getProductAvQnt() - purchasedQnt);
        p.setProductAvQnt(p.getProductAvQnt() - purchasedQnt);
        prodInCart = prodIncartRep.save (prodInCart);
        c.getCart().add(prodInCart);
         
        return c = cr.save(c);
    }

    @Transactional
    public Customer buyProduct (String email, Integer productCode, int purchasedQnt) throws RuntimeException{
        Customer c = cr.findByEmail(email);
        Product p = pr.findByProductCode(productCode);
        ProdInCart prodInCart = prodIncartRep.findByCAndP (c, p);
        PurchasedProd pp = ppr.findByCAndProdInCart(c, prodInCart);
        double totalPurchased;

        c.setPurchased(true);
        c = cr.save(c);
        if (!isValidEmail(email)){
            throw new IncorrectDataException ();
        }
        if (c == null){
            throw new CustomerDoesNotExistException();
        }
        if (p == null){
            throw new ProductDoesNotExistException();
        }
        if(!(c.getCart().contains(prodInCart))){
            throw new ProductNotInTheCartException ();
        }
        if ((prodInCart.getProductPrice() * purchasedQnt) > c.getCardBalance()) {
            throw new CardBalanceNotEnoughException ();
        }
        if (!((purchasedQnt <= prodInCart.getProductQnt()) && prodInCart.getProductQnt() <= p.getProductAvQnt())) {
            throw new ProductQuantityExceededLimitException();
        }
        prodInCart.setProductQnt(prodInCart.getProductQnt() - purchasedQnt);

        p.setProductAvQnt(p.getProductAvQnt() - purchasedQnt);
            
        totalPurchased = prodInCart.getProductPrice() * purchasedQnt;
        c.setCardBalance(c.getCardBalance() - totalPurchased);

        if (prodInCart.getProductQnt() == 0) {
            pp = new PurchasedProd(null, prodInCart.getProductName(), prodInCart.getProductCode(), purchasedQnt, prodInCart.getProductPrice(), prodInCart.getProductType(), totalPurchased, prodInCart, c);
            ppr.save(pp);
            pr.save(p);
            c.getPurchasedList().add(pp);
            c.getCart().remove(prodInCart);
        }
        return c = cr.save(c);
    }

}
