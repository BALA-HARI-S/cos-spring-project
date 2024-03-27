package net.breezeware.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class FoodMenuItemQuantityMap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private FoodMenuItemMap foodMenuItemMap;

    @NotNull
    private Integer quantity;

    @NotNull
    private Instant created;

    @NotNull
    private Instant modified;
}
