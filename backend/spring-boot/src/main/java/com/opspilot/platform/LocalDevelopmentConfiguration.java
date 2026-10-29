package com.opspilot.platform;

import java.util.UUID;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration 
@Profile ("local")
public class LocalDevelopmentConfiguration {

    @Bean 
    public ApplicationRunner initializeLocalOrganization(
            JdbcTemplate jdbcTemplate,
            @Value ("${opspilot.local.organization-id}") UUID organizationId
    ) {
        return args -> jdbcTemplate.update(
                """
                INSERT INTO organizations (id, name)
                VALUES (?, ?)
                ON CONFLICT (id) DO NOTHING
                """,
                organizationId,
                "Local Development"
        );
    }
}
