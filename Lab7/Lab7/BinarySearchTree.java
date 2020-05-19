//
// A binary search tree data structure for storing integer values. The tree
// has the property that for every node n, all values in n's left subtree are less
// than or equal to n's data value and all values in n's right subtree are larger.
//
import java.util.Random;

public class BinarySearchTree
{
    // inner Node class
    private class Node {
        private double data;
        private Node left;
        private Node right;

        private Node( double d, Node lt, Node rt ) {
            data = d;
            left = lt;
            right = rt;
        }

        private boolean isLeaf( ) {
            return ((left == null) && (right == null));
        }
    }

    private Node root; // top node in the tree

    // Constructor for objects of class BinarySearchTree. It initializes a tree
    // of 1000 values.
    public BinarySearchTree( String type )
    {
        if (type.equals( "large" )) {
            // make large tree of 1000 nodes for testing
            Random rand = new Random( 10 );  // seed with 10
            for (int i = 0; i < 1000; i++) {
                int r = rand.nextInt(100000);
                add( r );
            }
        } else if (type.equals( "small" )){
            // make little tree for debugging. The tree created looks like this
            //       5
            //         \
            //           8
            //         /   \
            //        6      9
            //         \
            //          7 
            add( 5 );
            add( 8 );
            add( 6 );
            add( 9 );
            add( 7 );
        }
    }

    // Adds the value to the tree. It maintains the binary search tree property 
    // that for every tree node n, all values in n's left subtree are less
    // than or equal to n's data value and all values in n's right subtree are larger.
    //
    public void add( double value ) 
    { 
        // inserting into an empty tree
        if ( root == null )
            root = new Node( value, null, null );
        else 
            recursiveAdd( value, root );
    }

    // Precondition: n is not null
    // Postcondition: value is inserted into the binary search tree whose root is n
    //
    private void recursiveAdd( double value, Node n )
    {
        if ( (value > n.data) && (n.right == null) )
        // value is added as the right child of n
            n.right = new Node( value, null, null );
        else if ( (value <= n.data) && (n.left == null) )
        // value is added as the left child of n
            n.left = new Node( value, null, null );   
        else if ( (value > n.data) && (n.right != null) )
        // insert value into n's right subtree
            recursiveAdd( value, n.right );
        else // the following is true: ( (value <= n.data) && (n.left != null) )
        // insert value into n's left subtree
            recursiveAdd( value, n.left );
    }

    // Returns the height of the tree. If the tree is the
    // empty tree, it returns -1.
    public int height( ) {
        return recursiveHeight( root );
    }

    // Returns the height of the tree whose root is node v. If v is null,
    // it returns -1.
    private int recursiveHeight( Node v ) {
        if ( v == null ) return -1;
        int lh = recursiveHeight( v.left );
        int rh = recursiveHeight( v.right );
        return Math.max( lh, rh ) + 1;
    }

    // Returns the number of nodes that are distance k from the root  
    public int numDistK( int k ) {
        return recursiveNumDistK( k, root );
    }

    // Returns the number of nodes that are distance k from the tree whose
    // root is node v.
    private int recursiveNumDistK( int k, Node v ) {
        if (v == null || k < 0)
        {
            return 0;
        }

        if (k == 0)
        {
            return 1;
        }

        return recursiveNumDistK(k - 1, v.left) + recursiveNumDistK(k - 1, v.right);
    }

    // Returns the number of values in the tree that are 
    // in the range [s...e], inclusive.
    public int numWithinRange( int s, int e ) {
        return recursiveNumWithinRange( s, e, root );
    }

    // Returns the number of values in the tree with root node v that are 
    // in the range [s...e], inclusive.
    public int recursiveNumWithinRange( int s, int e, Node v ) {
        if (v == null)
        {
            return 0;
        }

        if (v.data <= e && v.data >= s)
        {
            return 1 + recursiveNumWithinRange(s, e, v.left) + recursiveNumWithinRange(s, e, v.right);
        }
        else if (v.data < s)
        {
            return recursiveNumWithinRange(s, e, v.right);
        }
        return recursiveNumWithinRange(s, e, v.left); 
    }

    // Removes all leaf nodes in the tree and returns a count of how many
    // leaves were removed.    
    public int removeLeaves(  ) 
    {
        // special case if root is a leaf
        if (root.isLeaf()) {
            root = null;
            return 1;
        }
        else
            return recursiveRemoveLeaves( root );
    }

    // Removes all leaf nodes in the tree rooted at v and returns a count of how many
    // leaves were removed.  
    private int count = 0;
    private int recursiveRemoveLeaves( Node v ) 
    {
        // search left
        if (v.left != null)
        {
            if (v.left.isLeaf())
            {
                v.left = null;
                count++;
            }
            else
            {
                recursiveRemoveLeaves(v.left);
            }
        }
        
        // search right
        if (v.right != null)
        {
            if (v.right.isLeaf())
            {
                v.right = null;
                count++;
            }
            else
            {
                recursiveRemoveLeaves(v.right);
            }
        }
        
        return count;
    }

    public static void main(String[] args)
    {
        BinarySearchTree tree = new BinarySearchTree("large");
        System.out.println("Height before leaf deletion: " + tree.height());
        System.out.println("Number of deleted leaves: " + tree.removeLeaves());
        System.out.println("Height after leaf deletion: " + tree.height());
    }
}