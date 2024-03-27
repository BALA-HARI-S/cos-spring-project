package net.breezeware.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class FoodMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 1, max = 100)
    @Column(length = 100)
    private String name;

    @NotNull
    private Instant created;

    @NotNull
    private Instant modified;

    @ElementCollection(targetClass = Availability.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "food_menu_availability_map")
    private Set<Availability> menuAvailability;
}