package switch_tia.gym_project.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
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

    private String productName;

    private int productCode;

    private int productQnt;

    private double productPrice;

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

