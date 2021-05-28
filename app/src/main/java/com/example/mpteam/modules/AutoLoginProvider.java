package com.example.mpteam.modules;

import android.content.Context;
import android.os.Environment;

import java.io.File;

public class AutoLoginProvider {

    FileIO io = new FileIO();
    EncryptModule encryptModule = new EncryptModule();
    String filename = "login.dat";

    public void AutoLoginWriter(Context context, String email, String password) {
        File file = new File(context.getFilesDir(), filename);
        String string = email + "$" + password;
        byte[] encrypted = encryptModule.encrypt(string.getBytes());
        io.FileWriter(file, encrypted);
    }

    public String[] AutoLoginReader(Context context) {
        File file = new File(context.getFilesDir(), filename);
        String[] information = new String[2]; //information[0] : email / information[1] : password
        byte[] bytes = io.FileReader(file);
        byte[] decrypted = encryptModule.decrypt(bytes);
        String string = new String(decrypted);
        int index = string.indexOf('$');
        information[0] = string.substring(0, index);
        information[1] = string.substring(index + 1);
        return information;
    }

    public boolean AutoLoginRemover(Context context) {
        File file = new File(context.getFilesDir(), filename);
        if (file.exists()) {
            file.delete();
            return true;
        } else {
            return false;
        }
    }

    public boolean AutoLoginChecker(Context context) {
        File file = new File(context.getFilesDir(), filename);
        if (file.exists()) {
            return true;
        } else {
            return false;
        }
    }
}
