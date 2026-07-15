package model;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author danuk
 */
public class Warrior extends Character {

    public Warrior(String name) {
        super(name, 150);
    }

    public Warrior(String name, int maxHealth) {
        super(name, maxHealth);
    }

    @Override
    protected int getBaseAttack() {
        return 15 + (getLevel() * 3);
    }

    @Override
    public int attack() {
        int damage = super.attack();
        if (Math.random() < 0.2) {
            damage = (int) (damage * 1.5);
            System.out.println(getName() + " lands a devastating CRITICAL STRIKE!");
        }
        return damage;
    }
}
