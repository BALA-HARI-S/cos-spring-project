package net.breezeware.dao;

import net.breezeware.entity.FoodMenuItemQuantityMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodMenuItemQuantityMapRepository extends JpaRepository<FoodMenuItemQuantityMap, Long> {
    FoodMenuItemQuantityMap findByFoodMenuItemMapId(Long foodMenuItemMapId);
    void deleteByFoodMenuItemMapId(Long foodMenuItemMapId);
}
