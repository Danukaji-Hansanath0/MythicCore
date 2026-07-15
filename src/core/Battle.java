package core;

import model.Character;
import enemy.Enemy;
import item.Consumable;
import item.Weapon;
import java.util.Scanner;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author danuk
 */
public class Battle {
    private Character player;
    private Enemy enemy;
    private Scanner scanner;
    private boolean fled;

    public Battle(Character player, Enemy enemy, Scanner scanner) {
        this.player = player;
        this.enemy = enemy;
        this.scanner = scanner;
        this.fled = false;
    }

    public boolean start() {
        System.out.println("\n========================================");
        System.out.println("  BATTLE: " + player.getName() + " vs " + enemy.getName());
        System.out.println("========================================");
        System.out.println(enemy.getDisplayInfo());
        System.out.println(player.getStats());

        while (player.isAlive() && enemy.isAlive()) {
            System.out.println("\n--- YOUR TURN ---");
            System.out.println("1. Attack");
            System.out.println("2. Use Item");
            System.out.println("3. View Status");
            System.out.println("4. Flee");
            System.out.print("Choice: ");

            int choice = readInt();

            switch (choice) {
                case 1:
                    playerTurnAttack();
                    break;
                case 2:
                    playerTurnItem();
                    break;
                case 3:
                    System.out.println(player.getStats());
                    System.out.println(enemy.getDisplayInfo());
                    continue;
                case 4:
                    if (attemptFlee()) {
                        return false;
                    }
                    break;
                default:
                    System.out.println("Invalid choice. You hesitate...");
                    break;
            }

            if (enemy.isAlive()) {
                enemyTurn();
            }
        }

        if (fled) {
            System.out.println("You fled from battle!");
            return false;
        }

        if (player.isAlive()) {
            System.out.println("\n*** VICTORY! " + enemy.getName() + " has been defeated! ***");
            player.gainExperience(enemy.getExpReward());
            return true;
        } else {
            System.out.println("\n*** DEFEAT! " + player.getName() + " has fallen... ***");
            return false;
        }
    }

    private void playerTurnAttack() {
        int damage = player.attack();
        System.out.println(player.getName() + " attacks " + enemy.getName() + " for " + damage + " damage!");
        enemy.takeDmg(damage);
    }

    private void playerTurnItem() {
        item.Inventory inv = player.getInventory();
        if (inv.getCount() == 0) {
            System.out.println("No items in inventory!");
            return;
        }

        inv.displayInventory();
        System.out.print("Select item number (0 to cancel): ");
        int idx = readInt() - 1;

        if (idx < 0 || idx >= inv.getCount()) {
            System.out.println("Cancelled.");
            return;
        }

        item.Item selected = inv.getItem(idx);
        if (selected instanceof Consumable) {
            System.out.println(selected.use(player));
            inv.removeItem(idx);
        } else if (selected instanceof Weapon) {
            inv.equipWeapon(idx);
        } else {
            System.out.println(selected.use(player));
        }
    }

    private boolean attemptFlee() {
        double chance = Math.random();
        if (chance < 0.5) {
            System.out.println("Successfully fled from battle!");
            fled = true;
            return true;
        } else {
            System.out.println("Failed to flee!");
            return false;
        }
    }

    private void enemyTurn() {
        System.out.println("\n--- " + enemy.getName() + "'s TURN ---");
        int damage = enemy.attack();
        System.out.println(enemy.getName() + " attacks " + player.getName() + " for " + damage + " damage!");
        player.takeDmg(damage);
    }

    private int readInt() {
        try {
            String line = scanner.nextLine().trim();
            return Integer.parseInt(line);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
