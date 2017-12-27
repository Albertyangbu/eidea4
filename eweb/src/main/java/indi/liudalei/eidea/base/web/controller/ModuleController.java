package indi.liudalei.eidea.base.web.controller;

import indi.liudalei.eidea.base.entity.bo.ModuleBo;
import indi.liudalei.eidea.base.service.ModuleService;
import indi.liudalei.eidea.base.web.vo.UserResource;
import indi.liudalei.eidea.core.dto.PaginationResult;
import indi.liudalei.eidea.core.params.DeleteParams;
import indi.liudalei.eidea.core.params.QueryParams;
import indi.liudalei.eidea.core.web.def.WebConst;
import indi.liudalei.eidea.core.web.result.JsonResult;
import indi.liudalei.eidea.core.web.result.def.ErrorCodes;
import indi.liudalei.eidea.core.web.util.SearchHelper;
import indi.liudalei.eidea.core.web.vo.PagingSettingResult;
import com.googlecode.genericdao.search.Search;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;


/**
 * Created by Bobo on 2016/12/13 17:34.
 * ModuleController:模块设置
 */
@Controller
@RequestMapping("/base/module")
public class ModuleController {
    private static final String URI = "sys_module";
    @Autowired
    private ModuleService moduleService;

    /**
     * getModuleToJsp:页面跳转
     *
     * @return
     */
    @RequiresPermissions(value = "view")
    @RequestMapping(value = "/getModuleToJsp", method = RequestMethod.GET)
    public ModelAndView getModuleToJsp() {
        ModelAndView modelAndView = new ModelAndView("/base/module/module");
        modelAndView.addObject(WebConst.PAGING_SETTINGS, PagingSettingResult.getDbPaging());
        modelAndView.addObject(WebConst.PAGE_URI, URI);
        return modelAndView;
    }

    /**
     * getModuleList:模块设置列表
     *
     * @param
     * @return
     */
    @RequiresPermissions(value = "view")
    @RequestMapping(value = "/getModuleList", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult<PaginationResult<ModuleBo>> getModuleList(HttpSession session, @RequestBody QueryParams queryParams) {
        Search search = SearchHelper.getSearchParam(URI, session);
        PaginationResult<ModuleBo> moduleList = moduleService.getModuleList(search,queryParams);
        return JsonResult.success(moduleList);
    }

    /**
     * deleteModuleList:模块设置批量删除
     *
     * @param ids
     * @return
     */
    @RequiresPermissions(value = "view")
    @RequestMapping(value = "/deleteModuleList", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult<PaginationResult<ModuleBo>> deleteModuleList(@RequestBody DeleteParams<Integer > deleteParams, HttpSession session) {
        if (deleteParams.getIds().length == 0) {
            UserResource resource = (UserResource) session.getAttribute(WebConst.SESSION_RESOURCE);
            return JsonResult.fail(ErrorCodes.BUSINESS_EXCEPTION.getCode(), resource.getMessage("pagemenu.primarykey.information"));
        }
        moduleService.deleteModuleList(deleteParams.getIds());
        return getModuleList(session,deleteParams.getQueryParams());
    }

    /**
     * save模块设置新增
     *
     * @param moduleBo
     * @return
     */
    @RequiresPermissions(value = "add")
    @RequestMapping(value = "/saveModuleForCreated", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult<ModuleBo> saveModuleForCreated(@RequestBody ModuleBo moduleBo, HttpSession session) {
        if (moduleBo.isCreated()) {
            if (moduleService.findExistId(moduleBo.getId())) {
                UserResource resource = (UserResource) session.getAttribute(WebConst.SESSION_RESOURCE);
                return JsonResult.fail(ErrorCodes.BUSINESS_EXCEPTION.getCode(), resource.getMessage("pagemenu.connection.point"));
            }
        }
        moduleService.saveModule(moduleBo);
        return getModule(moduleBo.getId(), session);
    }

    @RequiresPermissions(value = "update")
    @RequestMapping(value = "/saveModuleForUpdated", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult<ModuleBo> saveModuleForUpdated(@RequestBody ModuleBo moduleBo, HttpSession session) {
        if (moduleBo.getId() == null) {
            UserResource resource = (UserResource) session.getAttribute(WebConst.SESSION_RESOURCE);
            return JsonResult.fail(ErrorCodes.BUSINESS_EXCEPTION.getCode(), resource.getMessage("pagemenu.primarykey.check.isnull"));
        }
        moduleService.saveModule(moduleBo);
        return getModule(moduleBo.getId(), session);
    }


    /**
     * get根据id查询module对象
     *
     * @param id
     * @return
     */
    @RequiresPermissions(value = "view")
    @RequestMapping(value = "/getModule", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult<ModuleBo> getModule(Integer id, HttpSession session) {
        ModuleBo moduleBo = null;
        if (id == null) {
            UserResource resource = (UserResource) session.getAttribute(WebConst.SESSION_RESOURCE);
            return JsonResult.fail(ErrorCodes.BUSINESS_EXCEPTION.getCode(), resource.getMessage("pagemenu.primarykey.information"));
        } else {
            moduleBo = moduleService.getModule(id);
        }
        return JsonResult.success(moduleBo);
    }

    /**
     * create:再次新建
     *
     * @return
     */
    @RequiresPermissions(value = "add")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult<ModuleBo> create() {
        return JsonResult.success(new ModuleBo());
    }

}
