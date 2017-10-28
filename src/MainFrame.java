import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;

public class MainFrame extends JFrame {
    private LoginFrame loginFrame = new LoginFrame();
    private JMenuBar jmb = new JMenuBar();
    private JMenu jmF = new JMenu("File");
    private JMenuItem jMenuItemExit = new JMenuItem("Exit");
    private JMenuItem jMenuItemLoto = new JMenuItem("Loto");
    private JMenuItem jMenuItemCal = new JMenuItem("Calculator");
    private JTextField jtfCal = new JTextField();
    private JPanel jpnCal = new JPanel(new GridLayout(3, 3));
    private JButton jbtn[] = new JButton[9];
    private int randomNum[] = new int[9];//亂數若重複_比對用
    boolean flag = true;
    private JDesktopPane jdp = new JDesktopPane();//用不到cp的話

    private JInternalFrame jInternalFrame = new JInternalFrame();
    private JInternalFrame jInternalFrame2 = new JInternalFrame();
    private Container jifCP, jifCP2;
    private JPanel jpn = new JPanel(new GridLayout(1, 6, 5, 5));//放樂透亂數
    private JPanel jpn2 = new JPanel(new GridLayout(1, 2, 5, 5));//放功能
    private JLabel jlb[] = new JLabel[6];
    private JButton jbtnClose = new JButton("Close");
    private JButton jbtnRe = new JButton("Re");
    private int check[] = new int[6];
    private Random rdm = new Random(System.currentTimeMillis());//每次亂數設定

    public MainFrame(LoginFrame login) {
        init();
        loginFrame = login;
    }

    public void init() {
        this.setBounds(300, 300, 500, 500);
        this.setLocationRelativeTo(null);
        this.setJMenuBar(jmb);
        this.setContentPane(jdp);//用不到cp的話
        jmb.add(jmF);
        jmF.add(jMenuItemExit);
        jmF.add(jMenuItemLoto);
        jmF.add(jMenuItemCal);
        jInternalFrame.setBounds(0, 0, 200, 80);
        jifCP = jInternalFrame.getContentPane();
        jInternalFrame.setLayout(new BorderLayout(5, 5));
        jifCP.add(jpn, BorderLayout.CENTER);
        jifCP.add(jpn2, BorderLayout.SOUTH);
        jpn2.add(jbtnClose);
        jpn2.add(jbtnRe);
        jInternalFrame2.setBounds(0, 0, 300, 400);//亂數計算機
        jifCP2 = jInternalFrame2.getContentPane();
        jInternalFrame2.setLayout(new BorderLayout(5, 5));
        jifCP2.add(jtfCal, BorderLayout.NORTH);
        jifCP2.add(jpnCal, BorderLayout.CENTER);
        jtfCal.setHorizontalAlignment(jtfCal.RIGHT);

        for (int i = 0; i < 9; i++) {
            if (i == 0) {
                randomNum[i] = rdm.nextInt(9);//第一個亂數產生不需要比對
            } else {
                while (flag) {
                    randomNum[i] = rdm.nextInt(9);
                    for (int j = 0; j < i; j++) {
                        if (randomNum[j] == randomNum[i]) {
                            flag = true;
                            break;
                        }
                        flag = false;
                    }
                }
            }
            flag = true;
            jbtn[i] = new JButton(Integer.toString(randomNum[i]));
            jpnCal.add(jbtn[i]);
        }
        for(int i=0;i<jbtn.length;i++){
            jbtn[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JButton tempButton=(JButton) e.getSource();
                    jtfCal.setText(jtfCal.getText()+tempButton.getText());
                }
            });
        }
        jMenuItemLoto.setAccelerator(KeyStroke.getKeyStroke('L', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        for (int i = 0; i < jlb.length; i++) {
            jlb[i] = new JLabel(Integer.toString(i));
            jpn.add(jlb[i]);
        }
        lotoGenerate();
        jMenuItemLoto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jdp.add(jInternalFrame);
                jInternalFrame.setVisible(true);
            }
        });
        jbtnRe.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lotoGenerate();
            }
        });
        jbtnClose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jInternalFrame.setVisible(false);
            }
        });
        jMenuItemCal.setAccelerator(KeyStroke.getKeyStroke('C', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        jMenuItemCal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jdp.add(jInternalFrame2);
                jInternalFrame2.setVisible(true);
            }
        });
        jMenuItemExit.setAccelerator(KeyStroke.getKeyStroke('X', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        jMenuItemExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                loginFrame.reset();
                loginFrame.setVisible(true);
            }
        });
    }

    private void lotoGenerate() {//重複亂數處理
        int i = 0;
        while (i < 6) {
            check[i] = rdm.nextInt(42) + 1;
            int j = 0;
            boolean flag = true;
            while (j < i && flag) {
                if (check[i] == check[j]) { //若亂數重複 則不放入jlb[]
                    flag = false;
                }
                j++;   //true 則往下一個亂數去比對  false則跑出去 重新跑迴圈從j=0
            }
            if (flag) {
                jlb[i].setText(Integer.toString(check[i]));//不放入jlb[]  若為flag=false的話
                i++;
            }
        }
    }


}
