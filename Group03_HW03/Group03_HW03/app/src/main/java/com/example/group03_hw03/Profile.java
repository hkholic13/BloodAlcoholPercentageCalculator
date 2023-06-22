//a. Assignment HW03
//b. Profile.java
//c. Group - 03
//1) Harika Priya Prathipati
//2) Himanshi Khatri
package com.example.group03_hw03;

public class Profile {
    private int weight;
    private String gender;

    public Profile(int weight, String gender) {
        this.weight = weight;
        this.gender = gender;
    }
    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
