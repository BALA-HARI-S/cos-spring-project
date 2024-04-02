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
@Table(name = "food_order")
public class FoodOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull(message = "The food order customer id must not be empty")
    @Column(name = "customer_id")
    private Long customerId;

    @NotNull(message = "The food order total cost must not be empty")
    @Column(name = "total_cost")
    private Double totalCost;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "The food order order status must not be empty")
    @Column(name = "order_status")
    private OrderStatus orderStatus;

    @NotNull(message = "The food order created date and time must not be empty")
    @Column(name = "created")
    private Instant created;
}
