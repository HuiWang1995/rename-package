package com.mashu.rename.packages.service.impl;

import com.mashu.rename.packages.entity.ProjectIndex;
import com.mashu.rename.packages.service.PackageRenameService;
import lombok.Setter;

@Setter
public class PackageRenameServiceImpl implements PackageRenameService {

    private ProjectIndex projectIndex;

    @Override
    public void renamePackage(String sourcePackage, String targetPackage, boolean isCovered) {

    }

    /**
     * 是否存在需要迁移的冲突的类
     *
     * @return
     */
    private boolean hasConflictClassFile() {
        return false;
    }
}
