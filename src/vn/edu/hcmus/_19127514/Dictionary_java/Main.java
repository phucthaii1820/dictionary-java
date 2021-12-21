package vn.edu.hcmus._19127514.Dictionary_java;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
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
        dictionary = Dictionary.connectData("data.dat");
        //dictionary = Dictionary.importData("slang.txt");
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(8,8,8,8));

        JPanel toppanel = new JPanel();
        toppanel.setLayout(new FlowLayout());

        JLabel label = new JLabel("Choice: ");

        String []listChoice = {"1.Search by slang word.", "2. Search by definition.", "3. Show history.", "4. Add new slang word.", "5. Edit slang word.", "6. Delete slang word.", "7. Reset the dictionary to the original.", "8. Random 1 slang word", "9. Random word quiz", "10. Random definition quiz"};
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
                        DefaultTableModel defaultTableModel = new DefaultTableModel();
                        defaultTableModel.addColumn("SST");
                        defaultTableModel.addColumn("Mean");

                        JTable table = new JTable(defaultTableModel);
                        JScrollPane scrollPane = new JScrollPane(table);

                        btnSearch.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                try {
                                    if(defaultTableModel.getRowCount() > 0) {
                                        for(int i = defaultTableModel.getRowCount() - 1; i >= 0; i--)
                                            defaultTableModel.removeRow(i);
                                    }

                                    String[] resultSearch = dictionary.search(tfSearch.getText());
                                    dictionary.addHistory(tfSearch.getText());
                                    //list.setListData(resultSearch);
                                    for(int i = resultSearch.length - 1; i >= 0; i--) {
                                        defaultTableModel.insertRow(0, new Object[] {i + 1, resultSearch[i]});
                                    }
                                }catch (Exception ex) {
                                    JFrame message = new JFrame("Message");
                                    JOptionPane.showMessageDialog(message, "Can't find that word in dictionary");
                                }
                            }
                        });

                        frame1.add(jPanelSearch, BorderLayout.PAGE_START);
                        //frame1.add(list, BorderLayout.CENTER);
                        frame1.add(scrollPane, BorderLayout.CENTER);

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

                        //JList list = new JList();
                        DefaultTableModel defaultTableModel = new DefaultTableModel();
                        defaultTableModel.addColumn("STT");
                        defaultTableModel.addColumn("Word");
                        defaultTableModel.addColumn("Mean");

                        JTable table = new JTable(defaultTableModel);
                        JScrollPane scrollPane = new JScrollPane(table);


                        btnSearch.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                if(tfSearch.getText().equals("")) {
                                    JFrame message = new JFrame("Message");
                                    JOptionPane.showMessageDialog(message, "You can not leave it blank");
                                }
                                else {
                                    dictionary.addHistory(tfSearch.getText());

                                    if(defaultTableModel.getRowCount() > 0) {
                                        for(int i = defaultTableModel.getRowCount() - 1; i >= 0; i--)
                                            defaultTableModel.removeRow(i);
                                    }

                                    Dictionary resultSearch = dictionary.searchByDefinition(tfSearch.getText());
                                    Vector<String> stringSearch = new Vector<String>();

                                    int index = 1;
                                    if (resultSearch.getSlang().size() != 0) {
                                        for (Map.Entry<String, Definition> entry : resultSearch.getSlang().entrySet()) {
                                            for(int i = 0; i < entry.getValue().getData().length; i++) {
                                                stringSearch.add(String.format("%s: %s",entry.getKey(), entry.getValue().getData()[i]));
                                                defaultTableModel.insertRow(index - 1, new Object[] {index ++,entry.getKey() , entry.getValue().getData()[i]});
                                            }
                                        }

                                        //list.setListData( stringSearch);
                                    }
                                    else {
                                        JFrame message = new JFrame("Message");
                                        JOptionPane.showMessageDialog(message, "Couldn't find a word with such a definition");
                                    }
                                }
                            }
                        });

                        frame1.add(jPanelSearch, BorderLayout.PAGE_START);
                        frame1.add(scrollPane, BorderLayout.CENTER);

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
//                        String []column = {"Slang word"};
//                        Vector<String> vector = new Vector<String>(dictionary.getHistory());
//                        JList list;
//                        if(vector.size() != 0)
//                            list = new JList(vector);
//                        else {
//                            vector.add("You haven't searched yet");
//                            list = new JList(vector);
//                        }
//
//                        JScrollPane s = new JScrollPane(list);

                        List<String> history = dictionary.getHistory();

                        DefaultTableModel defaultTableModel = new DefaultTableModel();
                        defaultTableModel.addColumn("STT");
                        defaultTableModel.addColumn("Word");

                        JTable table = new JTable(defaultTableModel);
                        JScrollPane scrollPane = new JScrollPane(table);

                        if (history.size() != 0) {
                            for(int i = 0; i < history.size(); i++)
                                defaultTableModel.insertRow(i, new Object[] {i + 1, history.get(i)});
                        }

                        frame1.add(scrollPane, BorderLayout.CENTER);

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
                                    if(tfWord.getText().equals("") || taDefiniton.getText().equals("")) {
                                        JFrame message = new JFrame("Message");
                                        JOptionPane.showMessageDialog(message, "You can not leave it blank");
                                        frame1.setVisible(true);
                                    } else {
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
                                                if(tfWord.getText().equals("") || taDefiniton.getText().equals("")) {
                                                    JFrame message = new JFrame("Message");
                                                    JOptionPane.showMessageDialog(message, "You can not leave it blank");
                                                }
                                                else {
                                                    String []tempResult = taDefiniton.getText().split("\n");
                                                    dictionary.editSlang(wordOld, tfWord.getText(),tempResult);

                                                    JFrame message = new JFrame("Message");
                                                    JOptionPane.showMessageDialog(message, "Edit success!!");
                                                    frameEdit.dispose();
                                                    frame1.dispose();
                                                    frame.setVisible(true);
                                                }
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
                        JButton btnSearch = new JButton("Delete");

                        jPanelSearch.add(tfSearch);
                        jPanelSearch.add(btnSearch);

                        btnSearch.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                try{
                                    if(tfSearch.getText().equals("")) {

                                    }
                                    else {

                                    }
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

                                    frameConfirm.addWindowListener(new WindowAdapter() {
                                        @Override
                                        public void windowClosing(WindowEvent e) {
                                            frameConfirm.dispose();
                                            frame1.setVisible(true);
                                        }
                                    });
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
                    case 7: {
                        JFrame frame1 = new JFrame("ON THIS DAY SLANG WORD");
                        frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        frame1.setLayout(new GridLayout(3, 1));
                        frame1.getRootPane().setBorder(new EmptyBorder(20, 20, 20, 20));
                        frame1.setPreferredSize(new Dimension(400, 250));

                        String newWord = dictionary.randomKey();

                        JPanel top = new JPanel();
                        top.setLayout(new FlowLayout());
                        JLabel jLabelTitle = new JLabel("ON THIS DAY SLANG WORD");
                        jLabelTitle.setFont(new Font("Calibri", Font.PLAIN, 20));
                        top.add(jLabelTitle);

                        JPanel center = new JPanel();
                        center.setLayout(new FlowLayout());
                        JLabel jLabelKey = new JLabel(newWord);
                        jLabelKey.setForeground(Color.RED);

                        JLabel labelTemp = new JLabel(" is mean ");

                        JLabel jLabelValue = new JLabel(dictionary.search(newWord)[0]);
                        jLabelValue.setForeground(Color.RED);
                        center.add(jLabelKey);
                        center.add(labelTemp);
                        center.add(jLabelValue);

                        JPanel end = new JPanel(new FlowLayout());

                        Button btnNext = new Button("NEXT");
                        btnNext.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                String newWordTemp = dictionary.randomKey();
                                jLabelKey.setText(newWordTemp);
                                jLabelValue.setText(dictionary.search(newWordTemp)[0]);
                            }
                        });

                        end.add(btnNext);


                        frame1.add(top);
                        frame1.add(center);
                        frame1.add(end);

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
                    case 8: {
                        JFrame frame1 = new JFrame("RANDOM WORD QUIZ");
                        frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        frame1.setLayout(new GridLayout(2, 1));
                        frame1.getRootPane().setBorder(new EmptyBorder(20, 20, 20, 20));
                        frame1.setPreferredSize(new Dimension(500, 200));

                        String newWord = dictionary.randomKey();
                        String result = dictionary.Slang.get(newWord).getData()[0];

                        List<String> temp = new ArrayList<String>();
                        temp.add(result);
                        temp.add(dictionary.randomValue());
                        temp.add(dictionary.randomValue());
                        temp.add(dictionary.randomValue());

                        for(int i = 0; i < 3; i++) {
                            int random = (int)(Math.random() * 4);
                            String a = temp.get(i);
                            temp.set(i, temp.get(random));
                            temp.set(random, a);
                        }

                        JPanel top = new JPanel();
                        top.setLayout(new FlowLayout());
                        JLabel jLabelTitle = new JLabel(newWord);
                        jLabelTitle.setFont(new Font("Calibri", Font.PLAIN, 20));
                        top.add(jLabelTitle);

                        JPanel center = new JPanel();
                        center.setLayout(new GridLayout(2, 2));
                        Button btn1 = new Button(temp.get(0));
                        Button btn2 = new Button(temp.get(1));
                        Button btn3 = new Button(temp.get(2));
                        Button btn4 = new Button(temp.get(3));

                        btn1.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                if(result.equals(btn1.getLabel())) {
                                    btn1.setForeground(Color.GREEN);
                                    JFrame message = new JFrame("Message");
                                    JOptionPane.showMessageDialog(message, "correct, good job!");
                                    frame1.dispose();
                                    frame.setVisible(true);
                                }
                                else {
                                    btn1.setForeground(Color.RED);
                                    JFrame message = new JFrame("Message");
                                    JOptionPane.showMessageDialog(message, "uncorrect, try again!");
                                }
                            }
                        });

                        btn2.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                if(result.equals(btn2.getLabel())) {
                                    btn2.setForeground(Color.GREEN);
                                    JFrame message = new JFrame("Message");
                                    JOptionPane.showMessageDialog(message, "correct, good job!");
                                    frame1.dispose();
                                    frame.setVisible(true);
                                }
                                else {
                                    btn2.setForeground(Color.RED);
                                    JFrame message = new JFrame("Message");
                                    JOptionPane.showMessageDialog(message, "uncorrect, try again!");
                                }
                            }
                        });

                        btn3.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                if(result.equals(btn3.getLabel())) {
                                    btn3.setForeground(Color.GREEN);
                                    JFrame message = new JFrame("Message");
                                    JOptionPane.showMessageDialog(message, "correct, good job!");
                                    frame1.dispose();
                                    frame.setVisible(true);
                                }
                                else {
                                    btn3.setForeground(Color.RED);
                                    JFrame message = new JFrame("Message");
                                    JOptionPane.showMessageDialog(message, "uncorrect, try again!");
                                }
                            }
                        });

                        btn4.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                if(result.equals(btn4.getLabel())) {
                                    btn4.setForeground(Color.GREEN);
                                    JFrame message = new JFrame("Message");
                                    JOptionPane.showMessageDialog(message, "correct, good job!");
                                    frame1.dispose();
                                    frame.setVisible(true);


                                }
                                else {
                                    btn4.setForeground(Color.RED);
                                    JFrame message = new JFrame("Message");
                                    JOptionPane.showMessageDialog(message, "uncorrect, try again!");
                                }
                            }
                        });

                        center.add(btn1);
                        center.add(btn2);
                        center.add(btn3);
                        center.add(btn4);

