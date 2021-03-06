package club.looli.onlineexam.home.interceptor;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 前台登录拦截器
 */
@Component
public class HomeInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //根据是否有结果判断是否登录
        String name = (String) request.getSession().getAttribute("name");
        //未登录
        if (StringUtils.isEmpty(name)) {
            //ajax请求
            if (StringUtils.equals(request.getHeader("X-Requested-With"), "XMLHttpRequest")) {
                Map<String, String> maps = new HashMap<>();
                maps.put("type", "error");
                maps.put("msg", "登录信息已失效");
                response.getWriter().write(JSONObject.toJSONString(maps));
                return false;
            } else {
                //转发到登录页面
                response.sendRedirect("/home/login");
            }
            return false;
        }
        //登录返回true
        return true;
    }
}
