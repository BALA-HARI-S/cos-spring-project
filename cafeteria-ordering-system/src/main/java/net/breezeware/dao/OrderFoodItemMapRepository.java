package net.breezeware.dao;

import net.breezeware.entity.OrderFoodItemMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface OrderFoodItemMapRepository extends JpaRepository<OrderFoodItemMap, Long> {
    List<OrderFoodItemMap> findByFoodOrderId(Long foodOrderId);
    OrderFoodItemMap findByFoodOrderIdAndFoodItemId(Long foodOrderId, Long foodItemId);
    void deleteByFoodOrderIdAndFoodItemId(Long foodOrderId, Long foodItemId);
    void deleteByFoodOrderId(Long foodOrderId);
}
