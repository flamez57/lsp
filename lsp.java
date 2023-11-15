/*
 * javac lsp.java
 * java lsp
 */

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class lsp {
    public static void main(String[] args) {
        if (args.length > 0) {
            System.out.println("get param:"+args[0]);
        }
        
        System.out.println("param length:"+args.length);

        FolderUtil f = new lsp.FolderUtil(args[0]);
        f.fileCount();
        List<PicCount> picCounts = f.getPicCountList();
        System.out.println(picCounts);
        //fileCount(args[0]);
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

    /*
     * 
     */
    public static void formatePicName(String folderPath) {
        File folder = new File(folderPath);
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
    }

    public static int fileCount(String folderPath) {
        List<PicCount> picCountList = new ArrayList<>();
        File folder = new File(folderPath);
        File[] files = folder.listFiles();
        int num = 0;
        if (files != null) {
            for (File file : files) {
                //System.out.println(file.getName());                
                //System.out.println(file.getParentFile());
                //System.out.println("======");

                if (file.isFile()) {
                    num++;
                } else if (file.isDirectory()) {
                    picCountList.add(new PicCount(file.getName(), fileCount(file.getAbsolutePath())));
                    System.out.println(file.getName() + "===" + fileCount(file.getAbsolutePath()));
                }
            }
        }
        return num;
    }

    class FolderUtil {
        private List<PicCount> picCountList;
        private String folderPath;

        public FolderUtil(String folderPath) {
            this.folderPath = folderPath;
        }

        public int fileCount() {
            File folder = new File(folderPath);
            File[] files = folder.listFiles();
            int num = 0;
            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        num++;
                    } else if (file.isDirectory()) {
                        picCountList.add(new PicCount(file.getName(), this.fileCount(file.getAbsolutePath())));
                    }
                }
            }
            return num;
        }

        public List<PicCount> getPicCountList() {
            return this.picCountList;
        }
    }

    /*
     * Number of files in a folder data model
     */
    class PicCount {
        String name;
        int qty;
        public PicCount(String name, int qty) {
            this.name = name;
            this.qty = qty;
        }
        public void setName(String name) {
            this.name = name;
        }
        public void setQty(int qty) {
            this.qty = qty;
        }
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