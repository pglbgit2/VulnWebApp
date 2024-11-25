package insa.ctf.vulnwebapp.Controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.logging.Level;
import java.util.logging.Logger;

@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler(Exception.class)
    public ModelAndView handleError(HttpServletRequest request, Exception e)   {
        Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Request: " + request.getRequestURL() + " raised " + e);
        return new ModelAndView("errors/error-500");
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ModelAndView handleError404(HttpServletRequest request, Exception e)   {
        Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Request: " + request.getRequestURL() + " raised " + e);
        return new ModelAndView("errors/error-404");
    }

    @ExceptionHandler(HttpClientErrorException.Forbidden.class)
    public ModelAndView handleAccessDenied(HttpServletRequest request, Exception e)   {
        Logger.getLogger(getClass().getName()).log(Level.WARNING, "Request: " + request.getRequestURL() + " raised " + e);
        return new ModelAndView("errors/error-403");
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ModelAndView handleResponseStatus(HttpServletRequest request, Exception e)   {
        Logger.getLogger(getClass().getName()).log(Level.WARNING, "Request: " + request.getRequestURL() + " raised " + e);
        return new ModelAndView("errors/error-403");
    }

    @ExceptionHandler(org.springframework.web.servlet.resource.NoResourceFoundException.class)
    public ModelAndView handleHttpError404(HttpServletRequest request, Exception e)   {
        Logger.getLogger(getClass().getName()).log(Level.WARNING, "Request: " + request.getRequestURL() + " raised " + e);
        return new ModelAndView("errors/error-404");
    }
}