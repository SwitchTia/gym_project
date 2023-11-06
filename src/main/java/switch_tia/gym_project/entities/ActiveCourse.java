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
@Table (name = "active_course")
public class ActiveCourse {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    @Column (name = "acID", nullable = false)
    private Integer courseID;

    @Column (name = "ac_name")
    private String couserName;

    @Column (name = "ac_code", nullable = false, unique = true)
    private int courseCode;

    @Column (name = "ac_price")
    private double coursePrice;

    @Version
    @Column (name = "version")
    private Long version;

    //Reverse relationship ActiveCourse Customer
    /*@ManyToOne
    @JsonIgnore
    @JoinColumn (name = "customer_id")
    private Customer c;
    */
}
