package com.rgb0101.crepas;

import android.content.Context;

import org.keyczar.Crypter;
import org.keyczar.exceptions.KeyczarException;

/**
 * Created by noirCynical on 2015. 11. 21..
 */
public class CREPASProcessing {
    private static String password= null;
    public static String crepasFromPath(byte[] msg, String path){
        try{
            Crypter crypter= new Crypter(path);
            password= crypter.encrypt(msg.toString());
        } catch (KeyczarException e){ e.printStackTrace(); }
        return password;
    }
    public static String crepasFromPath(byte[] msg, Context context){
        try{
            Crypter crypter= new Crypter("/src/main/assets/1");
            password= crypter.encrypt(msg.toString());
        } catch (KeyczarException e){ e.printStackTrace(); }
        return password;
    }
}