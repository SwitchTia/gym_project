package switch_tia.gym_project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import switch_tia.gym_project.entities.User;

public interface UserRepository extends JpaRepository <User, Integer>{
    
    User findByEmail (String email);
    boolean existsByEmail (String email);
}
