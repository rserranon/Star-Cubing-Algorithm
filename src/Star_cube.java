import java.util.*;
import java.io.*;
public class Star_cube {
	
	
	
	public static void print_table(ArrayList<Dimensions> table)
	{
		for(int i = 0;i<table.size();i++)
		{
			Dimensions temp = table.get(i);
			temp.print_dim();
		}
	}
	
	
	
	
	
	public static void main(String[] args)
	{
		Compute load = new Compute();
	
		//print_table(load.table);
		load.computeCount();
		//load.printCountTable();
		System.out.println("Enter iceberg value");
		
		Scanner sc = new Scanner(System.in);
		
		load.firstPass(sc.nextInt());
		
		//print_table(load.table);
		load.reduction();
		load.printReducedMap();
		load.starTree();
		
	}

}
