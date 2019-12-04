package club.looli.onlineexam.admin.controller;

import club.looli.onlineexam.admin.entity.ExamPaper;
import club.looli.onlineexam.admin.entity.Menu;
import club.looli.onlineexam.admin.page.Page;
import club.looli.onlineexam.admin.service.ExamPaperService;
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
 * 试卷管理控制器
 */
@RestController
@RequestMapping("/admin/exampaper")
public class ExamPaperController {

    @Autowired
    private ExamPaperService examService;

    /**
     * 点击试卷列表
     * @param modelAndView
     * @return 试卷列表页面
     */
    @RequestMapping(value = "/exampaperList",method = RequestMethod.GET)
    public ModelAndView list(ModelAndView modelAndView, HttpServletRequest request){
        Map<String, List<Menu>> map1 = (Map<String, List<Menu>>) request.getSession().getAttribute("map");
        List<Menu> menuList = map1.get("exampaper");
        modelAndView.addObject("exampaper",menuList);
        modelAndView.setViewName("exampaper/exampaper");
        return modelAndView;
    }

    /**
     * 获取试卷列表数据
     * @param page
     * @param status
     * @param studentId
     * @param examId
     * @return
     */
    @RequestMapping(value="/list",method=RequestMethod.POST)
    public Map<String, Object> getList(Page page,
                                       @RequestParam(name="status",required=false,defaultValue="") Integer status,
                                       @RequestParam(name="studentId",required=false,defaultValue="") Integer studentId,
                                       @RequestParam(name="examId",required=false,defaultValue="") Integer examId
    ){
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> queryMap = new HashMap<String, Object>();
        queryMap.put("status", status);
        queryMap.put("examineeId", studentId);
        queryMap.put("examId", examId);
        queryMap.put("start", page.getStart());
        queryMap.put("size", page.getRows());
        map.put("rows", examService.findAllBySearch(queryMap));
        map.put("total", examService.findCount(queryMap));
        return map;
    }

    /**
     * 添加试卷
     * @param exam
     * @return
     */
    @RequestMapping(value="/add",method=RequestMethod.POST)
    public Map<String, String> add(ExamPaper exam){
        Map<String, String> map = new HashMap<String, String>();
        if(exam == null){
            map.put("type", "error");
            map.put("msg", "请填写正确的试卷信息！");
            return map;
        }
        exam.setCreateTime(new Date());
        if(examService.add(exam) <= 0){
            map.put("type", "error");
            map.put("msg", "试卷信息添加失败，请联系管理员！");
            return map;
        }
        map.put("type", "success");
        map.put("msg", "试卷信息添加成功！");
        return map;
    }

    /**
     * 修改试卷信息
     * @param id
     * @param examId
     * @param examineeId
     * @return
     */
    @RequestMapping(value="/edit",method=RequestMethod.POST)
    public Map<String, String> edit(
            @RequestParam(name="id",required=false,defaultValue="") Integer id,
            @RequestParam(name="examId",required=false,defaultValue="") Integer examId,
            @RequestParam(name="examineeId",required=false,defaultValue="") Integer examineeId
    ){
        ExamPaper examPaper = new ExamPaper();
        examPaper.setId(id);
        examPaper.setExamId(examId);
        examPaper.setExamineeId(examineeId);
        Map<String, String> map = new HashMap<String, String>();
        if(examPaper == null){
            map.put("type", "error");
            map.put("msg", "请填写正确的试卷信息！");
            return map;
        }
        if(examService.edit(examPaper) <= 0){
            map.put("type", "error");
            map.put("msg", "试卷信息修改失败，请联系管理员！");
            return map;
        }
        map.put("type", "success");
        map.put("msg", "试卷信息修改成功！");
        return map;
    }

    /**
     * 删除试卷信息
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
        if(examService.delete(id) <= 0){
            map.put("type", "error");
            map.put("msg", "试卷删除失败，请联系管理员！");
            return map;
        }
        map.put("type", "success");
        map.put("msg", "试卷删除成功！");
        return map;
    }

}
