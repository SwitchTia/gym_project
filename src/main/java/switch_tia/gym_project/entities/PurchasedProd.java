package switch_tia.gym_project.entities;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table (name = "purchasedProd")
public class PurchasedProd {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    @Column (name = "productID", nullable = false)
    private Integer productID;

    //@Column (name = "prod_name")
    private String productName;

    //@Column (name = "product_code", nullable = false, unique = true)
    private int productCode;

    //@Column (name = "product_qnt")
    private int productQnt;

    //@Column (name = "product_price", nullable = false)
    private double productPrice;

    //@Column (name = "product_type")
    private String productType;

    private double purchasedAmount;

    //Relationship PurchasedProd and ProdInPurchase
    @JsonIgnore
    @OneToOne
    @JoinColumn (name = "productID")
    private ProdInCart prodInCart;


    //Relationship PurchasedProd and Customer
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "customerID")
    private Customer c;
    
}

