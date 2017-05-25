package eu.davidem.wallet.persistence.config;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.PlatformTransactionManager;

import eu.davidem.wallet.persistence.CreditCardPersistenceService;
import eu.davidem.wallet.persistence.UserPersistenceService;
import eu.davidem.wallet.persistence.entities.CreditCard;
import eu.davidem.wallet.persistence.repos.UsersRepository;

/**
 * Persistence Configuration to be imported from the Main Configuration
 * 
 * Any kind of configurations tight to the persistence layer belong to here.
 * 
 * @author Davide Martorana
 *
 */
@EnableJpaRepositories(basePackageClasses = { UsersRepository.class })
public class PersistenceConfig {

	@Bean
	public CreditCardPersistenceService cardPersistenceService() {
		return new CreditCardPersistenceService();
	}
	
	
	@Bean
	public UserPersistenceService userPersistenceService() {
		return new UserPersistenceService();
	}
	
	//TODO: Remove
	@Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	
	@Primary
	@Bean
	@ConfigurationProperties(prefix = "spring.datasource")
	public DataSource dataSource() {
		return DataSourceBuilder.create().build();
	}
	
	@Primary
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(final EntityManagerFactoryBuilder builder, final DataSource dataSource) {
		return builder
				.dataSource(dataSource)
				.packages(CreditCard.class)
				.persistenceUnit("bidlotto")
				.build();
	}

	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory bidlottoEntityManagerFactory) {
		return new JpaTransactionManager(bidlottoEntityManagerFactory);
	}
}
