package com.example.cs240familymapclient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Model.Event;
import Model.Person;

public class MapsFragment extends Fragment implements GoogleMap.OnMarkerClickListener, OnMapReadyCallback
{

    private GoogleMap myMap;

    private DataCache dataCache = DataCache.getInstance();

    private View view;

    private Marker marker;

    private HashMap<String, Marker> allMarkers;

    private int familyLineColor = Color.RED;
    private int spouseLineColor = Color.YELLOW;

    private List<PolylineOptions> familyLines;

    private List<Polyline> lines = new ArrayList<>();

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        TextView personField = view.findViewById(R.id.eventTextOnMap);

        personField.setOnClickListener(new View.OnClickListener()
           {
               @Override
               public void onClick(View view)
               {
                   Intent intent = new Intent(getActivity(), PersonActivity.class);
                   Event event = (Event)marker.getTag();
                   if (event != null)
                   {
                       Toast.makeText(getActivity(), "This works!!!!", Toast.LENGTH_LONG).show();
                       intent.putExtra("personIDKey",event.getPersonID());
                       startActivity(intent);
                   }
               }
           }
        );

        myMap = googleMap;

        myMap.setOnMarkerClickListener(this);

        allMarkers = new HashMap<>();

        dataCache.calculateCorrectEvents();

        createAllMarkers();

        Bundle bundle = getArguments();

