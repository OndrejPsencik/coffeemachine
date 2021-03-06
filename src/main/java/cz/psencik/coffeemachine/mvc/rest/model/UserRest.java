package cz.psencik.coffeemachine.mvc.rest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.List;

@Data
public class UserRest implements CommonRestObject<String> {
    private String username;
    private List<String> authorities;
    private Boolean enabled;

    @Override
    @JsonIgnore
    public String getId() {
        return username;
    }
}
