package ru;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.Serializable;
import java.util.List;

@Controller

public class Cont implements Serializable{

    @Autowired
    Serv serv;

    @GetMapping("/")
    @ResponseBody
    public String main_() {
        return "application";
    }

    @GetMapping("/x")
    @ResponseBody
    public TA main_2() {
        List<TA> x = serv.getList();
        TA t = x.get(0);
        for(TA t1 : x){
            if (t.b_uk == null || (t1.b_uk != null && t1.b_uk.length < t.b_uk.length)) {
                t = t1;
            }
        }
        return t;
    }
}
