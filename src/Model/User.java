package Model;

import java.io.Serializable;

public class User implements Serializable{
private String name,email,password;
private int funds,uid;
public User(String name, String email, String password){
	this.name=name;
	this.email=email;
	this.password=password;
	funds=0;
}
public User(String name, String email, int funds){
	this.name=name;
	this.email=email;
	this.funds=funds;
	
}

public User(int uid, String name, String email, int funds){
	this.setUid(uid);
	this.name = name;
	this.email = email;
	this.funds= funds;
}

public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
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
public int getFunds() {
	return funds;
}
public void setFunds(int funds) {
	this.funds = funds;
}

@Override
public String toString()
{
	return "Name: "+name+",passowrd: "+password;
}
public int getUid() {
	return uid;
}
public void setUid(int uid) {
	this.uid = uid;
}

}
