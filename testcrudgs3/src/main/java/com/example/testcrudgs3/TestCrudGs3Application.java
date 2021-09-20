package com.example.testcrudgs3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import com.vaadin.flow.component.dependency.NpmPackage;

@SpringBootApplication(exclude = ErrorMvcAutoConfiguration.class)
@NpmPackage(value = "lumo-css-framework", version = "^4.0.10")
public class TestCrudGs3Application extends SpringBootServletInitializer {

	private static final Logger log = LoggerFactory.getLogger(TestCrudGs3Application.class);

	public static void main(String[] args) {
		SpringApplication.run(TestCrudGs3Application.class);
	}

	@Bean
	public CommandLineRunner loadData(FuncionarioRepository repository) {
		return (args) -> {

			repository.save(new Funcionario("Jão", "461.958.810-58", "(59) 34343-0813", "29129-640", "myrtis8124@uorak.com"));
			repository.save(new Funcionario("Chloe", "466.062.590-70", "(63) 44837-5897", "75705-711", "saloua5408@uorak.com"));
			repository.save(new Funcionario("Julia", "719.168.910-20", "(37) 14232-7104", "50850-306", "shira7345@uorak.com"));
			repository.save(new Funcionario("David", "551.970.300-03", "(25) 43617-9967", "69313-460", "mattias3470@uorak.com"));
			repository.save(new Funcionario("Michelle", "793.002.890-98", "(47) 51790-9865", "89258-275", "naeem5573@uorak.com"));


			log.info("Customers found with findAll():");
			log.info("-------------------------------");
			for (Funcionario customer : repository.findAll()) {
				log.info(customer.toString());
			}
			log.info("");


			Funcionario customer = repository.findById(1L).get();
			log.info("Customer found with findOne(1L):");
			log.info("--------------------------------");
			log.info(customer.toString());
			log.info("");


			log.info("Customer found with findBycpfStartsWithIgnoreCase('461.958.810-58'):");
			log.info("--------------------------------------------");
			for (Funcionario bauer : repository
					.findBycpfStartsWithIgnoreCase("461.958.810-58")) {
				log.info(bauer.toString());
			}
			log.info("");
		};
	}

}
