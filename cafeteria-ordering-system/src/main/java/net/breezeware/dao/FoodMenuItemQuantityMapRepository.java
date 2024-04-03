package net.breezeware.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.breezeware.entity.FoodMenuItemQuantityMap;

@Repository
public interface FoodMenuItemQuantityMapRepository extends JpaRepository<FoodMenuItemQuantityMap, Long> {
    FoodMenuItemQuantityMap findByFoodMenuItemMapId(Long foodMenuItemMapId);

    void deleteByFoodMenuItemMapId(Long foodMenuItemMapId);
}
