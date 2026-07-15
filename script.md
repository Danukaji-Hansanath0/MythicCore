# Kingdom Quest - Video Demonstration Script

**Duration**: 5-10 minutes

---

## 1. Introduction (0:00 - 0:30)

**On screen**: Project opened in NetBeans/IDE, showing package structure

**Narrator**:
"Welcome to Kingdom Quest, a turn-based console RPG built entirely in Java. This project demonstrates core Object-Oriented Programming principles across 18 classes organized into 5 packages. In this video, I'll walk through the running application, then explain the architectural design behind it."

---

## 2. Compile & Run (0:30 - 1:15)

**On screen**: Terminal/CMD window

**Narrator**:
"Let's compile and run. From the project root, we compile with: `javac -d src -sourcepath src src/GameEngine.java` then run with `java -cp src GameEngine`."

*[Type commands, show clean compile with no errors]*

"The game compiles cleanly. Now let's play through it."

---

## 3. Character Creation (1:15 - 2:00)

**On screen**: Game running, character creation menu visible

**Narrator**:
"The game starts with a banner and character creation. We enter a name, then choose from three archetypes: Warrior with 150 HP and strong melee attacks, Archer with 100 HP and rapid double shots, or Wizard with 80 HP and devastating magic."

*[Enter name "Hero", select Warrior]*

"I'll pick the Warrior. The game initializes starter items — a Rusty Sword and two Health Potions — and activates three quests: Goblin Slayer, Orc Cleanup, and Dragon Hunter."

---

## 4. Player Status & Inventory (2:00 - 3:00)

**On screen**: Navigate through status and inventory menus

**Narrator**:
"Let's look at the main menu. We have Explore, Status, Inventory, Quest Log, Shop, Rest, and Quit."

*[Select "View Status"]*

"Status shows our character stats — level, HP, EXP progression, class, gold, and equipped weapon."

*[Select "Inventory"]*

"Inventory displays our items with an equipped marker. We can equip weapons or use consumables. Items are stored in a fixed-size array, demonstrating standard Java Arrays in a HAS-A relationship."

---

## 5. Battle Simulation (3:00 - 5:30)

**On screen**: Explore into battle with a Goblin

**Narrator**:
"Exploring the wilderness generates random enemies. Let's fight a Goblin."

*[Select Explore, encounter Goblin]*

"The battle loop begins. Each turn we can Attack, Use Item, View Status, or Flee."

*[Attack the Goblin]*

"Our Warrior attacks for calculated damage. The attack method demonstrates polymorphism — each class overrides it. The Warrior has a 20% chance for a critical strike dealing 1.5x damage."

*[Goblin attacks back]*

"The enemy attacks back, reducing our HP."

*[Use a Health Potion]*

"We can use a consumable mid-battle to heal. The Consumable's use() method takes a CombatMan target and restores health."

*[Kill the Goblin]*

"The Goblin is defeated. We gain EXP and gold. The EXP system has a while-loop that handles automatic leveling — when EXP crosses 100 times the current level, we level up, increasing max health by 25."

---

## 6. Leveling & Quest System (5:30 - 7:00)

**On screen**: Show level up, then quest log

**Narrator**:
"After a few battles, we level up. Notice the leveling formula: each level requires `level * 100` EXP. The while-loop processes multiple level-ups if enough EXP was earned at once."

*[Open Quest Log]*

"The quest system tracks kills. Each quest targets a specific enemy type — Goblin, Orc, or Dragon. The QuestBook holds an array of Quest objects. When we kill an enemy, `notifyKill()` iterates through active quests and updates progress via `addKill()`."

*[Complete a quest]*

"Completing a quest grants EXP, gold, and a reward item. This demonstrates the Quest HAS-A Reward composition."

---

## 7. Shop & Rest (7:00 - 8:00)

**On screen**: Open shop, buy items, rest at inn

**Narrator**:
"The shop sells weapons and potions displayed from arrays. Selecting a weapon checks gold balance, deducts the cost, and adds the item to inventory."

*[Buy an Iron Sword]*

"We equip it. Notice the Attack Power bonus now applies to our damage calculation in the attack() method."

*[Rest at Inn]*

"Resting at the inn fully restores HP for a gold fee — a simple if-else with gold comparison."

---

## 8. OOP Architecture Overview (8:00 - 9:30)

**On screen**: Show the class diagram from README.md, then scroll through key source files

**Narrator**:
"Now let's examine the OOP architecture. We have 18 classes across 5 packages."

**Interface — CombatMan**:
*[Open CombatMan.java]*
"CombatMan defines the contract for all combat participants — attack, takeDmg, isAlive, getName, getHealth, getMaxHealth. Both Character and Enemy implement this interface."

**Abstract Classes**:
*[Open Character.java, Enemy.java, Item.java]*
"Three abstract base classes: Character with inventory and quest book composition; Enemy with stat tracking and EXP rewards; Item with use() and getType() abstract methods."

**Inheritance Hierarchies**:
*[Open Warrior.java]*
"Two clear IS-A hierarchies: Warrior, Archer, Wizard extend Character; Goblin, Orc, DragonBoss extend Enemy. Each overrides attack() with unique behavior."

**Method Overloading**:
"Every class with constructors uses overloading with `this()` chaining. For example, Character has two constructors — one accepting just a name, another accepting name and maxHealth."

**Encapsulation**:
"All fields are private or protected, exposed only through public getters and setters — proper encapsulation throughout."

**HAS-A Relationships**:
"Character contains an Inventory and a QuestBook. Inventory contains an Item array. QuestBook contains a Quest array. Quest contains a Reward."

**Exception Handling**:
*[Open GameEngine.java, scroll to readIntInput()]*
"All user input is wrapped in try-catch blocks handling NumberFormatException, preventing crashes from invalid input."

---

## 9. Conclusion (9:30 - 10:00)

**On screen**: Back to terminal showing final stats

**Narrator**:
"To summarize, Kingdom Quest demonstrates: one interface, three abstract classes, two inheritance hierarchies with six concrete subclasses, method overriding and overloading, encapsulation, composition through multi-level HAS-A relationships, standard Java Arrays, and robust exception handling — all within 18 classes and approximately 650 lines of runnable Java code."

"Thanks for watching."

---

## Recording Tips

- Use OBS Studio or similar screen recorder
- Show the terminal at ~120x40 characters minimum
- When showing code, zoom to readable font size (16-18pt)
- Keep mouse movements deliberate and slow
- Speak clearly, pace at ~150 words per minute
- Have the game already compiled before recording
- For the code walkthrough section, have IDE already open to the right files
