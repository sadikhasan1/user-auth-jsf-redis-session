package com.dsi.authredis.view;

import com.dsi.authredis.entity.User;
import com.dsi.authredis.service.UserService;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.Serial;
import java.io.Serializable;
import java.util.Optional;

@Named
@SessionScoped
public class LoginBean implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String username;
    private String password;
    private boolean loggedIn;

    @Inject
    private UserService userService;

    @Inject
    private HttpServletRequest request;

    public String login() {
        Optional<User> userOptional = userService.getUserByUsername(username);

        if (userOptional.isPresent() && userOptional.get().getPassword().equals(password)) {
            loggedIn = true;
            HttpSession session = request.getSession(true);
            session.setAttribute("username", username);
            return "home.xhtml?faces-redirect=true";
        } else {
            loggedIn = false;
            return "login.xhtml";
        }
    }

    public String logout() {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        loggedIn = false;
        return "login.xhtml?faces-redirect=true";
    }

    public String checkLogin() {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            return "login.xhtml?faces-redirect=true";
        }
        return null;
    }

    // Getters and setters for username and password

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }
}