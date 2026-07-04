import java.util.Scanner;

// Define the Node class
class Node {
    int data;
    Node left, right;

    public Node(int item) {
        data = item;
        left = right = null;
    }
}

// Define the BinaryTree class
class BinaryTree {
    Node root;

    // Method to perform inorder traversal
    void inorderTraversal(Node node) {
        if (node == null) {
            return;
        }

        inorderTraversal(node.left);
        System.out.print(node.data + " ");
        inorderTraversal(node.right);
    }

    // Method to perform preorder traversal
    void preorderTraversal(Node node) {
        if (node == null) {
            return;
        }

        System.out.print(node.data + " ");
        preorderTraversal(node.left);
        preorderTraversal(node.right);
    }

    // Method to perform postorder traversal
    void postorderTraversal(Node node) {
        if (node == null) {
            return;
        }

        postorderTraversal(node.left);
        postorderTraversal(node.right);
        System.out.print(node.data + " ");
    }

    // Utility function to insert nodes in level order
    public Node insertLevelOrder(int[] arr, Node root, int i) {
        // Base case for recursion
        if (i < arr.length) {
            Node temp = new Node(arr[i]);
            root = temp;

            // Insert left child
            root.left = insertLevelOrder(arr, root.left, 2 * i + 1);

            // Insert right child
            root.right = insertLevelOrder(arr, root.right, 2 * i + 2);
        }
        return root;
    }
}

// Main class to test the tree traversal
public class TreeTraversal {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BinaryTree tree = new BinaryTree();

        System.out.println("Enter the number of elements in the tree:");
        int n = scanner.nextInt();
        int[] arr = new int[n];

        System.out.println("Enter the elements of the tree:");
        for (int i = 0; i < n; i++) {
            arr[i] = scanner.nextInt();
        }

        tree.root = tree.insertLevelOrder(arr, tree.root, 0);

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Inorder Traversal");
            System.out.println("2. Preorder Traversal");
            System.out.println("3. Postorder Traversal");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Inorder Traversal: ");
                    tree.inorderTraversal(tree.root);
                    System.out.println();
                    break;
                case 2:
                    System.out.print("Preorder Traversal: ");
                    tree.preorderTraversal(tree.root);
                    System.out.println();
                    break;
                case 3:
                    System.out.print("Postorder Traversal: ");
                    tree.postorderTraversal(tree.root);
                    System.out.println();
                    break;
                case 4:
                    System.out.println("Exiting...");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }
}

