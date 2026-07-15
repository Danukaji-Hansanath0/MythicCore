package core;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author danuk
 */
public interface CombatMan {
    int attack();
    void takeDmg(int dmg);
    boolean isAlive();
    String getName();
    int getHealth();
    int getMaxHealth();
}
