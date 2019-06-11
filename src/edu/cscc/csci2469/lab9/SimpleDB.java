//===================================
// Columbus State Community College
// CSCI 2469 - Sprint Semester 2019
// Assignment: Lab 9
// Programmer: Carl Olson
//===================================
package edu.cscc.csci2469.lab9;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.Scanner;

/**
 * Establishes a connection with a simple database and performs basic
 * CRUD operations
 */
public class SimpleDB {
    private static final ConsoleReader consoleReader = new ConsoleReader();
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static Scanner input = new Scanner(System.in);
    private static Connection connection;

    /**
     * Provides a simple switch to chose a database operation
     *
     * @param option the option string allows the user to choose a CRUD operation
     *               or quit the program
     * @throws SQLException Throws SQLException back to calling method
     */
    private static void executeOperation(String option) throws SQLException
    {
        switch (option)
        {
            case "I":
                insertRow();
                break;
            case "R":
                retrieveRow();
                break;
            case "U":
                updateRow();
                break;
            case "D":
                deleteRow();
                break;
            case "Q":
                close();
            default:
                close();
        }
    }

    /**
     * Method to delete a row specified by ID
     *
     * @throws SQLException throws SQLException back to calling method
     */
    private static void deleteRow() throws SQLException
    {
        System.out.print("Enter the ID of the record to delete: ");
        String id = input.nextLine().trim();
        String query = ("Select id, lastName, firstName, email from Info where ID = '" + id + "'");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        ResultSetMetaData metaData = resultSet.getMetaData();
        if (!resultSet.next())
        {
            System.out.println("So sorry, but I didn't find that one.");
        } else
        {
            for (int i = 1; i <= metaData.getColumnCount(); i++)
            {
                System.out.printf("%-12s\t", metaData.getColumnName(i));
            }
            System.out.println();
            resultSet.beforeFirst();
            while (resultSet.next())
            {
                for (int i = 1; i <= metaData.getColumnCount(); i++)
                {
                    System.out.printf("%-12s\t", resultSet.getObject(i));
                }
                System.out.println();
            }
            System.out.println("Delete this record permanently? (y/n) ");
            String delete = input.nextLine();
            if ("y".equalsIgnoreCase(delete))
            {
                String deleteCommand = ("Delete from Info where id  = '" + id + "'");
                statement.executeUpdate(deleteCommand);
                System.out.println("Success!");
            }
        }
    }

    /**
     * Method to insert a new row of data
     *
     * @throws SQLException throws SQLException back to the calling method
     */
    private static void insertRow() throws SQLException
    {
        String id = "testMessage";

        while (id.length() > 8)
        {
            System.out.print("Enter the ID (Must be 8 characters or less): ");
            id = input.nextLine();
        }
        System.out.println("Enter the First Name: ");
        String firstName = input.nextLine();
        System.out.print("Enter the Last Name: ");
        String lastName = input.nextLine();
        System.out.println("Enter the email address: ");
        String email = input.nextLine();

        String preparedStatementQuery = "Insert into Info (id, lastName, firstName, email) values (?, ?, ?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(preparedStatementQuery);
        preparedStatement.setString(1, id);
        preparedStatement.setString(2, lastName);
        preparedStatement.setString(3, firstName);
        preparedStatement.setString(4, email);
        preparedStatement.executeUpdate();

        System.out.println("Success!");
    }

    /**
     * Method to retrieve a row of data by ID
     *
     * @throws SQLException returns SQLException back to the calling method
     */
    private static void retrieveRow() throws SQLException
    {

        System.out.print("Enter the ID: ");
        String id = input.nextLine().trim();
        String query = ("Select id, lastName, firstName, email from Info where ID = '" + id + "'");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        ResultSetMetaData metaData = resultSet.getMetaData();
        if (!resultSet.next())
        {
            System.out.println("Sorry, but I didn't find that one.");
        } else
        {
            for (int i = 1; i <= metaData.getColumnCount(); i++)
            {
                System.out.printf("%-12s\t", metaData.getColumnName(i));
            }
            System.out.println();
            resultSet.beforeFirst();
            while (resultSet.next())
            {
                for (int i = 1; i <= metaData.getColumnCount(); i++)
                {
                    System.out.printf("%-12s\t", resultSet.getObject(i));
                }
                System.out.println();
            }
        }
    }

    /**
     * Method to get single character input from user
     *
     * @return String choice to switch method for selecting crud operation or to quit
     */
    private static String requestOperation()
    {
        boolean done = false;
        String choice = "";
        while (!done)
        {
            System.out.println("\nPlease enter a letter to select the database operation you wish to perform:");
            System.out.println("I -- insert\n" +
                    "R -- retrieve\n" +
                    "U -- update\n" +
                    "D -- delete\n" +
                    "Q -- quit");
            choice = input.nextLine().toUpperCase();
            if (!"IRUDQ".contains(choice) || choice.length() != 1)
            {
                System.out.println("Please enter an acceptable input.");
                requestOperation();
            } else
            {
                done = true;
            }
        }
        return choice;
    }

