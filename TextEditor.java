package editor;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextEditor extends JFrame {

    String path;
    File file;

    List<Integer> startIndexes = new ArrayList<>();
    List<Integer> endIndexes = new ArrayList<>();

    int count;
    Integer start = 0;
    Integer end = 0;

    Container mainContainer;
    JPanel topPanel;

    JMenuBar menuBar;
    JMenu fileMenu;
    JMenu searchMenu;
    JMenuItem openMenuItem;
    JMenuItem saveMenuItem;
    JMenuItem exitMenuItem;
    JMenuItem startSearchMenuItem;
    JMenuItem previousSearchMenuItem;
    JMenuItem nextMatchMenuItem;
    JMenuItem useRegexpMenuItem;

    JTextField searchField;
    JTextArea textArea;
    JScrollPane scrollableTextArea;
    JButton openBtn;
    JButton saveBtn;
    JButton searchBtn;
    JButton nextBtn;
    JButton previousBtn;
    JCheckBox regexpBox;

    JFileChooser fileChooser;

    public TextEditor() {

        // Frame
        setTitle("Text Editor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 500);
        setVisible(true);

        // Main Panel
        mainContainer = getContentPane();
        mainContainer.setLayout(new BorderLayout(8, 6));
        getRootPane().setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.green));

        // File Menu creating
        fileMenu = new JMenu("File");
        fileMenu.setName("MenuFile");
        fileMenu.setMnemonic(KeyEvent.VK_F);

        // Search Menu creating
        searchMenu = new JMenu("Search");
        searchMenu.setName("MenuSearch");
        searchMenu.setMnemonic(KeyEvent.VK_S);

        // File Menu Items creating
        // Open Menu Item creating
        openMenuItem = new JMenuItem("Open");
        openMenuItem.setName("MenuOpen");
        openMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));

        // Save Menu Item creating
        saveMenuItem = new JMenuItem("Save");
        saveMenuItem.setName("MenuSave");
        saveMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));

        // Exit Menu Item creating
        exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.setName("MenuExit");
        exitMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_MASK));

        // Search Menu Items creating
        startSearchMenuItem = new JMenuItem("Start search");
        startSearchMenuItem.setName("MenuStartSearch");
        startSearchMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_MASK));

        // Previous Menu Item creating
        previousSearchMenuItem = new JMenuItem("Previous search");
        previousSearchMenuItem.setName("MenuPreviousMatch");
        previousSearchMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, InputEvent.CTRL_MASK));

        // Next Menu Item creating
        nextMatchMenuItem = new JMenuItem("Next match");
        nextMatchMenuItem.setName("MenuNextMatch");
        nextMatchMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, InputEvent.CTRL_MASK));

        // Use Regexp Menu Item creating
        useRegexpMenuItem = new JMenuItem("Use regular expressions");
        useRegexpMenuItem.setName("MenuUseRegExp");
        useRegexpMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_MASK));

        // Add Menus to MenuBar
        menuBar = new JMenuBar();
        menuBar.add(fileMenu);
        menuBar.add(searchMenu);

        // Add Menu Items to File Menu
        fileMenu.add(openMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(exitMenuItem);

        // Add Menu Items to Search Menu
        searchMenu.add(startSearchMenuItem);
        searchMenu.add(previousSearchMenuItem);
        searchMenu.add(nextMatchMenuItem);
        searchMenu.add(useRegexpMenuItem);

        // Menu Bar creating
        setJMenuBar(menuBar);

        // File Chooser creating
        fileChooser = new JFileChooser("C:\\Users\\t2803\\IdeaProjects\\Origin\\src\\editor");
        fileChooser.setName("FileChooser");

        // Search Field creating
        searchField = new JTextField(30);
        searchField.setName("SearchField");

        // Text Area creating
        textArea = new JTextArea(20, 40);
        textArea.setName("TextArea");

        // Scroll Pane creating
        scrollableTextArea = new JScrollPane(textArea);
        scrollableTextArea.setName("ScrollPane");
        scrollableTextArea.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollableTextArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // Open Button creating
        openBtn = new JButton(new ImageIcon("src\\editor\\Images\\open.png"));
        openBtn.setName("OpenButton");
        openBtn.addActionListener(actionEvent -> open());

        // Save Button creating
        saveBtn = new JButton(new ImageIcon("src\\editor\\Images\\save.png"));
        saveBtn.setName("SaveButton");
        saveBtn.addActionListener(actionEvent -> save());

        // Search Button creating
        searchBtn = new JButton(new ImageIcon("src\\editor\\Images\\search.png"));
        searchBtn.setName("StartSearchButton");
        searchBtn.addActionListener(actionEvent -> search());
        startSearchMenuItem.addActionListener(actionEvent -> search());

        // Next Button creating
        nextBtn = new JButton(new ImageIcon("src\\editor\\Images\\next.png"));
        nextBtn.setName("NextMatchButton");
        nextBtn.addActionListener(actionEvent -> next());

        // Previous Button creating
        previousBtn = new JButton(new ImageIcon("src\\editor\\Images\\prev.png"));
        previousBtn.setName("PreviousMatchButton");
        previousBtn.addActionListener(actionEvent -> previous());

        // Check Box Regex creating
        regexpBox = new JCheckBox("Use regexp", false);
        regexpBox.setName("UseRegExCheckbox");
        regexpBox.setSelected(false);

        // Menu Items Action Listeners
        // Exit Menu Item Listener
        exitMenuItem.addActionListener(event -> System.exit(0));

        // Save Menu Item Listener
        saveMenuItem.addActionListener(actionEvent -> save());

        // Open Menu Item Listener
        openMenuItem.addActionListener(actionEvent -> open());

        // Top Panel creating
        topPanel = new JPanel();
        topPanel.setBorder(new LineBorder(Color.black, 3));
        topPanel.setBackground(Color.gray);
        topPanel.setLayout(new FlowLayout());

        // Add items to top panel
        topPanel.add(openBtn);
        topPanel.add(saveBtn);
        topPanel.add(searchField);
        topPanel.add(searchBtn);
        topPanel.add(previousBtn);
        topPanel.add(nextBtn);
        topPanel.add(regexpBox);
        add(fileChooser);

        // Add top panel to Main panel
        mainContainer.add(topPanel, BorderLayout.NORTH);

        // Adding elements to Center Panel creating
        mainContainer.add(scrollableTextArea, BorderLayout.CENTER);

        nextMatchMenuItem.addActionListener(actionEvent -> next());
        previousSearchMenuItem.addActionListener(actionEvent -> previous());
        useRegexpMenuItem.addActionListener(actionEvent -> regexpBox.setSelected(true));
    }

    /*
     * Handle the button clicks.
     */

    /*
     * Read in the contents of a file and display it in the text area.
     */
    private void open() {
        int ret = fileChooser.showOpenDialog(null);
        if (ret == JFileChooser.APPROVE_OPTION) {
            file = fileChooser.getSelectedFile();
            path = file.getAbsolutePath();
            textArea.setText("");
            try {
                byte[] bytes = Files.readAllBytes(Paths.get(file.getPath()));
                textArea.setText(new String(bytes));
            } catch (Throwable e) {
                System.out.println("Error while reading file");
            }
        }
    }

    /*
     * Save the contents of the editing area to a the file named "file.txt".
     */
    private void save() {
        int ret = fileChooser.showSaveDialog(null);
        if (ret == JFileChooser.APPROVE_OPTION) {
            file = fileChooser.getSelectedFile();
            path = file.getAbsolutePath();
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File(path)))) {
                writer.write(textArea.getText());
            } catch (IOException e) {
                System.out.println("Error while saving file");
            }
        }
    }

    private void search() {

        startIndexes.clear();
        endIndexes.clear();
        count = 0;

        // Get the string from the editing area.
        String editorText = textArea.getText();

        // Get the string the user wants to search for
        String searchValue = searchField.getText();

        try {
            // If regular expression checkBox is not selected then do...
            // Create an array of indexes of the found combinations
            if (regexpBox.getSelectedObjects() == null) {
                for (int i = 0; i < editorText.length() - searchValue.length() + 1; i++) {
                    if (editorText.substring(i, i + searchValue.length()).equals(searchValue)) {
                        startIndexes.add(i);
                        endIndexes.add(i + searchValue.length());
                    }
                }
                if (startIndexes.size() != 0 && endIndexes.size() != 0) {
                    start = startIndexes.get(count);
                    end = endIndexes.get(count);
                    selectText(start, end);
                }

                // If regular expression checkBox is selected then do...
            } else {
                Matcher matcher = Pattern.compile(searchValue).matcher(editorText);

                while (matcher.find()) {
                    startIndexes.add(matcher.start());
                    endIndexes.add(matcher.end());
                }
                start = startIndexes.get(count);
                end = endIndexes.get(count);

                if (start != -1) {
                    selectText(start, end);
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void next() {

        // Get the string from the editing area.
        String editorText = textArea.getText();

        // Get the string the user wants to search for
        String searchValue = searchField.getText();

        try {
            // If regular expression checkBox is not selected then do...
            if (regexpBox.getSelectedObjects() == null) {

                // Find the next occurrence, searching forward or backward depending on the setting of the reverse box.
                start = editorText.indexOf(searchValue, textArea.getSelectionEnd());
                end = start + searchValue.length();

                // If the string was found, move the selection so that the found string is highlighted.
                if (start != -1) {
                    selectText(start, end);
                }

                // If the last text doesn't contain search text
                if (editorText.contains(searchValue) && start == -1) {
                    start = editorText.indexOf(searchValue);
                    end = start + searchValue.length();
                    selectText(start, end);
                }

                // Else if regular expression checkBox is selected then do...
            } else {
                Matcher matcher = Pattern.compile(searchValue).matcher(editorText);

                if (matcher.find(textArea.getCaretPosition())) {
                    start = matcher.start();
                    end = matcher.end();
                    textArea.setCaretPosition(start);
                    selectText(start, end);
                } else if (!matcher.find(textArea.getCaretPosition()) && matcher.find()) {
                    selectText(matcher.start(), matcher.end());
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void previous() {

        // Get the string from the editing area.
        String editorText = textArea.getText();

        // Get the string the user wants to search for
        String searchValue = searchField.getText();

        try {
            // If regular expression checkBox is not selected then do...
            if (regexpBox.getSelectedObjects() == null) {

                // Find the next occurrence, searching forward or backward depending on the setting of the reverse box.
                start = editorText.lastIndexOf(searchValue, textArea.getSelectionStart() - 1);
                end = start + searchValue.length();

                // If the string was found, move the selection so that the found string is highlighted.
                if (start != -1 && !searchValue.equals("")) {
                    selectText(start, end);
                }

                // If the last text doesn't contain search text
                if (editorText.lastIndexOf(searchValue) != -1 && start == -1) {
                    start = editorText.lastIndexOf(searchValue);
                    end = start + searchValue.length();
                    selectText(start, end);
                }

            // Else if regular expression checkBox is selected then do...
            } else {
                Matcher matcher = Pattern.compile(searchValue).matcher(editorText);

                int pos = textArea.getSelectionStart();
                start = -1;
                end = -1;

                while (matcher.find()) {
                    int matchIdx = matcher.start();
                    if (matchIdx >= pos) break;

                    start = matcher.start();
                    end = matcher.end();
                }
                if (start == -1) { //try to find last
                    while (matcher.find()) {
                        start = matcher.start();
                        end = matcher.end();
                    }
                }
                if (start != -1) {
                    selectText(start, end);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void selectText(int start, int end) {
        textArea.setCaretPosition(end);
        textArea.select(start, end);
        textArea.grabFocus();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TextEditor::new);
    }
}