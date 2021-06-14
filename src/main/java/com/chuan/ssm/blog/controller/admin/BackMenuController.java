package com.chuan.ssm.blog.controller.admin;

import com.chuan.ssm.blog.entity.Menu;
import com.chuan.ssm.blog.enums.MenuLevel;
import com.chuan.ssm.blog.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @author chuan
 * @date 2021/4/20 16:23
 */
@Controller
@RequestMapping("/admin/menu")
public class BackMenuController {
    @Autowired
    private MenuService menuService;

    @RequestMapping(value = "")
    public String menuList(Model model) {
        List<Menu> menuList = menuService.listMenu();
        model.addAttribute("menuList", menuList);
        return "admin/menu/index";
    }

    @RequestMapping(value = "/insertSubmit", method = RequestMethod.POST)
    public String insertMenuSubmit(Menu menu) {
        if (menu.getMenuOrder() == null) {
            menu.setMenuOrder(MenuLevel.TOP_MENU.getValue());
        }
        menuService.insertMenu(menu);
        return "redirect:/admin/menu";
    }

    @RequestMapping("/delete/{id}")
    public String deleteMenu(@PathVariable("id") Integer id) {
        menuService.deleteMenu(id);
        return "redirect:/admin/menu";
    }

    @RequestMapping("/edit/{id}")
    public ModelAndView editMenuView(@PathVariable("id") Integer id) {
        ModelAndView modelAndView = new ModelAndView();

        Menu menu = menuService.getMenuById(id);
        modelAndView.addObject("menu", menu);

        List<Menu> menuList = menuService.listMenu();
        modelAndView.addObject("menuList", menuList);

        modelAndView.setViewName("admin/menu/edit");
        return modelAndView;
    }

    @RequestMapping(value = "/editSubmit", method = RequestMethod.POST)
    public String editMenuSubmit(Menu menu) {
        menuService.updateMenu(menu);
        return "redirect:/admin/menu";
    }
}
