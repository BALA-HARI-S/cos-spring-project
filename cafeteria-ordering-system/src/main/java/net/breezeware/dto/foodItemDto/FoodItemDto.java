package net.breezeware.dto.foodItemDto;

import com.fasterxml.jackson.annotation.JsonRootName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Data
@JsonRootName("FoodItem")
@NoArgsConstructor
@AllArgsConstructor
public class FoodItemDto {
    @Schema(description = "The ID of the food item")
    private Long id;

    @Schema(description = "The food item name of the food item")
    @NotBlank(message = "name must not be empty")
    private String name;

    @Schema(description = "The food item price of the food item")
    @NotNull
    @DecimalMin(value = "0.0", message = "Value must be greater than or equal to {value}")
    @DecimalMax(value = "100.0", message = "Value must be less than or equal to {value}")
    private Double price;

    @Schema(description = "The food item instant when the food item was created")
    private Instant created;

    @Schema(description = "The food item instant when the food item was last modified")
    private Instant modified;
}
