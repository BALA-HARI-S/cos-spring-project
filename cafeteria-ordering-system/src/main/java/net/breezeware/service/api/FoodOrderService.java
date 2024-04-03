package net.breezeware.service.api;

import java.util.List;

import net.breezeware.dto.food.order.CreateFoodOrderDto;
import net.breezeware.dto.food.order.FoodOrderDto;
import net.breezeware.dto.food.order.UpdateFoodOrderDto;
import net.breezeware.exception.FoodItemException;
import net.breezeware.exception.FoodMenuException;
import net.breezeware.exception.FoodOrderException;

public interface FoodOrderService {
    /**
     * Retrieves all food orders.
     * @return                   List of FoodOrderDto representing all food orders.
     * @throws FoodItemException if there is an error retrieving food items
     *                           associated with the orders.
     */
    List<FoodOrderDto> retrieveFoodOrders() throws FoodItemException;

    /**
     * Retrieves a specific food order by its ID.
     * @param  id                 The ID of the food order to retrieve.
     * @return                    FoodOrderDto representing the retrieved food
     *                            order.
     * @throws FoodOrderException if the specified food order is not found or if
     *                            there is an error retrieving it.
     * @throws FoodItemException  if there is an error retrieving food items
     *                            associated with the order.
     */
    FoodOrderDto retrieveFoodOrder(Long id) throws FoodOrderException, FoodItemException;

    /**
     * Creates a new food order.
     * @param  createFoodOrderDto Details of the food order to create.
     * @return                    FoodOrderDto representing the newly created food
     *                            order.
     * @throws FoodItemException  if there is an error related to food items while
     *                            creating the order.
     */
    FoodOrderDto createFoodOrder(CreateFoodOrderDto createFoodOrderDto)
            throws FoodItemException, FoodMenuException, FoodOrderException;

    /**
     * Updates an existing food order.
     * @param  orderId            The ID of the food order to update.
     * @param  updateFoodOrderDto Details of the updates to be applied to the food
     *                            order.
     * @return                    FoodOrderDto representing the updated food order.
     * @throws FoodOrderException if the specified food order is not found or if
     *                            there is an error updating it.
     * @throws FoodItemException  if there is an error related to food items while
     *                            updating the order.
     */
    FoodOrderDto updateFoodOrder(Long orderId, UpdateFoodOrderDto updateFoodOrderDto)
            throws FoodOrderException, FoodItemException;

    /**
     * Adds a food item to an existing food order.
     * @param  orderId            The ID of the food order to which the item will be
     *                            added.
     * @param  foodItemId         The ID of the food item to add.
     * @param  quantity           The quantity of the food item to add.
     * @return                    FoodOrderDto representing the updated food order
     *                            after adding the item.
     * @throws FoodOrderException if the specified food order is not found or if
     *                            there is an error adding the item.
     * @throws FoodItemException  if there is an error related to food items while
     *                            adding to the order.
     */
    FoodOrderDto addFoodItemToOrder(Long orderId, Long menuId, Long foodItemId, Integer quantity)
            throws FoodOrderException, FoodItemException, FoodMenuException;

    /**
     * Deletes a food item from a food order.
     * @param  orderId            The ID of the food order from which the item will
     *                            be deleted.
     * @param  foodItemId         The ID of the food item to delete from the order.
     * @throws FoodOrderException if the specified food order or food item is not
     *                            found or if there is an error deleting the item.
     */
    void deleteOrderFoodItem(Long orderId, Long foodItemId) throws FoodOrderException;

    /**
     * Deletes a food order by its ID.
     * @param  orderId            The ID of the food order to delete.
     * @throws FoodOrderException if the specified food order is not found or if
     *                            there is an error deleting it.
     */
    void deleteFoodOrder(Long orderId) throws FoodOrderException;
}
