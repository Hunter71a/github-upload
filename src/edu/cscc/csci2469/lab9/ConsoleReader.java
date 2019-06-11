//===================================
// Columbus State Community College
// CSCI 2469 - Spring Semester 2019
// Assignment: Lab 9
// Programmer: Carl Olson
//===================================
package edu.cscc.csci2469.lab9;

import java.util.Scanner;

/**
 * Provides simple input from the console
 */
public class ConsoleReader
{
    /**
     * Holds instance of the scanner
     */
    private Scanner scanner;
    
    /**
     * Constructor.
     */
    public ConsoleReader()
    {
        super();
        scanner = new Scanner(System.in);
    }
    
    /**
     * Reads the port number from System.in and validate it.
     *
     * @param prompt prompt string to be used
     * 
     * @return the supplied port number (after conversion to binary)
     */
    public int getPortNumber(final String prompt)
    {
        while (true)
        {
            System.out.print(prompt);
            String input = scanner.nextLine();

            try
            {
                int portNumber = Integer.parseInt(input);
                if (portNumber < 0 || portNumber > 65535)
                {
                    System.out.println("\n** Port number must be between 0 and 65535.");
                    System.out.flush();
                }
                else
                {
                    return (portNumber);
                }
            }
            catch (NumberFormatException e)
            {
                System.out.println("\n** invalid port number");
                System.out.flush();
            }
        }
    }

    /**
     * Reads a string from System.in and returns it to the caller.
     * 
     * @param prompt prompt string to be used
     * 
     * @return string read from System.in
     */
    public String getString(final String prompt)
    {
        System.out.print(prompt);
        String input = scanner.nextLine();
        return input;
    }
}
