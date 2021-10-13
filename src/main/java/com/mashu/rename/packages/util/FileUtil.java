package com.mashu.rename.packages.util;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 文件工具类
 *
 * @author mashu
 */
public class FileUtil {

    /**
     * 将当前路径下.java后缀文件返回
     *
     * @param path
     * @return
     */
    public static File[] findJavaFiles(String path) {
        File pathFile = new File(path);
        return pathFile.listFiles(((pathName) -> pathName.isFile() && pathName.getName().contains(".java")));
    }

    /**
     * 将当前路径下的文件夹返回
     *
     * @param path
     * @return
     */
    public static File[] findDirectories(String path) {
        File pathFile = new File(path);
        return pathFile.listFiles((File::isDirectory));
    }

    /**
     * 查找存在的文件
     *
     * @param files
     * @return
     */
    public static List<String> findExistFiles(List<String> files) {
        return files.stream()
                .filter(filePath -> new File(filePath).exists())
                .collect(Collectors.toList());
    }
}
