package com.example.website.citu.utils;

import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.nio.charset.Charset;

public class UtilUrl {
    public static String readJsonFromUrl(String url) throws IOException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            ObjectMapper mapper = new ObjectMapper();
            return jsonText;
        } finally {
            System.out.println("UtilUrl: readJsonFromUrl: " + url );
            is.close();
        }
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }
    public static String getObject(String jsonObject, String requstStr) throws IOException {
        URL url = new URL(requstStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//        conn.connect();
        conn.setReadTimeout(35000);
        conn.setConnectTimeout(35000);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json; utf-8");
        conn.setRequestProperty("Accept", "application/json");
        conn.setDoOutput(true);



        try(OutputStream outputStream = conn.getOutputStream()) {
            byte[] input = jsonObject.getBytes("utf-8");
            outputStream.write(input, 0, input.length);
            conn.getResponseCode();
        }


        conn.connect();
        try(BufferedReader br = new BufferedReader(
                new InputStreamReader(conn.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            return response.toString();

        }

    }
    public static String getObject(String jsonObject, String requstStr, boolean isPost) throws IOException {
        URL url = new URL(requstStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//        conn.connect();
        conn.setReadTimeout(95000);
        conn.setConnectTimeout(95000);
        String str = isPost?"POST":"GET";
        conn.setRequestMethod(str);
        conn.setRequestProperty("Content-Type", "application/json; utf-8");
        conn.setRequestProperty("Accept", "application/json");
        conn.setDoOutput(true);



        try(OutputStream outputStream = conn.getOutputStream()) {
            byte[] input = jsonObject.getBytes("utf-8");
            outputStream.write(input, 0, input.length);
            conn.getResponseCode();
        }


        conn.connect();
        try(BufferedReader br = new BufferedReader(
                new InputStreamReader(conn.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            return response.toString();

        }

    }

    public static String getObject(String url) throws IOException {

        URL requestURL = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) requestURL.openConnection();

        conn.setRequestMethod("GET");

        conn.connect();

        if(conn.getResponseCode() != 200) {
            throw new IOException("Response code is not 200");
        }

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(conn.getInputStream(), "utf-8"))) {

            StringBuilder response = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line.trim());
            }
            return response.toString();

        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }

    }

    public static int sendPost(String jsonObject, String requestStr) throws IOException {
        int response;
        URL url = new URL(requestStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//        conn.connect();
        conn.setReadTimeout(95000);
        conn.setConnectTimeout(95000);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json; utf-8");
        conn.setRequestProperty("Accept", "application/json");
        conn.setDoOutput(true);



        try(OutputStream outputStream = conn.getOutputStream()) {
            byte[] input = jsonObject.getBytes("utf-8");
            outputStream.write(input, 0, input.length);
             response = conn.getResponseCode();

        }


        conn.connect();
        return response;
    }




}
