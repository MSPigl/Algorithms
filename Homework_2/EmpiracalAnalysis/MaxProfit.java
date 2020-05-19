
/**
 * Write a description of class MaxProfit here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MaxProfit
{
    int maxTotal, currentTotal = 0;
    public int MP(int[] A, int sday, int eday)
    {
        if (sday == eday)
        {
            return A[sday];
        }
        currentTotal = 0;
        for (int i = sday; i <= eday; i++)
        {
            currentTotal += A[i];
            if (currentTotal > maxTotal)
            {
                maxTotal = currentTotal;
            }
        }
        MP(A, sday + 1, eday);

        return maxTotal;
    }
}
