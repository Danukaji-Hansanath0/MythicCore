package enemy;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author danuk
 */
public class DragonBoss extends Enemy {

    private boolean enraged;

    public DragonBoss() {
        super("Ancient Dragon", 300, 30, 200);
        this.enraged = false;
    }

    public DragonBoss(String name) {
        super(name, 300, 30, 200);
        this.enraged = false;
    }

    @Override
    public int attack() {
        if (!enraged && getHealth() <= getMaxHealth() / 2) {
            enraged = true;
            setBaseAttack(getBaseAttack() + 15);
            System.out.println(getName() + " enters ENRAGE mode! Attack power surges!");
        }
        int dmg = super.attack();
        if (enraged && Math.random() < 0.3) {
            dmg += 20;
            System.out.println(getName() + " breathes devastating FIRE!");
        }
        return dmg;
    }

    public boolean isEnraged() {
        return enraged;
    }
}
