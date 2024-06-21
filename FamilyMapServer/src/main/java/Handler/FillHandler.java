package Handler;

import RequestResult.FillRequest;
import RequestResult.FillResult;
import Service.FillService;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.HttpURLConnection;

/**
 * Fill Handler
 */
public class FillHandler implements HttpHandler
{
  /**
   * Handle the given request and generate an appropriate response.
   * See {@link HttpExchange} for a description of the steps
   * involved in handling an exchange.
   *
   * @param exchange the exchange containing the request from the
   *                 client and used to send the response
   * @throws NullPointerException if exchange is <code>null</code>
   */
  @Override
  public void handle(HttpExchange exchange) throws IOException
  {
    try
    {
      Gson gson = new Gson();

      if (exchange.getRequestMethod().equalsIgnoreCase("POST"))
      {
        String uri = exchange.getRequestURI().toString();

        int firstIndex = uri.indexOf("/",6);

        String username;
        String numberString;


        FillRequest fillRequest;

        if (firstIndex > 0)
        {
          username = uri.substring(6,firstIndex);
          numberString = uri.substring(firstIndex+1);
          int generationNum = Integer.parseInt(numberString);

          if (generationNum < 0)
          {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);

            Writer resBody = new OutputStreamWriter(exchange.getResponseBody());

            FillResult fillResult = new FillResult(false,"Error: Can not use negative numbers");

            gson.toJson(fillResult, resBody);
            // might neeed to use the write function here above

            resBody.close();


            // This might be an issue with being in here


          }

          fillRequest = new FillRequest(username,generationNum);
        }
        else
        {
          username = uri.substring(6);
          fillRequest = new FillRequest(username);
        }



        FillResult fillResult = new FillService().FillService_fill(fillRequest);

        if (fillResult.isSuccess())
        {
          exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        }
        else
        {
          exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
        }

        Writer resBody = new OutputStreamWriter(exchange.getResponseBody());

        gson.toJson(fillResult, resBody);

        resBody.close();
      }
      else
      {
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);

        Writer resBody = new OutputStreamWriter(exchange.getResponseBody());

        FillResult fillResult = new FillResult(false,"Error: Not POST");

        gson.toJson(fillResult, resBody);
        // might neeed to use the write function here above

        resBody.close();
      }
    }
    catch (IOException ex)
    {
      ex.printStackTrace();
    }
  }
}
