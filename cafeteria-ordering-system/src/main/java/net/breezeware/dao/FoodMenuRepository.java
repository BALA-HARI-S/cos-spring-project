package net.breezeware.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.breezeware.entity.Availability;
import net.breezeware.entity.FoodMenu;

@Repository
public interface FoodMenuRepository extends JpaRepository<FoodMenu, Long> {
    List<FoodMenu> findByMenuAvailability(Availability availability);
}
