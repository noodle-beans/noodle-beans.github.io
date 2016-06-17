import java.io.*;
import java.nio.file.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.*;

public class UpdateHtmlFiles
{	
	private static String pathChange = "../"; //all paths are relative to noodle-beans.github.io directory. This variable allows me to change the location of this program without any major edits
	
	public static void main( String[] args )
	{
		System.out.println( "What would you like to do?" );
		System.out.println( "	<0> Exit with no action" );
		System.out.println( "	<1> Update all html files (in place)" );
		System.out.println( "	<2> Add new comics from cache" );
		System.out.println( "" );
		System.out.print( "Enter your choice: " );
		
		Scanner input = new Scanner( System.in );
		int actionChoice = input.nextInt();
		
		switch( actionChoice )
		{
			case 1:
			update();
			System.out.println( "Html files updated!" );
			break;
			case 2:
			addFromCache();
			System.out.println( "Comics added to repository!" );
			break;
			default:
			System.out.println( "Program terminated: No action taken. Have a nice day!" );
			break;
		}
	}
	
	// update #######################################################################################################################################
	
	public static void update()
	{
		int maxNumber = new File( pathChange + "comics" ).list().length; //number of comics currently on website;
		
		// Reading >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
		
		ArrayList<String> lines = readLines( pathChange + "editing/base.html" );
		
		ArrayList<String> titles = readLines( pathChange + "editing/titles.txt" );
		ArrayList<String> mouseOver = readLines( pathChange + "editing/mouseOver.txt" );
		ArrayList<String> descriptions = readDescriptions( pathChange + "editing/descriptions.txt", maxNumber );
		
		// Writing >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
		
		String fileName;
		
		for( int j = 1; j <= maxNumber; j++ )
		{
			fileName = pathChange + "noodles/" + String.format( "%04d", j ) + ".html";
			
			lines.set( 29, "		<p id=\"title\"><a name=\"comicView\">" + titles.get( j - 1 ) + "</a></p>" );
			lines.set( 30, "		<img id=\"comic\" src=\"../comics/comic" + String.format( "%04d", j ) + ".JPG\" width=\"400\" height=\"400\" alt=\"" + mouseOver.get( j - 1 ) + "\" title = \"" + mouseOver.get( j - 1 ) + "\">" );
			
			if( j == 1 )
			{
				lines.set( 32, " 			<li><a class=\"inactive\" href=\"javascript:;\">FIRST</a></li><!--" );
				lines.set( 33, " 			--><li><a class=\"inactive\" href=\"javascript:;\">PREV</a></li><!--" );
				lines.set( 34, " 			--><li><a href=\"0002.html#comicView\">NEXT</a></li><!--" );
			}
			else if( j == maxNumber )
			{
				lines.set( 33, " 			--><li><a href=\"" + String.format( "%04d", j - 1 ) + ".html#comicView\">PREV</a></li><!--" );
				lines.set( 34, " 			--><li><a class=\"inactive\" href=\"javascript:;\">NEXT</a></li><!--" );
				lines.set( 35, " 			--><li><a class=\"inactive\" href=\"javascript:;\">LAST</a></li>" );
			}
			else
			{
				lines.set( 32, " 			<li><a href=\"0001.html#comicView\">FIRST</a></li><!--" );
				lines.set( 33, " 			--><li><a href=\"" + String.format( "%04d", j - 1 ) + ".html#comicView\">PREV</a></li><!--" );
				lines.set( 34, " 			--><li><a href=\"" + String.format( "%04d", j + 1 ) + ".html#comicView\">NEXT</a></li><!--" );
				lines.set( 35, " 			--><li><a href=\"../index.html#comicView\">LAST</a></li>" );
			}
			
			lines.set( 37, "		<p id=\"description\">" + descriptions.get( j - 1 ) + "<p>" );
			
			writeLines( lines, fileName );
		}
		
		//index.html edits
		lines.set( 4, "		<link rel=\"stylesheet\" href=\"style.css\">" );
		lines.set( 25, "			<li><a href=\"javascript:;\">Home</a></li><!--" );
		lines.set( 29, "		<p id=\"title\"><a name=\"comicView\">" + titles.get( maxNumber - 1 ) + "</a></p>" );
		lines.set( 30, "		<img id=\"comic\" src=\"comics/comic" + String.format( "%04d", maxNumber ) + ".JPG\" width=\"400\" height=\"400\" alt=\"" + mouseOver.get( maxNumber - 1 ) + "\" title = \"" + mouseOver.get( maxNumber - 1 ) + "\">" );
		lines.set( 32, " 			<li><a href=\"noodles/0001.html#comicView\">FIRST</a></li><!--" );
		lines.set( 33, " 			--><li><a href=\"noodles/" + String.format( "%04d", maxNumber - 1 ) + ".html#comicView\">PREV</a></li><!--" );
		lines.set( 34, " 			--><li><a class=\"inactive\" href=\"javascript:;\">NEXT</a></li><!--" );
		lines.set( 35, " 			--><li><a class=\"inactive\" href=\"javascript:;\">LAST</a></li>" );
		lines.set( 37, "		<p id=\"description\">" + descriptions.get( maxNumber - 1 ) + "<p>" );
			
		writeLines( lines, pathChange + "index.html" );
		
		
		// writing sitemap file
		ArrayList<String> sitemapLines = new ArrayList<String>();
		sitemapLines.add( "http://noodlebeans.tk/index.html" );
		for( int j = 0; j < maxNumber; j++ )
		{
			sitemapLines.add( "http://noodlebeans.tk/noodles/" + String.format( "%04d", j + 1 ) );
		}
		writeLines( sitemapLines, pathChange + "sitemap.txt" );
	}
	
