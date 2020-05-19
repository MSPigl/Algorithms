/**
 * Exercises in top-down dynamic programming.
 */
public class DynamicProgramming
{
    public int count = 0;
    
    /**
     * Returns true if it is possible to fill an order for orderSize
     * chicken nuggets using boxes of size 6, 9 and 20.
     * 
     * @param orderSize size of the desired order
     * @param S array of size [0...orderSize] used to store 
     *          solutions to subproblems, for dynamic programming
     * @return true if it is possible to fill the order, false otherwise
     */
    public boolean fillOrderDynProg( int orderSize, int S[] )
    {
        System.out.println(count);
        if (orderSize == 0) return true;

        if (S[orderSize] != -1)
        {
            if (S[orderSize] == 1)
            {
                return true;
            }
            else if (S[orderSize] == 0)
            {
                return false;
            }
        }

        count++;
        
        if (orderSize >= 6) { 
            if ( fillOrderDynProg( orderSize - 6 , S ) ) 
            {
                S[orderSize] = 1;
                return true;
            }
        }
        if (orderSize >= 9) {
            if ( fillOrderDynProg( orderSize - 9 , S ) ) 
            {
                S[orderSize] = 1;
                return true;
            }
        }
        if (orderSize >= 20) {
            if ( fillOrderDynProg( orderSize - 20 , S ) ) 
            {
                S[orderSize] = 1;
                return true;
            }
        }
        
        S[orderSize] = 0;
        return false;
    }

    /**
     * Top-down exhaustive search version of the coin row problem.
     * 
     * Given an array R of coin values, this method returns the maximum value of coins 
     * that you can pick up if you are limited to picking coins in R
     * at indices >= startIndex and with the constraint that you can not pick up two 
     * coins that are adjacent in R.
     * 
     * @param R array storing the values of the coins in the row
     * @param startIndex only coins with indices >= startIndex may be picked
     */
    public int maxPickUpCoins( int R[], int startIndex ) 
    {
        if ( startIndex >= R.length ) return 0;
        
        int value = R[startIndex];
        
        if (startIndex >= R.length - 1)
        {
            return value;
        }
        else if (startIndex >= R.length - 2)
        {
            return Math.max(value, maxPickUpCoins(R, startIndex + 1));
        }
        
        return Math.max(value + maxPickUpCoins(R, startIndex + 2), maxPickUpCoins(R, startIndex + 1));
    }

    /**
     * Top-down dynamic programming version of the coin row problem.
     * 
     * Given an array R of coin values, this method returns the maximum value of coins 
     * that you can pick up if you are limited to picking coins in R
     * at indices >= startIndex and with the constraint that you can not pick up two 
     * coins that are adjacent in R. Array S stores solutions to subproblems that
     * have already been solved.
     * 
     * @param R array storing the values of the coins in the row
     * @param startIndex only coins with indices >= startIndex may be picked
     * @param S array the same size as R used to store solutions to subproblems, 
     *          for dynamic programming 
     */
    public int maxPickUpCoinsDynProg( int R[], int startIndex, int S[] ) {
        if ( startIndex >= R.length ) return 0;
        
        if (S[startIndex] != -1)
        {
            return S[startIndex];
        }
        
        int value = R[startIndex];
        
        if (startIndex >= R.length - 1)
        {
            S[startIndex] = value;
            return value;
        }
        else if (startIndex >= R.length - 2)
        {
            S[startIndex] = Math.max(value, maxPickUpCoinsDynProg(R, startIndex + 1, S));
            return S[startIndex];
        }
        
        S[startIndex] = Math.max(value + maxPickUpCoinsDynProg(R, startIndex + 2, S), maxPickUpCoinsDynProg(R, startIndex + 1, S));
        return S[startIndex];
    }

    /**
     ******************** TEST METHODS ************************
     */
    public void testFillOrderDynProg() {

        int score = 0;
        int s1[] = new int[22];
        for (int i = 0; i < s1.length; i++) s1[i] = -1;
        boolean b1 = fillOrderDynProg( 21, s1 );
        if (b1 == true) score++;
        if (s1[15] == 1) score++;

        int s2[] = new int[23];
        for (int i = 0; i < s2.length; i++) s2[i] = -1;
        boolean b2 = fillOrderDynProg( 22, s2 );
        if (b2 == false) score++;
        if (s2[16] == 0) score++;
        if (s2[13] == 0) score++;

        int s3[] = new int[47];
        for (int i = 0; i < s3.length; i++) s3[i] = -1;
        boolean b3 = fillOrderDynProg( 46, s3 );
        if (b3 == true) score++;
        if (s3[16] == 0) score++;
        if (s3[28] == 0) score++;

        int s4[] = new int[48];
        for (int i = 0; i < s4.length; i++) s4[i] = -1;
        boolean b4 = fillOrderDynProg( 47, s4 );
        if (b4 == true) score++;
        if (s4[29] == 1) score++;

        System.out.println( "testFillOrderDynProg score: " + score + " out of 10 possible" );
    }

    public void testPickUpCoins() {
        int score = 0;
        int r3[] = {5, 2, 7 };
        int m3 = maxPickUpCoins( r3, 0 );
        if ( m3 == 12 ) score++;

        int r1[] = {1, 15, 20, 7, 2, 4, 3, 7};
        int m1 = maxPickUpCoins( r1, 0 );
        if ( m1 == 33 ) score++;

        int r2[] = {2, 2, 15, 25, 19, 3, 4, 12};
        int m2 = maxPickUpCoins( r2, 0 );
        if ( m2 == 48 ) score++;

        System.out.println( "testPickUpCoins score: " + score + " out of 3 possible" );

    }

    public void testPickUpCoinsDynProg() {

        int score = 0;

        int r3[] = {5, 2, 7 };
        int s3[] = new int[3];
        for (int i = 0; i < s3.length; i++) s3[i] = -1;
        int m3 = maxPickUpCoinsDynProg( r3, 0, s3 );
        if ( m3 == 12 ) score++;
        if ( s3[2] == 7 ) score++;
        if ( s3[1] == 7 ) score++;

        int r1[] = {1, 15, 20, 7, 2, 4, 3, 7};
        int s1[] = new int[8];
        for (int i = 0; i < s1.length; i++) s1[i] = -1;
        int m1 = maxPickUpCoinsDynProg( r1, 0, s1 );
        if ( m1 == 33 ) score++;
        if ( s1[5] == 11 ) score++;

        int r2[] = {2, 2, 15, 25, 19, 3, 4, 12};
        int s2[] = new int[8];
        for (int i = 0; i < s2.length; i++) s2[i] = -1;
        int m2 = maxPickUpCoinsDynProg( r2, 0, s2 );
        if ( m2 == 48 ) score++;
        if ( s2[4] == 31 ) score++;

        System.out.println( "testPickUpCoinsDynProg score: " + score + " out of 7 possible" );

    }


}