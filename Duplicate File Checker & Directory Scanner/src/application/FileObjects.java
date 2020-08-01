package application;
//Created by Cole Lamers
public class FileObjects
{
	private String fileType;
	private String filePath;
	private String fileName;

	public FileObjects(String fp, String fn)
	{
		this.filePath = fp;
		this.fileName = fn;
	}//2 argument constructor, file type is constructed from filename

	public FileObjects(){}//default constructor

	public void setFileType(String ft) {this.fileType = ft;}
	public String getFileType()
	{
		for (int i = 0; i < this.fileName.length(); i++)
		{
			if (this.fileName.charAt(this.fileName.length() - i - 1) == '.')
			{
				this.fileType = this.fileName.substring(this.fileName.length() - i);
				break;
			}
		}
		return this.fileType.toUpperCase();
	}//finds the first period from the end of the string and cuts it off from there, then returns it as a type

	public void setFilePath(String fp) {this.filePath = fp;}
	public String getFilePath() {return this.filePath;}

	public void setFileName(String fn) {this.fileName = fn;}
	public String getFileName()
	{
		String temp = "";
		for (int i = 0; i < this.fileName.length(); i++)
		{
			if (this.fileName.charAt(this.fileName.length() - i - 1) == '.')
			{
				temp = this.fileName.substring(0, this.fileName.length() - i - 1);
				break;
			}
		}
		return temp;	
	}//returns the filename without the filetype and capitalizes the first letter of the name with the first inner substring, then grabs the last portion of the full string in the second portion

	@Override
	public String toString()
	{
		return getFileType() + "," + getFilePath() + "," + getFileName();
	}//no spaces in between periods to eliminate the need to trim the unnecessary space as a csv
}
