
package hospital.models;


public class User {
    private int id;
    private String userName;
    private String passwordHash;
    private String role;
    private String staff;
    private boolean active;
    
    public User(){
        
    }

    public User(int id, String userName, String passwordHash, String role, String staff, boolean active) {
        this.id = id;
        this.userName = userName;
        this.passwordHash = passwordHash;
        this.role = role;
        this.staff = staff;
        this.active = active;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getStaff() {
        return staff;
    }

    public void setStaff(String staff) {
        this.staff = staff;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

 
    
    
}

