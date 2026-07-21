# 6. Use Case Descriptions

This section describes how users interact with the Kingdom Quest system through structured use case models.

---

## 6.1 Use Case Diagram Overview

```
                         ┌─────────────────────────────────────────────────┐
                         │              KINGDOM QUEST SYSTEM                │
                         │                                                 │
    ┌────────┐          │  ┌─────────────┐    ┌──────────────┐           │
    │        │──────────┼─►│  UC-01      │    │   UC-02      │           │
    │        │          │  │  Create      │    │   Explore    │           │
    │        │          │  │  Character   │    │   & Battle   │           │
    │        │          │  └─────────────┘    └──────┬───────┘           │
    │        │          │                            │                    │
    │        │          │  ┌─────────────┐    ┌──────┴───────┐           │
    │ PLAYER │──────────┼─►│  UC-03      │    │   UC-04      │           │
    │        │          │  │  Manage     │    │   Manage     │           │
    │        │          │  │  Inventory  │    │   Quests     │           │
    │        │          │  └─────────────┘    └──────────────┘           │
    │        │          │                                                 │
    │        │          │  ┌─────────────┐    ┌──────────────┐           │
    │        │──────────┼─►│  UC-05      │    │   UC-06      │           │
    │        │          │  │  Purchase   │    │   Rest at    │           │
    └────────┘          │  │  Items      │    │   Inn        │           │
                         │  └─────────────┘    └──────────────┘           │
                         │                                                 │
                         │  ┌─────────────┐    ┌──────────────┐           │
                         │  │  UC-07      │    │   UC-08      │           │
                         │  │  View       │    │   Quit       │           │
                         │  │  Status     │    │   Game       │           │
                         │  └─────────────┘    └──────────────┘           │
                         └─────────────────────────────────────────────────┘
```

---

## 6.2 UC-01: Create Character

| Field | Description |
|-------|-------------|
| **Use Case ID** | UC-01 |
| **Use Case Name** | Create Character |
| **Actor** | Player |
| **Preconditions** | Game has been launched; no character exists |
| **Postconditions** | Character is created with name, class, starter items, and initial quests |
| **Trigger** | Player launches the game |

### Main Flow (Happy Path)

```
1. System displays welcome banner
2. System prompts: "Enter your hero's name:"
3. Player types a name (e.g., "Aragorn")
4. System displays class options:
   1. Warrior - High HP, strong melee attacks
   2. Archer  - Balanced, rapid double shots
   3. Wizard  - Low HP, devastating magic
5. Player selects a class (e.g., 1 for Warrior)
6. System creates character with class-specific stats
7. System provides starter items:
   - Rusty Sword (ATK +5) — auto-equipped
   - Health Potion × 2 (Heal 30 HP each)
8. System initializes and activates 3 quests:
   - Goblin Slayer
   - Orc Cleanup
   - Dragon Hunter
9. System transitions to main menu
```

### Alternative Flows

| Flow | Condition | Action |
|------|-----------|--------|
| **AF-1** | Player enters empty name | System defaults to "Hero" |
| **AF-2** | Player enters invalid class (not 1-3) | System defaults to Warrior |
| **AF-3** | Player enters non-numeric class choice | System catches NumberFormatException, defaults to Warrior |

### Exception Flows

| Exception | Handling |
|-----------|----------|
| Empty name input | Name defaults to "Hero" |
| Invalid class number | Defaults to Warrior with message |
| Non-numeric input | Caught by readIntInput(), returns -1, defaults to Warrior |

---

## 6.3 UC-02: Explore and Battle

| Field | Description |
|-------|-------------|
| **Use Case ID** | UC-02 |
| **Use Case Name** | Explore and Battle |
| **Actor** | Player |
| **Preconditions** | Character exists and is alive; player is at main menu |
| **Postconditions** | Battle resolved; rewards distributed if victorious; quest progress updated |
| **Trigger** | Player selects "1. Explore (Find Enemies)" from main menu |

### Main Flow (Victory)

```
1. System displays: "You venture into the wilderness..."
2. System generates random enemy (Goblin 50%, Orc 35%, Dragon 15%)
3. System displays: "A [enemy] appears!"
4. System initiates Battle
5. Battle displays header and stats
6. Loop while both alive:
   a. System displays turn options (Attack/Item/Status/Flee)
   b. Player selects "1. Attack"
   c. System calculates player damage
   d. System displays damage and enemy HP
   e. System calculates enemy damage
   f. System displays damage and player HP
7. Enemy HP reaches 0
8. System displays: "VICTORY!"
9. System awards EXP (enemy's expReward value)
10. System awards Gold (5-14 random)
11. System notifies QuestBook of kill
12. System checks for quest completion rewards
13. System returns to main menu
```

### Alternative Flows