//                        JPanel end = new JPanel(new FlowLayout());
//
//                        Button btnNext = new Button("NEXT");
//                        btnNext.addActionListener(new ActionListener() {
//                            @Override
//                            public void actionPerformed(ActionEvent e) {
//                                String newWordTemp = dictionary.randomKey();
//                            }
//                        });

                        //end.add(btnNext);


                        frame1.add(top);
                        frame1.add(center);
                        //frame1.add(end);

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
                    case 9: {
                        JFrame frame1 = new JFrame("RANDOM DEFINITION QUIZ");
                        frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        frame1.setLayout(new GridLayout(2, 1));
                        frame1.getRootPane().setBorder(new EmptyBorder(20, 20, 20, 20));
                        frame1.setPreferredSize(new Dimension(500, 200));

                        String newWord = dictionary.randomKey();
                        String result = dictionary.Slang.get(newWord).getData()[0];

                        List<String> temp = new ArrayList<String>();
                        temp.add(newWord);
                        temp.add(dictionary.randomKey());
                        temp.add(dictionary.randomKey());
                        temp.add(dictionary.randomKey());

                        for(int i = 0; i < 3; i++) {
                            int random = (int)(Math.random() * 4);
                            String a = temp.get(i);
                            temp.set(i, temp.get(random));
                            temp.set(random, a);
                        }

                        JPanel top = new JPanel();
                        top.setLayout(new FlowLayout());
                        JLabel jLabelTitle = new JLabel(result);
                        jLabelTitle.setFont(new Font("Calibri", Font.PLAIN, 20));
                        top.add(jLabelTitle);

                        JPanel center = new JPanel();
                        center.setLayout(new GridLayout(2, 2));
                        Button btn1 = new Button(temp.get(0));
                        Button btn2 = new Button(temp.get(1));
                        Button btn3 = new Button(temp.get(2));
                        Button btn4 = new Button(temp.get(3));

                        btn1.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                if(newWord.equals(btn1.getLabel())) {
                                    btn1.setForeground(Color.GREEN);
                                    JFrame message = new JFrame("Message");
                                    JOptionPane.showMessageDialog(message, "correct, good job!");
                                    frame1.dispose();
                                    frame.setVisible(true);
                                }
                                else {
                                    btn1.setForeground(Color.RED);
                                    JFrame message = new JFrame("Message");
                                    JOptionPane.showMessageDialog(message, "uncorrect, try again!");
                                }
                            }
                        });

                        btn2.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                if(newWord.equals(btn2.getLabel())) {
                                    btn2.setForeground(Color.GREEN);
                                    JFrame message = new JFrame("Message");
                                    JOptionPane.showMessageDialog(message, "correct, good job!");
                                    frame1.dispose();
                                    frame.setVisible(true);
                                }
                                else {
                                    btn2.setForeground(Color.RED);
                                    JFrame message = new JFrame("Message");
                                    JOptionPane.showMessageDialog(message, "uncorrect, try again!");
                                }
                            }
                        });

                        btn3.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                if(newWord.equals(btn3.getLabel())) {
                                    btn3.setForeground(Color.GREEN);
                                    JFrame message = new JFrame("Message");
                                    JOptionPane.showMessageDialog(message, "correct, good job!");
                                    frame1.dispose();
                                    frame.setVisible(true);
                                }
                                else {
                                    btn3.setForeground(Color.RED);
                                    JFrame message = new JFrame("Message");
                                    JOptionPane.showMessageDialog(message, "uncorrect, try again!");
                                }
                            }
                        });

                        btn4.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                if(newWord.equals(btn4.getLabel())) {
                                    btn4.setForeground(Color.GREEN);
                                    JFrame message = new JFrame("Message");
                                    JOptionPane.showMessageDialog(message, "correct, good job!");
                                    frame1.dispose();
                                    frame.setVisible(true);


                                }
                                else {
                                    btn4.setForeground(Color.RED);
                                    JFrame message = new JFrame("Message");
                                    JOptionPane.showMessageDialog(message, "uncorrect, try again!");
                                }
                            }
                        });

                        center.add(btn1);
                        center.add(btn2);
                        center.add(btn3);
                        center.add(btn4);

//                        JPanel end = new JPanel(new FlowLayout());
//
//                        Button btnNext = new Button("NEXT");
//                        btnNext.addActionListener(new ActionListener() {
//                            @Override
//                            public void actionPerformed(ActionEvent e) {
//                                String newWordTemp = dictionary.randomKey();
//                            }
//                        });

                        //end.add(btnNext);


                        frame1.add(top);
                        frame1.add(center);
                        //frame1.add(end);

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
                }
            }
        });

        toppanel.add(label);
        toppanel.add(comboBox);
        toppanel.add(btnOk);

        add(toppanel, BorderLayout.CENTER);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    dictionary.saveData("data.dat");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
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
