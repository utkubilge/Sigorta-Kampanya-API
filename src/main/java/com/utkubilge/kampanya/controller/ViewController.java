package com.utkubilge.kampanya.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;



@Controller
public class ViewController {
    private final Logger logger = LoggerFactory.getLogger(KampanyaController.class);

	@GetMapping("/")
	public ModelAndView index() {
		logger.debug("request to GET index");
		ModelAndView modelAndView = new ModelAndView("index");
		//modelAndView.addObject("kampanyalarItems", kampanyaRepo.findAll());
		return modelAndView;
	}

    @GetMapping("/add-kampanya")
    public String showCreateForm(){
        return "add-kampanya";
    }
}
