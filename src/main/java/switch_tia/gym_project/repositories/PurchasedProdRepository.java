package switch_tia.gym_project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import switch_tia.gym_project.entities.Product;
import switch_tia.gym_project.entities.PurchasedProd;

public interface PurchasedProdRepository extends JpaRepository <PurchasedProd, Integer>{
    PurchasedProd findByProductCode (int productCode);
    boolean existsByProductCode (int productCode);

}
