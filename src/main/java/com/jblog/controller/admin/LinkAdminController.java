package com.jblog.controller.admin;

import com.github.pagehelper.PageInfo;
import com.jblog.common.bean.EasyUIResult;
import com.jblog.pojo.Link;
import com.jblog.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/link")
public class LinkAdminController {

    @Autowired
    private LinkService linkService;

    @RequestMapping("/listLink")
    public EasyUIResult<Link> listLinkData(
            @RequestParam(value = "page",defaultValue="1") Integer page,
            @RequestParam(value = "rows", defaultValue="10") Integer rows
    ) {
        PageInfo<Link> info = linkService.listLinkData(page, rows);
        return new EasyUIResult<>(info.getTotal(), info.getList());
    }

    @PostMapping("/addLink")
    public EasyUIResult<Integer> save(Link link) {
        return new EasyUIResult<>(linkService.addLink(link));
    }

    @PostMapping("/updateLink")
    public EasyUIResult<Integer> update(Link link) {
        return new EasyUIResult<>(linkService.updateLink(link));
    }

    @PostMapping("/deleteLink")
    public EasyUIResult<Integer> delete(
            @RequestParam(value = "ids") String ids
    ) {
        String[] temp = ids.split(",");
        Integer[] idList = new Integer[temp.length];
        for (int i = 0; i < temp.length; i++) {
            idList[i] = Integer.parseInt(temp[i]);
        }
        return new EasyUIResult<>(linkService.deleteLink(idList));
    }
}
