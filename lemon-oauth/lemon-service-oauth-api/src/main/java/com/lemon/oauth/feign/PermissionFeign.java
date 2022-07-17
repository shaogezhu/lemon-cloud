package com.lemon.oauth.feign;

import com.lemon.oauth.pojo.cms.PermissionDO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName permissionFeign
 **/
@FeignClient(name = "oauth", path = "/cms/permission",contextId = "permissionFeign")
public interface PermissionFeign {

    @RequestMapping("/list")
    List<PermissionDO> getAll();

    @RequestMapping(value = "/update")
    void updateById(@RequestParam(name = "id") Long id, @RequestParam(name = "name") String name,
                                          @RequestParam(name = "module") String module, @RequestParam(name = "mount") Boolean mount);

    @RequestMapping("/name")
    PermissionDO getOneByName(@RequestParam(name = "name") String name,
                                              @RequestParam(name = "module") String module);

    @RequestMapping("/insert")
    void insertOne(@RequestParam(name = "name") String name,
                                         @RequestParam(name = "module") String module);

}
