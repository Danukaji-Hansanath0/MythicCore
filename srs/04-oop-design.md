# 4. OOP Design Principles

This section details every Object-Oriented Programming principle demonstrated in Kingdom Quest, with code examples from the actual source.

---

## 4.1 Interface — CombatMan

### Definition

An **interface** defines a contract that implementing classes must fulfill. It specifies method signatures without implementation.

### Implementation

```java
package core;

public interface CombatMan {
    int attack();           // Calculate and return damage
    void takeDmg(int dmg);  // Reduce health by damage
    boolean isAlive();      // Check if health > 0
    String getName();       // Return entity name
    int getHealth();        // Return current health
    int getMaxHealth();     // Return maximum health
}
```

### Who Implements It

| Class | Relationship |
|-------|-------------|
| `Character` (abstract) | `implements CombatMan` |
| `Enemy` (abstract) | `implements CombatMan` |
| `Warrior` | Inherits via Character |
| `Archer` | Inherits via Character |
| `Wizard` | Inherits via Character |
| `Goblin` | Inherits via Enemy |
| `Orc` | Inherits via Enemy |
| `DragonBoss` | Inherits via Enemy |

### Why It Matters

The `Battle` class uses `CombatMan` as the type for both player and enemy, enabling polymorphic method calls. The battle logic doesn't need to know if it's dealing with a Character or Enemy — both fulfill the same contract.

```java
// Battle.java uses CombatMan interface implicitly
// through Character and Enemy types
int playerDmg = player.attack();   // Character's attack()
int enemyDmg = enemy.attack();     // Enemy's attack()
```

---

## 4.2 Abstract Classes

### Definition

An **abstract class** cannot be instantiated directly. It serves as a base template that provides common functionality to subclasses while declaring abstract methods that subclasses must implement.

### Abstract Classes in Kingdom Quest

#### 4.2.1 Character (Abstract)

```java
package model;

public abstract class Character implements core.CombatMan {
    private String name;
    private int health;
    private int maxHealth;
    private int level;
    private int exp;
    private Inventory inventory;
    private QuestBook questBook;

    // Constructor overloading
    public Character(String name) {
        this(name, 100);  // Chains to 2-arg constructor
    }

    public Character(String name, int maxHealth) {
        this.name = name;
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.level = 1;
        this.exp = 0;
        this.inventory = new Inventory();
        this.questBook = new QuestBook();
    }

    // Concrete methods - shared by all subclasses
    public int attack() {
        int baseDamage = getBaseAttack();
        Weapon equipped = inventory.getEquippedWeapon();
        if (equipped != null) {
            baseDamage += equipped.getAttackPower();
        }
        return baseDamage;
    }

    // Abstract method - each subclass MUST implement
    protected abstract int getBaseAttack();

    public void gainExperience(int amount) {
        if (amount <= 0) return;
        this.exp += amount;
        while (this.exp >= (this.level * 100)) {
            this.exp -= (this.level * 100);
            this.level++;
            this.maxHealth += 25;
            this.health = this.maxHealth;
        }
    }
    // ... getters and setters
}
```

**Key Points:**
- Cannot create `new Character()` — must use a subclass
- Provides shared implementation for `attack()`, `gainExperience()`, `takeDmg()`, `heal()`
- Declares `abstract int getBaseAttack()` — subclasses define their own formula

#### 4.2.2 Enemy (Abstract)

```java
package enemy;

public abstract class Enemy implements core.CombatMan {
    private String name;
    private int health;
    private int maxHealth;
    private int baseAttack;
    private int expReward;

    public Enemy(String name, int maxHealth, int baseAttack, int expReward) {
        this.name = name;
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.baseAttack = baseAttack;
        this.expReward = expReward;
    }

    public int attack() {
        int variance = (int) (Math.random() * 5) - 2;
        return baseAttack + variance;
    }

    // ... other methods
}
```

**Key Points:**
- Cannot create `new Enemy()` — must use Goblin, Orc, or DragonBoss
- Provides default `attack()` with variance — subclasses override for special abilities
- Encapsulates common enemy stats

#### 4.2.3 Item (Abstract)

