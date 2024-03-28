package net.breezeware.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.breezeware.entity.ErrorDetail;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.breezeware.dto.foodItemDto.FoodItemDto;
import net.breezeware.exception.FoodItemException;
import net.breezeware.service.api.FoodItemService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(FoodItemController.BASE_URL)
@Tag(name = "Food Item Management")
@AllArgsConstructor
@Slf4j
public class FoodItemController {
    static final String BASE_URL = "/food-items";

    private final FoodItemService foodItemService;

    @Operation(
            description = "Get List of all food items",
            summary = "Get All Food Items",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = FoodItemDto.class))
                    ),
                    @ApiResponse(
                            description = "Client Error",
                            responseCode = "4XX",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorDetail.class))
//                                            example = """
//                                                    {
//                                                      "message": "NOT_FOUND",
//                                                      "detail": "No food items found"
//                                                    }
//                                                    """))
                    )
            }
    )
    @GetMapping
    public List<FoodItemDto> retrieveFoodItems() throws FoodItemException {
        log.info("Entering retrieveFoodItems()");
        List<FoodItemDto> foodItemsList = foodItemService.retrieveFoodItems();
        log.info("Leaving retrieveFoodItems()");
        return foodItemsList;
    }

    @Operation(
            description = "Get detailed information about the food item",
            summary = "Get Food Item by Id",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = FoodItemDto.class))
                    ),
                    @ApiResponse(
                            description = "Client Error",
                            responseCode = "4XX",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorDetail.class))
//                                            example = """
//                                                    {
//                                                      "message": "NOT_FOUND",
//                                                      "detail": "Food item not found for id: 3"
//                                                    }
//                                                    """))
                    )
            }
    )
    @GetMapping(value = "/{id}")
    public FoodItemDto retrieveFoodItem(@PathVariable Long id) throws FoodItemException {
        log.info("Entering retrieveFoodItem()");
        FoodItemDto foodItemDto = foodItemService.retrieveFoodItem(id);
        log.info("Leaving retrieveFoodItem()");
        return foodItemDto;
    }

    @Operation(
            description = "Get detailed information about the food item",
            summary = "Get Food Item by name",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = FoodItemDto.class))
                    ),
                    @ApiResponse(
                            description = "Client Error",
                            responseCode = "4XX",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorDetail.class))
//                                            example = """
//                                                    {
//                                                      "message": "NOT_FOUND",
//                                                      "detail": "Food item not found for name: name"
//                                                    }
//                                                    """))
                    )
            }
    )
    @GetMapping(value = "/by-name/{name}")
    public FoodItemDto retrieveFoodItemByName(@PathVariable String name) throws FoodItemException {
        log.info("Entering retrieveFoodItemByName()");
        FoodItemDto foodItemDto = foodItemService.retrieveFoodItemByName(name);
        log.info("Leaving retrieveFoodItemByName()");
        return foodItemDto;
    }

    @Operation(
            description = "Create Food Item",
            summary = "Create Food Item",
            responses = {
                    @ApiResponse(
                            description = "Created",
                            responseCode = "201",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = FoodItemDto.class))
                    )
            }
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FoodItemDto createFoodItem(@Valid @RequestBody FoodItemDto foodItemDto) {
        log.info("Entering createFoodItem()");
        FoodItemDto foodItem = foodItemService.createFoodItem(foodItemDto);
        log.info("Leaving createFoodItem()");
        return foodItem;
    }

    @Operation(
            description = "Edit Food Item",
            summary = "Update Food Item by Id",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = FoodItemDto.class))
                    )
            }
    )
    @PatchMapping("/{id}")
    public FoodItemDto updateFoodItem(@PathVariable Long id, @Valid @RequestBody FoodItemDto foodItemDto) throws FoodItemException {
        log.info("Entering updateFoodItem()");
        FoodItemDto foodItemDto1 = foodItemService.updateFoodItem(id, foodItemDto);
        log.info("Leaving updateFoodItem()");
        return foodItemDto1;
    }

    @Operation(
            description = "Delete Food Item",
            summary = "Delete Food Item by Id",
            responses = {
                    @ApiResponse(
                            description = "NO CONTENT",
                            responseCode = "204"
                    ),
                    @ApiResponse(
                            description = "Client Error",
                            responseCode = "4XX",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorDetail.class))
//                                            example = """
//                                                    {
//                                                      "message": "NOT_FOUND",
//                                                      "detail": "Food item not found for id: 3"
//                                                    }
//                                                    """))
                    )
            }
    )
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFoodItem(@PathVariable Long id) throws FoodItemException {
        log.info("Entering deleteFoodItem()");
        foodItemService.deleteFoodItem(id);
        log.info("Leaving deleteFoodItem()");
    }

}
