package com.example.website.citu;

import com.example.website.citu.controller.WebController;
import com.example.website.citu.entity.SignRequest;
import com.example.website.citu.model.Block;
import com.example.website.citu.model.LiteVersionWiner;
import com.example.website.citu.utils.UtilUrl;
import com.example.website.citu.utils.UtilsJson;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

@SpringBootTest
public class Test {

    @org.junit.jupiter.api.Test
    public void test() throws IOException {
        SignRequest signRequest = new SignRequest();
        String address = "http://194.87.236.238:82";
        signRequest.setSign("MEUCIFiXl8l7EowHjyooTt8tpCB171m0WxUbmn+E/34wNyh6AiEAkLPAtbQwVnoAIAGowk0Rn67EjeqQrHGZwd214Wx/nuo=");
        String json = UtilUrl.getObject(UtilsJson.objToStringJson(signRequest),  address+ "/statusTransaction64");
        System.out.println("json: " + json);


        String sign = signRequest.getSign(); // Получаем значение подписи

// Добавляем параметр sign в URL

         json = UtilUrl.getObject(UtilsJson.objToStringJson(signRequest),  address+ "/findBlocksFromSign64");
        System.out.println("json: " + json);
    }


}
