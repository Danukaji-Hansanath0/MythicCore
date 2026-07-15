# Kingdom Quest

A turn-based console RPG built in Java demonstrating Object-Oriented Programming principles.

## How to Compile and Run

```bash
javac -d src -sourcepath src src/GameEngine.java
java -cp src GameEngine
```

## Class Diagram

```mermaid
classDiagram
    direction TB

    class CombatMan {
        <<interface>>
        +attack() int
        +takeDmg(int dmg) void
        +isAlive() boolean
        +getName() String
        +getHealth() int
        +getMaxHealth() int
    }

    class Character {
        <<abstract>>
        -String name
        -int health
        -int maxHealth
        -int level
        -int exp
        -Inventory inventory
        -QuestBook questBook
        +Character(String name)
        +Character(String name, int maxHealth)
        +attack() int
        #getBaseAttack() int*
        +gainExperience(int amount) void
        +isAlive() boolean
        +takeDmg(int amount) void
        +heal(int amount) void
        +getName() String
        +getHealth() int
        +setHealth(int health) void
        +getMaxHealth() int
        +getLevel() int
        +getExp() int
        +getInventory() Inventory
        +getQuestBook() QuestBook
        +getStats() String
    }

    class Warrior {
        +Warrior(String name)
        +Warrior(String name, int maxHealth)
        +getBaseAttack() int
        +attack() int
    }

    class Archer {
        +Archer(String name)
        +Archer(String name, int maxHealth)
        +getBaseAttack() int
        +attack() int
    }

    class Wizard {
        +Wizard(String name)
        +Wizard(String name, int maxHealth)
        +getBaseAttack() int
        +attack() int
    }

    class Enemy {
        <<abstract>>
        -String name
        -int health
        -int maxHealth
        -int baseAttack
        -int expReward
        +Enemy(String name, int maxHealth, int baseAttack, int expReward)
        +attack() int
        +takeDmg(int dmg) void
        +isAlive() boolean
        +getName() String
        +getHealth() int
        +getMaxHealth() int
        +getBaseAttack() int
        +getExpReward() int
        +getDisplayInfo() String
    }

    class Goblin {
        +Goblin()
        +Goblin(String name)
        +attack() int
    }

    class Orc {
        +Orc()
        +Orc(String name)
        +attack() int
    }

    class DragonBoss {
        -boolean enraged
        +DragonBoss()
        +DragonBoss(String name)
        +attack() int
        +isEnraged() boolean
    }

    class Item {
        <<abstract>>
        -String name
        -String description
        -int value
        +Item(String name, String description)
        +Item(String name, String description, int value)
        +use(CombatMan target) String*
        +getType() String*
        +getName() String
        +getDescription() String
        +getValue() int
    }

    class Weapon {
        -int attackPower
        +Weapon(String name, String description, int attackPower)
        +Weapon(String name, int attackPower)
        +use(CombatMan target) String
        +getType() String
        +getAttackPower() int
    }

    class Consumable {
        -int healAmount
        +Consumable(String name, String description, int healAmount)
        +Consumable(String name, int healAmount)
        +use(CombatMan target) String
        +getType() String
        +getHealAmount() int
    }

    class Inventory {
        -Item[] items
        -int count
        -int equippedWeaponIndex
        +Inventory()
        +addItem(Item item) boolean
        +removeItem(int index) Item
        +getItem(int index) Item
        +equipWeapon(int index) boolean
        +getEquippedWeapon() Weapon
        +getAllItems() Item[]
        +getCount() int
        +displayInventory() void
    }

    class Quest {
        -String name
        -String description
        -String targetEnemy
        -int requiredKills
        -int currentKills
        -Reward reward
        -boolean completed
        -boolean active
        +Quest(String name, String description, String targetEnemy, int requiredKills, Reward reward)
        +addKill(String enemyName) void
        +getName() String
        +getDescription() String
        +getTargetEnemy() String
        +getRequiredKills() int
        +getCurrentKills() int
        +getReward() Reward
        +isCompleted() boolean
        +isActive() boolean
        +setActive(boolean active) void
        +getProgress() String
    }

    class QuestBook {
        -Quest[] quests
        -int count
        +QuestBook()
        +addQuest(Quest quest) boolean
        +activateQuest(int index) void
        +getQuest(int index) Quest
        +getCount() int
        +getActiveQuests() Quest[]
        +notifyKill(String enemyName) void
        +displayQuests() void
    }

    class Reward {
        -int exp
        -int gold
        -String itemName
        +Reward(int exp, int gold)
        +Reward(int exp, int gold, String itemName)
        +getExp() int
        +getGold() int
        +getItemName() String
        +getDescription() String
    }

    class Battle {
        -Character player
        -Enemy enemy
        -Scanner scanner
        -boolean fled
        +Battle(Character player, Enemy enemy, Scanner scanner)
        +start() boolean
        -playerTurnAttack() void
        -playerTurnItem() void
        -attemptFlee() boolean
        -enemyTurn() void
    }

    class GameEngine {
        -Scanner scanner
        -Character player
        -boolean running
        -int gold
        -int questsCompleted
        +GameEngine()
        +start() void
        -printBanner() void
        -createCharacter() void
        -initializeStarterItems() void
        -initializeQuests() void
        -displayMainMenu() void
        -handleMainMenu(int choice) void
        -exploreArea() void
        -generateRandomEnemy() Enemy
        -checkQuestRewards() void
        -displayStatus() void
        -openInventory() void
        -viewQuests() void
        -openShop() void
        -restAtInn() void
        -readIntInput() int
        +main(String[] args) void
    }

    Character ..|> CombatMan
    Enemy ..|> CombatMan
    Warrior --|> Character
    Archer --|> Character
    Wizard --|> Character
    Goblin --|> Enemy
    Orc --|> Enemy
    DragonBoss --|> Enemy
    Weapon --|> Item
    Consumable --|> Item
    Character *-- Inventory
    Character *-- QuestBook
    Inventory o-- Item
    QuestBook o-- Quest
    Quest *-- Reward
    Battle o-- Character
    Battle o-- Enemy
    GameEngine o-- Character
    GameEngine o-- Battle
```

