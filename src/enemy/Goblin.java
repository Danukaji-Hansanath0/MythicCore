package enemy;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author danuk
 */
public class Goblin extends Enemy {

    public Goblin() {
        super("Goblin", 40, 8, 25);
    }

    public Goblin(String name) {
        super(name, 40, 8, 25);
    }

    @Override
    public int attack() {
        int dmg = super.attack();
        if (Math.random() < 0.2) {
            dmg += 5;
            System.out.println(getName() + " throws a dirty punch!");
        }
        return dmg;
    }
}
