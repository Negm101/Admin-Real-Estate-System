package me;

import javax.swing.*;

public class Global {
    public static final String adminDataPath = "/home/negm/IdeaProjects/Admin1/src/me/data/largeAdmin.txt";
    public static final String adminDataPathTemp = "/home/negm/IdeaProjects/Admin1/src/me/data/admin_temp.txt";
    public static final String propertyDataPath = "/home/negm/IdeaProjects/Admin1/src/me/data/property.txt";
    public static final String propertyDataPathTemp = "/home/negm/IdeaProjects/Admin1/src/me/property_temp.txt";
    public static final String lightThemePath = "com.formdev.flatlaf.FlatLightLaf";
    public static final String darkThemePath = "com.formdev.flatlaf.FlatDarkLaf";

    public static void setTheme() {
        try {
            UIManager.setLookAndFeel(Global.darkThemePath);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            System.out.println(e);
        }
    }
}
