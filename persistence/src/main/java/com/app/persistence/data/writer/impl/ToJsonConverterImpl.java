package com.app.persistence.data.writer.impl;

import com.app.persistence.data.writer.ToJsonConverter;
import com.app.persistence.data.writer.exception.ObjectToJsonException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;

public abstract class ToJsonConverterImpl<T> implements ToJsonConverter<T> {
    private final String jsonFilename;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    protected ToJsonConverterImpl(String jsonFilename) {
        this.jsonFilename = jsonFilename;
    }

    @Override
    public void to(T t) {
        try (var fileWriter = new FileWriter(jsonFilename)){
            gson.toJson(t, fileWriter);
        } catch (Exception e) {
            throw new ObjectToJsonException(e.getMessage());
        }
    }
}
