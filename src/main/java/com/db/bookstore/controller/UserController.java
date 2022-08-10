package com.db.bookstore.controller;

import com.db.bookstore.model.User;

import com.db.bookstore.service.BookService;
import com.db.bookstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    BookService bookService;

    @GetMapping("/register")
    public ModelAndView getRegisterForm() {
        return new ModelAndView("register-form");
    }

    @PostMapping("/register")
    public ModelAndView addUser(User user) {
        user.setRole("client");
        userService.insertUser(user);
        return new ModelAndView("redirect:/login");
    }

    @GetMapping("/login")
    public ModelAndView getLoginForm() {
        return new ModelAndView("login-form");
    }

    @PostMapping("/login")
    public ModelAndView verifyUser(User user, HttpServletResponse response) {
        try {
            User user1 = userService.findByUsernameOrEmailAndPassword(user);
            response.addCookie(new Cookie("id", "" + user1.getId()));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new ModelAndView("redirect:/dashboard");
    }

    @GetMapping("/dashboard")
    public ModelAndView getDashBoard(@CookieValue int id) {
        ModelAndView modelAndView = new ModelAndView("dashboard");
        try {
            modelAndView.addObject("user", userService.findById(id));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            modelAndView.addObject("bookList", bookService.findAll());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return modelAndView;
    }

    @GetMapping("/add-book")
    public ModelAndView getBookInformation(@CookieValue int id) {
        try {
            User user = userService.findById(id);
            if (user.getRole().equals("admin")) {
                ModelAndView modelAndView = new ModelAndView("add-book");
                return modelAndView;
            }
            ModelAndView modelAndView = new ModelAndView("error-message");
            modelAndView.addObject("errorMessage","Only the admin can add books!");
            return modelAndView;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
