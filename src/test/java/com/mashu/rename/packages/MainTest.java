package com.mashu.rename.packages;

import com.mashu.rename.packages.entity.ProjectIndex;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MainTest {

    public static void main(String[] args) {
        ProjectIndex projectIndex = new ProjectIndex("/home/wanglh/IdeaProjects/rename-package");
        projectIndex.scanAllProjectIndex();
        String packageName = "com.mashu.rename.packages.request";
        String packageName2 = "com.mashu.rename.packages";
        log.info("是否存在包[{}]: {}", packageName, projectIndex.getPackageNode(packageName) != null);
        log.info("是否存在中间包[{}]: {}", packageName2, projectIndex.getPackageNode(packageName2) != null);
        projectIndex.printProjectInfo();
    }
}
