package net.breezeware.dto.food.order;

import javax.validation.constraints.Min;

import com.fasterxml.jackson.annotation.JsonRootName;

import net.breezeware.entity.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@JsonRootName("UpdateFoodOrder")
@NoArgsConstructor
@AllArgsConstructor
public class UpdateFoodOrderDto {
    @Min(0)
    @Schema(description = "The total cost of the food order. Must be non-negative.")
    private Double totalCost;

    @Schema(description = "The status of the food order.")
    private OrderStatus orderStatus;
}