        if (bundle != null)
        {
            String eventID = bundle.getString("KEY");
            onMarkerClick(allMarkers.get(eventID));
        }
    }

    private void createAllMarkers()
    {

        for (Event event : dataCache.getAllowedEvents())
        {
            LatLng latLng = new LatLng(event.getLatitude(),event.getLongitude());

            BitmapDescriptor color;

            if (dataCache.getMyColorsMap().containsKey(event.getEventType().toLowerCase()))
            {
                color = BitmapDescriptorFactory.defaultMarker(dataCache.getMyColorsMap().get(event.getEventType().toLowerCase()));
            }
            else
            {
                color = (BitmapDescriptorFactory.defaultMarker(dataCache.getBaseColor()));
                dataCache.getMyColorsMap().put(event.getEventType().toLowerCase(), dataCache.getBaseColor());
                dataCache.setBaseColor(dataCache.getBaseColor() + 0x00001f);
            }

            Marker marker = myMap.addMarker(new MarkerOptions().position(latLng).icon(color));
            myMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            marker.setTag(event);
            allMarkers.put(event.getEventID(),marker);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (this.getContext().getClass() == EventActivity.class)
        {
            setHasOptionsMenu(false);
        }
        else
        {
            setHasOptionsMenu(true);
        }

    }

    @Override
    public void onResume()
    {
        super.onResume();
        if (myMap != null)
        {
            myMap.clear();
            dataCache.calculateCorrectEvents();
            createAllMarkers();
            if (dataCache.getAllowedEvents().contains(dataCache.getLastEventClickedOnMain()))
            {
                onMarkerClick(dataCache.getLastMarkerClickedOnMain());
            }
            else
            {
                TextView nameView = view.findViewById(R.id.nameTextOnMap);
                nameView.setText("Click a Marker");
                TextView eventView = view.findViewById(R.id.eventTextOnMap);
                eventView.setText("The previous Marker has been filtered out");
                ImageView genderView = view.findViewById(R.id.gender);
                genderView.setImageResource(R.drawable.ic_marker);
            }
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_maps, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }



        return this.view;
    }


    @Override
    public boolean onMarkerClick(@NonNull Marker marker)
    {
        if (this.getContext().getClass() == MainActivity.class)
        {
            dataCache.setLastMarkerClickedOnMain(marker);
        }

        LatLng latLng = marker.getPosition();
        myMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        this.marker = marker;
        Event event = (Event)marker.getTag();

        if (event == null)
        {
            event = dataCache.getLastEventClickedOnMain();
        }
        else if (this.getContext().getClass() == MainActivity.class)
        {
            dataCache.setLastEventClickedOnMain(event);
        }

        Person person = dataCache.getPeople().get(event.getPersonID());

        String nameOfPerson = person.getFirstName() + " " + person.getLastName();
        String eventInfo =  event.getEventType().toUpperCase()
                            + ": " + event.getCity() + ", " +
                            event.getCountry() + " (" + event.getYear() +
                            ")";

        TextView nameView = view.findViewById(R.id.nameTextOnMap);
        nameView.setText(nameOfPerson);
        TextView eventView = view.findViewById(R.id.eventTextOnMap);
        eventView.setText(eventInfo);
        ImageView genderView = view.findViewById(R.id.gender);

        if (person.getGender().equalsIgnoreCase("m"))
        {
            genderView.setImageResource(R.drawable.ic_male);
        }
        else
        {
            genderView.setImageResource(R.drawable.ic_female);
        }

        List<Event> lineList = new ArrayList<>();

        if (dataCache.isLifeStoryLines())
        {
            List<Event> list = dataCache.getPersonEvents().get(person.getPersonID());
            for (Event inputEvent : list)
            {
                lineList.add(inputEvent);
            }
        }

        if (!lines.isEmpty())
        {
            for (Polyline line : lines)
            {
                line.remove();
            }
            lines.clear();
        }

        LatLng startPoint = marker.getPosition();

        if (dataCache.isLifeStoryLines())
        {
            for (Event eventLines : lineList)
            {
                LatLng endPoint = new LatLng(eventLines.getLatitude(),eventLines.getLongitude());
                PolylineOptions lineSetup = new PolylineOptions().add(startPoint).add(endPoint).width((float) 10);
                lines.add(myMap.addPolyline(lineSetup));
            }
        }

        if (dataCache.isSpouseLines())
        {
            if (person.getSpouseID() != null)
            {
                Person spouse = dataCache.getPeople().get(person.getSpouseID());
                List<Event> spouseEvents = dataCache.getPersonEvents().get(spouse.getPersonID());
                Event selectedSpouseEvent = null;
                int year = 9999;
                for (Event spouseEvent : spouseEvents)
                {
                    if (spouseEvent.getEventType().equalsIgnoreCase("birth"))
                    {
                        selectedSpouseEvent = spouseEvent;
                        break;
                    }
                    else if (year > spouseEvent.getYear())
                    {
                        year = spouseEvent.getYear();
                        selectedSpouseEvent = spouseEvent;
                    }
                }
                // made changes here
                if (selectedSpouseEvent != null && dataCache.getAllowedEvents().contains(selectedSpouseEvent))
                {
                    LatLng endPoint = new LatLng(selectedSpouseEvent.getLatitude(),selectedSpouseEvent.getLongitude());
                    PolylineOptions lineSetup = new PolylineOptions().add(startPoint).add(endPoint).width((float) 10).color(spouseLineColor);
                    lines.add(myMap.addPolyline(lineSetup));
                }
            }
        }

        if (dataCache.isFamilyTreeLines())
        {
            drawFamilyLines(person,startPoint, 10);
        }

        return false;
    }

    void drawFamilyLines(Person person, LatLng startPoint, float lineWidth)
    {
        Log.d("map fragment", "Drawing line for " + person.getFirstName() + person.getLastName());
        if (person.getFatherID() != null)
        {
            Log.d("map fragment", "adding father line");
            List<Event> fatherEvents = dataCache.getPersonEvents().get(person.getFatherID());
            Event selectedEvent = null;
            int year = 9999;

            for (Event fatherEvent : fatherEvents)
            {
                if (fatherEvent.getEventType().equalsIgnoreCase("birth"))
                {
                    selectedEvent = fatherEvent;
                    break;
                }
                else if (year > fatherEvent.getYear())
                {
                    year = fatherEvent.getYear();
                    selectedEvent = fatherEvent;
                }
            }

            LatLng endPoint = null;
            if (selectedEvent != null && startPoint != null && dataCache.getAllowedEvents().contains(selectedEvent))
            {
                endPoint = new LatLng(selectedEvent.getLatitude(),selectedEvent.getLongitude());
                PolylineOptions lineSetup = new PolylineOptions().add(startPoint).add(endPoint).width(lineWidth).color(familyLineColor);
                lines.add(myMap.addPolyline(lineSetup));
            }
            Person father = dataCache.getPeople().get(person.getFatherID());
            drawFamilyLines(father,endPoint, (float)(lineWidth/1.5));
        }

        if (person.getMotherID() != null)
        {
            Log.d("map fragment", "adding mother line");
            List<Event> motherEvents = dataCache.getPersonEvents().get(person.getMotherID());
            Event selectedEvent = null;
            int year = 9999;
            for (Event motherEvent : motherEvents)
            {
                if (motherEvent.getEventType().equalsIgnoreCase("birth"))
                {
                    selectedEvent = motherEvent;
                    break;
                }
                else if (year > motherEvent.getYear())
                {
                    year = motherEvent.getYear();
                    selectedEvent = motherEvent;
                }
            }
            LatLng endPoint = null;
            if (selectedEvent != null && startPoint != null && dataCache.getAllowedEvents().contains(selectedEvent))
            {
                endPoint = new LatLng(selectedEvent.getLatitude(),selectedEvent.getLongitude());
                PolylineOptions lineSetup = new PolylineOptions().add(startPoint).add(endPoint).width(lineWidth).color(familyLineColor);
                lines.add(myMap.addPolyline(lineSetup));
            }
            Person mother = dataCache.getPeople().get(person.getMotherID());
            drawFamilyLines(mother,endPoint, (float)(lineWidth/1.5));
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settingsMenuItem:
                Toast.makeText(getActivity(), "This works!!!!", Toast.LENGTH_LONG).show();
                Intent settingsIntent = new Intent(getActivity(), SettingsActivity.class);
                startActivity(settingsIntent);
                return true;

            case R.id.searchMenuItem:
                Toast.makeText(getActivity(), "This works!!!!", Toast.LENGTH_LONG).show();
                Intent searchIntent = new Intent(getActivity(), SearchActivity.class);
                startActivity(searchIntent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}