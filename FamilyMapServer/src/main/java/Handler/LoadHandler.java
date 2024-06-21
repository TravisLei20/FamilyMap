package Handler;

import RequestResult.LoadRequest;
import RequestResult.LoadResult;
import Service.LoadService;
import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.HttpURLConnection;

/**
 * Load Handler
 */
public class LoadHandler implements HttpHandler
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
    Headers reqHeaders = exchange.getRequestHeaders();
    try
    {
      Gson gson = new Gson();
      if (exchange.getRequestMethod().equalsIgnoreCase("POST"))
      {
        Reader reqBody = new InputStreamReader(exchange.getRequestBody());

        LoadRequest loadRequest = gson.fromJson(reqBody,LoadRequest.class);

        LoadResult loadResult = new LoadService().LoadService_load(loadRequest);

        if (loadResult.isSuccess())
        {
          exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        }
        else
        {
          exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
        }
        Writer resBody = new OutputStreamWriter(exchange.getResponseBody());

        gson.toJson(loadResult, resBody);

        resBody.close();
      }
      else
      {
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);

        Writer resBody = new OutputStreamWriter(exchange.getResponseBody());

        LoadResult loadResult = new LoadResult(false, "Error: Not a POST");

        gson.toJson(loadResult, resBody);

        resBody.close();
      }
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
  }
}
