package net.breezeware.dto.food.menu;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonRootName;

import net.breezeware.entity.Availability;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@JsonRootName("FoodMenu")
@NoArgsConstructor
@AllArgsConstructor
public class UpdateFoodMenuDto {
    @Schema(description = "The name of the food menu")
    private String name;

    @Schema(description = "The availability of the food menu on different days")
    private Set<Availability> menuAvailability;
}
