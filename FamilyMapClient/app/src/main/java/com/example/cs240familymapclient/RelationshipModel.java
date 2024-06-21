package com.example.cs240familymapclient;

import Model.Person;

public class RelationshipModel
{
    private Person familyMember;
    private String relationship;

    public RelationshipModel(Person familyMember, String relationship) {
        this.familyMember = familyMember;
        this.relationship = relationship;
    }

    public Person getFamilyMember() {
        return familyMember;
    }

    public void setFamilyMember(Person familyMember) {
        this.familyMember = familyMember;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }
}
