
package com.github.mymvcspring.config.servlet;

import com.github.mymvcspring.web.viewcontroller.AbstractController;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TempServlet extends AbstractController {
//강사님 버전 서블릿

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("들어와써 헤헤헤");


    }
}
