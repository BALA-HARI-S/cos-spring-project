package net.breezeware;

import net.breezeware.dao.FoodItemRepository;
import net.breezeware.entity.FoodItem;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class DataLoader implements CommandLineRunner {

    private final FoodItemRepository foodItemRepository;

    public DataLoader(FoodItemRepository foodItemRepository) {
        this.foodItemRepository = foodItemRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadSampleFoodItems();
    }

    private void loadSampleFoodItems() {
        Instant now = Instant.now();
        FoodItem foodItem1 = new FoodItem(1L,"Dosa",10.00,now,now);
        foodItemRepository.save(foodItem1);

        FoodItem foodItem2 = new FoodItem(2L,"Idli",5.00,now,now);
        foodItemRepository.save(foodItem2);

        System.out.println("Loaded Food Items: " + foodItemRepository.count());
    }
}
