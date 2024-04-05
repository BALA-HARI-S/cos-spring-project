package net.breezeware.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.breezeware.entity.Availability;
import net.breezeware.entity.FoodMenu;

@Repository
public interface FoodMenuRepository extends JpaRepository<FoodMenu, Long> {
    List<FoodMenu> findByAvailability(Availability availability);
    Optional<FoodMenu> findByName(String name);
}
