package com.example.attendancesystem;

public class StudentModel {
    private int id;
    private String name;
    private int classes_held;
    private int classes_attended;

    @Override
    public String toString() {
        return
                "Name= " + name + "\n"+
                "Classes Held= " + classes_held +
                "   Classes Attended=" + classes_attended;

    }

    public StudentModel(int id, String name, int classes_held, int classes_attended)
    {
        this.id=id;
        this.name=name;
        this.classes_held=classes_held;
        this.classes_attended=classes_attended;
    }

    public StudentModel()
    {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getClasses_held() {
        return classes_held;
    }

    public void setClasses_held(int classes_held) {
        this.classes_held = classes_held;
    }

    public int getClasses_attended() {
        return classes_attended;
    }

    public void setClasses_attended(int classes_attended) {
        this.classes_attended = classes_attended;
    }
}

