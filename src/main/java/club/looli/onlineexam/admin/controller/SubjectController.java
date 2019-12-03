package club.looli.onlineexam.admin.controller;

import club.looli.onlineexam.admin.entity.Menu;
import club.looli.onlineexam.admin.entity.Subject;
import club.looli.onlineexam.admin.page.Page;
import club.looli.onlineexam.admin.service.SubjectService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 学科管控制器
 */
@RestController
@RequestMapping("/admin/subject")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    /**
     * 点击学科列表
     * @param modelAndView
     * @return 学科列表页面
     */
    @RequestMapping(value = "/subjectList",method = RequestMethod.GET)
    public ModelAndView list(ModelAndView modelAndView, HttpServletRequest request){
        Map<String, List<Menu>> map1 = (Map<String, List<Menu>>) request.getSession().getAttribute("map");
        List<Menu> menuList = map1.get("subject");
        modelAndView.addObject("subject",menuList);
        modelAndView.setViewName("subject/subject_list");
        return modelAndView;
    }

    /**
     * 获取学科列表
     * @param page
     * @param name
     * @return
     */
    @RequestMapping(value="/list",method=RequestMethod.POST)
    public Map<String, Object> getList(Page page,
                                       @RequestParam(name="name",required=false,defaultValue="") String name
    ){
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> queryMap = new HashMap<String, Object>();
        queryMap.put("name", "%"+name.trim()+"%");
        queryMap.put("start", page.getStart());
        queryMap.put("size", page.getRows());
        map.put("rows", subjectService.findAllBySearch(queryMap));
        map.put("total", subjectService.findCount("%"+name.trim()+"%"));
        return map;
    }

    /**
     * 添加学科
     * @param subject
     * @return
     */
    @RequestMapping(value="/add",method=RequestMethod.POST)
    public Map<String, String> add(Subject subject){
        Map<String, String> map = new HashMap<String, String>();
        if(subject == null){
            map.put("type", "error");
            map.put("msg", "请填写正确的学科信息！");
            return map;
        }
        if(StringUtils.isEmpty(subject.getName())){
            map.put("type", "error");
            map.put("msg", "请填写学科名称！");
            return map;
        }
        //判断学科名是否存在
        Subject byName = subjectService.findByName(subject.getName());
        if (byName != null){
            map.put("type", "error");
            map.put("msg", "学科名称已存在，请重新填写！");
            return map;
        }
        if(subjectService.add(subject) <= 0){
            map.put("type", "error");
            map.put("msg", "学科信息添加失败，请联系管理员！");
            return map;
        }
        map.put("type", "success");
        map.put("msg", "学科信息添加成功！");
        return map;
    }

    /**
     * 修改学科信息
     * @param subject
     * @return
     */
    @RequestMapping(value="/edit",method=RequestMethod.POST)
    public Map<String, String> edit(Subject subject){
        Map<String, String> map = new HashMap<String, String>();
        if(subject == null){
            map.put("type", "error");
            map.put("msg", "请填写正确的学科信息！");
            return map;
        }
        if(StringUtils.isEmpty(subject.getName())){
            map.put("type", "error");
            map.put("msg", "请填写学科名称！");
            return map;
        }
        //判断学科名是否存在
        Subject byName = subjectService.findByName(subject.getName());
        if (byName != null && !byName.getId().equals(subject.getId())){
            map.put("type", "error");
            map.put("msg", "学科名称已存在，请重新填写！");
            return map;
        }
        if(subjectService.edit(subject) <= 0){
            map.put("type", "error");
            map.put("msg", "学科信息修改失败，请联系管理员！");
            return map;
        }
        map.put("type", "success");
        map.put("msg", "学科信息修改成功！");
        return map;
    }

    /**
     * 删除学科信息
     * @param id
     * @return
     */
    @RequestMapping(value="/delete",method=RequestMethod.POST)
    public Map<String, String> delete(@RequestParam("id") Integer id){
        Map<String, String> map = new HashMap<String, String>();
        if(id == null || id.equals("") ){
            map.put("type", "error");
            map.put("msg", "选择要删除的数据！");
            return map;
        }
        if(subjectService.delete(id) <= 0){
            map.put("type", "error");
            map.put("msg", "学科删除失败，请联系管理员！");
            return map;
        }
        map.put("type", "success");
        map.put("msg", "学科删除成功！");
        return map;
    }

    /**
     * 显示学科下拉列表 + 全部
     * @return
     */
    @RequestMapping(value="/list1",method=RequestMethod.POST)
    public List<Subject> getList1() {
        List<Subject> all = subjectService.findAll();
        all.add(0,new Subject(-1,"全部"));
        return all;
    }
    /**
     * 显示学科下拉列表
     * @return
     */
    @RequestMapping(value="/list2",method=RequestMethod.POST)
    public List<Subject> getList2() {
        return subjectService.findAll();
    }

}
