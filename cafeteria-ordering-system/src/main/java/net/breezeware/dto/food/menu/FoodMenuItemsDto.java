package net.breezeware.dto.food.menu;

import java.time.Instant;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import net.breezeware.dto.food.item.FoodItemDto;
import net.breezeware.entity.Availability;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@JsonRootName("FoodMenuItems")
@NoArgsConstructor
@AllArgsConstructor
public class FoodMenuItemsDto {
    @Schema(description = "The name of the food menu")
    @NotBlank(message = "Menu name must not be empty")
    private String name;

    @Schema(description = "The availability of the food menu on different days")
    private Set<Availability> menuAvailability;

    @Schema(description = "The list food items in the menu")
    @NotNull(message = "Menu food items must not be empty")
    @JsonProperty("foodMenuItems")
    private List<FoodItemDto> foodMenuItemsDto;

    @Schema(description = "The timestamp when the food menu was created")
    @NotNull(message = "Menu created date and time must not be empty")
    private Instant created;

    @Schema(description = "The timestamp when the food menu was last modified")
    @NotNull(message = "Menu modified date and time must not be empty")
    private Instant modified;
}
