package com.example.cs240familymapclient;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import Model.Person;
import RequestResult.LoginRequest;
import RequestResult.RegisterRequest;


public class LoginFragment extends Fragment
{
    private EditText serverHost;
    private EditText serverPort;
    private EditText username;
    private EditText password;
    private EditText firstName;
    private EditText lastName;
    private EditText email;

    private String gender;

    private Button loginButton;
    private Button registerButton;

    private RadioButton maleButton, femaleButton;


    private Listener listener;

    public interface Listener
    {
        void notifyDone();
    }

    public void registerListener(Listener listener) {this.listener = listener;}

    @Override
    public void onCreate(Bundle saveInstanceState)
    {
        super.onCreate(saveInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        loginButton = view.findViewById(R.id.loginbuttonID);

        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String serverHostOnClick = serverHost.getText().toString();
                String serverPortOnClick = serverPort.getText().toString();
                String usernameOnClick = username.getText().toString();
                String passwordOnClick = password.getText().toString();
                String emailOnClick = email.getText().toString();
                String firstNameOnClick = firstName.getText().toString();
                String lastNameOnClick = lastName.getText().toString();

                boolean enableRegister = (serverHostOnClick.equals("") || serverPortOnClick.equals("") || usernameOnClick.equals("") || passwordOnClick.equals("") ||
                        emailOnClick.equals("") || firstNameOnClick.equals("") || lastNameOnClick.equals(""));

                boolean enableLogin = (serverHostOnClick.equals("") || serverPortOnClick.equals("") || usernameOnClick.equals("") || passwordOnClick.equals(""));

                if (enableLogin)
                {
                    loginButton.setEnabled(false);
                }
                else
                {
                    loginButton.setEnabled(true);
                }

                if (enableRegister)
                {
                    registerButton.setEnabled(false);
                }
                else
                {
                    registerButton.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        };

        serverHost = view.findViewById(R.id.serverHostText);
        serverPort = view.findViewById(R.id.serverPortText);
        username = view.findViewById(R.id.usernameText);
        password = view.findViewById(R.id.passwordText);
        firstName = view.findViewById(R.id.firstNameText);
        lastName = view.findViewById(R.id.lastNameText);
        email = view.findViewById(R.id.emailText);
        maleButton = view.findViewById(R.id.radioMale);
        femaleButton = view.findViewById(R.id.radioFemale);

        serverHost.addTextChangedListener(watcher);
        serverPort.addTextChangedListener(watcher);
        username.addTextChangedListener(watcher);
        password.addTextChangedListener(watcher);
        firstName.addTextChangedListener(watcher);
        lastName.addTextChangedListener(watcher);
        email.addTextChangedListener(watcher);


        loginButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String serverHostOnClick = serverHost.getText().toString();
                String serverPortOnClick = serverPort.getText().toString();
                String usernameOnClick = username.getText().toString();
                String passwordOnClick = password.getText().toString();

                try
                {
                    Handler uiThreadMessageHandler = new Handler(Looper.getMainLooper())
                    {
                        @Override
                        public void handleMessage(Message message)
                        {
                            Bundle bundle = message.getData();
                            String personID = bundle.getString("key", null);

                            if (personID != null)
                            {
                                if (listener != null)
                                {
                                    listener.notifyDone();
                                }

                                DataCache dataCache = DataCache.getInstance();
                                Person person = dataCache.getPeople().get(personID);
                                String name = person.getFirstName() + " " + person.getLastName();

                                Toast.makeText(getActivity(), name, Toast.LENGTH_LONG).show();
                            }
                            else
                            {
                                Toast.makeText(getActivity(), "Something Went Wrong", Toast.LENGTH_LONG).show();
                            }
                        }
                    };


                    LoginRequest request = new LoginRequest(usernameOnClick, passwordOnClick);
                    // Login Task
                    LoginTask task = new LoginTask(request, serverHostOnClick,serverPortOnClick,uiThreadMessageHandler);

                    ExecutorService executor = Executors.newSingleThreadExecutor();
                    executor.submit(task);
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
        }
        );

        registerButton = view.findViewById(R.id.registerbuttonID);

        registerButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String serverHostOnClick = serverHost.getText().toString();
                String serverPortOnClick = serverPort.getText().toString();
                String usernameOnClick = username.getText().toString();
                String passwordOnClick = password.getText().toString();
                String emailOnClick = email.getText().toString();
                String firstNameOnClick = firstName.getText().toString();
                String lastNameOnClick = lastName.getText().toString();
                String genderOnClick;

                if (maleButton.isChecked())
                {
                    genderOnClick = "m";
                }
                else if (femaleButton.isChecked())
                {
                    genderOnClick = "f";
                }
                else
                {
                    genderOnClick = "m";
                }

                try
                {
                    Handler uiThreadMessageHandler = new Handler(Looper.getMainLooper())
                    {
                        @Override
                        public void handleMessage(Message message)
                        {
                            Bundle bundle = message.getData();
                            String personID = bundle.getString("key", null);

                            if (personID != null)
                            {
                                if (listener != null)
                                {
                                    listener.notifyDone();
                                }

                                DataCache dataCache = DataCache.getInstance();
                                Person person = dataCache.getPeople().get(personID);
                                String name = person.getFirstName() + " " + person.getLastName();

                                Toast.makeText(getActivity(), name, Toast.LENGTH_LONG).show();
                            }
                            else
                            {
                                Toast.makeText(getActivity(), "Something Went Wrong", Toast.LENGTH_LONG).show();
                            }
                        }
                    };


                    RegisterRequest request = new RegisterRequest(usernameOnClick, passwordOnClick,
                            emailOnClick, firstNameOnClick, lastNameOnClick, genderOnClick);
                    // Register Task
                    RegisterTask task = new RegisterTask(request, serverHostOnClick,serverPortOnClick,uiThreadMessageHandler);

                    ExecutorService executor = Executors.newSingleThreadExecutor();
                    executor.submit(task);
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
        }
        );

        return view;
    }
}