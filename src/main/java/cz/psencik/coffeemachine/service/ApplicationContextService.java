package cz.psencik.coffeemachine.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class ApplicationContextService {
    private static ApplicationContextService instance;

    @Autowired
    private ApplicationContext applicationContext;

    public ApplicationContextService() {
        instance = this;
    }

    public static <T> T getBean(Class<T> clazz) {
        return instance.applicationContext.getBean(clazz);
    }
}
