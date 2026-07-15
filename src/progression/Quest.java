package progression;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author danuk
 */
public class Quest {
    private String name;
    private String description;
    private String targetEnemy;
    private int requiredKills;
    private int currentKills;
    private Reward reward;
    private boolean completed;
    private boolean active;

    public Quest(String name, String description, String targetEnemy, int requiredKills, Reward reward) {
        this.name = name;
        this.description = description;
        this.targetEnemy = targetEnemy;
        this.requiredKills = requiredKills;
        this.currentKills = 0;
        this.reward = reward;
        this.completed = false;
        this.active = false;
    }

    public void addKill(String enemyName) {
        if (completed || !active) return;
        if (enemyName.equalsIgnoreCase(targetEnemy)) {
            currentKills++;
            System.out.println("Quest progress: " + name + " (" + currentKills + "/" + requiredKills + ")");
            if (currentKills >= requiredKills) {
                completed = true;
                System.out.println("Quest COMPLETED: " + name + "!");
            }
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public String getTargetEnemy() {
        return targetEnemy;
    }

    public int getRequiredKills() {
        return requiredKills;
    }

    public int getCurrentKills() {
        return currentKills;
    }

    public Reward getReward() {
        return reward;
    }

    public boolean isCompleted() {
        return completed;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getProgress() {
        return name + " - " + description + " [" + currentKills + "/" + requiredKills + " " + targetEnemy + " killed]" +
                (completed ? " [DONE]" : (active ? " [ACTIVE]" : " [INACTIVE]"));
    }
}
