package net.breezeware.dto.food.menu;

import java.util.Map;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonRootName;

import net.breezeware.dto.food.item.FoodItemDto;
import net.breezeware.entity.Availability;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@JsonRootName("FoodMenuItemsQuantity")
@NoArgsConstructor
@AllArgsConstructor
public class FoodMenuItemsQuantityDto {
    @Schema(description = "The name of the food menu")
    @NotBlank(message = "Menu name must not be empty")
    private String name;

    @Schema(description = "The availability of the food menu on different days")
    @NotNull(message = "Menu availability must not be empty")
    private Set<Availability> menuAvailability;

    @Schema(description = "The list food items in the menu")
    @NotNull(message = "Food menu food items and its quantities must not be empty")
    private Map<FoodItemDto, Integer> foodMenuItemsQuantity;
}
