package br.ufc.dc.sd4mp.todo.mytodolist;

/**
 * Created by Caio on 11/05/2015.
 */
public class Task {

    private int id;
    private String title;
    private String description;

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

    public String toString() {
        return getTitle() + ": " + getDescription();
    }
}