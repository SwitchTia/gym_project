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
@Table (name = "purchased_prod")
public class PurchasedProd {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    @Column (name = "id", nullable = false)
    private int id;

    @Column (name = "pp_name")
    private String productName;

    @Column (name = "pp_code", nullable = false, unique = true)
    private int productCode;

    @Column (name = "pp_qnt")
    private int productQnt;

    @Column (name = "product_avqnt")
    private int productAvQnt;

    @Column (name = "pp_price", nullable = false)
    private double productPrice;

    @Column (name = "pp_type")
    private String productType;

    @Version
    @Column (name = "pp_version")
    private Long productVersion;

    
}
