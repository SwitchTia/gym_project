package switch_tia.gym_project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import switch_tia.gym_project.entities.Product;
import switch_tia.gym_project.entities.Customer;
import switch_tia.gym_project.entities.ProdInCart;

public interface ProdInCartRepository extends JpaRepository <ProdInCart, Integer>{
    ProdInCart findByCAndP(Customer c, Product p);

}
