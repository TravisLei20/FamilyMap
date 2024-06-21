package com.example.cs240familymapclient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import Model.Event;
import Model.Person;


public class SearchActivity extends AppCompatActivity {

    private static final int PERSON_ITEM_VIEW_TYPE = 0;
    private static final int EVENT_ITEM_VIEW_TYPE = 1;

    private DataCache dataCache = DataCache.getInstance();

    private List<Person> people = new ArrayList<>();
    private List<Event> events = new ArrayList<>();

    private SearchView searchView;

    private Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        dataCache.calculateCorrectEvents();

        searchView = findViewById(R.id.searchView);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                filterList(s);
                return false;
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(SearchActivity.this));

        people = dataCache.getAllPeople();
        events = dataCache.getAllowedEvents();

        adapter = new Adapter(people, events);
        recyclerView.setAdapter(adapter);
    }

    private void filterList(String text)
    {
        List<Person> filteredPersonList = new ArrayList<>();
        List<Event> filteredEventList = new ArrayList<>();

        for (Person person : people)
        {
            if (person.getFirstName().toLowerCase().contains(text.toLowerCase()))
            {
                filteredPersonList.add(person);

                // issue here?
                List<Event> eventList = dataCache.getPersonEvents().get(person.getPersonID());

                for (Event personEvent : eventList)
                {
                    if (dataCache.getAllowedEvents().contains(personEvent))
                    {
                        filteredEventList.add(personEvent);
                    }
                }
            }
            else if (person.getLastName().toLowerCase().contains(text.toLowerCase()))
            {
                filteredPersonList.add(person);
                List<Event> eventList = dataCache.getPersonEvents().get(person.getPersonID());

                for (Event personEvent : eventList)
                {
                    if (dataCache.getAllowedEvents().contains(personEvent))
                    {
                        filteredEventList.add(personEvent);
                    }
                }
            }
        }


        for (Event event : events)
        {
            if (event.getEventType().toLowerCase().contains(text.toLowerCase()))
            {
                if (dataCache.getAllowedEvents().contains(event))
                {
                    filteredEventList.add(event);
                }
            }
            else if (event.getCountry().toLowerCase().contains(text.toLowerCase()))
            {
                if (dataCache.getAllowedEvents().contains(event))
                {
                    filteredEventList.add(event);
                }
            }
            else if (event.getCity().toLowerCase().contains(text.toLowerCase()))
            {
                if (dataCache.getAllowedEvents().contains(event))
                {
                    filteredEventList.add(event);
                }
            }
            else if (isInt(text))
            {
                if (event.getYear() == Integer.parseInt(text))
                {
                    if (dataCache.getAllowedEvents().contains(event))
                    {
                        filteredEventList.add(event);
                    }
                }
            }
        }

        if (filteredEventList.isEmpty() && filteredPersonList.isEmpty())
        {
            Toast.makeText(this,"No data found", Toast.LENGTH_SHORT).show();
            adapter.setFilteredList(filteredPersonList,filteredEventList);
        }
        else
        {
            adapter.setFilteredList(filteredPersonList,filteredEventList);
        }
    }

    private boolean isInt(String text)
    {
        try {
            Integer.parseInt(text);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private class Adapter extends RecyclerView.Adapter<ViewHolder> {
        private List<Person> people;
        private List<Event> events;

        Adapter(List<Person> skiResorts, List<Event> hikingTrails) {
            this.people = skiResorts;
            this.events = hikingTrails;
        }

        public void setFilteredList(List<Person> people, List<Event> events)
        {
            this.people = people;
            this.events = events;
            notifyDataSetChanged();
        }

        @Override
        public int getItemViewType(int position) {
            return position < people.size() ? PERSON_ITEM_VIEW_TYPE : EVENT_ITEM_VIEW_TYPE;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view;

            if(viewType == PERSON_ITEM_VIEW_TYPE) {
                view = getLayoutInflater().inflate(R.layout.family_item, parent, false);
            } else {
                view = getLayoutInflater().inflate(R.layout.event_item, parent, false);
            }

            return new ViewHolder(view, viewType);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            if(position < people.size()) {
                holder.bind(people.get(position));
            } else {
                holder.bind(events.get(position - people.size()));
            }
        }

        @Override
        public int getItemCount() {
            return people.size() + events.size();
        }
    }

    private class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private TextView personFirstName = null;
        private TextView personLastName = null;
        private ImageView personGender = null;

        private TextView eventType = null;
        private TextView eventCity = null;
        private TextView eventCountry = null;
        private TextView eventYear = null;
        private TextView eventFirstName = null;
        private TextView eventLastName = null;

        private final int viewType;
        private Person person;
        private Event event;

        ViewHolder(View view, int viewType) {
            super(view);
            this.viewType = viewType;

            itemView.setOnClickListener(this);

            if(viewType == PERSON_ITEM_VIEW_TYPE) {
                personGender = itemView.findViewById(R.id.familyGender);
                personFirstName = itemView.findViewById(R.id.familyFirstName);
                personLastName = itemView.findViewById(R.id.familyLastName);

            } else {
                eventType = itemView.findViewById(R.id.eventType);
                eventCity = itemView.findViewById(R.id.eventCity);
                eventCountry = itemView.findViewById(R.id.eventCountry);
                eventYear = itemView.findViewById(R.id.eventYear);
                eventFirstName = itemView.findViewById(R.id.eventFirstName);
                eventLastName = itemView.findViewById(R.id.eventLastName);
            }
        }

        private void bind(Person person) {
            this.person = person;

            if (person.getGender().equalsIgnoreCase("m"))
            {
                personGender.setImageResource(R.drawable.ic_male);
            }
            else
            {
                personGender.setImageResource(R.drawable.ic_female);
            }

            String inputString;

            inputString = person.getFirstName();
            personFirstName.setText(inputString);

            inputString = person.getLastName();
            personLastName.setText(inputString);

        }

        private void bind(Event event)
        {
            this.event = event;

            String inputString;

            inputString = event.getEventType().toUpperCase() + ":";
            eventType.setText(inputString);

            inputString = event.getCity() + ",";
            eventCity.setText(inputString);

            inputString = event.getCountry();
            eventCountry.setText(inputString);

            inputString = "(" + event.getYear() + ")";
            eventYear.setText(inputString);

            inputString = dataCache.getPeople().get(event.getPersonID()).getFirstName();
            eventFirstName.setText(inputString);

            inputString = dataCache.getPeople().get(event.getPersonID()).getLastName();
            eventLastName.setText(inputString);
        }

        @Override
        public void onClick(View view) {
            if(viewType == PERSON_ITEM_VIEW_TYPE)
            {
                Toast.makeText(SearchActivity.this, "This works!!!!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(SearchActivity.this, PersonActivity.class);
                intent.putExtra("personIDKey",person.getPersonID());
                startActivity(intent);
            }
            else
            {
                Toast.makeText(SearchActivity.this, "This works!!!!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(SearchActivity.this, EventActivity.class);
                intent.putExtra("KEY",event.getEventID());
                startActivity(intent);
            }
        }
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