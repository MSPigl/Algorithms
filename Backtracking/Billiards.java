import java.util.ArrayList;

/**
 * Write a description of class Billiards here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Billiards
{
    /** class variable to count the number of non-trival calls made */
    private static long numCalls = 0;

    /**
    check to see if a ball placed in the next row at the given column
    into the given solution so far will be a legal placement

    @param int n number of ball in the top row of the solution
    @param ball balls placed so far
    @param nextBall column to try to place the next ball in the next row

    @return whether the adding a ball at nextBall in the next row of
    balls will cause a violation
     */
    public static boolean legalMove(int n, ArrayList<Integer> balls, int nextBall)
    {   
        int size = balls.size();
        ArrayList<Integer> temp = balls;

        int currentLength = size;

        for (int i = 0 ; i < size - 1; i++)
        {
            for (int j = temp.size() - currentLength; j < currentLength; j++)
            {
                if (temp.contains(nextBall) || Math.abs(temp.get(j) - temp.get(j + 1)) == 0)
                {
                    return false;
                }
                temp.add(Math.abs(temp.get(j) - temp.get(j + 1)));
            }          
            currentLength--;
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
    public static ArrayList<Integer> billiards(int n, ArrayList<Integer> balls, int nextBall)
    {
        // maybe we have placed all n queens
        if (balls.size() == n) {
            return balls;
        }

        // maybe this is an invalid nextCol (off the board)
        if (nextBall >= n) {
            return null;
        }

        // this is a nontrival call, so remember it
        numCalls++;

        // would this move introduce a problem?
        if (!legalMove(n, balls, nextBall)) {
            return billiards(n, balls, nextBall+1);
        }

        // we can make the move
        // put at the end of the state
        balls.add(nextBall);
        ArrayList<Integer> result = billiards(n, balls, 0);

        // did this lead us to a solution?
        if (result != null) return result;

        // if not, backtrack and try next possible move
        balls.remove(balls.size()-1);
        return billiards(n, balls, nextBall+1);
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

        //for (int n = 1; n < 33; n++)
        //{
        //    long startTime = System.currentTimeMillis();
        //    ArrayList<Integer> answer = billiards(n, new ArrayList<Integer>(), 0);
        //    long elapsedTime = System.currentTimeMillis() - startTime;

        //    System.out.printf("n=%d computation took %.2f seconds and %d nontrival recursive calls.\n",
        //        n, elapsedTime/1000.0, numCalls);
        //    if (answer == null) {
        //        System.out.println("No solution found.");
        //    }
        //    else {
        //        System.out.println(answer);
        //    }
        //}
    }
}