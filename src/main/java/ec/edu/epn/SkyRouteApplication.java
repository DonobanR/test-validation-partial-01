package ec.edu.epn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import ec.edu.epn.skyroute.service.PassengerService;

@SpringBootApplication
public class SkyRouteApplication {
	
    public static void main(String[] args) {
        SpringApplication.run(SkyRouteApplication.class, args);
    }
}
