package com.example;
import java.awt.Dimension;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Dimension dim = new Dimension(800, 600);
        
        Home Hgui = new Home();
        Hgui.setSize(dim);
        Hgui.setLocationRelativeTo(null);
        
        Hgui.setVisible(true);
        
        QuestionsPage Qgui = new QuestionsPage();
        Qgui.setSize(dim);
        Qgui.setLocationRelativeTo(null);
        Qgui.setVisible(true);
        
        ExamsPage Egui = new ExamsPage();
        Egui.setSize(dim);
        Egui.setLocationRelativeTo(null);
        Egui.setVisible(true);
    }
}
