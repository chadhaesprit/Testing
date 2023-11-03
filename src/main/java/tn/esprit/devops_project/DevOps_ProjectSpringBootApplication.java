package tn.esprit.devops_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class DevOps_ProjectSpringBootApplication {

    @GetMapping("/message")
    public String getMessage() {
        return "Working...!!";
    }


    public static void main(String[] args) {
        SpringApplication.run(DevOps_ProjectSpringBootApplication.class, args);
    }

}
