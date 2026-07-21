# 3. Functional Requirements

This section defines every functional requirement of the Kingdom Quest system. Each requirement has a unique ID for traceability.

---

## 3.1 Character Creation (FR-001)

| ID | Requirement | Priority |
|----|-------------|----------|
| FR-001.1 | The system shall display a welcome banner on game start | High |
| FR-001.2 | The system shall prompt the user to enter a character name | High |
| FR-001.3 | The system shall default to "Hero" if the user enters an empty name | High |
| FR-001.4 | The system shall display three class options: Warrior, Archer, Wizard | High |
| FR-001.5 | The system shall create a Warrior with 150 base HP if class 1 is selected | High |
| FR-001.6 | The system shall create an Archer with 100 base HP if class 2 is selected | High |
| FR-001.7 | The system shall create a Wizard with 80 base HP if class 3 is selected | High |
| FR-001.8 | The system shall default to Warrior if an invalid class number is entered | High |
| FR-001.9 | The system shall initialize the character at Level 1 with 0 EXP | High |
| FR-001.10 | The system shall provide starter items: Rusty Sword (ATK +5), 2x Health Potion (Heal 30) | High |
| FR-001.11 | The system shall auto-equip the Rusty Sword upon creation | High |
| FR-001.12 | The system shall initialize three quests (Goblin Slayer, Orc Cleanup, Dragon Hunter) | High |
| FR-001.13 | The system shall activate all three initial quests | High |

### Character Class Specifications

```
┌─────────────────────────────────────────────────────────────┐
│                    WARRIOR                                   │
├─────────────────────────────────────────────────────────────┤
│  Base HP:        150                                        │
│  Attack Formula: 15 + (level × 3)                           │
│  Special:        Critical Strike (20% chance, 1.5x damage) │
│  Strategy:       High HP, strong melee, crit bursts         │
└─────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────┐
│                     ARCHER                                   │
├─────────────────────────────────────────────────────────────┤
│  Base HP:        100                                        │
│  Attack Formula: 12 + (level × 2)                           │
│  Special:        Double Shot (30% chance, +50% damage)      │
│  Strategy:       Balanced, consistent damage output         │
└─────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────┐
│                     WIZARD                                   │
├─────────────────────────────────────────────────────────────┤
│  Base HP:        80                                         │
│  Attack Formula: 20 + (level × 4)                           │
│  Special:        Arcane Blast (25% chance, 2x damage)       │
│  Strategy:       Low HP, highest damage, risky but powerful │
└─────────────────────────────────────────────────────────────┘
```

---

## 3.2 Main Menu System (FR-002)

| ID | Requirement | Priority |
|----|-------------|----------|
| FR-002.1 | The system shall display a main menu after character creation | High |
| FR-002.2 | The menu shall offer 7 options numbered 1-7 | High |
| FR-002.3 | Option 1: Explore — Find and fight enemies | High |
| FR-002.4 | Option 2: View Status — Display character stats | High |
| FR-002.5 | Option 3: Inventory — Manage items | High |
| FR-002.6 | Option 4: Quest Log — View quest progress | High |
| FR-002.7 | Option 5: Shop — Buy weapons and potions | High |
| FR-002.8 | Option 6: Rest — Restore HP at the inn | High |
| FR-002.9 | Option 7: Quit — Exit the game | High |
| FR-002.10 | The system shall loop the main menu until Quit is selected | High |
| FR-002.11 | The system shall handle invalid selections with an error message | High |
| FR-002.12 | The system shall display final stats upon quitting | Medium |

### Menu Display Format:

```
=== MAIN MENU ===
1. Explore (Find Enemies)
2. View Status
3. Inventory
4. Quest Log
5. Shop
6. Rest (Restore HP)
7. Quit
Choice: _
```

---

## 3.3 Exploration System (FR-003)

