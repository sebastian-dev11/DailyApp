package com.dailyapp.loginapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @GetMapping("/dashboard.html")
    public String mostrarDashboard() {
        return "dashboard.html"; // Esto carga el archivo desde /src/main/resources/static/
    }
}
