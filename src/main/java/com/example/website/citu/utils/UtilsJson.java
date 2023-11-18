package com.example.website.citu.utils;

import com.example.website.citu.entity.InfoDificultyBlockchain;
import com.example.website.citu.model.Block;
import com.example.website.citu.model.DtoTransaction;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import java.util.Set;

public class UtilsJson {
    public static String objToStringJson(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        StringWriter writer = new StringWriter();
        mapper.writeValue(writer, object);
        return writer.toString();
    }

    public static Object jsonToClass(String json, Class cls) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, cls);
    }

    public static List<Block> jsonToListBlock(String json) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, new TypeReference<List<Block>>(){});
    }

    public static List<DtoTransaction> jsonToListDto(String json) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, new TypeReference<List<DtoTransaction>>(){});
    }

    public static Set<String> jsonToSetAddresses(String json) throws  JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, new TypeReference<Set<String>>(){});
    }



    public static InfoDificultyBlockchain jsonToInfoDifficulty(String json) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, InfoDificultyBlockchain.class);
    }


}
