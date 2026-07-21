# 5. Class Specifications

Detailed specification for all 18 Java classes in Kingdom Quest.

---

## 5.1 Package: `core`

### 5.1.1 CombatMan (Interface)

**File**: `src/core/CombatMan.java`  
**Lines**: 19  
**Type**: Interface

**Purpose**: Defines the contract for all entities that participate in combat.

**Methods**:

| Method | Return Type | Parameters | Description |
|--------|-------------|------------|-------------|
| `attack()` | `int` | None | Calculate and return damage dealt |
| `takeDmg(int)` | `void` | `dmg` — damage amount | Reduce health by damage |
| `isAlive()` | `boolean` | None | Return true if health > 0 |
| `getName()` | `String` | None | Return entity name |
| `getHealth()` | `int` | None | Return current health |
| `getMaxHealth()` | `int` | None | Return maximum health |

**Implementors**: `Character`, `Enemy`

---

### 5.1.2 Battle

**File**: `src/core/Battle.java`  
**Lines**: 149  
**Type**: Concrete Class

**Purpose**: Manages turn-based combat between a Character and an Enemy.

**Fields**:

| Field | Type | Access | Description |
|-------|------|--------|-------------|
| `player` | `Character` | private | The player character |
| `enemy` | `Enemy` | private | The enemy being fought |
| `scanner` | `Scanner` | private | Input handler |
| `fled` | `boolean` | private | Flee status flag |

**Constructor**:

```java
public Battle(Character player, Enemy enemy, Scanner scanner)
```

| Parameter | Description |
|-----------|-------------|
| `player` | The player's character instance |
| `enemy` | The generated enemy instance |
| `scanner` | Shared Scanner for console input |

**Methods**:

| Method | Return Type | Access | Description |
|--------|-------------|--------|-------------|
| `start()` | `boolean` | public | Execute battle loop; true = victory, false = defeat/flee |
| `playerTurnAttack()` | `void` | private | Calculate and apply player damage |
| `playerTurnItem()` | `void` | private | Handle item usage during battle |
| `attemptFlee()` | `boolean` | private | 50% chance to flee successfully |
| `enemyTurn()` | `void` | private | Calculate and apply enemy damage |
| `readInt()` | `int` | private | Parse integer input with exception handling |

**Battle Loop Logic**:

```
start():
  while (player.isAlive() && enemy.isAlive()):
    display turn header
    display 4 options
    read choice
    switch(choice):
      1 → playerTurnAttack()
      2 → playerTurnItem()
      3 → display stats, continue (skip enemy turn)
      4 → attemptFlee(), break if success
      default → display error

    if enemy.isAlive():
      enemyTurn()

  if fled → return false
  if player.isAlive() → VICTORY, return true
  else → DEFEAT, return false
```

---

## 5.2 Package: `model`

### 5.2.1 Character (Abstract)

**File**: `src/model/Character.java`  
**Lines**: 139  
**Type**: Abstract Class (implements `CombatMan`)

**Purpose**: Base class for all player archetypes.

**Fields**:

| Field | Type | Access | Default | Description |
|-------|------|--------|---------|-------------|
| `name` | `String` | private | from constructor | Character name |
| `health` | `int` | private | maxHealth | Current HP |
| `maxHealth` | `int` | private | 100 or from constructor | Maximum HP |
| `level` | `int` | private | 1 | Current level |
| `exp` | `int` | private | 0 | Current experience |
| `inventory` | `Inventory` | private | new Inventory() | Item container |
| `questBook` | `QuestBook` | private | new QuestBook() | Quest tracker |

**Constructors**:

| Constructor | Parameters | Chaining | Description |
|-------------|-----------|----------|-------------|
| `Character(String)` | `name` | `this(name, 100)` | Default HP of 100 |
| `Character(String, int)` | `name`, `maxHealth` | None | Custom HP |

**Methods**:

