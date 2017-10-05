package com.base.common.permission.tag;


import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.base.common.permission.constant.PermissionConstant;
import com.base.common.permission.vo.LoginInfo;

/**
 * 权限校验标签
 *
 */
public class PermissionTag extends TagSupport {
	private static final long serialVersionUID = 127335552988303927L;
	
	private String permissionKey;
	
	private boolean validate = true;
	
	public String getPermissionKey() {
		return permissionKey;
	}

	public void setPermissionKey(String permissionKey) {
		this.permissionKey = permissionKey;
	}

	public boolean isValidate() {
        return validate;
    }

    public void setValidate(boolean validate) {
        this.validate = validate;
    }

    @Override
	public int doStartTag() throws JspException {
		boolean flag = false;
		if (!validate) {
		    flag = true;
        }else{
            LoginInfo loginUser = (LoginInfo)pageContext.getSession().getAttribute(PermissionConstant.LOGIN_INFO);
            if (loginUser != null) {
                List<String> permissions = loginUser.getPermissions();
                if (permissions != null && !permissions.isEmpty()) {
                    for (String key : permissions) {
                        if (key.equals(permissionKey)) {
                            flag = true;
                            break;
                        }
                    }
                }
            } 
        }
		return flag? EVAL_BODY_INCLUDE : SKIP_BODY;

	}
	
}
