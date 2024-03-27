package net.breezeware.dao;

import net.breezeware.entity.Availability;
import net.breezeware.entity.FoodMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FoodMenuRepository extends JpaRepository<FoodMenu, Long> {
    List<FoodMenu> findByMenuAvailability(Availability availability);
}
