package File_Handling;

import java.io.*;
import java.util.Scanner;

public class Fileops1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String continue_input = "yes";
        while (continue_input.equalsIgnoreCase("yes")) {
            System.out.println("\n--- File Operations ---");
            System.out.println("1 : Create a file ");
            System.out.println("2 : Write in a file ");
            System.out.println("3 : Read a file ");
            System.out.println("4 : Delete a file ");
            System.out.println("5 : Delete an entire directory");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    create_file();
                    break;
                case 2:
                    System.out.print("Enter the name of the file to write to (with extension): ");
                    String f_name = sc.nextLine();
                    write_infile(f_name);
                    break;
                case 3:
                    System.out.print("Enter the name of the file to read (with extension): ");
                    String fname = sc.nextLine();
                    reading_file(fname);
                    break;
                case 4:
                    System.out.print("Enter the name of the file to delete (with extension): ");
                    String filename = sc.nextLine();
                    delete_file(filename);
                    break;
                case 5:
                    System.out.print("Are you sure you want to delete a directory? Type 'yes' to confirm: ");
                    String destruction_choice = sc.nextLine();
                    if (destruction_choice.equalsIgnoreCase("yes")) {
                        System.out.print("Enter the name of the directory to delete: ");
                        String dir_name = sc.nextLine();
                        delete_directory(dir_name);
                    }
                    break;
                default:
                    System.out.println("Invalid choice!");
            }

            System.out.print("\nDo you want to perform any other file operations? (yes/no): ");
            continue_input = sc.nextLine();
        }
        sc.close();
        System.out.println("Program terminated.");
    }

    public static void reading_file(String filename) {
        try {
            String basePath = System.getProperty("user.dir");
            File file = new File(basePath + File.separator + filename);
            Scanner sc = new Scanner(file);
            System.out.println("\n--- File Content ---");
            while (sc.hasNextLine()) {
                System.out.println(sc.nextLine());
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void create_file() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the name of the file (without extension): ");
        String filename = sc.nextLine();
        System.out.print("Enter the extension of the file (e.g., txt, java): ");
        String fileextension = sc.nextLine();

        String basePath = System.getProperty("user.dir");
        File file = new File(basePath + File.separator + filename + "." + fileextension);
        try {
            if (file.createNewFile()) {
                System.out.println("File created successfully at: " + file.getAbsolutePath());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("File creation failed: " + e.getMessage());
        }
    }

    public static void write_infile(String filename) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the text you want to write in the file:");
        String material = sc.nextLine();

        String basePath = System.getProperty("user.dir");
        File file = new File(basePath + File.separator + filename);

        try {
            FileWriter fw = new FileWriter(file);
            fw.write(material);
            fw.close();
            System.out.println("Text written successfully to: " + file.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("Write failed: " + e.getMessage());
        }
    }

    public static void delete_file(String filename) {
        String basePath = System.getProperty("user.dir");
        File file = new File(basePath + File.separator + filename);
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                System.out.println("File '" + file.getName() + "' deleted successfully.");
            } else {
                System.out.println("File deletion failed.");
            }
        } else {
            System.out.println("File not found.");
        }
    }

    public static void delete_directory(String directory_name) {
        String basePath = System.getProperty("user.dir");
        File directory = new File(basePath + File.separator + directory_name);
        if (!directory.exists() || !directory.isDirectory()) {
            System.out.println("Directory not found.");
            return;
        }

        delete_recursive(directory);

        if (directory.delete()) {
            System.out.println("Directory '" + directory.getName() + "' deleted successfully.");
        } else {
            System.out.println("Failed to delete the directory. It might not be empty.");
        }
    }

    private static void delete_recursive(File file) {
        if (file.isDirectory()) {
            File[] allContents = file.listFiles();
            if (allContents != null) {
                for (File child : allContents) {
                    delete_recursive(child);
                }
            }
        }
        if (!file.delete()) {
            System.out.println("Failed to delete: " + file.getAbsolutePath());
        }
    }
}
