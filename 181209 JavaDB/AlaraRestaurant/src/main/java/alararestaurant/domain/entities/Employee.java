package alararestaurant.domain.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "employees")
public class Employee extends BaseEntity {
    private String name;
    private Integer age;
    private Position position;
    private Set<Order> orders;

    public Employee() {
        this.orders = new HashSet<>();
    }

    @Column(nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(nullable = false)
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @ManyToOne(optional = false)
    @JoinColumn(name = "position_id", referencedColumnName = "id")
    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    @OneToMany(mappedBy = "employee", targetEntity = Order.class)
    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }
}