| Method | Return Type | Access | Description |
|--------|-------------|--------|-------------|
| `attack()` | `int` | public | Base damage + weapon bonus |
| `getBaseAttack()` | `int` | protected abstract | Class-specific attack formula |
| `gainExperience(int)` | `void` | public | Add EXP, handle level-ups |
| `isAlive()` | `boolean` | public | Return health > 0 |
| `takeDmg(int)` | `void` | public | Reduce health with bounds check |
| `heal(int)` | `void` | public | Restore health up to max |
| `getName()` | `String` | public | Return name |
| `setName(String)` | `void` | public | Set name |
| `getHealth()` | `int` | public | Return health |
| `setHealth(int)` | `void` | public | Set health with bounds check |
| `getMaxHealth()` | `int` | public | Return maxHealth |
| `setMaxHealth(int)` | `void` | public | Set maxHealth |
| `getLevel()` | `int` | public | Return level |
| `setLevel(int)` | `void` | public | Set level |
| `getExp()` | `int` | public | Return exp |
| `setExp(int)` | `void` | public | Set exp |
| `getInventory()` | `Inventory` | public | Return inventory |
| `getQuestBook()` | `QuestBook` | public | Return questBook |
| `getStats()` | `String` | public | Return formatted stats string |

**Level-Up Algorithm**:

```java
public void gainExperience(int amount) {
    if (amount <= 0) return;
    this.exp += amount;
    while (this.exp >= (this.level * 100)) {
        this.exp -= (this.level * 100);
        this.level++;
        this.maxHealth += 25;
        this.health = this.maxHealth;
        System.out.println("LEVEL UP! ...");
    }
}
```

---

### 5.2.2 Warrior

**File**: `src/model/Warrior.java`  
**Lines**: 36  
**Type**: Concrete Class (extends `Character`)

**Purpose**: High-HP melee fighter with critical strike ability.

**Inheritance**: `Warrior` → `Character` → `CombatMan`

**Constructors**:

| Constructor | Parameters | Chaining |
|-------------|-----------|----------|
| `Warrior(String)` | `name` | `super(name, 150)` |
| `Warrior(String, int)` | `name`, `maxHealth` | `super(name, maxHealth)` |

**Overridden Methods**:

| Method | Implementation |
|--------|----------------|
| `getBaseAttack()` | Returns `15 + (getLevel() * 3)` |
| `attack()` | Calls `super.attack()`, 20% chance for 1.5x multiplier |

**Special Ability**: Critical Strike
- Trigger: 20% chance (`Math.random() < 0.2`)
- Effect: Damage multiplied by 1.5
- Message: " lands a devastating CRITICAL STRIKE!"

---

### 5.2.3 Archer

**File**: `src/model/Archer.java`  
**Lines**: 37  
**Type**: Concrete Class (extends `Character`)

**Purpose**: Balanced fighter with double shot ability.

**Inheritance**: `Archer` → `Character` → `CombatMan`

**Constructors**:

| Constructor | Parameters | Chaining |
|-------------|-----------|----------|
| `Archer(String)` | `name` | `super(name, 100)` |
| `Archer(String, int)` | `name`, `maxHealth` | `super(name, maxHealth)` |

**Overridden Methods**:

| Method | Implementation |
|--------|----------------|
| `getBaseAttack()` | Returns `12 + (getLevel() * 2)` |
| `attack()` | Calls `super.attack()`, 30% chance for +50% bonus |

**Special Ability**: Double Shot
- Trigger: 30% chance (`Math.random() < 0.3`)
- Effect: Damage increased by 50% (`damage += damage * 0.5`)
- Message: " fires a rapid double shot!"

---

### 5.2.4 Wizard

**File**: `src/model/Wizard.java`  
**Lines**: 36  
**Type**: Concrete Class (extends `Character`)

**Purpose**: Low-HP magic user with arcane blast ability.

**Inheritance**: `Wizard` → `Character` → `CombatMan`

**Constructors**:

| Constructor | Parameters | Chaining |
|-------------|-----------|----------|
| `Wizard(String)` | `name` | `super(name, 80)` |
| `Wizard(String, int)` | `name`, `maxHealth` | `super(name, maxHealth)` |

**Overridden Methods**:

| Method | Implementation |
|--------|----------------|
| `getBaseAttack()` | Returns `20 + (getLevel() * 4)` |
| `attack()` | Calls `super.attack()`, 25% chance for 2x multiplier |

**Special Ability**: Arcane Blast
- Trigger: 25% chance (`Math.random() < 0.25`)
- Effect: Damage multiplied by 2.0
- Message: " unleashes a devastating ARCANE BLAST!"

---

## 5.3 Package: `enemy`

### 5.3.1 Enemy (Abstract)

