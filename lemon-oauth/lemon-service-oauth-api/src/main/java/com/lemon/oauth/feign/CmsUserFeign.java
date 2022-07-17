package com.lemon.oauth.feign;

import com.lemon.oauth.pojo.cms.PermissionDO;
import com.lemon.oauth.pojo.cms.UserDO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName CmsUserFeign
 **/
@FeignClient(name = "oauth", path = "/cms/user",contextId = "cmsUserFeign")
public interface CmsUserFeign {

    @RequestMapping("/detail")
    UserDO getUserById(@RequestParam("userId") Long userId);

    @RequestMapping("/permissions/id")
    List<PermissionDO>  getUserPermissions(@RequestParam("userId") Long userId);

    @RequestMapping("/check")
    Boolean checkIsRootByUserId(@RequestParam("userId") Long userId);

}
