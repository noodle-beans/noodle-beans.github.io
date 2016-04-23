import java.io.*;
import java.util.Scanner;

public class CreateBlankDescriptions
{
	public static void main( String args[] )
	{
		System.out.println( "Are you sure you want to overwrite descriptions.txt to remove content?\n(y/n): " );
		Scanner scanner = new Scanner( System.in );
		String s = scanner.next();
		if ( !s.equals( "y" ) )
		{
			System.exit(0);
		}
		
		
		PrintWriter out = null;
		
		try
		{
			out = new PrintWriter( new BufferedWriter( new FileWriter( "descriptions.txt" ) ) );
			
			for( int j = 1; j < 10000; j++ )
			{
				out.println();
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
}