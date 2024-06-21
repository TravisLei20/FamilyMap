package Handler;

import RequestResult.LoginRequest;
import RequestResult.LoginResult;
import Service.LoginService;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.HttpURLConnection;

import com.google.gson.*;

/**
 * Login Handler
 */
public class LoginHandler implements HttpHandler
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
      if (exchange.getRequestMethod().equalsIgnoreCase("POST"))
      {
          Gson gson = new Gson();

          Reader reqBody = new InputStreamReader(exchange.getRequestBody());

          // could be an issue here

          LoginRequest loginRequest = gson.fromJson(reqBody, LoginRequest.class);

          // might need read string function

          LoginResult loginResult = new LoginService().LoginService_login(loginRequest);

          if (loginResult.isSuccess())
          {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
          }
          else
          {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST,0);
          }

          Writer resBody = new OutputStreamWriter(exchange.getResponseBody());

          gson.toJson(loginResult, resBody);

          resBody.close();
        }
        else
        {
          Gson gson = new Gson();
          // The auth token was invalid somehow, so we return a "not authorized"
          // status code to the client.
          exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);

          Writer resBody = new OutputStreamWriter(exchange.getResponseBody());

          // make own result with message "error" success to false
          LoginResult loginResult = new LoginResult(false, "Error: Not a POST");

          gson.toJson(loginResult, resBody);

          resBody.close();
        }


    }
    catch (IOException e)
    {
      e.printStackTrace();

      Gson gson = new Gson();
      // Some kind of internal error has occurred inside the server (not the
      // client's fault), so we return an "internal server error" status code
      // to the client.
      exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);

      Writer resBody = new OutputStreamWriter(exchange.getResponseBody());

      // make own result with message "error" success to false
      LoginResult loginResult = new LoginResult(false, "Error: In server side");

      gson.toJson(loginResult, resBody);

      resBody.close();
    }
  }

//  private void writeString(String str, OutputStream os) throws IOException
//  {
//    OutputStreamWriter sw = new OutputStreamWriter(os);
//    BufferedWriter bw = new BufferedWriter(sw);
//    bw.write(str);
//    bw.flush();
//  }
}

