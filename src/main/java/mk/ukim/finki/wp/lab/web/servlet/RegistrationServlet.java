package mk.ukim.finki.wp.lab.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mk.ukim.finki.wp.lab.model.Registration;
import mk.ukim.finki.wp.lab.service.RegistrationService;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@WebServlet(name = "WorkshopServlet", urlPatterns = "/registration")

public class RegistrationServlet extends HttpServlet {
    private final SpringTemplateEngine springTemplateEngine;
    private final RegistrationService registrationService;

    public RegistrationServlet(SpringTemplateEngine springTemplateEngine, RegistrationService registrationService) {
        this.springTemplateEngine = springTemplateEngine;
        this.registrationService = registrationService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String workshopTitle = req.getParameter("workshopTitle");
        String participantName = req.getParameter("participantName");
        String participantEmail = req.getParameter("participantEmail");
        String number = req.getParameter("numSeats");

        Registration registration;
        try {
            int numberOfSeats = Integer.parseInt(number);
            registration = this.registrationService.register(workshopTitle, participantName, participantEmail, numberOfSeats);
        }
        catch (IllegalArgumentException e){
            resp.sendRedirect("/?errorMessage=" + URLEncoder.encode(e.getMessage(), StandardCharsets.UTF_8 ));
            return;
        }
        IWebExchange iWebExchange = JakartaServletWebApplication.buildApplication(req.getServletContext()).buildExchange(req, resp);

        WebContext context = new WebContext(iWebExchange);

        context.setVariable("registration", registration);
        context.setVariable("ipAddress", req.getRemoteAddr());
        context.setVariable("userAgent", req.getHeader("user-agent"));

        springTemplateEngine.process("registrationConfirmation.html", context, resp.getWriter());



    }
}
