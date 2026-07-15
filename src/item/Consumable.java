package item;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author danuk
 */
public class Consumable extends Item {
    private int healAmount;

    public Consumable(String name, String description, int healAmount) {
        super(name, description, healAmount);
        this.healAmount = healAmount;
    }

    public Consumable(String name, int healAmount) {
        this(name, "Restores health", healAmount);
    }

    @Override
    public String use(core.CombatMan target) {
        int before = target.getHealth();
        if (target instanceof model.Character) {
            ((model.Character) target).heal(healAmount);
        }
        return getName() + " used! HP " + before + " -> " + target.getHealth();
    }

    @Override
    public String getType() {
        return "Consumable";
    }

    public int getHealAmount() {
        return healAmount;
    }

    public void setHealAmount(int healAmount) {
        this.healAmount = healAmount;
    }
}
