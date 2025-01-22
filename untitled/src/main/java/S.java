//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

public class S {
    public static ArrayList<Integer> nums = new ArrayList<>();
    public static ArrayList<Integer> help = new ArrayList<>();
    public  static  long m = 0;
    public static HashMap<Integer, Integer> count = new HashMap<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int arr[] = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = scanner.nextInt();
            if(count.containsKey(arr[i])) {
                count.put(arr[i], count.get(arr[i])+1);
            } else {
                count.put(arr[i], 1);
                nums.add(arr[i]);
            }
        }

        bt(0, 0);
        System.out.println(m);

    }


    public static void bt(int index, long n) {
        if(index >= nums.size()) {
            if (n > m) {
                m = n;
            }
            return;
        }
        if (!(help.contains(nums.get(index)-1) || help.contains(nums.get(index)+1))) {
            help.add(nums.get(index));
            bt(index+1, n+ (long) nums.get(index) *count.get(nums.get(index)));
            help.remove(nums.get(index));
        }
        bt(index+1, n);
    }
}
