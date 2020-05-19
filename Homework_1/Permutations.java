/**
 * Prints all permutations of a given set
 * 
 * @author Chris Fall, Matt Pigliavento 
 * @version 02/06/2017
 */
public class Permutations
{    
    public static void permute(int[] array)
    {
        permuteSet(array, 0);
    }

    private static void permuteSet(int[] array, int pos)
    {
        if(pos >= array.length - 1)
        { //If we are at the last element - nothing left to permute
            //System.out.println(Arrays.toString(arr));
            //Print the array
            System.out.print("{");
            for(int i = 0; i < array.length - 1; i++)
            {
                System.out.print(array[i] + ", ");
            }
            if(array.length > 0) 
            {
                System.out.print(array[array.length - 1]);
            }
            System.out.println("}");
            return;
        }

        for(int i = pos; i < array.length; i++)
        { //For each index in the sub array arr[index...end]

            //Swap the elements at indices index and i
            int swap = array[pos];
            array[pos] = array[i];
            array[i] = swap;

            //Recurse on the sub array arr[index+1...end]
            permuteSet(array, pos+1);

            //Swap the elements back
            swap = array[pos];
            array[pos] = array[i];
            array[i] = swap;
        }
    }
    
    public static void main(String[] args)
    {
        int[] array = {1, 2, 3, 4};
        permuteSet(array, 0);
    }
}