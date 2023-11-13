/*
 * javac lsp.java
 * java lsp
 */

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class lsp {
    public static void main(String[] args) {
        File folder = new File("1971");
        File[] files = folder.listFiles();
        int i = 0;
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    System.out.println(file.getName());                
                    System.out.println(file.getParentFile());
                } else if (file.isDirectory()) {
                    System.out.println("======");
                    System.out.println(file.getAbsolutePath());
                    File _file = new File(file.getAbsolutePath());
                    File[] _files = _file.listFiles();
                    if (_files != null) {
                        i = 0;
                        for (File _f : _files) {
                            if (_f.isFile()) {
                                System.out.println(_f.getName());

                                String _newFileName = _f.getParentFile() + "/" + i +_f.getName().substring(_f.getName().lastIndexOf("."));
                                i++;
                                System.out.println(_newFileName);
                                File _newFile = new File(_newFileName);
                                _f.renameTo(_newFile);
                            }
                        }
                    }
                }
            }
        }
        /*System.out.println("lsp");

        List<String> files = getAllFiles("1973");
        for (String file : files) {
            System.out.println(file);
        }*/
        //System.out.println(files);

    
        
        //File oldFile = new File("OldFileName.txt");
        
        //File newFile = new File("NewFileName.txt");
        
        //boolean result = oldFile.renameTo(newFile);
        
        /*if (result) {
            System.out.println("ok");
        } else {
            System.out.println("no");
        }*/
        
    }

    public static List<String> getAllFiles(String folderPath) {
        List<String> fileList = new ArrayList<>();
        File folder = new File(folderPath);
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                System.out.println(file.getName());                
                System.out.println(file.getParentFile());
                System.out.println("======");

                if (file.isFile()) {
                    fileList.add(file.getAbsolutePath());
                } else if (file.isDirectory()) {
                    List<String> subFiles = getAllFiles(file.getAbsolutePath());
                    fileList.addAll(subFiles);
                }
            }
        }
        return fileList;
    }
}