| Flow | Condition | Action |
|------|-----------|--------|
| **AF-1** | Player selects "2. Use Item" | Display inventory, player uses consumable or equips weapon |
| **AF-2** | Player selects "3. View Status" | Display stats, no turn consumed, return to battle menu |
| **AF-3** | Player selects "4. Flee" | 50% success: end battle, return false. 50% fail: enemy attacks |
| **AF-4** | Player is defeated | Display "DEFEAT!", revive at half HP, return to main menu |
| **AF-5** | Quest completes during battle | System awards quest reward (EXP + Gold + Item) |

### Sub-Flow: Use Item in Battle

```
1. System displays inventory contents
2. System prompts: "Select item number (0 to cancel):"
3. Player selects item number
4. If Consumable: heal player, remove item from inventory
5. If Weapon: equip weapon
6. If invalid/0: cancel, no action taken
```

### Exception Flows

| Exception | Handling |
|-----------|----------|
| Empty inventory | Display "No items in inventory!" |
| Invalid item index | Display "Cancelled." |
| Non-numeric input | Caught by readInt(), returns -1, handled as invalid choice |

---

## 6.4 UC-03: Manage Inventory

| Field | Description |
|-------|-------------|
| **Use Case ID** | UC-03 |
| **Use Case Name** | Manage Inventory |
| **Actor** | Player |
| **Preconditions** | Character exists; player is at main menu |
| **Postconditions** | Inventory state updated (items equipped/used) |
| **Trigger** | Player selects "3. Inventory" from main menu |

### Main Flow

```
1. System displays inventory contents with equipped marker
2. System displays options:
   1. Equip Weapon
   2. Use Consumable
   3. Back
3. Player selects option
4. If "1. Equip Weapon":
   a. System prompts for item number
   b. Player enters item number
   c. System equips weapon (validates it's a weapon type)
   d. System displays equipped confirmation
5. If "2. Use Consumable":
   a. System prompts for item number
   b. Player enters item number
   c. System validates item is a Consumable
   d. System heals player, removes item from inventory
   e. System displays heal result
6. If "3. Back":
   a. Return to main menu
```

### Alternative Flows

| Flow | Condition | Action |
|------|-----------|--------|
| **AF-1** | Inventory is empty | Display "Empty." and return to menu |
| **AF-2** | Player equips non-weapon | Display "That is not a weapon!" |
| **AF-3** | Player uses non-consumable | Display "That item cannot be used like that." |
| **AF-4** | Invalid item number | Display "Invalid item index." |

---

## 6.5 UC-04: View Quest Log

| Field | Description |
|-------|-------------|
| **Use Case ID** | UC-04 |
| **Use Case Name** | View Quest Log |
| **Actor** | Player |
| **Preconditions** | Character exists; quests have been initialized |
| **Postconditions** | No state change (read-only operation) |
| **Trigger** | Player selects "4. Quest Log" from main menu |

### Main Flow

```
1. System displays quest book header: "QUEST BOOK (3/5)"
2. System iterates through all quests
3. For each quest, displays:
   - Quest number
   - Quest name
   - Description
   - Progress: [current/required] [enemy] killed
   - Status: [ACTIVE] or [DONE] or [INACTIVE]
4. Return to main menu
```

### Quest Display Format

```
=== QUEST BOOK (3/5) ===
  1. Goblin Slayer - Defeat 3 goblins in the wild [2/3 Goblin killed] [ACTIVE]
  2. Orc Cleanup - Slay 2 orcs threatening the village [0/2 Orc killed] [ACTIVE]
  3. Dragon Hunter - Defeat the Ancient Dragon [0/1 Ancient Dragon killed] [ACTIVE]
```

---

## 6.6 UC-05: Purchase Items (Shop)

| Field | Description |
|-------|-------------|
| **Use Case ID** | UC-05 |
| **Use Case Name** | Purchase Items |
| **Actor** | Player |
| **Preconditions** | Character exists; player is at main menu |
| **Postconditions** | Item added to inventory; gold deducted (if sufficient) |
| **Trigger** | Player selects "5. Shop" from main menu |

### Main Flow (Successful Purchase)

```
1. System displays: "Gold: [current gold]"
2. System displays weapons (items 1-4):
   - Iron Sword (ATK +8) - 80 Gold
   - Steel Blade (ATK +12) - 120 Gold
   - Enchanted Staff (ATK +15) - 150 Gold
   - War Hammer (ATK +18) - 180 Gold
3. System displays potions (items 5-7):
   - Health Potion (Heal 30) - 30 Gold
   - Greater Health Potion (Heal 60) - 60 Gold
   - Supreme Elixir (Heal 100) - 100 Gold
4. System displays: "0. Back"
5. System prompts: "Buy which item?"
6. Player selects item (e.g., 2 for Steel Blade)
7. System checks: gold >= item price?
   - YES: Deduct gold, add item to inventory, display "Purchased [item]!"
   - NO: Display "Not enough gold!"
8. Return to main menu
```

### Alternative Flows

