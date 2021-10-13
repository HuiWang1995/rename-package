package com.mashu.rename.packages.entity;

import com.mashu.rename.packages.common.Constant;
import lombok.Data;

@Data
public class ClassNode {


    /**
     * 类名
     */
    private String simpleName;

    /**
     * 类的全类名
     */
    private String name;

    /**
     * 类的包名
     */
    private String packageName;



    private String path;

    public ClassNode(String simpleName, String packageName, String path) {
        this.simpleName = simpleName;
        this.packageName = packageName;
        this.path = path;
        this.name = packageName + Constant.DOT + simpleName;
    }
}
