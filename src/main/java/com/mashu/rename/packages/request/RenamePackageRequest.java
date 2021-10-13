package com.mashu.rename.packages.request;

public class RenamePackageRequest {

    /**
     * 原包名
     */
    private String sourcePackage;

    /**
     * 目标包名
     */
    private String targetPackage;

    /**
     * 同名是否覆盖
     */
    private boolean isCovered;
}
