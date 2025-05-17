package com.mirea.mitrofanovms.employeedb;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class SuperHero {
    @PrimaryKey(autoGenerate = true)
    public long id;

    public String name;
    public String superpower;
    public int strengthLevel;

    public SuperHero(String name, String superpower, int strengthLevel) {
        this.name = name;
        this.superpower = superpower;
        this.strengthLevel = strengthLevel;
    }

    public long getId() { return id; }
    public String getName() { return name; }
    public String getSuperpower() { return superpower; }
    public int getStrengthLevel() { return strengthLevel; }
}