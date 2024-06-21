package com.example.cs240familymapclient;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Model.Event;
import Model.Person;
import RequestResult.MultiEventRequest;
import RequestResult.MultiEventResult;
import RequestResult.MultiPersonRequest;
import RequestResult.MultiPersonResult;
import RequestResult.RegisterRequest;
import RequestResult.RegisterResult;
import RequestResult.Response;

public class RegisterTask implements Runnable
{
    private RegisterRequest request;
    private String host;
    private String port;

    private final Handler messageHandler;

    public RegisterTask(RegisterRequest request, String host, String port, Handler messageHandler)
    {
        this.request = request;
        this.host = host;
        this.port = port;
        this.messageHandler = messageHandler;
    }

    @Override
    public void run()
    {
        try
        {
            RegisterResult result = new ServerProxy().register(host,port,request);
            Response response = new Response(result.isSuccess(),result.getMessage());

            if (result.isSuccess())
            {
                MultiPersonResult people = new ServerProxy().persons(host,port,result.getAuthtoken());
                MultiEventResult events = new ServerProxy().events(host,port,result.getAuthtoken());

                DataCache dataCache = DataCache.getInstance();

                dataCache.setUserID(result.getPersonID());

                for (Person person : people.getData()) {
                    dataCache.getAllPeople().add(person);
                    dataCache.getPeople().put(person.getPersonID(), person);
                }
                for (Event event : events.getData())
                {
                    dataCache.getEvents().put(event.getEventID(),event);
                    dataCache.getAllEvents().add(event);

                    if (dataCache.getPersonEvents().containsKey(event.getPersonID()))
                    {
                        List<Event> list = dataCache.getPersonEvents().get(event.getPersonID());
                        list.add(event);
                        dataCache.getPersonEvents().put(event.getPersonID(), list);
                    }
                    else
                    {
                        List<Event> list = new ArrayList<>();
                        list.add(event);
                        dataCache.getPersonEvents().put(event.getPersonID(), list);
                    }
                }

                dataCache.findSideAncestors();
            }

            response.setMessage(result.getPersonID());

            // Not returning the whole response
            sendMessage(response);
        }
        catch (IOException e)
        {
            sendMessage(null);
        }

    }

    private void sendMessage(Response response)
    {
        Message message = Message.obtain();
        Bundle messageBundle = new Bundle();
        messageBundle.putString("key",response.getMessage());
        message.setData(messageBundle);
        messageHandler.sendMessage(message);
    }
}