```java
package item;

public abstract class Item {
    private String name;
    private String description;
    private int value;

    // Constructor overloading
    public Item(String name, String description) {
        this(name, description, 0);
    }

    public Item(String name, String description, int value) {
        this.name = name;
        this.description = description;
        this.value = value;
    }

    // Abstract methods
    public abstract String use(core.CombatMan target);
    public abstract String getType();

    // ... getters and setters
}
```

**Key Points:**
- Cannot create `new Item()` — must use Weapon or Consumable
- Declares abstract `use()` and `getType()` — each item type defines its own behavior
- Constructor chaining via `this()` for optional value parameter

---

## 4.3 Inheritance

### Definition

**Inheritance** allows a class (subclass) to inherit fields and methods from another class (superclass), establishing an IS-A relationship.

### Inheritance Hierarchies

#### Hierarchy 1: Character Tree

```
                    ┌─────────────────┐
                    │  CombatMan      │
                    │  (Interface)    │
                    └────────┬────────┘
                             │ implements
                    ┌────────┴────────┐
                    │   Character     │
                    │   (Abstract)    │
                    └────────┬────────┘
                             │ extends
              ┌──────────────┼──────────────┐
              │              │              │
     ┌────────┴───────┐ ┌───┴────────┐ ┌───┴────────┐
     │    Warrior     │ │   Archer   │ │   Wizard   │
     └────────────────┘ └────────────┘ └────────────┘
```

| Subclass | Superclass | Relationship | Own HP | Attack Formula |
|----------|-----------|--------------|--------|----------------|
| Warrior | Character | IS-A Character | 150 | 15 + level × 3 |
| Archer | Character | IS-A Character | 100 | 12 + level × 2 |
| Wizard | Character | IS-A Character | 80 | 20 + level × 4 |

#### Hierarchy 2: Enemy Tree

```
                    ┌─────────────────┐
                    │  CombatMan      │
                    │  (Interface)    │
                    └────────┬────────┘
                             │ implements
                    ┌────────┴────────┐
                    │     Enemy       │
                    │   (Abstract)    │
                    └────────┬────────┘
                             │ extends
              ┌──────────────┼──────────────┐
              │              │              │
     ┌────────┴───────┐ ┌───┴────────┐ ┌───┴────────────┐
     │     Goblin     │ │    Orc     │ │  DragonBoss    │
     └────────────────┘ └────────────┘ └────────────────┘
```

| Subclass | Superclass | Relationship | HP | ATK | EXP |
|----------|-----------|--------------|-----|-----|-----|
| Goblin | Enemy | IS-A Enemy | 40 | 8 | 25 |
| Orc | Enemy | IS-A Enemy | 80 | 14 | 50 |
| DragonBoss | Enemy | IS-A Enemy | 300 | 30 | 200 |

#### Hierarchy 3: Item Tree

```
                    ┌─────────────────┐
                    │      Item       │
                    │   (Abstract)    │
                    └────────┬────────┘
                             │ extends
                    ┌────────┴────────┐
          ┌─────────┴──────┐   ┌──────┴─────────┐
          │    Weapon      │   │   Consumable   │
          └────────────────┘   └────────────────┘
```

| Subclass | Superclass | Relationship | Purpose |
|----------|-----------|--------------|---------|
| Weapon | Item | IS-A Item | Equip for ATK bonus |
| Consumable | Item | IS-A Item | Use for healing |

### Code Examples

```java
// Warrior inherits from Character
public class Warrior extends Character {
    public Warrior(String name) {
        super(name, 150);  // Calls Character's constructor
    }
    // Inherits: name, health, level, exp, inventory, questBook
    // Inherits: attack(), gainExperience(), takeDmg(), heal()
    // Overrides: getBaseAttack(), attack()
}

// Goblin inherits from Enemy
public class Goblin extends Enemy {
    public Goblin() {
        super("Goblin", 40, 8, 25);  // Calls Enemy's constructor
    }
    // Inherits: name, health, baseAttack, expReward
    // Inherits: takeDmg(), isAlive(), getName()
    // Overrides: attack()
}
```

---

## 4.4 Polymorphism

### Definition

**Polymorphism** means "many forms." It allows objects of different classes to be treated as objects of a common superclass, with each class providing its own implementation of shared methods.

### Method Overriding in Kingdom Quest

#### Player Character `attack()` Overrides

