package net.breezeware.service.api;

import net.breezeware.dto.foodOrderDto.FoodOrderDto;
import net.breezeware.exception.FoodItemException;
import net.breezeware.exception.FoodOrderException;

public interface DeliveryService {
    FoodOrderDto updateFoodOrderStatus(Long orderId) throws FoodOrderException, FoodItemException;
}
