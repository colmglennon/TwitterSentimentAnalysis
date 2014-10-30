package twitterSentiment;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TestData {
	
	public static void main(String[] args){
		BufferedReader br = null;
		int positive=0;
		int negative=0;
		int wrong =0;
		int total = 0;
		
		try{
			String sCurrentLine;
			br = new BufferedReader(new FileReader("C:/Users/Colm/Desktop/testdata.manual.2009.06.14.csv"));
			String cvsSplitBy=",";
			
			
			
			while((sCurrentLine=br.readLine()) !=null){
				String[] words = sCurrentLine.split(cvsSplitBy);
				
				int sentiment = Integer.parseInt(words[0].substring(1,2));
				NaiveBayes nb = new NaiveBayes(words[5]);
				
				if(nb.start()==0&&sentiment==4){
					positive++;
				}
				else if(nb.start()==1&&sentiment==0){
					positive++;
				}
				else negative++;
				
				
				
				
				
				
				System.out.println("Positive: "+positive+", Negative: "+negative+", Total: " + ++total);
			}
			
		}catch(IOException e){
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		
	}

}
