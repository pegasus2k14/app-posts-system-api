package com.curso.react.context;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringApplicationContext implements ApplicationContextAware{
    private static ApplicationContext CONTEXT;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
       CONTEXT = applicationContext;
    }

    //Metodo para acceder a los Beans de la aplicacion
    public static Object getBean(String beanName){
        if(CONTEXT==null) System.out.println("EL CONTEXTO ES NULO");
        return CONTEXT.getBean(beanName);
    }
}
