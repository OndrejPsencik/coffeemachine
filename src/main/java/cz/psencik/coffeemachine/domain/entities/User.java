package cz.psencik.coffeemachine.domain.entities;


import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@ToString(exclude = {"password"})
@Entity
@Table(name = "USERS")
public class User {
    @Id
    @Column(name="USERNAME")
    private String username;

    private String password;

    @ElementCollection
    @CollectionTable(
            name="AUTHORITIES",
            joinColumns = @JoinColumn(name="USERNAME")
    )
    @Column(name="AUTHORITY")
    private List<String> authorities;

    @NotNull
    private Boolean enabled;
}
