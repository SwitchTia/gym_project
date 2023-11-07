package switch_tia.gym_project.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table (name = "product")
public class Product {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    @Column (name = "productID", nullable = false)
    private Integer productID;

    @Column (name = "prod_name")
    private String productName;

    @Column (name = "product_code", nullable = false, unique = true)
    private int productCode;

    @Column (name = "product_avqnt")
    private int productAvQnt;

    @Column (name = "product_price", nullable = false)
    private double productPrice;

    @Column (name = "product_type")
    private String productType;

    @Version
    @Column (name = "product_version")
    private Long productVersion;

    /*Relationship Product and ProdInPurchase
    @JsonIgnore
    @OneToMany
    @JoinColumn (name = "productID")
    private List <ProdInPurchase> ppList = new ArrayList <> (); ;
    */
}
