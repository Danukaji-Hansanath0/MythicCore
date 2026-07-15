package progression;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author danuk
 */
public class QuestBook {
    private static final int MAX_QUESTS = 5;
    private Quest[] quests;
    private int count;

    public QuestBook() {
        this.quests = new Quest[MAX_QUESTS];
        this.count = 0;
    }

    public boolean addQuest(Quest quest) {
        if (count >= MAX_QUESTS) {
            System.out.println("Quest book is full!");
            return false;
        }
        quests[count] = quest;
        count++;
        System.out.println("New quest accepted: " + quest.getName());
        return true;
    }

    public void activateQuest(int index) {
        if (index < 0 || index >= count) return;
        quests[index].setActive(true);
        System.out.println("Quest activated: " + quests[index].getName());
    }

    public Quest getQuest(int index) {
        if (index < 0 || index >= count) return null;
        return quests[index];
    }

    public int getCount() {
        return count;
    }

    public Quest[] getActiveQuests() {
        int activeCount = 0;
        for (int i = 0; i < count; i++) {
            if (quests[i].isActive() && !quests[i].isCompleted()) {
                activeCount++;
            }
        }
        Quest[] active = new Quest[activeCount];
        int idx = 0;
        for (int i = 0; i < count; i++) {
            if (quests[i].isActive() && !quests[i].isCompleted()) {
                active[idx] = quests[i];
                idx++;
            }
        }
        return active;
    }

    public void notifyKill(String enemyName) {
        for (int i = 0; i < count; i++) {
            if (quests[i].isActive() && !quests[i].isCompleted()) {
                quests[i].addKill(enemyName);
            }
        }
    }

    public void displayQuests() {
        System.out.println("=== QUEST BOOK (" + count + "/" + MAX_QUESTS + ") ===");
        if (count == 0) {
            System.out.println("  No quests yet.");
            return;
        }
        for (int i = 0; i < count; i++) {
            System.out.println("  " + (i + 1) + ". " + quests[i].getProgress());
        }
    }

    @Override
    public String toString() {
        String result = "";
        for (int i = 0; i < count; i++) {
            result += "  " + (i + 1) + ". " + quests[i].getProgress() + "\n";
        }
        return result;
    }
}
