package com.stonecobra.utils.parse;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

public class Parse 
{
	private static final String GLOBAL_NEWLINE = "[\\n\\r]";
	private static final String[] REGEX_LIST = {"([A-Z])\\w+"};
	private static final String TARGET_STRING = "a new target String";
	private static final String OUT_FILE = "result.txt";
	static Logger log = Logger.getLogger(Parse.class.getName());
	
	public Parse()
	{
		init();
	}
	public Parse(String[] regex, String target)
	{
		init(regex, target);
	}	
	private void init(){	
		writeToFile(REGEX_LIST, TARGET_STRING);
	}	
	private void init(String[] regex, String target){	
		String ext = target.substring(target.lastIndexOf(".")+1,target.length());
		if(ext.equals("txt") || ext.endsWith("log")){
			BufferedReader read;
			try {
				read = new BufferedReader(new FileReader(target));
				String line = null;
				File out = new File(OUT_FILE);
				FileWriter write = new FileWriter(out, true);
				write.write("\n**************************************** RESULTS ****************************************\n\n");
				int lineCount = 0;
				while((line = read.readLine()) != null){
					lineCount++;
					for(int i = 0; i<regex.length;i++)
					{
						if(isCandidate(regex[i], line))
						{
							Matcher match = find(regex[i], line);
							int counter = 0;
							while(match.find())
							{		
								counter++;
								write.write("Match # " + counter + " found on line " + lineCount + " = " + match.group() + "\n");
							}
						}
					}
				}
				write.write("\n****************************************   END   ****************************************\n");
				write.close();
			} catch (FileNotFoundException e) {
				log.error(e);
			} catch (IOException e) {
				log.error(e);
			}	
		}
		else
		{	
			writeToFile(regex, target);
		}
	}
	public void writeToFile(String[] regex, String target){
		try {
			File out = new File(OUT_FILE);
			FileWriter write = new FileWriter(out, true);
			write.write("\n**************************************** RESULTS ****************************************\n\n");
			for(int i = 0; i<regex.length;i++)
			{
				if(isCandidate(regex[i], target))
				{
					Matcher match = find(regex[i], target);
					int counter = 0;
					while(match.find())
					{		
						counter++;
						write.write("Match # " + counter + " found = " + match.group() + "\n");	
					}
				}
			}
			write.write("\n****************************************   END   ****************************************\n");
			write.close();
		} catch (IOException e) {
			log.error(e);
		}		
	}
	static Matcher find(String regex, String target)
	{
		Pattern pattern = Pattern.compile(regex);
		Matcher match = pattern.matcher(target);
		return match;
	}
	static String removeLines(String data)
	{
		return data.replaceAll(GLOBAL_NEWLINE, " ");
	}
	static String rx_required(String regex)
	{
		return regex + "*";
	}
	static String rx_optional(String regex)
	{
		return regex + "?";
	}	
	static String rx_plus(String regex)
	{
		return regex + "+";
	}
	static boolean isCandidate(String regex, String target)
	{
		boolean candidate = false;
		Matcher match = find(regex, target);
		candidate = match.find() ? true : false;
		return candidate;
	}
	public static void main(String[] args){
		Parse test = new Parse();
		Parse text2 = new Parse(REGEX_LIST, TARGET_STRING);
		Parse text3 = new Parse();
	}
}

