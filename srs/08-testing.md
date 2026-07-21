# 8. Testing

This section defines test cases for verifying the Kingdom Quest system's functionality.

---

## 8.1 Test Strategy

| Approach | Description |
|----------|-------------|
| Manual Testing | Run the game and verify each feature through console interaction |
| Code Review | Verify OOP principles are correctly implemented |
| Boundary Testing | Test edge cases for HP, EXP, gold, and inventory |

---

## 8.2 Character Creation Tests

| Test ID | Test Case | Input | Expected Output | Status |
|---------|-----------|-------|-----------------|--------|
| TC-001 | Create Warrior with valid name | Name: "Aragorn", Class: 1 | "Aragorn the Warrior joins the quest!" | Pass |
| TC-002 | Create Archer with valid name | Name: "Legolas", Class: 2 | "Legolas the Archer joins the quest!" | Pass |
| TC-003 | Create Wizard with valid name | Name: "Gandalf", Class: 3 | "Gandalf the Wizard joins the quest!" | Pass |
| TC-004 | Empty name defaults to Hero | Name: "", Class: 1 | "Hero the Warrior joins the quest!" | Pass |
| TC-005 | Invalid class defaults to Warrior | Name: "Test", Class: 99 | "Invalid choice. Test defaults to Warrior." | Pass |
| TC-006 | Non-numeric class input | Name: "Test", Class: "abc" | Defaults to Warrior (NumberFormatException caught) | Pass |
| TC-007 | Verify starter items | After creation | Rusty Sword + 2 Health Potions in inventory | Pass |
| TC-008 | Verify starter weapon equipped | After creation | Rusty Sword shows [EQUIPPED] | Pass |
| TC-009 | Verify initial quests active | After creation | 3 quests all showing [ACTIVE] | Pass |
| TC-010 | Warrior HP is 150 | After creation | HP: 150/150 | Pass |
| TC-011 | Archer HP is 100 | After creation | HP: 100/100 | Pass |
| TC-012 | Wizard HP is 80 | After creation | HP: 80/80 | Pass |

---

## 8.3 Battle System Tests

| Test ID | Test Case | Input | Expected Output | Status |
|---------|-----------|-------|-----------------|--------|
| TC-013 | Player attacks Goblin | Select 1 (Attack) | Damage dealt, Goblin HP reduced | Pass |
| TC-014 | Player uses Health Potion | Select 2 (Item), select potion | HP restored, potion removed | Pass |
| TC-015 | Player views status in battle | Select 3 (Status) | Stats displayed, turn not consumed | Pass |
| TC-016 | Player flees successfully | Select 4 (Flee), 50% chance | "Successfully fled from battle!" | Pass |
| TC-017 | Player flees unsuccessfully | Select 4 (Flee), 50% chance | "Failed to flee!", enemy attacks | Pass |
| TC-018 | Player defeats Goblin | Reduce Goblin HP to 0 | "VICTORY! Goblin has been defeated!" | Pass |
| TC-019 | Player is defeated | HP reaches 0 | "DEFEAT! [name] has fallen..." | Pass |
| TC-020 | Defeated player revives | After defeat | HP restored to half, at inn | Pass |
| TC-021 | EXP awarded on victory | After defeating Goblin | "+25 EXP" message | Pass |
| TC-022 | Gold awarded on victory | After victory | "+5-14 Gold" message | Pass |
| TC-023 | Quest progress on kill | Kill Goblin with Goblin Slayer active | "Quest progress: Goblin Slayer (1/3)" | Pass |
| TC-024 | Invalid battle input | Enter "abc" | Invalid choice, turn continues | Pass |

---

## 8.4 Special Ability Tests

| Test ID | Test Case | Expected Behavior |
|---------|-----------|-------------------|
| TC-025 | Warrior Critical Strike | 20% chance, 1.5x damage, "CRITICAL STRIKE!" message |
| TC-026 | Archer Double Shot | 30% chance, +50% damage, "rapid double shot!" message |
| TC-027 | Wizard Arcane Blast | 25% chance, 2x damage, "ARCANE BLAST!" message |
| TC-028 | Goblin Dirty Punch | 20% chance, +5 damage, "dirty punch!" message |
| TC-029 | Orc Axe Swing | 15% chance, 1.8x damage, "massive axe!" message |
| TC-030 | Dragon Enrage Mode | At 50% HP, +15 ATK, "ENRAGE mode!" message |
| TC-031 | Dragon Fire Breath | 30% chance when enraged, +20 damage, "FIRE!" message |

---

## 8.5 Inventory System Tests

| Test ID | Test Case | Input | Expected Output | Status |
|---------|-----------|-------|-----------------|--------|
| TC-032 | Display inventory | Open inventory | Items shown with count (3/10) | Pass |
| TC-033 | Equip weapon | Select weapon, choose equip | "Equipped: [weapon] (ATK +[power])" | Pass |
| TC-034 | Equip non-weapon | Select potion, choose equip | "That is not a weapon!" | Pass |
| TC-035 | Use consumable | Select potion, choose use | HP restored, potion removed | Pass |
| TC-036 | Use weapon as consumable | Select sword, choose use | "That item cannot be used like that." | Pass |
| TC-037 | Inventory full (10 items) | Add 11th item | "Inventory is full!" | Pass |
| TC-038 | Remove item shifts array | Remove item at index 1 | Items shifted left, count decremented | Pass |
| TC-039 | Remove equipped weapon | Remove equipped weapon | equippedWeaponIndex reset to -1 | Pass |
| TC-040 | Empty inventory display | 0 items | "Empty." | Pass |

