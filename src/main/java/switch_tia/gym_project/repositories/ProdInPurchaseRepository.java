package switch_tia.gym_project.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import switch_tia.gym_project.entities.Product;
import switch_tia.gym_project.entities.Customer;
import switch_tia.gym_project.entities.ProdInPurchase;

public interface ProdInPurchaseRepository extends JpaRepository <ProdInPurchase, Integer>{
    ProdInPurchase findByCAndP(Customer c, Product p);

}
