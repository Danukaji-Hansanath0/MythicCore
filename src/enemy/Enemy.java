package enemy;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author danuk
 */
public abstract class Enemy implements core.CombatMan {
    private String name;
    private int health;
    private int maxHealth;
    private int baseAttack;
    private int expReward;

    public Enemy(String name, int maxHealth, int baseAttack, int expReward) {
        this.name = name;
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.baseAttack = baseAttack;
        this.expReward = expReward;
    }

    @Override
    public int attack() {
        int variance = (int) (Math.random() * 5) - 2;
        return baseAttack + variance;
    }

    @Override
    public void takeDmg(int dmg) {
        if (dmg < 0) return;
        this.health = Math.max(0, this.health - dmg);
        System.out.println(name + " took " + dmg + " damage! (HP: " + this.health + "/" + this.maxHealth + ")");
    }

    @Override
    public boolean isAlive() {
        return this.health > 0;
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

    public int getBaseAttack() {
        return baseAttack;
    }

    public void setBaseAttack(int baseAttack) {
        this.baseAttack = baseAttack;
    }

    public int getExpReward() {
        return expReward;
    }

    public void setExpReward(int expReward) {
        this.expReward = expReward;
    }

    public String getDisplayInfo() {
        return name + " [HP: " + health + "/" + maxHealth + " ATK: " + baseAttack + "]";
    }
}
