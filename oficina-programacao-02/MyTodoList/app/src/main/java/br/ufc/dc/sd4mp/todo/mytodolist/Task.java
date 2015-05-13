package br.ufc.dc.sd4mp.todo.mytodolist;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Caio on 11/05/2015.
 */
public class Task {

    private final String NEW_LINE = System.getProperty("line.separator");

    private int id;
    private String title;
    private String description;
    private String date;
    private boolean status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = Util.formatDate(date, Util.DATE_TIME);
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean getStatusRaw() {
        return this.status;
    }

    public String getStatus() {
        return this.status ? "Done" : "To Do";
    }

    public String toString() {
        return "Title: " + getTitle() + NEW_LINE + "Description: " + getDescription() + NEW_LINE + "Date: " + getDate() + NEW_LINE + getStatus();
    }
}
