package club.looli.onlineexam.home.controller;

import club.looli.onlineexam.admin.entity.Exam;
import club.looli.onlineexam.admin.entity.ExamPaper;
import club.looli.onlineexam.admin.entity.Examinee;
import club.looli.onlineexam.admin.entity.Question;
import club.looli.onlineexam.admin.service.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping("/home/user")
public class HomeUserController {

    @Autowired
    private ExamineeService examineeService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private ExamService examService;

    @Autowired
    private ExamPaperService examPaperService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private ExamPaperAnswerDetailsService examPaperAnswerDetailsService;

    /**
     * 前往考生中心首页
     * @param modelAndView
     * @return
     */
    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public ModelAndView login(ModelAndView modelAndView){
        modelAndView.setViewName("home/user/index");
        return modelAndView;
    }

    /**
     *
     * 获取当前用户
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/get_current",method = RequestMethod.POST)
    public Map<String, Object> getCurrent(HttpServletRequest httpServletRequest){
        String name = (String) httpServletRequest.getSession().getAttribute("name");
        Map<String, Object> map = new HashMap<>();
        if (name == null){
            map.put("type","error");
            map.put("msg","登录信息失效!");
        }
        map.put("type","success");
        map.put("truename",name);
        return map;
    }

    /**
     * 获取当前用户信息
     * @param modelAndView
     * @param request
     * @return
     */
    @RequestMapping(value = "/profile",method = RequestMethod.GET)
    public ModelAndView profile(ModelAndView modelAndView,HttpServletRequest request){
        String name = (String) request.getSession().getAttribute("name");
        Examinee byName = examineeService.findByName(name);
        modelAndView.addObject("info",byName);

        //获取学科
        String byId = subjectService.findById(byName.getSubjectId());
        modelAndView.addObject("subjectName",byId);

        modelAndView.setViewName("home/user/profile");
        return modelAndView;
    }

    /**
     * 更新用户信息
     * @param trueName
     * @param tel
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/update_info",method = RequestMethod.POST)
    public Map<String, Object> updateInfo(
            @RequestParam(name = "trueName",required = false ,defaultValue = "") String trueName,
            @RequestParam(name = "tel",required = false,defaultValue = "") String tel,
            HttpServletRequest httpServletRequest){
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isEmpty(trueName)){
            map.put("type","error");
            map.put("msg","请填写真实姓名!");
        }
        String name = (String) httpServletRequest.getSession().getAttribute("name");
        Examinee byName = examineeService.findByName(name);
        byName.setTrueName(trueName);
        byName.setTel(tel);
        examineeService.edit(byName);
        map.put("type","success");
        return map;
    }

    /**
     * 登出功能
     * @param request
     * @param modelAndView
     * @return
     */
    @RequestMapping(value = "/logout",method = RequestMethod.GET)
    public ModelAndView logout(
            HttpServletRequest request,
            ModelAndView modelAndView
    ){
        request.getSession().setAttribute("name",null);
        request.getSession().setAttribute("subjectName",null);
        request.getSession().setAttribute("info",null);
        request.getSession().setAttribute("startExamTime", null);
        modelAndView.setViewName("/home/login");
        return modelAndView;
    }


    /**
     * 修改密码
     * @param modelAndView
     * @param request
     * @return
     */
    @RequestMapping(value = "/password",method = RequestMethod.GET)
    public ModelAndView password(ModelAndView modelAndView,HttpServletRequest request){
        String name = (String) request.getSession().getAttribute("name");
        modelAndView.addObject("name",name);
        modelAndView.setViewName("home/user/password");
        return modelAndView;
    }


