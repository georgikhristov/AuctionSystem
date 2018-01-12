package Model;

import java.io.Serializable;
import java.util.ArrayList;

public class AuctionList implements Serializable {
	private ArrayList<Auction> list;
public AuctionList(){
	list = new ArrayList<Auction>();
}

public Auction getElem(int index){
	return list.get(index);
}

public Auction getElemByID(int id){
	Auction tmp =null;
	for(int i=0;i<list.size();i++){
		if (list.get(i).getId()==id){
			tmp= list.get(i);
		} 
		
	}
return tmp;
}
public void addElem(Auction obj){
	list.add(obj);
}

public int getSize(){
	return list.size();
}

public void clearList(){
	list.clear();
}
}
