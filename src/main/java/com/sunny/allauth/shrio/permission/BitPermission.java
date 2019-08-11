package com.sunny.allauth.shrio.permission;

import cn.hutool.core.util.StrUtil;
import org.apache.shiro.authz.Permission;


/**
 * BitPermission
 * 定义资源权限匹配规则
 * +资源名称+权限位+实例ID
 * 权限：
 * 0 表示所有权限
 * 1 新增 0001
 * 2 修改 0010
 * 4 删除 0100
 * 8 查看 1000
 * <p>
 * 如 +user+10 表示对资源user拥有修改/查看权限
 *
 * @author lijunsong
 * @date 2019/8/6 18:07
 * @since 1.0
 */
public class BitPermission implements Permission {
    private int permissionBit;
    private String instanceId;
    private String resourceIdentify;

    public BitPermission(String permissionString) {
        String[] array = permissionString.split("\\+");

        if (array.length > 1) {
            resourceIdentify = array[1];
        }

        if (StrUtil.isEmpty(resourceIdentify)) {
            resourceIdentify = "*";
        }

        if (array.length > 2) {
            permissionBit = Integer.valueOf(array[2]);
        }

        if (array.length > 3) {
            instanceId = array[3];
        }

        if (StrUtil.isEmpty(instanceId)) {
            instanceId = "*";
        }

    }

    @Override
    public boolean implies(org.apache.shiro.authz.Permission p) {
        if (!(p instanceof BitPermission)) {
            return false;
        }
        BitPermission other = (BitPermission) p;

        if (!("*".equals(this.resourceIdentify) || this.resourceIdentify.equals(other.resourceIdentify))) {
            return false;
        }

        if (!(this.permissionBit == 0 || (this.permissionBit & other.permissionBit) != 0)) {
            return false;
        }

        if (!("*".equals(this.instanceId) || this.instanceId.equals(other.instanceId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "BitPermission{" +
                "resourceIdentify='" + resourceIdentify + '\'' +
                ", permissionBit=" + permissionBit +
                ", instanceId='" + instanceId + '\'' +
                '}';
    }
}
