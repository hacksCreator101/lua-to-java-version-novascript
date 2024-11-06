package com.novascript.executor

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Main {
    public static void main(String[] args) {
        try {
            String urlString = "https://pastebin.com/raw/9UaLjr41";
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
          System.out.println("Made by NovaScript hacker");
            in.close();
            connection.disconnect();

            // Execute the retrieved code (this part depends on what the code does)
            // Note: Java does not have a direct equivalent to Lua's loadstring.
            // You would need to implement a way to execute the code if it's Java code.

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

