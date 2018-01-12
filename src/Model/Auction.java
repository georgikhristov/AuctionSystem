package Model;

import java.io.Serializable;
import java.sql.Timestamp;

public class Auction implements Serializable{
	private int id,minam,creatorid,timeset,highest;
	private String title, description, category, creatorEmail;
	private Timestamp timelast;
	public Auction(int id, int minam, Timestamp timelast, int creatorid, String title, String description,
			String category){
		this.id=id;
		this.minam=minam;
		this.timelast=timelast;
		this.creatorid=creatorid;
		this.title=title;
		this.description=description;
		this.category=category;
		highest =0;
	}
	public Auction( int minam, int timeset, int creatorid, String title, String description,
			String category){
		
		this.minam=minam;
		this.timeset=timeset;
		this.creatorid=creatorid;
		this.title=title;
		this.description=description;
		this.category=category;
		highest =0;
	}
	

	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getMinam() {
		return minam;
	}
	public void setMinam(int minam) {
		this.minam = minam;
	}
	public Timestamp getTimelast() {
		return timelast;
	}
	public void setTimelast(Timestamp timelast) {
		this.timelast = timelast;
	}
	public int getCreatorid() {
		return creatorid;
	}
	public void setCreatorid(int creatorid) {
		this.creatorid = creatorid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	@Override
	public String toString(){
		return "id:"+id + " creatid" + creatorid+ " tile:" + title+ " Description:" + description;
	}
	public int getTimeset() {
		return timeset;
	}
	public void setTimeset(int timeset) {
		this.timeset = timeset;
	}
	
	public String getTimeLeft(Timestamp ts){
		long end = ts.getTime()-System.currentTimeMillis();
		String time ;
		int seconds = (int) (end/ 1000) % 60 ;
		int minutes = (int) ((end/ (1000*60)) % 60);
		int hours   = (int) ((end/ (1000*60*60)));
		if(end>0){
		time = hours + "h "+ minutes + "m " + seconds + "s ";} else{ time="Expired";}
		return time;
	}
	
	public String getCreatorEmail() {
		return creatorEmail;
	}
	
	
	
	public void setCreatorEmail(String cmail){
		creatorEmail = cmail;
	}
	public int getHighest() {
		return highest;
	}
	public void setHighest(int highest) {
		this.highest = highest;
	}
}
