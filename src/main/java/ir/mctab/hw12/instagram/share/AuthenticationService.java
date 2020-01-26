package ir.mctab.hw12.instagram.share;

import ir.mctab.hw12.instagram.entities.User;

public class AuthenticationService  {

    private User loginUser;

    private static AuthenticationService authenticationService = null;

    public static AuthenticationService getInstance() {
        if (authenticationService == null)
            return authenticationService = new AuthenticationService();
        else
            return authenticationService;
    }

    private AuthenticationService() { }

    public void setLoginUser(User user) {
        this.loginUser = user;
    }

    public User getLoginUser() {
        return loginUser;
    }


}
