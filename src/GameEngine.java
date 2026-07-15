import model.Character;
import model.Warrior;
import model.Archer;
import model.Wizard;
import item.Weapon;
import item.Consumable;
import item.Inventory;
import enemy.Goblin;
import enemy.Orc;
import enemy.DragonBoss;
import enemy.Enemy;
import progression.Quest;
import progression.Reward;
import progression.QuestBook;
import core.Battle;

import java.util.Scanner;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author danuk
 */
public class GameEngine {
    private Scanner scanner;
    private Character player;
    private boolean running;
    private int gold;
    private int questsCompleted;

    public GameEngine() {
        this.scanner = new Scanner(System.in);
        this.running = true;
        this.gold = 0;
        this.questsCompleted = 0;
    }

    public void start() {
        printBanner();
        createCharacter();
        initializeStarterItems();
        initializeQuests();

        while (running) {
            displayMainMenu();
            int choice = readIntInput();
            handleMainMenu(choice);
        }

        System.out.println("\nThanks for playing Kingdom Quest!");
        System.out.println("Final Stats: " + player.getStats());
        System.out.println("Gold: " + gold + " | Quests Completed: " + questsCompleted);
        scanner.close();
    }

    private void printBanner() {
        System.out.println("╔══════════════════════════════════════════╗");
        System.out.println("║         KINGDOM QUEST                    ║");
        System.out.println("║   A Turn-Based RPG Adventure             ║");
        System.out.println("╚══════════════════════════════════════════╝");
        System.out.println();
    }

    private void createCharacter() {
        System.out.println("=== CHARACTER CREATION ===");
        System.out.print("Enter your hero's name: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            name = "Hero";
        }

        System.out.println("\nChoose your class:");
        System.out.println("1. Warrior - High HP, strong melee attacks");
        System.out.println("2. Archer  - Balanced, rapid double shots");
        System.out.println("3. Wizard  - Low HP, devastating magic");
        System.out.print("Choice: ");

        int classChoice = readIntInput();

        switch (classChoice) {
            case 1:
                player = new Warrior(name);
                System.out.println(name + " the Warrior joins the quest!");
                break;
            case 2:
                player = new Archer(name);
                System.out.println(name + " the Archer joins the quest!");
                break;
            case 3:
                player = new Wizard(name);
                System.out.println(name + " the Wizard joins the quest!");
                break;
            default:
                player = new Warrior(name);
                System.out.println("Invalid choice. " + name + " defaults to Warrior.");
                break;
        }
    }

    private void initializeStarterItems() {
        Weapon starterWeapon = new Weapon("Rusty Sword", "A worn but functional blade", 5);
        Consumable potion = new Consumable("Health Potion", "A basic healing draught", 30);
        Consumable potion2 = new Consumable("Health Potion", "A basic healing draught", 30);

        player.getInventory().addItem(starterWeapon);
        player.getInventory().addItem(potion);
        player.getInventory().addItem(potion2);
        player.getInventory().equipWeapon(0);
    }

    private void initializeQuests() {
        QuestBook qb = player.getQuestBook();

        Quest q1 = new Quest("Goblin Slayer", "Defeat 3 goblins in the wild", "Goblin", 3, new Reward(75, 20, "Iron Shield"));
        Quest q2 = new Quest("Orc Cleanup", "Slay 2 orcs threatening the village", "Orc", 2, new Reward(100, 40, "Steel Axe"));
        Quest q3 = new Quest("Dragon Hunter", "Defeat the Ancient Dragon", "Ancient Dragon", 1, new Reward(200, 100, "Dragon Scale Armor"));

        qb.addQuest(q1);
        qb.addQuest(q2);
        qb.addQuest(q3);
        qb.activateQuest(0);
        qb.activateQuest(1);
        qb.activateQuest(2);
    }

    private void displayMainMenu() {
        System.out.println("\n=== MAIN MENU ===");
        System.out.println("1. Explore (Find Enemies)");
        System.out.println("2. View Status");
        System.out.println("3. Inventory");
        System.out.println("4. Quest Log");
        System.out.println("5. Shop");
        System.out.println("6. Rest (Restore HP)");
        System.out.println("7. Quit");
        System.out.print("Choice: ");
    }

    private void handleMainMenu(int choice) {
        switch (choice) {
            case 1:
                exploreArea();
                break;
            case 2:
                displayStatus();
                break;
            case 3:
                openInventory();
                break;
            case 4:
                viewQuests();
                break;
            case 5:
                openShop();
                break;
            case 6:
                restAtInn();
                break;
            case 7:
                running = false;
                break;
            default:
                System.out.println("Invalid option. Try again.");
                break;
        }
    }

    private void exploreArea() {
        System.out.println("\nYou venture into the wilderness...");
        Enemy encountered = generateRandomEnemy();
        System.out.println("A " + encountered.getName() + " appears!");

        Battle battle = new Battle(player, encountered, scanner);
        boolean victory = battle.start();

        if (victory) {
            gold += 5 + (int)(Math.random() * 10);
            System.out.println("You looted some gold. (Total: " + gold + ")");
            player.getQuestBook().notifyKill(encountered.getName());
            checkQuestRewards();
        } else if (!player.isAlive()) {
            player.setHealth(player.getMaxHealth() / 2);
            System.out.println("You wake up at the inn, barely alive...");
        }
    }

