package finalsProj;

import java.io.PrintStream;
import java.util.*;

// Coded in jdk21
public class finalsProj {

    static Scanner scan = new Scanner(System.in);

    static BinaryTreeModel tree;

    public static void main(String[] args) {
        System.out.print("Enter Arithmetic in Infix Notation: ");
        String input = scan.nextLine();

        /*
        Sample Input:

        (A-B)/((C*D)+E)
        A/(B-C*D)
        */

        // Tree Initialization
        tree = makeTree(postfix(input));

        // Printing prefix postfix
        System.out.println("Infix  : " + input);
        System.out.println("Prefix : " + prefixFromTree(tree));
        System.out.println("Postfix: " + postfixFromTree(tree));
        System.out.println();

        // Printing Binary Expression Tree
        System.out.println("Binary Expression Tree: ");
        new BinaryTreePrinter(tree).print(System.out);
        System.out.println();


        System.out.println();

        int menu = 0;
        while (menu != 4) {
            System.out.println();
            System.out.println("""
                Menu:
                1. Update Operand.
                2. Print Expression Tree.
                3. Print Prefix/Postfix Notation
                4. Exit
                """);
            System.out.print("Enter Choice [1..4]: ");

            if (scan.hasNextInt()) {
                menu = scan.nextInt();
                scan.nextLine();

                switch (menu) {
                    case 1 -> {
                        System.out.print("Enter Operand to Update: ");
                        String oprnd = scan.next();

                        if (isOperator(oprnd)) {
                            System.out.println("Operator not allowed");
                            break;
                        }
                        if (!searchInTree(tree, oprnd)) {
                            System.out.println("Key Not found");
                            break;
                        }

                        System.out.print("Enter Value: ");
                        if (scan.hasNextInt()) {
                            String valu = scan.next();
                            scan.nextLine();
                            updateOperandInTree(tree, oprnd, valu);
                            System.out.println("Updating " + oprnd + " to " + valu + " is success.");
                        } else {
                            scan.next();
                            scan.nextLine();
                            System.out.println("Input value must be an Integer");

                        }
                    }
                    case 2 -> new BinaryTreePrinter(tree).print(System.out);
                    case 3 -> {
                        System.out.println("Prefix from tree: " + prefixFromTree(tree));
                        System.out.println("Postfix from tree: " + postfixFromTree(tree));
                    }
                    case 4 -> {
                        System.out.println("""
                                Exit.
                                
                                Programmer:
                                Lorica, Edward Angel
                                """);
                    }
                    default -> System.out.println("Invalid Input for Menu.");
                }
            } else {
                scan.next();
                scan.nextLine();
                System.out.println("Input value must be an Integer");
            }
        }
    }

    /// Identify if Input is Operator
    private static boolean isOperator(String input) {
        char[] OPERATORS = {'*', '/', '+', '-'};
        for (char operator : OPERATORS) {
            if (Character.toString(operator).equals(input))
                return true;

        }
        return false;
    }

    ///  Postfix Function - From Input  ////////////////////////////////////////////////////////////////////////////////

    public static String postfix(String inputString) {
        String[] operands = {"+", "-", "*", "/", "^", ")", "("};

        ArrayList<String> inputContainer = new ArrayList<>();
        ArrayList<String> stack = new ArrayList<>();
        ArrayList<String> expression = new ArrayList<>();

        for (int i = 0; i < inputString.length(); i++) {

            if (inputString.charAt(i) != ' ') {

                inputContainer.addLast(String.valueOf(inputString.charAt(i)));
            }
        }

        for (String input : inputContainer) {
            boolean isOperand = false;
            boolean isMD = false;
            boolean isAS = false;
            boolean isP = false;
            boolean isE = false;
            for (String operand : operands) {
                if (input.equals(operand)) {
                    isOperand = true;

                    switch (operand) {
                        case "+", "-" -> isAS = true;
                        case "*", "/" -> isMD = true;
                        case "^" -> isE = true;
                        case "(", ")" -> isP = true;
                    }
                    break;
                }
            }

            if (isOperand) //  + - / *
            {
                if (stack.isEmpty()) {
                    stack.addFirst(input);
                } else {
                    if (isP) {
                        if (input.equals("("))
                            stack.addFirst(input);

                        else {
                            while (!(stack.getFirst().equals("("))) {
                                expression.addLast(stack.getFirst());
                                stack.removeFirst();
                            }
                            stack.removeFirst();
                        }
                    } else if (isE)
                        stack.addFirst(input);

                    else if (isMD) {
                        if (stack.getFirst().equals("*") || stack.getFirst().equals("/")) {
                            expression.addLast(stack.getFirst());
                            stack.removeFirst();
                        }

                        stack.addFirst(input);

                    } else if (isAS) {
                        if (stack.getFirst().equals("*") || stack.getFirst().equals("/")) {
                            while (!(stack.getFirst().equals("+") || stack.getFirst().equals("-"))) {
                                expression.addLast(stack.getFirst());
                                stack.removeFirst();

                                if (stack.isEmpty())
                                    break;

                            }
                            if (!stack.isEmpty()) {
                                expression.addLast(stack.getFirst());
                                stack.removeFirst();

                            }
                            stack.addFirst(input);

                        } else if (stack.getFirst().equals("(")) {
                            stack.addFirst(input);
                        } else {
                            expression.addLast(stack.getFirst());
                            stack.removeFirst();
                            stack.addFirst(input);
                        }
                    }
                }
            } else {
                expression.addLast(input);
            }
            //STEP BY STEP
            //System.out.println(input + "\t\t" + stack + "\t\t" + expression);
        }
        while (!(stack.isEmpty())) {
            expression.addLast(stack.getFirst());
            stack.removeFirst();
            //STEP BY STEP
            //System.out.println("   " + "\t\t" + stack + "\t\t" + expression);
        }

        StringBuilder output = new StringBuilder();
        for (String s : expression)
            output.append(s);

        return output.toString();
    }

