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
@Table (name = "active_course")
public class ActiveCourse {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    @Column (name = "id", nullable = false)
    private int id;

    @Column (name = "ac_name")
    private String acName;

    @Column (name = "ac_code", nullable = false, unique = true)
    private int acCode;

    @Column (name = "ac_price")
    private double acPrice;

    @Version
    @Column (name = "version")
    private Long version;
    
}
