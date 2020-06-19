package com.revature.cats;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//repository
//service
//controller
// @SpringBootApplication includes @Configuration so we can write @Bean methods here
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.revature.cats.demobeans.DemoBean;


@SpringBootApplication
public class CatsApplication {

	public static void main(String[] args) {
		SpringApplication.run(CatsApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedMethods("GET","POST","PUT","PATCH","DELETE","OPTIONS");
			}
		};
	}
	/**
	 * Swagger "Docket" bean for all the Controllers in our project. API Documentation
	 * @return
	 */
//	@Bean
//	public Docket catApi() {
//		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.basePackage("com.revature.cats")).build();
//	}
	
	@Bean
	public DemoBean createDemoBean() {
			return new DemoBean();
	}
	
	// We can easily use beans from the Spring framework or Spring projects using Java config
	@Bean
	public CommandLineRunner printOnStartupRunner() {
		return (String[] args) -> {
			System.out.println("Hello from inside CommandLineRunner bean");
		};
	}
}
