package cz.psencik.coffeemachine.domain.entities;

import cz.psencik.coffeemachine.domain.listener.CoffeOrderListener;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@EntityListeners(CoffeOrderListener.class)
public class CoffeeOrder {
    @Id @GeneratedValue(strategy= GenerationType.AUTO)
    private Long orderId;

    @ManyToOne
    @NotNull
    private User user;

    @ManyToOne
    @NotNull
    private CoffeeMachine coffeeMachine;

    @Enumerated(EnumType.STRING)
    @NotNull
    private CoffeeOrderState coffeeOrderState;

    @Enumerated(EnumType.STRING)
    @NotNull
    private CoffeeType coffeeType;
}
