package com.curso.react;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.curso.react.security.AppProperties;


@SpringBootApplication
@EnableJpaAuditing
public class CursoPracticoReactApplication {

	public static void main(String[] args) {
		SpringApplication.run(CursoPracticoReactApplication.class, args);
	}


	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder(){
		return new BCryptPasswordEncoder();
	}
    
	//@Bean
	//public SpringApplicationContext springApplicationContext(){
	//	return new SpringApplicationContext();
	//}

	@Bean(name = "AppProperties")
	public AppProperties getAppProperties(){
		return new AppProperties();
	}

	@Bean
	public ModelMapper modelMapper(){
		ModelMapper modelMapper = new ModelMapper();
		//modelMapper.typeMap(UserDto.class, UserRest.class).addMappings(mapper->mapper.skip(UserRest::setPosts));
		return modelMapper;
	}
}
