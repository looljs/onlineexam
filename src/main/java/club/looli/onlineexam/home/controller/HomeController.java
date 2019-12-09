package club.looli.onlineexam.home.controller;

import club.looli.onlineexam.admin.entity.Examinee;
import club.looli.onlineexam.admin.entity.Subject;
import club.looli.onlineexam.admin.service.ExamService;
import club.looli.onlineexam.admin.service.ExamineeService;
import club.looli.onlineexam.admin.service.SubjectService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 前台主页控制器
 */
@RestController
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private ExamineeService examineeService;

    @Autowired
    private ExamService examService;

    /**
     * 前台用户登录
     * @param modelAndView
     * @return
     */
    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public ModelAndView login(ModelAndView modelAndView){
        modelAndView.setViewName("home/login");
        return modelAndView;
    }

    /**
     * 前台用户welcome
     * @param modelAndView
     * @return
     */
    @RequestMapping(value = "/welcome",method = RequestMethod.GET)
    public ModelAndView welcome(ModelAndView modelAndView,HttpServletRequest request){
        //从session中获取用户信息，获取考生学科id
        Examinee user = (Examinee) request.getSession().getAttribute("user");
        Integer id = user.getSubjectId();
        String name = user.getName();

        Map<String, Object> map = new HashMap<>();
        map.put("subjectId",id);
        String byId = subjectService.findById(id);
        map.put("time",new Date());
        //获取进行中的考试
        modelAndView.addObject("examList",examService.findExamsInProgressBySearch(map));
        modelAndView.addObject("subjectName",byId);

        //获取考过的试
        modelAndView.addObject("historyList",examService.findHistoryExamBySearch(name));

        modelAndView.setViewName("home/welcome");
        return modelAndView;
    }

    /**
     * 前台用户head
     * @param modelAndView
     * @return
     */
    @RequestMapping(value = "/head",method = RequestMethod.GET)
    public ModelAndView head(ModelAndView modelAndView){
        modelAndView.setViewName("home/head");
        return modelAndView;
    }

    /**
     * 前台用户menu
     * @param modelAndView
     * @return
     */
    @RequestMapping(value = "/menu",method = RequestMethod.GET)
    public ModelAndView menu(ModelAndView modelAndView){
        modelAndView.setViewName("home/menu");
        return modelAndView;
    }

    /**
     * 前台用户登录验证
     * @param examinee
     * @return
     */
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public Map<String, Object> login(Examinee examinee, HttpServletRequest request){
        Map<String, Object> map = new HashMap<>();
        Examinee byName = examineeService.findByName(examinee.getName());
        if (byName != null && StringUtils.equals(examinee.getPassword(),byName.getPassword())){
            map.put("type","success");
            map.put("msg","登录成功");
            request.getSession().setAttribute("name",byName.getName());
            request.getSession().setAttribute("user",byName);
            return map;
        }else {
            map.put("type","error");
            map.put("msg","登录失败！用户名或密码错误");
            return map;
        }
    }


    /**
     * 前台用户注册
     * @param modelAndView
     * @return
     */
    @RequestMapping(value = "/register",method = RequestMethod.GET)
    public ModelAndView register(ModelAndView modelAndView){
        List<Subject> all = subjectService.findAll();
        modelAndView.addObject("subjectList",all);
        modelAndView.setViewName("home/register");
        return modelAndView;
    }

    /**
     * 注册信息提交处理
     * @param examinee
     * @return
     */
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public Map<String, Object> register(Examinee examinee){
        Map<String, Object> map = new HashMap<>();
        examinee.setCreateTime(new Date());
        Examinee byName = examineeService.findByName(examinee.getName());
        if (byName != null){
            map.put("type","error");
            map.put("msg","注册失败！用户名已存在");
        }
        if (examineeService.add(examinee)<=0){
            map.put("type","error");
            map.put("msg","注册失败");
        }
        map.put("type","success");
        map.put("msg","注册成功");
        return map;
    }
}