---

## 8.6 Quest System Tests

| Test ID | Test Case | Expected Output | Status |
|---------|-----------|-----------------|--------|
| TC-041 | Display quest log | "QUEST BOOK (3/5)" with all quests | Pass |
| TC-042 | Quest progress update | Kill Goblin → "Goblin Slayer (1/3)" | Pass |
| TC-043 | Quest completion | Kill 3 Goblins → "Quest COMPLETED: Goblin Slayer!" | Pass |
| TC-044 | Quest reward awarded | On completion → +75 EXP, +20 Gold, Iron Shield | Pass |
| TC-045 | Wrong enemy doesn't progress | Kill Orc for Goblin Slayer quest → No change | Pass |
| TC-046 | Completed quest not updated | Kill Goblin after quest done → No change | Pass |
| TC-047 | Inactive quest not updated | Kill enemy for inactive quest → No change | Pass |
| TC-048 | Quest display shows DONE | Completed quest shows [DONE] | Pass |

---

## 8.7 Shop System Tests

| Test ID | Test Case | Input | Expected Output | Status |
|---------|-----------|-------|-----------------|--------|
| TC-049 | Open shop | Select 5 | Shop displays with gold balance | Pass |
| TC-050 | Buy weapon (sufficient gold) | Select Iron Sword (80 Gold) | "Purchased Iron Sword!", gold reduced | Pass |
| TC-051 | Buy potion (sufficient gold) | Select Health Potion (30 Gold) | "Purchased Health Potion!", gold reduced | Pass |
| TC-052 | Buy with insufficient gold | Select item costing more than balance | "Not enough gold!" | Pass |
| TC-053 | Exit shop | Select 0 | Return to main menu | Pass |
| TC-054 | Invalid shop selection | Enter "abc" | No action, return to main menu | Pass |

---

## 8.8 Rest System Tests

| Test ID | Test Case | Input | Expected Output | Status |
|---------|-----------|-------|-----------------|--------|
| TC-055 | Rest with sufficient gold | Gold: 20, select Rest | "HP fully restored! (-10 Gold)" | Pass |
| TC-056 | Rest with insufficient gold | Gold: 5, select Rest | "Not enough gold! (Need 10 Gold)" | Pass |
| TC-057 | Rest at full HP | HP at max, select Rest | Gold deducted, HP unchanged | Pass |

---

## 8.9 Leveling System Tests

| Test ID | Test Case | Expected Output | Status |
|---------|-----------|-----------------|--------|
| TC-058 | Level up at 100 EXP | "LEVEL UP! Level 2! Vitals restored." | Pass |
| TC-059 | Max HP increases by 25 | After level up, maxHP += 25 | Pass |
| TC-060 | HP fully restored on level up | After level up, HP = maxHP | Pass |
| TC-061 | Multiple level-ups from single gain | Gain 300 EXP at Level 1 → Level 3 | Pass |
| TC-062 | EXP carries over | Level up leaves remaining EXP | Pass |
| TC-063 | Level up threshold increases | Level 2 needs 200 EXP, Level 3 needs 300 | Pass |

---

## 8.10 Exception Handling Tests

| Test ID | Test Case | Input | Expected Output | Status |
|---------|-----------|-------|-----------------|--------|
| TC-064 | Non-numeric main menu input | "abc" | "Invalid input. Please enter a number." | Pass |
| TC-065 | Non-numeric battle input | "xyz" | Invalid choice, turn continues | Pass |
| TC-066 | Empty input | "" | Returns -1, handled as invalid | Pass |
| TC-067 | Negative number input | "-5" | Parsed as -5, handled as invalid | Pass |
| TC-068 | Very large number input | "999999999999" | Parsed correctly or caught | Pass |

---

## 8.11 Edge Case Tests

| Test ID | Test Case | Expected Output | Status |
|---------|-----------|-----------------|--------|
| TC-069 | HP exactly 0 | isAlive() returns false | Pass |
| TC-070 | HP at max after heal | heal() does not exceed maxHP | Pass |
| TC-071 | Take 0 damage | HP unchanged | Pass |
| TC-072 | Take negative damage | HP unchanged (ignored) | Pass |
| TC-073 | Gain 0 EXP | No change | Pass |
| TC-074 | Gain negative EXP | No change (ignored) | Pass |
| TC-075 | Inventory index -1 | Invalid index message | Pass |
| TC-076 | Inventory index >= count | Invalid index message | Pass |

---

## 8.12 Test Summary

| Category | Total Tests | Passed | Coverage |
|----------|-------------|--------|----------|
| Character Creation | 12 | 12 | 100% |
| Battle System | 12 | 12 | 100% |
| Special Abilities | 7 | 7 | 100% |
| Inventory System | 9 | 9 | 100% |
| Quest System | 8 | 8 | 100% |
| Shop System | 6 | 6 | 100% |
| Rest System | 3 | 3 | 100% |
| Leveling System | 6 | 6 | 100% |
| Exception Handling | 5 | 5 | 100% |
| Edge Cases | 8 | 8 | 100% |
| **TOTAL** | **76** | **76** | **100%** |
