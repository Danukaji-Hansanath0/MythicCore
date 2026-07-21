# 2. Overall Description

## 2.1 Product Perspective

Kingdom Quest is a **standalone, console-based** application. It has no network dependencies, no database, and no external library dependencies. The entire system is built using only the Java Standard Library (`java.util.Scanner` for input).

### System Context Diagram

```
┌──────────────────────────────────────────────────────────┐
│                    KINGDOM QUEST                          │
│                                                          │
│  ┌──────────┐  ┌──────────┐  ┌──────────┐  ┌────────┐  │
│  │  Player  │  │  Battle  │  │   Shop   │  │  Quest │  │
│  │  System  │  │  System  │  │  System  │  │ System │  │
│  └────┬─────┘  └────┬─────┘  └────┬─────┘  └───┬────┘  │
│       │              │              │             │       │
│  ┌────┴──────────────┴──────────────┴─────────────┴────┐ │
│  │                   GameEngine                        │ │
│  │              (Main Controller)                      │ │
│  └─────────────────────────────────────────────────────┘ │
│                                                          │
│  ┌──────────┐  ┌──────────┐  ┌──────────┐              │
│  │ Inventory│  │  Enemy   │  │Progression│              │
│  │  System  │  │  System  │  │  System   │              │
│  └──────────┘  └──────────┘  └──────────┘              │
│                                                          │
└──────────────────────────────────────────────────────────┘
         │                    │
         ▼                    ▼
   ┌──────────┐        ┌──────────┐
   │ Keyboard │        │ Console  │
   │  Input   │        │  Output  │
   └──────────┘        └──────────┘
```

### Technology Stack

| Component | Technology |
|-----------|------------|
| Programming Language | Java (JDK 8+) |
| IDE | Apache NetBeans |
| Build System | Ant (build.xml) |
| Input Method | `java.util.Scanner` (keyboard) |
| Output Method | `System.out` (console) |
| Data Storage | In-memory only (no files/database) |
| Version Control | Git |

## 2.2 Product Functions

The system is organized into **6 major functional areas**:

### 2.2.1 Character Management

```
┌─────────────────────────────────────────┐
│         CHARACTER MANAGEMENT            │
├─────────────────────────────────────────┤
│ • Create character with name            │
│ • Select class (Warrior/Archer/Wizard)  │
│ • Track HP, Level, EXP                  │
│ • Handle damage, healing, leveling      │
│ • Manage equipped weapon                │
└─────────────────────────────────────────┘
```

**Character Class Comparison:**

| Stat | Warrior | Archer | Wizard |
|------|---------|--------|--------|
| Base HP | 150 | 100 | 80 |
| Attack Formula | 15 + level × 3 | 12 + level × 2 | 20 + level × 4 |
| Special Ability | Critical Strike | Double Shot | Arcane Blast |
| Special Chance | 20% | 30% | 25% |
| Special Multiplier | 1.5x damage | +50% damage | 2x damage |
| Playstyle | Tanky melee | Balanced ranged | Glass cannon magic |

### 2.2.2 Combat System

```
┌─────────────────────────────────────────┐
│           COMBAT SYSTEM                 │
├─────────────────────────────────────────┤
│ • Turn-based battle loop                │
│ • Player actions: Attack/Item/Status/Flee│
│ • Enemy AI with special attacks         │
│ • Damage calculation with variance      │
│ • Victory: EXP + gold rewards           │
│ • Defeat: Revive at half HP             │
│ • Flee: 50% success chance              │
└─────────────────────────────────────────┘
```

**Combat Flow:**
```
Player Turn                          Enemy Turn
    │                                     │
    ├──► Attack ──► Calculate Damage ──► Enemy HP - Damage
    │                                     │
    ├──► Use Item ──► Heal/Equip ────►    │
    │                                     │
    ├──► View Status ──► Display ────►    │
    │                                     │
    ├──► Flee ──► 50% chance ───────►    │
    │                                     │
    │                              Enemy attacks
    │                                     │
    │                              Player HP - Damage
    │                                     │
    └──────── Loop until someone dies ────┘
```

### 2.2.3 Enemy System

```
┌─────────────────────────────────────────┐
│           ENEMY SYSTEM                  │
├─────────────────────────────────────────┤
│ • 3 enemy types with unique stats       │
│ • Random encounter generation           │
│ • Special attack abilities              │
│ • EXP rewards on defeat                 │
│ • Dragon boss with enrage mechanic      │
└─────────────────────────────────────────┘
```

**Enemy Comparison:**

| Enemy | HP | ATK | EXP Reward | Special Ability | Spawn Rate |
|-------|-----|-----|------------|-----------------|------------|
| Goblin | 40 | 8 | 25 | Dirty Punch (+5 dmg, 20%) | 50% |
| Orc | 80 | 14 | 50 | Axe Swing (1.8x dmg, 15%) | 35% |
| Ancient Dragon | 300 | 30 | 200 | Enrage + Fire Breath (+20 dmg, 30%) | 15% |

### 2.2.4 Inventory System

```
┌─────────────────────────────────────────┐
│         INVENTORY SYSTEM                │
├─────────────────────────────────────────┤
│ • Fixed-size array (max 10 items)       │
│ • Add/remove items                      │
│ • Equip weapons for ATK bonus           │
│ • Use consumables for healing           │
│ • Display with equipped marker          │
└─────────────────────────────────────────┘
```

**Item Types:**

| Type | Purpose | Stats | Usage |
|------|---------|-------|-------|
| Weapon | Equip for ATK bonus | attackPower | Equipped in inventory |
| Consumable | Restore HP when used | healAmount | Consumed on use |

### 2.2.5 Quest System

