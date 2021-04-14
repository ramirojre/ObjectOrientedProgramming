/*Name: Question_1
 * 
 * Description: This program reads and writes to object files with extension
 * .bin. A user is prompted to Add info to a file, Display info from a file,
 * delete information, update information, or exit the program.
 * 
 * Author: Ramiro Esquivel Jr.
 * 
 * Course: CPSC 24500
 * 
 * Section: 002
 * 
 * Instructor: Shamsuddin
 * 
 * Date: 4/4/2021
 * 
 */

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;
import static java.util.Map.entry;
import java.util.ArrayList;

/**
 * Serialize class is used to create serializable objects 
 * @author Ramiro Esquivel Jr
 *
 */
class Serialize implements Serializable {
	
	private static final long serialVersionUID = 1L;
	String name;
	int[] phoneNumbers;
	Map<String, Integer> DOB = new HashMap<String, Integer>();
	String email;
	
	Serialize(String name, int[] arr, Map<String, Integer> map, String email) {
		
		this.name = name;
		phoneNumbers = arr;
		DOB = map;
		this.email = email;
	}
	
	public void setName(String newName) {
		
		name = newName;
	}
	
	public void setPhoneNumbers(int[] newNumberList) {
		
		phoneNumbers = newNumberList;
	}
	
	public void setDOB(Map<String, Integer> newDOB) {
		
		DOB = newDOB;
	}
	
	public void setEmail(String newEmail) {
		
		email = newEmail;
	}
	
	public String getName() {
		
		return name;
	}
	
	public int[] getPhoneNumbers() {
		
		return phoneNumbers;
	}
	
	public Map<String, Integer> getDOB() {
		
		return DOB;
	}
	
	public String getEmail() {
		
		return email;
	}
	
	/**
	 * Used to return the int[] containing the phone numbers as a String for
	 * the toString() method.
	 * 
	 * @param phoneNumbers
	 * @return
	 */
	private String phoneNumbersToString(int[] phoneNumbers) {
		
		String arr = "[";
		
		for (int x = 0; x < phoneNumbers.length; x++) {
			
			if (x == phoneNumbers.length - 1) {
				
				arr += phoneNumbers[x] + "]";
			}
			
			else {
				arr += phoneNumbers[x] + ", ";
			}
		}
		
		return arr;
	}
	
	/**
	 * Used to  return the dictionary containing the DOB as a String for the
	 * toString() method.
	 * 
	 * @param dict2
	 * @return
	 */
	private String DOBToString(Map<String, Integer> dict2) {
		
		String dictionary = "{";
		
		for (Map.Entry<String, Integer> entry : dict2.entrySet()) {
			
			dictionary += entry.getKey() + " : " + entry.getValue() + ", ";
		}
		
		dictionary = dictionary.substring(0, dictionary.length() - 2);
		dictionary += "}";
		
		return dictionary;
	}
	
	public String toString() {
		
		return "Name: " + name + "\n" +
			   "Phone Numbers: " + phoneNumbersToString(phoneNumbers) + "\n" +
			   "DOB List: " + DOBToString(DOB) + "\n" +
			   "Email: " + email + "\n";
	}
}

/**
 * Main Driver class that runs the Menu-driven program
 * @author Ramiro Esquivel Jr
 *
 */
public class Homework11 {
	
