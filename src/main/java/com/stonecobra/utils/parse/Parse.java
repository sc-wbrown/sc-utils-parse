package com.stonecobra.utils.parse;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parse 
{
	class LineParser{};
	private static final String GLOBAL_NEWLINE = "[\\n\\r]";
	private static final String REGEX = "REGEX_PATTERN";
	private static final String TARGET_STRING = "TARGET";
	
	public Parse()
	{
		init();
	}
	public Parse(String regex, String target)
	{
		init(regex, target);
	}	
	private void init(){
		Matcher match = find(REGEX, TARGET_STRING);
		System.out.println( match.find() );
	}	
	private void init(String regex, String target){	
		String ext = target.substring(target.lastIndexOf(".")+1,target.length());
		if(ext.equals("txt") || ext.endsWith("log")){
			BufferedReader read;
			try {
				read = new BufferedReader(new FileReader(target));
				String line = null;
				File out = new File("result.txt");
				while((line = read.readLine()) != null){
					System.out.println(line);
					if(isCandidate(regex, line)){
						System.out.println("match");
						Matcher match = find(regex, line);
						try {
							FileWriter write = new FileWriter(out, true);
							int counter = 0;
							while(match.find()){	
								counter++;
								System.out.println(match.group());
								write.write("Match " + counter + " :" + match.group() + "\n");
							}
							write.close();
						} catch (IOException e) {
							e.printStackTrace();
							System.out.println("Could not create file writer");
						}			
					}
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				System.out.println("File not found");
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("No line to read");
			}	
		}
		else
		{
			if(isCandidate(regex, target))
			{ 
				Matcher match = find(regex, target);
				File out = new File("result.txt");
				try {
					FileWriter write = new FileWriter(out);
					int counter = 0;
					while(match.find()){	
						counter++;
						System.out.println(match.group());
						write.write("Match " + counter + " :" + match.group() + "\n");
					}
					write.close();
				} catch (IOException e) {
					e.printStackTrace();
					System.out.println("Could not create file writer");
				}		
			}
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
}

