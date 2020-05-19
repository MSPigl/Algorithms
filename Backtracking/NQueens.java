import java.util.ArrayList;

/**
 * N Queens solution finder, almost complete solution
 *
 * Add your name:
 * @author Jim Teresco
 */

public class NQueens {

    /** class variable to count the number of non-trival calls made */
    private static long numCalls = 0;

    /**
    check to see if a queen placed in the next row at the given column
    into the given solution so far will be a legal placement

    @param int n number of queens in the ultimate solution
    @param queens queens placed so far
    @param nextCol column to try to place the next queen in the next row

    @return whether the adding a queen at nextCol in the next row of
    queens will cause a violation
     */
    public static boolean legalMove(int n, ArrayList<Integer> queens, int nextCol)
    {
        if (queens.contains(nextCol))
        {
            return false;
        }

        for (int i = 0 ; i < queens.size(); i++)
        {
            if (Math.abs(nextCol - queens.get(i)) == Math.abs(queens.size() - i))
            {
                return false;
            }
        }

        return true;
    }

    /**
    method to find an N-queens solution

    @param n the problem size being solved
    @param queens ArrayList of queens placed so far, each specifies
    a column for the row corrsponding to the entry
    @param nextCol column to try to place the next queen in the next row

    @return an ArrayList with the successful queens placement, null
    if no solution is found with this configuration and move
     */
    public static ArrayList<Integer> nqueens(int n,
    ArrayList<Integer> queens,
    int nextCol) {

        // maybe we have placed all n queens
        if (queens.size() == n) {
            return queens;
        }

        // maybe this is an invalid nextCol (off the board)
        if (nextCol >= n) {
            return null;
        }

        // this is a nontrival call, so remember it
        numCalls++;

        // would this move introduce a problem?
        if (!legalMove(n, queens, nextCol)) {
            return nqueens(n, queens, nextCol+1);
        }

        // we can make the move
        // put at the end of the state
        queens.add(nextCol);
        ArrayList<Integer> result = nqueens(n, queens, 0);

        // did this lead us to a solution?
        if (result != null) return result;

        // if not, backtrack and try next possible move
        queens.remove(queens.size()-1);
        return nqueens(n, queens, nextCol+1);
    }

    /**
    main method to find solutions to N Queens

    @param args args[0] is the number of queens
     */
    public static void main(String args[]) {

        //         // check for required argument
        //         if (args.length != 1) {
        //             System.err.println("Usage: java NQueens nQueens");
        //             System.exit(1);
        //         }
        // 
        //         // parse n
        //         int n = 0;
        // 
        //         try {
        //             n = Integer.parseInt(args[0]);
        //         }
        //         catch (NumberFormatException e) {
        //             System.err.println("Usage: java NQueens nQueens");
        //             System.exit(1);
        //         }

        //         if (n < 1) {
        //             System.err.println("nQueens must be a positive integer");
        //             System.exit(1);
        //         }

        // compute the solution

        for (int n = 1; n < 33; n++)
        {
            long startTime = System.currentTimeMillis();
            ArrayList<Integer> answer = nqueens(n, new ArrayList<Integer>(), 0);
            long elapsedTime = System.currentTimeMillis() - startTime;

            System.out.printf("n=%d computation took %.2f seconds and %d nontrival recursive calls.\n",
                n, elapsedTime/1000.0, numCalls);
            if (answer == null) {
                System.out.println("No solution found.");
            }
            else {
                System.out.println(answer);
            }
        }
    }
}