	public static void main(String[] args) {
		
		ArrayList<Serialize> list = new ArrayList<>();
		
		Scanner scan = new Scanner(System.in);
		
		boolean runtime = true;
		
		// Used to capture user input when specifying specific entry in list
		int entryChoice;
		int size;
		
		// Used to capture input necessary to create a new Serialize object
		String filename = "";
		String name = "";
		int phoneNumberAmount = 0;
		int[] numbers;
		Map<String, Integer> dictionary;
		String DOB = "";
		String email = "";
		
		// Used to validate user choice to create a new file or not.
		char yn = ' ';
		
		while(runtime) {
			
			System.out.println("Please choose an option below:");
			System.out.println("1: Add information into a file.");
			System.out.println("2: Retrieve information from file and " + 
							   "display it.");
			System.out.println("3: Delete information.");
			System.out.println("4: Update information.");
			System.out.println("5: Exit");
			
			int userChoice = validateChoice();
			
			switch (userChoice) {
			
			// User chose to add information
			case 1:
				
				System.out.println("Please enter the filename.");
				filename = scan.nextLine() + ".bin";
				System.out.println();
				
				try {
					
					list = storeFileContents(filename);
				}
				
				catch(ClassNotFoundException e) {
					
					e.printStackTrace();
					
					break;
				}
				
				catch (FileNotFoundException e) {
					
					System.out.println("The file entered could not be found.");
					System.out.println("Create new file using " + filename +
									   " as the name? y/n");
					
					yn = validateFileCreation();
					
					if (yn == 'y') {
						
						createFile(filename);
					}
					
					break;
				}
				
				catch (EOFException e) {
					
					
				}
				
				catch (IOException e) {
					
					e.printStackTrace();
					
					break;
				}
				
				System.out.println("Please enter the name: ");
				
				name = scan.nextLine();
				
				System.out.println("Please enter the amount of numbers to " +
								   "add:");
				
				phoneNumberAmount = validateIntInput();
				
				numbers = new int[phoneNumberAmount];
				
				for (int x = 0; x < phoneNumberAmount; x++) {
					
					System.out.println("Please enter #" + (x + 1));
					
					numbers[x] = validateIntInput();
				}
				
				System.out.print("Please enter the DOB in the format of (" + 
				 		 		 "mm/dd/yyyy): ");
				
				DOB = checkDOB(scan.next());
				scan.nextLine();
				
				dictionary = createDictionary(DOB);
				
				System.out.println("Please enter the email");
				
				email = scan.next();
				scan.nextLine();
				
				list.add(new Serialize(name, numbers, dictionary, email));
				
				try {
					
					writeFile(list, filename);
				} 
				
				catch (IOException e1) {
					
					e1.printStackTrace();
					
					break;
				}
				
				break;
				
			// User chose to Retrieve Information from the file
			case 2:
				
				System.out.println("Please enter the filename.");
				filename = scan.nextLine() + ".bin";
				System.out.println();
				
				
				try {
					
					System.out.println("Current file contents:\n");
					
					
					list = storeFileContents(filename);
					
					if (list.size() == 0) {
						
						System.out.println("**The specified file is empty.");
						System.out.println("Please add information to it " + 
										   "first.\n");
						
						break;
					}
					
					readFile(filename);
				} 
				
				catch (ClassNotFoundException e) {
					
					e.printStackTrace();
					
					break;
				}
				
				catch (FileNotFoundException e) {
					
					System.out.println("The file entered could not be found.");
					System.out.println("Create new file using " + filename +
									   " as the name? y/n");
					
					yn = validateFileCreation();
					
					if (yn == 'y') {
						
						createFile(filename);
					}
					
					break;
				}
				
				catch (EOFException e) {
					
					System.out.println("**The specified file is empty.");
					System.out.println("Please add information to it first.\n");
					
					break;
				}
				
				catch (IOException e) {
					
					e.printStackTrace();
					
					break;
				}
				
				break;
				
			// User chose to delete information from the file
			case 3:
				
				System.out.println("Please enter the filename to edit its " +
								   "contents.");
				filename = scan.nextLine() + ".bin";
				System.out.println();
				
				try {
					
					list = storeFileContents(filename);
					
					if (list.size() == 0) {
						
						System.out.println("**The specified file is empty.");
						System.out.println("Please add information to it " + 
										   "first.\n");
						
						break;
					}
					
					readFile(filename);
				} 
				
				catch (ClassNotFoundException e) {
					
					e.printStackTrace();
					
					break;
				}
				
				catch (FileNotFoundException e) {
					
					System.out.println("The file entered could not be found.");
					
					break;
				}
				
				catch (EOFException e) {
					
					System.out.println("**The specified file is empty.");
					System.out.println("Please add information to it first.\n");
					
					break;
				}
				
				catch (IOException e) {
					
					e.printStackTrace();
					
					break;
				}
				
				System.out.println("Which entry would you like to delete?");
				
				size = list.size();
				
				entryChoice = validateEntry(size);
				
				list.remove((entryChoice - 1));
				
				try {
					
					writeFile(list, filename);
				} 
				
				catch (IOException e) {
					
					e.printStackTrace();
					
					break;
				}
				
				break;
				
			// User chose to update information of one of the file contents.
			case 4:
				
				System.out.println("Please enter the filename to update its " +
						   		   "contents.");
				filename = scan.nextLine() + ".bin";
				System.out.println();
				
				try {
					
					list = storeFileContents(filename);
					
					if (list.size() == 0) {
						
						System.out.println("**The specified file is empty.");
						System.out.println("Please add information to it " + 
										   "first.\n");
						
						break;
					}
					
					readFile(filename);
				} 
				
				catch (ClassNotFoundException e) {
					
					e.printStackTrace();
					
					break;
				}
				
				catch (FileNotFoundException e) {
					
					System.out.println("The file entered could not be found.");
					System.out.println("Create new file using " + filename +
									   " as the name? y/n");
					
					yn = validateFileCreation();
					
					if (yn == 'y') {
						
						createFile(filename);
					}
					
					break;
				}
				
				catch (EOFException e) {
					
					System.out.println("**The specified file is empty.");
					System.out.println("Please add information to it first.\n");
					
					break;
				}
				
				catch  (IOException e) {
					
					e.printStackTrace();
					
					break;
				}
				
				System.out.println("Which entry would you like to update?");
				
				size = list.size();
				
				entryChoice = validateEntry(size);
				
				System.out.println("Which information would you like to " + 
								   "update?");
				
				System.out.println("1: Name");
				System.out.println("2: Phone Numbers");
				System.out.println("3: DOB");
				System.out.println("4: Email");
				
				size = 4;
				int updateChoice = validateEntry(size);
				
				switch (updateChoice) {
				
				// User chose to update the name
				case 1:
					
					System.out.println("Please enter the new Name:");
					
					name = scan.nextLine();
					
					list.get((entryChoice - 1)).setName(name);
					
					try {
						
						writeFile(list, filename);
					} 
					
					catch (IOException e) {
						
						e.printStackTrace();
					}
					
					break;
					
				// User chose to update the phone numbers
				case 2:
					
					System.out.println("Please re-enter the total amount of " +
									   "numbers:");
					
					phoneNumberAmount = validateIntInput();
					
					System.out.println("Please re-enter in the numbers:");
					
					numbers = new int[phoneNumberAmount];
					
					for (int x = 0; x < phoneNumberAmount; x++) {
						
						System.out.println("Please enter #" + (x + 1));
						
						numbers[x] = validateIntInput();
					}
					
					list.get((entryChoice - 1)).setPhoneNumbers(numbers);
					
					try {
						
						writeFile(list, filename);
					} 
					
					catch (IOException e) {
						
						e.printStackTrace();
					}
					
					break;
					
				// User chose to update the DOB
				case 3:
					
					System.out.print("Please re-enter the DOB in the format " + 
			 		 		 "of (mm/dd/yyyy): ");
			
					DOB = checkDOB(scan.next());
					scan.nextLine();
			
					dictionary = createDictionary(DOB);
					
					list.get((entryChoice - 1)).setDOB(dictionary);
					
					try {
						
						writeFile(list, filename);
					} 
					
					catch (IOException e) {
						
						e.printStackTrace();
					}
					
					break;
					
				// User chose to update the Email
				case 4:
					
					System.out.println("Please re-enter the email:");
					
					email = scan.nextLine();
					
					list.get((entryChoice - 1)).setEmail(email);
					
					try {
						
						writeFile(list, filename);
					} 
					
					catch (IOException e) {
						
						e.printStackTrace();
					}
					
					break;
				}
				
				break;
				
			// User chose to exit
			case 5:
				
				scan.close();
				System.exit(0);
				break;
			}
		}
	}
	
