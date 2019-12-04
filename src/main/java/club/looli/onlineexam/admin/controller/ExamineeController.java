package club.looli.onlineexam.admin.controller;

import club.looli.onlineexam.admin.entity.Examinee;
import club.looli.onlineexam.admin.entity.Menu;
import club.looli.onlineexam.admin.page.Page;
import club.looli.onlineexam.admin.service.ExamineeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 考生管理控制器
 */
@RestController
@RequestMapping("/admin/examinee")
public class ExamineeController {

    @Autowired
    private ExamineeService examineeService;

    /**
     * 点击考生列表
     * @param modelAndView
     * @return 考生列表页面
     */
    @RequestMapping(value = "/examineeList",method = RequestMethod.GET)
    public ModelAndView list(ModelAndView modelAndView, HttpServletRequest request){
        Map<String, List<Menu>> map1 = (Map<String, List<Menu>>) request.getSession().getAttribute("map");
        List<Menu> menuList = map1.get("examinee");
        modelAndView.addObject("examinee",menuList);
        modelAndView.setViewName("examinee/examinee");
        return modelAndView;
    }
//    name: 65
//    examineeId: 1
//    page: 1
//    rows: 20

    /**
     * 获取考生列表数据
     * @param page
     * @param name
     * @return
     */
    @RequestMapping(value="/list",method=RequestMethod.POST)
    public Map<String, Object> getList(Page page,
                                       @RequestParam(name="name",required=false,defaultValue="") String name,
                                       @RequestParam(name="examineeId",required=false,defaultValue="") String examineeId
    ){
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> queryMap = new HashMap<String, Object>();
        queryMap.put("name", "%"+name.trim()+"%");
        queryMap.put("start", page.getStart());
        queryMap.put("size", page.getRows());
        if (examineeId.equals("-1")|| examineeId.equals("")){
            examineeId = null;
        }
        queryMap.put("examineeId", examineeId);
        map.put("rows", examineeService.findAllBySearch(queryMap));
        map.put("total", examineeService.findCount(queryMap));
        return map;
    }

    /**
     * 添加考生
     * @param examinee
     * @return
     */
    @RequestMapping(value="/add",method=RequestMethod.POST)
    public Map<String, String> add(Examinee examinee){
        Map<String, String> map = new HashMap<String, String>();
        if(examinee == null){
            map.put("type", "error");
            map.put("msg", "请填写正确的考生信息！");
            return map;
        }
        if(StringUtils.isEmpty(examinee.getName())){
            map.put("type", "error");
            map.put("msg", "请填写考生名称！");
            return map;
        }
        if(examinee.getSubjectId().equals("")){
            map.put("type", "error");
            map.put("msg", "请选择考生学科！");
            return map;
        }
        if(StringUtils.isEmpty(examinee.getPassword())){
            map.put("type", "error");
            map.put("msg", "请填写考生密码！");
            return map;
        }
        if(StringUtils.isEmpty(examinee.getTrueName())){
            map.put("type", "error");
            map.put("msg", "请填写考生姓名！");
            return map;
        }
        if(StringUtils.isEmpty(examinee.getTel())){
            map.put("type", "error");
            map.put("msg", "请填写手机号！");
            return map;
        }
        //判断考生名是否存在
        Examinee byName = examineeService.findByName(examinee.getName());
        if (byName != null){
            map.put("type", "error");
            map.put("msg", "考生名称已存在，请重新填写！");
            return map;
        }
        examinee.setCreateTime(new Date());
        if(examineeService.add(examinee) <= 0){
            map.put("type", "error");
            map.put("msg", "考生信息添加失败，请联系管理员！");
            return map;
        }
        map.put("type", "success");
        map.put("msg", "考生信息添加成功！");
        return map;
    }

    /**
     * 修改学科信息
     * @param examinee
     * @return
     */
    @RequestMapping(value="/edit",method=RequestMethod.POST)
    public Map<String, String> edit(Examinee examinee){
        Map<String, String> map = new HashMap<String, String>();
        if(examinee == null){
            map.put("type", "error");
            map.put("msg", "请填写正确的考生信息！");
            return map;
        }
        if(StringUtils.isEmpty(examinee.getName())){
            map.put("type", "error");
            map.put("msg", "请填写考生名称！");
            return map;
        }
        if(examinee.getSubjectId().equals("")){
            map.put("type", "error");
            map.put("msg", "请选择考生学科！");
            return map;
        }
        if(StringUtils.isEmpty(examinee.getPassword())){
            map.put("type", "error");
            map.put("msg", "请填写考生密码！");
            return map;
        }
        if(StringUtils.isEmpty(examinee.getTrueName())){
            map.put("type", "error");
            map.put("msg", "请填写考生姓名！");
            return map;
        }
        if(StringUtils.isEmpty(examinee.getTel())){
            map.put("type", "error");
            map.put("msg", "请填写手机号！");
            return map;
        }
        //判断考生名是否存在
        Examinee byName = examineeService.findByName(examinee.getName());
        if (byName != null && !byName.getId().equals(examinee.getId())){
            map.put("type", "error");
            map.put("msg", "考生名称已存在，请重新填写！");
            return map;
        }
//        examinee.setCreateTime(new Date());
        if(examineeService.edit(examinee) <= 0){
            map.put("type", "error");
            map.put("msg", "考生信息修改失败，请联系管理员！");
            return map;
        }
        map.put("type", "success");
        map.put("msg", "考生信息修改成功！");
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
        if(examineeService.delete(id) <= 0){
            map.put("type", "error");
            map.put("msg", "考生删除失败，请联系管理员！");
            return map;
        }
        map.put("type", "success");
        map.put("msg", "考生删除成功！");
        return map;
    }

    /**
     * 显示考生下拉列表 + 全部
     * @return
     */
    @RequestMapping(value="/list1",method=RequestMethod.POST)
    public List<Examinee> getList1() {
        List<Examinee> all = examineeService.findAll();
        all.add(0,new Examinee(-1,"全部"));
        return all;
    }

    /**
     * 显示考生下拉列表
     * @return
     */
    @RequestMapping(value="/list2",method=RequestMethod.POST)
    public List<Examinee> getList2() {
        List<Examinee> all = examineeService.findAll();
        return all;
    }

}
