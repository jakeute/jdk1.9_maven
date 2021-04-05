package mjava.lang;

import java.util.Arrays;

public class CharacterTest {
//    public static void main(String[] args) {
//        //是否是数字
//        Character.isDigit('6');
//        //是否是字母
//        Character.isLetter('a');
//    }


    public static void main(String[] args) {
        int[] array=new int[]{2,6,3,9,0,4,5,2};
        sort1(array);
        System.out.println(Arrays.toString(array));
    }
    /**
     * 插入排序
     *
     * @param array
     */
    static void sort1(int[] array) {
        for (int i = 1; i < array.length; i++) {
            int temp=array[i];
            int j= i-1;
            while(j>=0 && array[j]>temp){
                array[j+1]=array[j];
                j--;
            }
            array[j+1]=temp;
        }
    }

    /**
     * 冒泡
     *
     * @param array
     */
    static void sort2(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = 0; j < array.length - i - 1; j++) {
                if (array[j] > array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
    }

    /**
     * 选择排序
     *
     * @param array
     */
    static void sort3(int[] array) {
        int minIndex;
        for (int i = 0; i < array.length; i++) {
            minIndex = i;
            for (int j = i + 1; j < array.length; j++) {
                if (array[j] < array[minIndex]) {
                    minIndex = j;
                }
            }
            int temp = array[i];
            array[i] = array[minIndex];
            array[minIndex] = temp;
        }
    }

    /**
     * 快排
     *
     * @param array
     */
    static void sort4(int[] array, int l, int r) {
        if (l >= r)
            return;
        int i = l, j = r;
        int p = array[l];
        while (i != j) {
            while (j > i && array[j] >= p) j--;
            while (i < j && array[i] <= p) i++;
            if (i < j) {
                swap(array, i, j);
            }
        }
        swap(array, l, i);
        sort4(array, l, i - 1);
        sort4(array, i + 1, r);
    }

    static void swap(int[] array, int i, int j) {
        int t = array[i];
        array[i] = array[j];
        array[j] = t;
    }
}
