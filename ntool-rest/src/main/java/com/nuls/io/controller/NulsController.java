package com.nuls.io.controller;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


/**
 * Created by Administrator on 2017/8/16.
 */
@Controller
@RequestMapping(value = "/nuls")
public class NulsController {


    @ResponseBody
    @PostMapping("/add")
    public int addUser(){
    	
        return 1;
    }

}