package net.breezeware.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.breezeware.dto.foodOrderDto.CreateFoodOrderDto;
import net.breezeware.dto.foodOrderDto.FoodOrderDto;
import net.breezeware.dto.foodOrderDto.UpdateFoodOrderDto;
import net.breezeware.entity.ErrorDetail;
import net.breezeware.exception.FoodItemException;
import net.breezeware.exception.FoodOrderException;
import net.breezeware.service.api.FoodOrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(FoodOrderController.BASE_URL)
@Tag(name = "Food Order Management")
@AllArgsConstructor
@Slf4j
public class FoodOrderController {
    static final String BASE_URL = "/food-order";

    private final FoodOrderService foodOrderService;

    @Operation(
            description = "Get List of all food orders",
            summary = "Get list of all food orders",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = FoodOrderDto.class))
                    )
            }
    )
    @GetMapping
    public List<FoodOrderDto> retrieveFoodOrders() throws FoodItemException {
        log.info("Entering retrieveFoodOrders() controller");
        List<FoodOrderDto> foodOrderDtoList = foodOrderService.retrieveFoodOrders();
        log.info("Leaving retrieveFoodOrders() controller");
        return foodOrderDtoList;
    }

    @Operation(
            description = "Get food order by id",
            summary = "Get food order by id",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = FoodOrderDto.class))
                    ),
                    @ApiResponse(
                            description = "Not Found",
                            responseCode = "404",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorDetail.class))
                    )
            }
    )
    @GetMapping("/{id}")
    public FoodOrderDto retrieveFoodOrder(@PathVariable Long id) throws FoodItemException, FoodOrderException {
        log.info("Entering retrieveFoodMenus() controller");
        FoodOrderDto foodOrderDto = foodOrderService.retrieveFoodOrder(id);
        log.info("Leaving retrieveFoodMenus() controller");
        return foodOrderDto;
    }

    @Operation(
            description = "Create food order",
            summary = "Create food order",
            responses = {
                    @ApiResponse(
                            description = "Created",
                            responseCode = "201",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = FoodOrderDto.class))
                    )
            }
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FoodOrderDto createFoodOrder(@RequestBody CreateFoodOrderDto createFoodOrderDto) throws FoodItemException {
        log.info("Entering createFoodOrder() controller");
        FoodOrderDto foodOrderDto = foodOrderService.createFoodOrder(createFoodOrderDto);
        log.info("Leaving createFoodOrder() controller");
        return foodOrderDto;
    }

    @Operation(
            description = "Update food order",
            summary = "Update food order",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = FoodOrderDto.class))
                    ),
                    @ApiResponse(
                            description = "Not Found",
                            responseCode = "404",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorDetail.class))
                    )
            }
    )
    @PatchMapping("update-order/{orderId}")
    public FoodOrderDto updateFoodOrder(@PathVariable Long orderId,@RequestBody UpdateFoodOrderDto updateFoodOrderDto) throws FoodItemException, FoodOrderException {
        log.info("Entering updateFoodOrder() controller");
        FoodOrderDto foodOrderDto = foodOrderService.updateFoodOrder(orderId,updateFoodOrderDto);
        log.info("Leaving updateFoodOrder() controller");
        return foodOrderDto;
    }

    @Operation(
            description = "Add food item to order",
            summary = "Add food item to order",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = FoodOrderDto.class))
                    ),
                    @ApiResponse(
                            description = "Not Found",
                            responseCode = "404",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorDetail.class))
                    )
            }
    )
    @PostMapping("add-food-item")
    public FoodOrderDto addFoodItemToOrder(@RequestParam Long orderId,@RequestParam Long foodItemId,@RequestParam Integer quantity) throws FoodItemException, FoodOrderException {
        log.info("Entering addFoodItemToOrder() controller");
        FoodOrderDto foodOrderDto = foodOrderService.addFoodItemToOrder(orderId,foodItemId,quantity);
        log.info("Leaving addFoodItemToOrder() controller");
        return foodOrderDto;
    }

    @Operation(
            description = "Delete order food item",
            summary = "Delete order food item",
            responses = {
                    @ApiResponse(
                            description = "No Content",
                            responseCode = "204",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
                    ),
                    @ApiResponse(
                            description = "Not Found",
                            responseCode = "404",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorDetail.class))
                    )
            }
    )
    @DeleteMapping("delete-order-food-item")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrderFoodItem(@RequestParam Long orderId,@RequestParam Long foodItemId) throws FoodOrderException {
        log.info("Entering deleteOrderFoodItem() controller");
        foodOrderService.deleteOrderFoodItem(orderId,foodItemId);
        log.info("Leaving deleteOrderFoodItem() controller");
    }

    @Operation(
            description = "Delete food order",
            summary = "Delete food order",
            responses = {
                    @ApiResponse(
                            description = "No Content",
                            responseCode = "204",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
                    ),
                    @ApiResponse(
                            description = "Not Found",
                            responseCode = "404",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorDetail.class))
                    )
            }
    )
    @DeleteMapping("delete-food-order/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFoodOrder(@PathVariable Long id) throws FoodOrderException {
        log.info("Entering deleteFoodOrder() controller");
        foodOrderService.deleteFoodOrder(id);
        log.info("Leaving deleteFoodOrder() controller");
    }


}