package net.breezeware.entity;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
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
@Table(name = "food_item")
public class FoodItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "The food item name must not be empty")
    @Size(min = 1, max = 100)
    @Column(name = "name")
    private String name;

    @NotNull(message = "The food item price must not be empty")
    @Min(0)
    @Column(name = "price")
    private Double price;

    @NotNull(message = "The food item created date and time must not be empty")
    @Column(name = "created")
    private Instant created;

    @NotNull(message = "The food item modified date and time must not be empty")
    @Column(name = "modified")
    private Instant modified;
}
