package com.jweb.dao.flyway;

import javax.annotation.PostConstruct;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.FlywayException;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@PropertySource(value = {"classpath:flyway/flyway-${spring.profiles.active}.properties"})
@ConfigurationProperties(prefix="flyway")
@Data
public class FlywayMigrater {
	
	private String url;
	private String user;
	private String password;
	private String table;
	
	@PostConstruct
	public void migrate(){
		
		table = table == null || "".equals(table.trim()) ? "flyway_version" : table;
		String baseLocation = "flyway/sql";
		
		String[] locations = new String[3];
		if(url.contains("postgresql")) {
			locations[0] = baseLocation+"/create/pgsql";
			locations[1] = baseLocation+"/alter/pgsql";
		}else {
			locations[0] = baseLocation+"/create/mysql";
			locations[1] = baseLocation+"/alter/mysql";
		}
		locations[2] = baseLocation+"/insert";
	
		Flyway flyway = Flyway.configure()
				.dataSource(url, user, password)
				.table(table)
				.locations(locations)
				.baselineOnMigrate(true)
				.encoding("UTF-8")
				.validateOnMigrate(true)
				.outOfOrder(false)
				.load();

		try {
			flyway.migrate();
			log.info("Flyway migrate success");
		} catch (FlywayException e) {
			log.warn("Flyway migrate fail", e);
			throw new RuntimeException(e);
		}
	
	}
}
