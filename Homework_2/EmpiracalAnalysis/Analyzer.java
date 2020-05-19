
/**
 * Write a description of class Analyzer here.
 * 
 * @author Matt Pigliavento, Chris Fall 
 * @version Spring 2017
 */
public class Analyzer
{
    public static int[] bubbleSort(int[] A)
    {
        for (int i = 0; i < A.length - 2; i++)
        {
            for (int j = 0; j < A.length - 2 - i; j++)
            {
                if (A[j + 1] < A[j])
                {
                    int temp = A[j];
                    A[j] = A[j + 1];
                    A[j + 1] = temp;
                }
            }
        }
        return A;
    }

    public static int[] selectionSort(int[] A)
    {
        int min, i, j;
        for (i = 0; i < A.length - 1; i++)
        {
            min = i;
            for (j = i + 1; j < A.length; j++)
            {
                if (A[j] < A[min])
                {
                    min = j;
                }
            }
            int temp = A[i];
            A[i] = A[min];
            A[min] = temp;
        } 
        return A;
    }

    public static int[] insertionSort(int[] A)
    {
        int i, j, v;
        for (i = 1; i < A.length; i++)
        {
            v = A[i];
            j = i - 1;
            while (j >= 0 && A[j] > v)
            {
                A[j + 1] = A[j];
                j -= 1;
            }
            A[j + 1] = v;
        }
        return A;
    }

    public static int[] quickSort(int[] A, int low, int high)
    {
        if (low < high)
        {
            int pi = partition(A, low, high);

            // Recursively sort elements before
            // partition and after partition
            quickSort(A, low, pi-1);
            quickSort(A, pi+1, high);
        }
        return A;
    }

    public static int partition(int[] arr, int low, int high)
    {
        int pivot = arr[high];
        int i = (low-1); // index of smaller element
        for (int j=low; j<=high-1; j++)
        {
            // If current element is smaller than or
            // equal to pivot
            if (arr[j] <= pivot)
            {
                i++;

                // swap arr[i] and arr[j]
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }

        // swap arr[i+1] and arr[high]
        int temp = arr[i+1];
        arr[i+1] = arr[high];
        arr[high] = temp;

        return i+1;
    }

    public static void main(String[] args)
    {
        String order = args[0];
        String type = args[1];
        int size = Integer.parseInt(args[2]);
        int[] A;
        Analyzer analyze = new Analyzer();
        if (order.equals("random"))
        {
            A = Generator.randomArray(size);
        }
        else if (order.equals("ascending"))
        {
            A = Generator.sortedAscendingArray(size);
        }
        else if (order.equals("descending"))
        {
            A = Generator.sortedDescendingArray(size);
        }
        else if (order.equals("nearly"))
        {
            A = Generator.nearlySorted(size);
        }
        else
        {
            System.out.println("Invalid array generation request");
            return;
        }
        
        long time;
        double seconds = 0;
        if (type.equals("bubble"))
        {
            time = System.currentTimeMillis();
            bubbleSort(A);
            seconds = (System.currentTimeMillis() - time)/1000.0;
        }
        else if (type.equals("selection"))
        {
            time = System.currentTimeMillis();
            selectionSort(A);
            seconds = (System.currentTimeMillis() - time)/1000.0;
        }
        else if (type.equals("insertion"))
        {
            time = System.currentTimeMillis();
            insertionSort(A);
            seconds = (System.currentTimeMillis() - time)/1000.0;
        }
        else if (type.equals("merge"))
        {
            MergeShortTest ms = new MergeShortTest();
            time = System.currentTimeMillis();
            ms.sort(A);
            seconds = (System.currentTimeMillis() - time)/1000.0;
        }
        else if (type.equals("quick"))
        {
            time = System.currentTimeMillis();
            quickSort(A, 0, A.length - 1);
            seconds = (System.currentTimeMillis() - time)/1000.0;
        }
        else
        {
            System.out.println("Invalid sorting type");
        }

        System.out.println(size + " " + type + " " + order + " " + seconds);
    }
}