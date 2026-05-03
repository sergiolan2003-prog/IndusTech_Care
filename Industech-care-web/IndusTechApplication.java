package com.industech;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

/**
 * Punto de entrada de IndusTech API.
 *
 * IMPORTANTE: el excludeFilters excluye los paquetes legacy/ y main/
 * del escaneo de componentes de Spring. Aunque esos archivos fueron
 * excluidos de la compilación en el pom.xml, esta anotación es un
 * segundo nivel de protección.
 */
@SpringBootApplication(
        exclude = { HibernateJpaAutoConfiguration.class },
        scanBasePackages = {
                "com.industech.controllers",
                "com.industech.services",
                "com.industech.security",
                "com.industech.dto",
                "com.industech.exceptions",
                "com.industech.repositories",
                "com.industech.config"
        }
)
public class IndusTechApplication {
    public static void main(String[] args) {
        SpringApplication.run(IndusTechApplication.class, args);
    }
}