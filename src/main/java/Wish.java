import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Wish {
    public static void main(String[] args) {
        greet();
        getUserInput();
    }

    private static void greet() {
        System.out.println("Hello! I am Wish\n" + "What can I do for you?");
    }

    private static void getUserInput() {
        Scanner sc = new Scanner(System.in);
        ArrayList<Task> database = new ArrayList<>(100);
        String command = sc.next();

        while (!command.equals("bye")) {
            switch (command) {
                case "list":
                    list(database);
                    break;

                case "done":
                    onDoneCommand(database, sc);
                    break;

                case "todo":
                    onToDoCommand(database, sc);
                    break;

                case "deadline":
                    onDeadlineCommand(database, sc);
                    break;

                case "event":
                    onEventCommand(database, sc);
                    break;

                default:
                    System.out.println("Oops! No such command exists. Please enter a valid command");
                    break;
            }

            command = sc.next();
        }

        System.out.println("Goodbye! All the best and see you again soon!");
    }

    private static void add(ArrayList<Task> database, Task task) {
        database.add(task);
        System.out.println("Looking good! I have added this task:");
        System.out.println(task);
        System.out.println("Now you have " + database.size() + " tasks in your list");
    }

    /**
     * Prints all the tasks in the database.
     */
    private static void list(ArrayList<Task> database) {
        if (database.size() == 0) {
            System.out.println("There are no tasks in the database");
        } else {
            System.out.println("Here are the tasks in your list:");
            for (int i = 0; i < database.size(); i++) {
                System.out.println((i + 1) + ". " + database.get(i));
            }
        }
    }

    private static void onDoneCommand(ArrayList<Task> database, Scanner sc) {
        if (database.size() != 0) {
            try {
                int index = sc.nextInt();

                if (index > database.size()) {
                    throw new Exception("Index cannot be greater than size of database");
                }

                database.get(index - 1).markAsDone();
                System.out.println("Nice! I have marked this task as done!");
            } catch (InputMismatchException e) {
                System.out.println("Index must be a number");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("There are no tasks in the database to mark as done");
        }

        sc.nextLine();
    }

    private static void onToDoCommand(ArrayList<Task> database, Scanner sc) {
        String description = sc.nextLine();

        if (description.equals("")) {
            System.out.println("The description of a todo cannot be empty");
        } else {
            ToDo toDo = new ToDo(description.trim());
            add(database, toDo);
        }
    }

    private static void onDeadlineCommand(ArrayList<Task> database, Scanner sc) {
        String toParse = sc.nextLine();

        if (!toParse.equals("")) {
            try {
                String[] parsed = toParse.split("/by ");
                if (parsed.length == 1) {
                    throw new Exception("Please input the deadline in a valid format");
                } else {
                    Deadline deadline = new Deadline(parsed[0].trim(), parsed[1].trim());
                    add(database, deadline);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Task description and deadline cannot be empty");
        }
    }

    private static void onEventCommand(ArrayList<Task> database, Scanner sc) {
        String toParse = sc.nextLine();

        if (!toParse.equals("")) {
            try {
                String[] parsed = toParse.split("/at ");
                if (parsed.length == 1) {
                    throw new Exception("Please input the start and end timing in a valid format");
                } else {
                    Event event = new Event(parsed[0].trim(), parsed[1].trim());
                    add(database, event);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Task description, start time and end time cannot be empty");
        }
    }
}