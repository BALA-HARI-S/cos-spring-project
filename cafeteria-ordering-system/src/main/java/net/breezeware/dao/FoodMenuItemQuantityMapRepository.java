package net.breezeware.dao;

import net.breezeware.entity.FoodMenuItemQuantityMap;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodMenuItemQuantityMapRepository extends JpaRepository<FoodMenuItemQuantityMap, Long> {
    void deleteByFoodMenuItemMapId(Long foodMenuItemMapId);
}
