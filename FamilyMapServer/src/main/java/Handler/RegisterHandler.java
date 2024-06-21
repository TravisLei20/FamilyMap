package Handler;

import RequestResult.RegisterRequest;
import RequestResult.RegisterResult;

import Service.RegisterService;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.HttpURLConnection;

/**
 * Register Handler
 */
public class RegisterHandler implements HttpHandler
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
    Gson gson = new Gson();
    try
    {
      if (exchange.getRequestMethod().equalsIgnoreCase("POST"))
      {
        Reader reqBody = new InputStreamReader(exchange.getRequestBody());

        RegisterRequest registerRequest = gson.fromJson(reqBody, RegisterRequest.class);

        RegisterResult registerResult = new RegisterService().RegisterService_register(registerRequest);

        if (registerResult.isSuccess())
        {
          exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK,0);
        }
        else
        {
          exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST,0);
        }

        Writer resBody = new OutputStreamWriter(exchange.getResponseBody());

        gson.toJson(registerResult, resBody);

        resBody.close();
      }
      else
      {
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
        Writer resBody = new OutputStreamWriter(exchange.getResponseBody());
        RegisterResult registerResult = new RegisterResult(false,"Error: Not a POST");
        gson.toJson(registerResult, resBody);

        resBody.close();
      }
    }
      catch (IOException e)
    {
      e.printStackTrace();

      exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);

      Writer resBody = new OutputStreamWriter(exchange.getResponseBody());
      RegisterResult registerResult = new RegisterResult(false,"Error");
      gson.toJson(registerResult, resBody);

      resBody.close();
    }
  }
}