    /**
     * 修改密码
     * @param password
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/update_password",method = RequestMethod.POST)
    public Map<String, Object> updatePassword(
            @RequestParam(name = "password",required = false,defaultValue = "") String password,
            HttpServletRequest httpServletRequest
            ){
        Map<String, Object> map = new HashMap<>();
        String name = (String) httpServletRequest.getSession().getAttribute("name");
        Examinee byName = examineeService.findByName(name);
        if (!StringUtils.equals(byName.getPassword(),password)){
            map.put("type","error");
            map.put("msg","旧密码错误!");
        }
        byName.setPassword(password);
        examineeService.edit(byName);
        map.put("type","success");
        return map;
    }

    /**
     * 我的考试
     * @param modelAndView
     * @param request
     * @param page
     * @param name
     * @return
     */
    @RequestMapping(value = "/exam_list",method = RequestMethod.GET)
    public ModelAndView examList(ModelAndView modelAndView, HttpServletRequest request,
                                 @RequestParam(name = "page" , required = false , defaultValue = "") Integer page,
                                 @RequestParam(name = "name" , required = false , defaultValue = "") String name){

        if (page == null){
            page = 1;
        }
        if ( page.equals("")){
            page = 1;
        }
        if (name == null){
            name = "";
        }
        Map<String, Object> map = new HashMap<>();
        if ( ((page - 1 ) < 0)){
            page = 1 ;
        }
        if (page <=0 ){
            page = 1 ;
        }
        map.put("start",(page - 1 ) * 10);
        map.put("size",10);
        map.put("name","%"+name+"%");
        map.put("time",new Date());
        Examinee user = (Examinee) request.getSession().getAttribute("user");
        map.put("subjectId",user.getSubjectId());
        String byId = subjectService.findById(user.getSubjectId());
        List<Exam> examsBySearch = examService.findExamsBySearch(map);
        if (examsBySearch.size() <= 0){
            page = page - 1 ;
            if ((page - 1) < 0 ){
                page = 1;
            }
            map.put("start",(page - 1 ) * 10);
            map.put("size",10);
            map.put("name","%"+name+"%");
            map.put("examineeId",user.getId());
            examsBySearch = examService.findExamsBySearch(map);
        }
        modelAndView.addObject("page",page);
        modelAndView.addObject("examList",examsBySearch);
        modelAndView.addObject("name",name);
        modelAndView.addObject("byId",byId);
        modelAndView.setViewName("home/user/exam_list");
        return modelAndView;
    }

    /**
     * 历史考试
     * @param modelAndView
     * @param request
     * @param page
     * @param name
     * @return
     */
    @RequestMapping(value = "/history_list",method = RequestMethod.GET)
    public ModelAndView historyList(ModelAndView modelAndView, HttpServletRequest request,
                                 @RequestParam(name = "page" , required = false , defaultValue = "") Integer page,
                                 @RequestParam(name = "name" , required = false , defaultValue = "") String name){

        if (page == null){
            page = 1;
        }
        if ( page.equals("")){
            page = 1;
        }
        if (name == null){
            name = "";
        }
        Map<String, Object> map = new HashMap<>();
        if ( ((page - 1 ) < 0)){
            page = 1 ;
        }
        map.put("start",(page - 1 ) * 10);
        map.put("size",10);
        map.put("name","%"+name+"%");

        Examinee user = (Examinee) request.getSession().getAttribute("user");
        map.put("examineeId",user.getId());
        List<Map<String, Object>> historyExamsBySearch = examService.findHistoryExamsBySearch(map);
        if (historyExamsBySearch.size() <= 0){
            page = page-1;
            if (page <=0 ){
                page = 1 ;
            }
            map.put("start",(page - 1 ) * 10);
            map.put("size",10);
            map.put("name","%"+name+"%");
            map.put("examineeId",user.getId());
            historyExamsBySearch = examService.findHistoryExamsBySearch(map);
        }
        String byId = subjectService.findById(user.getSubjectId());
        modelAndView.addObject("page",page);
        modelAndView.addObject("historyList",historyExamsBySearch);
        modelAndView.addObject("name",name);
        modelAndView.addObject("byId",byId);
        modelAndView.setViewName("home/user/history_list");
        return modelAndView;
    }

