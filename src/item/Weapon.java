package item;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author danuk
 */
public class Weapon extends Item {
    private int attackPower;

    public Weapon(String name, String description, int attackPower) {
        super(name, description, attackPower * 10);
        this.attackPower = attackPower;
    }

    public Weapon(String name, int attackPower) {
        this(name, "A trusty weapon", attackPower);
    }

    @Override
    public String use(core.CombatMan target) {
        return getName() + " is equipped. ATK +" + attackPower;
    }

    @Override
    public String getType() {
        return "Weapon";
    }

    public int getAttackPower() {
        return attackPower;
    }

    public void setAttackPower(int attackPower) {
        this.attackPower = attackPower;
    }
}
