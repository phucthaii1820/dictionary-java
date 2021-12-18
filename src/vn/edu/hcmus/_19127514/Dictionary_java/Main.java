package vn.edu.hcmus._19127514.Dictionary_java;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.*;
import java.util.*;
import java.util.List;


public class Main extends JPanel {
    Dictionary dictionary;
    public Main(JFrame frame) throws IOException {
        dictionary = Dictionary.importData("slang.txt");

        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(8,8,8,8));

        JPanel toppanel = new JPanel();
        toppanel.setLayout(new FlowLayout());

        JLabel label = new JLabel("Choice: ");

        String []listChoice = {"1.Search by slang word.", "2. Search by definition.", "3. Show history.", "4. Add new slang word.", "5. Edit slang word.", "6. Delete slang word.", "7. Reset the dictionary to the original.", "8. Random 1 slang word", "9. ", "10. "};
        JComboBox comboBox = new JComboBox(listChoice);

        JButton btnOk = new JButton("OK");

        btnOk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                switch (comboBox.getSelectedIndex()) {
                    case 0: {
                        JFrame frame1 = new JFrame("SEARCH BY SLANG WORD");
                        frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        frame1.setLayout(new BorderLayout());
                        frame1.getRootPane().setBorder(new EmptyBorder(20, 20, 20, 20));
                        frame1.setPreferredSize(new Dimension(400, 300));

                        JPanel jPanelSearch = new JPanel();
                        jPanelSearch.setLayout(new FlowLayout());
                        JTextField tfSearch = new JTextField(20);
                        JButton btnSearch = new JButton("Search");

                        jPanelSearch.add(tfSearch);
                        jPanelSearch.add(btnSearch);

                        JList list = new JList();

                        btnSearch.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                try {
                                    String[] resultSearch = dictionary.search(tfSearch.getText());
                                    list.setListData(resultSearch);
                                }catch (Exception ex) {
                                    JFrame message = new JFrame("Message");
                                    JOptionPane.showMessageDialog(message, "Can't find that word in dictionary");
                                }
                            }
                        });

                        frame1.add(jPanelSearch, BorderLayout.PAGE_START);
                        frame1.add(list, BorderLayout.CENTER);

                        frame1.pack();
                        frame1.setVisible(true);
                        frame1.setDefaultCloseOperation(frame1.DISPOSE_ON_CLOSE);

                        frame1.addWindowListener(new WindowAdapter() {
                            @Override
                            public void windowClosing(WindowEvent e) {
                                frame.setVisible(true);
                            }
                        });

                        break;
                    }
                    case 1: {
                        JFrame frame1 = new JFrame("SEARCH BY DEFINITION");
                        frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        frame1.setLayout(new BorderLayout());
                        frame1.getRootPane().setBorder(new EmptyBorder(20, 20, 20, 20));
                        frame1.setSize(500, 250);

                        JPanel jPanelSearch = new JPanel();
                        jPanelSearch.setLayout(new FlowLayout());
                        JTextField tfSearch = new JTextField(20);
                        JButton btnSearch = new JButton("Search");

                        jPanelSearch.add(tfSearch);
                        jPanelSearch.add(btnSearch);

                        JList list = new JList();
                        JScrollPane s = new JScrollPane(list);


                        btnSearch.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                Dictionary resultSearch = dictionary.searchByDefinition(tfSearch.getText());
                                Vector<String> stringSearch = new Vector<String>();

                                if (resultSearch.getSlang().size() != 0) {
                                    for (Map.Entry<String, Definition> entry : resultSearch.getSlang().entrySet()) {
                                        for(int i = 0; i < entry.getValue().getData().length; i++) {
                                            stringSearch.add(String.format("%s: %s",entry.getKey(), entry.getValue().getData()[i]));
                                        }
                                    }

                                    list.setListData( stringSearch);
                                }
                                else {
                                    JFrame message = new JFrame("Message");
                                    JOptionPane.showMessageDialog(message, "Couldn't find a word with such a definition");
                                }
                            }
                        });

                        frame1.add(jPanelSearch, BorderLayout.PAGE_START);
                        frame1.add(s, BorderLayout.CENTER);

                        frame1.pack();
                        frame1.setVisible(true);
                        frame1.setDefaultCloseOperation(frame1.DISPOSE_ON_CLOSE);

                        frame1.addWindowListener(new WindowAdapter() {
                            @Override
                            public void windowClosing(WindowEvent e) {
                                frame.setVisible(true);
                            }
                        });

                        break;
                    }
                    case 2: {
                        JFrame frame1 = new JFrame("SHOW HISTORY");
                        frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        frame1.setLayout(new BorderLayout());
                        frame1.getRootPane().setBorder(new EmptyBorder(20, 20, 20, 20));
                        frame1.setSize(500, 250);

                        //dictionary.addHistory("hello");
                        String []column = {"Slang word"};
                        Vector<String> vector = new Vector<String>(dictionary.getHistory());
                        JList list;
                        if(vector.size() != 0)
                            list = new JList(vector);
                        else {
                            vector.add("You haven't searched yet");
                            list = new JList(vector);
                        }

                        JScrollPane s = new JScrollPane(list);

                        frame1.add(s, BorderLayout.CENTER);

                        frame1.pack();
                        frame1.setVisible(true);
                        frame1.setDefaultCloseOperation(frame1.DISPOSE_ON_CLOSE);

                        frame1.addWindowListener(new WindowAdapter() {
                            @Override
                            public void windowClosing(WindowEvent e) {
                                frame.setVisible(true);
                            }
                        });

                        break;
                    }
                    case 3: {
                        JFrame frame1 = new JFrame("ADD NEW SLANG");
                        frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        frame1.getRootPane().setBorder(new EmptyBorder(20, 20, 20, 20));
                        frame1.setLayout(new BorderLayout());
                        //-----------
                        JPanel jPanelWord = new JPanel();
                        jPanelWord.setLayout(new BorderLayout());
                        JLabel lbWord = new JLabel("Word ");
                        lbWord.setPreferredSize(new Dimension(96, 20));
                        JTextField tfWord = new JTextField( 20);

                        jPanelWord.add(lbWord, BorderLayout.LINE_START);
                        jPanelWord.add(tfWord, BorderLayout.CENTER);

                        //-----------
                        JLabel lbDefinition = new JLabel("Definition ");
                        lbDefinition.setPreferredSize(new Dimension(100, 20));
                        JTextArea taDefiniton = new JTextArea(10, 20);
                        taDefiniton.setLineWrap(true);
                        taDefiniton.setWrapStyleWord(true);
                        JScrollPane jScrollPane = new JScrollPane(taDefiniton);

                        //-----------

                        JPanel bottompanel = new JPanel();
                        bottompanel.setLayout(new FlowLayout());

                        JButton btnOk = new JButton("OK");
                        btnOk.setActionCommand("OK");
                        bottompanel.add(btnOk);

                        btnOk.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                frame1.setVisible(false);
                                try{
                                    if (dictionary.getSlang().containsKey(tfWord.getText())) {
                                        JFrame frameWord = new JFrame();
                                        frameWord.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                                        frameWord.getRootPane().setBorder(new EmptyBorder(20, 20, 20, 20));
                                        frameWord.setLayout(new GridLayout(2, 1));

                                        JPanel jPanelOverWrite = new JPanel();
                                        jPanelOverWrite.setLayout(new FlowLayout());

                                        JLabel lbOverWrite = new JLabel("Over Write");
                                        JComboBox comboBoxOverWrite = new JComboBox();
                                        String []overWrite = dictionary.search(tfWord.getText());
                                        for(int i = 0; i < overWrite.length; i++)
                                            comboBoxOverWrite.addItem(overWrite[i]);

                                        JButton btnOk = new JButton("OK");

                                        btnOk.addActionListener(new ActionListener() {
                                            @Override
                                            public void actionPerformed(ActionEvent e) {
                                                try {
                                                    dictionary.overWrite(tfWord.getText(), overWrite[comboBoxOverWrite.getSelectedIndex()], taDefiniton.getText());
                                                    JFrame message = new JFrame("Message");
                                                    JOptionPane.showMessageDialog(message, "Over write success!!");
                                                    frameWord.dispose();
                                                    frame1.dispose();
                                                    frame.setVisible(true);
                                                }catch (Exception ex) {
                                                    System.out.println(ex);
                                                }
                                            }
                                        });

                                        jPanelOverWrite.add(lbOverWrite);
                                        jPanelOverWrite.add(comboBoxOverWrite);
                                        jPanelOverWrite.add(btnOk);


                                        JPanel bottomOverWrite = new JPanel();
                                        bottomOverWrite.setLayout(new FlowLayout());

                                        JButton btnDuplicate = new JButton("Duplicate");
                                        bottomOverWrite.add(btnDuplicate);

                                        btnDuplicate.addActionListener(new ActionListener() {
                                            @Override
                                            public void actionPerformed(ActionEvent e) {
                                                try{
                                                    dictionary.addSlang(tfWord.getText(), taDefiniton.getText());
                                                    JFrame message = new JFrame("Message");
                                                    JOptionPane.showMessageDialog(message, "Duplicate success!!");
                                                    frameWord.dispose();
                                                    frame1.dispose();
                                                    frame.setVisible(true);
                                                }catch (Exception ex) {
                                                    System.out.println(ex);
                                                }
                                            }
                                        });

                                        frameWord.add(jPanelOverWrite);
                                        frameWord.add(bottomOverWrite);

                                        frameWord.pack();
                                        frameWord.setVisible(true);
                                        frameWord.setDefaultCloseOperation(frameWord.DISPOSE_ON_CLOSE);

                                        frameWord.addWindowListener(new WindowAdapter() {
                                            @Override
                                            public void windowClosing(WindowEvent e) {
                                                frame1.setVisible(true);
                                            }
                                        });

                                    }
                                    else {
                                        dictionary.addSlang(tfWord.getText(), taDefiniton.getText());
                                        JFrame message = new JFrame("Message");
                                        JOptionPane.showMessageDialog(message, "Add success!!");
                                        frame.setVisible(true);
                                    }
                                }catch (Exception ex) {
                                }
                            }
                        });

                        frame1.add(jPanelWord, BorderLayout.PAGE_START);
                        frame1.add(lbDefinition, BorderLayout.LINE_START);
                        frame1.add(jScrollPane, BorderLayout.CENTER);
                        frame1.add(bottompanel, BorderLayout.PAGE_END);

                        frame1.pack();
                        frame1.setVisible(true);
                        frame1.setDefaultCloseOperation(frame.DISPOSE_ON_CLOSE);

                        frame1.addWindowListener(new WindowAdapter() {
                            @Override
                            public void windowClosing(WindowEvent e) {
                                frame.setVisible(true);
                            }
                        });

                        break;
                    }
                    case 4: {
                        JFrame frame1 = new JFrame("EDIT SLANG WORD");
                        frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        frame1.setLayout(new FlowLayout());
                        frame1.getRootPane().setBorder(new EmptyBorder(20, 20, 20, 20));

                        JPanel jPanelSearch = new JPanel();
                        jPanelSearch.setLayout(new FlowLayout());
                        JTextField tfSearch = new JTextField(20);
                        JButton btnSearch = new JButton("Search");

                        jPanelSearch.add(tfSearch);
                        jPanelSearch.add(btnSearch);

                        btnSearch.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                try {
                                    frame1.setVisible(false);

                                    String[] resultSearch = dictionary.search(tfSearch.getText());
                                    String wordOld = tfSearch.getText();
                                    JFrame frameEdit = new JFrame("EDIT");
                                    frameEdit.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                                    frameEdit.getRootPane().setBorder(new EmptyBorder(20, 20, 20, 20));
                                    frameEdit.setLayout(new BorderLayout());
                                    //-----------
                                    JPanel jPanelWord = new JPanel();
                                    jPanelWord.setLayout(new BorderLayout());
                                    JLabel lbWord = new JLabel("Word ");
                                    lbWord.setPreferredSize(new Dimension(96, 20));
                                    JTextField tfWord = new JTextField( tfSearch.getText(), 20);

                                    jPanelWord.add(lbWord, BorderLayout.LINE_START);
                                    jPanelWord.add(tfWord, BorderLayout.CENTER);

                                    //-----------
                                    JLabel lbDefinition = new JLabel("Definition ");
                                    lbDefinition.setPreferredSize(new Dimension(100, 20));
                                    JTextArea taDefiniton = new JTextArea(10, 20);
                                    taDefiniton.setLineWrap(true);
                                    taDefiniton.setWrapStyleWord(true);
                                    JScrollPane jScrollPane = new JScrollPane(taDefiniton);

                                    for(int i = 0; i < resultSearch.length; i++) {
                                        taDefiniton.append(resultSearch[i]);
                                        if(i != resultSearch.length - 1)
                                            taDefiniton.append("\n");
                                    }

                                    //-----------

                                    JPanel bottompanel = new JPanel();
                                    bottompanel.setLayout(new FlowLayout());

                                    JButton btnOk = new JButton("OK");
                                    btnOk.setActionCommand("OK");
                                    bottompanel.add(btnOk);

                                    btnOk.addActionListener(new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent e) {
                                            try{
                                                String []tempResult = taDefiniton.getText().split("\n");
                                                dictionary.editSlang(wordOld, tfWord.getText(),tempResult);

                                                JFrame message = new JFrame("Message");
                                                JOptionPane.showMessageDialog(message, "Edit success!!");
                                                frameEdit.dispose();
                                                frame1.dispose();
                                                frame.setVisible(true);
                                            }catch (Exception ex){
                                                JFrame message = new JFrame("Message");
                                                JOptionPane.showMessageDialog(message, "Edit fail!!");
                                            }
                                        }
                                    });

                                    frameEdit.add(jPanelWord, BorderLayout.PAGE_START);
                                    frameEdit.add(lbDefinition, BorderLayout.LINE_START);
                                    frameEdit.add(taDefiniton, BorderLayout.CENTER);
                                    frameEdit.add(bottompanel, BorderLayout.PAGE_END);

                                    frameEdit.pack();
                                    frameEdit.setVisible(true);
                                    frameEdit.setDefaultCloseOperation(frameEdit.DISPOSE_ON_CLOSE);

                                    frameEdit.addWindowListener(new WindowAdapter() {
                                        @Override
                                        public void windowClosing(WindowEvent e) {
                                            frameEdit.dispose();
                                            frame1.setVisible(true);
                                        }
                                    });

                                }catch (Exception ex) {
                                    JFrame message = new JFrame("Message");
                                    JOptionPane.showMessageDialog(message, "Can't find that word in dictionary!!");
                                    frame1.setVisible(true);
                                }
                            }
                        });

                        frame1.add(jPanelSearch);

                        frame1.pack();
                        frame1.setVisible(true);
                        frame1.setDefaultCloseOperation(frame1.DISPOSE_ON_CLOSE);

                        frame1.addWindowListener(new WindowAdapter() {
                            @Override
                            public void windowClosing(WindowEvent e) {
                                frame.setVisible(true);
                            }
                        });

                        break;
                    }
                    case 5: {
                        JFrame frame1 = new JFrame("DELETE SLANG WORD");
                        frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        frame1.setLayout(new FlowLayout());
                        frame1.getRootPane().setBorder(new EmptyBorder(20, 20, 20, 20));

                        JPanel jPanelSearch = new JPanel();
                        jPanelSearch.setLayout(new FlowLayout());
                        JTextField tfSearch = new JTextField(20);
                        JButton btnSearch = new JButton("Search");

                        jPanelSearch.add(tfSearch);
                        jPanelSearch.add(btnSearch);

                        btnSearch.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                try{
                                    frame1.setVisible(false);
                                    String[] temmp = dictionary.search(tfSearch.getText());

                                    JFrame frameConfirm = new JFrame("CONFIRM");
                                    frameConfirm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                                    frameConfirm.setLayout(new GridLayout(2, 1));

                                    JPanel jPanelLbConfirm = new JPanel();
                                    jPanelLbConfirm.setLayout(new FlowLayout());
                                    JLabel lbConfirm = new JLabel("Are you sure ?");
                                    jPanelLbConfirm.add(lbConfirm);

                                    JPanel jPanelConfirm = new JPanel();
                                    jPanelConfirm.setLayout(new FlowLayout());
                                    JButton btnConfirm = new JButton("CONFIRM");
                                    JButton btnCancel = new JButton("CANCEL");

                                    jPanelConfirm.add(btnConfirm);
                                    jPanelConfirm.add(btnCancel);

                                    btnConfirm.addActionListener(new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent e) {
                                            try {
                                                dictionary.getSlang().remove(tfSearch.getText());
                                                frameConfirm.dispose();
                                                frame1.dispose();

                                                JFrame message = new JFrame("Message");
                                                JOptionPane.showMessageDialog(message, "Delete success!!");

                                                frame.setVisible(true);
                                            } catch (Exception ex) {
                                                JFrame message = new JFrame("Message");
                                                JOptionPane.showMessageDialog(message, "Delete fail!!");
                                            }
                                        }
                                    });

                                    btnCancel.addActionListener(new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent e) {
                                            frameConfirm.dispose();
                                            frame1.setVisible(true);
                                        }
                                    });

                                    frameConfirm.add(jPanelLbConfirm);
                                    frameConfirm.add(jPanelConfirm);

                                    frameConfirm.pack();
                                    frameConfirm.setVisible(true);
                                    frameConfirm.setDefaultCloseOperation(frameConfirm.DISPOSE_ON_CLOSE);
                                }
                                catch (Exception ex) {
                                    JFrame message = new JFrame("Message");
                                    JOptionPane.showMessageDialog(message, "Can't find that word in dictionary");
                                    frame1.setVisible(true);
                                }
                            }
                        });

                        frame1.add(jPanelSearch);

                        frame1.pack();
                        frame1.setVisible(true);
                        frame1.setDefaultCloseOperation(frame1.DISPOSE_ON_CLOSE);

                        frame1.addWindowListener(new WindowAdapter() {
                            @Override
                            public void windowClosing(WindowEvent e) {
                                frame.setVisible(true);
                            }
                        });

                        break;
                    }
                    case 6: {
                        JFrame frameConfirm = new JFrame("RESET THE DICTIONARY TO THE ORIGINAL");
                        frameConfirm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        frameConfirm.setLayout(new GridLayout(2, 1));

                        JPanel jPanelLbConfirm = new JPanel();
                        jPanelLbConfirm.setLayout(new FlowLayout());
                        JLabel lbConfirm = new JLabel("Are you sure ?");
                        jPanelLbConfirm.add(lbConfirm);

                        JPanel jPanelConfirm = new JPanel();
                        jPanelConfirm.setLayout(new FlowLayout());
                        JButton btnConfirm = new JButton("CONFIRM");
                        JButton btnCancel = new JButton("CANCEL");

                        jPanelConfirm.add(btnConfirm);
                        jPanelConfirm.add(btnCancel);

                        btnConfirm.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                try{
                                    dictionary = Dictionary.importData("slang.txt");
                                    JFrame message = new JFrame("Message");
                                    JOptionPane.showMessageDialog(message, "Reset success!!");

                                    frameConfirm.dispose();
                                    frame.setVisible(true);
                                }catch (Exception ex) {
                                    JFrame message = new JFrame("Message");
                                    JOptionPane.showMessageDialog(message, "Reset fail!!");
                                }
                            }
                        });

                        btnCancel.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                frameConfirm.dispose();
                                frame.setVisible(true);
                            }
                        });

                        frameConfirm.add(jPanelLbConfirm);
                        frameConfirm.add(jPanelConfirm);

                        frameConfirm.pack();
                        frameConfirm.setVisible(true);
                        frameConfirm.setDefaultCloseOperation(frameConfirm.DISPOSE_ON_CLOSE);

                        frameConfirm.addWindowListener(new WindowAdapter() {
                            @Override
                            public void windowClosing(WindowEvent e) {
                                frame.setVisible(true);
                            }
                        });

                        break;
                    }
                }
            }
        });

        toppanel.add(label);
        toppanel.add(comboBox);
        toppanel.add(btnOk);

        add(toppanel, BorderLayout.CENTER);
    }

    private static void createAndShowGUI() throws IOException {
        //Make sure we have nice window decorations.
        JFrame.setDefaultLookAndFeelDecorated(true);

        //Create and set up the window.
        JFrame frame = new JFrame("Information");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        Main main = new Main(frame);
        main.setOpaque(true);

        frame.setContentPane(main);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }



    public static void main(String[] args) throws IOException {
        createAndShowGUI();
    }
}