## Project Structure

```
src/
├── GameEngine.java          (main entry point)
├── core/
│   ├── CombatMan.java       (interface)
│   └── Battle.java          (battle simulation)
├── model/
│   ├── Character.java       (abstract base)
│   ├── Warrior.java
│   ├── Archer.java
│   └── Wizard.java
├── item/
│   ├── Item.java            (abstract base)
│   ├── Weapon.java
│   ├── Consumable.java
│   └── Inventory.java
├── enemy/
│   ├── Enemy.java           (abstract base)
│   ├── Goblin.java
│   ├── Orc.java
│   └── DragonBoss.java
└── progression/
    ├── Quest.java
    ├── QuestBook.java
    └── Reward.java
```

## OOP Concepts Demonstrated

| Concept | Where |
|---------|-------|
| Interface | `CombatMan` - contract for all combat entities |
| Abstract Classes | `Character`, `Enemy`, `Item` |
| Inheritance | Warrior/Archer/Wizard extends Character; Goblin/Orc/DragonBoss extends Enemy; Weapon/Consumable extends Item |
| Encapsulation | All fields private, accessed via getters/setters |
| Polymorphism | `attack()` overridden in every subclass with unique behavior |
| Overloading | Multiple constructors via `this()` chaining in Character, Item, Weapon, Consumable, Enemy, Goblin, Orc, DragonBoss |
| HAS-A (Composition) | Character owns Inventory + QuestBook; Inventory holds Item[]; QuestBook holds Quest[]; Quest has Reward |
| Arrays | `Item[]` in Inventory, `Quest[]` in QuestBook, `Weapon[]`/`Consumable[]` in shop |
| Exception Handling | try-catch for NumberFormatException on all Scanner input |
| Flow Control | while loops for menus/battle, for loops for arrays, if-else + switch-case |
