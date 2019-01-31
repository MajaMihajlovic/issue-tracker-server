package com.issuetracker.issuetracker.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("api/report")
@Scope("request")
public interface ReportController  {


}
