import java.io.Serializable;

public class ComicInfo implements Serializable
{
	private String comicPath; //path relative to noodle-beans.github.io directory
	private String title;
	private String description;
	private String mouseOver;
	
	ComicInfo()
	{
		comicPath = "";
		title = "";
		description = "";
		mouseOver = "";
	}
	
	ComicInfo( String cp, String t, String d, String mo )
	{
		comicPath = cp;
		title = t;
		description = d;
		mouseOver = mo;
	}
	
	public String getComicPath()
	{
		return comicPath;
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public String getDescription()
	{
		return description;
	}
	
	public String getMouseOver()
	{
		return mouseOver;
	}
	
	public void setComicPath( String cp )
	{
		comicPath = cp;
	}
	
	public void setTitle( String t )
	{
		title = t;
	}
	
	public void setDescription( String d )
	{
		description = d;
	}
	
	public void setMouseOver( String mo )
	{
		mouseOver = mo;
	}
	
	
}