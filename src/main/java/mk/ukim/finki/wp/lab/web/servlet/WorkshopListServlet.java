package mk.ukim.finki.wp.lab.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mk.ukim.finki.wp.lab.service.WorkshopService;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;

@WebServlet(name = "WorkshopListServlet", urlPatterns = "")
public class WorkshopListServlet extends HttpServlet {
    private final SpringTemplateEngine springTemplateEngine;
    private final WorkshopService workshopService;

    public WorkshopListServlet(SpringTemplateEngine springTemplateEngine, WorkshopService workshopService) {
        this.springTemplateEngine = springTemplateEngine;
        this.workshopService = workshopService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        IWebExchange webExchange = JakartaServletWebApplication
                .buildApplication(getServletContext())
                .buildExchange(req, resp);

        WebContext context = new WebContext(webExchange);

        String text = req.getParameter("text");
        String maxPrice = req.getParameter("maxPrice");

        // Empty parameters are treated as null
        if (text != null && text.isEmpty()) text = null;
        if (maxPrice != null && maxPrice.isEmpty()) maxPrice = null;

        if (text != null || maxPrice != null) {
            Double price = maxPrice != null ? Double.parseDouble(maxPrice) : null;
            context.setVariable("workshops", this.workshopService.search(text, price));
        } else {
            context.setVariable("workshops", this.workshopService.listAll());
        }

        context.setVariable("errorMessage", req.getParameter("errorMessage"));

        springTemplateEngine.process("listWorkshops.html", context, resp.getWriter());
    }
}