package net.breezeware.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import net.breezeware.dto.food.menu.CreateFoodMenuDto;
import net.breezeware.dto.food.menu.FoodMenuDto;
import net.breezeware.dto.food.menu.FoodMenuItemsDto;
import net.breezeware.dto.food.menu.FoodMenuItemsQuantityDto;
import net.breezeware.dto.food.menu.UpdateFoodMenuDto;
import net.breezeware.entity.ErrorDetail;
import net.breezeware.exception.FoodItemException;
import net.breezeware.exception.FoodMenuAlreadyExistException;
import net.breezeware.exception.FoodMenuException;
import net.breezeware.service.api.FoodMenuService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(FoodMenuController.BASE_URL)
@Tag(name = "Food Menu Management")
@AllArgsConstructor
@Slf4j
public class FoodMenuController {
    static final String BASE_URL = "/food-menus";

    private FoodMenuService foodMenuService;

    @Operation(description = "Get List of all food menus", summary = "Get All Food Menus",
            responses = { @ApiResponse(description = "Success", responseCode = "200",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = FoodMenuDto.class))) })
    @GetMapping
    public List<FoodMenuDto> retrieveFoodMenus() throws FoodMenuException {
        log.info("Entering retrieveFoodMenus() controller");
        List<FoodMenuDto> foodMenuDtoList = foodMenuService.retrieveFoodMenus();
        log.info("Leaving retrieveFoodMenus() controller");
        return foodMenuDtoList;
    }

    @Operation(description = "Get food menu of the day", summary = "Get food menu of the day",
            responses = { @ApiResponse(description = "Success", responseCode = "200",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = FoodMenuItemsQuantityDto.class))) })
    @GetMapping("/today-menu")
    public List<FoodMenuItemsQuantityDto> retrieveFoodMenuOfTheDay() throws FoodMenuException, FoodItemException {
        log.info("Entering retrieveFoodMenuOfTheDay() controller");
        List<FoodMenuItemsQuantityDto> foodMenuItemsQuantityDto = foodMenuService.retrieveFoodMenuOfTheDay();
        log.info("Leaving retrieveFoodMenuOfTheDay() controller");
        return foodMenuItemsQuantityDto;
    }

    @Operation(description = "Get food menu by id", summary = "Get food menu by its id",
            responses = {
                @ApiResponse(description = "Success", responseCode = "200",
                        content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                schema = @Schema(implementation = FoodMenuItemsDto.class))),
                @ApiResponse(description = "Client Error", responseCode = "4XX",
                        content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                schema = @Schema(implementation = ErrorDetail.class))) })
    @GetMapping("/{id}")
    public FoodMenuItemsDto retrieveFoodMenu(@PathVariable Long id) throws FoodMenuException, FoodItemException {
        log.info("Entering retrieveFoodMenu() by id controller");
        FoodMenuItemsDto foodMenuItemsDto = foodMenuService.retrieveFoodMenu(id);
        log.info("Leaving retrieveFoodMenu() by id controller");
        return foodMenuItemsDto;
    }

    @Operation(description = "Create food menu", summary = "Create food menu",
            responses = {
                @ApiResponse(description = "Created", responseCode = "201",
                        content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                schema = @Schema(implementation = FoodMenuDto.class))),
                @ApiResponse(description = "Client Error", responseCode = "4XX",
                        content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                schema = @Schema(implementation = ErrorDetail.class))) })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FoodMenuDto createFoodMenu(@Valid @RequestBody CreateFoodMenuDto createFoodMenuDto)
            throws FoodMenuAlreadyExistException {
        log.info("Entering createFoodMenu() controller");
        FoodMenuDto createdFoodMenuDto = foodMenuService.createFoodMenu(createFoodMenuDto);
        log.info("Leaving createFoodMenu() controller");
        return createdFoodMenuDto;
    }

    @Operation(description = "Update menu", summary = "Update menu",
            responses = {
                @ApiResponse(description = "Success", responseCode = "200",
                        content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                schema = @Schema(implementation = FoodMenuDto.class))),
                @ApiResponse(description = "Client Error", responseCode = "4XX",
                        content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                schema = @Schema(implementation = ErrorDetail.class))) })
    @PatchMapping("/update/{id}")
    public FoodMenuDto updateFoodMenu(@PathVariable Long id, @Valid @RequestBody UpdateFoodMenuDto updateFoodMenuDto)
            throws FoodMenuException {
        log.info("Entering updateFoodMenu() controller");
        FoodMenuDto updatedFoodMenuDto = foodMenuService.updateFoodMenu(id, updateFoodMenuDto);
        log.info("Leaving updateFoodMenu() controller");
        return updatedFoodMenuDto;
    }

    @Operation(description = "Update food menu items", summary = "Update food menu items",
            responses = {
                @ApiResponse(description = "Success", responseCode = "200",
                        content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                schema = @Schema(implementation = FoodMenuItemsDto.class))),
                @ApiResponse(description = "Client Error", responseCode = "4XX",
                        content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                schema = @Schema(implementation = ErrorDetail.class))) })
    @PatchMapping("/add/food-item")
    public FoodMenuItemsDto updateFoodMenuItem(@Min(1) @RequestParam Long menuId, @Min(1) @RequestParam Long foodItemId)
            throws FoodMenuException, FoodItemException {
        log.info("Entering updateFoodMenuItem() controller");
        FoodMenuItemsDto updatedFoodMenuItemsDto = foodMenuService.addFoodItemToMenu(menuId, foodItemId);
        log.info("Leaving updateFoodMenuItem() controller");
        return updatedFoodMenuItemsDto;
    }

    @Operation(description = "Update food menu item quantity", summary = "Update food menu item quantity",
            responses = {
                @ApiResponse(description = "Success", responseCode = "200",
                        content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                schema = @Schema(implementation = FoodMenuItemsQuantityDto.class))),
                @ApiResponse(description = "Client Error", responseCode = "4XX",
                        content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                schema = @Schema(implementation = ErrorDetail.class))) })
    @PatchMapping("/update/food-item/quantity")
    public FoodMenuItemsQuantityDto updateFoodMenuItemQuantity(@Min(1) @RequestParam Long menuId,
            @Min(1) @RequestParam Long foodItemId, @Min(0) @RequestParam Integer quantity)
            throws FoodMenuException, FoodItemException {
        log.info("Entering updateFoodMenuItemQuantity() controller");
        FoodMenuItemsQuantityDto updatedFoodMenuItemQuantityDto =
                foodMenuService.updateFoodMenuItemQuantity(menuId, foodItemId, quantity);
        log.info("Leaving updateFoodMenuItemQuantity() controller");
        return updatedFoodMenuItemQuantityDto;
    }

    @Operation(description = "Delete food menu", summary = "Delete food menu",
            responses = { @ApiResponse(description = "No Content", responseCode = "204",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)) })
    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFoodMenu(@PathVariable Long id) throws FoodMenuException {
        log.info("Entering deleteFoodMenu() controller");
        foodMenuService.deleteFoodMenu(id);
        log.info("Leaving deleteFoodMenu() controller");
    }

    @Operation(description = "Delete food menu item", summary = "Delete food menu item",
            responses = { @ApiResponse(description = "No Content", responseCode = "204",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)) })
    @DeleteMapping("/delete/food-item")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFoodMenuItem(@Min(1) @RequestParam Long foodMenuId, @Min(1) @RequestParam Long foodItemId)
            throws FoodMenuException {
        log.info("Entering deleteFoodMenuItem() controller");
        foodMenuService.deleteFoodMenuItem(foodMenuId, foodItemId);
        log.info("Leaving deleteFoodMenuItem() controller");
    }
}