	/**
	 * Method used to check to validate the input of the user when they are
	 * choosing to create a new file.
	 * @return
	 */
	public static char validateFileCreation() {
		
		boolean valid = false;
		
		char value = ' ';
		
		Scanner scan = new Scanner(System.in);
		
		while (!valid) {
			
			try {
				
				value = scan.next().charAt(0);
				
				if (Character.valueOf(value).compareTo(
					Character.valueOf('y')) != 0 &&
					Character.valueOf(value).compareTo(
					Character.valueOf('Y')) != 0 &&
					Character.valueOf(value).compareTo(
					Character.valueOf('n')) != 0 &&
					Character.valueOf(value).compareTo(
					Character.valueOf('N')) != 0) {
					
					System.out.println("Invalid option. Please re-enter.");
				
				}
				
				else {
					
					valid = true;
				}
			}
			
			catch (InputMismatchException e) {
				
				System.out.println("Invalid option. Please re-enter.");
				scan.next();
			}
		}
		
		System.out.println();
		
		return value;
	}
	
	/**
	 * This method creates a new file using a filename specified by the user.
	 * Warns the user when the File could not be created.
	 * @param filename
	 */
	public static void createFile(String filename) {
		
		try {
			
			File newFile = new File(filename);
			newFile.createNewFile();
		}
		
		catch (IOException e) {
			
			System.out.println("File could not be created.");
		}
	}
	