| Class | Override | Special Mechanic |
|-------|----------|------------------|
| Warrior | `public int attack()` | 20% chance → `damage = (int)(damage * 1.5)` |
| Archer | `public int attack()` | 30% chance → `damage += (int)(damage * 0.5)` |
| Wizard | `public int attack()` | 25% chance → `damage = (int)(damage * 2.0)` |

```java
// Warrior.java
@Override
public int attack() {
    int damage = super.attack();  // Call parent's attack() first
    if (Math.random() < 0.2) {
        damage = (int) (damage * 1.5);
        System.out.println(getName() + " lands a devastating CRITICAL STRIKE!");
    }
    return damage;
}

// Archer.java
@Override
public int attack() {
    int damage = super.attack();
    if (Math.random() < 0.3) {
        int bonus = (int) (damage * 0.5);
        damage += bonus;
        System.out.println(getName() + " fires a rapid double shot!");
    }
    return damage;
}

// Wizard.java
@Override
public int attack() {
    int damage = super.attack();
    if (Math.random() < 0.25) {
        damage = (int) (damage * 2.0);
        System.out.println(getName() + " unleashes a devastating ARCANE BLAST!");
    }
    return damage;
}
```

#### Enemy `attack()` Overrides

| Class | Override | Special Mechanic |
|-------|----------|------------------|
| Goblin | `public int attack()` | 20% chance → `dmg += 5` |
| Orc | `public int attack()` | 15% chance → `dmg = (int)(dmg * 1.8)` |
| DragonBoss | `public int attack()` | Enrage check + 30% fire breath → `dmg += 20` |

```java
// Goblin.java
@Override
public int attack() {
    int dmg = super.attack();
    if (Math.random() < 0.2) {
        dmg += 5;
        System.out.println(getName() + " throws a dirty punch!");
    }
    return dmg;
}

// DragonBoss.java
@Override
public int attack() {
    if (!enraged && getHealth() <= getMaxHealth() / 2) {
        enraged = true;
        setBaseAttack(getBaseAttack() + 15);
        System.out.println(getName() + " enters ENRAGE mode!");
    }
    int dmg = super.attack();
    if (enraged && Math.random() < 0.3) {
        dmg += 20;
        System.out.println(getName() + " breathes devastating FIRE!");
    }
    return dmg;
}
```

#### Item `use()` Overrides

| Class | Override | Behavior |
|-------|----------|----------|
| Weapon | `public String use(CombatMan target)` | Returns equip message |
| Consumable | `public String use(CombatMan target)` | Heals target, returns heal message |

```java
// Weapon.java
@Override
public String use(core.CombatMan target) {
    return getName() + " is equipped. ATK +" + attackPower;
}

// Consumable.java
@Override
public String use(core.CombatMan target) {
    int before = target.getHealth();
    if (target instanceof model.Character) {
        ((model.Character) target).heal(healAmount);
    }
    return getName() + " used! HP " + before + " -> " + target.getHealth();
}
```

### Polymorphism in Action (Battle)

```java
// Battle.java — same method call, different behavior
int playerDmg = player.attack();  // Calls Warrior/Archer/Wizard version
int enemyDmg = enemy.attack();    // Calls Goblin/Orc/Dragon version

// The Battle class doesn't know which specific class it's calling
// This is runtime polymorphism (dynamic dispatch)
```

---

## 4.5 Method Overloading

### Definition

**Method overloading** allows a class to have multiple methods with the same name but different parameter lists. In Kingdom Quest, this is primarily used for **constructor overloading** with `this()` chaining.

### Constructor Overloading Instances

| Class | Constructor 1 | Constructor 2 | Chaining |
|-------|--------------|---------------|----------|
| Character | `Character(String name)` | `Character(String name, int maxHealth)` | `this(name, 100)` |
| Warrior | `Warrior(String name)` | `Warrior(String name, int maxHealth)` | `super(name, 150)` |
| Archer | `Archer(String name)` | `Archer(String name, int maxHealth)` | `super(name, 100)` |
| Wizard | `Wizard(String name)` | `Wizard(String name, int maxHealth)` | `super(name, 80)` |
| Item | `Item(String name, String desc)` | `Item(String name, String desc, int value)` | `this(name, desc, 0)` |
| Weapon | `Weapon(String name, String desc, int atk)` | `Weapon(String name, int atk)` | `this(name, "A trusty weapon", atk)` |
| Consumable | `Consumable(String name, String desc, int heal)` | `Consumable(String name, int heal)` | `this(name, "Restores health", heal)` |
| Goblin | `Goblin()` | `Goblin(String name)` | `super("Goblin", 40, 8, 25)` |
| Orc | `Orc()` | `Orc(String name)` | `super("Orc", 80, 14, 50)` |
| DragonBoss | `DragonBoss()` | `DragonBoss(String name)` | `super("Ancient Dragon", 300, 30, 200)` |
| Reward | `Reward(int exp, int gold)` | `Reward(int exp, int gold, String itemName)` | `this(exp, gold, null)` |

