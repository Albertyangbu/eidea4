package indi.liudalei.eidea.security;


import indi.liudalei.eidea.base.def.OperatorDef;
import indi.liudalei.eidea.base.entity.bo.UserBo;
import indi.liudalei.eidea.base.entity.bo.UserContent;
import indi.liudalei.eidea.base.service.SpringContextHolder;
import indi.liudalei.eidea.base.service.UserService;
import indi.liudalei.eidea.core.web.def.WebConst;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.web.subject.support.WebDelegatingSubject;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by 刘大磊 on 2017/4/8 9:39.
 */
@Slf4j
public class EideaRealm extends AuthorizingRealm {
    private UserService userService;

    /**
     * 授权
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        if (userService == null) {
            userService = SpringContextHolder.getBean(UserService.class);
        }
        log.debug("------------------------------>doGetAuthorizationInfo-11");
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        String username = (String) principalCollection.getPrimaryPrincipal();
        WebDelegatingSubject subject = (WebDelegatingSubject) SecurityUtils.getSubject();
        HttpServletRequest request = (HttpServletRequest) subject.getServletRequest();
        UserContent userContent = (UserContent) request.getSession().getAttribute(WebConst.SESSION_USERCONTENT);
        Map<String, List<OperatorDef>> privilegesMap = userContent.getPrivileges();
        Set<String> privilegeKeySet = privilegesMap.keySet();
        userContent.getPrivileges();
        String requestURL = request.getServletPath();
        for (String key : privilegeKeySet) {
            if (requestURL.startsWith(key)) {
                List<OperatorDef> operatorDefList = privilegesMap.get(key);
                Set<String> permissionList = new HashSet<>();
                for (OperatorDef operatorDef : operatorDefList) {
                    permissionList.add(operatorDef.getKey());
                }

                StringBuilder sb = new StringBuilder("[");
                for (int i = 0; i < operatorDefList.size(); i++) {
                    if (i > 0) {
                        sb.append(",");
                    }
                    sb.append("\"").append(operatorDefList.get(i).getKey()).append("\"");
                }
                sb.append("]");
                request.setAttribute(WebConst.PAGE_PRIVILEGES, sb.toString());

                authorizationInfo.setStringPermissions(permissionList);
            }

        }
        return authorizationInfo;
    }

    /**
     * 认证
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        if (userService == null) {
            userService = SpringContextHolder.getBean(UserService.class);
        }
        UserBo user = userService.getUserByUsername((String) authenticationToken.getPrincipal());
        //账号不存在
        if (user == null) {
            throw new UnknownAccountException();
        }
        if (user.getIsactive().equals("N")) {
            throw new LockedAccountException();
        }

        AuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user.getUsername(), user.getPassword(), "wms");
        log.debug("authenticationToken.getPrincipal()=" + authenticationToken.getPrincipal() + "");
        log.debug("------------------------------>doGetAuthenticationInfo22");
        return authenticationInfo;
    }

}
