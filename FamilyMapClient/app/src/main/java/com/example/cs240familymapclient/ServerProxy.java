package com.example.cs240familymapclient;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;


import RequestResult.LoginRequest;
import RequestResult.LoginResult;
import RequestResult.MultiEventResult;
import RequestResult.MultiPersonResult;
import RequestResult.RegisterRequest;
import RequestResult.RegisterResult;

public class ServerProxy {

    LoginResult login(String serverHost, String serverPort, LoginRequest request) throws IOException
    {
        try
        {
            Gson gson = new Gson();

            URL url = new URL("http://" + serverHost + ":" + serverPort + "/user/login");

            String reqData = gson.toJson(request);

            HttpURLConnection http = (HttpURLConnection) url.openConnection();

            http.setReadTimeout(5000);
            http.setRequestMethod("POST");
            http.setDoOutput(true);

            http.connect();

            OutputStream reqBody = http.getOutputStream();

            writeString(reqData, reqBody);

            reqBody.close();

            if (http.getResponseCode() == HttpURLConnection.HTTP_OK)
            {
                InputStream respBody = http.getInputStream();
                String respData = readString(respBody);
                return (LoginResult)gson.fromJson(respData,LoginResult.class);
            }
            else
            {
                InputStream respBody = http.getErrorStream();
                String respData = readString(respBody);
                return (LoginResult)gson.fromJson(respData,LoginResult.class);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }


    RegisterResult register(String serverHost, String serverPort, RegisterRequest request) throws IOException
    {
        try
        {
            Gson gson = new Gson();

            URL url = new URL("http://" + serverHost + ":" + serverPort + "/user/register");

            String reqData = gson.toJson(request);

            HttpURLConnection http = (HttpURLConnection) url.openConnection();

            http.setReadTimeout(5000);
            http.setRequestMethod("POST");
            http.setDoOutput(true);

            http.connect();

            OutputStream reqBody = http.getOutputStream();

            writeString(reqData, reqBody);

            reqBody.close();

            if (http.getResponseCode() == HttpURLConnection.HTTP_OK)
            {
                InputStream respBody = http.getInputStream();
                String respData = readString(respBody);
                return (RegisterResult)gson.fromJson(respData,RegisterResult.class);
            }
            else
            {


                InputStream respBody = http.getErrorStream();
                String respData = readString(respBody);
                return (RegisterResult)gson.fromJson(respData,RegisterResult.class);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }


    MultiPersonResult persons(String serverHost, String serverPort, String authtoken) throws IOException
    {
        try
        {
            Gson gson = new Gson();

            URL url = new URL("http://" + serverHost + ":" + serverPort + "/person");


            HttpURLConnection http = (HttpURLConnection) url.openConnection();

            http.setReadTimeout(5000);
            http.setRequestMethod("GET");
            http.addRequestProperty("Authorization", authtoken);
            http.setDoOutput(false);

            http.connect();

            if (http.getResponseCode() == HttpURLConnection.HTTP_OK)
            {
                InputStream respBody = http.getInputStream();
                String respData = readString(respBody);
                return (MultiPersonResult)gson.fromJson(respData,MultiPersonResult.class);
            }
            else
            {
                InputStream respBody = http.getErrorStream();
                String respData = readString(respBody);
                return (MultiPersonResult)gson.fromJson(respData,MultiPersonResult.class);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }


    MultiEventResult events(String serverHost, String serverPort, String authtoken) throws IOException
    {
        try
        {
            Gson gson = new Gson();

            URL url = new URL("http://" + serverHost + ":" + serverPort + "/event");

            HttpURLConnection http = (HttpURLConnection) url.openConnection();

            http.setReadTimeout(5000);
            http.setRequestMethod("GET");
            http.addRequestProperty("Authorization", authtoken);
            http.setDoOutput(false);

            http.connect();

            if (http.getResponseCode() == HttpURLConnection.HTTP_OK)
            {
                InputStream respBody = http.getInputStream();
                String respData = readString(respBody);
                return (MultiEventResult)gson.fromJson(respData,MultiEventResult.class);
            }
            else
            {
                InputStream respBody = http.getErrorStream();
                String respData = readString(respBody);
                return (MultiEventResult)gson.fromJson(respData,MultiEventResult.class);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }


    /*
		The readString method shows how to read a String from an InputStream.
	*/
    private static String readString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(is);
        char[] buf = new char[1024];
        int len;
        while ((len = sr.read(buf)) > 0) {
            sb.append(buf, 0, len);
        }
        return sb.toString();
    }

    /*
        The writeString method shows how to write a String to an OutputStream.
    */
    private static void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }


}
