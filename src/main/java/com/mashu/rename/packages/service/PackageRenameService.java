package com.mashu.rename.packages.service;

/**
 * 包重命名服务
 *
 * @author mashu
 */
public interface PackageRenameService {

    /**
     * 重命名包名
     *
     * @param sourcePackage 原包名
     * @param targetPackage 新包名
     * @param isCovered 同名类是否可覆盖
     * @throws RuntimeException 当不可覆盖时，遇到冲突则抛出异常
     */
    void renamePackage(String sourcePackage, String targetPackage, boolean isCovered);
}