### Code Example: Character Constructor Chaining

```java
public class Character implements core.CombatMan {
    // Constructor 1: Just name (uses default HP of 100)
    public Character(String name) {
        this(name, 100);  // Chains to Constructor 2
    }

    // Constructor 2: Name and custom HP
    public Character(String name, int maxHealth) {
        this.name = name;
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.level = 1;
        this.exp = 0;
        this.inventory = new Inventory();
        this.questBook = new QuestBook();
    }
}

// Usage:
Character c1 = new Warrior("Hero");           // Uses 1-arg constructor
Character c2 = new Warrior("Boss", 500);      // Uses 2-arg constructor
```

---

## 4.6 Encapsulation

### Definition

**Encapsulation** is the bundling of data (fields) and methods that operate on that data into a single unit (class), while restricting direct access to some components. Fields are `private`/`protected` and accessed through `public` getters and setters.

### Encapsulation in Every Class

| Class | Private Fields | Public Accessors |
|-------|----------------|------------------|
| Character | name, health, maxHealth, level, exp, inventory, questBook | getName(), setName(), getHealth(), setHealth(), getLevel(), setLevel(), getExp(), setExp(), getMaxHealth(), setMaxHealth(), getInventory(), getQuestBook(), getStats() |
| Enemy | name, health, maxHealth, baseAttack, expReward | getName(), setName(), getHealth(), setHealth(), getMaxHealth(), getBaseAttack(), setBaseAttack(), getExpReward(), setExpReward(), getDisplayInfo() |
| Item | name, description, value | getName(), setName(), getDescription(), setDescription(), getValue(), setValue(), toString() |
| Weapon | attackPower | getAttackPower(), setAttackPower() |
| Consumable | healAmount | getHealAmount(), setHealAmount() |
| Inventory | items, count, equippedWeaponIndex | getCount(), getMaxSize(), getAllItems() |
| Quest | name, description, targetEnemy, requiredKills, currentKills, reward, completed, active | getName(), setName(), getDescription(), getTargetEnemy(), getRequiredKills(), getCurrentKills(), getReward(), isCompleted(), isActive(), setActive(), getProgress() |
| QuestBook | quests, count | getCount(), getQuest() |
| Reward | exp, gold, itemName | getExp(), setExp(), getGold(), setGold(), getItemName(), setItemName(), getDescription() |
| Battle | player, enemy, scanner, fled | (no public accessors — internal state only) |
| GameEngine | scanner, player, running, gold, questsCompleted | (private fields — methods manage state internally) |
| DragonBoss | enraged | isEnraged() |

### Code Example: Encapsulation

```java
// Private field — cannot be accessed directly
public class Character {
    private int health;  // PRIVATE — no direct access

    // Public getter — controlled read access
    public int getHealth() {
        return health;
    }

    // Public setter — controlled write access with validation
    public void setHealth(int health) {
        this.health = Math.max(0, Math.min(this.maxHealth, health));
        // Ensures health is always between 0 and maxHealth
    }
}

// Usage:
Character c = new Warrior("Hero");
// c.health = 100;         // COMPILE ERROR — private field
c.setHealth(100);           // OK — uses setter with validation
int hp = c.getHealth();     // OK — uses getter
```

---

## 4.7 Composition (HAS-A Relationships)

### Definition

**Composition** is a relationship where a class contains an instance of another class as a field, establishing a HAS-A relationship. The contained object's lifecycle is tied to the container.

### HAS-A Relationships in Kingdom Quest

