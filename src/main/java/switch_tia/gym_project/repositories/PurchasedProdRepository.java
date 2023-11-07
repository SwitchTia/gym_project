package switch_tia.gym_project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import switch_tia.gym_project.entities.Customer;
import switch_tia.gym_project.entities.ProdInCart;
import switch_tia.gym_project.entities.PurchasedProd;

public interface PurchasedProdRepository extends JpaRepository <PurchasedProd, Integer>{
    PurchasedProd findByCAndProdInCart(Customer c, ProdInCart prodInCart);
}
