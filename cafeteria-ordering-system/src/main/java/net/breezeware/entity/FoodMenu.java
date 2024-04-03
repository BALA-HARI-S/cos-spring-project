package net.breezeware.entity;

import java.time.Instant;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "food_menu")
public class FoodMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "The food menu name must not be empty")
    @Size(min = 1, max = 100)
    @Column(name = "name", length = 100)
    private String name;

    @NotNull(message = "The food menu created date and time must not be empty")
    @Column(name = "created")
    private Instant created;

    @NotNull(message = "The food menu modified date and time must not be empty")
    @Column(name = "modified")
    private Instant modified;

    @ElementCollection(targetClass = Availability.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "food_menu_availability_map")
    private Set<Availability> availability;
}
