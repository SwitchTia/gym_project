package switch_tia.gym_project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import switch_tia.gym_project.entities.Customer;

public interface CustomerRepository extends JpaRepository <Customer, Integer>{
    
    Customer findByEmail (String email);
    boolean existsByEmail (String email);
}
