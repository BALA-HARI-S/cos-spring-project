package net.breezeware.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.breezeware.dto.*;
import net.breezeware.entity.ErrorDetail;
import net.breezeware.exception.FoodItemException;
import net.breezeware.exception.FoodMenuException;
import net.breezeware.service.api.FoodMenuService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(FoodMenuController.BASE_URL)
@Tag(name = "Food Menu Management")
@AllArgsConstructor
@Slf4j
public class FoodMenuController {
    static final String BASE_URL = "/food-menu";

    private FoodMenuService foodMenuService;


    @Operation(
            description = "Get List of all food menus",
            summary = "Get All Food Menus",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = FoodMenuDto.class))
                    )
            }
    )
    @GetMapping
    public List<FoodMenuDto> retrieveFoodMenus() throws FoodMenuException {
        log.info("Entering retrieveFoodMenus() controller");
        List<FoodMenuDto> foodMenuDtoList = foodMenuService.retrieveFoodMenus();
        log.info("Leaving retrieveFoodMenus() controller");
        return foodMenuDtoList;
    }

    @Operation(
            description = "Get food menu of the day",
            summary = "Get food menu of the day",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = FoodMenuDto.class))
                    )
            }
    )
    @GetMapping("/today-menu/{id}")
    public FoodMenuItemsQuantityDto retrieveFoodMenuOfTheDay(@PathVariable Long id) throws FoodMenuException, FoodItemException {
        log.info("Entering retrieveFoodMenuOfTheDay() controller");
        FoodMenuItemsQuantityDto foodMenuItemsQuantityDto = foodMenuService.retrieveFoodMenuOfTheDay(id);
        log.info("Leaving retrieveFoodMenuOfTheDay() controller");
        return foodMenuItemsQuantityDto;
    }

    @Operation(
            description = "Get food menu by id",
            summary = "Get food menu by its id",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = FoodMenuItemsDto.class))
                    ),
                    @ApiResponse(
                            description = "Client Error",
                            responseCode = "4XX",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorDetail.class))
                    )
            }
    )
    @GetMapping("/{id}")
    public FoodMenuItemsDto retrieveFoodMenu(@PathVariable Long id) throws FoodMenuException, FoodItemException {
        log.info("Entering retrieveFoodMenu() by id controller");
        FoodMenuItemsDto foodMenuItemsDto = foodMenuService.retrieveFoodMenu(id);
        log.info("Leaving retrieveFoodMenu() by id controller");
        return foodMenuItemsDto;
    }

    @Operation(
            description = "Create food menu",
            summary = "Create food menu",
            responses = {
                    @ApiResponse(
                            description = "Created",
                            responseCode = "201",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = FoodMenuDto.class))
                    )
            }
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FoodMenuDto createFoodMenu(@Valid @RequestBody FoodMenuCreateDto foodMenuCreateDto) throws FoodMenuException {
        log.info("Entering createFoodMenu() controller");
        FoodMenuDto createdFoodMenuDto = foodMenuService.createFoodMenu(foodMenuCreateDto);
        log.info("Leaving createFoodMenu() controller");
        return createdFoodMenuDto;
    }

    @Operation(
            description = "Update food menu",
            summary = "Update food menu",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = FoodMenuDto.class))
                    ),
                    @ApiResponse(
                            description = "Client Error",
                            responseCode = "4XX",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorDetail.class))
                    )
            }
    )
    @PatchMapping("/{id}")
    public FoodMenuDto updateFoodMenu(@PathVariable Long id,@Valid @RequestBody FoodMenuUpdateDto foodMenuUpdateDto) throws FoodMenuException {
        log.info("Entering updateFoodMenu() controller");
        FoodMenuDto updatedFoodMenuDto = foodMenuService.updateFoodMenu(id,foodMenuUpdateDto);
        log.info("Leaving updateFoodMenu() controller");
        return updatedFoodMenuDto;
    }

    @Operation(
            description = "Delete food menu",
            summary = "Delete food menu",
            responses = {
                    @ApiResponse(
                            description = "No Content",
                            responseCode = "204",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
                    )
            }
    )
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFoodMenu(@PathVariable Long id) throws FoodMenuException {
        log.info("Entering updateFoodMenu() controller");
        foodMenuService.deleteFoodMenu(id);
        log.info("Leaving updateFoodMenu() controller");
    }
}
