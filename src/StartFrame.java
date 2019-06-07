import com.sun.xml.internal.messaging.saaj.soap.JpegDataContentHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartFrame {

    JFrame mainFrame;
    final String gameRule = "Игра 2048 имеет в общей сложности 16 сеток, в начале она будет случайным образом генерировать два квадрата со значением 2 и квадрат со значением 4.，\n" +
            "Игрок может контролировать направление скольжения квадрата с помощью клавиш со стрелками вверх, вниз, влево и вправо на клавиатуре.，\n" +
            "Каждый раз, когда вы нажимаете клавиши со стрелками, все квадраты будут перемещаться ближе к одному направлению, квадраты одинакового значения будут добавлены и объединены в один квадрат.，\n" +
            "Кроме того, при каждом скольжении квадрат со значением 2 или 4 будет генерироваться случайным образом.，\n" +
            "Игрокам нужно найти способ сделать поле значений 2048 в этих 16 сетках, если 16 сеток заполнены и больше не могут быть перемещены.，\n" +
            "Тогда игра окончена.";
    public StartFrame() {
        initFrame();
    }

    private void initFrame() {
        mainFrame = new JFrame("2048 Game");
        mainFrame.setSize(500, 500);
        mainFrame.setResizable(false);//Фиксированный размер окна
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLocationRelativeTo(null);//Окно по центру


        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));

        jPanel.add(newLine(Box.createVerticalStrut(25)));//Добавить пустую область

        JLabel jLabel = new JLabel("2048");
        jLabel.setForeground(new java.awt.Color(0x776e65));
        jLabel.setFont(new java.awt.Font("Dialog", 1, 92));
        jPanel.add(newLine(jLabel));

        /*
        JLabel author = new JLabel("by xxx");
        jPanel.add(newLine(author));
        */


        jPanel.add(newLine(Box.createVerticalStrut(50)));


        JButton btn1 = new JButton("Начать игру");
        btn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                new Game();
                mainFrame.dispose();
            }
        });
        jPanel.add(newLine(btn1));


        jPanel.add(newLine(Box.createVerticalStrut(50)));


        JButton btn2 = new JButton("Правила игры");
        btn2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JOptionPane.showMessageDialog(null, gameRule, "Правила игры", JOptionPane.PLAIN_MESSAGE);
            }
        });
        jPanel.add(newLine(btn2));


        jPanel.add(newLine(Box.createVerticalStrut(50)));


        JButton btn3 = new JButton("Выход из игры");
        btn3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.exit(0);
            }
        });
        jPanel.add(newLine(btn3));


        mainFrame.add(jPanel);

        mainFrame.setVisible(true);
    }

    //Добавьте новый ряд вертикально центрированных элементов управления, заполнив клеевой объект с обеих сторон элемента управления.
    private JPanel newLine(Component c) {

        JPanel jp = new JPanel();
        jp.setLayout(new BoxLayout(jp, BoxLayout.X_AXIS));
        jp.add(Box.createHorizontalGlue());
        jp.add(c);
        jp.add(Box.createHorizontalGlue());
        jp.setOpaque(false);//Установить непрозрачность

        return jp;
    }

}