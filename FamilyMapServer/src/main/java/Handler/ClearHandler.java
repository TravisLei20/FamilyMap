package Handler;

import RequestResult.ClearRequest;
import RequestResult.ClearResult;
import Service.ClearService;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;

/**
 * Handles the clear command
 */
public class ClearHandler implements HttpHandler
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
        ClearResult clearResult = new ClearService().ClearService_clear(new ClearRequest());
        if (clearResult.isSuccess())
        {
          exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK,0);

          Writer resBody = new OutputStreamWriter(exchange.getResponseBody());

          gson.toJson(clearResult, resBody);

          resBody.close();
        }
        else
        {
          exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST,0);

          Writer resBody = new OutputStreamWriter(exchange.getResponseBody());

          gson.toJson(clearResult, resBody);

          resBody.close();
        }
      }
      else
      {
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST,0);

        Writer resBody = new OutputStreamWriter(exchange.getResponseBody());

        ClearResult clearResult = new ClearResult(false, "Error: Request not POST.");

        gson.toJson(clearResult, resBody);

        resBody.close();
      }
    }
    catch (IOException ex)
    {
      ex.printStackTrace();

      Gson gson = new Gson();

      exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);

      Writer resBody = new OutputStreamWriter(exchange.getResponseBody());

      ClearResult clearResult = new ClearResult(false, "Error: Something when wrong with the server or something, IDK, Good luck.");

      gson.toJson(clearResult, resBody);

      resBody.close();

      exchange.getResponseBody().close();
    }
  }
}
