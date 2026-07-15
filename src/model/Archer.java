package model;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author danuk
 */
public class Archer extends Character {

    public Archer(String name) {
        super(name, 100);
    }

    public Archer(String name, int maxHealth) {
        super(name, maxHealth);
    }

    @Override
    protected int getBaseAttack() {
        return 12 + (getLevel() * 2);
    }

    @Override
    public int attack() {
        int damage = super.attack();
        if (Math.random() < 0.3) {
            int bonus = (int) (damage * 0.5);
            damage += bonus;
            System.out.println(getName() + " fires a rapid double shot!");
        }
        return damage;
    }
}
