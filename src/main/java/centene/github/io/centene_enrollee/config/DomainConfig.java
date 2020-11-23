package centene.github.io.centene_enrollee.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EntityScan(basePackages = {"centene.github.io.centene_enrollee.domain"})
@EnableJpaRepositories(basePackages = {"centene.github.io.centene_enrollee.repos"})
@EnableTransactionManagement
public class DomainConfig {
}
