package assignment1;
import java.util.*;
import java.util.ArrayList;

public class LZ77 {
    
    ArrayList<Tag> Tags = new ArrayList<Tag>();
    
    public void Compression()
    {
        Scanner input = new Scanner(System.in);
        
        String data;
        System.out.println("Enter your data: ");
        data = input.nextLine();

        

        int positionIndex = 0;
        String temp = "";
        String exists = "";
        String newSymbol = "";
        int count = 0;
        
        for (int i = 0; i < data.length(); i++)
        {
            temp += data.charAt(i);
            //newSymbol += data.charAt(i+1);
            
            if (i == 0)
            {
                
                Tag t = new Tag(0, 0, temp);
                Tags.add(t);
                exists += temp;
                count++;
                temp = "";
            }
            
            else if (temp.length() == 1 && !exists.contains(temp))
            {
                
                Tag t = new Tag(0, 0, temp);
                Tags.add(t);
                exists += temp;
                count++;
                temp = "";
            }
            
            else
            {
                if (!exists.contains(temp))
                {
                    
                    String temp2 = temp.substring(0,temp.length()-1);
                    positionIndex = exists.lastIndexOf(temp2);
                    newSymbol = temp.substring(temp.length()-1);
                    Tag t = new Tag(count - positionIndex, temp.length()-1, newSymbol);
                    Tags.add(t);
                    exists += temp;
                    count += temp.length();
                    temp = "";
                }
            } 
            newSymbol = "";
               
        }
        
        if(!"".equals(temp))
        {
            positionIndex = exists.lastIndexOf(temp);
            newSymbol = "NULL";
            Tag t = new Tag(count - positionIndex, temp.length(), newSymbol);
            Tags.add(t);
        }
        
        for (int i = 0; i < Tags.size(); i++)
        {
            System.out.println("<"+Tags.get(i).Position+","+Tags.get(i).Length+","+Tags.get(i).Symbol+">");
        }
       
        int max = Tags.get(0).Position; 
        for (int i =0; i < Tags.size(); i++)
        {
           if (Tags.get(i).Position > max)
           {
               max = Tags.get(i).Position;
           }
        }
        
        int positionBit = log2(max)+1;
        
        int max2 = Tags.get(0).Length; 
        for (int i =0; i < Tags.size(); i++)
        {
           if (Tags.get(i).Length > max2)
           {
               max2 = Tags.get(i).Length;
           }
        }
        
        int lengthBit = log2(max2)+1;
        int symbolBit = 8;
        
        int compressedSize = (positionBit + lengthBit + symbolBit) * Tags.size();
        int originalSize = 8 * data.length();
        
        System.out.println("Original Size: " + originalSize);
        System.out.println("Compressed Size: " + compressedSize);
        
    }
    
    public static int log2(int x) 
    {
        return (int) (Math.log(x) / Math.log(2));
    }
 
    public void Decompression()
    {
        String decompressed = "";
        String temp = "";
        int j=0;
        int k;
        
        for (int i = 0; i < Tags.size(); i++)
        {
            if (Tags.get(i).Position == 0)
            {
                j++;
                decompressed += Tags.get(i).Symbol;
            }
            else
            {
                k = j - Tags.get(i).Position;
                temp = decompressed.substring(k, k+Tags.get(i).Length);
                j += Tags.get(i).Length + 1;
                if ("NULL".equals(Tags.get(i).Symbol))
                {
                    Tags.get(i).Symbol = "";
                }
                decompressed += temp + Tags.get(i).Symbol;
            }
            
        }
       
        System.out.println("Decomprission: "+decompressed);
    }
    
}