| ID | Requirement | Priority |
|----|-------------|----------|
| FR-003.1 | The system shall display "You venture into the wilderness..." on explore | High |
| FR-003.2 | The system shall generate a random enemy using probability distribution | High |
| FR-003.3 | 50% chance to encounter a Goblin (random roll 0-49) | High |
| FR-003.4 | 35% chance to encounter an Orc (random roll 50-84) | High |
| FR-003.5 | 15% chance to encounter a Dragon Boss (random roll 85-99) | High |
| FR-003.6 | The system shall display the enemy name upon encounter | High |
| FR-003.7 | The system shall initiate a Battle with the generated enemy | High |
| FR-003.8 | The system shall award gold (5-14) upon victory | High |
| FR-003.9 | The system shall update quest progress upon victory | High |
| FR-003.10 | The system shall check for quest completion rewards after battle | High |
| FR-003.11 | The system shall revive the player at half HP upon defeat | High |
| FR-003.12 | The system shall display "You wake up at the inn, barely alive..." on defeat | Medium |

### Enemy Probability Distribution:

```
Random Roll (0-99)
├── 0-49  (50%) ──► Goblin    [HP:40,  ATK:8,   EXP:25 ]
├── 50-84 (35%) ──► Orc       [HP:80,  ATK:14,  EXP:50 ]
└── 85-99 (15%) ──► Dragon    [HP:300, ATK:30,  EXP:200]
```

---

## 3.4 Battle System (FR-004)

| ID | Requirement | Priority |
|----|-------------|----------|
| FR-004.1 | The system shall display a battle header with player and enemy names | High |
| FR-004.2 | The system shall display enemy info (HP, ATK) at battle start | High |
| FR-004.3 | The system shall display player stats at battle start | High |
| FR-004.4 | The battle loop shall continue while both player and enemy are alive | High |
| FR-004.5 | The system shall offer 4 options per turn: Attack, Use Item, View Status, Flee | High |
| FR-004.6 | The system shall process player action before enemy action | High |
| FR-004.7 | The system shall skip enemy turn if enemy is dead after player action | High |
| FR-004.8 | The system shall handle invalid choices with "Invalid choice" message | High |

### Battle Actions:

#### Attack (FR-004.a)

| ID | Requirement | Priority |
|----|-------------|----------|
| FR-004.a.1 | The system shall call player.attack() to calculate damage | High |
| FR-004.a.2 | The system shall display the damage dealt | High |
| FR-004.a.3 | The system shall reduce enemy HP by the damage amount | High |
| FR-004.a.4 | The system shall display updated enemy HP | High |

#### Use Item (FR-004.b)

| ID | Requirement | Priority |
|----|-------------|----------|
| FR-004.b.1 | The system shall display inventory contents | High |
| FR-004.b.2 | The system shall prompt for item selection (0 to cancel) | High |
| FR-004.b.3 | Consumables: The system shall heal the player and remove the item | High |
| FR-004.b.4 | Weapons: The system shall equip the weapon | High |
| FR-004.b.5 | The system shall handle empty inventory with message | High |

#### View Status (FR-004.c)

| ID | Requirement | Priority |
|----|-------------|----------|
| FR-004.c.1 | The system shall display player stats without consuming a turn | High |
| FR-004.c.2 | The system shall display enemy stats | High |
| FR-004.c.3 | The system shall return to the battle menu after viewing | High |

#### Flee (FR-004.d)

| ID | Requirement | Priority |
|----|-------------|----------|
| FR-004.d.1 | The system shall give a 50% chance to successfully flee | High |
| FR-004.d.2 | On success: display "Successfully fled" and end battle | High |
| FR-004.d.3 | On failure: display "Failed to flee!" and enemy gets a turn | High |

### Enemy Turn (FR-004.e)

| ID | Requirement | Priority |
|----|-------------|----------|
| FR-004.e.1 | The system shall call enemy.attack() to calculate damage | High |
| FR-004.e.2 | The system shall display the enemy's attack message | High |
| FR-004.e.3 | The system shall reduce player HP by the damage amount | High |

### Battle Resolution (FR-004.f)

