package utils;

import com.sun.org.apache.xpath.internal.operations.String;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created by Martín Ruíz on 4/14/2017.
 */
public class Utils {

    public static Image geErrorIcon(){
        try {
            Image iconError = new Image(new FileInputStream("resources\\icons\\ic_error_black_24dp_1x.png"));
            return iconError;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}
