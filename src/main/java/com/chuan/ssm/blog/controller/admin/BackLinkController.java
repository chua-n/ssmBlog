package com.chuan.ssm.blog.controller.admin;

import com.chuan.ssm.blog.entity.Link;
import com.chuan.ssm.blog.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;

/**
 * @author chuan
 * @date 2021/4/20 16:08
 */
@Controller
@RequestMapping("/admin/link")
public class BackLinkController {
    @Autowired
    private LinkService linkService;

    /**
     * 后台链接列表显示。
     *
     * @return
     */
    @RequestMapping(value = "")
    public ModelAndView linkList() {
        ModelAndView modelAndView = new ModelAndView();
        List<Link> linkList = linkService.listLink(null);
        modelAndView.addObject("linkList", linkList);
        modelAndView.setViewName("admin/link/index");
        return modelAndView;
    }

    /**
     * 后台添加链接页面显示。
     *
     * @return
     */
    @RequestMapping(value = "/insert")
    public ModelAndView insertLinkView() {
        ModelAndView modelAndView = new ModelAndView();

        List<Link> linkList = linkService.listLink(null);
        modelAndView.addObject("linkList", linkList);
        modelAndView.setViewName("admin/link/insert");
        return modelAndView;
    }

    /**
     * 后台添加链接页面提交。
     *
     * @param link
     * @return
     */
    @RequestMapping(value = "/insertSubmit", method = RequestMethod.POST)
    public String insertLinkSubmit(Link link) {
        link.setLinkCreateTime(new Date());
        link.setLinkUpdateTime(new Date());
        link.setLinkStatus(1);
        linkService.insertLink(link);
        return "redirect:/admin/link/insert";
    }

    /**
     * 删除链接。
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete/{id}")
    public String deleteLink(@PathVariable("id") Integer id) {
        linkService.deleteLink(id);
        return "redirect:/admin/link";
    }

    /**
     * 编辑链接页面展示。
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/edit/{id}")
    public ModelAndView editLinkView(@PathVariable("id") Integer id) {
        ModelAndView modelAndView = new ModelAndView();

        Link linkCustom = linkService.getLinkById(id);
        modelAndView.addObject("linkCustom", linkCustom);

        List<Link> linkList = linkService.listLink(null);
        modelAndView.addObject("linkList", linkList);

        modelAndView.setViewName("admin/link/edit");
        return modelAndView;
    }

    /**
     * 编辑链接提交。
     *
     * @param link
     * @return
     */
    @RequestMapping(value = "/editSubmit", method = RequestMethod.POST)
    public String editLinkSubmit(Link link) {
        link.setLinkUpdateTime(new Date());
        linkService.updateLink(link);
        return "redirect:/admin/link";
    }
}