**File**: `src/enemy/Enemy.java`  
**Lines**: 87  
**Type**: Abstract Class (implements `CombatMan`)

**Purpose**: Base class for all hostile entities.

**Fields**:

| Field | Type | Access | Description |
|-------|------|--------|-------------|
| `name` | `String` | private | Enemy name |
| `health` | `int` | private | Current HP |
| `maxHealth` | `int` | private | Maximum HP |
| `baseAttack` | `int` | private | Base damage |
| `expReward` | `int` | private | EXP awarded on defeat |

**Constructor**:

```java
public Enemy(String name, int maxHealth, int baseAttack, int expReward)
```

**Methods**:

| Method | Return Type | Access | Description |
|--------|-------------|--------|-------------|
| `attack()` | `int` | public | Base damage ±2 variance |
| `takeDmg(int)` | `void` | public | Reduce health, display damage |
| `isAlive()` | `boolean` | public | Return health > 0 |
| `getName()` | `String` | public | Return name |
| `setName(String)` | `void` | public | Set name |
| `getHealth()` | `int` | public | Return health |
| `setHealth(int)` | `void` | public | Set health with bounds check |
| `getMaxHealth()` | `int` | public | Return maxHealth |
| `getBaseAttack()` | `int` | public | Return baseAttack |
| `setBaseAttack(int)` | `void` | public | Set baseAttack |
| `getExpReward()` | `int` | public | Return expReward |
| `setExpReward(int)` | `void` | public | Set expReward |
| `getDisplayInfo()` | `String` | public | Return formatted info string |

**Attack Formula**:

```java
public int attack() {
    int variance = (int) (Math.random() * 5) - 2;  // -2 to +2
    return baseAttack + variance;
}
```

---

### 5.3.2 Goblin

**File**: `src/enemy/Goblin.java`  
**Lines**: 31  
**Type**: Concrete Class (extends `Enemy`)

**Purpose**: Weak, fast enemy.

**Inheritance**: `Goblin` → `Enemy` → `CombatMan`

**Stats**:

| Property | Value |
|----------|-------|
| Name | "Goblin" |
| HP | 40 |
| ATK | 8 |
| EXP Reward | 25 |

**Constructors**:

| Constructor | Parameters | Chaining |
|-------------|-----------|----------|
| `Goblin()` | None | `super("Goblin", 40, 8, 25)` |
| `Goblin(String)` | `name` | `super(name, 40, 8, 25)` |

**Special Ability**: Dirty Punch
- Trigger: 20% chance
- Effect: +5 bonus damage
- Message: " throws a dirty punch!"

---

### 5.3.3 Orc

**File**: `src/enemy/Orc.java`  
**Lines**: 31  
**Type**: Concrete Class (extends `Enemy`)

**Purpose**: Medium-strength enemy with heavy hits.

**Inheritance**: `Orc` → `Enemy` → `CombatMan`

**Stats**:

| Property | Value |
|----------|-------|
| Name | "Orc" |
| HP | 80 |
| ATK | 14 |
| EXP Reward | 50 |

**Constructors**:

| Constructor | Parameters | Chaining |
|-------------|-----------|----------|
| `Orc()` | None | `super("Orc", 80, 14, 50)` |
| `Orc(String)` | `name` | `super(name, 80, 14, 50)` |

**Special Ability**: Axe Swing
- Trigger: 15% chance
- Effect: Damage multiplied by 1.8
- Message: " swings a massive axe overhead!"

---

### 5.3.4 DragonBoss

**File**: `src/enemy/DragonBoss.java`  
**Lines**: 44  
**Type**: Concrete Class (extends `Enemy`)

**Purpose**: Boss enemy with enrage mechanic.

**Inheritance**: `DragonBoss` → `Enemy` → `CombatMan`

**Additional Fields**:

| Field | Type | Access | Default | Description |
|-------|------|--------|---------|-------------|
| `enraged` | `boolean` | private | false | Enrage state |

**Stats**:

| Property | Value |
|----------|-------|
| Name | "Ancient Dragon" |
| HP | 300 |
| ATK | 30 (45 when enraged) |
| EXP Reward | 200 |

**Constructors**:

| Constructor | Parameters | Chaining |
|-------------|-----------|----------|
| `DragonBoss()` | None | `super("Ancient Dragon", 300, 30, 200)` |
| `DragonBoss(String)` | `name` | `super(name, 300, 30, 200)` |

