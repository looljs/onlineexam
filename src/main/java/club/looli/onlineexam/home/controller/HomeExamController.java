package club.looli.onlineexam.home.controller;

import club.looli.onlineexam.admin.entity.*;
import club.looli.onlineexam.admin.service.ExamPaperAnswerDetailsService;
import club.looli.onlineexam.admin.service.ExamPaperService;
import club.looli.onlineexam.admin.service.ExamService;
import club.looli.onlineexam.admin.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 前台用户考试控制器
 */
@RestController
@RequestMapping("/home/exam")
public class HomeExamController {

    @Autowired
    private ExamService examService;
    @Autowired
    private ExamPaperService examPaperService;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private ExamPaperAnswerDetailsService examPaperAnswerDetailsService;


    /**
     * 进入考试界面
     * @param model
     * @param examId
     * @param request
     * @return
     */
    @RequestMapping(value="/examing",method= RequestMethod.GET)
    public ModelAndView index(ModelAndView model, Integer examId, HttpServletRequest request){
        Exam exam = examService.findById(examId);
        request.getSession().setAttribute("exam","exam");
        Examinee examinee = (Examinee) request.getSession().getAttribute("user");
        ExamPaper examPaper = new ExamPaper();


        //获取试题
        Map<String,Object> map = new HashMap<>();
        map.put("examId",exam.getId());
        map.put("examineeId",examinee.getId());
        List<Question> questions = examPaperAnswerDetailsService.findAllBySearch2(map);
        List<Question> singleQuestionList = new ArrayList<>();
        List<Question> muiltQuestionList = new ArrayList<>();
        List<Question> chargeQuestionList = new ArrayList<>();
        int muiltQuestionNum=0,singleQuestionNum=0,chargeQuestionNum=0;
        int muiltQuestionScore=0,singleQuestionScore=0,chargeQuestionScore=0;
        for (Question q :
                questions) {
            if (q.getQuestionType() == 0 ){
                singleQuestionList.add(q);
                singleQuestionNum++;
                singleQuestionScore += q.getScore();
            }
            if (q.getQuestionType() == 1 ){
                muiltQuestionList.add(q);
                muiltQuestionNum++;
                muiltQuestionScore += q.getScore();
            }
            if (q.getQuestionType() == 2 ){
                chargeQuestionList.add(q);
                chargeQuestionNum++;
                chargeQuestionScore += q.getScore();
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


        //

        Date now = new Date();
        Object attributeStartTime = request.getSession().getAttribute("startExamTime");
        if(attributeStartTime == null){
            request.getSession().setAttribute("startExamTime", now);
        }
        Date startExamTime = (Date)request.getSession().getAttribute("startExamTime");
        int passedTime = (int)(now.getTime() - startExamTime.getTime())/1000/60;
        if(passedTime >= exam.getAvaliableTime()){
            //表示时间已经耗尽，按零分处理
            examPaper.setScore(0);
            examPaper.setStartExamTime(startExamTime);
            examPaper.setStatus(1);
            examPaper.setUseTime(passedTime);
//            examPaperService.submitPaper(examPaper);
            model.setViewName("/home/exam/error");
            model.addObject("msg", "当前考试时间已耗尽，还未提交试卷，做0分处理！");
            return model;
        }
        Integer hour = (exam.getAvaliableTime()-passedTime)/60;
        Integer minitute = (exam.getAvaliableTime()-passedTime)%60;
        Integer second = (exam.getAvaliableTime()*60-(int)(now.getTime() - startExamTime.getTime())/1000)%60;

        //更新试卷开始时间
        Map<String, Object> queryMap = new HashMap<String, Object>();
        queryMap.put("examId", exam.getId());
        queryMap.put("examineeId", examinee.getId());
        ExamPaper find = examPaperService.find(queryMap);
        if (find.getStartExamTime() == null || find.getStartExamTime().equals("")){
            find.setStartExamTime(now);
            find.setStatus(1);
//            find.setStatus(1);
            examPaperService.updateStartExamTime(find);
        }

//        examPaper.setStartExamTime(now);
//        examPaperService.updateStartExamTime(examPaper);
        model.addObject("hour",hour);
        model.addObject("minitute",minitute);
        model.addObject("second",second);
        model.addObject("exam",exam);
        model.addObject("examPaper",examPaper);
        model.setViewName("home/exam/examing");
        return model;
    }


    /**
     * 开始考试前检查合法性，随机生成试题
     * @param examId
     * @return
     */
    @RequestMapping(value="/statr_exam",method= RequestMethod.POST)
    public Map<String, String> startExam(Integer examId, HttpServletRequest request){
        Map<String, String> ret = new HashMap<String, String>();
        //判断考试是否合理
        Exam exam = examService.findById(examId);
        request.getSession().setAttribute("exam2",exam);
        if(exam == null){
            ret.put("type", "error");
            ret.put("msg", "考试信息不存在!");
            return ret;
        }
        if(exam.getEndTime().getTime() < new Date().getTime()){
            ret.put("type", "error");
            ret.put("msg", "该考试已结束!");
            return ret;
        }
        //判断试卷是否合理
        Examinee examinee = (Examinee)request.getSession().getAttribute("user");
        if(exam.getSubjectId().longValue() != examinee.getSubjectId().longValue()){
            ret.put("type", "error");
            ret.put("msg", "学科不同，无权进行考试!");
            return ret;
        }
        Map<String, Object> queryMap = new HashMap<String, Object>();
        queryMap.put("examId", exam.getId());
        queryMap.put("examineeId", examinee.getId());
        ExamPaper find = examPaperService.find(queryMap);

        if(find != null){
            if(find.getStatus() == 1){
                //表示已经考过
                ret.put("type", "error");
                ret.put("msg", "您已经考过这门考试了，不能再考!");
                return ret;
            }
            //走到这里表示试卷已经生成，但是没有提交考试，可以开始重新考试
            ret.put("type", "success");
            ret.put("msg", "可以开始考试");
            return ret;
        }
        //此时，说明符合考试条件，随机生成试卷试题
        //要做判断，看是否满足生成试卷的条件
        //获取单选题总数
        Map<String, Integer> qMap = new HashMap<String, Integer>();
        qMap.put("questionType",0);
        qMap.put("subjectId", exam.getSubjectId());
        int singleQuestionTotalNum = questionService.getQuestionNumByType(qMap);
        if(exam.getSingleQuestionNum() > singleQuestionTotalNum){
            ret.put("type", "error");
            ret.put("msg", "单选题数量超过题库单选题总数，无法生成试卷!");
            return ret;
        }
        //获取多选题总数
        qMap.put("questionType", 1);
        int muiltQuestionTotalNum = questionService.getQuestionNumByType(qMap);
        if(exam.getMuiltQuestionNum() > muiltQuestionTotalNum){
            ret.put("type", "error");
            ret.put("msg", "多选题数量超过题库多选题总数，无法生成试卷!");
            return ret;
        }
        //获取判断题总数
        qMap.put("questionType", 2);
        int chargeQuestionTotalNum = questionService.getQuestionNumByType(qMap);
        if(exam.getChargeQuestionNum() > chargeQuestionTotalNum){
            ret.put("type", "error");
            ret.put("msg", "判断题数量超过题库判断题总数，无法生成试卷!");
            return ret;
        }
        //所有条件都满足，开始创建试卷，随机生成试题
        ExamPaper examPaper = new ExamPaper();
        examPaper.setCreateTime(new Date());
        examPaper.setExamId(examId);
        examPaper.setStatus(0);
        examPaper.setExamineeId(examinee.getId());
        examPaper.setTotalScore(exam.getTotalScore());
        examPaper.setUseTime(0);
        if(examPaperService.add(examPaper) <= 0){
            ret.put("type", "error");
            ret.put("msg", "试卷生成失败，请联系管理员!");
            return ret;
        }
        request.getSession().setAttribute("examId",examId);
        request.getSession().setAttribute("examPaperId",examPaper.getId());
        //更新考试试卷数量
        examService.updateExam(exam);
        //试卷已经正确生成，现在开始随机生成试题
        Map<String, Object> queryQuestionMap = new HashMap<String, Object>();
        queryQuestionMap.put("questionType", 0);
        queryQuestionMap.put("subjectId", exam.getSubjectId());

        if(exam.getSingleQuestionNum() > 0){
            //考试规定单选题数量大于0
            //获取所有的单选题
            List<Question> singleQuestionList = questionService.findAllBySearch(queryQuestionMap);
            //随机选取考试规定数量的单选题，插入到数据库中
            List<Question> selectedSingleQuestionList = getRandomList(singleQuestionList, exam.getSingleQuestionNum());
            insertQuestionAnswer(selectedSingleQuestionList, examId, examPaper.getId(), examinee.getId());
        }
        if(exam.getMuiltQuestionNum() > 0){
            queryQuestionMap.put("questionType", 1);
            //获取所有的多选题
            List<Question> muiltQuestionList = questionService.findAllBySearch(queryQuestionMap);
            //随机选取考试规定数量的多选题，插入到数据库中
            List<Question> selectedMuiltQuestionList = getRandomList(muiltQuestionList, exam.getMuiltQuestionNum());
            insertQuestionAnswer(selectedMuiltQuestionList, examId, examPaper.getId(), examinee.getId());

        }
        if(exam.getChargeQuestionNum() > 0){
            //获取所有的判断题
            queryQuestionMap.put("questionType", 2);
            List<Question> chargeQuestionList = questionService.findAllBySearch(queryQuestionMap);
            //随机选取考试规定数量的判断题，插入到数据库中
            List<Question> selectedChargeQuestionList = getRandomList(chargeQuestionList, exam.getChargeQuestionNum());
            insertQuestionAnswer(selectedChargeQuestionList, examId, examPaper.getId(), examinee.getId());
        }
        exam.setPaperNum(exam.getPaperNum() + 1);
        examService.updateExam(exam);
        ret.put("type", "success");
        ret.put("msg", "试卷生成成功!");
        return ret;
    }

    /**
     * 插入指定数量的试题到答题详情表
     * @param questionList
     * @param examId
     * @param examPaperId
     * @param examineeid
     */
    private void insertQuestionAnswer(List<Question> questionList,Integer examId,Integer examPaperId,Integer examineeid){
        for(Question question:questionList){
            ExamPaperAnswerDetails examPaperAnswer = new ExamPaperAnswerDetails();
            examPaperAnswer.setExamId(examId);
            examPaperAnswer.setExamPaperId(examPaperId);
            examPaperAnswer.setIsCorrect(0);
            examPaperAnswer.setQuestionId(question.getId());
            examPaperAnswer.setExamineeId(examineeid);
            examPaperAnswerDetailsService.add(examPaperAnswer);
        }
    }

    /**
     * 随机从给定的list中选取给定数量的元素
     * @param questionList
     * @param n
     * @return
     */
    private List<Question> getRandomList(List<Question> questionList,int n){
        if(questionList.size() <= n)return questionList;
        Map<Integer, String> selectedMap = new HashMap<Integer, String>();
        List<Question> selectedList = new ArrayList<Question>();
        while(selectedList.size() < n){
            for(Question question:questionList){
                int index = (int)(Math.random() * questionList.size());
                if(!selectedMap.containsKey(index)){
                    selectedMap.put(index, "");
                    selectedList.add(questionList.get(index));
                    if(selectedList.size() >= n)break;
                }
            }
        }
        return selectedList;
    }
    /**
     * 用户选择答题提交单个答案
     * @param
     * @param request
     * @return
     */
    @RequestMapping(value="/submit_answer",method=RequestMethod.POST)
    public Map<String, String> submitAnswer(@RequestParam(name = "questionId",defaultValue = "") Integer questionId,
                                            @RequestParam(name = "answer",defaultValue = "") String answer, HttpServletRequest request){
        Map<String, String> ret = new HashMap<String, String>();
        Exam exam = (Exam) request.getSession().getAttribute("exam2");
        if(exam == null){
            ret.put("type", "error");
            ret.put("msg", "考试信息不存在!");
            return ret;
        }
        if(exam.getEndTime().getTime() < new Date().getTime()){
            ret.put("type", "error");
            ret.put("msg", "该考试已结束!");
            return ret;
        }
        Examinee examinee = (Examinee)request.getSession().getAttribute("user");
        if(exam.getSubjectId().longValue() != examinee.getSubjectId().longValue()){
            ret.put("type", "error");
            ret.put("msg", "学科不同，无权进行考试!");
            return ret;
        }
        Map<String, Object> queryMap = new HashMap<String, Object>();
        queryMap.put("examId", exam.getId());
        queryMap.put("examineeId", examinee.getId());
        ExamPaper findExamPaper = examPaperService.find(queryMap);
        if(findExamPaper == null){
            ret.put("type", "error");
            ret.put("msg", "不存在试卷!");
            return ret;
        }
        Question question = questionService.findById(questionId);
        if(question == null){
            ret.put("type", "error");
            ret.put("msg", "试题不存在，请规范操作!");
            return ret;
        }
        ExamPaperAnswerDetails examPaperAnswerDetails = new ExamPaperAnswerDetails();
        examPaperAnswerDetails.setExamineeId(examinee.getId());
        examPaperAnswerDetails.setExamId(exam.getId());
        examPaperAnswerDetails.setQuestionId(questionId);
        examPaperAnswerDetails.setAnswer(answer);
        //此时，可以将答案插入到数据库中
        if(question.getAnswer().equals(answer)){
            examPaperAnswerDetails.setIsCorrect(1);
        }else {
            examPaperAnswerDetails.setIsCorrect(0);
        }
        if(examPaperAnswerDetailsService.submitAnswer(examPaperAnswerDetails) <= 0){
            ret.put("type", "error");
            ret.put("msg", "答题出错，请联系管理员!");
            return ret;
        }
        ret.put("type", "success");
        ret.put("msg", "答题成功!");
        return ret;
    }

    /**
     * 提交考试
     * @param request
     * @return
     */
    @RequestMapping(value="/submit_exam",method=RequestMethod.POST)
    public Map<String, String> submitExam(HttpServletRequest request){
        Integer examId = (Integer) request.getSession().getAttribute("examId");
        Integer examPaperId = (Integer) request.getSession().getAttribute("examPaperId");
        Map<String, String> ret = new HashMap<String, String>();
        Exam exam = examService.findById(examId);
        if(exam == null){
            ret.put("type", "error");
            ret.put("msg", "考试不存在，请正确操作!");
            return ret;
        }
        if(exam.getEndTime().getTime() < new Date().getTime()){
            ret.put("type", "error");
            ret.put("msg", "该考试已结束!");
            return ret;
        }
        Examinee examinee = (Examinee) request.getSession().getAttribute("user");
        if(exam.getSubjectId().longValue() != examinee.getSubjectId().longValue()){
            ret.put("type", "error");
            ret.put("msg", "学科不同，无权进行操作!");
            return ret;
        }
        Map<String, Object> queryMap = new HashMap<String, Object>();
        queryMap.put("examId", exam.getId());
        queryMap.put("examineeId", examinee.getId());
        ExamPaper examPaper = examPaperService.find(queryMap);
        if(examPaper == null){
            ret.put("type", "error");
            ret.put("msg", "不存在试卷!");
            return ret;
        }
        if(examPaper.getId().intValue() != examPaperId.intValue()){
            ret.put("type", "error");
            ret.put("msg", "考试试卷不正确，请规范操作!");
            return ret;
        }
//        if(examPaper.getStatus() == 1){
//            ret.put("type", "error");
//            ret.put("msg", "请勿重复交卷!");
//            return ret;
//        }
        //此时计算考试得分
        queryMap.put("examPaperId", examPaperId);
        List<Question> questions = examPaperAnswerDetailsService.findAllBySearch3(queryMap);
        int score = 0;
        for(Question question:questions){
            score += question.getScore();
        }
        examPaper.setEndExamTime(new Date());
        examPaper.setScore(score);
//        examPaper.setStartExamTime((Date)request.getSession().getAttribute("startExamTime"));
        examPaper.setStatus(1);
        examPaper.setUseTime((int)(examPaper.getEndExamTime().getTime()-examPaper.getStartExamTime().getTime())/1000/60);
        examPaperService.submitPaper(examPaper);
        //计算考试统计结果,更新考试的已考人数，及格人数
        exam.setExamedNum(exam.getExamedNum() + 1);
        if(examPaper.getScore() >= exam.getPassScore()){
            //说明及格了
            exam.setPassNum(exam.getPassNum() + 1);
        }
        request.getSession().setAttribute("startExamTime", null);
        //更新统计结果
        examService.updateExam(exam);
        ret.put("type", "success");
        ret.put("msg", "提交成功!");
        return ret;
    }
}
