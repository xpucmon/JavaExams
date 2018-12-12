package alararestaurant.domain.entities;

import alararestaurant.common.OrderType;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "orders")
public class Order extends BaseEntity {
    private String customer;
    private LocalDateTime dateTime;
    @Enumerated(value = EnumType.STRING)
    private String type;
    private Employee employee;
    private Set<OrderItem> orderItems;

    public Order() {
        this.orderItems = new HashSet<>();
    }

    @Column(nullable = false)
    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    @Column(name = "date_time", nullable = false)
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Column(nullable = false, columnDefinition = "enum('ForHere', 'ToGo')")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @ManyToOne(optional = false)
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @OneToMany(mappedBy = "order", targetEntity = OrderItem.class)
    public Set<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(Set<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}
