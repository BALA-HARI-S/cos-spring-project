package net.breezeware.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.breezeware.dao.FoodOrderRepository;
import net.breezeware.dto.foodOrderDto.FoodOrderDto;
import net.breezeware.entity.FoodOrder;
import net.breezeware.entity.OrderStatus;
import net.breezeware.exception.FoodItemException;
import net.breezeware.exception.FoodOrderException;
import net.breezeware.service.api.DeliveryService;
import net.breezeware.service.api.FoodOrderService;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class DeliveryServiceImpl implements DeliveryService {

    private FoodOrderRepository foodOrderRepository;
    private FoodOrderService foodOrderService;
    @Override
    public FoodOrderDto updateFoodOrderStatus(Long orderId) throws FoodOrderException, FoodItemException {
        log.info("Entering updateFoodOrderStatus() service");
        FoodOrder foodOrder = foodOrderRepository.findById(orderId).orElseThrow(() ->
                new FoodOrderException("Food order not found for id: " + orderId));

        foodOrder.setOrderStatus(OrderStatus.ORDER_DELIVERED);
        FoodOrder updatedFoodOrder = foodOrderRepository.save(foodOrder);
        log.info("Leaving updateFoodOrderStatus() service");
        return foodOrderService.retrieveFoodOrder(updatedFoodOrder.getId());
    }
}
