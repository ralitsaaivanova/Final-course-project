package org.example.tltravel.config;

import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalModelAttributeAdvice {

    @ModelAttribute
    public void addSessionAttributesToModel(HttpSession session, Model model) {
        Object agentId = session.getAttribute("agentId");
        if (agentId != null) {
            model.addAttribute("agentId", agentId);
        }
    }
}
