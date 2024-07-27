package com.dsi.authredis.view;

import com.dsi.authredis.entity.User;
import com.dsi.authredis.redis.SessionManager;
import com.dsi.authredis.service.UserService;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
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
            String sessionId = FacesContext.getCurrentInstance().getExternalContext().getSessionId(true);
            SessionManager.storeSession(sessionId, username);
            loggedIn = true;
            return "home.xhtml";
        } else {
            loggedIn = false;
            return "login.xhtml";
        }
    }

    public String logout() {
        String sessionId = FacesContext.getCurrentInstance().getExternalContext().getSessionId(false);
        SessionManager.deleteSession(sessionId);
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        loggedIn = false;
        return "login.xhtml";
    }

    public String checkLogin() {
        String sessionId = FacesContext.getCurrentInstance().getExternalContext().getSessionId(false);
        String username = SessionManager.getSession(sessionId);
        return username != null ? "home.xhtml" : "login.xhtml";
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