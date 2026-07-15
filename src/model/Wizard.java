package model;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author danuk
 */
public class Wizard extends Character {

    public Wizard(String name) {
        super(name, 80);
    }

    public Wizard(String name, int maxHealth) {
        super(name, maxHealth);
    }

    @Override
    protected int getBaseAttack() {
        return 20 + (getLevel() * 4);
    }

    @Override
    public int attack() {
        int damage = super.attack();
        if (Math.random() < 0.25) {
            damage = (int) (damage * 2.0);
            System.out.println(getName() + " unleashes a devastating ARCANE BLAST!");
        }
        return damage;
    }
}
