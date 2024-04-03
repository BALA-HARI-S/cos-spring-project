package net.breezeware.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.breezeware.dto.food.order.FoodOrderDto;
import net.breezeware.entity.ErrorDetail;
import net.breezeware.exception.FoodItemException;
import net.breezeware.exception.FoodOrderException;
import net.breezeware.service.api.DeliveryService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(DeliveryController.BASE_URL)
@Tag(name = "Delivery Management")
@AllArgsConstructor
@Slf4j
public class DeliveryController {
    static final String BASE_URL = "/delivery";

    private final DeliveryService deliveryService;

    @Operation(description = "Update food order status", summary = "Update food order status",
            responses = {
                @ApiResponse(description = "Success", responseCode = "200",
                        content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                schema = @Schema(implementation = FoodOrderDto.class))),
                @ApiResponse(description = "Not Found", responseCode = "404",
                        content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                schema = @Schema(implementation = ErrorDetail.class))) })
    @PatchMapping("/update/order-status/{orderId}")
    public FoodOrderDto updateFoodOrderStatus(@PathVariable Long orderId) throws FoodOrderException, FoodItemException {
        log.info("Entering updateFoodOrderStatus() controller");
        FoodOrderDto updatedFoodOrder = deliveryService.updateFoodOrderStatus(orderId);
        log.info("Leaving updateFoodOrderStatus() controller");
        return updatedFoodOrder;
    }

}