**Special Abilities**:

1. **Enrage Mode**
   - Trigger: HP ≤ 50% (150 HP)
   - Effect: baseAttack increased by 15 (30 → 45)
   - Message: " enters ENRAGE mode! Attack power surges!"
   - One-time activation per battle

2. **Fire Breath**
   - Trigger: 30% chance (only when enraged)
   - Effect: +20 bonus damage
   - Message: " breathes devastating FIRE!"

---

## 5.4 Package: `item`

### 5.4.1 Item (Abstract)

**File**: `src/item/Item.java`  
**Lines**: 59  
**Type**: Abstract Class

**Purpose**: Base class for all game items.

**Fields**:

| Field | Type | Access | Description |
|-------|------|--------|-------------|
| `name` | `String` | private | Item name |
| `description` | `String` | private | Item description |
| `value` | `int` | private | Gold value |

**Constructors**:

| Constructor | Parameters | Chaining |
|-------------|-----------|----------|
| `Item(String, String)` | `name`, `description` | `this(name, description, 0)` |
| `Item(String, String, int)` | `name`, `description`, `value` | None |

**Abstract Methods**:

| Method | Return Type | Description |
|--------|-------------|-------------|
| `use(CombatMan)` | `String` | Apply item effect |
| `getType()` | `String` | Return "Weapon" or "Consumable" |

**Concrete Methods**:

| Method | Return Type | Description |
|--------|-------------|-------------|
| `getName()` | `String` | Return name |
| `setName(String)` | `void` | Set name |
| `getDescription()` | `String` | Return description |
| `setDescription(String)` | `void` | Set description |
| `getValue()` | `int` | Return value |
| `setValue(int)` | `void` | Set value |
| `toString()` | `String` | Return formatted string |

---

### 5.4.2 Weapon

**File**: `src/item/Weapon.java`  
**Lines**: 41  
**Type**: Concrete Class (extends `Item`)

**Purpose**: Equippable attack power boost.

**Inheritance**: `Weapon` → `Item`

**Additional Fields**:

| Field | Type | Access | Description |
|-------|------|--------|-------------|
| `attackPower` | `int` | private | ATK bonus when equipped |

**Constructors**:

| Constructor | Parameters | Chaining |
|-------------|-----------|----------|
| `Weapon(String, String, int)` | `name`, `description`, `attackPower` | `super(name, description, attackPower * 10)` |
| `Weapon(String, int)` | `name`, `attackPower` | `this(name, "A trusty weapon", attackPower)` |

**Methods**:

| Method | Return Type | Implementation |
|--------|-------------|----------------|
| `use(CombatMan)` | `String` | Returns equip message (does not actually equip — inventory handles that) |
| `getType()` | `String` | Returns "Weapon" |
| `getAttackPower()` | `int` | Return attackPower |
| `setAttackPower(int)` | `void` | Set attackPower |

---

### 5.4.3 Consumable

**File**: `src/item/Consumable.java`  
**Lines**: 45  
**Type**: Concrete Class (extends `Item`)

**Purpose**: Usable health restoration item.

**Inheritance**: `Consumable` → `Item`

**Additional Fields**:

| Field | Type | Access | Description |
|-------|------|--------|-------------|
| `healAmount` | `int` | private | HP restored when used |

**Constructors**:

| Constructor | Parameters | Chaining |
|-------------|-----------|----------|
| `Consumable(String, String, int)` | `name`, `description`, `healAmount` | `super(name, description, healAmount)` |
| `Consumable(String, int)` | `name`, `healAmount` | `this(name, "Restores health", healAmount)` |

**Methods**:

| Method | Return Type | Implementation |
|--------|-------------|----------------|
| `use(CombatMan)` | `String` | Heals target if Character, returns heal message |
| `getType()` | `String` | Returns "Consumable" |
| `getHealAmount()` | `int` | Return healAmount |
| `setHealAmount(int)` | `void` | Set healAmount |

**Use Implementation**:

```java
public String use(core.CombatMan target) {
    int before = target.getHealth();
    if (target instanceof model.Character) {
        ((model.Character) target).heal(healAmount);
    }
    return getName() + " used! HP " + before + " -> " + target.getHealth();
}
```

---