	// addFromCache #################################################################################################################################
	
	public static void addFromCache()
	{
		System.out.println( "Number of comics currently in repository: " + new File( pathChange + "comics" ).list().length );
		System.out.print( "Enter the number of comics you want to have in the repository: " );
		Scanner input = new Scanner( System.in );
		int maxNumber = input.nextInt(); //number of comics to move from cache to website
				
		for( int j = 1; j <= maxNumber; j++ )
		{
			File comicDestination = new File( pathChange + "comics/comic" + String.format( "%04d", j ) + ".JPG" );
			
			if( !comicDestination.exists() )
			{
				Path comicDestinationPath = Paths.get( pathChange + "comics/comic" + String.format( "%04d", j ) + ".JPG" );
				Path comicLocationPath = Paths.get( pathChange + "../Comic Cache/comicsCache/comic" + String.format( "%04d", j ) + ".JPG" );
				
				try
				{
					Files.move( comicLocationPath, comicDestinationPath );
				}
				catch( IOException e )
				{
					System.out.println( "Comic not moved to repository." );
				}
			}
		}
		
		ArrayList<String> cacheTitles = readLines( pathChange + "../Comic Cache/titlesCache.txt" );
		ArrayList<String> cacheMouseOver = readLines( pathChange + "../Comic Cache/mouseOverCache.txt" );
		ArrayList<String> cacheDescriptions = readDescriptions( pathChange + "../Comic Cache/descriptionsCache.txt", maxNumber );
		
		writeLines( cacheTitles.subList( 0, maxNumber ), pathChange + "editing/titles.txt" );
		writeLines( cacheMouseOver.subList( 0, maxNumber ), pathChange + "editing/mouseOver.txt" );
		
		PrintWriter out = null;
		
		try
		{
			out = new PrintWriter( new BufferedWriter( new FileWriter( pathChange + "editing/descriptions.txt" ) ) );
			
			for( int j = 1; j < 10000; j++ )
			{
				out.println( ( j <= maxNumber ) ? cacheDescriptions.get( j - 1 ) : "" );
				out.println( "#" + String.format( "%04d", j ) + "#" );
				out.println();
			}
		}
		catch ( IOException e )
		{
			System.out.println( "Writer not created for descriptions.txt" );
		}
		finally
		{
			out.close();
		}
		
		update();
	}
	
	// Helper functions #############################################################################################################################
	
	// readLines ------------------------------------------------------------------------------------------------------------------------------------
	
	public static ArrayList<String> readLines( String fileName )
	{
		File file = new File( fileName );
		
		if ( !file.exists() )
		{
			System.out.println( fileName + " does not exist" );
			System.exit(2);
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
			System.exit(3);
		}
		catch( IOException e )
		{
			System.out.println( "BufferedReader not created for " + fileName );
			System.exit(4);
		}
		finally
		{
			return lines;
		}
	}
	
	// readDescriptions -----------------------------------------------------------------------------------------------------------------------------
	
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
	
	// writeLines -----------------------------------------------------------------------------------------------------------------------------------
	
	public static void writeLines( List<String> lines, String fileName )
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