package com.chuan.ssm.blog.controller.admin;

import com.chuan.ssm.blog.entity.Options;
import com.chuan.ssm.blog.service.OptionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author chuan
 * @date 2021/4/20 17:02
 */
@Controller
@RequestMapping("/admin/options")
public class BackOptionsController {
    @Autowired
    private OptionsService optionsService;

    @RequestMapping(value = "")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView();
        Options option = optionsService.getOptions();
        modelAndView.addObject("option", option);

        modelAndView.setViewName("admin/options/index");
        return modelAndView;
    }

    @RequestMapping(value = "/edit")
    public ModelAndView editOptionView() {
        ModelAndView modelAndView = new ModelAndView();
        Options option = optionsService.getOptions();
        modelAndView.addObject("option", option);

        modelAndView.setViewName("admin/options/edit");
        return modelAndView;
    }

    @RequestMapping(value = "/editSubmit", method = RequestMethod.POST)
    public String editOptionSubmit(Options options) {
        // 如果记录不存在，那就新建
        Options optionsCustom = optionsService.getOptions();
        if (optionsCustom.getOptionId() == null) {
            optionsService.insertOptions(options);
        } else {
            optionsService.updateOptions(options);
        }
        return "redirect:/admin/options";
    }
}
