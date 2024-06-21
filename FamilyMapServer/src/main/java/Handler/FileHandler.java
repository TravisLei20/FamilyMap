package Handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.HttpURLConnection;

import java.io.IOException;
import java.nio.file.Files;

/**
 * File Handler
 */
public class FileHandler implements HttpHandler
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
      OutputStream respBody = exchange.getResponseBody();
      if (exchange.getRequestMethod().toUpperCase().equals("GET"))
      {
        String urlPath = exchange.getRequestURI().toString();
        String filePath;

        if (urlPath.length() == 0)
        {
          exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, 0);
          Files.copy(new File("web/HTML/404.html").toPath(), respBody);
          respBody.close();
        }
        else
        {
          if (urlPath.equals("/"))
          {
            urlPath = "/index.html";
          }

          if (!urlPath.contains("web/"))
          {
            filePath = "web" + urlPath;
          }
          else
          {
            filePath = urlPath;
          }

          File file = new File(filePath);

          if (file.exists())
          {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);

            Files.copy(file.toPath(), respBody);

            respBody.close();
          }
          else
          {
            System.out.println("in 404");
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, 0);
            Files.copy(new File("web/HTML/404.html").toPath(), respBody);
            respBody.close();
          }
        }
      }
      else
      {
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_METHOD, 0);
        respBody.close();
      }
    }
    catch (IOException e)
    {
      exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
      exchange.getResponseBody().close();
      e.printStackTrace();
    }
  }
}