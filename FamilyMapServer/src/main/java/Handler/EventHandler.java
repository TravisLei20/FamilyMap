package Handler;

import Model.Authtoken;
import RequestResult.*;
import Service.*;
import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;

/**
 * The Event Handler
 */
public class EventHandler implements HttpHandler
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
    Gson gson=new Gson();
    try {
      if (exchange.getRequestMethod().equalsIgnoreCase("GET")) {
        Headers reqHeaders=exchange.getRequestHeaders();

        if (reqHeaders.containsKey("Authorization")) {
          String token=reqHeaders.getFirst("Authorization");

          Authtoken authToken=new AuthorizationHelper().AuthorizationHelper_auth(token);

          if (authToken != null) {
            String uri=exchange.getRequestURI().toString();

            if (uri.contains("/event/")) {
              String eventID=uri.substring(7);

              SingleEventRequest singleEventRequest=new SingleEventRequest(eventID, authToken.getUsername());

              SingleEventResult singlePersonResult=new SingleEventService().SingleEventService_findEvent(singleEventRequest);

              if (singlePersonResult.isSuccess()) {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
              } else {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
              }

              Writer resBody=new OutputStreamWriter(exchange.getResponseBody());

              gson.toJson(singlePersonResult, resBody);

              resBody.close();
            }
            else
              {
              MultiEventRequest multiEventRequest=new MultiEventRequest(authToken.getUsername());

              MultiEventResult multiEventResult=new MultiEventService().MultiEventService_findMultiEvent(multiEventRequest);

              if (multiEventResult.isSuccess())
              {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
              }
              else
                {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
              }

              Writer resBody=new OutputStreamWriter(exchange.getResponseBody());

              gson.toJson(multiEventResult, resBody);

              resBody.close();
            }
          } else {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);

            Writer resBody=new OutputStreamWriter(exchange.getResponseBody());

            SingleEventResult fail=new SingleEventResult(false, "Error: Incorrect user AuthToken");

            gson.toJson(fail, resBody);

            resBody.close();
          }
        } else {
          exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);

          Writer resBody=new OutputStreamWriter(exchange.getResponseBody());

          SingleEventResult fail=new SingleEventResult(false, "Error: Does not contain an Authorization key");

          gson.toJson(fail, resBody);

          resBody.close();
        }
      }
    }

    catch(Exception ex)
      {
        ex.printStackTrace();

        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);

        Writer resBody=new OutputStreamWriter(exchange.getResponseBody());

        SingleEventResult fail=new SingleEventResult(false, "Error: Something with the server");

        gson.toJson(fail, resBody);

        resBody.close();
      }
    }
  }

