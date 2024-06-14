# DSA-proj
It is a program that takes a math expression written with parentheses and turns it into a prefix and postfix notation.

The special feature of this program is that it can handle variables like "A", "B", and so on.  Imagine these variables as boxes that can hold numbers. Initially, all the boxes are empty, but the program lets you change the values inside these boxes.

## Example Output:
### Enter Arithmetic.
Example arithmetic input: ```(A-B)/((C*D)+E)```

```
Enter Arithmetic in Infix Notation: (A-B)/((C*D)+E)
Infix  : (A-B)/((C*D)+E)
Prefix : /-AB+*CDE
Postfix: AB-CD*E+/

Binary Expression Tree: 
/
├── -
│  ├── A
│  └── B
└── +
   ├── *
   │  ├── C
   │  └── D
   └── E


Menu:
1. Update Operand.
2. Print Expression Tree.
3. Print Prefix/Postfix Notation
4. Exit

Enter Choice [1..4]: 
```

### 1.1 Update Operand
Updating ```A``` to ```123```
```
Enter Choice [1..4]: 1
Enter Operand to Update: A
Enter Value: 123
Updating A to 123 is success.

Menu:
1. Update Operand.
2. Print Expression Tree.
3. Print Prefix/Postfix Notation
4. Exit

Enter Choice [1..4]: 2
/
├── -
│  ├── 123
│  └── B
└── +
   ├── *
   │  ├── C
   │  └── D
   └── E
Menu:
1. Update Operand.
2. Print Expression Tree.
3. Print Prefix/Postfix Notation
4. Exit

Enter Choice [1..4]: 
```
### 1.2 Update Operand but Operand doesn't exist.
Finding operand that doesn't exist, for example: ```X```
```
Enter Choice [1..4]: 1
Enter Operand to Update: X
Key Not found

Menu:
1. Update Operand.
2. Print Expression Tree.
3. Print Prefix/Postfix Notation
4. Exit

Enter Choice [1..4]: 
```
Note: old overwritten operand won't work anymore.
```
Enter Choice [1..4]: 1
Enter Operand to Update: A
Key Not found

Menu:
1. Update Operand.
2. Print Expression Tree.
3. Print Prefix/Postfix Notation
4. Exit

Enter Choice [1..4]: 
```
### 1.3 Updating an already updated Operand
Changing an already overwritten operand ```123``` to this new value ```543```
```
Enter Choice [1..4]: 1
Enter Operand to Update: 123
Enter Value: 543
Updating 123 to 543 is success.

Menu:
1. Update Operand.
2. Print Expression Tree.
3. Print Prefix/Postfix Notation
4. Exit

Enter Choice [1..4]: 2
/
├── -
│  ├── 543
│  └── B
└── +
   ├── *
   │  ├── C
   │  └── D
   └── E
Menu:
1. Update Operand.
2. Print Expression Tree.
3. Print Prefix/Postfix Notation
4. Exit

Enter Choice [1..4]: 
```
### 2. Print Expression Tree.
```
Enter Choice [1..4]: 2
/
├── -
│  ├── 543
│  └── B
└── +
   ├── *
   │  ├── C
   │  └── D
   └── E
Menu:
1. Update Operand.
2. Print Expression Tree.
3. Print Prefix/Postfix Notation
4. Exit

Enter Choice [1..4]: 
```
### Printing Prefix and Postfix Notation
```
Enter Choice [1..4]: 3
Prefix from tree: /-543B+*CDE
Postfix from tree: 543B-CD*E+/

Menu:
1. Update Operand.
2. Print Expression Tree.
3. Print Prefix/Postfix Notation
4. Exit

Enter Choice [1..4]: 
```
### 4. Exit
```
Enter Choice [1..4]: 4
Exit.

Programmer:
Lorica, Edward Angel


Process finished with exit code 0
```