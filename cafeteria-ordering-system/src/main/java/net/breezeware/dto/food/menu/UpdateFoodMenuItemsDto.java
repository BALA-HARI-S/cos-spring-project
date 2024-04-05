package net.breezeware.dto.food.menu;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import net.breezeware.dto.food.item.FoodItemDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@JsonRootName("UpdateFoodMenuItems")
@NoArgsConstructor
@AllArgsConstructor
public class UpdateFoodMenuItemsDto {
    @Schema(description = "The list food items in the menu")
    @JsonProperty("foodMenuItems")
    @NotNull(message = "Food items must not be empty")
    private List<FoodItemDto> foodMenuItemsDto;
}
