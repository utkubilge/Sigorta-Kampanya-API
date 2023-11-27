package com.utkubilge.kampanya;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.utkubilge.kampanya.model.Kampanya;
import com.utkubilge.kampanya.repo.KampanyaRepo;

@SpringBootApplication
public class KampanyaApplication {

	public static void main(String[] args) {
		SpringApplication.run(KampanyaApplication.class, args);
	}

	//Populate with data
	@Bean
	CommandLineRunner commandLineRunner(KampanyaRepo repo) {
		return args -> {
			repo.save(new Kampanya( "Kampanya 1", "Kampanya İçeriği Detay 1", 1));
			repo.save(new Kampanya( "Kampanya 2", "Kampanya İçeriği Detay 2", 1));
		};
	}
	

}
