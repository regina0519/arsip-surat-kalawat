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
    private static boolean editor=true;//SEMENTARA
    private static boolean admin=true;//SEMENTARA
    
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
