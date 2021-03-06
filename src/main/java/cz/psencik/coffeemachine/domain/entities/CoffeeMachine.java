package cz.psencik.coffeemachine.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import cz.psencik.coffeemachine.domain.listener.CoffeeMachineListener;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Entity
@ToString(exclude = {"orders"})
@EntityListeners(CoffeeMachineListener.class)
public class CoffeeMachine {
    @Id @GeneratedValue
    private Long machineId;

    @NotNull
    @NotBlank
    private String description;

    @NotNull
    @Min(0) @Max(10)
    private int floor;

    @NotNull
    @NotBlank
    private String kitchen;

    @JsonIgnore
    @OneToMany(mappedBy = "coffeeMachine")
    private List<CoffeeOrder> orders;
}