| ID | Requirement | Priority |
|----|-------------|----------|
| FR-004.f.1 | On victory: display "VICTORY!" message | High |
| FR-004.f.2 | On victory: award EXP equal to enemy's expReward | High |
| FR-004.f.3 | On victory: return true to caller | High |
| FR-004.f.4 | On defeat: display "DEFEAT!" message | High |
| FR-004.f.5 | On defeat: return false to caller | High |

---

## 3.5 Combat Formulas (FR-005)

### Player Damage Calculation

```
baseDamage = getBaseAttack()           // Class-specific formula
weaponBonus = equippedWeapon.attackPower  // 0 if no weapon
totalDamage = baseDamage + weaponBonus
// Then class-specific modifier applied:
// Warrior: 20% chance → damage × 1.5
// Archer:  30% chance → damage + (damage × 0.5)
// Wizard:  25% chance → damage × 2.0
```

### Enemy Damage Calculation

```
baseDamage = enemy.baseAttack
variance = random(-2, +2)     // Math.random() * 5 - 2
totalDamage = baseDamage + variance
// Then enemy-specific modifier applied:
// Goblin:  20% chance → damage + 5
// Orc:     15% chance → damage × 1.8
// Dragon:  30% chance (when enraged) → damage + 20
```

### Level-Up Formula

```
expRequired = currentLevel × 100
if (totalEXP >= expRequired):
    totalEXP -= expRequired
    currentLevel++
    maxHealth += 25
    health = maxHealth    // Full heal on level up
    // Loop continues if multiple level-ups possible
```

---

## 3.6 Enemy Specifications (FR-006)

### Goblin

| Property | Value |
|----------|-------|
| Name | "Goblin" |
| HP | 40 |
| Base ATK | 8 |
| EXP Reward | 25 |
| Special | Dirty Punch: 20% chance, +5 bonus damage |
| Behavior | Uses `super.attack()` for base damage with ±2 variance, then checks for dirty punch |

### Orc

| Property | Value |
|----------|-------|
| Name | "Orc" |
| HP | 80 |
| Base ATK | 14 |
| EXP Reward | 50 |
| Special | Axe Swing: 15% chance, 1.8x damage multiplier |
| Behavior | Uses `super.attack()` for base damage, then checks for axe swing |

### Dragon Boss (Ancient Dragon)

| Property | Value |
|----------|-------|
| Name | "Ancient Dragon" |
| HP | 300 |
| Base ATK | 30 |
| EXP Reward | 200 |
| Special Phase 1 | Normal attack with ±2 variance |
| Special Phase 2 | Enrage: activates when HP ≤ 50%, adds +15 to base ATK |
| Special Phase 2 | Fire Breath: 30% chance, +20 bonus damage |
| Behavior | Checks for enrage on each turn, then applies fire breath if enraged |

---

## 3.7 Inventory System (FR-007)

| ID | Requirement | Priority |
|----|-------------|----------|
| FR-007.1 | The system shall use a fixed-size array of 10 items | High |
| FR-007.2 | The system shall track current item count separately | High |
| FR-007.3 | The system shall display "Inventory is full" when attempting to add item #11 | High |
| FR-007.4 | The system shall display "Acquired: [item name]" on successful add | Medium |
| FR-007.5 | The system shall remove an item and shift remaining elements left | High |
| FR-007.6 | The system shall adjust equipped weapon index after removal | High |
| FR-007.7 | The system shall validate weapon type before equipping | High |
| FR-007.8 | The system shall display "[EQUIPPED]" marker for equipped weapon | Medium |
| FR-007.9 | The system shall display item count (e.g., "3/10") | Medium |

### Inventory Operations:

```
addItem(Item)
├── Check: count < MAX_SIZE (10)?
│   ├── YES: items[count] = item; count++; return true
│   └── NO:  Display "Inventory full"; return false

removeItem(int index)
├── Check: 0 <= index < count?
│   ├── YES: Save item at index
│   │        Shift items[index..count-2] = items[index+1..count-1]
│   │        Set items[count-1] = null; count--
│   │        Adjust equippedWeaponIndex if needed
│   │        Return saved item
│   └── NO:  Display "Invalid index"; return null

equipWeapon(int index)
├── Check: index valid AND items[index] instanceof Weapon?
│   ├── YES: equippedWeaponIndex = index; return true
│   └── NO:  Display error; return false
```

