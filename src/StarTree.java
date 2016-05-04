import java.util.ArrayList;

public class StarTree {
	
	String root;
	int root_count;
	
	public StarTree(String root)
	{
		this.root=root;
	}

ArrayList<StarTree> child = new ArrayList<StarTree>();

public void set(StarTree childRoot)
{
child.add(childRoot);
}

List<StarTree> children = new ArrayList<>();
boolean isLeaf=false;
boolean hasSibling=false;
StarTree(){}
StarTree(int c){
	this.count =c;
}
StarTree(String attr,int c){
	this.attribute=attr;
	this.count = c;
}

public boolean search (StarTree childRoot)
{
	for(int i =0 ;i<child.size();i++)
	{
		if(child.get(i).root.equals(childRoot.root))
			return true;
	}
	
	return false;
}


}