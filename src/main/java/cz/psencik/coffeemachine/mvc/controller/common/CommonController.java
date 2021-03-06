package cz.psencik.coffeemachine.mvc.controller.common;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.web.server.ResponseStatusException;

import java.util.stream.Collectors;

public class CommonController {
    public static void checkErrors(Errors errors) {
        if (errors.hasErrors())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    errors.getAllErrors().stream().map(
                            e -> String.format("%s - %s", (e.getArguments()!=null && e.getArguments().length > 0 && e.getArguments()[0] instanceof DefaultMessageSourceResolvable) ?
                                    ((DefaultMessageSourceResolvable) e.getArguments()[0]).getCode()
                                    : e.getObjectName(), e.getDefaultMessage())
                    ).collect(Collectors.joining(", ")));
    }
}