    ///  Binary Tree Functions   ///////////////////////////////////////////////////////////////////////////////////////

    /// Making Binary Expression Tree from postfix string
    public static BinaryTreeModel makeTree(String postfix) {
        Stack<BinaryTreeModel> stack = new Stack<>();

        for (int i = 0; i < postfix.length(); i++) {
            // Check if it is OPERATOR
            boolean isOperator = isOperator(Character.toString(postfix.charAt(i)));

            BinaryTreeModel selectedElement = new BinaryTreeModel(postfix.charAt(i));
            if (isOperator) // additional function for operator
            {
                BinaryTreeModel firstpop = stack.pop();
                BinaryTreeModel secondpop = stack.pop();

                selectedElement.setRight(firstpop);
                selectedElement.setLeft(secondpop);
            }

            // function for Operand
            stack.push(selectedElement);
        }
        return stack.pop();
    }

    /// Prefix Function - From Binary Tree
    public static String prefixFromTree(BinaryTreeModel tree) {
        StringBuilder output = new StringBuilder();

        output.append(tree.getValue());

        if (tree.getLeft() != null) {
            output.append(prefixFromTree(tree.getLeft()));
        }
        if (tree.getRight() != null) {
            output.append(prefixFromTree(tree.getRight()));
        }

        return output.toString();
    }

    /// Postfix Function - From Binary Tree
    public static String postfixFromTree(BinaryTreeModel tree) {
        StringBuilder output = new StringBuilder();

        if (tree.getLeft() != null) {
            output.append(postfixFromTree(tree.getLeft()));
        }
        if (tree.getRight() != null) {
            output.append(postfixFromTree(tree.getRight()));
        }
        output.append(tree.getValue());

        return output.toString();
    }

    /// Update Operand in Binary Tree
    public static void updateOperandInTree(BinaryTreeModel tree, String Search, String Value) {
        if (tree.getValue().toString().equals(Search.toUpperCase()))
            tree.setValue(Value);

        else {
            if (tree.getLeft() != null)
                updateOperandInTree(tree.getLeft(), Search.toUpperCase(), Value);

            if (tree.getRight() != null)
                updateOperandInTree(tree.getRight(), Search.toUpperCase(), Value);
        }
    }

    /// Search Key in Binary Tree
    public static boolean searchInTree(BinaryTreeModel tree, String Search) {
        if (tree.getValue().toString().equals(Search.toUpperCase()))
            return true;

        else {
            if (tree.getLeft() != null) {
                boolean output = searchInTree(tree.getLeft(), Search.toUpperCase());
                if (output)
                    return true;
            }


            if (tree.getRight() != null)
                return searchInTree(tree.getRight(), Search.toUpperCase());

        }
        return false;
    }

    ///  Binary Tree Model   ///////////////////////////////////////////////////////////////////////////////////////////
    public static class BinaryTreeModel {

        private Object value;
        private BinaryTreeModel left;
        private BinaryTreeModel right;

        public BinaryTreeModel(Object value) {
            this.value = value;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }

        public BinaryTreeModel getLeft() {
            return left;
        }

        public void setLeft(BinaryTreeModel left) {
            this.left = left;
        }

        public BinaryTreeModel getRight() {
            return right;
        }

        public void setRight(BinaryTreeModel right) {
            this.right = right;
        }
    }

    /// Binary Tree Printer
    public static class BinaryTreePrinter {

        private final BinaryTreeModel tree;

        public BinaryTreePrinter(BinaryTreeModel tree) {
            this.tree = tree;
        }

        private String traversePreOrder(BinaryTreeModel root) {

            if (root == null) {
                return "";
            }

            StringBuilder sb = new StringBuilder();
            sb.append(root.getValue());

            String pointerRight = "└── ";
            String pointerLeft = (root.getRight() != null) ? "├── " : "└── ";

            traverseNodes(sb, "", pointerLeft, root.getLeft(), root.getRight() != null);
            traverseNodes(sb, "", pointerRight, root.getRight(), false);

            return sb.toString();
        }

        private void traverseNodes(StringBuilder sb, String padding, String pointer, BinaryTreeModel node,
                                   boolean hasRightSibling) {

            if (node != null) {

                sb.append("\n");
                sb.append(padding);
                sb.append(pointer);
                sb.append(node.getValue());

                StringBuilder paddingBuilder = new StringBuilder(padding);
                if (hasRightSibling) {
                    paddingBuilder.append("│  ");
                } else {
                    paddingBuilder.append("   ");
                }

                String paddingForBoth = paddingBuilder.toString();
                String pointerRight = "└── ";
                String pointerLeft = (node.getRight() != null) ? "├── " : "└── ";

                traverseNodes(sb, paddingForBoth, pointerLeft, node.getLeft(), node.getRight() != null);
                traverseNodes(sb, paddingForBoth, pointerRight, node.getRight(), false);

            }
        }

        public void print(PrintStream os) {
            os.print(traversePreOrder(tree));
        }

    }
}
