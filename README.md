# Binary tree to string

A java utility for generating formatted strings from binary trees.

## Installation

To install the printer, simply add the `toString` method, and the `TreeString` class inside your `BinaryTree` class. These methods can be found in `BinaryTree.java`, where a full setup is provided.

```java
public class BinaryTree {
  private Node root;

  private class Node {
    Node left, right;
    int value;
  }

  // add the toString method
  @Override
  public String toString() {
    // get the content from BinaryTree.java
  }

  // add the TreeString class
  private class TreeString {
    // get the content from BinaryTree.java
  }
}
```

The code will only work if you have the same names as above, unless you change the code.

- `BinaryTree` can be called something else if you want to
- `Node` class has to be called Node
- `root` has to be called root and be of type `Node`
- `left` has to be called left and be of type `Node`
- `right` has to be called left and be of type `Node`
- `value` has to be called value, but does not be of type `int`. Since the printer uses `String.valueOf(node.value)`, the printer will probably work on any datatype. If it is an object, it will fall back to the objects `toString` method.

## Usage

The file contains an override of the `toString()` method, and a private class that contains all the logic. Simple call the tree's toString() function, and a representation of the tree is returned.

## Example print

```
                                +--[ 31  ]
                                |
                   +--[  50  ]--+
                   |
        +--[ 61 ]--+
        |          |
        |          |                        +--[ 89  ]--+
        |          |                        |           |
        |          |                        |           +--[ 99 ]
        |          |                        |
        |          |            +--[ 207 ]--+
        |          |            |
        |          +--[ 225  ]--+
        |                       |
        |                       +--[ 291 ]--+
        |                                   |
        |                                   +--[ 373 ]--+
        |                                               |
        |                                               +--[3513]
        |
[5767]--+
        |
        +--[6460]--+
                   |
                   |            +--[78788]--+
                   |            |           |
                   |            |           +--[91482]
                   |            |
                   +--[811201]--+
```
