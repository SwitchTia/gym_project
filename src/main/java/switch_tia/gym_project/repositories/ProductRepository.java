package switch_tia.gym_project.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import switch_tia.gym_project.entities.Product;

public interface ProductRepository extends JpaRepository <Product, Integer>{
    
    Product findByProductCode (int productCode);
    boolean existsByProductCode (int productCode);

    Page <Product> findByProductType (String productType, PageRequest pageRequest);
}
