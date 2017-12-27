package indi.liudalei.eidea.general.web.controller;

import indi.liudalei.eidea.base.entity.bo.TabBo;
import indi.liudalei.eidea.base.entity.bo.WindowBo;
import indi.liudalei.eidea.base.entity.bo.WindowHelpBo;
import indi.liudalei.eidea.base.service.WindowService;
import indi.liudalei.eidea.core.web.def.WebConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

/**
 * Created by 刘大磊 on 2017/5/24 11:07.
 * 窗体信息
 */
@Controller
@RequestMapping("/general/window")
public class GeneralWindowController {
    @Autowired
    private WindowService windowService;

    @RequestMapping("/show/{windowId}")
    public ModelAndView getWindow(@PathVariable("windowId") Integer windowId, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("general/window");
        String language = (String) session.getAttribute(WebConst.SESSION_LANGUAGE);
        WindowBo windowBo = windowService.getWindowBo(windowId, language);
        TabBo tabBo = windowBo.getTabList().get(0);
        modelAndView.addObject("windowBo", windowBo);
        modelAndView.addObject("tabBo", tabBo);
        return modelAndView;
    }

    @RequestMapping("/help/{windowId}")
    public ModelAndView showHelp(@PathVariable("windowId") Integer windowId, HttpSession session) {
        String language = (String) session.getAttribute(WebConst.SESSION_LANGUAGE);
        WindowHelpBo windowHelpBo = windowService.getWindowHelpBo(windowId, language);
        ModelAndView modelAndView = new ModelAndView("/general/help");
        modelAndView.addObject("windowHelpBo", windowHelpBo);
        return modelAndView;
    }

}
