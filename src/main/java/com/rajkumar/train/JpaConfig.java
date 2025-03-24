package com.rajkumar.train;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = {"com.rajkumar.train"})
@EntityScan("com.rajkumar.train")
public class JpaConfig {
}
