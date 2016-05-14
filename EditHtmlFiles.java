import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.*;

public class EditHtmlFiles
{
	public static void main( String[] args )
	{
		int maxNumber = 16;
		
		// Reading ==================================================================================================================================
		
		ArrayList<String> lines = readLines( "editing/base.html" );
		
		ArrayList<String> titles = readLines( "editing/titles.txt" );
		ArrayList<String> mouseOver = readLines( "editing/mouseOver.txt" );
		ArrayList<String> descriptions = readDescriptions( "editing/descriptions.txt", maxNumber );
		
		//for( String s : descriptions )
		//{
		//	System.out.println(s);
		//}
		
		// Writing ==================================================================================================================================
		
		String fileName;
		
		for( int j = 1; j <= maxNumber; j++ )
		{
			fileName = "noodles/" + String.format( "%04d", j ) + ".html";
			
			lines.set( 14, "		<p id=\"title\"><a name=\"comicView\">" + titles.get( j - 1 ) + "</a></p>" );
			lines.set( 15, "		<img id=\"comic\" src=\"../comics/comic" + String.format( "%04d", j ) + ".JPG\" width=\"400\" height=\"400\" alt=\"" + mouseOver.get( j - 1 ) + "\" title = \"" + mouseOver.get( j - 1 ) + "\">" );
			
			if( j == 1 )
			{
				lines.set( 17, " 			<li><a class=\"inactive\" href=\"javascript:;\">FIRST</a></li><!--" );
				lines.set( 18, " 			--><li><a class=\"inactive\" href=\"javascript:;\">PREV</a></li><!--" );
				lines.set( 19, " 			--><li><a href=\"0002.html#comicView\">NEXT</a></li><!--" );
			}
			else if( j == maxNumber )
			{
				lines.set( 18, " 			--><li><a href=\"" + String.format( "%04d", j - 1 ) + ".html#comicView\">PREV</a></li><!--" );
				lines.set( 19, " 			--><li><a class=\"inactive\" href=\"javascript:;\">NEXT</a></li><!--" );
				lines.set( 20, " 			--><li><a class=\"inactive\" href=\"javascript:;\">LAST</a></li>" );
			}
			else
			{
				lines.set( 17, " 			<li><a href=\"0001.html#comicView\">FIRST</a></li><!--" );
				lines.set( 18, " 			--><li><a href=\"" + String.format( "%04d", j - 1 ) + ".html#comicView\">PREV</a></li><!--" );
				lines.set( 19, " 			--><li><a href=\"" + String.format( "%04d", j + 1 ) + ".html#comicView\">NEXT</a></li><!--" );
				lines.set( 20, " 			--><li><a href=\"../index.html#comicView\">LAST</a></li>" );
			}
			
			lines.set( 22, "		<p id=\"description\">" + descriptions.get( j - 1 ) + "<p>" );
			
			writeLines( lines, fileName );
		}
		
		//index.html edits
		lines.set( 4, "		<link rel=\"stylesheet\" href=\"style.css\">" );
		lines.set( 10, "			<li><a href=\"javascript:;\">Home</a></li><!--" );
		lines.set( 14, "		<p id=\"title\"><a name=\"comicView\">" + titles.get( maxNumber - 1 ) + "</a></p>" );
		lines.set( 15, "		<img id=\"comic\" src=\"comics/comic" + String.format( "%04d", maxNumber ) + ".JPG\" width=\"400\" height=\"400\" alt=\"" + mouseOver.get( maxNumber - 1 ) + "\" title = \"" + mouseOver.get( maxNumber - 1 ) + "\">" );
		lines.set( 17, " 			<li><a href=\"noodles/0001.html#comicView\">FIRST</a></li><!--" );
		lines.set( 18, " 			--><li><a href=\"noodles/" + String.format( "%04d", maxNumber - 1 ) + ".html#comicView\">PREV</a></li><!--" );
		lines.set( 19, " 			--><li><a class=\"inactive\" href=\"javascript:;\">NEXT</a></li><!--" );
		lines.set( 20, " 			--><li><a class=\"inactive\" href=\"javascript:;\">LAST</a></li>" );
		lines.set( 22, "		<p id=\"description\">" + descriptions.get( maxNumber - 1 ) + "<p>" );
			
		writeLines( lines, "index.html" );
	}
	
	public static ArrayList<String> readLines( String fileName )
	{
		File file = new File( fileName );
		
		if ( !file.exists() )
		{
			System.out.println( fileName + " does not exist" );
			System.exit(1);
		}
		
		BufferedReader reader = null;
		ArrayList<String> lines = new ArrayList<String>();
		
		try
		{
			reader = new BufferedReader( new FileReader( file ) );
			String line;
			
			while( ( line = reader.readLine() ) != null )
			{
				lines.add( line );
			}
			
			reader.close();
		}
		catch( IndexOutOfBoundsException e )
		{
			System.out.println( fileName + " is incorrectly formatted" );
			System.exit(4);
		}
		catch( IOException e )
		{
			System.out.println( "BufferedReader not created for " + fileName );
			System.exit(5);
		}
		finally
		{
			return lines;
		}
	}
	
	public static ArrayList<String> readDescriptions( String fileName, int maxNumber )
	{
		File file = new File( fileName );
		
		if ( !file.exists() )
		{
			System.out.println( fileName + " does not exist" );
			System.exit(1);
		}
		
		ArrayList<String> descriptions = new ArrayList<String>();
		
		try
		{
			Scanner descriptionScanner = new Scanner( file ).useDelimiter( Pattern.compile( "[\n\r\f]{2}#[0-9]{4}#[\n\r\f]{4}" ) );
			String currentDescription;
						
			for( int j = 0; j < maxNumber; j++ )
			{
				currentDescription = descriptionScanner.next().replaceAll( "[\n\r\f]{2}", "<br>" ).replace( "\"", "\\\"" ); //replaces newline char with html line break and replaces " with \" so quotes don't affect html syntax
				currentDescription = currentDescription.replace( "\u201c", "&ldquo;" ).replace( "\u201d", "&rdquo;" ).replace( "\u2019", "&rsquo;" ); //makes curvy special mac apostrophies and quotes work with html 
				descriptions.add( currentDescription ); 
			}
			
			descriptionScanner.close();
		}
		catch( IndexOutOfBoundsException e )
		{
			System.out.println( fileName + " is incorrectly formatted" );
			System.exit(4);
		}
		catch( IOException e )
		{
			System.out.println( "Scanner not created for " + fileName );
			System.exit(5);
		}
		finally
		{
			return descriptions;
		}
	}
	
	public static void writeLines( ArrayList<String> lines, String fileName )
	{
		PrintWriter out = null;
		
		try
		{
			out = new PrintWriter( new BufferedWriter( new FileWriter( fileName ) ) );
			
			for( int j = 0; j < lines.size(); j++ )
			{
				out.println( lines.get( j ) );
			}
		}
		catch ( IOException e )
		{
			System.out.println( "Writer not created for " + fileName );
		}
		finally
		{
			out.close();
		}
	}
}