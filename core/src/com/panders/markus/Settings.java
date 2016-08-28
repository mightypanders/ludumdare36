package com.panders.markus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

/**
 * Created by markus on 27.08.16.
 */
public class Settings {
    public static boolean soundEnabled = true;
    public final static String file = ".savegame";

    public static void load(){
        try {
            FileHandle fileHandle = Gdx.files.external(file);

            String[] strings = fileHandle.readString().split("\n");

            soundEnabled = Boolean.parseBoolean(strings[0]);
        }catch(Throwable e){

        }
    }
    public static void save(){
        try{
            FileHandle filehandle = Gdx.files.external(file);
            filehandle.writeString(Boolean.toString(soundEnabled)+"\n",false);
        }catch (Throwable e){
            System.out.print(e.getMessage());
            System.out.print("There was an error while saving");
        }
    }
}