### 5.4.4 Inventory

**File**: `src/item/Inventory.java`  
**Lines**: 109  
**Type**: Concrete Class

**Purpose**: Manages player item storage using a fixed-size array.

**Constants**:

| Constant | Value | Description |
|----------|-------|-------------|
| `MAX_SIZE` | 10 | Maximum items in inventory |

**Fields**:

| Field | Type | Access | Default | Description |
|-------|------|--------|---------|-------------|
| `items` | `Item[]` | private | new Item[10] | Fixed-size array |
| `count` | `int` | private | 0 | Current item count |
| `equippedWeaponIndex` | `int` | private | -1 | Equipped weapon index |

**Methods**:

| Method | Return Type | Description |
|--------|-------------|-------------|
| `addItem(Item)` | `boolean` | Add item if space; shift not needed (add at end) |
| `removeItem(int)` | `Item` | Remove and shift elements left |
| `getItem(int)` | `Item` | Get item at index (null if invalid) |
| `equipWeapon(int)` | `boolean` | Equip weapon at index (validates type) |
| `getEquippedWeapon()` | `Weapon` | Return currently equipped weapon |
| `getAllItems()` | `Item[]` | Return copy of items array |
| `getCount()` | `int` | Return current count |
| `getMaxSize()` | `int` | Return MAX_SIZE |
| `displayInventory()` | `void` | Print formatted inventory |

---

## 5.5 Package: `progression`

### 5.5.1 Quest

**File**: `src/progression/Quest.java`  
**Lines**: 89  
**Type**: Concrete Class

**Purpose**: Represents a kill-based objective.

**Fields**:

| Field | Type | Access | Default | Description |
|-------|------|--------|---------|-------------|
| `name` | `String` | private | from constructor | Quest name |
| `description` | `String` | private | from constructor | Quest description |
| `targetEnemy` | `String` | private | from constructor | Required enemy type |
| `requiredKills` | `int` | private | from constructor | Kills needed |
| `currentKills` | `int` | private | 0 | Current progress |
| `reward` | `Reward` | private | from constructor | Completion reward |
| `completed` | `boolean` | private | false | Completion status |
| `active` | `boolean` | private | false | Active status |

**Constructor**:

```java
public Quest(String name, String description, String targetEnemy, int requiredKills, Reward reward)
```

**Methods**:

| Method | Return Type | Description |
|--------|-------------|-------------|
| `addKill(String)` | `void` | Update progress on matching kill |
| `getName()` | `String` | Return name |
| `setName(String)` | `void` | Set name |
| `getDescription()` | `String` | Return description |
| `getTargetEnemy()` | `String` | Return targetEnemy |
| `getRequiredKills()` | `int` | Return requiredKills |
| `getCurrentKills()` | `int` | Return currentKills |
| `getReward()` | `Reward` | Return reward |
| `isCompleted()` | `boolean` | Return completed |
| `isActive()` | `boolean` | Return active |
| `setActive(boolean)` | `void` | Set active |
| `getProgress()` | `String` | Return formatted progress |

---

### 5.5.2 QuestBook

**File**: `src/progression/QuestBook.java`  
**Lines**: 93  
**Type**: Concrete Class

**Purpose**: Manages player quest storage using a fixed-size array.

**Constants**:

| Constant | Value | Description |
|----------|-------|-------------|
| `MAX_QUESTS` | 5 | Maximum quests in book |

**Fields**:

| Field | Type | Access | Default | Description |
|-------|------|--------|---------|-------------|
| `quests` | `Quest[]` | private | new Quest[5] | Fixed-size array |
| `count` | `int` | private | 0 | Current quest count |

**Methods**:

| Method | Return Type | Description |
|--------|-------------|-------------|
| `addQuest(Quest)` | `boolean` | Add quest if space available |
| `activateQuest(int)` | `void` | Set quest at index as active |
| `getQuest(int)` | `Quest` | Get quest at index |
| `getActiveQuests()` | `Quest[]` | Return array of active incomplete quests |
| `notifyKill(String)` | `void` | Update all active quests for matching kill |
| `getCount()` | `int` | Return current count |
| `displayQuests()` | `void` | Print formatted quest log |
| `toString()` | `String` | Return formatted string of all quests |

---

### 5.5.3 Reward

**File**: `src/progression/Reward.java`  
**Lines**: 58  
**Type**: Concrete Class