```
Character ──HAS-A──► Inventory     (Character owns Inventory)
Character ──HAS-A──► QuestBook     (Character owns QuestBook)
Inventory ──HAS-A──► Item[]        (Inventory contains Item array)
QuestBook ──HAS-A──► Quest[]       (QuestBook contains Quest array)
Quest      ──HAS-A──► Reward       (Quest has Reward)
Battle     ──HAS-A──► Character    (Battle uses Character)
Battle     ──HAS-A──► Enemy        (Battle uses Enemy)
```

### Code Examples

```java
// Character HAS-A Inventory and QuestBook
public abstract class Character {
    private Inventory inventory;   // HAS-A
    private QuestBook questBook;   // HAS-A

    public Character(String name, int maxHealth) {
        this.inventory = new Inventory();    // Creates owned object
        this.questBook = new QuestBook();    // Creates owned object
    }

    public Inventory getInventory() { return inventory; }
    public QuestBook getQuestBook() { return questBook; }
}

// Inventory HAS-A Item[]
public class Inventory {
    private Item[] items;  // HAS-A array of Items
    private int count;

    public Inventory() {
        this.items = new Item[MAX_SIZE];  // Fixed-size array
        this.count = 0;
    }
}

// Quest HAS-A Reward
public class Quest {
    private Reward reward;  // HAS-A

    public Quest(String name, String desc, String target, int kills, Reward reward) {
        this.reward = reward;  // Takes ownership of Reward
    }
}

// QuestBook HAS-A Quest[]
public class QuestBook {
    private Quest[] quests;  // HAS-A array of Quests
    private int count;
}
```

### Multi-Level Composition

```
Character
└── HAS-A Inventory
    └── HAS-A Item[] (up to 10 Items)
        └── Each Item is a Weapon or Consumable

Character
└── HAS-A QuestBook
    └── HAS-A Quest[] (up to 5 Quests)
        └── Each Quest HAS-A Reward
```

---

## 4.8 Aggregation

### Definition

**Aggregation** is a special form of composition where the contained objects can exist independently of the container. The container holds references but doesn't own the lifecycle.

### Aggregation in Kingdom Quest

| Container | Contains | Relationship |
|-----------|----------|--------------|
| Inventory | Item[] | Items can exist independently |
| QuestBook | Quest[] | Quests can exist independently |
| Shop (GameEngine) | Weapon[] | Shop weapons are temporary display objects |
| Shop (GameEngine) | Consumable[] | Shop potions are temporary display objects |

```java
// GameEngine shop — creates temporary arrays for display
private void openShop() {
    Weapon[] shopWeapons = {
        new Weapon("Iron Sword", "A solid iron blade", 8),
        new Weapon("Steel Blade", "Forged steel, sharp edge", 12),
        // ... more weapons
    };
    Consumable[] shopPotions = {
        new Consumable("Health Potion", "Restores 30 HP", 30),
        // ... more potions
    };
    // These arrays exist only for the shop display
}
```

---

## 4.9 Java Arrays (Standard Arrays)

### Definition

Kingdom Quest uses **standard Java arrays** (not Collections framework) for data storage, as required by the project constraints.

### Array Instances

| Location | Array Type | Size | Purpose |
|----------|-----------|------|---------|
| `Inventory.items` | `Item[]` | 10 | Store player items |
| `QuestBook.quests` | `Quest[]` | 5 | Store active quests |
| `GameEngine.shopWeapons` | `Weapon[]` | 4 | Shop weapon display |
| `GameEngine.shopPotions` | `Consumable[]` | 3 | Shop potion display |

### Array Management Patterns

```java
// Fixed-size array with count tracking
public class Inventory {
    private static final int MAX_SIZE = 10;
    private Item[] items;
    private int count;

    public boolean addItem(Item item) {
        if (count >= MAX_SIZE) return false;  // Bounds check
        items[count] = item;
        count++;
        return true;
    }

    public Item removeItem(int index) {
        if (index < 0 || index >= count) return null;  // Bounds check
        Item removed = items[index];
        // Shift elements left
        for (int i = index; i < count - 1; i++) {
            items[i] = items[i + 1];
        }
        items[count - 1] = null;
        count--;
        return removed;
    }
}
```

---

## 4.10 Control Structures

### While Loops

| Location | Purpose | Condition |
|----------|---------|-----------|
| `GameEngine.start()` | Main game loop | `while (running)` |
| `Battle.start()` | Battle loop | `while (player.isAlive() && enemy.isAlive())` |
| `Character.gainExperience()` | Level-up processing | `while (this.exp >= this.level * 100)` |

