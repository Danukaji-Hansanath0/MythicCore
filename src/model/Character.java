package model;

import item.Inventory;
import item.Weapon;
import progression.QuestBook;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author danuk
 */
public abstract class Character implements core.CombatMan {
    private String name;
    private int health;
    private int maxHealth;
    private int level;
    private int exp;
    private Inventory inventory;
    private QuestBook questBook;

    public Character(String name) {
        this(name, 100);
    }

    public Character(String name, int maxHealth) {
        this.name = name;
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.level = 1;
        this.exp = 0;
        this.inventory = new Inventory();
        this.questBook = new QuestBook();
    }

    @Override
    public int attack() {
        int baseDamage = getBaseAttack();
        Weapon equipped = inventory.getEquippedWeapon();
        if (equipped != null) {
            baseDamage += equipped.getAttackPower();
        }
        return baseDamage;
    }

    protected abstract int getBaseAttack();

    public void gainExperience(int amount) {
        if (amount <= 0) return;
        this.exp += amount;
        System.out.println(name + " gained " + amount + " EXP");

        while (this.exp >= (this.level * 100)) {
            this.exp -= (this.level * 100);
            this.level++;
            this.maxHealth += 25;
            this.health = this.maxHealth;

            System.out.println("LEVEL UP! " + name + " progressed to Level " + level + "! Vitals restored.");
        }
    }

    @Override
    public boolean isAlive() {
        return this.health > 0;
    }

    @Override
    public void takeDmg(int amount) {
        if (amount < 0) return;

        this.health = Math.max(0, this.health - amount);
        System.out.println(name + " sustained " + amount + " points of damage! (Current HP: " + this.health + "/" + this.maxHealth + ")");
    }

    public void heal(int amount) {
        if (amount <= 0) return;
        this.health = Math.min(this.maxHealth, this.health + amount);
        System.out.println(name + " recovered " + amount + " HP! (Current HP: " + this.health + "/" + this.maxHealth + ")");
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = Math.max(0, Math.min(this.maxHealth, health));
    }

    @Override
    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public QuestBook getQuestBook() {
        return questBook;
    }

    public String getStats() {
        return name + " [Lv." + level + "] HP: " + health + "/" + maxHealth + " | EXP: " + exp + "/" + (level * 100);
    }
}
