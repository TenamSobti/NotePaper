import java.io.*;
import java.awt.event.*;
import java.awt.print.PrinterException;
import javax.swing.*;
import javax.swing.plaf.metal.*;

class Editor extends JFrame implements ActionListener {
    // ? Creating Required Objects.
    JFrame f;
    JTextArea t;
    JMenuBar menu;
    JMenu file, edit;
    JMenuItem f1, f2, f3, f4, e1, e2, e3, close;

    Editor() {
        f = new JFrame("Tenam's Notepaper");
        // ? Setting Overall Theme Of Application.
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
            MetalLookAndFeel.setCurrentTheme(new OceanTheme());
        } catch (Exception e) {
            System.out.println("Error setting look and feel: " + e);
        }
        // ? Setting Text Component.
        t = new JTextArea();
        // ? Setting Menu Bar.
        menu = new JMenuBar();
        // ? Setting File Menu.
        file = new JMenu("File");
        // ? File Menu Items.
        f1 = new JMenuItem("New");
        f2 = new JMenuItem("Open");
        f3 = new JMenuItem("Save");
        f4 = new JMenuItem("Print");
        // ? Action On Clicking File Menu Items.
        f1.addActionListener(this);
        f2.addActionListener(this);
        f3.addActionListener(this);
        f4.addActionListener(this);
        // ? Adding File Menu Items To The File Menu.
        file.add(f1);
        file.add(f2);
        file.add(f3);
        file.add(f4);
        // ? Setting Edit Menu.
        edit = new JMenu("Edit");
        // ? Edit Menu Items.
        e1 = new JMenuItem("Cut");
        e2 = new JMenuItem("Copy");
        e3 = new JMenuItem("Paste");
        // ? Action On Clicking Edit Menu Items.
        e1.addActionListener(this);
        e2.addActionListener(this);
        e3.addActionListener(this);
        // ? Adding Edit Menu Items To The Edit Menu.
        edit.add(e1);
        edit.add(e2);
        edit.add(e3);
        // ? Setting Close Item
        close = new JMenuItem("Close");
        // ? Action On Clicking Close Menu Item.
        close.addActionListener(this);
        // ? Adding File And Edit Menu To Menu Bar Along With Close Item.
        menu.add(file);
        menu.add(edit);
        menu.add(close);
        // ? Adding MenuBar, TextArea To JFrame And Setting The Size Of JFrame Window.
        f.setJMenuBar(menu);
        f.setContentPane(t);
        f.setSize(500, 500);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // ? Get Command Into String Variable.
        String s = e.getActionCommand();
        if (s.equals("New")) {
            t.setText("");
        } else if (s.equals("Open")) {
            // ? Initializing JFileChooser Object With Current Directory.
            JFileChooser j = new JFileChooser(".");
            // ? Invoking showOpenDialog() Method To Select File To Open.
            int r = j.showOpenDialog(null);
            // ? If File Was Selected.
            if (r == JFileChooser.APPROVE_OPTION) {
                // ? Set The fi To The Path Of Selected File.
                File fi = new File(j.getSelectedFile().getAbsolutePath());
                try {
                    // ? Strings To Copy The Text Of The File Into The TextArea.
                    String s1 = "", s2 = "";
                    // ? Reading Entire Text File Into FileReader Object
                    FileReader fr = new FileReader(fi);
                    // ? Reading File Reader Object Into BufferedReader Object
                    BufferedReader br = new BufferedReader(fr);
                    // ? Reading BufferedReader Object Into String Variable Line By Line.
                    s2 = br.readLine();
                    while ((s1 = br.readLine()) != null) {
                        s2 = s2 + '\n' + s1;
                    }
                    // ? Setting The TextArea To The Text Of The String s2.
                    t.setText(s2);
                    // ? Closing BufferedReader Object To Save Resources.
                    br.close();
                } catch (Exception e1) {
                    // ? Message Box To Display Error Message If Failed To Open.
                    JOptionPane.showMessageDialog(f, "Error Opening File: " + e1);
                }
            }
        } else if (s.equals("Save")) {
            // ? Initializing JFileChooser Object With Current Directory.
            JFileChooser j = new JFileChooser(".");
            // ? Invoking showSaveDialog() Method To Select Location To Save File.
            int r = j.showSaveDialog(null);
            // ? If File Was Saved.
            if (r == JFileChooser.APPROVE_OPTION) {
                // ? Set The fi To The Path Of Selected File.
                File fi = new File(j.getSelectedFile().getAbsolutePath());
                try {
                    // ? FileWriter To Write To File At Path fi.
                    FileWriter fw = new FileWriter(fi, false);
                    // ? BufferedWriter To Write To File At Path fi.
                    BufferedWriter bw = new BufferedWriter(fw);
                    // ? Writing The Text Of The TextArea Into BufferedWriter Object.
                    bw.write(t.getText());
                    // ? After Writing Is Finished, Flush The Stream.
                    bw.flush();
                    // ? Closing BufferedWriter Object To Save Resources.
                    bw.close();
                } catch (Exception e1) {
                    // ? Message Box To Display Error Message If Failed To Save.
                    JOptionPane.showMessageDialog(f, "Error Saving File: " + e1);
                }
            }
        } else if (s.equals("Print")) {
            try {
                t.print();
            } catch (PrinterException e1) {
                System.out.println("Printer Exception: " + e1);
            }
        } else if (s.equals("Cut")) {
            t.cut();
        } else if (s.equals("Copy")) {
            t.copy();
        } else if (s.equals("Paste")) {
            t.paste();
        } else if (s.equals("Close")) {
            // ? Set Visibility To False And Terminate.
            f.setVisible(false);
            System.exit(0);
        }
    }

    public static void main(String[] args) throws Exception {
        Editor e = new Editor();
        // ? Display The Frame.
        e.f.setVisible(true);
    }
}