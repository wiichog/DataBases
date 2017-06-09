import java.io.*;
import java.util.*;

public class main {
    public static void main (String [ ] args) {
    String directory = "C:/Users/Juan Luis Garcia/Documents/MEGAsync/UVG SEPTIMO SEMESTRE/Bases de Datos/Proyecto/bin/GUI/Servidor/ddd";
    List<String> textFiles = new ArrayList<String>();
    File dir = new File(directory);
    for (File file : dir.listFiles()) {
    if (file.getName().endsWith((".txt"))) {
    	System.out.println(file.getName());
      //textFiles.add(file.getName());
    }
  }
    } 
} 