	/**
	 * Used to validate the initial selection from the list of options offered
	 * in the beginning of the program.
	 * @return
	 */
	public static int validateChoice() {
		
		boolean valid = false;
		
		int value = 0;
		
		Scanner scan = new Scanner(System.in);
		
		while (!valid) {
			
			try {
				
				value = scan.nextInt();
				
				if (value < 0 || value > 5) {
					
					System.out.println("Invalid option. Please re-enter.");
				}
				
				else {
					
					valid = true;
				}
			}
			
			catch (InputMismatchException e) {
				
				System.out.println("Invalid option. Please re-enter.");
				scan.next();
			}
		}
		
		System.out.println();
		
		return value;
	}
	
	/**
	 * Used to ensure that when general numeric values are required, the user
	 * has entered them correctly. 
	 * @return
	 */
	public static int validateIntInput() {
		
		boolean valid = false;
		
		int value = 0;
		
		Scanner scan = new Scanner(System.in);
		
		while (!valid) {
			
			try {
				
				value = scan.nextInt();
				
				if (value < 0) {
					
					System.out.println("Invalid amount. Please re-enter.");
				}
				
				else {
					
					valid = true;
				}
			}
			
			catch (InputMismatchException e) {
				
				System.out.println("Invalid amount. Please re-enter.");
				scan.next();
			}
		}
		
		System.out.println();
		
		return value;
	}
	
	/**
	 * Used within the switch cases to validate the object choice the user
	 * wishes to select when deleting or updating information.
	 * @param size
	 * @return
	 */
	public static int validateEntry(int size) {
		
		boolean valid = false;
		
		int value = 0;
		
		Scanner scan = new Scanner(System.in);
		
		while (!valid) {
			
			try {
				
				value = scan.nextInt();
				
				if (value < 0 || value > size) {
					
					System.out.println("Invalid entry. Please re-enter.");
				}
				
				else {
					
					valid = true;
				}
			}
			
			catch (InputMismatchException e) {
				
				System.out.println("Invalid entry. Please re-enter.");
				scan.next();
			}
		}
		
		System.out.println();
		
		return value;
	}
	
