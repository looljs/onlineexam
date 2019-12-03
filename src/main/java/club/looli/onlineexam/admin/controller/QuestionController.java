package club.looli.onlineexam.admin.controller;

import club.looli.onlineexam.admin.entity.Menu;
import club.looli.onlineexam.admin.entity.Question;
import club.looli.onlineexam.admin.page.Page;
import club.looli.onlineexam.admin.service.QuestionService;
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
 * 试题管理控制器
 */
@RestController
@RequestMapping("/admin/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    /**
     * 点击试题列表
     * @param modelAndView
     * @return 试题列表页面
     */
    @RequestMapping(value = "/questionList",method = RequestMethod.GET)
    public ModelAndView list(ModelAndView modelAndView, HttpServletRequest request){
        Map<String, List<Menu>> map1 = (Map<String, List<Menu>>) request.getSession().getAttribute("map");
        List<Menu> menuList = map1.get("question");
        modelAndView.addObject("question",menuList);
        modelAndView.setViewName("question/question");
        return modelAndView;
    }

    /**
     * 获取试题列表数据
     * @param page
     * @param title
     * @param questionType
     * @return
     */
    @RequestMapping(value="/list",method=RequestMethod.POST)
    public Map<String, Object> getList(Page page,
                                       @RequestParam(name="title",required=false,defaultValue="") String title,
                                       @RequestParam(name="questionType",required=false,defaultValue="") String questionType,
                                       @RequestParam(name="subjectId",required=false,defaultValue="") Integer subjectId
    ){
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> queryMap = new HashMap<String, Object>();
        queryMap.put("title", "%"+title.trim()+"%");
        queryMap.put("start", page.getStart());
        queryMap.put("size", page.getRows());
        if (questionType.equals("")){
            questionType = null;
        }
        if (subjectId != null){
            if (subjectId.equals("-1")){
                subjectId = null;
            }
        }
        queryMap.put("questionType", questionType);
        queryMap.put("subjectId", subjectId);
        map.put("rows", questionService.findAllBySearch(queryMap));
        map.put("total", questionService.findCount(queryMap));
        return map;
    }

    /**
     * 添加试题
     * @param question
     * @return
     */
    @RequestMapping(value="/add",method=RequestMethod.POST)
    public Map<String, String> add(Question question){
        Map<String, String> map = new HashMap<String, String>();
        if(question == null){
            map.put("type", "error");
            map.put("msg", "请填写正确的试题信息！");
            return map;
        }
        //判断试题名是否存在
//        Question byName = questionService.findByTitle(question.getTitle());
//        if (byName != null){
//            map.put("type", "error");
//            map.put("msg", "试题名称已存在，请重新填写！");
//            return map;
//        }
        question.setCreateTime(new Date());
        if(questionService.add(question) <= 0){
            map.put("type", "error");
            map.put("msg", "试题信息添加失败，请联系管理员！");
            return map;
        }
        map.put("type", "success");
        map.put("msg", "试题信息添加成功！");
        return map;
    }

    /**
     * 修改学科信息
     * @param question
     * @return
     */
    @RequestMapping(value="/edit",method=RequestMethod.POST)
    public Map<String, String> edit(Question question){
        Map<String, String> map = new HashMap<String, String>();
        if(question == null){
            map.put("type", "error");
            map.put("msg", "请填写正确的试题信息！");
            return map;
        }
//        //判断试题名是否存在
//        Question byName = questionService.findByTitle(question.getTitle());
//        if (byName != null && !byName.getId().equals(question.getId())){
//            map.put("type", "error");
//            map.put("msg", "试题名称已存在，请重新填写！");
//            return map;
//        }
//        question.setCreateTime(new Date());
        if(questionService.edit(question) <= 0){
            map.put("type", "error");
            map.put("msg", "试题信息修改失败，请联系管理员！");
            return map;
        }
        map.put("type", "success");
        map.put("msg", "试题信息修改成功！");
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
        if(questionService.delete(id) <= 0){
            map.put("type", "error");
            map.put("msg", "试题删除失败，请联系管理员！");
            return map;
        }
        map.put("type", "success");
        map.put("msg", "试题删除成功！");
        return map;
    }

}
