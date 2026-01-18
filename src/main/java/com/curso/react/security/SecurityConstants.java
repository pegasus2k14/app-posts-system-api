package com.curso.react.security;

import com.curso.react.context.SpringApplicationContext;

public class SecurityConstants {
    public static final  long EXPIRATION_DATE = 864000000; //duracion del token en milis egundos
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/users";

    public static String getTokenSecret(){
        //recuperamos el Bean AppProperties
        AppProperties appProperties = 
            (AppProperties) SpringApplicationContext.getBean("AppProperties");
            //recuperamos y retornamos la propiedad token.secret
            return appProperties.getTokenSecret();
    }

}
