package Model;

import java.io.Serializable;

public class LogIn implements Serializable{
private String email;
private String password;

public LogIn(String email,String password)
{
	this.email=email;
	this.password=password;
}

public String getEmail() {
	return email;
}

public String getPassword() {
	return password;
}


}
