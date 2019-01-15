package ru.jf17.ide;

import org.fife.ui.autocomplete.*;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.Style;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rsyntaxtextarea.SyntaxScheme;
import org.fife.ui.rtextarea.RTextScrollPane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

import static org.fife.ui.rsyntaxtextarea.TokenTypes.*;

public class JavaWindow  extends JFrame {


    private String workFolder;
    private boolean isOpen;
    private String pathOpenFile;
    File file ;

    public JavaWindow(File pathOpenFile_IN) throws HeadlessException {
        this.file = pathOpenFile_IN;


        isOpen = false;


        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.setBackground(new Color(0,0,0));
        final RSyntaxTextArea textArea = new RSyntaxTextArea(40, 80);
        textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
        textArea.setCodeFoldingEnabled(true);
        // textArea.setFont(Font.decode("UTF8"));

        textArea.setBackground(new Color(0,0,0)); // цвет фона
        textArea.setForeground(new Color(168, 168, 168)); // цвет текста
        textArea.setCurrentLineHighlightColor(new Color(10,10,10)); //цвет активной линии
        textArea.setMarginLineColor(new Color(0,0,0));
        textArea.setCaretColor(Color.RED);

        SyntaxScheme syntScheme =textArea.getSyntaxScheme();

        syntScheme.setStyle(RESERVED_WORD,new Style(new Color(77,210,255))); // import , static ,void,
        syntScheme.setStyle(FUNCTION,new Style(new Color(77,255,166))); // String , File ,
        syntScheme.setStyle(LITERAL_STRING_DOUBLE_QUOTE,new Style(new Color(0,128,0))); // строки
        syntScheme.setStyle(SEPARATOR,new Style(new Color(168, 168, 168))); // скобочки круглые и фигурные
        syntScheme.setStyle(COMMENT_DOCUMENTATION,new Style(Color.darkGray)); // Documentation
        syntScheme.setStyle(COMMENT_MARKUP,new Style(Color.darkGray)); // Documentation
        syntScheme.setStyle(COMMENT_EOL,new Style(Color.darkGray)); // Documentation
        syntScheme.setStyle(COMMENT_MULTILINE,new Style(Color.darkGray)); // Documentation






        // Font font = new Font("Verdana", Font.PLAIN, 11);
        BackgroundMenuBar menuBar = new BackgroundMenuBar();

        JMenu fileMenu = new JMenu("File");
        fileMenu.setForeground(new Color(168, 168, 168));
        // fileMenu.setFont(font);
        JMenu fontMenu = new JMenu("Размер шрифта");
        fontMenu.setForeground(new Color(168, 168, 168));
        // fileMenu.setFont(font);

        JMenu changeMenu = new JMenu("Генератор кода");
        changeMenu.setForeground(new Color(168, 168, 168));

        JMenuItem ifelseCode = new JMenuItem("if / else if / else");
        changeMenu.add(ifelseCode);



        JMenuItem saveItem = new JMenuItem("Save");
        saveItem.setForeground(new Color(168, 168, 168));

        JMenuItem emptyItem1 = new JMenuItem("   ");
        JMenuItem emptyItem2 = new JMenuItem("   ");

        JMenuItem fontNORMALItem = new JMenuItem("Default");
        fontMenu.add(fontNORMALItem);
        JMenuItem fontBigItem = new JMenuItem("Big");
        fontMenu.add(fontBigItem);

        JMenuItem fontUPItem = new JMenuItem("font size +");
        fontUPItem.setForeground(new Color(168, 168, 168));
        JMenuItem fontDOWNItem = new JMenuItem("font size -");
        fontDOWNItem.setForeground(new Color(168, 168, 168));

        JMenuItem cmdItem = new JMenuItem("CMD");
        fileMenu.add(cmdItem);

        JMenuItem openFileDirectoryItem = new JMenuItem("Open file directory");
        fileMenu.add(openFileDirectoryItem);


        ifelseCode.addActionListener(new ActionListener()  {
            public void actionPerformed(ActionEvent e) {
                int position = textArea.getCaretPosition();
                String str = "if ( /* условие */ ) { /* код */ ;} \n else if( /* условие */ ){ /* код */ ;}\n else{ /* код */ ;}";
                textArea.insert(str , position);

            }});

        fontNORMALItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Font font123 = textArea.getFont();
                textArea.setFont(new Font(font123.getName(), font123.getStyle(),13));
            }});
        fontUPItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Font font123 = textArea.getFont();
                int sizetepm = font123.getSize() + 1;
                textArea.setFont(new Font(font123.getName(), font123.getStyle(),sizetepm));

            }});

        fontDOWNItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Font font123 = textArea.getFont();
                int sizetepm = font123.getSize() - 1;
                if(sizetepm< 8){sizetepm = 8;}

                textArea.setFont(new Font(font123.getName(), font123.getStyle(),sizetepm));

            }});

        saveItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if(isOpen) {
                    try {
                        Writer writerr = new OutputStreamWriter(new FileOutputStream(pathOpenFile), "UTF-8");
                        textArea.write(writerr);
                        writerr.close();
                        // textArea.write(new FileWriter(pathOpenFile));

                        JOptionPane.showMessageDialog(null, "File saved !");
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "File is NOT open!");
                }



            }});

        fontBigItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Font font123 = textArea.getFont();
                textArea.setFont(new Font(font123.getName(), font123.getStyle(),21));
            }});

        cmdItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {



                OSValidator validator = new OSValidator();

                if (isOpen && validator.isWindows()) {

                    Process p = null;
                    try {

                        if (workFolder != null) {

                            File workdirFile = new File(workFolder);
                            p = Runtime.getRuntime().exec("cmd /C start /D "+workdirFile+" cmd.exe ");


                        }
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }else if(isOpen && validator.isUnix()){

                    String command= "/usr/bin/xterm";

                    Process p = null;
                    try {

                        if (workFolder != null) {

                            ProcessBuilder pb =  new ProcessBuilder(command);
                            pb.directory(new File(workFolder));
                            p = pb.start();

                        }
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }


                }
            }
        });


                    workFolder=file.getParent();
                    //    /*
                    try {
                        // What to do with the file, e.g. display it in a TextArea
                        pathOpenFile = file.getAbsolutePath();
                        isOpen=true;

                        Reader reader = new InputStreamReader(new FileInputStream(file.getAbsolutePath()), "UTF8");

                        textArea.read(reader,file.getAbsolutePath());
                        textArea.setCaretPosition(0);
                        reader.close();
                    } catch (IOException ex) {
                        System.out.println("problem accessing file"+file.getAbsolutePath());
                    }




        menuBar.add(fileMenu);
        menuBar.add(fontMenu);
        menuBar.add(changeMenu);
        menuBar.add(saveItem);
        menuBar.add(fontUPItem);
        menuBar.add(fontDOWNItem);
        menuBar.add(emptyItem1);
        menuBar.add(emptyItem2);

        contentPane.add(new RTextScrollPane(textArea));

        // A CompletionProvider is what knows of all possible completions, and
        // analyzes the contents of the text area at the caret position to
        // determine what completion choices should be presented. Most instances
        // of CompletionProvider (such as DefaultCompletionProvider) are designed
        // so that they can be shared among multiple text components.
        CompletionProvider provider = createCompletionProvider();

        // An AutoCompletion acts as a "middle-man" between a text component
        // and a CompletionProvider. It manages any options associated with
        // the auto-completion (the popup trigger key, whether to display a
        // documentation window along with completion choices, etc.). Unlike
        // CompletionProviders, instances of AutoCompletion cannot be shared
        // among multiple text components.
        AutoCompletion ac = new AutoCompletion(provider);
        ac.install(textArea);

        int mask = InputEvent.CTRL_MASK;
        ac.setTriggerKey(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, mask));

        setJMenuBar(menuBar);

        setContentPane(contentPane);
        setTitle(file.getName() +" - JF17 IDE " );
      //  setDefaultCloseOperation();
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }



    /**
     * Create a simple provider that adds some Java-related completions.
     */
    private CompletionProvider createCompletionProvider() {

        // A DefaultCompletionProvider is the simplest concrete implementation
        // of CompletionProvider. This provider has no understanding of
        // language semantics. It simply checks the text entered up to the
        // caret position for a match against known completions. This is all
        // that is needed in the majority of cases.
        DefaultCompletionProvider provider = new DefaultCompletionProvider();

        // Add completions for all Java keywords. A BasicCompletion is just
        // a straightforward word completion.
        // JF17 template
        provider.addCompletion(new BasicCompletion(provider, "class "));
        provider.addCompletion(new BasicCompletion(provider, "interface "));
        provider.addCompletion(new BasicCompletion(provider, "import "));
        provider.addCompletion(new BasicCompletion(provider, "package "));
        provider.addCompletion(new BasicCompletion(provider, "public "));
        provider.addCompletion(new BasicCompletion(provider, "private "));
        provider.addCompletion(new BasicCompletion(provider, "extends "));
        provider.addCompletion(new BasicCompletion(provider, "implements "));
        provider.addCompletion(new BasicCompletion(provider, "final "));
        provider.addCompletion(new BasicCompletion(provider, "String "));
        provider.addCompletion(new BasicCompletion(provider, "Integer "));
        provider.addCompletion(new BasicCompletion(provider, "boolean "));
        provider.addCompletion(new BasicCompletion(provider, "true"));
        provider.addCompletion(new BasicCompletion(provider, "false"));
        provider.addCompletion(new BasicCompletion(provider, "void "));
        provider.addCompletion(new BasicCompletion(provider, "List<"));
        provider.addCompletion(new BasicCompletion(provider, "ArrayList<"));
        provider.addCompletion(new BasicCompletion(provider, "Map<"));
        provider.addCompletion(new BasicCompletion(provider, "HashMap<"));
        provider.addCompletion(new BasicCompletion(provider, "try{\n"));
        provider.addCompletion(new BasicCompletion(provider, "catch{\n"));
        provider.addCompletion(new BasicCompletion(provider, "new "));
        provider.addCompletion(new BasicCompletion(provider, "return "));
        // Spring Anotations :
        provider.addCompletion(new BasicCompletion(provider, "Bean "));
        provider.addCompletion(new BasicCompletion(provider, "Data "));
        provider.addCompletion(new BasicCompletion(provider, "Autowired "));
        provider.addCompletion(new BasicCompletion(provider, "Service "));
        provider.addCompletion(new BasicCompletion(provider, "Repository "));
        provider.addCompletion(new BasicCompletion(provider, "Component "));
        provider.addCompletion(new BasicCompletion(provider, "Controller "));
        provider.addCompletion(new BasicCompletion(provider, "RestController "));

        provider.addCompletion(new ShorthandCompletion(provider, "hello",
                "class HelloWorld {\n" +
                        "    public static void main(String[] args){\n" +
                        "\t\tSystem.out.println(\"Hello World!\");\n" +
                        "    }\n" +
                        "}\n", "Hello World!"));

        provider.addCompletion(new ShorthandCompletion(provider, "print",
                "System.out.println(", "System.out.println("));
        provider.addCompletion(new ShorthandCompletion(provider, "main",
                "public static void main(String[] args){ \n            // your code ", "public static void main ..."));

        return provider;

    }












}
