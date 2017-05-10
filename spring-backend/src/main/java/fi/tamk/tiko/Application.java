package fi.tamk.tiko;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The Application class is used to configure the backend.
 *
 * @author  Vilho Stenman
 * @version 4.0
 * @since   1.0
 */
@SpringBootApplication
public class Application {
   /**
    * Runs the backend.
    *
    * @param args   Command line arguments
    * @version      4.0
    * @since        1.0
    */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}