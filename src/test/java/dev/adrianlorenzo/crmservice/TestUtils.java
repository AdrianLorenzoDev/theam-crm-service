package dev.adrianlorenzo.crmservice;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class TestUtils {
    public static String toJson(Object resource){
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        return gson.toJson(resource);
    }
}
