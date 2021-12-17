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


//        JPanel bottompanel = new JPanel();
//        bottompanel.setLayout(new FlowLayout());
//
//        JButton btnOk = new JButton("OK");
//        btnOk.setActionCommand("OK");
//        bottompanel.add(btnOk);

        btnOk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch (comboBox.getSelectedIndex()) {
                    case 0: {
                        JFrame frame = new JFrame("SEARCH BY SLANG WORD");
                        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        frame.setLayout(new BorderLayout());
                        frame.getRootPane().setBorder(new EmptyBorder(20, 20, 20, 20));
                        frame.setPreferredSize(new Dimension(400, 300));

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
                                String[] resultSearch = dictionary.search(tfSearch.getText());
                                list.setListData(resultSearch);
                            }
                        });

                        frame.add(jPanelSearch, BorderLayout.PAGE_START);
                        frame.add(list, BorderLayout.CENTER);

                        frame.pack();
                        frame.setVisible(true);
                        frame.setDefaultCloseOperation(frame.DISPOSE_ON_CLOSE);

//                        frame.addWindowListener(new WindowAdapter() {
//                            @Override
//                            public void windowClosing(WindowEvent e) {
//                                frame.setVisible(true);
//                            }
//                        });

                        break;
                    }
                    case 1: {
                        JFrame frame = new JFrame("SEARCH BY DEFINITION");
                        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        frame.setLayout(new BorderLayout());
                        frame.getRootPane().setBorder(new EmptyBorder(20, 20, 20, 20));
                        frame.setSize(500, 250);

                        JPanel jPanelSearch = new JPanel();
                        jPanelSearch.setLayout(new FlowLayout());
                        JTextField tfSearch = new JTextField(20);
                        JButton btnSearch = new JButton("Search");

                        jPanelSearch.add(tfSearch);
                        jPanelSearch.add(btnSearch);

                        JList list = new JList();

//                        String [][] data = {{""}};
//                        String[] column = { "Slang word", "Definition" };
//                        JTable jTable = new JTable(data, column);
                        JScrollPane s = new JScrollPane(list);


                        btnSearch.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                Dictionary resultSearch = dictionary.searchByDefinition(tfSearch.getText());
                                //List<String> stringSearch = new ArrayList<String>();
                                Vector<String> stringSearch = new Vector<String>();

                                for (Map.Entry<String, Definition> entry : resultSearch.getSlang().entrySet()) {
                                    for(int i = 0; i < entry.getValue().getData().length; i++) {
                                        stringSearch.add(String.format("%s: %s",entry.getKey(), entry.getValue().getData()[i]));
                                    }
                                }

                                list.setListData( stringSearch);
                            }
                        });

                        frame.add(jPanelSearch, BorderLayout.PAGE_START);
                        frame.add(s, BorderLayout.CENTER);

                        frame.pack();
                        frame.setVisible(true);
                        frame.setDefaultCloseOperation(frame.DISPOSE_ON_CLOSE);

//                        frame.addWindowListener(new WindowAdapter() {
//                            @Override
//                            public void windowClosing(WindowEvent e) {
//                                frame.setVisible(true);
//                            }
//                        });

                        break;
                    }
                    case 2: {
                        JFrame frame = new JFrame("SHOW HISTORY");
                        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        frame.setLayout(new BorderLayout());
                        frame.getRootPane().setBorder(new EmptyBorder(20, 20, 20, 20));
                        frame.setSize(500, 250);

                        dictionary.addHistory("hello");
                        String []column = {"Slang word"};
                        Vector<String> vector = new Vector<String>(dictionary.getHistory());
                        JList list = new JList(vector);

//                        String [][] data = {{""}};
//                        String[] column = { "Slang word", "Definition" };
//                        JTable jTable = new JTable(data, column);
                        JScrollPane s = new JScrollPane(list);

                        frame.add(s, BorderLayout.CENTER);

                        frame.pack();
                        frame.setVisible(true);
                        frame.setDefaultCloseOperation(frame.DISPOSE_ON_CLOSE);

//                        frame.addWindowListener(new WindowAdapter() {
//                            @Override
//                            public void windowClosing(WindowEvent e) {
//                                frame.setVisible(true);
//                            }
//                        });

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
