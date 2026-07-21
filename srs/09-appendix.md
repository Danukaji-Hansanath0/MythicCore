# 9. Appendix

---

## 9.1 Class Diagram

A full PlantUML class diagram is available at: [`KingdomQuest_ClassDiagram.puml`](KingdomQuest_ClassDiagram.puml)

**To render the diagram:**
- **Visual Paradigm**: File > Import > PlantUML
- **Online**: Paste into [plantuml.com](https://www.plantuml.com/plantuml)
- **VS Code**: Install PlantUML extension, preview with `Alt+D`
- **IntelliJ**: Install PlantUML Integration plugin

## 9.1 Project Structure

```
MythicCore/
├── src/
│   ├── GameEngine.java              # Entry point, main menu, shop
│   ├── core/
│   │   ├── CombatMan.java           # Interface for combat
│   │   └── Battle.java              # Turn-based combat loop
│   ├── model/
│   │   ├── Character.java           # Abstract base for player
│   │   ├── Warrior.java             # High HP, crit strikes
│   │   ├── Archer.java              # Balanced, double shots
│   │   └── Wizard.java              # Low HP, arcane blast
│   ├── item/
│   │   ├── Item.java                # Abstract base for items
│   │   ├── Weapon.java              # Attack power boost
│   │   ├── Consumable.java          # HP restoration
│   │   └── Inventory.java           # Item array manager
│   ├── enemy/
│   │   ├── Enemy.java               # Abstract base for enemies
│   │   ├── Goblin.java              # Weak, fast
│   │   ├── Orc.java                 # Medium, heavy hits
│   │   └── DragonBoss.java          # Boss with enrage phase
│   └── progression/
│       ├── Quest.java               # Kill-count objective
│       ├── QuestBook.java           # Quest array manager
│       └── Reward.java              # EXP, gold, item reward
├── test/                            # (empty - future tests)
├── build/                           # Compiled .class files
├── srs/                             # SRS documentation
│   ├── 01-introduction.md
│   ├── 02-overview.md
│   ├── 03-functional-requirements.md
│   ├── 04-oop-design.md
│   ├── 05-class-specifications.md
│   ├── 06-use-cases.md
│   ├── 07-non-functional.md
│   ├── 08-testing.md
│   └── 09-appendix.md
├── build.xml                        # Ant build file
├── manifest.mf                      # JAR manifest
├── README.md                        # Project overview
├── SRS_Document.md                  # Combined SRS (single file)
└── script.md                        # Video demo script
```

---

## 9.2 Class Summary Table

| # | Class | Package | Type | Extends/Implements | Lines |
|---|-------|---------|------|-------------------|-------|
| 1 | CombatMan | core | Interface | -- | 19 |
| 2 | Battle | core | Class | -- | 149 |
| 3 | Character | model | Abstract | implements CombatMan | 139 |
| 4 | Warrior | model | Class | extends Character | 36 |
| 5 | Archer | model | Class | extends Character | 37 |
| 6 | Wizard | model | Class | extends Character | 36 |
| 7 | Enemy | enemy | Abstract | implements CombatMan | 87 |
| 8 | Goblin | enemy | Class | extends Enemy | 31 |
| 9 | Orc | enemy | Class | extends Enemy | 31 |
| 10 | DragonBoss | enemy | Class | extends Enemy | 44 |
| 11 | Item | item | Abstract | -- | 59 |
| 12 | Weapon | item | Class | extends Item | 41 |
| 13 | Consumable | item | Class | extends Item | 45 |
| 14 | Inventory | item | Class | -- | 109 |
| 15 | Quest | progression | Class | -- | 89 |
| 16 | QuestBook | progression | Class | -- | 93 |
| 17 | Reward | progression | Class | -- | 58 |
| 18 | GameEngine | root | Class | -- | 331 |

**Total**: 18 classes | ~650 lines | 5 packages

---

## 9.3 Package Responsibilities

| Package | Classes | Responsibility |
|---------|---------|----------------|
| core | CombatMan, Battle | Combat interface and battle execution |
| model | Character, Warrior, Archer, Wizard | Player character definitions |
| enemy | Enemy, Goblin, Orc, DragonBoss | Enemy definitions and AI |
| item | Item, Weapon, Consumable, Inventory | Item types and storage |
| progression | Quest, QuestBook, Reward | Quest tracking and rewards |
| root | GameEngine | Main game loop and orchestration |

---

## 9.4 OOP Principles Summary

| Principle | Count | Implementation |
|-----------|-------|----------------|
| Interface | 1 | CombatMan |
| Abstract Class | 3 | Character, Enemy, Item |
| Inheritance | 3 hierarchies | 3 character subclasses, 3 enemy subclasses, 2 item subclasses |
| Polymorphism | 8 overrides | attack() x6, use() x2 |
| Method Overloading | 11 constructors | Character, Warrior, Archer, Wizard, Item, Weapon, Consumable, Goblin, Orc, DragonBoss, Reward |
| Encapsulation | 14 classes | All fields private with public accessors |
| Composition (HAS-A) | 6 | Character-Inventory, Character-QuestBook, Inventory-Item[], QuestBook-Quest[], Quest-Reward, Battle-Character/Enemy |
| Java Arrays | 4 | Item[10], Quest[5], Weapon[4], Consumable[3] |
| Exception Handling | 2 locations | GameEngine.readIntInput(), Battle.readInt() |

---

## 9.5 Control Structures Summary

| Structure | Count | Locations |
|-----------|-------|-----------|
| while loops | 3 | Game loop, battle loop, level-up |
| for loops | 7 | Array iteration in Inventory, QuestBook, GameEngine |
| switch | 3 | Menu, class selection, battle actions |
| if-else | 9+ | Enemy spawn, gold checks, special attacks, boundary checks |
| try-catch | 2 | Integer.parseInt() in GameEngine and Battle |

---

## 9.6 Game Data Tables

### Character Stats

| Class | Base HP | Attack Formula | Special | Chance | Multiplier |
|-------|---------|----------------|---------|--------|------------|
| Warrior | 150 | 15 + level x 3 | Critical Strike | 20% | 1.5x |
| Archer | 100 | 12 + level x 2 | Double Shot | 30% | +50% |
| Wizard | 80 | 20 + level x 4 | Arcane Blast | 25% | 2x |

### Enemy Stats

| Enemy | HP | ATK | EXP | Special | Chance | Effect |
|-------|-----|-----|-----|---------|--------|--------|
| Goblin | 40 | 8 | 25 | Dirty Punch | 20% | +5 dmg |
| Orc | 80 | 14 | 50 | Axe Swing | 15% | 1.8x dmg |
| Dragon | 300 | 30 | 200 | Fire Breath | 30% | +20 dmg |

### Quest Rewards

| Quest | Target | Kills | EXP | Gold | Item |
|-------|--------|-------|-----|------|------|
| Goblin Slayer | Goblin | 3 | 75 | 20 | Iron Shield |
| Orc Cleanup | Orc | 2 | 100 | 40 | Steel Axe |
| Dragon Hunter | Dragon | 1 | 200 | 100 | Dragon Scale Armor |

### Shop Items

| Item | Type | Stat | Price |
|------|------|------|-------|
| Iron Sword | Weapon | ATK +8 | 80 |
| Steel Blade | Weapon | ATK +12 | 120 |
| Enchanted Staff | Weapon | ATK +15 | 150 |
| War Hammer | Weapon | ATK +18 | 180 |
| Health Potion | Consumable | Heal 30 | 30 |
| Greater Health Potion | Consumable | Heal 60 | 60 |
| Supreme Elixir | Consumable | Heal 100 | 100 |

---

## 9.7 Glossary

| Term | Definition |
|------|------------|
| Abstract Class | A class that cannot be instantiated; serves as a template for subclasses |
| Composition | A HAS-A relationship where one class contains another as a field |
| Constructor Overloading | Multiple constructors with different parameter lists in the same class |
| Encapsulation | Bundling data and methods while restricting direct access to fields |
| Inheritance | An IS-A relationship where a subclass inherits from a superclass |
| Interface | A contract defining methods that implementing classes must provide |
| Method Overriding | A subclass provides a specific implementation of a parent method |
| Polymorphism | Different classes responding differently to the same method call |
| Polymorphic Binding | Runtime determination of which method to call based on object type |

---

## 9.8 Revision History

| Version | Date | Author | Changes |
|---------|------|--------|---------|
| 1.0 | July 2026 | Danuk | Initial SRS document |

---

*This SRS document was prepared for the Object-Oriented Programming Section 1 campus project.*
