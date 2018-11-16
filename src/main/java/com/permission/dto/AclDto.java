package com.permission.dto;

import com.permission.model.SysAcl;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

/**
 * 权限适配
 *
 * @author zt1994 2018/10/31 20:57
 */
@Getter
@Setter
public class AclDto extends SysAcl {

    //是否默认选中
    private boolean checked = false;

    //是否有权限操作
    private boolean hasAcl = false;

    public static AclDto adapt(SysAcl acl) {
        AclDto dto = new AclDto();
        BeanUtils.copyProperties(acl, dto);
        return dto;
    }
}
