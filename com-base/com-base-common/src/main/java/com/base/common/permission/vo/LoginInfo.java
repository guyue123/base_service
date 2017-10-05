package com.base.common.permission.vo;

import java.util.List;
import java.util.Map;
/**
 * 用户登录后的信息
 * @author 朱周城
 *
 */
public class LoginInfo{
    //用户名
    private String username;
    //节点ID
    private String nodeId;
  //权限ID集
    private List<String> permissions;
    //角色类型
    private String roleType;
    
    private Map<String,Object> userInfo;
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

    public Map<String, Object> getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(Map<String, Object> userInfo) {
        this.userInfo = userInfo;
    }

    @Override
    public String toString() {
        return "LoginInfo [username=" + username + ", nodeId=" + nodeId + ", permissions=" + permissions
                + ", roleType=" + roleType + ", userInfo=" + userInfo + "]";
    }
    
}
