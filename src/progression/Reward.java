package progression;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author danuk
 */
public class Reward {
    private int exp;
    private int gold;
    private String itemName;

    public Reward(int exp, int gold) {
        this(exp, gold, null);
    }

    public Reward(int exp, int gold, String itemName) {
        this.exp = exp;
        this.gold = gold;
        this.itemName = itemName;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getDescription() {
        String desc = "EXP: " + exp + ", Gold: " + gold;
        if (itemName != null) {
            desc += ", Item: " + itemName;
        }
        return desc;
    }
}
