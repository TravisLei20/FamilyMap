package com.example.cs240familymapclient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import Model.Event;

public class EventActivity extends AppCompatActivity {

    DataCache dataCache = DataCache.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        Intent intent = getIntent();
        String eventID = intent.getStringExtra("KEY");
        Event event = dataCache.getEvents().get(eventID);

        FragmentManager fragmentManager = this.getSupportFragmentManager();

        Fragment fragment = createMapFragment();

        Bundle bundle = new Bundle();
        bundle.putString("KEY",event.getEventID());
        fragment.setArguments(bundle);
        fragmentManager.beginTransaction().add(R.id.eventFragmentFrameLayout, fragment).commit();
    }

    private Fragment createMapFragment()
    {
        MapsFragment fragment = new MapsFragment();
        return fragment;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(item.getItemId() == android.R.id.home)
        {
            Intent intent= new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        return true;
    }
}