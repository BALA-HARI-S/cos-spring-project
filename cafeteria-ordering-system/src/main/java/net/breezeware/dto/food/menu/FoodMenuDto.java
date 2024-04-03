package net.breezeware.dto.food.menu;

import java.time.Instant;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
public class FoodMenuDto {

    @Schema(description = "The ID of the food menu")
    @NotNull(message = "Menu id must not be empty")
    private Long id;

    @Schema(description = "The name of the food menu")
    @NotBlank(message = "Menu name must not be empty")
    private String name;

    @Schema(description = "The timestamp when the food menu was created")
    @NotNull(message = "Menu created date and time must not be empty")
    private Instant created;

    @Schema(description = "The timestamp when the food menu was last modified")
    @NotNull(message = "Menu modified date and time must not be empty")
    private Instant modified;

    @Schema(description = "The availability of the food menu on different days")
    @NotNull(message = "Menu availability must not be empty")
    private Set<Availability> menuAvailability;
}
