package net.breezeware.service.api;

import java.util.List;

import net.breezeware.dto.food.menu.CreateFoodMenuDto;
import net.breezeware.dto.food.menu.FoodMenuDto;
import net.breezeware.dto.food.menu.FoodMenuItemsDto;
import net.breezeware.dto.food.menu.FoodMenuItemsQuantityDto;
import net.breezeware.dto.food.menu.UpdateFoodMenuDto;
import net.breezeware.exception.FoodItemException;
import net.breezeware.exception.FoodMenuAlreadyExistException;
import net.breezeware.exception.FoodMenuException;

public interface FoodMenuService {
    /**
     * Retrieves a list of all food menus.
     * @return                   A list of {@link FoodMenuDto} representing all food
     *                           menus.
     * @throws FoodMenuException if an error occurs while retrieving the food menus.
     */
    List<FoodMenuDto> retrieveFoodMenus() throws FoodMenuException;

    /**
     * Retrieves details of a specific food menu by its ID.
     * @param  id                The ID of the food menu to retrieve.
     * @return                   The {@link FoodMenuItemsDto} representing the
     *                           details of the food menu.
     * @throws FoodMenuException if an error occurs while retrieving the food menu.
     * @throws FoodItemException if an error occurs while retrieving the food items
     *                           associated with the menu.
     */
    FoodMenuItemsDto retrieveFoodMenu(Long id) throws FoodMenuException, FoodItemException;

    /**
     * Retrieves the food menu of the day.
     * @return                   A list of {@link FoodMenuItemsQuantityDto}
     *                           representing the food menu of the day.
     * @throws FoodMenuException if an error occurs while retrieving the food menu
     *                           of the day.
     * @throws FoodItemException if an error occurs while retrieving the food items
     *                           associated with the menu.
     */
    List<FoodMenuItemsQuantityDto> retrieveFoodMenuOfTheDay() throws FoodMenuException, FoodItemException;

    /**
     * Creates a new food menu.
     * @param  createFoodMenuDto The {@link CreateFoodMenuDto} representing the
     *                           details of the new food menu.
     * @return                   The {@link FoodMenuDto} representing the created
     *                           food menu.
     */
    FoodMenuDto createFoodMenu(CreateFoodMenuDto createFoodMenuDto) throws FoodMenuAlreadyExistException;

    /**
     * Updates an existing food menu.
     * @param  id                The ID of the food menu to update.
     * @param  updateFoodMenuDto The {@link UpdateFoodMenuDto} representing the
     *                           updated details of the food menu.
     * @return                   The {@link FoodMenuDto} representing the updated
     *                           food menu.
     * @throws FoodMenuException if an error occurs while updating the food menu.
     */
    FoodMenuDto updateFoodMenu(Long id, UpdateFoodMenuDto updateFoodMenuDto) throws FoodMenuException;

    /**
     * Deletes a food menu by its ID.
     * @param  id                The ID of the food menu to delete.
     * @throws FoodMenuException if an error occurs while deleting the food menu.
     */
    void deleteFoodMenu(Long id) throws FoodMenuException;

    /**
     * Adds a food item to a menu.
     * @param  menuId            The ID of the menu to which the food item is to be
     *                           added.
     * @param  foodItemId        The ID of the food item to add to the menu.
     * @return                   The {@link FoodMenuItemsDto} representing the
     *                           updated details of the menu.
     * @throws FoodMenuException if an error occurs while adding the food item to
     *                           the menu.
     * @throws FoodItemException if an error occurs while retrieving the food item.
     */
    FoodMenuItemsDto addFoodItemToMenu(Long menuId, Long foodItemId) throws FoodMenuException, FoodItemException;

    /**
     * Updates the quantity of a food item in a menu.
     * @param  menuId            The ID of the menu containing the food item.
     * @param  foodItemId        The ID of the food item to update.
     * @param  quantity          The new quantity of the food item.
     * @return                   The {@link FoodMenuItemsQuantityDto} representing
     *                           the updated details of the menu item.
     * @throws FoodMenuException if an error occurs while updating the quantity of
     *                           the food item.
     * @throws FoodItemException if an error occurs while retrieving the food item.
     */
    FoodMenuItemsQuantityDto updateFoodMenuItemQuantity(Long menuId, Long foodItemId, Integer quantity)
            throws FoodMenuException, FoodItemException;

    /**
     * Deletes a food item from a menu.
     * @param  foodMenuId        The ID of the menu from which the food item is to
     *                           be deleted.
     * @param  foodItemId        The ID of the food item to delete from the menu.
     * @throws FoodMenuException if an error occurs while deleting the food item
     *                           from the menu.
     */
    void deleteFoodMenuItem(Long foodMenuId, Long foodItemId) throws FoodMenuException;
}