package net.breezeware.dao;

import net.breezeware.entity.FoodMenu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FoodMenuRepository extends JpaRepository<FoodMenu, Long> {
}