### For Loops

| Location | Purpose |
|----------|---------|
| `Inventory.removeItem()` | Shift array elements left |
| `Inventory.displayInventory()` | Iterate and display items |
| `QuestBook.getActiveQuests()` | Filter active quests |
| `QuestBook.notifyKill()` | Update all quests |
| `QuestBook.displayQuests()` | Display all quests |
| `GameEngine.checkQuestRewards()` | Check all quests for completion |
| `GameEngine.openShop()` | Display shop items |

### Switch Statements

| Location | Purpose |
|----------|---------|
| `GameEngine.createCharacter()` | Class selection (1-3) |
| `GameEngine.handleMainMenu()` | Menu navigation (1-7) |
| `Battle.start()` | Battle action selection (1-4) |

### If-Else Chains

| Location | Purpose |
|----------|---------|
| `GameEngine.generateRandomEnemy()` | Enemy spawn probability (50/35/15) |
| `GameEngine.restAtInn()` | Gold balance check |
| `Battle.attemptFlee()` | 50% flee success chance |
| `Warrior.attack()` | 20% critical strike check |
| `Archer.attack()` | 30% double shot check |
| `Wizard.attack()` | 25% arcane blast check |
| `Goblin.attack()` | 20% dirty punch check |
| `Orc.attack()` | 15% axe swing check |
| `DragonBoss.attack()` | Enrage + fire breath checks |

---

## 4.11 Exception Handling

### Definition

**Exception handling** uses try-catch blocks to gracefully handle runtime errors, preventing program crashes.

### Implementation

```java
// GameEngine.java
private int readIntInput() {
    try {
        String line = scanner.nextLine().trim();
        return Integer.parseInt(line);
    } catch (NumberFormatException e) {
        System.out.println("Invalid input. Please enter a number.");
        return -1;
    }
}

// Battle.java
private int readInt() {
    try {
        String line = scanner.nextLine().trim();
        return Integer.parseInt(line);
    } catch (NumberFormatException e) {
        return -1;
    }
}
```

### Exception Handling Coverage

| Location | Method | Exception Type | Handling |
|----------|--------|---------------|----------|
| GameEngine.java:317 | `readIntInput()` | `NumberFormatException` | Print error, return -1 |
| Battle.java:141 | `readInt()` | `NumberFormatException` | Return -1 (silent) |

### Input Validation Pattern

```
User types "abc" when expecting a number
    │
    ▼
Integer.parseInt("abc") throws NumberFormatException
    │
    ▼
catch block executes
    │
    ├──► Prints "Invalid input. Please enter a number."
    └──► Returns -1 (invalid choice sentinel)
         │
         ▼
    Caller handles -1 gracefully (default case in switch)
```

---

## 4.12 OOP Principles Summary Table

| # | Principle | Count | Classes Involved |
|---|-----------|-------|------------------|
| 1 | Interface | 1 | CombatMan → Character, Enemy |
| 2 | Abstract Class | 3 | Character, Enemy, Item |
| 3 | Inheritance | 3 hierarchies | Character→3 subclasses, Enemy→3 subclasses, Item→2 subclasses |
| 4 | Polymorphism | 8 overridden methods | attack() in 6 subclasses, use() in 2 subclasses |
| 5 | Method Overloading | 11 constructors | Character, Warrior, Archer, Wizard, Item, Weapon, Consumable, Goblin, Orc, DragonBoss, Reward |
| 6 | Encapsulation | 14 classes | All classes with private fields and public accessors |
| 7 | Composition (HAS-A) | 6 relationships | Character→Inventory, Character→QuestBook, Inventory→Item[], QuestBook→Quest[], Quest→Reward |
| 8 | Aggregation | 2 | Inventory contains Item[], QuestBook contains Quest[] |
| 9 | Java Arrays | 4 arrays | Item[10], Quest[5], Weapon[4], Consumable[3] |
| 10 | While Loops | 3 | Game loop, battle loop, level-up loop |
| 11 | For Loops | 7 | Array iteration in multiple classes |
| 12 | Switch | 3 | Menu, character selection, battle actions |
| 13 | If-Else | 9+ | Enemy spawn, gold checks, special attacks |
| 14 | Exception Handling | 2 | readIntInput(), readInt() |
