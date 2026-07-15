package item;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author danuk
 */
public abstract class Item {
    private String name;
    private String description;
    private int value;

    public Item(String name, String description) {
        this(name, description, 0);
    }

    public Item(String name, String description, int value) {
        this.name = name;
        this.description = description;
        this.value = value;
    }

    public abstract String use(core.CombatMan target);

    public abstract String getType();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "[" + getType() + "] " + name + " - " + description;
    }
}
