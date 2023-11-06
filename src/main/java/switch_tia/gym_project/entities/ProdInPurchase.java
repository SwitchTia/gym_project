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
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table (name = "purchased_prod")
public class ProdInPurchase {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    @Column (name = "ppID", nullable = false)
    private Integer productID;

    //@Column (name = "pp_name")
    private String productName;

    //@Column (name = "pp_code", nullable = false, unique = true)
    private int productCode;

    //@Column (name = "pp_qnt")
    private int productQnt;

    //@Column (name = "product_avqnt")
    private int productAvQnt;

   // @Column (name = "pp_price", nullable = false)
    private double productPrice;

    //@Column (name = "pp_type")
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
    
}
