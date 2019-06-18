/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 *
 * @author dmt1
 */
public class ApiData {

    public static String getData(String apiurl) throws MalformedURLException, IOException {
        // Create URL object and open connection.
        
        URL url = new URL(apiurl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        // Set request method to GET
        
        connection.setRequestMethod("GET");
        // Set request headers.
        
        connection.setRequestProperty("User-Agent", "server"); // no need to specify actual device.
        connection.setRequestProperty("Accept", "application/json;charset=UTF-8");

        // Create input stream from connection.
        
        InputStream inputStream = connection.getInputStream();
        // Create output stream to write the bytes read from inputstream.
        
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        // 1. Read from input stream into byte-array.
        // 2. Write byte array into output stream which grows dynamically.
        // 3. Reading stops when bytes read (len) is -1, which means EOF or connection closed.
        
        int len;
        byte[] buffer = new byte[4096];
        while(-1 != (len=inputStream.read(buffer))){
        outputStream.write(buffer, 0, len);
        }
        return outputStream.toString();
        
        
        
        
        
        /* OK with scanner also.
        // Read from the input stream of the connection.
        Scanner scanner = new Scanner(connection.getInputStream());
        
        String jsonResult = "";
        while (scanner.hasNext())
        {            
            jsonResult += scanner.nextLine(); 
        }
        scanner.close();
//        System.out.println("JSON Received: " + jsonResult);
        
        return jsonResult;
        */
    }
}
