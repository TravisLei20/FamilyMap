package com.example.cs240familymapclient;


import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Model.Event;
import Model.Person;

public class DataCache
{
    private static DataCache instance = new DataCache();

    public static DataCache getInstance()
    {
        return instance;
    }

    private DataCache()
    {
        people = new HashMap<>();
        events = new HashMap<>();
        personEvents = new HashMap<>();

        personRelationships = new HashMap<>();
        userID = "";
        allPeople = new ArrayList(){};
        allEvents = new ArrayList(){};


        paternal = new ArrayList<>();
        maternal = new ArrayList<>();

        allowedEvents = new ArrayList<>();

        myColorsMap = new HashMap<>();
        baseColor = 0x000000;

        lastMarkerClickedOnMain = null;
        lastEventClickedOnMain = null;

        lifeStoryLines = true;
        familyTreeLines = true;
        spouseLines = true;
        fathersSide = true;
        mothersSide = true;
        maleEvents = true;
        femaleEvents = true;

    }

    private String userID;

    // ID to object
    private HashMap<String, Person> people;
    private  HashMap<String, Event> events;


    private  List<Person> allPeople;
    private List<Event> allEvents;

    // This is the events for each person
    private HashMap<String, List<Event>> personEvents;

    private Event lastEventClickedOnMain;
    private Marker lastMarkerClickedOnMain;

    private HashMap<String, Float> myColorsMap;
    private float baseColor;

    private List<Event> allowedEvents;

    private List<String> paternal;
    private List<String> maternal;

    private HashMap<String, List<RelationshipModel>> personRelationships;

    private boolean lifeStoryLines;
    private boolean familyTreeLines;
    private boolean spouseLines;
    private boolean fathersSide;
    private boolean mothersSide;
    private boolean maleEvents;
    private boolean femaleEvents;





    public void findPersonFamily(String personID)
    {
        Person person = people.get(personID);

        List<RelationshipModel> listOfFamily = new ArrayList(){};

        if (person.getFatherID() != null)
        {
            Person father = people.get(person.getFatherID());
            listOfFamily.add(new RelationshipModel(father,"father"));
        }
        if (person.getMotherID() != null)
        {
            Person mother = people.get(person.getMotherID());
            listOfFamily.add(new RelationshipModel(mother,"mother"));
        }
        if (person.getSpouseID() != null)
        {
            Person spouse = people.get(person.getSpouseID());
            listOfFamily.add(new RelationshipModel(spouse,"spouse"));
        }

        for (Person child : allPeople)
        {
            if (child.getFatherID() != null)
            {
                if (child.getFatherID().equals(personID))
                {
                    listOfFamily.add(new RelationshipModel(child,"child"));
                }
            }
            if (child.getMotherID() != null)
            {
                if (child.getMotherID().equals(personID))
                {
                    listOfFamily.add(new RelationshipModel(child,"child"));
                }
            }
        }

        personRelationships.put(personID,listOfFamily);
    }

    public void calculateCorrectEvents()
    {
        List<Event> goodEvents = new ArrayList<>();

        for (Event event : allEvents)
        {
            Person person = people.get(event.getPersonID());
            if (event.getPersonID().equals(userID))
            {
                if (person.getGender().equalsIgnoreCase("m") && maleEvents)
                {
                    goodEvents.add(event);
                }
                else if (person.getGender().equalsIgnoreCase("f") && femaleEvents)
                {
                    goodEvents.add(event);
                }
            }
            else if (person.getSpouseID().equalsIgnoreCase(userID))
            {
                if (person.getGender().equalsIgnoreCase("m") && maleEvents)
                {
                    goodEvents.add(event);
                }
                else if (person.getGender().equalsIgnoreCase("f") && femaleEvents)
                {
                    goodEvents.add(event);
                }
            }
            else if (person.getGender().equalsIgnoreCase("m") && maleEvents)
            {
                if (paternal.contains(person.getPersonID()) && fathersSide)
                {
                    goodEvents.add(event);
                }
                else if (maternal.contains(person.getPersonID()) && mothersSide)
                {
                    goodEvents.add(event);
                }
            }
            else if (person.getGender().equalsIgnoreCase("f") && femaleEvents)
            {
                if (paternal.contains(person.getPersonID()) && fathersSide)
                {
                    goodEvents.add(event);
                }
                else if (maternal.contains(person.getPersonID()) && mothersSide)
                {
                    goodEvents.add(event);
                }
            }
        }
        allowedEvents.clear();
        allowedEvents = goodEvents;
    }

    public void findSideAncestors()
    {
        Person user = people.get(userID);

        paternal.clear();
        maternal.clear();

        Person father = people.get(user.getFatherID());
        findFatherSide(father);

        Person mother = people.get(user.getMotherID());
        findMotherSide(mother);
    }