| Flow | Condition | Action |
|------|-----------|--------|
| **AF-1** | Insufficient gold | Display "Not enough gold!" |
| **AF-2** | Player selects 0 | Return to main menu |
| **AF-3** | Invalid selection | No action, return to main menu |
| **AF-4** | Inventory full | Display "Inventory is full!" (from addItem) |

---

## 6.7 UC-06: Rest at Inn

| Field | Description |
|-------|-------------|
| **Use Case ID** | UC-06 |
| **Use Case Name** | Rest at Inn |
| **Actor** | Player |
| **Preconditions** | Character exists; player is at main menu |
| **Postconditions** | HP restored to max; gold deducted |
| **Trigger** | Player selects "6. Rest (Restore HP)" from main menu |

### Main Flow (Successful Rest)

```
1. System checks: gold >= 10?
   - YES:
     a. Deduct 10 Gold
     b. Restore HP to max
     c. Display: "Rested at the inn. HP fully restored! (-10 Gold)"
   - NO:
     a. Display: "Not enough gold to rest at the inn! (Need 10 Gold)"
2. Return to main menu
```

### Alternative Flows

| Flow | Condition | Action |
|------|-----------|--------|
| **AF-1** | Insufficient gold | Display error with required amount |
| **AF-2** | Player already at full HP | Still deducts gold, HP unchanged |

---

## 6.8 UC-07: View Status

| Field | Description |
|-------|-------------|
| **Use Case ID** | UC-07 |
| **Use Case Name** | View Status |
| **Actor** | Player |
| **Preconditions** | Character exists |
| **Postconditions** | No state change (read-only operation) |
| **Trigger** | Player selects "2. View Status" from main menu |

### Main Flow

```
1. System displays character stats:
   - Name [Level X] HP: [current]/[max] | EXP: [current]/[required]
   - Class: [Warrior/Archer/Wizard]
   - Gold: [amount]
   - Weapon: [name] (ATK +[power]) or "None"
2. Return to main menu
```

### Status Display Format

```
=== CHARACTER STATUS ===
Aragorn [Lv.5] HP: 175/275 | EXP: 45/500
Class: Warrior
Gold: 120
Weapon: Steel Blade (ATK +12)
```

---

## 6.9 UC-08: Quit Game

| Field | Description |
|-------|-------------|
| **Use Case ID** | UC-08 |
| **Use Case Name** | Quit Game |
| **Actor** | Player |
| **Preconditions** | Character exists; player is at main menu |
| **Postconditions** | Game ends; final stats displayed |
| **Trigger** | Player selects "7. Quit" from main menu |

### Main Flow

```
1. System sets running = false
2. Main game loop exits
3. System displays: "Thanks for playing Kingdom Quest!"
4. System displays final stats: [character stats]
5. System displays: "Gold: [amount] | Quests Completed: [count]"
6. Scanner closes
7. Program terminates
```

---

## 6.10 Use Case Traceability Matrix

| Use Case | Classes Involved | OOP Principles Used |
|----------|-----------------|---------------------|
| UC-01: Create Character | GameEngine, Warrior/Archer/Wizard, Inventory, QuestBook, Quest, Reward | Polymorphism, Composition, Constructor Overloading |
| UC-02: Explore & Battle | GameEngine, Battle, Enemy (Goblin/Orc/Dragon), Character, Inventory, QuestBook | Polymorphism, Encapsulation, Exception Handling |
| UC-03: Manage Inventory | Inventory, Weapon, Consumable, Item | Inheritance, Polymorphism, instanceof |
| UC-04: View Quest Log | QuestBook, Quest | Encapsulation, For Loops |
| UC-05: Purchase Items | GameEngine, Weapon, Consumable, Inventory | Composition, Arrays |
| UC-06: Rest at Inn | GameEngine, Character | Encapsulation |
| UC-07: View Status | Character, Inventory, Weapon | Encapsulation, Polymorphism |
| UC-08: Quit Game | GameEngine | While Loop |

---

## 6.11 State Diagram: Game Flow

```
                    ┌──────────┐
                    │  START   │
                    └────┬─────┘
                         │
                         ▼
                 ┌───────────────┐
                 │   CHARACTER   │
                 │   CREATION    │
                 │   (UC-01)     │
                 └───────┬───────┘
                         │
                         ▼
              ┌──────────────────────┐
              │     MAIN MENU        │◄──────────────┐
              │  ┌────────────────┐  │               │
              │  │ 1. Explore     │──┼──► Battle     │
              │  │ 2. Status      │──┼──► Display    │──────┐
              │  │ 3. Inventory   │──┼──► Manage     │──────┤
              │  │ 4. Quest Log   │──┼──► Display    │──────┤
              │  │ 5. Shop        │──┼──► Purchase   │──────┤
              │  │ 6. Rest        │──┼──► Heal       │──────┤
              │  │ 7. Quit        │──┼──► Exit       │      │
              │  └────────────────┘  │               │      │
              └──────────────────────┘               │      │
                         ▲                           │      │
                         │                           │      │
                         └───────────────────────────┘──────┘
```
