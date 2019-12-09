package club.looli.onlineexam.admin.controller;

import club.looli.onlineexam.admin.service.ExamPaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 成绩统计管理控制器
 */
@RestController
@RequestMapping("/admin/stats")
public class StatsController {

    @Autowired
    private ExamPaperService examPaperService;

    @RequestMapping(value = "/exam_stats",method = RequestMethod.GET)
    public ModelAndView list(ModelAndView modelAndView){
        modelAndView.setViewName("stats/exam_list");
        return modelAndView;
    }

    /**
     * 根据考试信息统计结果
     * @param examId
     * @return
     */
    @RequestMapping(value="/get_stats",method=RequestMethod.POST)
    public Map<String, Object> getStats(@RequestParam("examId") Integer examId){
        Map<String, Object> ret = new HashMap<String, Object>();
        if(examId == null){
            ret.put("type", "error");
            ret.put("msg", "选择要统计的考试信息！");
            return ret;
        }
        List<Map<String, Object>> examStats = examPaperService.getExamStats(examId);
        ret.put("type", "success");
        ret.put("msg", "统计成功！");
        ret.put("examList", getListByMap(examStats, "name"));
        ret.put("examScore", getListByMap(examStats, "score"));
        return ret;
    }

    private List<Object> getListByMap(List<Map<String, Object>> mapList,String key){
        List<Object> ret = new ArrayList<Object>();
        for(Map<String, Object> map:mapList){
            ret.add(map.get(key));
        }
        return ret;
    }

}
