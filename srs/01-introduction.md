# 1. Introduction

## 1.1 Purpose

This Software Requirements Specification (SRS) document provides a complete description of all functionalities, constraints, and design decisions for the **Kingdom Quest** project. It is prepared as part of the **Object-Oriented Programming Section 1** campus project requirement.

The purpose of this document is to:
- Define what the system does and how it behaves
- Serve as a reference for developers, evaluators, and instructors
- Document the OOP principles applied throughout the codebase
- Provide a basis for future extensions and maintenance

## 1.2 Scope

**Kingdom Quest** is a text-based, turn-based Role-Playing Game (RPG) built entirely in Java. It runs as a console application with no graphical interface.

### What the system does:

| Area | Description |
|------|-------------|
| Character Creation | Player creates a hero by choosing a name and one of three classes |
| Exploration | Player explores a fantasy wilderness, encountering random enemies |
| Combat | Turn-based battles with attack, item use, status check, and flee options |
| Inventory | Player manages up to 10 items — weapons and consumables |
| Quests | Kill-based quest objectives with progress tracking and rewards |
| Shop | Player buys weapons and potions using gold earned in battle |
| Rest | Player can rest at an inn to restore health for a gold fee |
| Progression | Player gains EXP, levels up, and grows stronger over time |

### What the system does NOT do:

- No graphical user interface (GUI)
- No multiplayer or network play
- No save/load game state
- No external file I/O for game data
- No use of Java Collections framework (uses standard arrays only)

### Project Statistics:

| Metric | Value |
|--------|-------|
| Language | Java |
| IDE | NetBeans |
| Total Classes | 18 |
| Packages | 5 |
| Estimated Lines of Code | ~650 |
| External Dependencies | None |

## 1.3 Definitions, Acronyms, Abbreviations

| Term | Definition |
|------|------------|
| SRS | Software Requirements Specification |
| OOP | Object-Oriented Programming |
| RPG | Role-Playing Game |
| HP | Health Points — the amount of damage a character can take before dying |
| ATK | Attack Power — the damage a character deals to enemies |
| EXP | Experience Points — earned from battles and quests, used to level up |
| Gold | In-game currency used to buy items and rest at the inn |
| CLI | Command-Line Interface — text-based input/output |
| GUI | Graphical User Interface — visual interface with windows and buttons |
| JRE | Java Runtime Environment |
| LOC | Lines of Code |
| UML | Unified Modeling Language — standard for visualizing system design |
| IS-A | Inheritance relationship — a subclass IS-A type of its parent class |
| HAS-A | Composition relationship — a class HAS-A reference to another class |
| Abstract Class | A class that cannot be instantiated; serves as a template |
| Interface | A contract defining methods that implementing classes must provide |
| Polymorphism | The ability for different classes to respond differently to the same method call |
| Encapsulation | Hiding internal state and requiring access through public methods |
| Constructor Overloading | Multiple constructors with different parameter lists |
| Method Overriding | A subclass provides a specific implementation of a parent class method |

## 1.4 References

| Reference | Description |
|-----------|-------------|
| Java SE 8+ Documentation | Official Java language and API documentation |
| NetBeans IDE | Development environment used for coding and building |
| Object-Oriented Programming Principles | Course materials for OOP Section 1 |
| README.md | Project overview and class architecture documentation |
| script.md | Video demonstration script |
| SRS_Document.md | Combined SRS document (single-file version) |

## 1.5 Overview

The remainder of this SRS document is organized as follows:

| Section | File | Content |
|---------|------|---------|
| 1 | `01-introduction.md` | This file — purpose, scope, definitions |
| 2 | `02-overview.md` | Product perspective, system functions, user characteristics |
| 3 | `03-functional-requirements.md` | All functional requirements with IDs and descriptions |
| 4 | `04-oop-design.md` | OOP principles implementation with code examples |
| 5 | `05-class-specifications.md` | Detailed specification for all 18 classes |
| 6 | `06-use-cases.md` | Use case descriptions with actors, flows, and preconditions |
| 7 | `07-non-functional.md` | Non-functional requirements |
| 8 | `08-testing.md` | Test cases and expected results |
| 9 | `09-appendix.md` | Class diagram, project structure, statistics |
