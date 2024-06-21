package com.example.cs240familymapclient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.SupportMapFragment;

import java.util.ArrayList;
import java.util.List;

import Model.Event;
import Model.Person;

public class PersonActivity extends AppCompatActivity
{

    private String personID;

    private DataCache dataCache = DataCache.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);

        Intent intent = getIntent();
        personID = intent.getStringExtra("personIDKey");
        Person person = dataCache.getPeople().get(personID);

        List<Event> personEvents = new ArrayList<>();
        List<Event> test = dataCache.getPersonEvents().get(personID);

        for (Event event : test)
        {
            if (dataCache.getAllowedEvents().contains(event))
            {
                personEvents.add(event);
            }
        }

        Event temp;

        for (int i = 0; i < personEvents.size(); i++)
        {
            for (int j = i; j < personEvents.size(); j++)
            {
                if (personEvents.get(i).getYear() > personEvents.get(j).getYear())
                {
                    temp = personEvents.get(i);
                    personEvents.set(i,personEvents.get(j));
                    personEvents.set(j,temp);
                }
            }
        }

        dataCache.findPersonFamily(personID);
        List<RelationshipModel> relationships = dataCache.getPersonRelationships().get(personID);

        TextView personFieldFirstName = findViewById(R.id.personActivityFirstName);
        personFieldFirstName.setText(person.getFirstName());

        TextView personFieldLastName = findViewById(R.id.personActivityLastName);
        personFieldLastName.setText(person.getLastName());

        TextView personFieldGender = findViewById(R.id.personActivityGender);
        if (person.getGender().equalsIgnoreCase("m"))
        {
            personFieldGender.setText("Male");
        }
        else
        {
            personFieldGender.setText("Female");
        }

        ExpandableListView expandableListView = findViewById(R.id.PersonExpandableList);


        expandableListView.setAdapter(new ExpandableListAdapter(personEvents, relationships));
    }

    private class ExpandableListAdapter extends BaseExpandableListAdapter {

        private static final int EVENTS_GROUP_POSITION = 0;
        private static final int FAMILY_GROUP_POSITION = 1;

        private final List<Event> events;
        private final List<RelationshipModel> persons;

        ExpandableListAdapter(List<Event> events, List<RelationshipModel> persons) {
            this.events = events;
            this.persons = persons;
        }

        @Override
        public int getGroupCount() {
            return 2;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            switch (groupPosition) {
                case EVENTS_GROUP_POSITION:
                    return events.size();
                case FAMILY_GROUP_POSITION:
                    return persons.size();
                default:
                    throw new IllegalArgumentException("Unrecognized group position: " + groupPosition);
            }
        }

        @Override
        public Object getGroup(int groupPosition) {
            // Not used
            return null;
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            // Not used
            return null;
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            if(convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.list_item_group, parent, false);
            }

            TextView titleView = convertView.findViewById(R.id.listTitle);

            switch (groupPosition) {
                case EVENTS_GROUP_POSITION:
                    titleView.setText("Events");
                    break;
                case FAMILY_GROUP_POSITION:
                    titleView.setText("Family");
                    break;
                default:
                    throw new IllegalArgumentException("Unrecognized group position: " + groupPosition);
            }

            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            View itemView;

            switch(groupPosition) {
                case EVENTS_GROUP_POSITION:
                    itemView = getLayoutInflater().inflate(R.layout.event_item, parent, false);
                    initializeEventView(itemView, childPosition);
                    break;
                case FAMILY_GROUP_POSITION:
                    itemView = getLayoutInflater().inflate(R.layout.family_item, parent, false);
                    initializeFamilyView(itemView, childPosition);
                    break;
                default:
                    throw new IllegalArgumentException("Unrecognized group position: " + groupPosition);
            }

            return itemView;
        }

        private void initializeEventView(View eventItemView, final int childPosition) {

            Event event = events.get(childPosition);

            String inputString;

            TextView eventType = eventItemView.findViewById(R.id.eventType);
            inputString = event.getEventType().toUpperCase() + ":";
            eventType.setText(inputString);

            TextView eventCity = eventItemView.findViewById(R.id.eventCity);
            inputString = event.getCity() + ",";
            eventCity.setText(inputString);

            TextView eventCountry = eventItemView.findViewById(R.id.eventCountry);
            inputString = event.getCountry();
            eventCountry.setText(inputString);

            TextView eventYear = eventItemView.findViewById(R.id.eventYear);
            inputString = "(" + event.getYear() + ")";
            eventYear.setText(inputString);

            TextView eventFirstName = eventItemView.findViewById(R.id.eventFirstName);
            inputString = dataCache.getPeople().get(event.getPersonID()).getFirstName();
            eventFirstName.setText(inputString);

            TextView eventLastName = eventItemView.findViewById(R.id.eventLastName);
            inputString = dataCache.getPeople().get(event.getPersonID()).getLastName();
            eventLastName.setText(inputString);

            eventItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(PersonActivity.this, "This works!!!!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(PersonActivity.this, EventActivity.class);
                    intent.putExtra("KEY",event.getEventID());
                    startActivity(intent);
                }
            });
        }

        private void initializeFamilyView(View familyItemView, final int childPosition) {

            RelationshipModel person = persons.get(childPosition);

            ImageView genderView = familyItemView.findViewById(R.id.familyGender);

            if (person.getFamilyMember().getGender().equalsIgnoreCase("m"))
            {
                genderView.setImageResource(R.drawable.ic_male);
            }
            else
            {
                genderView.setImageResource(R.drawable.ic_female);
            }

            String inputString;

            TextView familyFirstName = familyItemView.findViewById(R.id.familyFirstName);
            inputString = person.getFamilyMember().getFirstName();
            familyFirstName.setText(inputString);

            TextView familyLastName = familyItemView.findViewById(R.id.familyLastName);
            inputString = person.getFamilyMember().getLastName();
            familyLastName.setText(inputString);

            TextView familyRelationship = familyItemView.findViewById(R.id.familyRelationship);
            inputString = person.getRelationship();
            familyRelationship.setText(inputString);

            familyItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(PersonActivity.this, "This works!!!!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(PersonActivity.this, PersonActivity.class);
                    intent.putExtra("personIDKey",person.getFamilyMember().getPersonID());
                    startActivity(intent);
                }
            });
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
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