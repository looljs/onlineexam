package club.looli.onlineexam.admin.controller;

import club.looli.onlineexam.admin.entity.Menu;
import club.looli.onlineexam.admin.entity.Question;
import club.looli.onlineexam.admin.page.Page;
import club.looli.onlineexam.admin.service.QuestionService;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
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
     * 修改试题信息
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
     * 删除试题信息
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

    /**
     * 显示试题下拉列表 + 全部
     * @return
     */
    @RequestMapping(value="/list1",method=RequestMethod.POST)
    public List<Question> getList1() {
        List<Question> all = questionService.findAll();
        all.add(0,new Question(-1,"全部"));
        return all;
    }


    /**
     * 上传文件批量导入试题
     * @param excelFile
     * @return
     */
    @RequestMapping(value="upload_file",method=RequestMethod.POST)
    public Map<String, Object> uploadFile(MultipartFile excelFile, Integer subjectId){
        Map<String, Object> map = new HashMap<>();
        if(excelFile == null){
            map.put("type", "error");
            map.put("msg", "请选择文件!");
            return map;
        }
        if(subjectId == null){
            map.put("type", "error");
            map.put("msg", "请选择所属科目!");
            return map;
        }
        if(excelFile.getSize() > 5 * 1024 * 1024){
            map.put("type", "error");
            map.put("msg", "文件大小不要超过5M!");
            return map;
        }
        String msg = "";
        //获取文件后缀
        String suffix = excelFile.getOriginalFilename().substring(excelFile.getOriginalFilename().lastIndexOf(".")+1, excelFile.getOriginalFilename().length());
        if(!"xls,xlsx".contains(suffix)){
            map.put("type", "error");
            map.put("msg", "文件类型不正确,请上传xls,xlsx格式的文件!");
            return map;
        }
        try {
             msg = readExcel(excelFile.getInputStream(),subjectId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        map.put("type", "success");
        map.put("msg", msg);
        return map;
    }

    /**
     * 读取excel文件内容，并插入到数据库
     * @param fileInputStream
     * @return
     */
    private String readExcel(InputStream fileInputStream, Integer subjectId){
        String msg = "";
        try {
            //读取Excel文件
            HSSFWorkbook hssfWorkbook = new HSSFWorkbook(fileInputStream);
            //获取第一个sheet
            HSSFSheet sheetAt = hssfWorkbook.getSheetAt(0);
            //获取最后一行的编号
            if(sheetAt.getLastRowNum() <= 0){
                msg = "该文件为空";
            }
            //遍历每一行
            for(int rowIndex = 1;rowIndex <= sheetAt.getLastRowNum(); rowIndex++){
                Question question = new Question();
                HSSFRow row = sheetAt.getRow(rowIndex);
                //根据列号获取列
                if(row.getCell(0) == null){
                    msg += "第" + rowIndex + "行，试题类型为空，跳过<br/>";
                    continue;
                }
                Double numericCellValue = row.getCell(0).getNumericCellValue();
                question.setQuestionType(numericCellValue.intValue());
                if(row.getCell(1) == null){
                    msg += "第" + rowIndex + "行，题目为空，跳过<br/>";
                    continue;
                }
                question.setTitle(row.getCell(1).getStringCellValue());
                if(row.getCell(2) == null){
                    msg += "第" + rowIndex + "行，分值为空，跳过<br/>";
                    continue;
                }
                numericCellValue = row.getCell(2).getNumericCellValue();
                question.setScore(numericCellValue.intValue());
                if(row.getCell(3) == null){
                    msg += "第" + rowIndex + "行，选项A为空，跳过<br/>";
                    continue;
                }
                question.setAttrA(row.getCell(3).getStringCellValue());
                if(row.getCell(4) == null){
                    msg += "第" + rowIndex + "行，选项B为空，跳过<br/>";
                    continue;
                }
                question.setAttrB(row.getCell(4).getStringCellValue());
                question.setAttrC(row.getCell(5) == null ? "" : row.getCell(5).getStringCellValue());
                question.setAttrD(row.getCell(6) == null ? "" : row.getCell(6).getStringCellValue());
                if(row.getCell(7) == null){
                    msg += "第" + rowIndex + "行，正确答案为空，跳过\n";
                    continue;
                }
                question.setAnswer(row.getCell(7).getStringCellValue());
                question.setCreateTime(new Date());
                question.setSubjectId(subjectId);
                if(questionService.add(question) <= 0){
                    msg += "第" + rowIndex + "行，插入数据库失败\n";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return msg;

    }

}
