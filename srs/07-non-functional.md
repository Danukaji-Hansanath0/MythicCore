# 7. Non-Functional Requirements

Non-functional requirements define the quality attributes, constraints, and standards that the Kingdom Quest system must meet.

---

## 7.1 Performance Requirements

| ID | Requirement | Metric | Target |
|----|-------------|--------|--------|
| NFR-001 | Input response time | Time from keypress to system response | < 100ms |
| NFR-002 | Memory usage | Maximum heap memory during gameplay | < 50MB |
| NFR-003 | Battle computation time | Time to process one attack turn | < 10ms |
| NFR-004 | Game startup time | Time from launch to main menu | < 2 seconds |

---

## 7.2 Reliability Requirements

| ID | Requirement | Description |
|----|-------------|-------------|
| NFR-005 | Crash prevention | System shall not crash on any valid or invalid user input |
| NFR-006 | Exception handling | All NumberFormatException cases shall be caught and handled |
| NFR-007 | Boundary checks | HP, EXP, gold, and inventory count shall never go below 0 |
| NFR-008 | Array bounds | All array access shall be validated before access |
| NFR-009 | Null safety | Null checks shall be performed before dereferencing objects |

### Reliability Measures

```
Input Validation Chain:
User Input → Scanner.nextLine() → Integer.parseInt()
                                      │
                                      ├── Success → Process normally
                                      │
                                      └── NumberFormatException
                                           │
                                           ▼
                                      catch block
                                           │
                                           ├── Display error message
                                           └── Return -1 (safe default)

HP/Stat Boundary Checks:
setHealth(value):
  value = Math.max(0, Math.min(maxHealth, value))
  // Ensures: 0 ≤ health ≤ maxHealth

setExp(value):
  exp = Math.max(0, value)
  // Ensures: exp ≥ 0
```

---

## 7.3 Usability Requirements

| ID | Requirement | Description |
|----|-------------|-------------|
| NFR-010 | Clear menus | All menus shall be numbered with descriptive labels |
| NFR-011 | Error messages | Error messages shall be informative and suggest corrective action |
| NFR-012 | Status visibility | Player stats shall be visible during battle without consuming a turn |
| NFR-013 | Progress feedback | Quest progress shall update immediately upon enemy defeat |
| NFR-014 | Consistent formatting | All displays shall use consistent text formatting |

### Usability Standards

| Element | Format | Example |
|---------|--------|---------|
| Menu Header | `=== MENU NAME ===` | `=== MAIN MENU ===` |
| Menu Option | `N. Description` | `1. Explore (Find Enemies)` |
| Status Display | `[Name] [Lv.X] HP: [cur]/[max] \| EXP: [cur]/[req]` | `Hero [Lv.3] HP: 125/200 \| EXP: 45/300` |
| Battle Header | `========================================` | Separates battle sections |
| Error Message | Plain text with context | `"Not enough gold!"` |
| Success Message | Positive feedback | `"Acquired: Steel Blade"` |

---

## 7.4 Maintainability Requirements

| ID | Requirement | Description |
|----|-------------|-------------|
| NFR-015 | Single Responsibility | Each class shall have one primary responsibility |
| NFR-016 | Naming conventions | All names shall follow Java conventions (camelCase, PascalCase) |
| NFR-017 | Method length | No method shall exceed 50 lines of code |
| NFR-018 | Field access | All instance fields shall be private/protected |
| NFR-019 | Documentation | Each class shall have a class-level Javadoc comment |

### Code Organization Standards

| Convention | Standard | Example |
|------------|----------|---------|
| Class names | PascalCase | `GameEngine`, `DragonBoss` |
| Method names | camelCase | `getBaseAttack()`, `gainExperience()` |
| Field names | camelCase | `maxHealth`, `equippedWeaponIndex` |
| Constants | UPPER_SNAKE_CASE | `MAX_SIZE`, `MAX_QUESTS` |
| Package names | lowercase | `model`, `enemy`, `item`, `core`, `progression` |
| Boolean getters | `is` prefix | `isAlive()`, `isCompleted()`, `isActive()` |
| Standard getters | `get` prefix | `getName()`, `getHealth()`, `getAttackPower()` |
| Standard setters | `set` prefix | `setName()`, `setHealth()`, `setActive()` |

---

## 7.5 Portability Requirements

| ID | Requirement | Description |
|----|-------------|-------------|
| NFR-020 | Platform independence | System shall run on Windows, Linux, and macOS |
| NFR-021 | Java version | System shall compile and run on JRE 8 or higher |
| NFR-022 | No external dependencies | System shall use only Java Standard Library |
| NFR-023 | Console compatibility | System shall work with standard 80-column terminals |

### Platform Compatibility

| Platform | JRE Required | Tested | Notes |
|----------|-------------|--------|-------|
| Windows 10/11 | JDK 8+ | Yes | Primary development platform |
| Linux (Ubuntu, etc.) | JDK 8+ | Yes | Fully compatible |
| macOS | JDK 8+ | Yes | Fully compatible |

---

## 7.6 Security Requirements

| ID | Requirement | Description |
|----|-------------|-------------|
| NFR-024 | No file access | System shall not read/write external files |
| NFR-025 | No network access | System shall not make network connections |
| NFR-026 | No secrets | System shall not contain hardcoded passwords or keys |
| NFR-027 | Input sanitization | All user input shall be validated before processing |

---

## 7.7 Scalability Considerations

| ID | Requirement | Description |
|----|-------------|-------------|
| NFR-028 | Extensible classes | New character classes can be added by extending Character |
| NFR-029 | Extensible enemies | New enemies can be added by extending Enemy |
| NFR-030 | Extensible items | New items can be added by extending Item |
| NFR-031 | Extensible quests | New quests can be added by creating Quest objects |

### Extension Points

| Extension Point | How to Extend |
|-----------------|---------------|
| New character class | Create class extending Character, override `getBaseAttack()` and `attack()` |
| New enemy type | Create class extending Enemy, override `attack()` |
| New weapon | Create Weapon object with name, description, and attackPower |
| New consumable | Create Consumable object with name, description, and healAmount |
| New quest | Create Quest object with name, description, target, kills, and reward |

---

## 7.8 Documentation Requirements

| ID | Requirement | Description |
|----|-------------|-------------|
| NFR-032 | README | Project shall have a comprehensive README.md |
| NFR-033 | SRS Document | Project shall have a Software Requirements Specification |
| NFR-034 | Class documentation | Each class shall have brief Javadoc comments |
| NFR-035 | Video script | Project shall have a demonstration video script |

---

## 7.9 Quality Attributes Summary

| Quality Attribute | Rating | Justification |
|-------------------|--------|---------------|
| **Reliability** | High | Exception handling on all inputs, boundary checks on all stats |
| **Usability** | Medium | Text-based UI with clear menus, but no GUI |
| **Performance** | High | In-memory operations, no I/O bottlenecks |
| **Maintainability** | High | Clean OOP design, single responsibility, private fields |
| **Portability** | High | Pure Java, no external dependencies |
| **Security** | High | No file/network access, no secrets |
| **Scalability** | Medium | Extensible via inheritance, but arrays are fixed-size |
| **Documentation** | High | README, SRS, video script, class comments |
