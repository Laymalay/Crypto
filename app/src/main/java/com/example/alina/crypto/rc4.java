package com.example.alina.crypto;

/**
 * Created by alina on 12/5/17.
 */

import android.content.Context;
import android.icu.text.LocaleDisplayNames;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.util.Date;



public class rc4
{

    public char[] box = new char[256];
    int i,j;
    Context _context;
    double wtime;
    double fileSize=0;

    public rc4(byte[] key, int key_length, Context context)
    {
        this._context = context;
        char temp;
        for (i = 0; i != 256; ++i)
            box[i] = (char)i;

        for (i = j = 0; i != 256; ++i)
        {
            j = (j + key[i % key_length] + box[i]) % 256;
            temp = box[i];
            box[i] = box[j];
            box[j] =  temp;
        }
        i = j = 0;
    }
    char rc4_output()
    {
        char temp;

        i = (i + 1) % 256;
        j = (j + box[i]) % 256;

        temp = box[j];
        box[j] = box[i];
        box[i] =  temp;

        return box[(temp + box[j]) % 256];
    }


    void make() throws IOException {

        long start = System.nanoTime();

        File fileout = new File(_context.getFilesDir().getPath(), "ciphertext.txt");
        fileout.createNewFile();
        Log.d("file", fileout.getAbsolutePath());
        InputStream inputStream = _context.getAssets().open("text.txt");
        OutputStream outputStream = new FileOutputStream(fileout);


        int byteRead;
        int b;

        while ((byteRead = inputStream.read()) != -1) {
            b = (char)byteRead ^ rc4_output();
            outputStream.write(b);
            fileSize++;
        }
        fileSize = fileSize/1000000.0;
        inputStream.close();
        outputStream.close();
        Date d2 = new Date();


        wtime = (System.nanoTime() - start)/1000000.0;
//        long elapsedTime = System.currentTimeMillis() - startTime;
//        wtime = elapsedTime;
    }





}