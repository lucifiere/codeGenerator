package liao.code.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by ao on 2017/10/18.
 */
@RequestMapping("page")
@Controller
public class PageController {
    @RequestMapping("start")
    public String start(){
        return "index";
    }
}
