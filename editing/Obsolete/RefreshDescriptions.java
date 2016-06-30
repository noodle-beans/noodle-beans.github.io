import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.*;

public class RefreshDescriptions
{
	public static void main( String args[] )
	{
		System.out.println( "Are you sure you want to refresh descriptions.txt? No content will be lost.\n(y/n): " );
		Scanner scanner = new Scanner( System.in );
		String s = scanner.next();
		if ( !s.equals( "y" ) )
		{
			System.exit(0);
		}
		
		int maxNumber = 13;
		ArrayList<String> descriptions = readDescriptions( "descriptions.txt", maxNumber );
		
		PrintWriter out = null;
		
		try
		{
			out = new PrintWriter( new BufferedWriter( new FileWriter( "descriptions.txt" ) ) );
			
			for( int j = 1; j < 10000; j++ )
			{
				out.println( ( j <= maxNumber ) ? descriptions.get( j - 1 ) : "" );
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
				currentDescription = descriptionScanner.next().replaceAll( "[\n\r\f]{2}", "<br>" ).replace( "\"", "\\\"" );
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
}