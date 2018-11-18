package ttt.Controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ttt.domain.JSOdata;

import java.io.IOException;
import java.util.Properties;

@Controller
//@RequestMapping("/start")
public class c1 {

    @Autowired
    private MetGet mg;
    @Autowired
    private Generate_data gd;

    @Autowired
    @Qualifier("settings")
    private Properties Settings;

    @RequestMapping(value = "/idat", method = RequestMethod.GET)
    public ModelAndView strt() throws IOException {
        mg.GetFromURL();
        JSOdata MetaInfo = mg.getMeta();
        return new ModelAndView("idat", "command", MetaInfo);
    }
    @RequestMapping(value="/idat" ,method = RequestMethod.POST)
    public ModelAndView addData(@ModelAttribute("SpringWeb") JSOdata MetaInfo, ModelMap model) throws IOException {

        String s[]=MetaInfo.getResult().split(",");

        JSOdata dat = mg.getMeta();

        int sz = dat.fields.size();
        int i=0;
        while (i<sz)
        {
            dat.fields.get(i).result=s[i];
            i++;

        }
        dat.fields.get(i-1).result=s[i];
        dat.setResume(gd.GenerD(dat));
        return new ModelAndView("idat", "command", dat);
    }

//    @RequestMapping(value="/start",method = RequestMethod.GET)
//    public String printHello(ModelMap model) throws IOException {
//
//        try {
//            mg.GetFromURL();
//            JSOdata MetaInfo = mg.getMeta();
//            String result = gd.GenerD(MetaInfo);
//            model.addAttribute("message", result);
////        model.addAttribute("message", "Успешний старт!");
//        } catch (Throwable t) {
//            System.out.print(t);
////            logger.error("Ошибка получения мета данных. " + t.toString());
//            model.addAttribute("message", t.toString());
//
//        }
//        return "start";
//
//    }
}