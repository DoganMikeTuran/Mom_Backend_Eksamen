/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;
import java.util.concurrent.Callable;

/**
 *
 * @author dmt1
 */
public class ApiCallable implements Callable<String> {

    private String url;

    public ApiCallable(String url) {
        this.url = url;
    }
    
    
    
    @Override
    public String call() throws Exception {
       return ApiData.getData(url);
    }
    
}
