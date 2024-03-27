package net.breezeware.dao;

import net.breezeware.entity.FoodMenuItemMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodMenuItemMapRepository extends JpaRepository<FoodMenuItemMap, Long> {
    List<FoodMenuItemMap> findByFoodMenuId(Long foodMenuId);
    FoodMenuItemMap findByFoodMenuIdAndFoodItemId(Long foodMenuId, Long foodItemId);
    void deleteByFoodMenuId(Long foodMenuId);
}
