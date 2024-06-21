package Handler;

import Model.Authtoken;
import RequestResult.MultiPersonRequest;
import RequestResult.MultiPersonResult;
import RequestResult.SinglePersonRequest;
import RequestResult.SinglePersonResult;
import Service.AuthorizationHelper;
import Service.MultiPersonService;
import Service.SinglePersonService;
import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;

/**
 * Person Handler
 */
public class PersonHandler implements HttpHandler
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
      if (exchange.getRequestMethod().equalsIgnoreCase("GET"))
      {
        Headers reqHeaders = exchange.getRequestHeaders();

        if (reqHeaders.containsKey("Authorization"))
        {
          String token = reqHeaders.getFirst("Authorization");

          Authtoken authToken = new AuthorizationHelper().AuthorizationHelper_auth(token);

          if (authToken != null)
          {
            String uri = exchange.getRequestURI().toString();

            if (uri.contains("/person/"))
            {
              String personID = uri.substring(8);

              SinglePersonRequest singleRequest = new SinglePersonRequest(personID,authToken.getUsername());

              SinglePersonResult singleResult = new SinglePersonService().SinglePersonService_findSinglePerson(singleRequest);

              if (singleResult.isSuccess())
              {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK,0);
              }
              else
              {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST,0);
              }

              Writer resBody = new OutputStreamWriter(exchange.getResponseBody());

              gson.toJson(singleResult, resBody);

              resBody.close();
            }
            else
            {
              MultiPersonRequest multiPersonRequest = new MultiPersonRequest(authToken.getUsername());

              MultiPersonResult multiPersonResult = new MultiPersonService().MultiPersonService_findMultiPerson(multiPersonRequest);

              if (multiPersonResult.isSuccess())
              {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK,0);
              }
              else
              {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST,0);
              }

              Writer resBody = new OutputStreamWriter(exchange.getResponseBody());

              gson.toJson(multiPersonResult, resBody);

              resBody.close();
            }
          }
          else
          {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST,0);

            Writer resBody = new OutputStreamWriter(exchange.getResponseBody());

            SinglePersonResult fail = new SinglePersonResult(false, "Error: Incorrect user AuthToken");

            gson.toJson(fail, resBody);

            resBody.close();
          }
        }
        else
        {
          exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST,0);

          Writer resBody = new OutputStreamWriter(exchange.getResponseBody());

          SinglePersonResult fail = new SinglePersonResult(false, "Error: Does not contain an Authorization key");

          gson.toJson(fail, resBody);

          resBody.close();
        }
      }
      else
      {
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST,0);

        Writer resBody = new OutputStreamWriter(exchange.getResponseBody());

        SinglePersonResult fail = new SinglePersonResult(false, "Error: not GET");

        gson.toJson(fail, resBody);

        resBody.close();
      }
    }
    catch (Exception ex)
    {
      ex.printStackTrace();

      exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST,0);

      Writer resBody = new OutputStreamWriter(exchange.getResponseBody());

      SinglePersonResult fail = new SinglePersonResult(false, "Error: Something with the server");

      gson.toJson(fail, resBody);

      resBody.close();
    }
  }
}
