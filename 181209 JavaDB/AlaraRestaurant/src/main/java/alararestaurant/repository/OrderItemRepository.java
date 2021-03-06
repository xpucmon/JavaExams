package alararestaurant.repository;

import alararestaurant.domain.entities.Item;
import alararestaurant.domain.entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
}
