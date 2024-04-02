package net.breezeware;

import lombok.AllArgsConstructor;
import net.breezeware.dao.*;
import net.breezeware.entity.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component
@AllArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final FoodItemRepository foodItemRepository;
    private final FoodMenuRepository foodMenuRepository;
    private final FoodMenuItemMapRepository foodMenuItemMapRepository;
    private final FoodMenuItemQuantityMapRepository foodMenuItemQuantityMapRepository;
    private final FoodOrderRepository foodOrderRepository;
    private final OrderFoodItemMapRepository orderFoodItemMapRepository;

    @Override
    public void run(String... args) throws Exception {
        Instant now = Instant.now();

        // Food Item
        // 1
        FoodItem foodItem1 = new FoodItem();
        foodItem1.setId(1L);
        foodItem1.setName("Dosa");
        foodItem1.setPrice(15.00);
        foodItem1.setCreated(now);
        foodItem1.setModified(now);
        foodItemRepository.save(foodItem1);
        // 2
        FoodItem foodItem2 = new FoodItem();
        foodItem2.setId(2L);
        foodItem2.setName("Idli");
        foodItem2.setPrice(10.00);
        foodItem2.setCreated(now);
        foodItem2.setModified(now);
        foodItemRepository.save(foodItem2);
        System.out.println("Loaded Food Item: " + foodItemRepository.count());


        Set<Availability> menuAvailability = new HashSet<>(Arrays.asList(Availability.MONDAY,Availability.TUESDAY,Availability.WEDNESDAY));

        // Menu
        // Menu 1
        FoodMenu foodMenu1 = new FoodMenu();
        foodMenu1.setId(1L);
        foodMenu1.setName("Standard");
        foodMenu1.setCreated(now);
        foodMenu1.setModified(now);
        foodMenu1.setMenuAvailability(menuAvailability);
        foodMenuRepository.save(foodMenu1);
        // 2
        FoodMenu foodMenu2 = new FoodMenu();
        foodMenu2.setId(2L);
        foodMenu2.setName("Special");
        foodMenu2.setCreated(now);
        foodMenu2.setModified(now);
        foodMenu2.setMenuAvailability(menuAvailability);
        foodMenuRepository.save(foodMenu2);
        System.out.println("Loaded Food Menu: " + foodMenuRepository.count());

        // Menu Item Map
        // 1
        FoodMenuItemMap foodMenuItemMap1 = new FoodMenuItemMap();
        foodMenuItemMap1.setId(1L);
        foodMenuItemMap1.setFoodMenu(foodMenu1);
        foodMenuItemMap1.setFoodItem(foodItem1);
        foodMenuItemMapRepository.save(foodMenuItemMap1);
        // 2
        FoodMenuItemMap foodMenuItemMap2 = new FoodMenuItemMap();
        foodMenuItemMap2.setId(2L);
        foodMenuItemMap2.setFoodMenu(foodMenu1);
        foodMenuItemMap2.setFoodItem(foodItem2);
        foodMenuItemMapRepository.save(foodMenuItemMap2);
        // 3
        FoodMenuItemMap foodMenuItemMap3 = new FoodMenuItemMap();
        foodMenuItemMap3.setId(3L);
        foodMenuItemMap3.setFoodMenu(foodMenu2);
        foodMenuItemMap3.setFoodItem(foodItem1);
        foodMenuItemMapRepository.save(foodMenuItemMap3);
        System.out.println("Loaded Menu Item Map: " + foodMenuItemMapRepository.count());

        // Menu Item Quantity Map
        // 1
        FoodMenuItemQuantityMap foodMenuItemQuantityMap1 = new FoodMenuItemQuantityMap();
        foodMenuItemQuantityMap1.setId(1L);
        foodMenuItemQuantityMap1.setCreated(now);
        foodMenuItemQuantityMap1.setModified(now);
        foodMenuItemQuantityMap1.setFoodMenuItemMap(foodMenuItemMap1);
        foodMenuItemQuantityMap1.setQuantity(100);
        foodMenuItemQuantityMapRepository.save(foodMenuItemQuantityMap1);
        // 2
        FoodMenuItemQuantityMap foodMenuItemQuantityMap2 = new FoodMenuItemQuantityMap();
        foodMenuItemQuantityMap2.setId(2L);
        foodMenuItemQuantityMap2.setCreated(now);
        foodMenuItemQuantityMap2.setModified(now);
        foodMenuItemQuantityMap2.setFoodMenuItemMap(foodMenuItemMap2);
        foodMenuItemQuantityMap2.setQuantity(200);
        foodMenuItemQuantityMapRepository.save(foodMenuItemQuantityMap2);
        // 3
        FoodMenuItemQuantityMap foodMenuItemQuantityMap3 = new FoodMenuItemQuantityMap();
        foodMenuItemQuantityMap3.setId(3L);
        foodMenuItemQuantityMap3.setCreated(now);
        foodMenuItemQuantityMap3.setModified(now);
        foodMenuItemQuantityMap3.setFoodMenuItemMap(foodMenuItemMap3);
        foodMenuItemQuantityMap3.setQuantity(150);
        foodMenuItemQuantityMapRepository.save(foodMenuItemQuantityMap3);
        System.out.println("Loaded Menu Item Quantity Map: " + foodMenuItemQuantityMapRepository.count());

        // Food Order
        // 1
        FoodOrder foodOrder1 = new FoodOrder();
        foodOrder1.setId(1L);
        foodOrder1.setCustomerId(1L);
        foodOrder1.setTotalCost(15.0);
        foodOrder1.setOrderStatus(OrderStatus.ORDER_CART);
        foodOrder1.setCreated(now);
        foodOrderRepository.save(foodOrder1);
        System.out.println("Loaded Food Order: " + foodOrderRepository.count());

        // Order Food Item Map
        OrderFoodItemMap orderFoodItemMap1 = new OrderFoodItemMap();
        orderFoodItemMap1.setId(1L);
        orderFoodItemMap1.setFoodOrder(foodOrder1);
        orderFoodItemMap1.setFoodMenu(foodMenu1);
        orderFoodItemMap1.setFoodItem(foodItem1);
        orderFoodItemMap1.setQuantity(1);
        orderFoodItemMapRepository.save(orderFoodItemMap1);
        System.out.println("Loaded Food Order Items: " + orderFoodItemMapRepository.count());
    }
}
