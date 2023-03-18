//package pacjava;

import java.io.*;
import java.util.*;
import javax.sound.sampled.SourceDataLine;
import java.nio.file.*;

class Book implements Serializable
{
	private int isbn;
	private String title,author;
	private double price;

	public Book()
	{
		isbn=0;
		title=null;
		author=null;
		price=0;
	}

	public Book(int i,String t, String a, double p)
	{
		isbn=i;
		title=t;
		author=a;
		price=p;
	}
	public String display()
	{
		return "\nTitle: "+title+"\nAuthor: "+author+"\nISBN: "+isbn+"\nPrice: "+price+"\n";
	}
}

class Library implements Serializable
{
	private List<Book> collection;
	public Library()
	{
		collection=new ArrayList<Book>();
	}
	public void addBook(Book b)
	{
		collection.add(b);
	}
	public String display()
	{
		String total="\n";
		for(int i=0;i<collection.size();i++){
			Book b=collection.get(i);
			total=total+b.display();
		}
		return total;
	}
}

class Main
{
	static String fileName=null;
	static Library lib=new Library();
	static Scanner s=new Scanner(System.in);
	static Boolean r=true;
	
	public static void main(String []args)
	{
		Path path=Path.of("index.txt");
		while(r)
		{
			System.out.println("\nEnter 0 to load a library"+"\nEnter 1 to save or quit"+"\nEnter 2 to list all books in library"+"\nEnter 3 to add book in library");
			int answer=s.nextInt();
			if(answer==0)
			{
				
				
				try
				{
					String string=Files.readString(path);
					System.out.println(string);

					System.out.println("Enter the file name to load : ");
					fileName=s.next();
					FileInputStream fis=null;
					ObjectInputStream in=null;
					File file=new File(fileName+".ser");

					fis=new FileInputStream(file);
					in=new ObjectInputStream(fis);
					lib=(Library)in.readObject();
					fis.close();
					in.close();
					
					System.out.println("File opened successfully....!!");
				}
				catch(Exception e)
				{
					e.getMessage();
				}
				
			}
			
			else if(answer==1)
			{
				System.out.println("Enter a file name: ");
				fileName=s.next()+".ser";
				r=false;
				FileOutputStream fos=null;
				ObjectOutputStream out=null;
				try
				{
					fos=new FileOutputStream(fileName);
					out=new ObjectOutputStream(fos);
					out.writeObject(lib);
					fos.close();
					out.close();
					int i=0;
					
					File f1=new File("index.txt");
					Scanner sf=new Scanner(f1);
					int count=0;
					while(sf.hasNextLine()){
						String cf=fileName;
						String sff=sf.nextLine();
						if(cf.equals(sff)) {count++;}
					}
					if(count!=0){
						System.out.println("Existing file has been updated...!!");
					}
					else{
					Files.writeString(path,fileName+"\n",StandardOpenOption.APPEND);
					System.out.println("New file saved successfully...!!");
					}
				    
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				
			}
			else if(answer==2)
			{  
				System.out.println(lib.display());
			}
			else if(answer==3)
			{
				int i;
				String t,a;
				double p;
				
				System.out.println("\nEnter Title: ");
				t=s.next();
				System.out.println("\nEnter Author: ");
				a=s.next();
				System.out.println("\nEnter ISBN no.: ");
				i=s.nextInt();
				System.out.println("\nEnter Price: ");
				p=s.nextDouble();
				
				Book b=new Book(i,t,a,p);
				lib.addBook(b);
				
			}
		}
		System.exit(0);
	}
}