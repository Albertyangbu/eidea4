package com.dsdl.eidea.general.web.controller;

import com.dsdl.eidea.base.entity.bo.FieldBo;
import com.dsdl.eidea.base.entity.bo.FieldInListPageBo;
import com.dsdl.eidea.base.entity.po.TabPo;
import com.dsdl.eidea.base.service.FieldService;
import com.dsdl.eidea.base.service.TabService;
import com.dsdl.eidea.core.dto.PaginationResult;
import com.dsdl.eidea.core.params.QueryParams;
import com.dsdl.eidea.core.web.def.WebConst;
import com.dsdl.eidea.core.web.result.JsonResult;
import com.dsdl.eidea.general.bo.FieldStructureBo;
import com.dsdl.eidea.general.bo.TabFormStructureBo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 刘大磊 on 2017/5/24 11:08.
 */
@Controller
@RequestMapping("/general/tab")
public class GeneralTabController {
    @Autowired
    private TabService tabService;
    @Autowired
    private FieldService fieldService;

    /**
     * 显示tab列表界面
     *
     * @param tabId
     * @return
     */
    @RequestMapping("/showList/{tabId}")
    public ModelAndView showListPage(@PathVariable("tabId") Integer tabId, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("general/list");
        String lang = (String) session.getAttribute(WebConst.SESSION_LANGUAGE);
        List<FieldInListPageBo> fieldInListPageBoList = fieldService.getListPageFiledList(tabId, lang);
        modelAndView.addObject("tabId", tabId);
        TabPo tabPo = tabService.getTab(tabId);
        modelAndView.addObject("pk", "id" + tabPo.getTableColumnId());
        modelAndView.addObject("fieldInListPageBoList", fieldInListPageBoList);
        return modelAndView;
    }
    @RequestMapping("/list/{tabId}")
    @ResponseBody
    public JsonResult<PaginationResult<Map<String, String>>> list(@PathVariable("tabId") Integer tabId, @RequestBody QueryParams queryParams) {
        PaginationResult list = fieldService.getDataList(tabId, queryParams);
        return JsonResult.success(list);
    }
    /**
     * 显示tab编辑界面
     *
     * @param tabId
     * @return
     */
    @RequestMapping("/showForm/{tabId}")
    public ModelAndView showFormPage(@PathVariable("tabId") Integer tabId, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("general/edit");
        String lang = (String) session.getAttribute(WebConst.SESSION_LANGUAGE);
        TabFormStructureBo tabFormStructureBo = fieldService.getFormPageFieldList(tabId, lang);
        modelAndView.addObject("tabId",tabId);
        modelAndView.addObject("tabFormStructureBo", tabFormStructureBo);
        return modelAndView;
    }
    @RequestMapping("/create/{tabId}")
    public JsonResult<Map<String, String>> create(@PathVariable("tabId") Integer tabId, HttpSession session) {
        String lang = (String) session.getAttribute(WebConst.SESSION_LANGUAGE);
        TabFormStructureBo tabFormStructureBo = fieldService.getFormPageFieldList(tabId, lang);
        List<FieldStructureBo> fieldStructureBoList = tabFormStructureBo.getFieldStructureBoList();
        Map<String, String> result = new HashMap<>();
        for (FieldStructureBo fieldStructureBo : fieldStructureBoList) {
            result.put("id" + fieldStructureBo.getFieldPo().getId(), null);
        }
        return JsonResult.success(result);
    }
    @RequestMapping("/get/{tabId}/{recordId}")
    public JsonResult<List<FieldBo>> edit(@PathVariable("tabId") Integer tabId, @PathVariable("recordId") Integer recordId) {

        return null;
    }
}

