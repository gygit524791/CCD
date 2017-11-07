package ccd.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GetFoldFileNames {
    public static List<String> getFileName(String path) {
        List<String> fileNames = new ArrayList<String>();
        File f = new File(path);
        if (!f.exists()) {
            System.out.println(path + "no files");
            return null;
        }

        File fa[] = f.listFiles();
        for (int i = 0; i < fa.length; i++) {
            File fs = fa[i];
            if (fs.isDirectory()) {
                System.out.println(fs.getName() + "is a directory");
            } else {
                fileNames.add(fs.getName());
            }
        }
        return fileNames;
    }
}