/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.LoginDAO;
import java.io.IOException;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import model.LoginBean;

/**
 *
 * @author skdonep
 */
@ManagedBean(name = "loginController")
@SessionScoped
public class LoginController implements Serializable {
    private LoginBean loginBean;
    private String errorMessage;
    /**
     * Creates a new instance of LoginController
     */
    public LoginController() {
        loginBean = new LoginBean();
    }

    public LoginBean getLoginBean() {
        return loginBean;
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    
    public void validateCredentials() throws IOException{
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        LoginDAO loginDB= new LoginDAO();
        String username = this.loginBean.getUsername();
        String password = this.loginBean.getPassword();
        if(loginDB.validCredentialsInDB(username,password)){
            externalContext.redirect("Home.xhtml");
        }
        else{
            this.errorMessage="Invalid Username/Password";
            externalContext.redirect("LoginFailed.xhtml");
        }
    }
}