	/**
	 * Ensures that the DOB entered by the use is correct.
	 * @param DOB
	 * @return
	 */
	public static String checkDOB(String DOB) {
		
		String test = DOB;
		
		boolean valid = false;
		
		int month = 0;
		
		int day = 0;
		
		int year = 0;
		
		Scanner scan = new Scanner(System.in);
		
		
		int date = Integer.parseInt(LocalDateTime.now().toString(
									).substring(0, 4));
		
		while (!valid) {
			
			try {
				
				month = Integer.parseInt(test.substring(0, 2));
				
				day = Integer.parseInt(test.substring(3, 5));
				
				year = Integer.parseInt(test.substring(6, 10));
				
				if (month < 1 || month > 12) {
					
					System.out.print("Invalid DOB. Please enter the DOB in " + 
									 "the format of (mm/dd/yyyy):");
					
					test = scan.next();
				}
				
				else if (year < 1900 || year > date) {
					
					System.out.print("Invalid DOB. Please enter the DOB in " + 
							   		 "the format of (mm/dd/yyyy):");
					
					test = scan.next();
				}
				
				else if (day < 1 || day > 31) {
					
					System.out.print("Invalid DOB. Please enter the DOB in " + 
							   		 "the format of (mm/dd/yyyy):");
					
					test = scan.next();
				}
				
				else {
					
					valid = true;
				}
			}
			
			catch (Exception e) {
				
				System.out.print("Invalid DOB. Please enter the DOB in the " + 
								   "format of (mm/dd/yyyy):");
				
				test = scan.next();
			}
		}
		
		valid = false;
		
		while (!valid ) {
			
			// Checks day to see if it is valid for the month entered by user
			switch (month) {
			
			// January
			case 1:
				
				if (day > 31) {
					
					System.out.println("Invalid day entered for January." + 
									   "Please re-enter.");
					
					day = checkDay();
					
					break;
				}
			
			// February
			case 2:
				
				if (year % 4 == 0 && day > 29) {
					
					System.out.println("Invalid day entered for February." + 
									   "Please re-enter.");
					
					day = checkDay();
					
					break;
				}
				
				else if (day > 28) {
					
					System.out.println("Invalid day entered for February." + 
							   		   "Please re-enter.");
					
					day = checkDay();
					
					break;
				}
				
			// March
			case 3:
				
				if (day > 31) {
					
					System.out.println("Invalid day entered for March." + 
							   		   "Please re-enter.");
					
					day = checkDay();
					
					break;
				}
				
			// April
			case 4:
				
				if (day > 30) {
					
					System.out.println("Invalid day entered for April." + 
							   		   "Please re-enter.");
					
					day = checkDay();
					
					break;
				}
			
			// May
			case 5:
				
				if (day > 31) {
					
					System.out.println("Invalid day entered for May." + 
							   		   "Please re-enter.");
					
					day = checkDay();
					
					break;
				}
				
			// June
			case 6:
				
				if (day > 30) {
					
					System.out.println("Invalid day entered for June." + 
							   		   "Please re-enter.");
					
					day = checkDay();
					
					break;
				}
				
			// July
			case 7:
				
				if (day > 31) {
					
					System.out.println("Invalid day entered for July." + 
								 	   "Please re-enter.");
					
					day = checkDay();
					
					break;
				}
				
			// August
			case 8:
				
				if (day > 31) {
					
					System.out.println("Invalid day entered for August." + 
									   "Please re-enter.");
					
					day = checkDay();
					
					break;
				}
				
			// September
			case 9:
				
				if (day > 30) {
					
					System.out.println("Invalid day entered for September." + 
							   		   "Please re-enter.");
					
					day = checkDay();
					
					break;
				}
				
			// October
			case 10:
				
				if (day > 31) {
					
					System.out.println("Invalid day entered for October." + 
									   "Please re-enter.");
					
					day = checkDay();
					
					break;
				}
				
			// November
			case 11:
				
				if (day > 30) {
					
					System.out.println("Invalid day entered for November." + 
							   		   "Please re-enter.");
					
					day = checkDay();
					
					break;
				}
				
			// December
			case 12:
				
				if (day > 31) {
					
					System.out.println("Invalid day entered for December." + 
							   		   "Please re-enter.");
					
					day = checkDay();
					
					break;
				}
				
			default:
				
				valid = true;
			}
		}
		
		// Returns the validated DOB in format of mm/dd/yyyy
		return String.format("%02d", month) + "/" + String.format("%02d", day) + 
			   "/" + year;
	}
	
