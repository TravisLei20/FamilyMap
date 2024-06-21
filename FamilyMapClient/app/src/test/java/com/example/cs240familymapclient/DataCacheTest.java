package com.example.cs240familymapclient;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import Model.Event;
import Model.Person;

public class DataCacheTest
{
    private DataCache dataCache = DataCache.getInstance();

    private Person son1;
    private Person son2;
    private Person daughter1;
    private Person daughter2;
    private Person father1;
    private Person mother1;
    private Person husband1;
    private Person child1;

    private Event child1Birth;
    private Event daughter1Birth;
    private Event husband1Birth;
    private Event mother1Birth;
    private Event father1Birth;

    // No @BeforeAll
    private void setUp()
    {
        dataCache.clearAllDataCache();
        dataCache.setUserID("child1");
        son1 = new Person("son1","user","son1","family","m",null,"father1","mother1");
        son2 = new Person("son2","user","son2","family","m",null,"father1","mother1");
        daughter1 = new Person("daughter1","user","daughter1","family","f","husband1","father1","mother1");
        daughter2 = new Person("daughter2","user","daughter2","family","f",null,"father1","mother1");
        father1 = new Person("father1","user","father1","family","m","mother1",null , null);
        mother1 = new Person("mother1","user","mother1","family","f","father1",null , null);
        husband1 = new Person("husband1","user","husband1","family","m","daughter1",null,null);
        child1 = new Person("child1","user","child1","family","m",null,"husband1","daughter1");
        dataCache.getAllPeople().add(son1);
        dataCache.getAllPeople().add(son2);
        dataCache.getAllPeople().add(daughter1);
        dataCache.getAllPeople().add(daughter2);
        dataCache.getAllPeople().add(father1);
        dataCache.getAllPeople().add(mother1);
        dataCache.getAllPeople().add(husband1);
        dataCache.getAllPeople().add(child1);

        for (Person person : dataCache.getAllPeople())
        {
            dataCache.getPeople().put(person.getPersonID(),person);
        }

        child1Birth = new Event("child1Birth","user","child1",(float) 10.2,(float) 10.2,"US","LA","birth",1);
        daughter1Birth = new Event("daughter1Birth","user","daughter1",(float) 10.2,(float) 10.2,"US","LA","birth",1);
        husband1Birth = new Event("husband1Birth","user","husband1",(float) 10.2,(float) 10.2,"US","LA","birth",1);
        mother1Birth = new Event("mother1Birth","user","mother1",(float) 10.2,(float) 10.2,"US","LA","birth",1);
        father1Birth = new Event("father1Birth","user","father1",(float) 10.2,(float) 10.2,"US","LA","birth",1);
        dataCache.getAllEvents().add(child1Birth);
        dataCache.getAllEvents().add(daughter1Birth);
        dataCache.getAllEvents().add(husband1Birth);
        dataCache.getAllEvents().add(mother1Birth);
        dataCache.getAllEvents().add(father1Birth);
        dataCache.getEvents().put(child1Birth.getEventID(),child1Birth);
        dataCache.getEvents().put(daughter1Birth.getEventID(),daughter1Birth);
        dataCache.getEvents().put(husband1Birth.getEventID(),husband1Birth);
        dataCache.getEvents().put(mother1Birth.getEventID(),mother1Birth);
        dataCache.getEvents().put(father1Birth.getEventID(),father1Birth);

        dataCache.findSideAncestors();
    }

    @Test
    public void testFindPersonFamily()
    {
        setUp();

        dataCache.findPersonFamily(daughter1.getPersonID());

        List<RelationshipModel> actual = dataCache.getPersonRelationships().get(daughter1.getPersonID());

        for (RelationshipModel person : actual)
        {
            assertTrue(person.getFamilyMember().equals(husband1) ||
                    person.getFamilyMember().equals(father1) ||
                    person.getFamilyMember().equals(mother1) ||
                    person.getFamilyMember().equals(child1));
        }
    }

    @Test
    public void testFindAncestors()
    {
        setUp();
        List<String> mothersSide = new ArrayList<>();
        mothersSide.add(father1.getPersonID());
        mothersSide.add(mother1.getPersonID());
        mothersSide.add(daughter1.getPersonID());

        assertEquals(mothersSide,dataCache.getMaternal());
    }

    @Test
    public void testCalculateCorrectEvents()
    {
        setUp();
        List<Event> allowedEventsTest = new ArrayList<>();
        allowedEventsTest.add(child1Birth);
        allowedEventsTest.add(daughter1Birth);
        allowedEventsTest.add(husband1Birth);
        allowedEventsTest.add(mother1Birth);
        allowedEventsTest.add(father1Birth);

        dataCache.calculateCorrectEvents();

        assertEquals(allowedEventsTest, dataCache.getAllowedEvents());
    }

    @Test
    public void testCalculateCorrectEventsMaleFilterOff()
    {
        setUp();
        List<Event> allowedEventsTest = new ArrayList<>();

        allowedEventsTest.add(daughter1Birth);
        allowedEventsTest.add(mother1Birth);

        dataCache.setMaleEvents(false);

        dataCache.calculateCorrectEvents();

        assertEquals(allowedEventsTest, dataCache.getAllowedEvents());

        dataCache.setMaleEvents(true);
    }

    @Test
    public void testCalculateCorrectEventsFemaleFilterOff()
    {
        setUp();
        List<Event> allowedEventsTest = new ArrayList<>();
        allowedEventsTest.add(child1Birth);
        allowedEventsTest.add(husband1Birth);

        allowedEventsTest.add(father1Birth);

        dataCache.setFemaleEvents(false);

        dataCache.calculateCorrectEvents();

        dataCache.setFemaleEvents(true);

        assertEquals(allowedEventsTest, dataCache.getAllowedEvents());
    }
}
