package DataTest;

import Bean.Helper.FeatureHelper;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.*;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

/**
 * Created by garen on 2019/4/2.
 */
public class FLDLTestView extends JDialog{
    private JPanel contentPane;
    private JButton buttonOK; //运行按钮

    private JTextField textField1; //基准文件路径输入区域
    private JTextField textField2; //测试数据路径输入区域
    private JTextField textField3; //模型路径输入区域

    private JTextArea textArea1;

    private JButton clearButton;
    private JButton choosefileButton; //基准文件选择按钮
    private JButton choosefileButton1; //模型选择按钮
    private JButton choosefileButton2; //测试数据集选择按钮


    private JRadioButton variableNameRadioButton;
    private JRadioButton typeRadioButton;
    private JRadioButton operateTypeRadioButton;
    private JRadioButton methodInvokeNameRadioButton;
    private JRadioButton methodInvokeParaRadioButton;
    private JRadioButton forRadioButton;
    private JRadioButton whileRadioButton;
    private JRadioButton switchRadioButton;
    private JRadioButton dowhileRadioButton;
    private JRadioButton IFRadioButton;
    private JRadioButton wholeStructRadioButton;
    private JRadioButton functionRadioButton;

//    private JPanel panel1;
//    private JPanel panel2;
    private FeatureHelper featureHelper = FeatureHelper.NULLFEATURE;


    //private ScrollPane scrollPane;

    public FLDLTestView() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        choosefileButton.addActionListener(new ActionListener() {
           @Override
            public void actionPerformed(ActionEvent e) {
                fileChooser1("xml", "java"); //文件选择（自动过滤掉非“xml”,“java”文件）
            }
        });

        choosefileButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileChooser2();

            }
        });

        choosefileButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileChooser3("mdl");
            }
        });

        variableNameRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                featureHelper = FeatureHelper.VARIABLENAME;
            }
        });

        typeRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                featureHelper = FeatureHelper.TYPE;
            }
        });

        operateTypeRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                featureHelper = FeatureHelper.OPERATETYPE;
            }
        });

        methodInvokeNameRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                featureHelper = FeatureHelper.METHODINVOKENAME;
            }
        });

        methodInvokeParaRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                featureHelper = FeatureHelper.METHODINVOKEPARA;
            }
        });

        forRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                featureHelper = FeatureHelper.FOR;
            }
        });

        whileRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                featureHelper = FeatureHelper.WHILE;
            }
        });

        switchRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                featureHelper = FeatureHelper.SWITCH;
            }
        });

        dowhileRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                featureHelper = FeatureHelper.DOWHILE;
            }
        });

        IFRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                featureHelper = FeatureHelper.IF;
            }
        });

        wholeStructRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                featureHelper = FeatureHelper.WHOLESTRUCTURE;
            }
        });

        functionRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                featureHelper = FeatureHelper.FUNCTION;
            }
        });



        textArea1.setLineWrap(true);// 激活自动换行功能
        textArea1.setWrapStyleWord(true);// 激活断行不断字功能
        outputUI(); //把控制台的内容输出到textarea里面
        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea1.setText("");
            }
        });


        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);


    }

    private void onOK() {
        String benchmarkXMLPath = textField1.getText();
        String test_path = textField2.getText();
        String model_File = textField3.getText();
        if (isEmpty(benchmarkXMLPath)) {
            System.out.println("请选择基准文件路径或者输入正确的基准文件路径");
            return;
        } else {
            if (!isEmpty(test_path)) {
                if (!isEmpty(model_File)) {
                    FLDLDataTester fldlDataTester = new FLDLDataTester();
                    try {
                        fldlDataTester.run(test_path, benchmarkXMLPath,model_File, featureHelper);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else {
                    System.out.println("请选择或输入正确的模型路径");
                }
            } else {
                System.out.println("请选择测试文件路径或者输入正确的测试文件路径");
            }
        }

        // add your code here
        //dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    private void fileChooser1(String... type) {
        JFileChooser chooser = new JFileChooser(textField1.getText());
        if (!isEmpty(type)) {
            StringBuilder suffix = new StringBuilder();
            for (String s : type) {
                suffix.append("." + s);
            }
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    suffix.toString(), type);
            // 设置文件过滤类型
            chooser.setFileFilter(filter);
        } else {
            // 设置选择文件夹
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        }
        // 打开选择器面板
        int returnVal = chooser.showOpenDialog(new JPanel());
        // 保存文件从这里入手，输出的是文件名
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            String path = chooser.getSelectedFile().getAbsolutePath();
            textField1.setText(path);
        }
    }

    private void fileChooser2(String... type) {
        JFileChooser chooser = new JFileChooser(textField2.getText());
        if (!isEmpty(type)) {
            StringBuilder suffix = new StringBuilder();
            for (String s : type) {
                suffix.append("." + s);
            }
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    suffix.toString(), type);
            // 设置文件过滤类型
            chooser.setFileFilter(filter);
        } else {
            // 设置选择文件夹
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        }
        // 打开选择器面板
        int returnVal = chooser.showOpenDialog(new JPanel());
        // 保存文件从这里入手，输出的是文件名
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            String path = chooser.getSelectedFile().getAbsolutePath();
            textField2.setText(path);
        }
    }

    /**
     * 打开文件选择器
     *
     * @param type 要选取的文件类型
     */
    private void fileChooser3(String... type) {
        JFileChooser chooser = new JFileChooser(textField3.getText());
        if (!isEmpty(type)) {
            StringBuilder suffix = new StringBuilder();
            for (String s : type) {
                suffix.append("." + s);
            }
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    suffix.toString(), type);
            // 设置文件过滤类型
            chooser.setFileFilter(filter);
        } else {
            // 设置选择文件夹
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        }
        // 打开选择器面板
        int returnVal = chooser.showOpenDialog(new JPanel());
        // 保存文件从这里入手，输出的是文件名
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            String path = chooser.getSelectedFile().getAbsolutePath();
            textField3.setText(path);
        }
    }

    /**
     * 判断给定字符串是否为空
     *
     * @param s
     * @return
     */
    private boolean isEmpty(String s) {
        return s == null || "".equals(s);
    }

    private boolean isEmpty(String... s) {
        return s == null || s.length == 0 || isEmpty(s[0]);
    }

    /**
     * 捕获控制台输出到GUI界面上
     */
    protected void outputUI() {
        OutputStream textAreaStream = new OutputStream() {
            public void write(int b) throws IOException {
                textArea1.append(String.valueOf((char) b));
            }

            public void write(byte b[]) throws IOException {
                textArea1.append(new String(b));
            }

            public void write(byte b[], int off, int len) throws IOException {
                textArea1.append(new String(b, off, len));
            }
        };
        PrintStream myOut = new PrintStream(textAreaStream);
        System.setOut(myOut);
        System.setErr(myOut);
    }

    public static void main(String[] args) {
        FLDLTestView dialog = new FLDLTestView();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }


}
