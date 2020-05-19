import java.util.Random;

/**
 * Write a description of class Arrays here.
 * 
 * @author Matthew Pigliavento
 * @version (a version number or a date)
 */
public class IntArrayGenerator
{
    public static void printArray(int[] array)
    {
        System.out.print("{");
        for (int i = 0; i < array.length-1; i++)
        {
            System.out.print(array[i] + ", ");
        }
        System.out.println(array[array.length-1] + "}");
    }

    public static int[] randomArray(int size, int range)
    {
        Random ran = new Random();
        int[] array = new int[size];
        for (int i = 0; i < size; i++)
        {
            array[i] = ran.nextInt(range+1);
        }
        return array;
    }

    public static int[] sortedAscendingArray(int size)
    {
        int[] array = new int[size];
        for (int i = 1; i <= size; i++)
        {
            array[i - 1] = i;
        }
        return array;
    }

    public static int[] sortedDescendingArray(int size)
    {
        int[] array = new int[size];
        int j = size;
        for (int i = 0; i < size; i++)
        {
            array[i] = j;
            j--;
        }

        return array;
    }

    public static int[] nearlySorted(int size)
    {
        int[] array = new int[size];
        int j = size;
        for (int i = 0; i < size - 5; i++)
        {
            array[i] = i + 1;
        }
        for (int i = size - 5; i < size; i++)
        {
            array[i] = j;
            j--;
        }
        return array;
    }

    public static void main(String[] args)
    {
        printArray(randomArray(20, 45));
        printArray(sortedAscendingArray(20));
        printArray(sortedDescendingArray(20));
        printArray(nearlySorted(20));
    }
}