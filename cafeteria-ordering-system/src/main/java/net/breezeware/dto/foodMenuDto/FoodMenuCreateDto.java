package net.breezeware.dto.foodMenuDto;

import com.fasterxml.jackson.annotation.JsonRootName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.breezeware.entity.Availability;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@JsonRootName("FoodMenu")
@NoArgsConstructor
@AllArgsConstructor
public class FoodMenuCreateDto {
    @Schema(description = "The name of the food menu")
    @NotNull
    private String name;

    @Schema(description = "The availability of the food menu on different days")
    @NotNull
    private Set<Availability> menuAvailability;
}
