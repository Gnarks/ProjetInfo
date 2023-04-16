package code.projetinfo;

import java.io.*;
import java.lang.reflect.Type;

/**
 * Handles the interactions between files and instances.
 * @param <T>
 * Type of instance(s) to store/load in/from a file.
 */
public class objectFile <T> {

    private String FileName;

    /**
     * @param FileName
     * Name of the currently used file.
     */
    public objectFile(String FileName){
        this.FileName = FileName;
    }

    /**
     * Save an instance of type T in FileName.
     * @param Object
     * Instance of type T which is going to be stored in FileName.
     */
    public void save(T Object){
        try{
            FileOutputStream fileOut = new FileOutputStream(this.FileName);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(Object);

            objectOut.close();
            fileOut.close();
        }
        catch (IOException i){
            System.out.println(i);
        }
    }

    /**
     * Load an instance of type T from a FileName and store it in Object.
     * @param Object
     * Instance of type T which is going to store the instance stored in FileName.
     * @return
     * The instance of type T stored in FileName.
     */
    public T load(T Object){
        try {
            FileInputStream fileIn = new FileInputStream(this.FileName);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            T obj = (T) objectIn.readObject();

            fileIn.close();
            objectIn.close();

            return obj;
        }
        catch (Exception j){
            System.out.println(j);
            return null;
        }
    }

    /**
     * Setter for the used file.
     * @param newFileName
     * The name of the new file to use.
     */
    public void setFileName(String newFileName){
        this.FileName = newFileName;
    }

    /**
     * Getter for the used file.
     * @return
     * Currently used file.
     */
    public String getFileName(){
        return this.FileName;
    }
}