	/**
	 * Validates that the day entered by the user is valid for when the DOB is
	 * being verified.
	 */
	public static int checkDay() {
		
		int day = 0;
		
		boolean valid = false;
		
		Scanner scan = new Scanner(System.in);
		
		while (!valid) {
		
			try {
			
				day = Integer.parseInt(scan.next());
				
				if (day < 1 || day > 31) {
					
					System.out.println("Invalid day entered, please re-enter.");
				}
				
				else {
					
					valid = true;
				}
			}
		
			catch (Exception e) {
				
				System.out.println("Invalid day entered, please re-enter.");
				scan.next();
			}
		}
		
		return day;
	}
	
	/**
	 * Creates a new Map that contains the DOB.
	 * D - the Day
	 * M - the Month
	 * Y - the Year
	 * @param DOB
	 * @return
	 */
	public static Map<String, Integer> createDictionary(String DOB) {
		
		Map<String, Integer> dict;
		
		int month = Integer.parseInt(DOB.substring(0, 2));
		
		int day = Integer.parseInt(DOB.substring(3, 5));
		
		int year = Integer.parseInt(DOB.substring(6, 10));
		
		dict = Map.ofEntries(entry("D", day),
				 			 entry("M", month),
				 			 entry("Y", year));
		
		return dict;
	}
	
	/**
	 * Used to write out the contents of the ArrayList list to a specified
	 * object file.
	 * @param s
	 * @param filename
	 * @throws IOException
	 */
	public static void writeFile(ArrayList<Serialize> s, String filename) throws 
																   IOException {
		
		try {
			
			FileOutputStream file = new FileOutputStream(filename);
			ObjectOutputStream output = new ObjectOutputStream(file);
			output.writeObject(s);
		} 
		
		catch (FileNotFoundException e) {
			
		}
	}
	
	/**
	 * Used to read the contents of an object file into the ArrayList list in
	 * memory, so that access to specific elements is more efficient.
	 * @param filename
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static ArrayList<Serialize> storeFileContents(String filename) throws 
									   IOException, ClassNotFoundException {
		
		try {
			
			FileInputStream inputStream = new FileInputStream(filename);
			ObjectInputStream outputStream = new ObjectInputStream(inputStream);
			
			ArrayList<Serialize> tmpList = new ArrayList<>();
			
			tmpList = (ArrayList<Serialize>)outputStream.readObject();
			
			return tmpList;
		}
		
		finally {
			
		}
	}
	
	/**
	 * Used to print the contents of every object within the object file.
	 * @param filename
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static void readFile(String filename) throws IOException, 
												 ClassNotFoundException {
		
		try {
			
			FileInputStream inputStream = new FileInputStream(filename);
			ObjectInputStream outputStream = new ObjectInputStream(inputStream);
			
			ArrayList<Serialize> cereal = new ArrayList<>();
			
			cereal = (ArrayList<Serialize>)outputStream.readObject();
			
			for(int x = 0; x < cereal.size(); x++){
				
				System.out.println("Entry " + (x + 1) + ":");
				System.out.println(cereal.get(x));
			}
		}
		
		finally {
			
		}
	}
}
