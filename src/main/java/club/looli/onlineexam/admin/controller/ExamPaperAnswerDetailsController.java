package club.looli.onlineexam.admin.controller;

import club.looli.onlineexam.admin.entity.Menu;
import club.looli.onlineexam.admin.page.Page;
import club.looli.onlineexam.admin.service.ExamPaperAnswerDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 试卷答题详情管理控制器
 */
@RestController
@RequestMapping("/admin/examPaperAnswerDetails")
public class ExamPaperAnswerDetailsController {

    @Autowired
    private ExamPaperAnswerDetailsService examPaperAnswerDetailsService;

    /**
     * 点击试卷答题详情列表
     * @param modelAndView
     * @return 试卷答题详情列表页面
     */
    @RequestMapping(value = "/examPaperAnswerDetailsList",method = RequestMethod.GET)
    public ModelAndView list(ModelAndView modelAndView, HttpServletRequest request){
        Map<String, List<Menu>> map1 = (Map<String, List<Menu>>) request.getSession().getAttribute("map");
        List<Menu> menuList = map1.get("examPaperAnswerDetails");
        modelAndView.addObject("examPaperAnswerDetails",menuList);
        modelAndView.setViewName("examPaperAnswerDetails/examPaperAnswerDetails");
        return modelAndView;
    }

    /**
     * 获取试卷答题详情列表数据
     * @param page
     * @param questionId
     * @param studentId
     * @param examId
     * @return
     */
    @RequestMapping(value="/list",method=RequestMethod.POST)
    public Map<String, Object> getList(Page page,
                                       @RequestParam(name="studentId",required=false,defaultValue="") Integer studentId,
                                       @RequestParam(name="examId",required=false,defaultValue="") Integer examId,
                                       @RequestParam(name="questionId",required=false,defaultValue="") Integer questionId
    ){
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> queryMap = new HashMap<String, Object>();
        queryMap.put("questionId", questionId);
        queryMap.put("examineeId", studentId);
        queryMap.put("examId", examId);
        queryMap.put("start", page.getStart());
        queryMap.put("size", page.getRows());
        map.put("rows", examPaperAnswerDetailsService.findAllBySearch(queryMap));
        map.put("total", examPaperAnswerDetailsService.findCount(queryMap));
        return map;
    }

//    /**
//     * 编辑试卷答题详情
//     * @param examPaperAnswerDetails
//     * @return
//     */
//    @RequestMapping(value="/edit",method=RequestMethod.POST)
//    public Map<String, String> edit(ExamPaperAnswerDetails examPaperAnswerDetails){
//        Map<String, String> map = new HashMap<String, String>();
//        if(examPaperAnswerDetails == null){
//            map.put("type", "error");
//            map.put("msg", "请填写正确的试卷答题详情信息！");
//            return map;
//        }
//        if(examPaperAnswerDetailsService.edit(examPaperAnswerDetails) <= 0){
//            map.put("type", "error");
//            map.put("msg", "试卷答题详情信息添加失败，请联系管理员！");
//            return map;
//        }
//        map.put("type", "success");
//        map.put("msg", "试卷答题详情信息添加成功！");
//        return map;
//    }

}