    private void findFatherSide(Person person)
    {
        if (person.getFatherID() != null)
        {
            Person father = people.get(person.getFatherID());
            findFatherSide(father);
        }
        if (person.getMotherID() != null)
        {
            Person mother = people.get(person.getMotherID());
            findFatherSide(mother);
        }
        paternal.add(person.getPersonID());
    }

    private void findMotherSide(Person person)
    {
        if (person.getFatherID() != null)
        {
            Person father = people.get(person.getFatherID());
            findMotherSide(father);
        }
        if (person.getMotherID() != null)
        {
            Person mother = people.get(person.getMotherID());
            findMotherSide(mother);
        }
        maternal.add(person.getPersonID());
    }


    public void clearAllDataCache()
    {
        people.clear();
        events.clear();
        personEvents.clear();

        personRelationships.clear();
        userID = "";
        allPeople.clear();
        allEvents.clear();

        maternal.clear();
        paternal.clear();
        allowedEvents.clear();
        allPeople.clear();
        lifeStoryLines = true;
        familyTreeLines = true;
        spouseLines = true;
        fathersSide = true;
        mothersSide = true;
        maleEvents = true;
        femaleEvents = true;
    }


    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public HashMap<String, Person> getPeople() {
        return people;
    }

    public void setPeople(HashMap<String, Person> people) {
        this.people = people;
    }

    public HashMap<String, Event> getEvents() {
        return events;
    }

    public void setEvents(HashMap<String, Event> events) {
        this.events = events;
    }

    public List<Person> getAllPeople() {
        return allPeople;
    }

    public void setAllPeople(List<Person> allPeople) {
        this.allPeople = allPeople;
    }

    public List<Event> getAllEvents() {
        return allEvents;
    }

    public void setAllEvents(List<Event> allEvents) {
        this.allEvents = allEvents;
    }

    public HashMap<String, List<Event>> getPersonEvents() {
        return personEvents;
    }

    public void setPersonEvents(HashMap<String, List<Event>> personEvents) {
        this.personEvents = personEvents;
    }

    public Event getLastEventClickedOnMain() {
        return lastEventClickedOnMain;
    }

    public void setLastEventClickedOnMain(Event lastEventClickedOnMain) {
        this.lastEventClickedOnMain = lastEventClickedOnMain;
    }

    public Marker getLastMarkerClickedOnMain() {
        return lastMarkerClickedOnMain;
    }

    public void setLastMarkerClickedOnMain(Marker lastMarkerClickedOnMain) {
        this.lastMarkerClickedOnMain = lastMarkerClickedOnMain;
    }

    public HashMap<String, Float> getMyColorsMap() {
        return myColorsMap;
    }

    public void setMyColorsMap(HashMap<String, Float> myColorsMap) {
        this.myColorsMap = myColorsMap;
    }

    public float getBaseColor() {
        return baseColor;
    }

    public void setBaseColor(float baseColor) {
        this.baseColor = baseColor;
    }

    public List<Event> getAllowedEvents() {
        return allowedEvents;
    }

    public void setAllowedEvents(List<Event> allowedEvents) {
        this.allowedEvents = allowedEvents;
    }

    public HashMap<String, List<RelationshipModel>> getPersonRelationships() {
        return personRelationships;
    }

    public void setPersonRelationships(HashMap<String, List<RelationshipModel>> personRelationships) {
        this.personRelationships = personRelationships;
    }

    public boolean isLifeStoryLines() {
        return lifeStoryLines;
    }

    public void setLifeStoryLines(boolean lifeStoryLines) {
        this.lifeStoryLines = lifeStoryLines;
    }

    public boolean isFamilyTreeLines() {
        return familyTreeLines;
    }

    public void setFamilyTreeLines(boolean familyTreeLines) {
        this.familyTreeLines = familyTreeLines;
    }

    public boolean isSpouseLines() {
        return spouseLines;
    }

    public void setSpouseLines(boolean spouseLines) {
        this.spouseLines = spouseLines;
    }

    public boolean isFathersSide() {
        return fathersSide;
    }

    public void setFathersSide(boolean fathersSide) {
        this.fathersSide = fathersSide;
    }

    public boolean isMothersSide() {
        return mothersSide;
    }

    public void setMothersSide(boolean mothersSide) {
        this.mothersSide = mothersSide;
    }

    public boolean isMaleEvents() {
        return maleEvents;
    }

    public void setMaleEvents(boolean maleEvents) {
        this.maleEvents = maleEvents;
    }

    public boolean isFemaleEvents() {
        return femaleEvents;
    }

    public void setFemaleEvents(boolean femaleEvents) {
        this.femaleEvents = femaleEvents;
    }

    public List<String> getPaternal() {
        return paternal;
    }

    public List<String> getMaternal() {
        return maternal;
    }
}
