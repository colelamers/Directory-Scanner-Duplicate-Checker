package application;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Comparator;
import java.util.*;
//Created by Cole Lamers
public class Functions
{
	public int rbSortNum = 1;
	public int rbFileKindNum = 1;
	public String rbFileKind = "txt";
	Date now = new Date();
	SimpleDateFormat fileDate = new SimpleDateFormat("YYYY-MM-dd");
	File debugLogger = new File (System.getProperty("user.home") + "\\Desktop\\" + fileDate.format(now) + " debugLog.txt");
	int debugCount = 0;
	List<FileObjects> filterList = new ArrayList<>();
	List<FileObjects> fol = new ArrayList<>();
	List<FileObjects> duplicateList = new ArrayList<>();
	ArrayList<Integer> listIndexOfDuplicates = new ArrayList<>();


	public void listFilesInDirectory(final File folder) throws IOException
	{
		for (final File fileEntry : folder.listFiles()) 
		{
			if (fileEntry.isDirectory()) 
			{
				listFilesInDirectory(fileEntry);
			} //recursively checks if a directory, then makes it go through again if it is a folder and checks within
			else 
			{
				fol.add(new FileObjects(fileEntry.getParent(), fileEntry.getName()));
			}//adds the contents to the object
		}
	}

	public void fileTypeFilter(String filter) throws IOException
	{
		logFile("fileTypeFilter run");
		for (FileObjects fo : fol)
		{
			if(fo.getFileType().toLowerCase().equals(filter.toLowerCase()))
			{
				filterList.add(fo);
			}
		}
	}//passes through the string value from the combobox

	public void duplicateCheck(List<FileObjects> whichList) throws IOException
	{
		logFile("dulicateCheck run");
		for (int i = 0; i < whichList.size(); i++)//index that gets compared to everything
		{
			int count = 0; // resets counter needed to listOfIndexOfDuplicates for a duplicate
			for (int j = (whichList.size() + i - whichList.size()); j < whichList.size(); j++)//index that compares all less K against the initial one; i
			{
				if (whichList.get(i).getFileName().toLowerCase().equals(whichList.get(j).getFileName().toLowerCase()) && i != j && !listIndexOfDuplicates.contains(j))//contents must equal, must not be same index number, must not have already added that index
				{
					duplicateList.add(whichList.get(j));
					listIndexOfDuplicates.add(j);
					count++;//adds to the counter needed to listOfIndexOfDuplicates if the string has more than one occurance
				}
				if (count == 1 && !listIndexOfDuplicates.contains(i))//means that there is more than one occurance of the same string
				{
					duplicateList.add(whichList.get(i));//this will add back in i == j, ignoring that parameter earlier only once because there are duplicates
					listIndexOfDuplicates.add(i);
				}
			}
		}
	}

	public void fileSort(int sort, List<FileObjects> whichList) throws IOException
	{
		logFile("fileSort run");
		switch (sort)
		{
		case 1:
			sortAscFileTypes(whichList);
			break;
		case 2:
			sortAscFilePaths(whichList);
			break;
		case 3:
			sortAscFileNames(whichList);
			break;
		}
	}//allows for sorting the data after a quick check to see what radio button is selected

	public void fileOutput(int output) throws IOException
	{
		logFile("fileOutput run");
		switch (output)
		{
		case 1:
			rbFileKind = "txt";
			break;
		case 2:
			rbFileKind = "csv";
			break;
		}
	}//sets a string value according to the radio button selected to return as the file type

	public void sortAscFileNames(List<FileObjects> listOfFileObjects)
	{
		Collections.sort(listOfFileObjects, new Comparator<FileObjects>()
		{
			@Override
			public int compare(FileObjects arg1, FileObjects arg2)
			{
				return arg1.getFileName().toLowerCase().compareTo(arg2.getFileName().toLowerCase());
			}
		}
				);

	}

	public void sortAscFileTypes(List<FileObjects> listOfFileObjects)
	{
		Collections.sort(listOfFileObjects, new Comparator<FileObjects>()
		{
			@Override
			public int compare(FileObjects arg1, FileObjects arg2)
			{
				return arg1.getFileType().toLowerCase().compareTo(arg2.getFileType().toLowerCase());
			}
		}
				);

	}

	public void sortAscFilePaths(List<FileObjects> listOfFileObjects)
	{
		Collections.sort(listOfFileObjects, new Comparator<FileObjects>()
		{
			@Override
			public int compare(FileObjects arg1, FileObjects arg2)
			{
				return arg1.getFilePath().toLowerCase().compareTo(arg2.getFilePath().toLowerCase());
			}
		}
				);

	}

	public void logFile(String text) throws IOException
	{
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
		PrintWriter debug = null;
		debug = new PrintWriter(new FileOutputStream(debugLogger, true));//FileOutputStream set to true so that it knows to not overwrite the file but to add to it
		debug.println(sdf.format(date)  + ", " + text);
		debug.close();
	}
}
