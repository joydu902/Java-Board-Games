# Markdown

Markdown is a plain-text file format. There are lots of programming tools that use Markdown, and it's useful and
easy to learn. Hash marks (the number sign) indicate headers. Asterisks indicate lists.

# Template

Use the following Code Smell template (copy and paste it at the end of this file and then edit it; don't include the "Begin template" or "End template" lines):

==== Begin template ====
## Code Smell: [Write the code smell name]

### Code Smell Category: [Write the code smell category name]

### List of classes and line numbers involved:

* [Write a class and list of line numbers, one class per asterisk, that describe the smell]

### Description:

[In your own words, explain how this particular code smells.]

### Solution:

[In your own words, explain how you might solve this code smell:
how would you refactor the code?]
==== End template ====

# List of code smells

## Code Smell: Alternative Classes with Different Interfaces

### Code Smell Category: Object-Orientation Abusers

### List of classes and line numbers involved:

* SequencerOrderList(the whole class)
* PickerOrderList(the whole class)

### Description:

These two classes perform identical functionality but have different method names.
Their attributes and methods are the same, except for the name Picker and Sequencer.

### Solution:
To fix Alternative Classes with Different Interfaces.
We can create a class called OrderList and delete class SequencerOrderList and class PickerOrderList,
then create two instances to represent SequencerOrderList and PickerOrderList.

## Code Smell: Alternative Classes with Different Interfaces

### Code Smell Category: Object-Orientation Abusers

### List of classes and line numbers involved:

* Picker(the whole class)
* Sequencer(the whole class)

### Description:

These two classes perform identical functionality but have different method names.
Their attributes and methods are the same, except for the name Picker and Sequencer.

### Solution:
To fix Alternative Classes with Different Interfaces.
We can create a class called Worker and delete class Sequencer and class Picker,
For now, we can just create two instances to represent Sequencer and Picker. If we want more functionalities,
we can create class Sequencer and class Picker to inherit Worker with more extend methods.

## Code Smell: Large Class

### Code Smell Category: Bloaters

### List of classes and line numbers involved:

* WareHouseManger(the whole class).

### Description:

In the WareHouseManager, there are too many attributes and methods in the class. Most of the attributes and methods
can be divided into two categories, picker and sequencer. Therefore, over time, class get bloated as the program grows,
since we need to create two attributes and two methods in order to satisfy each new functionality.

### Solution:
First I will extract interface from this class and called IWareHouseManager that contains some certain duplicate
methods, leave those methods unimplemented. Then create two subclass pickerWareHouseManager and
SequencerWareHouseManager to implement this interface.

## Code Smell: Dead Code

### Code Smell Category: Dispensables

### List of classes and line numbers involved:

* Worker(the whole class)

### Description:

The Worker class does not contain any methods and parameters. Also, this class is no longer used.

### Solution:

To fix Dead Code, I'd delete the whole Worker class, since this class is never used.

## Code Smell: Speculative Generality

### Code Smell Category: Dispensables

### List of classes and line numbers involved:

* GenericPathSoftware(the whole class)

### Description:

The GenericPathSoftware class does almost nothing, this class is created "just in case" to support anticipated future features that never get implemented. The GenericPathSoftware class is designed to rearrange the items in the
order list so that they are in picking order. Right now, It just print "-> Order list set by generic software."

### Solution:

To fix Speculative Generality, I'd move the method setOrder() from class GenericPathSoftware to class PickerOrderList.

## Code Smell: Switch Statements

### Code Smell Category: Object-Orientation Abusers

### List of classes and line numbers involved:

* WarehouseSimulation(line 53-89)

### Description:

There is a large sequence of if statements in WarehouseSimulation(line 53-89). It's hard to understand too many statements.

### Solution:

Move line53-64 to a separate new method called handle_Order(), line65-77 to a separate new method called handle_Picker(), line78-89 to a separate new method called handle_Sequencer(). Then replace the old code with a call to those 3 methods.