    private Enemy generateRandomEnemy() {
        int roll = (int)(Math.random() * 100);
        if (roll < 50) {
            return new Goblin();
        } else if (roll < 85) {
            return new Orc();
        } else {
            return new DragonBoss();
        }
    }

    private void checkQuestRewards() {
        QuestBook qb = player.getQuestBook();
        for (int i = 0; i < qb.getCount(); i++) {
            Quest q = qb.getQuest(i);
            if (q.isCompleted() && q.getReward() != null) {
                Reward r = q.getReward();
                player.gainExperience(r.getExp());
                gold += r.getGold();
                System.out.println("Quest reward: +" + r.getExp() + " EXP, +" + r.getGold() + " Gold");
                if (r.getItemName() != null) {
                    Weapon rewardWeapon = new Weapon(r.getItemName(), "Quest reward weapon", 10 + questsCompleted * 3);
                    player.getInventory().addItem(rewardWeapon);
                }
                questsCompleted++;
            }
        }
    }

    private void displayStatus() {
        System.out.println("\n=== CHARACTER STATUS ===");
        System.out.println(player.getStats());
        System.out.println("Class: " + player.getClass().getSimpleName());
        System.out.println("Gold: " + gold);
        Weapon equipped = player.getInventory().getEquippedWeapon();
        System.out.println("Weapon: " + (equipped != null ? equipped.getName() + " (ATK +" + equipped.getAttackPower() + ")" : "None"));
    }

    private void openInventory() {
        player.getInventory().displayInventory();
        System.out.println("\n1. Equip Weapon");
        System.out.println("2. Use Consumable");
        System.out.println("3. Back");
        System.out.print("Choice: ");
        int choice = readIntInput();

        if (choice == 1) {
            System.out.print("Enter item number to equip: ");
            int idx = readIntInput() - 1;
            player.getInventory().equipWeapon(idx);
        } else if (choice == 2) {
            System.out.print("Enter item number to use: ");
            int idx = readIntInput() - 1;
            item.Item item = player.getInventory().getItem(idx);
            if (item instanceof Consumable) {
                System.out.println(item.use(player));
                player.getInventory().removeItem(idx);
            } else {
                System.out.println("That item cannot be used like that.");
            }
        }
    }

    private void viewQuests() {
        player.getQuestBook().displayQuests();
    }

    private void openShop() {
        System.out.println("\n=== SHOP ===");
        System.out.println("Gold: " + gold);
        Weapon[] shopWeapons = {
            new Weapon("Iron Sword", "A solid iron blade", 8),
            new Weapon("Steel Blade", "Forged steel, sharp edge", 12),
            new Weapon("Enchanted Staff", "Hums with magical energy", 15),
            new Weapon("War Hammer", "Crushing force", 18)
        };
        Consumable[] shopPotions = {
            new Consumable("Health Potion", "Restores 30 HP", 30),
            new Consumable("Greater Health Potion", "Restores 60 HP", 60),
            new Consumable("Supreme Elixir", "Restores 100 HP", 100)
        };

        System.out.println("\nWeapons:");
        for (int i = 0; i < shopWeapons.length; i++) {
            System.out.println("  " + (i + 1) + ". " + shopWeapons[i].getName() + " (ATK +" + shopWeapons[i].getAttackPower() + ") - " + shopWeapons[i].getValue() + " Gold");
        }
        System.out.println("\nPotions:");
        for (int i = 0; i < shopPotions.length; i++) {
            System.out.println("  " + (shopWeapons.length + i + 1) + ". " + shopPotions[i].getName() + " (Heal " + shopPotions[i].getHealAmount() + ") - " + shopPotions[i].getValue() + " Gold");
        }
        System.out.println("  0. Back");

        System.out.print("Buy which item? ");
        int choice = readIntInput();

        if (choice >= 1 && choice <= shopWeapons.length) {
            Weapon w = shopWeapons[choice - 1];
            if (gold >= w.getValue()) {
                gold -= w.getValue();
                player.getInventory().addItem(w);
                System.out.println("Purchased " + w.getName() + "!");
            } else {
                System.out.println("Not enough gold!");
            }
        } else if (choice > shopWeapons.length && choice <= shopWeapons.length + shopPotions.length) {
            Consumable c = shopPotions[choice - shopWeapons.length - 1];
            if (gold >= c.getValue()) {
                gold -= c.getValue();
                player.getInventory().addItem(c);
                System.out.println("Purchased " + c.getName() + "!");
            } else {
                System.out.println("Not enough gold!");
            }
        }
    }

    private void restAtInn() {
        int cost = 10;
        if (gold >= cost) {
            gold -= cost;
            player.setHealth(player.getMaxHealth());
            System.out.println("Rested at the inn. HP fully restored! (-" + cost + " Gold)");
        } else {
            System.out.println("Not enough gold to rest at the inn! (Need " + cost + " Gold)");
        }
    }

    private int readIntInput() {
        try {
            String line = scanner.nextLine().trim();
            return Integer.parseInt(line);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
            return -1;
        }
    }

    public static void main(String[] args) {
        GameEngine game = new GameEngine();
        game.start();
    }
}
