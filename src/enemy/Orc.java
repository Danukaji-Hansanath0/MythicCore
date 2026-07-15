package enemy;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author danuk
 */
public class Orc extends Enemy {

    public Orc() {
        super("Orc", 80, 14, 50);
    }

    public Orc(String name) {
        super(name, 80, 14, 50);
    }

    @Override
    public int attack() {
        int dmg = super.attack();
        if (Math.random() < 0.15) {
            dmg = (int) (dmg * 1.8);
            System.out.println(getName() + " swings a massive axe overhead!");
        }
        return dmg;
    }
}
