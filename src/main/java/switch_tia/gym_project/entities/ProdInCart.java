package switch_tia.gym_project.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table (name = "prodInCart")
public class ProdInCart {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    @Column (name = "prodInCartID", nullable = false)
    private Integer productID;

    private String productName;

    private int productCode;

    private int productQnt;

    private int productAvQnt;

    private double productPrice;

    private String productType;

    //@Version
    //@Column (name = "pp_version")
    //private Long productVersion;

    //Relationship ProdInPurchase and Product
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "productID")
    private Product p;

    //Relationship ProdInPurchase and Customer
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "customerID")
    private Customer c;

    //Relationship ProdInPurchase and PurchasedProd
    //@OneToOne
    //@JoinColumn (name = "productID")
    //private PurchasedProd pp;
    
    
}
