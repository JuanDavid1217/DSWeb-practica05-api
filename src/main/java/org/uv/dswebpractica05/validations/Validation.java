/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.dswebpractica05.validations;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author juan
 */
public class Validation {
    
    public static boolean telefonoValidation(String telefono){
        return telefono.length()==10;
    }
    
    public static Date stringtoDate(String fecha){
        String formato="yyyy/MM/dd";
        Date date=null;
        try{
            date=new SimpleDateFormat(formato).parse(fecha);
        }catch(ParseException ex){
            ex.getMessage();
        }
        return date;
    }
    
    public static String datetoSring(Date date){
        String formato="dd/MM/yyyy";
        SimpleDateFormat formatoFecha=new SimpleDateFormat(formato);
        return formatoFecha.format(date);
    }
    
    public static String dateValidation(String date){
        String newFormat=null;
        if(date.length()==10){
            String [] parts=date.split("/");
            if(parts.length==3){
                try{
                    int day=Integer.parseInt(parts[0]);
                    int mont=Integer.parseInt(parts[1]);
                    int year=Integer.parseInt(parts[2]);
                    if((day>0&&day<32)&&(mont>0&&mont<13)&&year>1900){
                        if(day==29 && mont==2 && year%4!=0){
                            newFormat="Invalid Date.";
                        }else{
                            newFormat=year+"/"+mont+"/"+day;
                        }
                    }
                }catch(Exception ex){
                    newFormat="Invalid Date.";
                }
            }
        }
        return newFormat;
    }
}
