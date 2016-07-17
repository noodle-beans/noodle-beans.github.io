import java.io.*;
import java.nio.file.*;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
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
		// writing sitemap file
		ArrayList<String> sitemapLines = new ArrayList<String>();
		
		// Reading >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
		
		ArrayList<String> lines = readLines( pathChange + "editing/base.html" );
		ArrayList<ComicInfo> comicInfoList = (ArrayList<ComicInfo>) readObject( pathChange + "editing/comicInfoList.dat" );
		HashMap<String,ArrayList<Integer>> collectionsToComicsMap = (HashMap<String,ArrayList<Integer>>) readObject( pathChange + "editing/collectionsToComicsMap.dat" );
		
		// Writing >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
		
		String fileName;
		ComicInfo comic;
		
		// Main Feed ================================================================================================================================
		
		ArrayList<Integer> mainFeed = collectionsToComicsMap.get( "Main Feed" );
		
		for( int j = 1; j <= mainFeed.size(); j++ )
		{
			fileName = pathChange + "noodles/" + String.format( "%04d", j ) + ".html";
			comic = comicInfoList.get( mainFeed.get( j - 1 ).intValue() );
			
			lines.set( 30, "		<p id=\"title\"><a name=\"comicView\">" + comic.getTitle() + "</a></p>" );
			lines.set( 31, "		<img id=\"comic\" src=\"" + "../" + comic.getComicPath() + "\" width=\"400\" height=\"400\" alt=\"" + comic.getMouseOver() + "\" title = \"" + comic.getMouseOver() + "\">" );
			
			if( j == 1 ) //first comic in collection
			{
				lines.set( 33, " 			<li><a class=\"inactive\" href=\"javascript:;\">FIRST</a></li><!--" );
				lines.set( 34, " 			--><li><a class=\"inactive\" href=\"javascript:;\">PREV</a></li><!--" );
				lines.set( 35, " 			--><li><a href=\"0002.html#comicView\">NEXT</a></li><!--" );
				lines.set( 36, " 			--><li><a href=\"../index.html#comicView\">LAST</a></li>" );
			}
			else if( j == mainFeed.size() ) //last comic in collection
			{
				lines.set( 33, " 			<li><a href=\"0001.html#comicView\">FIRST</a></li><!--" );
				lines.set( 34, " 			--><li><a href=\"" + String.format( "%04d", j - 1 ) + ".html#comicView\">PREV</a></li><!--" );
				lines.set( 35, " 			--><li><a class=\"inactive\" href=\"javascript:;\">NEXT</a></li><!--" );
				lines.set( 36, " 			--><li><a class=\"inactive\" href=\"javascript:;\">LAST</a></li>" );
			}
			else
			{
				lines.set( 33, " 			<li><a href=\"0001.html#comicView\">FIRST</a></li><!--" );
				lines.set( 34, " 			--><li><a href=\"" + String.format( "%04d", j - 1 ) + ".html#comicView\">PREV</a></li><!--" );
				lines.set( 35, " 			--><li><a href=\"" + String.format( "%04d", j + 1 ) + ".html#comicView\">NEXT</a></li><!--" );
				lines.set( 36, " 			--><li><a href=\"../index.html#comicView\">LAST</a></li>" );
			}
			
			lines.set( 38, "		<p id=\"description\">" + comic.getDescription() + "<p>" );
			
			writeLines( lines, fileName );
			sitemapLines.add( "http://noodlebeans.tk/noodles/" + String.format( "%04d", j + 1 ) );
		}
		
		//index.html edits
		comic = comicInfoList.get( mainFeed.get( mainFeed.size() - 1 ).intValue() ); //should be the value of comic already, but just to make sure last comic is focus
		
		lines.set( 4, "		<link rel=\"stylesheet\" href=\"style.css\">" );
		lines.set( 25, "			<li><a href=\"javascript:;\">Home</a></li><!--" );
		lines.set( 30, "		<p id=\"title\"><a name=\"comicView\">" + comic.getTitle() + "</a></p>" );
		lines.set( 31, "		<img id=\"comic\" src=\"" + comic.getComicPath() + "\" width=\"400\" height=\"400\" alt=\"" + comic.getMouseOver() + "\" title = \"" + comic.getMouseOver() + "\">" );
		lines.set( 33, " 			<li><a href=\"noodles/0001.html#comicView\">FIRST</a></li><!--" );
		lines.set( 34, " 			--><li><a href=\"noodles/" + String.format( "%04d", mainFeed.size() - 1 ) + ".html#comicView\">PREV</a></li><!--" );
		lines.set( 35, " 			--><li><a class=\"inactive\" href=\"javascript:;\">NEXT</a></li><!--" );
		lines.set( 36, " 			--><li><a class=\"inactive\" href=\"javascript:;\">LAST</a></li>" );
		lines.set( 38, "		<p id=\"description\">" + comic.getDescription() + "<p>" );
			
		writeLines( lines, pathChange + "index.html" );
		sitemapLines.add( "http://noodlebeans.tk/index.html" );
		
		// Serious Baby =============================================================================================================================
		
		//reset lines
		lines.set( 4, "		<link rel=\"stylesheet\" href=\"../seriousBabyStyle.css\">" );
		lines.set( 25, "			<li><a href=\"../index.html#comicView\">Home</a></li><!--" );
		lines.remove( 26 );
		
		
		ArrayList<Integer> seriousBaby = (ArrayList<Integer>) collectionsToComicsMap.get( "Serious Baby" );
		
		for( int j = 1; j <= seriousBaby.size(); j++ )
		{
			fileName = pathChange + "seriousbaby/" + String.format( "%04d", j ) + ".html";
			comic = comicInfoList.get( seriousBaby.get( j - 1 ).intValue() );
			
			lines.set( 29, "		<p id=\"title\"><a name=\"comicView\">" + comic.getTitle() + "</a></p>" );
			lines.set( 30, "		<img id=\"comic\" src=\"" + "../" + comic.getComicPath() + "\" width=\"400\" height=\"400\" alt=\"" + comic.getMouseOver() + "\" title = \"" + comic.getMouseOver() + "\">" );
			
			if( j == 1 ) //first comic in collection
			{
				lines.set( 32, " 			<li><a class=\"inactive\" href=\"javascript:;\">FIRST</a></li><!--" );
				lines.set( 33, " 			--><li><a class=\"inactive\" href=\"javascript:;\">PREV</a></li><!--" );
				lines.set( 34, " 			--><li><a href=\"0002.html#comicView\">NEXT</a></li><!--" );
				lines.set( 35, " 			--><li><a href=\"" + String.format( "%04d", seriousBaby.size() ) + ".html#comicView\">LAST</a></li>" );
			}
			else if( j == seriousBaby.size() ) //last comic in collection
			{
				lines.set( 32, " 			<li><a href=\"0001.html#comicView\">FIRST</a></li><!--" );
				lines.set( 33, " 			--><li><a href=\"" + String.format( "%04d", j - 1 ) + ".html#comicView\">PREV</a></li><!--" );
				lines.set( 34, " 			--><li><a class=\"inactive\" href=\"javascript:;\">NEXT</a></li><!--" );
				lines.set( 35, " 			--><li><a class=\"inactive\" href=\"javascript:;\">LAST</a></li>" );
			}
			else
			{
				lines.set( 32, " 			<li><a href=\"0001.html#comicView\">FIRST</a></li><!--" );
				lines.set( 33, " 			--><li><a href=\"" + String.format( "%04d", j - 1 ) + ".html#comicView\">PREV</a></li><!--" );
				lines.set( 34, " 			--><li><a href=\"" + String.format( "%04d", j + 1 ) + ".html#comicView\">NEXT</a></li><!--" );
				lines.set( 35, " 			--><li><a href=\"" + String.format( "%04d", seriousBaby.size() ) + ".html#comicView\">LAST</a></li>" );
			}
			
			lines.set( 37, "		<p id=\"description\">" + comic.getDescription() + "<p>" );
			
			writeLines( lines, fileName );
			sitemapLines.add( "http://noodlebeans.tk/seriousbaby/" + String.format( "%04d", j + 1 ) );
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
		
		// read info from cache
		ArrayList<String> cacheTitles = readLines( pathChange + "../Comic Cache/titlesCache.txt" );
		ArrayList<String> cacheMouseOver = readLines( pathChange + "../Comic Cache/mouseOverCache.txt" );
		ArrayList<String> cacheDescriptions = readDescriptions( pathChange + "../Comic Cache/descriptionsCache.txt", maxNumber );
		ArrayList<String> cacheContainingCollections = readLines( pathChange + "../Comic Cache/containingCollectionsCache.txt" );
		
		ArrayList<ComicInfo> comicInfoList = new ArrayList<ComicInfo>();
		ComicInfo comicInfo;  //class created in containing directory (not a java class)
		
		// Add new collections here
		HashMap<String,ArrayList<Integer>> collectionsToComicsMap = new HashMap<String,ArrayList<Integer>>();
		collectionsToComicsMap.put( "Main Feed", new ArrayList<Integer>() );
		collectionsToComicsMap.put( "Serious Baby", new ArrayList<Integer>() );
		String collectionsContainingComic;
		
		//copy over comic image files, get comic info for each comic, and sort comics into collections
		for( int j = 1; j <= maxNumber; j++ )
		{
			File comicDestination = new File( pathChange + "comics/comic" + String.format( "%04d", j ) + ".JPG" );
			
			if( !comicDestination.exists() )
			{
				Path comicDestinationPath = Paths.get( pathChange + "comics/comic" + String.format( "%04d", j ) + ".JPG" );
				Path comicLocationPath = Paths.get( pathChange + "../Comic Cache/comicsCache/comic" + String.format( "%04d", j ) + ".JPG" );
				
				try
				{
					Files.copy( comicLocationPath, comicDestinationPath );
				}
				catch( IOException e )
				{
					System.out.println( "Comic not moved to repository." );
				}
			}
			
			comicInfo = new ComicInfo();
			
			comicInfo.setTitle( cacheTitles.get( j - 1 ) );
			comicInfo.setMouseOver( cacheMouseOver.get( j - 1 ) );
			comicInfo.setDescription( cacheDescriptions.get( j - 1 ) );
			comicInfo.setComicPath( "comics/comic" + String.format( "%04d", j ) + ".JPG" );
			
			comicInfoList.add( comicInfo );
			
			collectionsContainingComic = cacheContainingCollections.get( j - 1 );
			
			if( collectionsContainingComic.contains( "mf" ) ) //main feed
			{
				collectionsToComicsMap.get( "Main Feed" ).add( j - 1 );
			}
			
			if( collectionsContainingComic.contains( "sb" ) ) // serious baby
			{
				collectionsToComicsMap.get( "Serious Baby" ).add( j - 1 );
			}
		}
		
		//save comic info in comicInfoList.dat
		writeObject( comicInfoList, pathChange + "editing/comicInfoList.dat" );
		//save collection to comics map in collectionsToComicsMap.dat
		writeObject( collectionsToComicsMap, pathChange + "editing/collectionsToComicsMap.dat" );
		
		update();
	}
	
	
	// ##############################################################################################################################################
	// ############################################################## Helper functions ##############################################################
	// ##############################################################################################################################################
	
	
	// Reading >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	
	// readLines ------------------------------------------------------------------------------------------------------------------------------------
	
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
			System.exit(2);
		}
		catch( IOException e )
		{
			System.out.println( "BufferedReader not created for " + fileName );
			System.exit(3);
		}
		
		return lines;
	}
	
	// readDescriptions -----------------------------------------------------------------------------------------------------------------------------
	
	public static ArrayList<String> readDescriptions( String fileName, int maxNumber )
	{
		File file = new File( fileName );
		
		if ( !file.exists() )
		{
			System.out.println( fileName + " does not exist" );
			System.exit(4);
		}
		
		ArrayList<String> descriptions = new ArrayList<String>();
		
		try
		{
			Scanner descriptionScanner = new Scanner( file ).useDelimiter( Pattern.compile( "[\n\r\f]{2}#[0-9]{4}#[\n\r\f]{4}" ) );
						
			for( int j = 0; j < maxNumber; j++ )
			{
				descriptions.add( descriptionScanner.next() ); 
			}
			
			descriptionScanner.close();
		}
		catch( IndexOutOfBoundsException e )
		{
			System.out.println( fileName + " is incorrectly formatted" );
			System.exit(5);
		}
		catch( IOException e )
		{
			System.out.println( "Scanner not created for " + fileName );
			System.exit(6);
		}
		
		return descriptions;
	}
	
	// readObject --------------------------------------------------------------------------------------------------------------------------------
	
	public static Object readObject( String fileName )
	{
		File file = new File( fileName );
		
		if ( !file.exists() )
		{
			System.out.println( fileName + " does not exist" );
			System.exit(7);
		}
		
		Object obj = null;
		
		try
		{
			ObjectInputStream in = new ObjectInputStream( new BufferedInputStream( new FileInputStream( file ) ) );
			
			obj = in.readObject();
			
			in.close();
		}
		catch( ClassNotFoundException e )
		{
			System.out.println( "ClassNotFoundException: object not read." );
		}
		catch( IOException e )
		{
			System.out.println( "IOException: object not read." );
		}
		
		return obj;
	}
	
	// Writing >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	
	// writeLines -----------------------------------------------------------------------------------------------------------------------------------
	
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
			System.exit(7);
		}
		finally
		{
			out.close();
		}
	}
	
	// writeObject -------------------------------------------------------------------------------------------------------------------------------
	
	public static void writeObject( Object obj, String fileName )
	{
		File file = new File( fileName );
		
		try
		{
			if ( !file.exists() )
			{
				file.createNewFile();
			}
		}
		catch( Exception e )
		{
			System.out.println( "Exception: file not created." );
			System.exit(8);
		}
				
		try
		{
			ObjectOutputStream out = new ObjectOutputStream( new BufferedOutputStream( new FileOutputStream( file ) ) );
			
			out.writeObject( obj );
			
			out.close();
		}
		catch( IOException e )
		{
			System.out.println( "IOException: object not written." );
			System.exit(9);
		}
	}
}