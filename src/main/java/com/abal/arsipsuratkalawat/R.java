/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abal.arsipsuratkalawat;

import com.thowo.jmjavaframework.JMFunctions;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 *
 * @author jimi
 */
public class R {
    private static String langDef="en";
    private static String regDef="US";
    
    private static String lang=langDef;
    private static String reg=regDef;
    
    public static void set(String code){
        String[] local=code.split("_");
        if(local.length>1){
            lang=local[0];
            reg=local[1];
        }
        JMFunctions.setDefaultLanguage(lang+"_"+reg);
    }
    public static String label(String var){
        Locale l=new Locale(lang,reg);
        if(l==null){
            l=new Locale(langDef,regDef);
        }
        return ResourceBundle.getBundle("localization/Labels",l).getString(var);
    }
}