    /**
     * Method to perform update operations on an existing row of data
     *
     * @throws SQLException passes SQLException back to the calling method
     */
    private static void updateRow() throws SQLException
    {
        System.out.print("Enter the ID of the record to update: ");
        String id = input.nextLine().trim();
        String query = ("Select id, lastName, firstName, email from Info where ID = '" + id + "'");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        ResultSetMetaData metaData = resultSet.getMetaData();
        if (!resultSet.next())
        {
            System.out.println("So sorry, but I didn't find that one.");
        } else
        {
            for (int i = 1; i <= metaData.getColumnCount(); i++)
            {
                System.out.printf("%-12s\t", metaData.getColumnName(i));
            }
            System.out.println();
            resultSet.beforeFirst();
            while (resultSet.next())
            {
                for (int i = 1; i <= metaData.getColumnCount(); i++)
                {
                    System.out.printf("%-12s\t", resultSet.getObject(i));
                }
                System.out.println();
            }
            System.out.println("Enter the new first name or hit enter to keep the existing field: ");
            String firstName = input.nextLine();
            if (!"".equals(firstName))
            {
                String updateCommand = ("Update Info " + "set firstName =  '" + firstName + "' where id = '" + id + "'");
                statement.executeUpdate(updateCommand);
            }

            System.out.print("Enter the new last name or hit enter to keep the existing field: ");
            String lastName = input.nextLine();
            if (!"".equals(lastName))
            {
                String updateCommand = ("Update Info " + "set lastName =  '" + lastName + "' where id = '" + id + "'");
                statement.executeUpdate(updateCommand);
            }

            System.out.println("Enter the new email address or hit enter to keep the existing field: ");
            String email = input.nextLine();
            if (!"".equals(email))
            {
                String updateCommand = ("Update Info " + "set email =  '" + email + "' where id = '" + id + "'");
                statement.executeUpdate(updateCommand);
            }
            System.out.println("Success!");
        }
    }

    /**
     * Method to close the connection and terminate the program
     */
    private static void close()
    {
        try
        {
            connection.close();
            System.out.println("Closing connection and terminating program by user request");
            System.exit(0);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Method getConnectionBean collects user input or falls back on default settings to assemble
     * all data necessary for establishing a connection to a database
     *
     * @return object of type ConnectionBean back to the main method to establish database connection
     */
    private static ConnectionBean getConnectionBean()
    {

        ConnectionBean connectionBean = new ConnectionBean();
        System.out.print("\nHit enter to default to \"localhost\" or enter for a custom IP or URL: ");
        String response = input.nextLine();
        if (!"".equals(response))
        {
            connectionBean.setDbURL(response);
        }
        System.out.print("Hit enter to use default server port 3306, \nor press any key and hit enter to specify a custom port:  ");
        response = input.nextLine();
        if (!"".equals(response))
        {
            connectionBean.setPort(consoleReader.getPortNumber("Enter the server port: "));
        }
        System.out.print("Hit enter to use default database name of \"simpleDB\" " +
                "or press any key and hit enter update the database name: ");
        response = input.nextLine();
        if (!"".equals(response))
        {
            connectionBean.setDbName(response);
        }
        System.out.print("Hit enter to default to root user or enter user name: ");
        response = input.nextLine();
        if (!"".equals(response))
        {
            connectionBean.setDbUser(response);
        }
        connectionBean.setUserPassword(consoleReader.getString("Enter User Password: "));
        return connectionBean;
    }


    /**
     * Main method for SimpleDB
     *
     * @param args Command like arguments for server settings
     * @throws ClassNotFoundException If database driver not found throw exception
     * @throws SQLException           SQL exception
     */
    public static void main(String[] args) throws ClassNotFoundException, SQLException
    {
        Class.forName(DRIVER);
        ConnectionBean connectionBean;
        connectionBean = getConnectionBean();
        connection = DriverManager.getConnection("jdbc:mysql://" + connectionBean.getDbURL() + ":" + connectionBean.getPort() + "/" + connectionBean.getDbName() + "?useSSL=false", connectionBean.getDbUser(), connectionBean.getUserPassword());


        while (true)
        {
            executeOperation(requestOperation());
        }
    }

    /**
     * Bean for storing connection information
     */
    private static class ConnectionBean {
        private int port = 3306;
        private String dbURL = "localhost";
        private String dbName = "simpleDB";
        private String dbUser = "root";
        private String userPassword = "";

        private int getPort()
        {
            return port;
        }

        private void setPort(int port)
        {
            this.port = port;
        }

        private String getDbURL()
        {
            return dbURL;
        }

        private void setDbURL(String dbURL)
        {
            this.dbURL = dbURL;
        }

        private String getDbName()
        {
            return dbName;
        }

        private void setDbName(String dbName)
        {
            this.dbName = dbName;
        }

        private String getDbUser()
        {
            return dbUser;
        }

        private void setDbUser(String dbUser)
        {
            this.dbUser = dbUser;
        }

        private String getUserPassword()
        {
            return userPassword;
        }

        private void setUserPassword(String userPassword)
        {
            this.userPassword = userPassword;
        }
    }
}
