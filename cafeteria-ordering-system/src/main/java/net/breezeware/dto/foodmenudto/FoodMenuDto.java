package net.breezeware.dto.foodmenudto;

import com.fasterxml.jackson.annotation.JsonRootName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.breezeware.entity.Availability;

import java.time.Instant;
import java.util.Set;

@Data
@JsonRootName("FoodMenu")
@NoArgsConstructor
@AllArgsConstructor
public class FoodMenuDto {

    @Schema(description = "The ID of the food menu")
    private Long id;

    @Schema(description = "The name of the food menu")
    private String name;

    @Schema(description = "The timestamp when the food menu was created")
    private Instant created;

    @Schema(description = "The timestamp when the food menu was last modified")
    private Instant modified;

    @Schema(description = "The availability of the food menu on different days")
    private Set<Availability> menuAvailability;
}
