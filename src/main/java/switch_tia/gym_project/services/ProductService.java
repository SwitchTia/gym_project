package switch_tia.gym_project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import switch_tia.gym_project.UTILITIES.exceptions.IncorrectDataException;
import switch_tia.gym_project.UTILITIES.exceptions.ProductAlreadyExistsException;
import switch_tia.gym_project.UTILITIES.exceptions.ProductDoesNotExistException;
import switch_tia.gym_project.entities.Product;
import switch_tia.gym_project.repositories.ProductRepository;

@Service
public class ProductService {

    @Autowired
    ProductRepository pr;


    public Product saveProduct (Product p) throws RuntimeException {
        if (pr.existsByProductCode(p.getProductCode()))  {
            throw new ProductAlreadyExistsException ();
        }
        return pr.save (p);
    }

    public String deleteProduct (int productCode) throws RuntimeException {
        Product p = pr.findByProductCode (productCode);
        if (p == null) {
            throw new IncorrectDataException();
        }
        pr.delete (p);
        String x = "The product has been deleted";
        return x;
    }

    public Product getProduct (int productCode) {
        return pr.findByProductCode(productCode);
    }

    public Product modifyProductCode (int productCode, int newProductCode) {
        Product p = pr.findByProductCode(productCode);
        if (p == null){
            throw new ProductDoesNotExistException();
        }
        p.setProductCode(newProductCode);
        pr.save (p);
        return p;
    }

    public Page<Product> findByType (String productType, int pageNr, int pageSize, String sortDirection) {
        Sort sort;
        if ("desc".equalsIgnoreCase(sortDirection)) {
            sort = Sort.by(Sort.Order.desc("productPrice"));
        } else {
            sort = Sort.by(Sort.Order.asc("productPrice"));
        }
        PageRequest pageReq = PageRequest.of(pageNr, pageSize, sort);
        return pr.findByProductType (productType, pageReq);
    }
}
