package com.code;

//import com.code.Config.StorageProperties;
import com.code.Service.StorageService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@MapperScan("com.code.Mapper")
//@EnableConfigurationProperties(StorageProperties.class)
public class GraduationApplication extends SpringBootServletInitializer{
	public static void main(String[] args) {
		SpringApplication.run(GraduationApplication.class, args);
	}

//	@Bean
//	CommandLineRunner init(StorageService storageService){
//		return(args) -> {
//			storageService.init();
//		};
//	}
}
