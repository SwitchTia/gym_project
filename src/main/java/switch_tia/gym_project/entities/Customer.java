package switch_tia.gym_project.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table (name = "customer")
public class Customer implements UserDetails{
    
    
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    @Column (name = "customer_id", nullable = false)
    private int customerId;

    @Column (name = "firstname")
    private String firstname;

    @Column (name = "lastname")
    private String lastname;

    @Column (name = "email", nullable = false, unique = true)
    private String email;

    @Column (name = "password")
    private String password;

    @Column (name = "purchased", nullable = false)
    private boolean purchased;

    @Enumerated  (EnumType.STRING) 
    private Role role;

    //Relationship Customer Course
    @ManyToMany
    @JoinColumn (name = "course_code")
    private List <Course> courseList = new ArrayList <> ();

    //Relationship Users and ActiveTask
    @OneToMany
    @JsonIgnore
    @JoinColumn (name = "active_course")
    private List <ActiveCourse> activeCourseList = new ArrayList <> ();
    
    //Relationship Customer Product
    @ManyToMany
    @JoinColumn (name = "product_code")
    private List <Product> purchasedList = new ArrayList <> ();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    
}
