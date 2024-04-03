package net.breezeware.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.breezeware.entity.FoodMenuItemMap;

@Repository
public interface FoodMenuItemMapRepository extends JpaRepository<FoodMenuItemMap, Long> {
    List<FoodMenuItemMap> findByFoodMenuId(Long foodMenuId);

    FoodMenuItemMap findByFoodMenuIdAndFoodItemId(Long foodMenuId, Long foodItemId);

    void deleteByFoodMenuId(Long foodMenuId);
}
