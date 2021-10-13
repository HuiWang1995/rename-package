package com.mashu.rename.packages.entity;

import cn.hutool.core.date.TimeInterval;
import cn.hutool.json.JSONUtil;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.List;

/**
 * 工程结构索引
 * @note 工程需满足maven工程结构
 *
 * @author mashu
 */
@Data
@Slf4j
public class ProjectIndex {

    /**
     * 文件分隔符
     */
    private final static String separator = File.separator;

    /**
     * 工程根路径
     */
    private String rootDir;

    /**
     * main文件夹路径
     */
    private String mainJavaDir;

    /**
     * test文件夹路径
     */
    private String testJavaDir;

    /**
     * main文件夹下的包
     */
    private final PackageNode virtualMainPackageNode;

    /**
     * test文件夹下的包
     */
    private final PackageNode virtualTestPackageNode;


    /**
     * 根据项目根路径构造项目索引
     * @param rootDir
     */
    public ProjectIndex(String rootDir) {
        this.rootDir = rootDir;
        String srcDir = rootDir + separator + "src";
        this.mainJavaDir = srcDir + separator + "main" + separator + "java";
        this.testJavaDir = srcDir + separator + "test" + separator + "java";
        this.virtualMainPackageNode = new PackageNode(mainJavaDir);
        this.virtualTestPackageNode = new PackageNode(testJavaDir);
    }

    /**
     * 扫描工程根目录信息
     */
    public void scanProjectRootIndex() {
        virtualTestPackageNode.scanPackage();
        virtualTestPackageNode.scanClass();
        virtualMainPackageNode.scanPackage();
        virtualMainPackageNode.scanClass();
    }

    /**
     * 递归将整个工程的信息扫描
     */
    public void scanAllProjectIndex() {
        TimeInterval timeInterval = new TimeInterval();
        virtualMainPackageNode.recursiveScanPackageNode();
        log.info("扫描Main路径文件耗时：{}ms", timeInterval.intervalMs());
        timeInterval.restart();
        virtualTestPackageNode.recursiveScanPackageNode();
        log.info("扫描Test路径文件耗时：{}ms", timeInterval.intervalMs());
    }

    /**
     * 获取包名下所有的类
     * @param packageName
     * @return
     */
    public List<ClassNode> getClassesOfPackage(String packageName) {
        //todo
        return null;
    }

    /**
     * 获取测试文件夹，包名下所有的类
     * @param packageName
     * @return
     */
    public List<ClassNode> getTestClassesOfPackage(String packageName) {
        //todo
        return null;
    }

    /**
     * 查找Main文件夹下的包对象
     * @param packageName
     * @return
     */
    public PackageNode getPackageNode(String packageName) {
        return virtualMainPackageNode.getPackageNode(packageName);
    }

    /**
     * 打印工程信息，忽略空包、忽略测试文件夹
     */
    public void printProjectInfo() {
        log.info(JSONUtil.toJsonStr(this));
    }






}
