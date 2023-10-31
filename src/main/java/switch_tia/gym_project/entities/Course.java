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
@Table (name = "course")
public class Course {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    @Column (name = "id", nullable = false)
    private int id;

    @Column (name = "courseName")
    private String courseName;

    @Column (name = "courseCode", nullable = false, unique = true)
    private int courseCode;

    @Column (name = "coursePrice")
    private double coursePrice;

    @Version
    @Column (name = "version")
    private Long version;
    
}
