import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.*;

public class Compute {
	
	ArrayList<Dimensions> table = new ArrayList<Dimensions>();
	ArrayList<HashMap<String, Integer>> countTable = new ArrayList<HashMap<String, Integer>>();
	HashMap<String, Integer> consolidatedTable = new HashMap<String, Integer>();
	int root_count=0;
	
	ArrayList<Dimensions> copyTable = new ArrayList<Dimensions>();
	
	HashMap<String,Integer> reducedConsolidatedMap = new HashMap<String, Integer>(); 
	HashMap<Dimensions,Integer> reducedMap = new HashMap<Dimensions, Integer>(); 
	
	int dim_no;
	

	public Read_file()
	{
		String fileName="/Users/KarthikSwaminathan/Documents/workspace/Star_cubing/src/input.txt";
		String line = null;
		try {
            FileReader fileReader = new FileReader(fileName);
            
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            
            while((line = bufferedReader.readLine()) != null) {
               
               String[] tokens = line.split(",");
               
               Dimensions temp = new Dimensions(tokens.length);
               temp.setDimensions(tokens);
               table.add(temp);
               
               
               
            }   

            bufferedReader.close();         
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                "Unable to open file '" + 
                fileName + "'");                
        }
        catch(IOException ex) {
            System.out.println(
                "Error reading file '" 
                + fileName + "'");                  
        }
	}
	
	
	public void computeCount()
	{
		dim_no=table.get(0).dim_no;
		for(int i = 0;i<dim_no;i++)
		{
			countTable.add(new HashMap<String, Integer>());
		}
		for(int i=0; i<table.size();i++)
		{
			Dimensions temp = table.get(i);
			for(int j=0;j<temp.dim.length;j++)
			{
				if(countTable.get(j).containsKey(temp.dim[j]))
				{
					Integer value = countTable.get(j).get(temp.dim[j])+1;
					countTable.get(j).put(temp.dim[j],value);
					consolidatedTable.put(temp.dim[j],value);
				}
				
				else{ 
					countTable.get(j).put(temp.dim[j],1);
					consolidatedTable.put(temp.dim[j],1);
				
				}
			}
		}
	}
	
	public void printCountTable()
	{
		for(int i = 0; i<countTable.size();i++)
		{
			HashMap<String, Integer> temp = countTable.get(i);
			System.out.println("Dimension count:   " +i);
			for(String s : temp.keySet())
			System.out.println(s +"   " + temp.get(s));
			System.out.println("");	
		}
		
	}
	
	public void firstPass(int iceberg)
	{	copyTable=table;
		for(Dimensions i: table)
		{
			String[] temp = i.dim;
			for(int j=0;j<temp.length;j++)
			{
				if(consolidatedTable.get(temp[j])<iceberg)
				{
					temp[j]="*";
				}
			}
			i.dim=temp;
			
		}
		
		
		
		
	}
	
	public void reduction()
	{  int value;
		
		for(int i = 0;i<table.size();i++)
		{
		table.get(i).buildConcat();
		Dimensions temp = table.get(i);
		if(!reducedConsolidatedMap.containsKey(table.get(i).concated))
		{
			reducedConsolidatedMap.put(table.get(i).concated,i);
			
			reducedMap.put(temp, 1);
			
		}
		
		else if(reducedConsolidatedMap.containsKey(table.get(i).concated))
		{
			int index = reducedConsolidatedMap.get(table.get(i).concated);
					
				value=	reducedMap.get(table.get(index));
				
			reducedMap.put(table.get(index),value+1);
		}
		
		}
		
	}
	
	public void printReducedMap()
	{
		for(Dimensions d: reducedMap.keySet())
		{   
			
			System.out.print("Count: "+ reducedMap.get(d)+"     ");
			d.print_dim();
			
		}
	}
	
	public void computeRootCount()
	{
		for(Dimensions d: reducedMap.keySet())
		{   
			
		root_count= root_count + reducedMap.get(d);
		
		
			
		}
	}
	
	public void starCubing()
	{
		computeRootCount();
		System.out.println(root_count);
		
		for(Dimensions d: reducedMap.keySet())
		{   
			
			
			createStarTree(d.dim, reducedMap.get(d));
			getRow();
		}
		
		dfs(root);
		
		
	}

		
	}
		
		public StarTree checkIfChild(StarTree root, String s)
		{
			
			ArrayList<StarTree> child = root.children;
			StarTree temp;
			
			if(!child.isEmpty()){
				for(int i=0; i<child.size(); i++){
					temp = child.get(i);
					if(temp.attribute.equals(s))
						return temp;
				}
			}		
			return null;
		}
		
		public void createStarTree(String[] row, int iCount)
		{
			StarTree currentNode = root;
			
			for(int i=0; i<row.size(); i++){
				StarTree status = checkIfChild(currentNode, row.get(i));
				if(status == null){
					StarTree newNode = new StarTree(row.get(i),iCount);
					if(i == row.size()-1){
						newNode.isLeaf = true;
					}
					currentNode.children.add(newNode);
					if(currentNode.children.size() > 1){
						currentNode.hasSibling = true;
					}
					currentNode = newNode;
					System.out.println("Child Added");
					}
				else{
					currentNode = status;
					currentNode.count = currentNode.count+1;
					System.out.println("Root Already Exists");
				}
			}
		}
		
		public void getRow()
		{
			for(Map.Entry<ArrayList<String>, Integer> i : reducedTable.entrySet()){
				createStarTree(i.getKey(),i.getValue());
			}
		}	
		
		
		
		public void dfs(StarTree root)
		{
			if(root.children.size()<=0){
				return  ;
			}
			
			
			for(int i=0;i<root.children.size();i++){
			    	
			System.out.println("elements of"+root.attribute);	
			 System.out.println(root.children.get(i).attribute);  
			  dfs(root.children.get(i));
			  	
			}	
			
		
				 if(root.hasSibling>0){
					 dfs(root.sibling)
					  
				  }
			     if(root.isLeaf>0){
			    	dfs(root.child)
			     }
		}
			
		return;
		
		 	
		
			
		
		
		
		
		
		
		
		
		
		
		
	}
}
	
	


	
	
	
	
	
	
	
	
	

