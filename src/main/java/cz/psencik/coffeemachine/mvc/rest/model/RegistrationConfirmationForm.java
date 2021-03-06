package cz.psencik.coffeemachine.mvc.rest.model;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class RegistrationConfirmationForm {
    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String code;
}
