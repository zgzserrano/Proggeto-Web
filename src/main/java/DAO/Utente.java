package dao;

public class Utente {
    private String account;
    private String password;
    private boolean admin;


    public Utente(String account, String password, boolean admin) {
        this.account = account;
        this.password = password;
        this.admin = admin;
    }


    public String getAccount() {
        return account;
    }

    public String getPassword() {
        return password;
    }

    public boolean getAdmin() {
        return admin;
    }

    @Override
    public String toString() {
        return account + " " + password;
    }
}
