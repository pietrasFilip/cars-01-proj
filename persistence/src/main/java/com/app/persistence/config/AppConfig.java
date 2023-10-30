package com.app.persistence.config;

import com.app.persistence.data.reader.factory.FromDbToCarWithValidator;
import com.app.persistence.data.reader.factory.FromJsonToCarWithValidator;
import com.app.persistence.data.reader.factory.FromTxtToCarWithValidator;
import com.app.persistence.data.reader.processor.CarDataProcessor;
import com.app.persistence.data.reader.processor.impl.CarDataProcessorDbImpl;
import com.app.persistence.data.reader.processor.impl.CarDataProcessorJsonImpl;
import com.app.persistence.data.reader.processor.impl.CarDataProcessorTxtImpl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.RequiredArgsConstructor;
import org.jdbi.v3.core.Jdbi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@ComponentScan("com.app")
@PropertySource("classpath:application.properties")
@RequiredArgsConstructor
public class AppConfig {
    private final Environment environment;

    @Bean
    public Gson gson() {
        return new GsonBuilder().serializeNulls().setPrettyPrinting().enableComplexMapKeySerialization().create();
    }

    @Bean
    public Jdbi jdbi() {
        var url = environment.getRequiredProperty("db.url");
        var username = environment.getRequiredProperty("db.username");
        var password = environment.getRequiredProperty("db.password");
        return Jdbi.create(url, username, password);
    }

    @Bean
    public CarDataProcessor carDataDbProcessor(FromDbToCarWithValidator dataFactory) {
        return new CarDataProcessorDbImpl(dataFactory);
    }

    @Bean
    public CarDataProcessor carDataJsonProcessor(FromJsonToCarWithValidator dataFactory) {
        return new CarDataProcessorJsonImpl(dataFactory);
    }

    @Bean
    public CarDataProcessor carDataTxtProcessor(FromTxtToCarWithValidator dataFactory) {
        return new CarDataProcessorTxtImpl(dataFactory);
    }
}
