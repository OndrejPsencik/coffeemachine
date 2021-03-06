package cz.psencik.coffeemachine.mvc.rest.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CoffeeMachineRestObject implements CommonRestObject<Long> {
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

    @Override
    @JsonIgnore
    public Long getId() {
        return machineId;
    }
}
