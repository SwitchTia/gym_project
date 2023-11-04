package switch_tia.gym_project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import switch_tia.gym_project.entities.Product;
import switch_tia.gym_project.repositories.ProductRepository;
import switch_tia.gym_project.services.ProductService;

@RestController
@RequestMapping ("/product")
public class ProductController {
    
    @Autowired
    ProductRepository pr;

    @Autowired
    ProductService ps;
    

    @PostMapping ("/saveProduct")
    @PreAuthorize ("hasAuthority ('ADMIN')")
    public ResponseEntity saveProduct (@RequestBody Product p){
        try {
            return new ResponseEntity (ps.saveProduct(p), HttpStatus.OK);
        } catch (RuntimeException e) {
            String ex = e.getClass().getSimpleName();
            return new ResponseEntity (ex, HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping ("/deleteProduct")
    @PreAuthorize ("hasAuthority ('ADMIN')")
    public ResponseEntity deleteProduct (@RequestParam ("productCode") int productCode ) {
        try {
            return new ResponseEntity (ps.deleteProduct(productCode), HttpStatus.OK);
        } catch (RuntimeException e) {
            String ex = e.getClass().getSimpleName();
            return new ResponseEntity (ex, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping ("/getAllProducts")
    @PreAuthorize ("hasAnyAuthority ('ADMIN','INSTRUCTOR')")
    public ResponseEntity getAllProducts () {
        return new ResponseEntity (pr.findAll(), HttpStatus.OK);
    }

    @GetMapping ("/getProduct")
    @PreAuthorize ("hasAnyAuthority ('ADMIN','INSTRUCTOR','CUSTOMER')")
    public ResponseEntity getProduct (@RequestParam ("productCode") int productCode) {
        try {
            return new ResponseEntity (ps.getProduct(productCode), HttpStatus.OK);
        } catch (RuntimeException e) {
            String ex = e.getClass().getSimpleName();
            return new ResponseEntity (ex, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping ("/modifyProductCode")
    @PreAuthorize ("hasAuthority ('ADMIN')")
    public ResponseEntity modifyProductCode (@RequestParam ("productCode") int productCode, @RequestParam ("newProductCode") int newProductCode){
        try {
            return new ResponseEntity (ps.modifyProductCode (productCode, newProductCode), HttpStatus.OK);
        } catch (RuntimeException e) {
            String ex = e.getClass().getSimpleName();
            return new ResponseEntity (ex, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping ("/findByType")
    @PreAuthorize ("hasAnyAuthority ('ADMIN','INSTRUCTOR')")
    public Page <Product> findByProductType (@RequestParam ("productType") String productType, @RequestParam ("pageNr") int pageNr, @RequestParam ("pageSize") int pageSize, @RequestParam ("sortDirection") String sortDirection){
        return ps.findByType (productType, pageNr, pageSize, sortDirection);
    }

}
