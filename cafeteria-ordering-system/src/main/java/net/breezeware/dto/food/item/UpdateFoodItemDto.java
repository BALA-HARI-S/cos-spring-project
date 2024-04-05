package net.breezeware.dto.food.item;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;

import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@JsonRootName("CreateFoodItem")
@NoArgsConstructor
@AllArgsConstructor
public class UpdateFoodItemDto {

    @Schema(description = "The food item name of the food item")
    private String name;

    @Schema(description = "The food item price of the food item")
    @DecimalMin(value = "0.0", message = "Value must be greater than or equal to {value}")
    @DecimalMax(value = "100.0", message = "Value must be less than or equal to {value}")
    private Double price;
}
