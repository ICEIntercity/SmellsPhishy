package cz.intercity.smellsphishy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;


@SpringBootApplication
public class Application {

    public static void main(String [] args){

        SpringApplication.run(Application.class, args);

    }
}
