package com.aldo.aldope.controladores;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ErroresControlador implements ErrorController {
    @RequestMapping(value = "error", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView renderErrorPage(HttpServletRequest httpServletRequest) {

        ModelAndView errorPage = new ModelAndView("error");

        String errorMsg = "";

        int httpErrorCode = getErrorCode(httpServletRequest);
        switch (httpErrorCode) {
            case 400:
                errorMsg = "El recurso solicitado no existe";
                break;
            case 403:
                errorMsg = "NO tiene permisos para acceder";
                break;
            case 401:
                errorMsg = "No se encuentra autorizado";
                break;
            case 404:
                errorMsg = "El recurso solciitado no fue encontrado";
                break;
            case 500:
                errorMsg = "Ocurrio un error interno";
                break;
            case 0:
                errorMsg = "asdasfdafa";
                break;
        }

        errorPage.addObject("codigo", httpErrorCode);
        errorPage.addObject("mensaje", errorMsg);
        return errorPage;
    }

    private int getErrorCode(HttpServletRequest httpServletRequest) {
        Integer errorCode = (Integer) httpServletRequest.getAttribute("jakarta.servlet.error.status_code");
        return errorCode != null ? errorCode.intValue() : 0;

    }

    public String getErrorPath() {
        return "/error.html";
    }
}
