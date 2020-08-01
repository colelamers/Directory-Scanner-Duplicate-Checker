package application;


import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Pane;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import java.util.ArrayList;
import java.util.Collections;
//Created by Cole Lamers

public class jfsdcController extends Functions
{
	@FXML private TextField tbDirectory;
	@FXML private ComboBox<String> cbFileType;
	@FXML private Pane paneTabMain;
	@FXML private Pane paneTabSecond;
	@FXML private Label labelResults;
	@FXML private RadioButton rbFileType;
	@FXML private RadioButton rbFilePath;
	@FXML private RadioButton rbFileName;
	@FXML private RadioButton rbTXT;
	@FXML private RadioButton rbCSV;
	@FXML private RadioButton rbDuplicateCheck;


	public void btnBrowseClick(ActionEvent event)throws IOException
	{
		logFile("browse button clicked");
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new java.io.File("C:\\"));
		chooser.setDialogTitle("Select Directory Path");
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setAcceptAllFileFilterUsed(true);

		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) 
		{
			tbDirectory.setText(String.valueOf(chooser.getSelectedFile()));
		}
	}

	public void btnGetDataClick(ActionEvent event) throws IOException
	{
		fol.removeAll(fol);//clears the list each time the button is pressed so that it doesn't accumulate together if a new search is conducted
		filterList.removeAll(filterList);
		duplicateList.removeAll(duplicateList);
		listIndexOfDuplicates.removeAll(listIndexOfDuplicates);
		labelResults.setText("Results: 0");//resets the text each time it's called
		try
		{
			File directoryPath = new File(tbDirectory.getText());//directory is set by whatever the user inputs in the directory field
			listFilesInDirectory(directoryPath);
			//		initialize();//need to add files to combobo

			labelResults.setText("Results: " + fol.size());
			resetComboBox();

		}//just gets all the data and creates an object out of it
		catch (NullPointerException e)
		{
			logFile(e.toString());
		}
	}

	public void btnFilterClick(ActionEvent event) throws IOException
	{
		if (labelResults.getText() == "Results: 0")
		{
			JOptionPane.showMessageDialog(null, "No results collected, click Get Data.");
		}
		else
		{
			fileOutput(rbFileKindNum);//checks what type of file to output to
			File desktopPath = new File (System.getProperty("user.home") + "\\Desktop\\filterFiles." + rbFileKind);//saves file to desktop -- UPDATE THIS AFTER THE PERIOD TO CAPTURE WHICHEVER RADIO BUTTON IS CHECKED FOR CSV OR TXT
			PrintWriter pw = new PrintWriter(desktopPath);

			if (cbFileType.getValue() == null)
			{
				logFile("No ComboBox filter selected");

				if (rbDuplicateCheck.isSelected()) 
				{
					duplicateCheck(fol);
					fileSort(rbSortNum, duplicateList);//performs switch case check to see how it'll be sorted
					for (FileObjects dupe : duplicateList)
					{
						pw.println(dupe);
					}
				}//list of all objectsand checked for duplicates
				else
				{
					fileSort(rbSortNum, fol);
					for (FileObjects obj : fol)
					{
						pw.println(obj);
					}//adds all the contents stored in the listOfFiles object to the text file
				}//list of all objects
			}//this will just return the whole object if the user is not filtering based off of the file types
			else
			{
				logFile("ComboBox filter selected");
				if (rbDuplicateCheck.isSelected())
				{
					fileTypeFilter(cbFileType.getValue().toLowerCase());
					duplicateCheck(filterList);
					fileSort(rbSortNum, filterList);//checks which sorting type to print the data as
					for (FileObjects dupeList : duplicateList)
					{
						pw.println(dupeList);
					}
				}//list of filtered list from combobox and duplicate checked from that list
				else
				{
					fileTypeFilter(cbFileType.getValue().toLowerCase());
					fileSort(rbSortNum, filterList);//checks which sorting type to print the data as
					for (FileObjects filter : filterList)
					{
						pw.println(filter);
					}
				}//list of filtered objects from combobox
			}//if the combobox has text, it will perform this type of filter where it gets the additionally filtered list instead of the object in it's entirety
			pw.close();//flushes the data to the file
			JOptionPane.showMessageDialog(null, "Data output to filterFiles.txt. Please collect new data.");

			fol.removeAll(fol);//clears the list each time the button is pressed so that it doesn't accumulate together if a new search is conducted
			filterList.removeAll(filterList);
			duplicateList.removeAll(duplicateList);
			listIndexOfDuplicates.removeAll(listIndexOfDuplicates);
			labelResults.setText("Results: 0");//resets the text each time it's called		}
	}
	public void initialize()
	{
		rbFileType.setSelected(true);
		rbTXT.setSelected(true);
		tbDirectory.setText("b:\\zdupes");
		labelResults.setText("Results: 0");
	}

	/*`
	 * Action Events
	 * This just determines the values of the comboboxes
	 */

	public void resetComboBox()
	{
		cbFileType.getItems().clear();
		ArrayList<String> distinctFileTypes = new ArrayList<>();
		for(int i = 0; i < fol.size(); i++)
		{
			if (!distinctFileTypes.contains(fol.get(i).getFileType()))
			{
				distinctFileTypes.add(fol.get(i).getFileType());//added solely because I can't check off the combobox
			}//if the filetype at that spot does not exist, it adds it to the list
		}		

		Collections.sort(distinctFileTypes);//sorts the list now alphabetically

		for (int i = 0; i < distinctFileTypes.size(); i++)
		{
			cbFileType.getItems().add(
					distinctFileTypes.get(i)
					);
		}//adds the alphabetically sorted list into the combobox
	}
	public void fileTypeChecked(ActionEvent event)
	{
		if (rbFileType.isSelected())
		{
			rbFilePath.setSelected(false);
			rbFileName.setSelected(false);
		}
		else if (rbFileType.isSelected() == false && rbFilePath.isSelected() == false && rbFileName.isSelected() == false)
		{
			rbFileType.setSelected(true);
		}//ensures one button is enabled
		rbSortNum = 1;//set for switch case
	}
	public void filePathChecked(ActionEvent event)
	{
		if (rbFilePath.isSelected())
		{
			rbFileType.setSelected(false);
			rbFileName.setSelected(false);
		}
		else if (rbFileType.isSelected() == false && rbFilePath.isSelected() == false && rbFileName.isSelected() == false)
		{
			rbFilePath.setSelected(true);
		}//ensures one button is enabled
		rbSortNum = 2;//set for switch case
	}
	public void fileNameChecked(ActionEvent event)
	{
		if (rbFileName.isSelected())
		{
			rbFilePath.setSelected(false);
			rbFileType.setSelected(false);
		}
		else if (rbFileType.isSelected() == false && rbFilePath.isSelected() == false && rbFileName.isSelected() == false)
		{
			rbFileName.setSelected(true);
		}//ensures one button is enabled
		rbSortNum = 3;//set for switch case
	}
	public void txtChecked(ActionEvent event)
	{
		if (rbTXT.isSelected())
		{
			rbCSV.setSelected(false);
		}
		else if (rbTXT.isSelected() == false && rbCSV.isSelected() == false)
		{
			rbTXT.setSelected(true);
		}//ensures one button is enabled
		rbFileKindNum = 1;//set for switch case
	}
	public void csvChecked(ActionEvent event)
	{
		if (rbCSV.isSelected())
		{
			rbTXT.setSelected(false);
		}
		else if (rbTXT.isSelected() == false && rbCSV.isSelected() == false)
		{
			rbCSV.setSelected(true);
		}//ensures one button is enabled
		rbFileKindNum = 2;//set for switch case
	}

}

