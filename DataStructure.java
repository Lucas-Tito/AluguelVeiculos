import java.util.ArrayList;

public class DataStructure {

    // Returns index of stringToFind if it is present in arr[],
    // else return -1
    static int binarySearch_String(ArrayList<Vehicle> arr, String stringToFind){

        int low= 0, high = arr.size() - 1;
        while (low<= high) {
            int mid = low+ (high - low) / 2;
 
            int res = stringToFind.compareTo(arr.get(mid).getNome());
 
            // Check if stringToFind is present at mid
            if (res == 0)
                return mid;
 
            // If stringToFind greater, ignore left half
            if (res > 0)
                low= mid + 1;
 
            // If stringToFind is smaller, ignore right half
            else
                high = mid - 1;
        }
 
        return -1;

    }
    
}
