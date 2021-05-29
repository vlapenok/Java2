import java.util.Arrays;

public class MainApp {
    public static void main(String[] args) {
        String[][] arr = new String[4][4];
        int count = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                count++;
                arr[i][j] = Integer.toString(count);
            }
        }
        System.out.println(Arrays.deepToString(arr));

//        Первый пункт ДЗ
//        String[][] arr1 = new String[4][8];
//        arrException(arr1);

//        Второй пункт ДЗ
        arr[2][2] = "число";

        try {
            arrException(arr);
        } catch (MyArrayDataException e) {
            System.out.println(e.getTxt());
        }
    }

    public static void arrException(String[][] arr) {
        int sum = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                if(arr.length != 4 || arr[i].length != 4) {
                    throw new MyArraySizeException("Размер массива не 4х4");
                }
                if(!arr[i][j].matches("[-+]?\\d+")) {
                    throw new MyArrayDataException("Исключение в ячейке массива [" + i + "][" + j + "]");
                }
                int num = Integer.parseInt(arr[i][j]);
                sum += num;
            }
        }
        System.out.println("Сумма элементов массива: " + sum);
    }
}