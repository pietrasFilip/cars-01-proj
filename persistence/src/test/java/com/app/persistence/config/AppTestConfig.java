package com.app.persistence.config;

import com.app.persistence.data.reader.factory.FromDbToCarWithValidator;
import com.app.persistence.data.reader.factory.FromJsonToCarWithValidator;
import com.app.persistence.data.reader.factory.FromTxtToCarWithValidator;
import com.app.persistence.data.reader.loader.repository.db.CarRepository;
import com.app.persistence.data.reader.loader.repository.db.impl.CarRepositoryImpl;
import com.app.persistence.data.reader.processor.CarDataProcessor;
import com.app.persistence.data.reader.processor.impl.CarDataProcessorDbImpl;
import com.app.persistence.data.reader.processor.impl.CarDataProcessorJsonImpl;
import com.app.persistence.data.reader.processor.impl.CarDataProcessorTxtImpl;
import com.app.persistence.data.reader.validator.CarDataValidator;
import com.app.persistence.data.reader.validator.impl.CarDataValidatorImpl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.RequiredArgsConstructor;
import org.jdbi.v3.core.Jdbi;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

@RequiredArgsConstructor
public class AppTestConfig {
    private final Environment environment;

    @Bean
    public Gson gson() {
        return new GsonBuilder().serializeNulls().setPrettyPrinting().create();
    }

    @Bean
    public Jdbi jdbi() {
        var url = environment.getRequiredProperty("db.url");
        var username = environment.getRequiredProperty("db.username");
        var password = environment.getRequiredProperty("db.password");
        return Jdbi.create(url, username, password);
    }

    @Bean
    public CarDataValidator carDataValidator() {
        return new CarDataValidatorImpl();
    }

    @Bean
    public CarRepository carRepository(Jdbi jdbi) {
        return new CarRepositoryImpl(jdbi);
    }

    @Bean
    public FromTxtToCarWithValidator dataTxtFactory(CarDataValidator carDataValidator) {
        return new FromTxtToCarWithValidator(carDataValidator);
    }

    @Bean
    public FromJsonToCarWithValidator dataJsonFactory(Gson gson, CarDataValidator carDataValidator) {
        return new FromJsonToCarWithValidator(gson, carDataValidator);
    }

    @Bean
    public FromDbToCarWithValidator dataDbFactory(CarRepository carRepository, CarDataValidator carDataValidator) {
        return new FromDbToCarWithValidator(carRepository, carDataValidator);
    }

    @Bean
    public CarDataProcessor carDataProcessorTxt(FromTxtToCarWithValidator dataFactory) {
        return new CarDataProcessorTxtImpl(dataFactory);
    }

    @Bean
    public CarDataProcessor carDataProcessorJson(FromJsonToCarWithValidator dataFactory) {
        return new CarDataProcessorJsonImpl(dataFactory);
    }

    @Bean
    public CarDataProcessor carDataProcessorDb(FromDbToCarWithValidator dataFactory) {
        return new CarDataProcessorDbImpl(dataFactory);
    }
}
