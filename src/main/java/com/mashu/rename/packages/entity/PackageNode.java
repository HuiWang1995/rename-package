package com.mashu.rename.packages.entity;

import cn.hutool.core.util.StrUtil;
import com.mashu.rename.packages.common.Constant;
import com.mashu.rename.packages.util.FileUtil;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Data
@NoArgsConstructor
@Slf4j
public class PackageNode {

    private static final String EMPTY_NAME = "";

    /**
     * 当前包的包名
     */
    private String name;

    private String simpleName;

    /**
     * 当前包下的类
     */
    private List<ClassNode> classNodes;

    /**
     * 当前包下的包
     */
    private List<PackageNode> packageNodes;

    private String path;

    public PackageNode(String path, String simpleName, String name) {
        this.path = path;
        this.name = name;
        this.simpleName = simpleName;
    }

    public PackageNode(String path) {
        this.path = path;
        this.name = EMPTY_NAME;
        this.simpleName = EMPTY_NAME;
    }

    /**
     * 扫描当前目录下的类
     */
    public void scanClass() {
        File[] classFiles = FileUtil.findJavaFiles(path);
        if (null == classFiles || classFiles.length <= 0) {
            return;
        }
        classNodes = new ArrayList<>(classFiles.length);
        Arrays.stream(classFiles).forEach(classFile -> {
            log.info("发现类文件：" + classFile.getName());
            classNodes.add(
                    new ClassNode(classFile.getName().substring(0, classFile.getName().indexOf(Constant.javaFileSuffix)),
                    name, classFile.getAbsolutePath()));
        });
    }

    /**
     * 扫描当前目录下的包
     */
    public void scanPackage() {
        File[] dirFiles = FileUtil.findDirectories(path);
        if (null == dirFiles || dirFiles.length <= 0) {
            return;
        }
        packageNodes = new ArrayList<>(dirFiles.length);
        Arrays.stream(dirFiles).forEach(dirFile-> {
            String tmpSimpleName = dirFile.getName();
            String tmpName = isVirtualPackage() ? tmpSimpleName : name + Constant.DOT + tmpSimpleName;
            packageNodes.add(new PackageNode(dirFile.getAbsolutePath(), tmpSimpleName, tmpName));
        });
    }

    /**
     * 递归扫描层级下的包和类
     */
    public void recursiveScanPackageNode() {
        // 扫描本层级
        scanPackage();
        scanClass();
        // 递归扫描下层级
        if (null == packageNodes) {
            return;
        }
        packageNodes.forEach(PackageNode::recursiveScanPackageNode);
    }

    /**
     * 获取包对象
     * @param packageName 包名， 例如 com.mashu.rename.packages
     * @return
     */
    public PackageNode getPackageNode(String packageName) {
        // 全路径匹配
        if (StrUtil.equals(name, packageName)) {
            return this;
        }
        if (packageNodes == null) {
            return null;
        }
        // 当前包是否所需包父包
        if (!packageName.startsWith(name)) {
            return null;
        }
        // 计算下级包名剪字符串前后offset
        int firstIndex = isVirtualPackage() ? 0 : name.length() + 1;
        // 从当前包名+’.‘的下一个位置开始找‘.’，若找到则减到这个前面，若无找到，说明已经剩下末级包名
        int secondIndex = packageName.indexOf(Constant.DOT, name.length() + 1);
        if (secondIndex < 0) {
            secondIndex = packageName.length();
        }
        final String child = packageName.substring(firstIndex , secondIndex);
        Optional<PackageNode> childNodeOptional = packageNodes.stream()
                .filter(packageNode -> packageNode.getSimpleName().equals(child))
                .findFirst();
        // 若存在下级包,递归查找
        return childNodeOptional.map(packageNode -> packageNode.getPackageNode(packageName)).orElse(null);

    }

    /**
     * 递归找出该包下所有的类
     * @param packageName
     * @return
     */
    public List<ClassNode> getPackageClasses(String packageName) {
        // 找到对应的根节点
        PackageNode rootPackage = getPackageNode(packageName);
        if (null == rootPackage) {
            return new ArrayList<>();
        }
        return rootPackage.getChildrenClassNodes();
    }

    /**
     * 递归获取包内所有类
     * @return
     */
    private List<ClassNode> getChildrenClassNodes() {
        List<ClassNode> childrenClassNodes = new ArrayList<>();
        // 获取本级类
        if (null != classNodes) {
            childrenClassNodes.addAll(classNodes);
        }
        // 获取下级类
        if (null != this.getPackageNodes()) {
            this.getPackageNodes().forEach(packageNode -> {
                childrenClassNodes.addAll(packageNode.getChildrenClassNodes());
            });
        }
        return childrenClassNodes;
    }

    private boolean isVirtualPackage() {
        return EMPTY_NAME.equals(name);
    }

}
