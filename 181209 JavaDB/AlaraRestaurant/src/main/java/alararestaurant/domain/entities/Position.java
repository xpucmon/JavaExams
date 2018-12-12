package alararestaurant.domain.entities;

import alararestaurant.common.Constants;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "positions")
public class Position extends BaseEntity {
    private String name;
    private Set<Employee> employees;

    public Position() {
        this.employees = new HashSet<>();
    }

    @Column(nullable = false, unique = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "position", targetEntity = Employee.class)
    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }
}