---

## 3.8 Quest System (FR-008)

| ID | Requirement | Priority |
|----|-------------|----------|
| FR-008.1 | The system shall support up to 5 concurrent quests | High |
| FR-008.2 | Each quest shall have: name, description, target enemy, required kills, reward | High |
| FR-008.3 | The system shall track current kills per quest | High |
| FR-008.4 | The system shall mark quests as completed when currentKills >= requiredKills | High |
| FR-008.5 | The system shall only update progress for active, incomplete quests | High |
| FR-008.6 | The system shall use case-insensitive enemy name matching | High |
| FR-008.7 | The system shall display progress updates during battle | Medium |
| FR-008.8 | The system shall display "Quest COMPLETED" when objective is met | High |

### Quest Progress Update Flow:

```
notifyKill(enemyName)
└── for each quest in quests[]:
    ├── Skip if quest.completed OR !quest.active
    ├── Check: enemyName.equalsIgnoreCase(quest.targetEnemy)?
    │   ├── YES: quest.currentKills++
    │   │        Display progress: "Quest progress: [name] ([current]/[required])"
    │   │        Check: currentKills >= requiredKills?
    │   │        ├── YES: quest.completed = true
    │   │        │        Display "Quest COMPLETED: [name]!"
    │   │        └── NO: Continue
    │   └── NO: Skip this quest
```

---

## 3.9 Reward System (FR-009)

| ID | Requirement | Priority |
|----|-------------|----------|
| FR-009.1 | Each reward shall contain: EXP, Gold, and optional Item Name | High |
| FR-009.2 | The system shall award EXP immediately upon quest completion | High |
| FR-009.3 | The system shall award Gold immediately upon quest completion | High |
| FR-009.4 | The system shall create and add a weapon if itemName is not null | High |
| FR-009.5 | The reward weapon's ATK shall scale with quests completed (10 + completed × 3) | Medium |
| FR-009.6 | The system shall increment questsCompleted counter | Medium |

### Reward Processing Flow:

```
checkQuestRewards()
└── for each quest in QuestBook:
    ├── Check: quest.completed AND quest.reward != null?
    │   ├── YES: reward = quest.reward
    │   │        player.gainExperience(reward.exp)
    │   │        gold += reward.gold
    │   │        Display "Quest reward: +[exp] EXP, +[gold] Gold"
    │   │        Check: reward.itemName != null?
    │   │        ├── YES: Create Weapon(itemName, "Quest reward", 10+completed*3)
    │   │        │        Add to player inventory
    │   │        └── NO: No item reward
    │   │        questsCompleted++
    │   └── NO: Skip
```

---

## 3.10 Shop System (FR-010)

| ID | Requirement | Priority |
|----|-------------|----------|
| FR-010.1 | The system shall display current gold balance | High |
| FR-010.2 | The system shall display 4 weapons and 3 potions with prices | High |
| FR-010.3 | The system shall prompt for item selection (0 to go back) | High |
| FR-010.4 | The system shall check gold balance before purchase | High |
| FR-010.5 | On sufficient gold: deduct cost and add item to inventory | High |
| FR-010.6 | On insufficient gold: display "Not enough gold!" | High |
| FR-010.7 | The system shall handle invalid selections gracefully | High |

### Shop Display Format:

```
=== SHOP ===
Gold: [current gold]

Weapons:
  1. Iron Sword (ATK +8) - 80 Gold
  2. Steel Blade (ATK +12) - 120 Gold
  3. Enchanted Staff (ATK +15) - 150 Gold
  4. War Hammer (ATK +18) - 180 Gold

Potions:
  5. Health Potion (Heal 30) - 30 Gold
  6. Greater Health Potion (Heal 60) - 60 Gold
  7. Supreme Elixir (Heal 100) - 100 Gold
  0. Back

Buy which item? _
```

---

## 3.11 Rest System (FR-011)

