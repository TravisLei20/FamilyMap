package com.example.cs240familymapclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import Model.Event;

public class SettingsActivity extends AppCompatActivity {

    DataCache dataCache = DataCache.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        ToggleButton lifeStory = (ToggleButton) findViewById(R.id.lifeStoryFilter);
        lifeStory.setChecked(dataCache.isLifeStoryLines());
        lifeStory.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    dataCache.setLifeStoryLines(true);
                } else {
                    dataCache.setLifeStoryLines(false);
                }
            }
        });

        ToggleButton familyTree = (ToggleButton) findViewById(R.id.familyTreeFilter);
        familyTree.setChecked(dataCache.isFamilyTreeLines());
        familyTree.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    dataCache.setFamilyTreeLines(true);
                } else {
                    dataCache.setFamilyTreeLines(false);
                }
            }
        });

        ToggleButton spouseFilter = (ToggleButton) findViewById(R.id.spouseFilter);
        spouseFilter.setChecked(dataCache.isSpouseLines());
        spouseFilter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    dataCache.setSpouseLines(true);
                } else {
                    dataCache.setSpouseLines(false);
                }
            }
        });

        ToggleButton fatherFilter = (ToggleButton) findViewById(R.id.fatherFilter);
        fatherFilter.setChecked(dataCache.isFathersSide());
        fatherFilter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    dataCache.setFathersSide(true);
                } else {
                    dataCache.setFathersSide(false);
                }
            }
        });

        ToggleButton motherFilter = (ToggleButton) findViewById(R.id.motherFilter);
        motherFilter.setChecked(dataCache.isMothersSide());
        motherFilter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    dataCache.setMothersSide(true);
                } else {
                    dataCache.setMothersSide(false);
                }
            }
        });

        ToggleButton maleFilter = (ToggleButton) findViewById(R.id.maleFilter);
        maleFilter.setChecked(dataCache.isMaleEvents());
        maleFilter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    dataCache.setMaleEvents(true);
                } else {
                    dataCache.setMaleEvents(false);
                }
            }
        });

        ToggleButton femaleFilter = (ToggleButton) findViewById(R.id.femaleFilter);
        femaleFilter.setChecked(dataCache.isFemaleEvents());
        femaleFilter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    dataCache.setFemaleEvents(true);
                } else {
                    dataCache.setFemaleEvents(false);
                }
            }
        });

        TextView logoutField = findViewById(R.id.logout);

        logoutField.setOnClickListener(new View.OnClickListener()
           {
               @Override
               public void onClick(View view)
               {
                   Toast.makeText(SettingsActivity.this, "This works!!!!", Toast.LENGTH_LONG).show();
                   Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                   dataCache.clearAllDataCache();
                   startActivity(intent);
               }
           }
        );
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