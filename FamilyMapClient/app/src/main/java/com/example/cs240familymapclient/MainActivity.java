package com.example.cs240familymapclient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.google.androidgamesdk.gametextinput.Listener;
import com.google.androidgamesdk.gametextinput.State;

public class MainActivity extends AppCompatActivity implements LoginFragment.Listener
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = this.getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragmentFrameLayout);

        if (fragment == null)
        {
            fragment = createLoginFragment();

            fragmentManager.beginTransaction().add(R.id.fragmentFrameLayout, fragment).commit();
        }
        else
        {
            if (fragment instanceof LoginFragment)
            {
                ((LoginFragment) fragment).registerListener(this);
            }
        }
    }

    private Fragment createLoginFragment()
    {
        LoginFragment fragment = new LoginFragment();
        fragment.registerListener(this);
        return fragment;
    }

    @Override
    public void notifyDone()
    {
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        Fragment fragment = new MapsFragment();

        fragmentManager.beginTransaction().replace(R.id.fragmentFrameLayout, fragment).commit();
    }

}