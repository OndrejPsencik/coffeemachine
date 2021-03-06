package cz.psencik.coffeemachine.mvc.controller.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.psencik.coffeemachine.mvc.exceptions.NotFoundException;
import cz.psencik.coffeemachine.mvc.rest.model.CommonRestObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static cz.psencik.coffeemachine.mvc.controller.common.CommonController.checkErrors;

public class CommonCrudController<E, ID, R extends CommonRestObject<ID>, SR> {
    private final Class<E> entityClass;
    private final Class<R> restClass;
    private final String[] ignoredProperties;

    @Autowired
    protected JpaRepository<E, ID> repository;

    @Autowired
    protected ObjectMapper mapper;

    public CommonCrudController(Class<E> entityClass, Class<R> restClass, String[] ignoredProperties) {
        this.entityClass = entityClass;
        this.restClass = restClass;
        this.ignoredProperties = ignoredProperties;
    }

    @GetMapping
    public List<R> list(Pageable p, HttpServletRequest request) {
        return repository.findAll(p).stream().map(e -> mapper.convertValue(e, restClass)).collect(Collectors.toList());
    }

    protected void onCreate(E entity, HttpServletRequest request) {
        //empty
    }

    @Transactional
    @PostMapping
    public R create(@Valid @ModelAttribute SR entity, Errors errors, HttpServletRequest request) throws Exception {
        checkErrors(errors);
        var instance = entityClass.getDeclaredConstructor().newInstance();
        BeanUtils.copyProperties(entity, instance, ignoredProperties);
        onCreate(instance, request);
        repository.save(instance);
        return mapper.convertValue(instance, restClass);
    }

    @Transactional
    @PutMapping("{entityId}")
    public R update(@PathVariable ID entityId, @Valid @ModelAttribute R entity, Errors errors) {
        checkErrors(errors);
        var instance = repository.findById(entityId).orElseThrow(NotFoundException::new);
        BeanUtils.copyProperties(entity, instance, ignoredProperties);
        repository.save(instance);
        return mapper.convertValue(instance, restClass);
    }

    @Transactional
    @GetMapping("{entityId}")
    public R detail(@PathVariable ID entityId) {
        var instance = repository.findById(entityId).orElseThrow(NotFoundException::new);
        return mapper.convertValue(instance, restClass);
    }
}
