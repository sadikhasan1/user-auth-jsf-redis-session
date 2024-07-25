package com.dsi.authredis.view;

import com.dsi.authredis.entity.User;
import com.dsi.authredis.service.UserService;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.Optional;

@Named
@SessionScoped
public class LoginBean implements Serializable {

    private String email;
    private String password;
    private boolean loggedIn;

    @Inject
    private UserService userService; // Ensure you have a UserService for database operations

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String login() {
        FacesContext context = FacesContext.getCurrentInstance();
        Optional<User> userOptional = userService.getUserByEmail(email);

        if (userOptional.isPresent() && userOptional.get().getPassword().equals(password)) { // Hash passwords in production
            loggedIn = true;
            context.getExternalContext().getSessionMap().put("user", userOptional.get());
            return "home.xhtml?faces-redirect=true"; // Redirect to home page
        } else {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid credentials", null));
            return null;
        }
    }

    public String logout() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().invalidateSession(); // Invalidate session on logout
        loggedIn = false;
        return "login.xhtml?faces-redirect=true"; // Redirect to login page
    }
}
