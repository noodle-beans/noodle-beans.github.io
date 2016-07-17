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
	
	public String getTitleRaw()
	{
		return title;
	}
	
	public String getTitle()
	{
		return title.replace( "\"", "&quot;" ); //cleans string for use in html
	}
	
	public String getDescriptionRaw()
	{
		return description;
	}
	
	public String getDescription()
	{
		return description.replaceAll( "[\n\r\f]{2}", "<br>" ).replace( "\"", "&quot;" ).replace( "\u201c", "&ldquo;" ).replace( "\u201d", "&rdquo;" ).replace( "\u2019", "&rsquo;" ); //replaces newline char with html line break, replaces " with \" so quotes don't affect html syntax, makes curvy special apple ios apostrophies and quotes work with html
	}
	
	public String getMouseOverRaw()
	{
		return mouseOver;
	}
	
	public String getMouseOver()
	{
		return mouseOver.replace( "\"", "&quot;" ); //cleans string for use in html
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