| ID | Requirement | Priority |
|----|-------------|----------|
| FR-011.1 | Resting shall cost 10 Gold | High |
| FR-011.2 | The system shall check gold balance before resting | High |
| FR-011.3 | On sufficient gold: deduct 10 Gold, restore HP to max | High |
| FR-011.4 | On insufficient gold: display error with required amount | High |
| FR-011.5 | The system shall display confirmation message | Medium |

---

## 3.12 Experience and Leveling (FR-012)

| ID | Requirement | Priority |
|----|-------------|----------|
| FR-012.1 | EXP required for next level = currentLevel × 100 | High |
| FR-012.2 | Level 1 requires 100 EXP, Level 2 requires 200 EXP, etc. | High |
| FR-012.3 | The system shall subtract required EXP and increment level on level-up | High |
| FR-012.4 | The system shall increase max HP by 25 on each level-up | High |
| FR-012.5 | The system shall fully restore HP on level-up | High |
| FR-012.6 | The system shall handle multiple level-ups from a single EXP gain | High |
| FR-012.7 | The system shall display "LEVEL UP!" message for each level gained | High |
| FR-012.8 | The system shall ignore EXP gains of 0 or less | Medium |

### Level-Up Example:

```
Player at Level 1, EXP: 80, gains 150 EXP
Total EXP: 80 + 150 = 230
Level 1 requires: 1 × 100 = 100
230 >= 100 → Level up to Level 2!
Remaining EXP: 230 - 100 = 130
Level 2 requires: 2 × 100 = 200
130 < 200 → Stop (no more level-ups)
Final: Level 2, EXP: 130, MaxHP: +25
```

---

## 3.13 Status Display (FR-013)

| ID | Requirement | Priority |
|----|-------------|----------|
| FR-013.1 | Status shall display: Name, Level, HP, EXP, Class, Gold, Weapon | High |
| FR-013.2 | HP format: "HP: [current]/[max]" | High |
| FR-013.3 | EXP format: "EXP: [current]/[required]" | High |
| FR-013.4 | Weapon display: "Weapon: [name] (ATK +[power])" or "None" | Medium |

### Status Display Format:

```
=== CHARACTER STATUS ===
Hero [Lv.3] HP: 125/200 | EXP: 45/300
Class: Warrior
Gold: 85
Weapon: Steel Blade (ATK +12)
```

---

## 3.14 Exception Handling (FR-014)

| ID | Requirement | Priority |
|----|-------------|----------|
| FR-014.1 | All `Integer.parseInt()` calls shall be wrapped in try-catch | High |
| FR-014.2 | NumberFormatException shall be caught and handled gracefully | High |
| FR-014.3 | Invalid input shall display "Invalid input. Please enter a number." | High |
| FR-014.4 | The system shall return -1 for invalid input | High |
| FR-014.5 | The system shall not crash on any non-numeric input | High |

### Exception Handling Locations:

| Location | Method | Exception |
|----------|--------|-----------|
| GameEngine.java | `readIntInput()` | NumberFormatException |
| Battle.java | `readInt()` | NumberFormatException |

---

## 3.15 Gold Economy (FR-015)

| ID | Requirement | Priority |
|----|-------------|----------|
| FR-015.1 | Player starts with 0 Gold | High |
| FR-015.2 | Gold earned on enemy defeat: 5 + random(0-9) = 5-14 Gold | High |
| FR-015.3 | Gold earned from quest rewards: specified per quest | High |
| FR-015.4 | Gold spent on shop items: specified per item | High |
| FR-015.5 | Gold spent on rest: 10 Gold | High |
| FR-015.6 | The system shall display "Not enough gold" when balance is insufficient | High |
| FR-015.7 | The system shall display final gold on game quit | Medium |

### Gold Sources and Sinks:

```
GOLD IN                          GOLD OUT
───────                          ────────
Battle Victory: 5-14             Shop Weapons: 80-180
Goblin Slayer Quest: 20          Shop Potions: 30-100
Orc Cleanup Quest: 40            Inn Rest: 10
Dragon Hunter Quest: 100
```
