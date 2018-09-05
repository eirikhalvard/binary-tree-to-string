public class BinaryTree {
  private Node root;

  private class Node {
    Node left, right;
    int value;
  }

  @Override
  public String toString() {
    TreeString ts = new TreeString();
    return ts.solve();
  }

  private class TreeString {
    private int treeHeight;
    private int size;
    private int yHeight;
    private int xHeight;
    private int[] maxWordLength;
    private int[][] preorderHeights;
    private char[][] chars;


    private String solve() {
      this.treeHeight = height(root);
      this.size = size(root);
      if (size == 0) {
        return "(empty tree)";
      } else if (size == 1) {
        return "[" + String.valueOf(root.value) + "]";
      }

      this.maxWordLength = new int[treeHeight + 1];
      this.yHeight = computeYLength(root);
      this.xHeight = computeXLength();
      this.chars = new char[yHeight][xHeight];
      fillCharsWithWhitespace();
      traverseAndWrite();
      return charArrayToString();
    }

    private void fillCharsWithWhitespace() {
      for (int i = 0; i < yHeight; i++) {
        for (int j = 0; j < xHeight; j++) {
          chars[i][j] = ' ';
        }
      }
    }

    private int height(Node n) {
      if (n == null) {
        return -1;
      }
      return 1 + Math.max(height(n.left), height(n.right));
    }

    private int size(Node n) {
      if (n == null) return 0;
      return 1 + size(n.left) + size(n.right);
    }

    private void traverseAndWrite() {
      this.preorderHeights = new int[size][3];
      findPreorderHeights(root, 0);

      // find starting y-point
      int rootsLeftHeight = preorderHeights[0][1];
      int rootStartY = rootsLeftHeight == 0 ? 0 : rootsLeftHeight + 1;

      traverseAndWrite(root, 0, rootStartY, 0, new int[]{0});
    }

    private void traverseAndWrite(Node n, int depth, int startY, int startX, int[] iterator) {
      int num = preorderHeights[iterator[0]++][0];
      String nodeString = valueString(n, depth);
      writeToCharArray(nodeString, startY, startX);
      startX += nodeString.length();
      if (n.left != null) {
        int leftsRightHeight = preorderHeights[iterator[0]][2];
        int leftsInnerHeight = leftsRightHeight == 0 ? 1 : leftsRightHeight + 2;
        int leftStartY = (startY - 1) - leftsInnerHeight;
        writeConnectingLines(startY, leftStartY, startX);
        traverseAndWrite(n.left, depth + 1, leftStartY, startX + 5, iterator);
      }

      if (n.right != null) {
        int rightsLeftHeight = preorderHeights[iterator[0]][1];
        int rightsInnerHeight = rightsLeftHeight == 0 ? 1 : rightsLeftHeight + 2;
        int rightStartY = startY + 1 + rightsInnerHeight;
        writeConnectingLines(startY, rightStartY, startX);
        traverseAndWrite(n.right, depth + 1, rightStartY, startX + 5, iterator);
      }
    }

    private void writeConnectingLines(int startY, int endY, int startX) {
      writeToCharArray("--+", startY, startX);
      int diff = endY - startY;
      int increment = diff > 0 ? 1 : -1;
      if (diff > 0) {
        for (int i = startY + 1; i < endY; i++) {
          writeToCharArray("|", i, startX + 2);
        }
      } else {
        for (int i = endY + 1; i < startY; i++) {
          writeToCharArray("|", i, startX + 2);
        }
      }
      writeToCharArray("+--", endY, startX + 2);

    }

    private int[] findPreorderHeights(Node n, int h) {
      if (n.left == null && n.right == null) {
        preorderHeights[h][0] = 1;
        return new int[]{preorderHeights[h][0], h};
      } else if (n.right == null) {
        int[] resultLeft = findPreorderHeights(n.left, h + 1);
        preorderHeights[h][0] = 2 + resultLeft[0];
        preorderHeights[h][1] = resultLeft[0];
        return new int[]{preorderHeights[h][0], resultLeft[1]};
      } else if (n.left == null) {
        int[] resultRight = findPreorderHeights(n.right, h + 1);
        preorderHeights[h][0] = 2 + resultRight[0];
        preorderHeights[h][2] = resultRight[0];
        return new int[]{preorderHeights[h][0], resultRight[1]};
      } else {
        int[] resultLeft = findPreorderHeights(n.left, h + 1);
        int[] resultRight = findPreorderHeights(n.right, resultLeft[1] + 1);
        preorderHeights[h][0] = 3 + resultLeft[0] + resultRight[0];
        preorderHeights[h][1] = resultLeft[0];
        preorderHeights[h][2] = resultRight[0];
        return new int[]{preorderHeights[h][0], resultRight[1]};
      }
    }

    private void writeToCharArray(String line, int y, int x) {
      if (line.length() + x >= xHeight) {
        new Exception("Line was to long to write");
      }

      for (int i = 0; i < line.length(); i++) {
        chars[y][x + i] = line.charAt(i);
      }
    }

    private String charArrayToString() {
      String result = "";
      for (int i = 0; i < chars.length; i++) {
        for (int j = 0; j < chars[0].length; j++) {
          result += String.valueOf(chars[i][j]);
        }
        result += "\n";
      }
      return result.substring(0, result.length() - 1); // remove last newline
    }

    private int computeYLength(Node n) {
      if (n.left == null && n.right == null) {
        return 1;
      } else if (n.right == null) {
        return 2 + computeYLength(n.left);
      } else if (n.left == null) {
        return 2 + computeYLength(n.right);
      } else {
        return 3 + computeYLength(n.left) + computeYLength(n.right);
      }
    }

    private int computeXLength() {
      computeMaxWordLength(root, 0);
      int sum = 0;
      for (int i = 0; i < treeHeight; i++) {
        sum += "[".length()
          + maxWordLength[i]
          + "]".length()
          + "--+--".length();
      }
      sum +=  "[".length() + maxWordLength[treeHeight] + "]".length();
      return sum;
    }

    private void computeMaxWordLength(Node n, int depth) {
      if (n == null) return;
      int nodeStringLength = n.toString().length();
      if (nodeStringLength > maxWordLength[depth]) {
        maxWordLength[depth] = nodeStringLength;
      }
      computeMaxWordLength(n.left, depth + 1);
      computeMaxWordLength(n.right, depth + 1);
    }

    private String valueString(Node n, int depth) {
      String result = "";
      int totalCount = maxWordLength[depth] - n.toString().length();
      int leftCount = totalCount / 2;
      int rightCount = totalCount - leftCount;
      String leftPadding = repeat(" ", leftCount);
      String rightPadding = repeat(" ", rightCount);

      return "[" + leftPadding + String.valueOf(n.value) + rightPadding + "]";
    }

    private String repeat(String s, int count) {
      String result = "";
      for (int i = 0; i < count; i++) {
        result += s;
      }
      return result;
    }
  }
}
