# Fuyuki's Functional Furniture

## Table of Contents
 - Overview
 - Quick Start Guide
 - Technologies
 - ERD
 - Contributors

## Overview
Have you ever found buying furniture to be a hassle? Do you have trouble leaving the house or getting to a store? At Fuyuki's Functional Furniture we have come up with an easy-to-use application, so you can shop for furniture from the comfort of your own home.

## Quick Start Guide
This app is easily navigable with the use of colon commands. Any other text will be validated and inserted into the selected control (if supported).

### Basic Navigation
`:<` and `:>` are used to go to the previous and next control on a screen respectively. Multiple `<` or `>` may be used within the same command to quickly navigate between controls. \
`^` and `v` When a table is selected, these will select the previous and next row of the table respectively. Multiple `^` or `v` may be used to cycle through multiple rows at once. Please note that these commands do not start with a colon.  
`:t` is used to toggle indices on controls. This can help if you're new to the application, and you do not know in what order the controls will be traversed. \
`<Enter>` Pressing Enter on an otherwise blank like will activate the currently selected control.

### Screen Navigation
These commands may be used on any screen for quick navigation. \
`:m` Go back to the main menu. \
`:h` View the order history for the current user. \
`:c` View the cart for the current user. \
`:l` Logout and return to the login screen.

## Features
- Users may create an account and sign in.
- Users may browse available items, sorted by category.
- Users may order single items, or store multiple items in a cart to order later.
- Users may view a list of their past orders, with the ability to see details for a particular order.
- Users may view the current contents of their cart.
- Managers may view inventory for any of the stores they manage.
- Managers may update the quantity of any item in their store(s).

## Technologies
### Java Core
 - Java 8
 - PostgreSQL JDBC Driver
 - Mockito Core
 - Mockito Inline
 - JUnit
 - Jetbrains Annotations
### Data management
 - Docker
 - PostgreSQL
### Code management
 - Maven
 - Git
 - Github hosting

## Entity Relationship Diagram
![](https://raw.githubusercontent.com/220808-Java-React-Enterprise/Nathan-P0/main/P0%20ERD.png) 

## Contributors
- Nathan Gilbert
