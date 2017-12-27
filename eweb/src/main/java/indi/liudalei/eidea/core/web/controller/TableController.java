package indi.liudalei.eidea.core.web.controller;

import indi.liudalei.eidea.base.entity.bo.SelectItemBo;
import indi.liudalei.eidea.base.web.vo.UserResource;
import indi.liudalei.eidea.core.def.EntityType;
import indi.liudalei.eidea.core.def.JavaDataType;
import indi.liudalei.eidea.core.dto.PaginationResult;
import indi.liudalei.eidea.core.entity.bo.TableBo;
import indi.liudalei.eidea.core.entity.bo.TableMetaDataBo;
import indi.liudalei.eidea.core.params.DeleteParams;
import indi.liudalei.eidea.core.params.QueryParams;
import indi.liudalei.eidea.core.service.TableService;
import indi.liudalei.eidea.core.web.def.WebConst;
import indi.liudalei.eidea.core.web.result.JsonResult;
import indi.liudalei.eidea.core.web.result.def.ErrorCodes;
import indi.liudalei.eidea.core.web.util.SearchHelper;
import indi.liudalei.eidea.core.web.vo.PagingSettingResult;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 刘大磊 on 2016/12/6 16:24.
 */
@Controller
@RequestMapping("/core/table")
public class TableController {
    private static final String URI = "core_table";
    @Autowired
    private TableService tableService;
    @Autowired
    private HttpSession session;

    @RequestMapping(value = "/showList", method = RequestMethod.GET)
    @RequiresPermissions(value = "view")
    public ModelAndView showList() {
        ModelAndView modelAndView = new ModelAndView("/core/table/table");
        modelAndView.addObject(WebConst.PAGING_SETTINGS, PagingSettingResult.getDbPaging());
        modelAndView.addObject(WebConst.PAGE_URI, URI);
        return modelAndView;
    }

    @RequiresPermissions("view")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult<PaginationResult<TableBo>> list(HttpSession session, @RequestBody QueryParams queryParams) {
        Search search = SearchHelper.getSearchParam(URI, session);
        PaginationResult<TableBo> tablePoList = tableService.findList(search, queryParams);
        return JsonResult.success(tablePoList);
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ResponseBody
    @RequiresPermissions(value = "view")
    public JsonResult<TableBo> get(Integer id) {
        TableBo tableBo = null;
        if (id == null) {
            tableBo = new TableBo();
        } else {

            tableBo = tableService.getTableBo(id);


        }
        return JsonResult.success(tableBo);
    }

    @RequestMapping(value = "/getEntityBoList", method = RequestMethod.GET)
    @ResponseBody
    @RequiresPermissions(value = "view")
    public List<SelectItemBo> getEntityBoList() {
        List<SelectItemBo> selectItemBoList = new ArrayList<>();
        EntityType[] entityTypeList = EntityType.values();
        for (EntityType entityType : entityTypeList) {
            SelectItemBo selectItemBo = new SelectItemBo();
            selectItemBo.setKey(entityType.getKey());
            selectItemBo.setValue(entityType.getDesc());
            selectItemBoList.add(selectItemBo);
        }
        return selectItemBoList;
    }

    /**
     * @param tableBo
     * @return
     */
    @RequestMapping(value = "/saveForUpdated", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions(value = "update")
    public JsonResult<TableBo> saveForUpdated(@RequestBody TableBo tableBo) {
        UserResource resource = (UserResource) session.getAttribute(WebConst.SESSION_RESOURCE);
        if (tableBo.getId() == null) {
            return JsonResult.fail(ErrorCodes.BUSINESS_EXCEPTION.getCode(), resource.getMessage("common.primary_key.isempty"));
        }
        tableBo = tableService.saveTableBo(tableBo);
        return get(tableBo.getId());
    }

    @RequestMapping(value = "/saveForCreated", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions(value = "add")
    public JsonResult<TableBo> saveForCreated(@RequestBody TableBo tableBo) {
        tableBo = tableService.saveTableBo(tableBo);
        return get(tableBo.getId());
    }

    @RequestMapping(value = "/deletes", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions(value = "delete")
    public JsonResult<PaginationResult<TableBo>> deletes(@RequestBody DeleteParams<Integer> deleteParams, HttpSession session) {
        UserResource resource = (UserResource) session.getAttribute(WebConst.SESSION_RESOURCE);
        if (deleteParams.getIds() == null || deleteParams.getIds().length == 0) {
            return JsonResult.fail(ErrorCodes.BUSINESS_EXCEPTION.getCode(), resource.getMessage("client.msg.select_delete"));
        }
        tableService.deleteTables(deleteParams.getIds());
        return list(session, deleteParams.getQueryParams());
    }

    @RequestMapping(value = "/getJavaTypeList", method = RequestMethod.GET)
    @ResponseBody
    @RequiresPermissions(value = "view")
    public JsonResult<String> getJavaTypeList() {
        JavaDataType[] columnDataTypes = JavaDataType.values();
        JsonArray jsonArray = new JsonArray();
        for (JavaDataType columnDataType : columnDataTypes) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("key", columnDataType.getKey());
            jsonObject.addProperty("desc", columnDataType.getDesc());
            jsonArray.add(jsonObject);
        }

        return JsonResult.success(jsonArray.toString());
    }

    @RequestMapping(value = "/getTableInfo", method = RequestMethod.GET)
    @ResponseBody
    @RequiresPermissions(value = "view")
    public JsonResult<TableMetaDataBo> getTableInfo(String tableName, HttpSession session) {
        UserResource userResource = (UserResource) session.getAttribute(WebConst.SESSION_RESOURCE);
        if (tableName == null && tableName.equals("")) {
            return JsonResult.fail(ErrorCodes.BUSINESS_EXCEPTION.getCode(), userResource.getMessage("table.error.table_name.not_null"));
        }
        if (tableService.findExistTableByName(tableName)) {
            TableMetaDataBo tableMetaDataBo = tableService.getTableDescription(tableName);
            return JsonResult.success(tableMetaDataBo);
        } else {
            return JsonResult.fail(ErrorCodes.BUSINESS_EXCEPTION.getCode(), userResource.getMessage("table.error.name_not_exist"));
        }
    }

    @RequestMapping(value = "/saveTableInfo", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions(value = "add")
    public JsonResult<TableMetaDataBo> saveTableInfo(@RequestBody TableMetaDataBo tableInfo) {
        try {
            tableService.saveTableInfoByWizard(tableInfo);
        } catch (Exception validateException) {
            validateException.printStackTrace();
            return JsonResult.fail(ErrorCodes.BUSINESS_EXCEPTION.getCode(), validateException.getMessage());
        }
        return JsonResult.success(tableInfo);
    }

}
