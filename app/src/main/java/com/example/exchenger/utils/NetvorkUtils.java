package com.example.exchenger.utils;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Scanner;

public class NetvorkUtils {

    public static URL genetayedURL(){
        Uri built = Uri.parse("https://api.exchangeratesapi.io/latest");

        URL url = null;
        try {
            url = new URL(built.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;

    }

    public static String getResponseFromURL(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {

            InputStream in = urlConnection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");
            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        }catch (UnknownHostException e){
            return null;
        }
        finally {
            urlConnection.disconnect();
        }

    }
}
