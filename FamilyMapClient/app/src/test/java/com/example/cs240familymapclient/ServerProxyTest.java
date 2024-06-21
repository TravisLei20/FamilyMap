package com.example.cs240familymapclient;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.IOException;

import Model.Event;
import Model.Person;
import RequestResult.LoginRequest;
import RequestResult.LoginResult;
import RequestResult.MultiEventResult;
import RequestResult.MultiPersonResult;
import RequestResult.RegisterRequest;
import RequestResult.RegisterResult;

@FixMethodOrder( MethodSorters.NAME_ASCENDING)
public class ServerProxyTest
{
    private ServerProxy serverProxy = new ServerProxy();
    private String correctServerHost = "10.0.2.2";
    private String correctServerPort = "8081";
    private LoginRequest correctLoginRequest = new LoginRequest("Hulk","Smash");
    private LoginRequest wrongLoginRequest = new LoginRequest("wrong","wrong");
    private RegisterRequest correctRegisterRequest = new RegisterRequest("Ironman","Jarvis",
            "ironman@gmail.com", "Tony", "Stark", "m");
    private RegisterRequest wrongRegisterRequest = new RegisterRequest(null,null,
            null, null, null, null);
    private String correctAuthToken;
    private String wrongAuthToken = "Wrong!!!";

    // I had to do this because the JUnit that I used didn't have @BeforeAll.
    // Used @FixMethodOrder( MethodSorters.NAME_ASCENDING) to fix this issue.
    @Test
    public void Test1_correctRegister_SetUp()
    {
        try
        {
            System.out.println("here");
            RegisterResult registerResult = serverProxy.register(correctServerHost,correctServerPort,new RegisterRequest("Hulk","Smash",
                                                            "rawr@gmail.com", "Bruce", "Banner", "m"));
            assertTrue(registerResult.isSuccess());
            assertEquals("Hulk",registerResult.getUsername());
            correctAuthToken = registerResult.getAuthtoken();
        }
        catch (IOException ioException)
        {
            System.out.println("Failed here");
            ioException.printStackTrace();
        }
    }

    @Test
    public void Test2_correctRegister()
    {
        try
        {
            assertTrue(serverProxy.register(correctServerHost,correctServerPort,correctRegisterRequest).isSuccess());
        }
        catch (IOException ioException)
        {
            ioException.printStackTrace();
        }
    }

    @Test
    public void Test3_cannotRegisterTwice()
    {
        try
        {
            assertFalse(serverProxy.register(correctServerHost,correctServerPort,correctRegisterRequest).isSuccess());
        }
        catch (IOException ioException)
        {
            ioException.printStackTrace();
        }
    }

    @Test
    public void Test4_wrongRegister()
    {
        try
        {
            assertFalse(serverProxy.register(correctServerHost,correctServerPort,wrongRegisterRequest).isSuccess());
        }
        catch (IOException ioException)
        {
            ioException.printStackTrace();
        }
    }

    @Test
    public void Test5_correctLogin()
    {
        try
        {
            LoginResult loginResult = serverProxy.login(correctServerHost,correctServerPort,correctLoginRequest);
            assertTrue(loginResult.isSuccess());
            assertEquals(correctLoginRequest.getUsername(),loginResult.getUsername());
        }
        catch (IOException ioException)
        {
            ioException.printStackTrace();
        }
    }

    @Test
    public void Test6_wrongLogin()
    {
        try
        {
            assertFalse(serverProxy.login(correctServerHost,correctServerPort,wrongLoginRequest).isSuccess());
        }
        catch (IOException ioException)
        {
            ioException.printStackTrace();
        }
    }

    @Test
    public void Test7_correctPerson()
    {
        try
        {
            MultiPersonResult persons = serverProxy.persons(correctServerHost,correctServerPort,correctAuthToken);
            assertTrue(persons.isSuccess());
            for (Person person : persons.getData())
            {
                assertEquals("Hulk",person.getAssociatedUsername());
            }
        }
        catch (IOException ioException)
        {
            ioException.printStackTrace();
        }
    }

    @Test
    public void Test8_wrongPerson()
    {
        try
        {
            MultiPersonResult persons = serverProxy.persons(correctServerHost,correctServerPort,wrongAuthToken);
            assertFalse(persons.isSuccess());
        }
        catch (IOException ioException)
        {
            ioException.printStackTrace();
        }
    }

    @Test
    public void Test9_correctEvent()
    {
        try
        {
            MultiEventResult events = serverProxy.events(correctServerHost,correctServerPort,correctAuthToken);
            assertTrue(events.isSuccess());
            for (Event event : events.getData())
            {
                assertEquals("Hulk",event.getAssociatedUsername());
            }
        }
        catch (IOException ioException)
        {
            ioException.printStackTrace();
        }
    }

    @Test
    public void Test9_1_wrongEvent()
    {
        try
        {
            MultiEventResult events = serverProxy.events(correctServerHost,correctServerPort,wrongAuthToken);
            assertFalse(events.isSuccess());
        }
        catch (IOException ioException)
        {
            ioException.printStackTrace();
        }
    }








}
