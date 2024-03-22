package net.breezeware.dao;

import net.breezeware.entity.FoodMenuItemMap;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoodMenuItemMapRepository extends JpaRepository<FoodMenuItemMap, Long> {
    List<FoodMenuItemMap> findByFoodMenuId(Long foodMenuId);
    void deleteByFoodMenuId(Long foodMenuId);
}