    /**
     * 回顾考试
     * @param model
     * @param examId
     * @param examPaperId
     * @param request
     * @return
     */
    @RequestMapping(value = "/review_exam",method = RequestMethod.GET)
    public ModelAndView reviewExam(ModelAndView model,
                              @RequestParam(name = "examId",defaultValue = "") Integer examId,
                              @RequestParam(name = "examPaperId",defaultValue = "") Integer examPaperId,HttpServletRequest request){
        Examinee examinee = (Examinee) request.getSession().getAttribute("user");
        Exam exam = examService.findById(examId);
        if(exam == null){
            model.setViewName("/home/user/error");
            model.addObject("msg", "当前考试不存在!");
            return model;
        }
        Map<String, Object> queryMap = new HashMap<String, Object>();
        queryMap.put("examId", examId);
        queryMap.put("examineeId", examinee.getId());
        //根据考试信息和学生信息获取试卷
        ExamPaper examPaper = examPaperService.find(queryMap);
        if(examPaper == null){
            model.setViewName("/home/user/error");
            model.addObject("msg", "当前考试不存在试卷");
            return model;
        }
        if(examPaper.getStatus() == 0){
            model.setViewName("/home/user/error");
            model.addObject("msg", "您还没有考过这门考试！");
            return model;
        }

        //获取试题
        Map<String,Object> map = new HashMap<>();
        map.put("examId",exam.getId());
        map.put("examineeId",examinee.getId());
        List<Map<String,Object>> questions = examPaperAnswerDetailsService.findAllBySearch4(map);
        List<Map<String,Object>> singleQuestionList = new ArrayList<>();
        List<Map<String,Object>> muiltQuestionList = new ArrayList<>();
        List<Map<String,Object>> chargeQuestionList = new ArrayList<>();
        int muiltQuestionNum=0,singleQuestionNum=0,chargeQuestionNum=0;
        int muiltQuestionScore=0,singleQuestionScore=0,chargeQuestionScore=0;
        for (Map<String,Object> q :
                questions) {
            if ((int)q.get("questionType") == 0 ){
                singleQuestionList.add(q);
                singleQuestionNum++;
                singleQuestionScore += (int)q.get("score");
            }
            if ((int)q.get("questionType") == 1 ){
                muiltQuestionList.add(q);
                muiltQuestionNum++;
                muiltQuestionScore += (int)q.get("score");
            }
            if ((int)q.get("questionType") == 2 ){
                chargeQuestionList.add(q);
                chargeQuestionNum++;
                chargeQuestionScore += (int)q.get("score");
            }
        }
        model.addObject("singleQuestionList",singleQuestionList);
        model.addObject("muiltQuestionList",muiltQuestionList);
        model.addObject("chargeQuestionList",chargeQuestionList);
        model.addObject("singleQuestionNum",singleQuestionNum);
        model.addObject("muiltQuestionNum",muiltQuestionNum);
        model.addObject("chargeQuestionNum",chargeQuestionNum);
        model.addObject("singleQuestionScore",singleQuestionScore);
        model.addObject("muiltQuestionScore",muiltQuestionScore);
        model.addObject("chargeQuestionScore",chargeQuestionScore);
        model.addObject("exam",exam);
        model.addObject("examPaper",examPaper);
        model.setViewName("home/user/review_exam");
        Integer hour = (examPaper.getUseTime())/60;
        Integer minitute = (examPaper.getUseTime())%60;
        Integer second = (examPaper.getUseTime()/1000)%60;
        model.addObject("hour",hour);
        model.addObject("minitute",minitute);
        model.addObject("second",second);
        return model;
    }


    /**
     * 跳转到错误界面
     * @param model
     * @return
     */
    @RequestMapping(value = "/error",method = RequestMethod.GET)
    public ModelAndView error(ModelAndView model){
        model.setViewName("home/user/error");
       return model;
    }

}
