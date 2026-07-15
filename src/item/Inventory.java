package item;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author danuk
 */
public class Inventory {
    private static final int MAX_SIZE = 10;
    private Item[] items;
    private int count;
    private int equippedWeaponIndex;

    public Inventory() {
        this.items = new Item[MAX_SIZE];
        this.count = 0;
        this.equippedWeaponIndex = -1;
    }

    public boolean addItem(Item item) {
        if (count >= MAX_SIZE) {
            System.out.println("Inventory is full! Cannot pick up " + item.getName());
            return false;
        }
        items[count] = item;
        count++;
        System.out.println("Acquired: " + item.getName());
        return true;
    }

    public Item removeItem(int index) {
        if (index < 0 || index >= count) {
            System.out.println("Invalid item index.");
            return null;
        }
        Item removed = items[index];
        for (int i = index; i < count - 1; i++) {
            items[i] = items[i + 1];
        }
        items[count - 1] = null;
        count--;
        if (equippedWeaponIndex == index) {
            equippedWeaponIndex = -1;
        } else if (equippedWeaponIndex > index) {
            equippedWeaponIndex--;
        }
        return removed;
    }

    public Item getItem(int index) {
        if (index < 0 || index >= count) return null;
        return items[index];
    }

    public boolean equipWeapon(int index) {
        if (index < 0 || index >= count) {
            System.out.println("Invalid item index.");
            return false;
        }
        if (!(items[index] instanceof Weapon)) {
            System.out.println("That is not a weapon!");
            return false;
        }
        equippedWeaponIndex = index;
        Weapon w = (Weapon) items[index];
        System.out.println("Equipped: " + w.getName() + " (ATK +" + w.getAttackPower() + ")");
        return true;
    }

    public Weapon getEquippedWeapon() {
        if (equippedWeaponIndex < 0 || equippedWeaponIndex >= count) return null;
        if (items[equippedWeaponIndex] instanceof Weapon) {
            return (Weapon) items[equippedWeaponIndex];
        }
        return null;
    }

    public Item[] getAllItems() {
        Item[] result = new Item[count];
        for (int i = 0; i < count; i++) {
            result[i] = items[i];
        }
        return result;
    }

    public int getCount() {
        return count;
    }

    public int getMaxSize() {
        return MAX_SIZE;
    }

    public void displayInventory() {
        System.out.println("=== INVENTORY (" + count + "/" + MAX_SIZE + ") ===");
        if (count == 0) {
            System.out.println("  Empty.");
            return;
        }
        for (int i = 0; i < count; i++) {
            String marker = (i == equippedWeaponIndex) ? " [EQUIPPED]" : "";
            System.out.println("  " + (i + 1) + ". " + items[i].toString() + marker);
        }
    }
}
