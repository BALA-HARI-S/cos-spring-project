package net.breezeware.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.breezeware.entity.OrderFoodItemMap;

@Repository
public interface OrderFoodItemMapRepository extends JpaRepository<OrderFoodItemMap, Long> {
    List<OrderFoodItemMap> findByFoodOrderId(Long foodOrderId);

    OrderFoodItemMap findByFoodOrderIdAndFoodItemId(Long foodOrderId, Long foodItemId);

    void deleteByFoodOrderIdAndFoodItemId(Long foodOrderId, Long foodItemId);

    void deleteByFoodOrderId(Long foodOrderId);
}
