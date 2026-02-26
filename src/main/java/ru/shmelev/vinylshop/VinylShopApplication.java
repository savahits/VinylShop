package ru.shmelev.vinylshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

import static org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO;

@SpringBootApplication
@PropertySource(value = "classpath:database.properties")
@EnableSpringDataWebSupport(pageSerializationMode = VIA_DTO)
public class VinylShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(VinylShopApplication.class, args);
	}

}