**Purpose**: Stores quest completion rewards.

**Fields**:

| Field | Type | Access | Description |
|-------|------|--------|-------------|
| `exp` | `int` | private | Experience reward |
| `gold` | `int` | private | Gold reward |
| `itemName` | `String` | private | Item reward (nullable) |

**Constructors**:

| Constructor | Parameters | Chaining |
|-------------|-----------|----------|
| `Reward(int, int)` | `exp`, `gold` | `this(exp, gold, null)` |
| `Reward(int, int, String)` | `exp`, `gold`, `itemName` | None |

**Methods**:

| Method | Return Type | Description |
|--------|-------------|-------------|
| `getExp()` | `int` | Return exp |
| `setExp(int)` | `void` | Set exp |
| `getGold()` | `int` | Return gold |
| `setGold(int)` | `void` | Set gold |
| `getItemName()` | `String` | Return itemName |
| `setItemName(String)` | `void` | Set itemName |
| `getDescription()` | `String` | Return formatted description |

---

## 5.6 Package: `root`

### 5.6.1 GameEngine

**File**: `src/GameEngine.java`  
**Lines**: 331  
**Type**: Concrete Class (Entry Point)

**Purpose**: Main entry point; orchestrates all game systems.

**Fields**:

| Field | Type | Access | Default | Description |
|-------|------|--------|---------|-------------|
| `scanner` | `Scanner` | private | new Scanner(System.in) | Input handler |
| `player` | `Character` | private | null | Player character |
| `running` | `boolean` | private | true | Game loop control |
| `gold` | `int` | private | 0 | Player currency |
| `questsCompleted` | `int` | private | 0 | Completed quest count |

**Methods**:

| Method | Return Type | Access | Description |
|--------|-------------|--------|-------------|
| `start()` | `void` | public | Initialize and run game loop |
| `printBanner()` | `void` | private | Display ASCII art title |
| `createCharacter()` | `void` | private | Character creation process |
| `initializeStarterItems()` | `void` | private | Provide starting equipment |
| `initializeQuests()` | `void` | private | Set up initial quests |
| `displayMainMenu()` | `void` | private | Show main menu |
| `handleMainMenu(int)` | `void` | private | Process menu selection |
| `exploreArea()` | `void` | private | Generate enemy, start battle |
| `generateRandomEnemy()` | `Enemy` | private | Create random enemy |
| `checkQuestRewards()` | `void` | private | Process completed quests |
| `displayStatus()` | `void` | private | Show character stats |
| `openInventory()` | `void` | private | Inventory management |
| `viewQuests()` | `void` | private | Display quest log |
| `openShop()` | `void` | private | Shop interface |
| `restAtInn()` | `void` | private | Restore HP |
| `readIntInput()` | `int` | private | Parse integer input |

**Main Method**:

```java
public static void main(String[] args) {
    GameEngine game = new GameEngine();
    game.start();
}
```

---

## 5.7 Class Summary Table

| # | Class | Package | Type | Extends/Implements | Lines |
|---|-------|---------|------|-------------------|-------|
| 1 | CombatMan | core | Interface | — | 19 |
| 2 | Battle | core | Class | — | 149 |
| 3 | Character | model | Abstract Class | implements CombatMan | 139 |
| 4 | Warrior | model | Class | extends Character | 36 |
| 5 | Archer | model | Class | extends Character | 37 |
| 6 | Wizard | model | Class | extends Character | 36 |
| 7 | Enemy | enemy | Abstract Class | implements CombatMan | 87 |
| 8 | Goblin | enemy | Class | extends Enemy | 31 |
| 9 | Orc | enemy | Class | extends Enemy | 31 |
| 10 | DragonBoss | enemy | Class | extends Enemy | 44 |
| 11 | Item | item | Abstract Class | — | 59 |
| 12 | Weapon | item | Class | extends Item | 41 |
| 13 | Consumable | item | Class | extends Item | 45 |
| 14 | Inventory | item | Class | — | 109 |
| 15 | Quest | progression | Class | — | 89 |
| 16 | QuestBook | progression | Class | — | 93 |
| 17 | Reward | progression | Class | — | 58 |
| 18 | GameEngine | root | Class | — | 331 |

**Total**: 18 classes | ~650 lines | 5 packages
