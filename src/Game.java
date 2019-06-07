import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Game {

    //Класс сущности для хранения цветов
    private static class Color {
        public Color(int fc, int bgc) {
            fontColor = fc;//Цвет шрифта
            bgColor = bgc;//Цвет фона
        }

        public int fontColor;//Цвет шрифта
        public int bgColor;//Цвет фона
    }

    JFrame mainFrame;//Главное окно объекта
    JLabel[] jLabels;
    int[] datas = new int[]{0, 0, 0, 0,
            0, 0, 0, 0,
            0, 0, 0, 0,
            0, 0, 0, 0};//Значение на каждом квадрате
    int[] temp = new int[4];//Временный массив, извлеченный из алгоритма перемещения блока
    int[] temp2 = new int[16];//Используется для обнаружения слияния блоков


    List emptyBlocks = new ArrayList<Integer>(16);//временный список, используемый при создании новых квадратов для хранения пустых квадратов

    //Хранится в цвете map
    static HashMap<Integer, Color> colorMap = new HashMap<Integer, Color>() {{
        put(0, new Color(0x776e65, 0xCDC1B4));
        put(2, new Color(0x776e65, 0xeee4da));
        put(4, new Color(0x776e65, 0xede0c8));
        put(8, new Color(0xf9f6f2, 0xf2b179));
        put(16, new Color(0xf9f6f2, 0xf59563));
        put(32, new Color(0xf9f6f2, 0xf67c5f));
        put(64, new Color(0xf9f6f2, 0xf65e3b));
        put(128, new Color(0xf9f6f2, 0xedcf72));
        put(256, new Color(0xf9f6f2, 0xedcc61));
        put(512, new Color(0xf9f6f2, 0xe4c02a));
        put(1024, new Color(0xf9f6f2, 0xe2ba13));
        put(2048, new Color(0xf9f6f2, 0xecc400));
    }};

    public Game() {
        initGameFrame();
        initGame();
        refresh();
    }

    //Генерация двух 2 квадратов и одного 4 квадрата при запуске
    private void initGame() {
        for (int i = 0; i < 2; i++) {
            generateBlock(datas, 2);
        }
        generateBlock(datas, 4);
    }

    //Произвольно генерировать 4 или 2 квадрата
    private void randomGenerate(int arr[]) {
        int ran = (int) (Math.random() * 10);
        if (ran > 5) {
            generateBlock(arr, 4);
        } else {
            generateBlock(arr, 2);
        }

    }

    //Произвольно сгенерировать новый квадрат, параметр: значение квадрата, который будет сгенерирован
    private void generateBlock(int arr[], int num) {
        emptyBlocks.clear();

        for (int i = 0; i < 16; i++) {
            if (arr[i] == 0) {
                emptyBlocks.add(i);
            }
        }
        int len = emptyBlocks.size();
        if (len == 0) {
            return;
        }
        int pos = (int) (Math.random() * 100) % len;
        arr[(int) emptyBlocks.get(pos)] = num;
        refresh();

    }


    //Выигрыш и проигрыш суждения и окончательная обработка
    private void judge(int arr[]) {

        if (isWin(arr)) {
            JOptionPane.showMessageDialog(null, "Поздравляем, вы успешно собрали 2048 квадратов.", "Ты выиграл", JOptionPane.PLAIN_MESSAGE);
            System.exit(0);
        }
        if (isEnd(arr)) {
            int max = getMax(datas);
            JOptionPane.showMessageDialog(null, "Извините, вы не составили коробку 2048, ваш самый большой квадрат：" + max, "Конец игры", JOptionPane.PLAIN_MESSAGE);
            System.exit(0);
        }

    }

    //Определите, выигрывает ли игрок, если победителем является поле, большее или равное 2048
    private boolean isWin(int arr[]) {
        for (int i : arr) {
            if (i >= 2048) {
                return true;
            }
        }
        return false;

    }

    //Эта функция используется для определения того, закончилась ли игра. Если игра заполнена, она не будет генерировать пустой блок. Если коробка заполнена, она вернет true, что указывает на окончание игры.
    private boolean isEnd(int arr[]) {

        int[] tmp = new int[16];
        int isend = 0;

        System.arraycopy(arr, 0, tmp, 0, 16);
        left(tmp);
        if (isNoBlank(tmp)) {
            isend++;
        }

        System.arraycopy(arr, 0, tmp, 0, 16);
        right(tmp);
        if (isNoBlank(tmp)) {
            isend++;
        }

        System.arraycopy(arr, 0, tmp, 0, 16);
        up(tmp);
        if (isNoBlank(tmp)) {
            isend++;
        }

        System.arraycopy(arr, 0, tmp, 0, 16);
        down(tmp);
        if (isNoBlank(tmp)) {
            isend++;
        }

        if (isend == 4) {
            return true;
        } else {
            return false;
        }
    }

    //Определите, нет ли пустых квадратов
    private boolean isNoBlank(int arr[]) {

        for (int i : arr) {
            if (i == 0) {
                return false;
            }
        }
        return true;
    }

    //Получить наибольшее квадратное значение
    private int getMax(int arr[]) {
        int max = arr[0];
        for (int i : arr) {
            if (i >= max) {
                max = i;
            }
        }
        return max;
    }

    //Обновить данные, отображаемые каждым квадратом
    private void refresh() {
        JLabel j;
        for (int i = 0; i < 16; i++) {
            int arr = datas[i];
            j = jLabels[i];
            if (arr == 0) {
                j.setText("");
            } else if (arr >= 1024) {
                j.setFont(new java.awt.Font("Dialog", 1, 42));
                j.setText(String.valueOf(datas[i]));
            } else {
                j.setFont(new java.awt.Font("Dialog", 1, 50));
                j.setText(String.valueOf(arr));
            }

            Color currColor = colorMap.get(arr);
            j.setBackground(new java.awt.Color(currColor.bgColor));
            j.setForeground(new java.awt.Color(currColor.fontColor));
        }
    }

    //Инициализируйте игровое окно и выполните несколько сложных операций.
    private void initGameFrame() {

        //Создайте JFrame и сделайте некоторые настройки
        mainFrame = new JFrame("2048 Game");
        mainFrame.setSize(500, 500);
        mainFrame.setResizable(false);//固定窗口尺寸
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLocationRelativeTo(null);

        mainFrame.setLayout(new GridLayout(4, 4));
        mainFrame.getContentPane().setBackground(new java.awt.Color(0xCDC1B4));
        //Добавить кнопку прослушивания
        mainFrame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {
            }

            @Override
            public void keyPressed(KeyEvent keyEvent) {

                System.arraycopy(datas, 0, temp2, 0, 16);

                //Вызовите различные функции обработчика в зависимости от кнопки
                switch (keyEvent.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        up(datas);
                        break;

                    case KeyEvent.VK_DOWN:
                        down(datas);
                        break;

                    case KeyEvent.VK_LEFT:
                        left(datas);
                        break;

                    case KeyEvent.VK_RIGHT:
                        right(datas);
                        break;

                }


                //Определите, есть ли слияние квадратов после перемещения, если они есть, сгенерируйте новый квадрат, если нет, новый квадрат не будет сгенерирован.
                if (!Arrays.equals(datas, temp2)) {
                    randomGenerate(datas);
                }

                refresh();
                judge(datas);
            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {
            }
        });

        //Используйте стиль интерфейса системы по умолчанию
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        //Используйте 16 JLabels для отображения 16 квадратов
        jLabels = new JLabel[16];
        JLabel j; //Повторное использование ссылок, избегайте создания слишком большого количества ссылок для
        for (int i = 0; i < 16; i++) {
            jLabels[i] = new JLabel("0", JLabel.CENTER);
            j = jLabels[i];
            j.setOpaque(true);
            // Установите границу, параметры: вверх, влево, вниз, вправо, цвет границы
            j.setBorder(BorderFactory.createMatteBorder(6, 6, 6, 6, new java.awt.Color(0xBBADA0)));

            //j.setForeground(new java.awt.Color(0x776E65));
            j.setFont(new java.awt.Font("Dialog", 1, 52));
            mainFrame.add(j);
        }
        mainFrame.setVisible(true);
    }

    private void left(int arr[]) {
        moveLeft(arr);

        combineLeft(arr);

        moveLeft(arr);//После слияния будет создан пробел, поэтому снова переместите его влево.


    }

    //Слияние квадратов слева
    private void combineLeft(int arr[]) {
        for (int l = 0; l < 4; l++) {
            //0 1 2
            for (int i = 0; i < 3; i++) {
                if ((arr[l * 4 + i] != 0 && arr[l * 4 + i + 1] != 0) && arr[l * 4 + i] == arr[l * 4 + i + 1]) {
                    arr[l * 4 + i] *= 2;
                    arr[l * 4 + i + 1] = 0;
                }
            }
        }
    }

    //Квадрат смещен влево, используя временный массив для каждой строки для достижения левого смещения
    private void moveLeft(int arr[]) {
        for (int l = 0; l < 4; l++) {


            int z = 0, fz = 0;
            for (int i = 0; i < 4; i++) {
                if (arr[l * 4 + i] == 0) {
                    z++;
                } else {
                    temp[fz] = arr[l * 4 + i];
                    fz++;
                }
            }
            for (int i = fz; i < 4; i++) {
                temp[i] = 0;
            }
            for (int j = 0; j < 4; j++) {
                arr[l * 4 + j] = temp[j];
            }
        }
    }

    private void right(int arr[]) {

        moveRight(arr);
        combineRight(arr);
        moveRight(arr);

    }

    private void combineRight(int arr[]) {
        for (int l = 0; l < 4; l++) {
            //3 2 1
            for (int i = 3; i > 0; i--) {
                if ((arr[l * 4 + i] != 0 && arr[l * 4 + i - 1] != 0) && arr[l * 4 + i] == arr[l * 4 + i - 1]) {
                    arr[l * 4 + i] *= 2;
                    arr[l * 4 + i - 1] = 0;
                }
            }
        }
    }

    private void moveRight(int arr[]) {

        for (int l = 0; l < 4; l++) {

            int z = 3, fz = 3;
            for (int i = 3; i >= 0; i--) {
                if (arr[l * 4 + i] == 0) {
                    z--;
                } else {
                    temp[fz] = arr[l * 4 + i];
                    fz--;
                }
            }
            for (int i = fz; i >= 0; i--) {
                temp[i] = 0;
            }
            for (int j = 3; j >= 0; j--) {
                arr[l * 4 + j] = temp[j];
            }
        }
    }


    private void up(int arr[]) {
        moveUp(arr);
        combineUp(arr);
        moveUp(arr);

    }

    private void combineUp(int arr[]) {


        for (int r = 0; r < 4; r++) {
            for (int i = 0; i < 3; i++) {
                if ((arr[r + 4 * i] != 0 && arr[r + 4 * (i + 1)] != 0) && arr[r + 4 * i] == arr[r + 4 * (i + 1)]) {
                    arr[r + 4 * i] *= 2;
                    arr[r + 4 * (i + 1)] = 0;
                }
            }
        }
    }

    private void moveUp(int arr[]) {

        for (int r = 0; r < 4; r++) {

            int z = 0, fz = 0;
            for (int i = 0; i < 4; i++) {
                if (arr[r + 4 * i] == 0) {
                    z++;
                } else {
                    temp[fz] = arr[r + 4 * i];
                    fz++;
                }
            }
            for (int i = fz; i < 4; i++) {
                temp[i] = 0;
            }
            for (int j = 0; j < 4; j++) {
                arr[r + 4 * j] = temp[j];
            }
        }
    }


    private void down(int arr[]) {
        moveDown(arr);
        combineDown(arr);
        moveDown(arr);
    }

    private void combineDown(int arr[]) {
        for (int r = 0; r < 4; r++) {
            for (int i = 3; i > 0; i--) {
                if ((arr[r + 4 * i] != 0 && arr[r + 4 * (i - 1)] != 0) && arr[r + 4 * i] == arr[r + 4 * (i - 1)]) {
                    arr[r + 4 * i] *= 2;
                    arr[r + 4 * (i - 1)] = 0;
                }
            }
        }
    }

    private void moveDown(int arr[]) {
        for (int r = 0; r < 4; r++) {

            int z = 3, fz = 3;
            for (int i = 3; i >= 0; i--) {
                if (arr[r + 4 * i] == 0) {
                    z--;
                } else {
                    temp[fz] = arr[r + 4 * i];
                    fz--;
                }
            }
            for (int i = fz; i >= 0; i--) {
                temp[i] = 0;
            }
            for (int j = 3; j >= 0; j--) {
                arr[r + 4 * j] = temp[j];
            }
        }
    }

}
