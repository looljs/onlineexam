package club.looli.onlineexam.admin.controller;

import club.looli.onlineexam.admin.entity.Exam;
import club.looli.onlineexam.admin.entity.Menu;
import club.looli.onlineexam.admin.page.Page;
import club.looli.onlineexam.admin.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 考试管理控制器
 */
@RestController
@RequestMapping("/admin/exam")
public class ExamController {

    @Autowired
    private ExamService examService;

    /**
     * 点击考试列表
     * @param modelAndView
     * @return 考试列表页面
     */
    @RequestMapping(value = "/examList",method = RequestMethod.GET)
    public ModelAndView list(ModelAndView modelAndView, HttpServletRequest request){
        Map<String, List<Menu>> map1 = (Map<String, List<Menu>>) request.getSession().getAttribute("map");
        List<Menu> menuList = map1.get("exam");
        modelAndView.addObject("exam",menuList);
        modelAndView.setViewName("exam/exam");
        return modelAndView;
    }

    /**
     * 获取考试列表数据
     * @param page
     * @param name
     * @return
     */
    @RequestMapping(value="/list",method=RequestMethod.POST)
    public Map<String, Object> getList(Page page,
                                       @RequestParam(name="name",required=false,defaultValue="") String name,
                                       @RequestParam(name="subjectId",required=false,defaultValue="") Integer subjectId,
                                       @RequestParam(name="startTime",required=false,defaultValue="") String startTime,
                                       @RequestParam(name="endTime",required=false,defaultValue="") String endTime
    ){
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> queryMap = new HashMap<String, Object>();
        queryMap.put("name", "%"+name.trim()+"%");
        queryMap.put("start", page.getStart());
        queryMap.put("size", page.getRows());
        if (subjectId != null){
            if (subjectId.equals("-1")){
                subjectId = null;
            }
        }
        if (startTime != null){
            if (startTime.equals("")){
                startTime = null;
            }
        }
        if (endTime != null){
            if (endTime.equals("")){
                endTime = null;
            }
        }
        queryMap.put("subjectId", subjectId);
        queryMap.put("startTime", startTime);
        queryMap.put("endTime", endTime);
        map.put("rows", examService.findAllBySearch(queryMap));
        map.put("total", examService.findCount(queryMap));
        return map;
    }


    @RequestMapping(value="/add",method=RequestMethod.POST)
    public Map<String, String> add(
            @RequestParam(name = "name") String name,
            @RequestParam(name = "subjectId") Integer subjectId,
            @RequestParam(name = "startTime") String startTime,
            @RequestParam(name = "endTime") String endTime,
            @RequestParam(name = "avaliableTime") Integer avaliableTime,
            @RequestParam(name = "totalScore") Integer totalScore,
            @RequestParam(name = "passScore") Integer passScore,
            @RequestParam(name = "singleQuestionNum") Integer singleQuestionNum,
            @RequestParam(name = "muiltQuestionNum") Integer muiltQuestionNum,
            @RequestParam(name = "chargeQuestionNum") Integer chargeQuestionNum) throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Map<String, String> map = new HashMap<String, String>();

        Exam exam = new Exam();
        exam.setName(name);
        exam.setSubjectId(subjectId);
        exam.setStartTime(sdf.parse(startTime));
        exam.setEndTime(sdf.parse(endTime));
        exam.setAvaliableTime(avaliableTime);
        exam.setTotalScore(totalScore);
        exam.setPassScore(passScore);
        exam.setSingleQuestionNum(singleQuestionNum);
        exam.setChargeQuestionNum(chargeQuestionNum);
        exam.setMuiltQuestionNum(muiltQuestionNum);
        exam.setQuestionNum(exam.getSingleQuestionNum()+exam.getMuiltQuestionNum()+exam.getChargeQuestionNum());
        exam.setCreateTime(new Date());
        exam.setPassNum(0);
        if(examService.add(exam) <= 0){
            map.put("type", "error");
            map.put("msg", "考试信息添加失败，请联系管理员！");
            return map;
        }
        map.put("type", "success");
        map.put("msg", "考试信息添加成功！");
        return map;
    }

    /**
     * 修改考试信息
     * @param exam
     * @return
     */
    @RequestMapping(value="/edit",method=RequestMethod.POST)
    public Map<String, String> edit(Exam exam){
        Map<String, String> map = new HashMap<String, String>();
        if(exam == null){
            map.put("type", "error");
            map.put("msg", "请填写正确的考试信息！");
            return map;
        }
//        //判断考试名是否存在
//        Exam byName = examService.findByname(Exam.getname());
//        if (byName != null && !byName.getId().equals(Exam.getId())){
//            map.put("type", "error");
//            map.put("msg", "考试名称已存在，请重新填写！");
//            return map;
//        }
//        Exam.setCreateTime(new Date());
        if(examService.edit(exam) <= 0){
            map.put("type", "error");
            map.put("msg", "考试信息修改失败，请联系管理员！");
            return map;
        }
        map.put("type", "success");
        map.put("msg", "考试信息修改成功！");
        return map;
    }

    /**
     * 删除考试信息
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
            map.put("msg", "考试删除失败，请联系管理员！");
            return map;
        }
        map.put("type", "success");
        map.put("msg", "考试删除成功！");
        return map;
    }

    /**
     * 显示考试下拉列表 + 全部
     * @return
     */
    @RequestMapping(value="/list1",method=RequestMethod.POST)
    public List<Exam> getList1() {
        List<Exam> all = examService.findAll();
        all.add(0,new Exam(-1,"全部"));
        return all;
    }

    /**
     * 显示考试下拉列表
     * @return
     */
    @RequestMapping(value="/list2",method=RequestMethod.POST)
    public List<Exam> getList2() {
        List<Exam> all = examService.findAll();
        return all;
    }
}
