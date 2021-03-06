package cz.psencik.coffeemachine.domain.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class Registration {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long registrationId;
    private String username;
    private String password;
    private String validationCode;
}
