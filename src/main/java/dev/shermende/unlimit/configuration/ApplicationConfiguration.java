package dev.shermende.unlimit.configuration;

import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import dev.shermende.unlimit.configuration.properties.UnlimitProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Configuration
public class ApplicationConfiguration {

    @Bean
    @Validated
    @ConfigurationProperties("dev.shermende.unlimit.app")
    public UnlimitProperties integrationOauthTokenLogProperties() {
        return new UnlimitProperties();
    }

    @Bean
    public CsvSchema csvSchema() {
        return CsvSchema.builder()
            .addColumn("orderId")
            .addColumn("amount", CsvSchema.ColumnType.NUMBER_OR_STRING)
            .addColumn("currency")
            .addColumn("comment")
            .build();
    }

    @Bean
    public CsvMapper csvObjectReader() {
        return new CsvMapper();
    }

    @Bean
    public JsonMapper jsonObjectMapper() {
        return new JsonMapper();
    }

}