```
┌─────────────────────────────────────────┐
│          QUEST SYSTEM                   │
├─────────────────────────────────────────┤
│ • Kill-based objectives                 │
│ • Track progress (current/required)     │
│ • Auto-update on enemy defeat           │
│ • Award EXP, gold, and items            │
│ • Support up to 5 concurrent quests     │
└─────────────────────────────────────────┘
```

**Initial Quests:**

| Quest | Target | Required Kills | Rewards |
|-------|--------|----------------|---------|
| Goblin Slayer | Goblin | 3 | 75 EXP, 20 Gold, Iron Shield |
| Orc Cleanup | Orc | 2 | 100 EXP, 40 Gold, Steel Axe |
| Dragon Hunter | Ancient Dragon | 1 | 200 EXP, 100 Gold, Dragon Scale Armor |

### 2.2.6 Shop & Economy

```
┌─────────────────────────────────────────┐
│         SHOP & ECONOMY                  │
├─────────────────────────────────────────┤
│ • Gold earned from battles              │
│ • Gold earned from quest rewards        │
│ • Buy weapons and potions               │
│ • Gold check before purchase            │
│ • Rest at inn costs 10 gold             │
└─────────────────────────────────────────┘
```

**Shop Inventory:**

| Item | Type | Stat | Price |
|------|------|------|-------|
| Iron Sword | Weapon | ATK +8 | 80 Gold |
| Steel Blade | Weapon | ATK +12 | 120 Gold |
| Enchanted Staff | Weapon | ATK +15 | 150 Gold |
| War Hammer | Weapon | ATK +18 | 180 Gold |
| Health Potion | Consumable | Heal 30 HP | 30 Gold |
| Greater Health Potion | Consumable | Heal 60 HP | 60 Gold |
| Supreme Elixir | Consumable | Heal 100 HP | 100 Gold |

## 2.3 User Classes and Characteristics

| User Type | Description | Technical Skill | Interaction |
|-----------|-------------|-----------------|-------------|
| Player | Primary user who plays the game | Basic computer literacy | Keyboard input, reads console output |
| Developer | Person maintaining/extending the code | Java programming | Reads source code, modifies classes |
| Evaluator | Instructor reviewing the project | OOP knowledge | Reviews code for OOP principles |

## 2.4 Operating Environment

| Component | Requirement |
|-----------|-------------|
| Operating System | Windows, Linux, or macOS |
| Java Version | JDK 8 or higher |
| Memory | Minimum 64MB available heap |
| Disk Space | < 1MB for compiled classes |
| Display | Console/terminal with 80+ column width |
| Input | Standard keyboard |

## 2.5 Design and Implementation Constraints

| Constraint | Description |
|------------|-------------|
| Java Only | Must be implemented entirely in Java |
| No GUI | Console text interface only |
| No Collections | Must use standard Java arrays (`Item[]`, `Quest[]`) |
| OOP Compliance | Must demonstrate: Interface, Abstract Class, Inheritance, Polymorphism, Encapsulation, Method Overloading, Composition |
| Exception Handling | Must handle `NumberFormatException` for all user input |
| No External Libraries | Only `java.util.Scanner` from standard library |
| Private Fields | All instance variables must be `private` or `protected` |
| Public Accessors | All field access through public getters/setters |
| Single Source File Entry | `GameEngine.java` contains `main()` method |

## 2.6 Assumptions and Dependencies

### Assumptions

1. User has Java Development Kit (JDK) 8+ installed
2. User has a text-based terminal/console available
3. User can type numeric input for menu selections
4. The game runs in a single session (no persistence between runs)

### Dependencies

| Dependency | Type | Purpose |
|------------|------|---------|
| `java.util.Scanner` | Standard Library | Read user input from console |
| `java.lang.Math` | Standard Library | Random number generation for combat variance |

## 2.7 System Architecture

### Package Structure

```
KingdomQuest/
├── src/
│   ├── GameEngine.java              [root]     Main entry point
│   ├── core/
│   │   ├── CombatMan.java           [interface] Combat contract
│   │   └── Battle.java             [class]     Battle manager
│   ├── model/
│   │   ├── Character.java          [abstract]  Player base class
│   │   ├── Warrior.java            [class]     Warrior archetype
│   │   ├── Archer.java             [class]     Archer archetype
│   │   └── Wizard.java             [class]     Wizard archetype
│   ├── enemy/
│   │   ├── Enemy.java              [abstract]  Enemy base class
│   │   ├── Goblin.java             [class]     Goblin enemy
│   │   ├── Orc.java                [class]     Orc enemy
│   │   └── DragonBoss.java         [class]     Dragon boss enemy
│   ├── item/
│   │   ├── Item.java               [abstract]  Item base class
│   │   ├── Weapon.java             [class]     Weapon item
│   │   ├── Consumable.java         [class]     Consumable item
│   │   └── Inventory.java          [class]     Item storage
│   └── progression/
│       ├── Quest.java              [class]     Quest definition
│       ├── QuestBook.java          [class]     Quest storage
│       └── Reward.java             [class]     Quest rewards
├── test/                                        (empty - future)
├── build/                                       Compiled classes
├── build.xml                                    Ant build file
├── README.md                                    Project overview
├── SRS_Document.md                              Combined SRS
└── srs/                                         Detailed SRS folder
```

### Package Responsibilities

| Package | Classes | Responsibility |
|---------|---------|----------------|
| `core` | CombatMan, Battle | Combat interface and battle execution |
| `model` | Character, Warrior, Archer, Wizard | Player character definitions |
| `enemy` | Enemy, Goblin, Orc, DragonBoss | Enemy definitions and AI |
| `item` | Item, Weapon, Consumable, Inventory | Item types and storage |
| `progression` | Quest, QuestBook, Reward | Quest tracking and rewards |
| `root` | GameEngine | Main game loop and orchestration |
