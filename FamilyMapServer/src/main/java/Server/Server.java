package Server;

import java.io.*;
import java.net.*;

import DeserializeJSONFiles.JSONData;
import Handler.*;
import com.sun.net.httpserver.*;

/**
 * This is the top level that acts as the server
 */
public class Server
{
	private static final int MAX_WAITING_CONNECTIONS = 12;

	private HttpServer server;

	/**
	 * The main
	 * @param args the port number for local host
	 */
    public static void main(String[] args)
	{
        int port = Integer.parseInt(args[0]);
        new Server().run(port);
    }

	/**
	 * This starts running the server
	 * @param port the designated port
	 */
	private void run(int port)
	{
		System.out.println("Initializing HTTP Server.Server");

		InetSocketAddress serverAddress = new InetSocketAddress(port);

		try
		{
			server = HttpServer.create(serverAddress, MAX_WAITING_CONNECTIONS);
		}
		catch (IOException e) {
			e.printStackTrace();
			return;
		}

        registerHandlers();

		System.out.println("Starting server");

		server.start();

		System.out.println("Server.Server started. Listening on port " + port);
	}

	/**
	 * This handles the registers
	 */
    private void registerHandlers()
	{
        System.out.println("Creating contexts");

        JSONData.JSONData_getData();

		server.createContext("/user/register", new RegisterHandler());

		server.createContext("/user/login", new LoginHandler());

		server.createContext("/clear", new ClearHandler());

		server.createContext("/fill", new FillHandler());

		server.createContext("/load", new LoadHandler());

		server.createContext("/person/", new PersonHandler());

		server.createContext("/person", new PersonHandler());

		server.createContext("/event/", new EventHandler());

		server.createContext("/event", new EventHandler());

		server.createContext("/", new FileHandler());

		//server.createContext("", new FileHandler());
    }
}

