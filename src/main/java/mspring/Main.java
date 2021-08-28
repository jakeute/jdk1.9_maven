package mspring;


import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] p = scanner.nextLine().split(" ");
        int m = Integer.parseInt(p[0]), n = Integer.parseInt(p[1]);
        int[][] arr = new int[m][n];
        for (int i = 0; i < m; i++) {
            String[] w = scanner.nextLine().split(" ");
            for (int j = 0; j < n; j++) {
                arr[i][j] = Integer.parseInt(w[j]);
            }
        }

        int[][] lc = new int[m][n];
        if (m + n - 2 > arr[m - 1][n - 1]) {
            System.out.println(-1);
            return;
        }

        boolean res = test(arr, 0, 0, 0, lc);
        if (res) {
            System.out.println(min);
        } else {
            System.out.println(-1);
        }
    }


    static int min = Integer.MAX_VALUE;

    static boolean test(int[][] arr, int i, int j, int step, int[][] lc) {
        if (i > arr.length - 1 || i < 0 || j < 0 || j > arr[0].length - 1) {
            return false;
        }
        if (arr[i][j] < step || lc[i][j] == 1) {
            return false;
        }

        if (i == arr.length - 1 && j == arr[0].length - 1) {
            if (step < min)
                min = step;
            return true;
        }

        lc[i][j] = 1;

        boolean res = test(arr, i + 1, j, step + 1, lc) || test(arr, i - 1, j, step + 1, lc) ||
                test(arr, i, j + 1, step + 1, lc) || test(arr, i, j - 1, step + 1, lc);
        lc[i][j] = 0;

        return res;
    }

}