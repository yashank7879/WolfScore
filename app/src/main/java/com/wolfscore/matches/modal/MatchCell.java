package com.wolfscore.matches.modal;

public class MatchCell {
    String routine_id;
    String user_id;
    String routine_name;
    String notes;
    int id;
    public MatchCell()
    {
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getRoutine_id() {
        return routine_id;
    }
    public void setRoutine_id(String routine_id) {
        this.routine_id = routine_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getRoutine_name() {
        return routine_name;
    }

    public void setRoutine_name(String routine_name) {
        this.routine_name = routine_name;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
