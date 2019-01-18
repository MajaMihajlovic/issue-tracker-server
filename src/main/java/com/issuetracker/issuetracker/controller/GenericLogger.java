package com.issuetracker.issuetracker.controller;

import com.issuetracker.issuetracker.common.CommonController;
import com.rits.cloning.Cloner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.GenericTypeResolver;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;

//dovrsiti loger kasnije
    public class GenericLogger<T> extends CommonController {

        protected Cloner cloner;
        private Class<T> type;
        private HttpSession httpSession;

        public GenericLogger() {
            cloner = new Cloner();
            //this.type = (Class<T>) GenericTypeResolver.resolveTypeArgument(getClass(), GenericLogger.class);
            this.httpSession = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession(true);
        }


    }

