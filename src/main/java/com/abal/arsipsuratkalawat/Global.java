/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abal.arsipsuratkalawat;

/**
 *
 * @author Regina
 */
public class Global {
    private static String user;
    private static boolean editor;
    private static boolean admin;
    private static int activeYear=2020;
    
    public static int getActiveYear(){
        return activeYear;
    }
    public static void setActiveYear(int year){
        activeYear=year;
    }
    public static String getUser(){
        return user;
    }
    public static void setUser(String userId){
        user=userId;
    }
    public static boolean getAdmin(){
        return admin;
    }
    public static void setAdmin(boolean isAdmin){
        admin=isAdmin;
    }
    public static boolean getEditor(){
        return editor;
    }
    public static void setEditor(boolean isEditor){
        editor=isEditor;
    }
}
