/**
 * Created by 114512 on 4/24/2018.
 */
//Ean McFadden
//April 24, 2018
//Project 4
import java.util.*;
import java.io.*;
import com.google.gson.Gson;
class AddTask implements Comparable<AddTask>{
    String taskTitle;
    String taskDescription;
    int taskPriority;
    public AddTask(String taskTitle, String taskDescription, int taskPriority ){
        this.taskTitle = taskTitle;
        this.taskDescription = taskDescription;
        this.taskPriority = taskPriority;
    }
    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public int getTaskPriority() {
        return taskPriority;
    }

    public void setTaskPriority(int taskPriority) {
        this.taskPriority = taskPriority;
    }
    public void taskDisplay(){
        System.out.println("Title: " + this.taskTitle + "\nDescription: " + this.taskDescription + "\nPriority: " + this.taskPriority);
    }
    public String toString(){
        return String.format("Title: %s \nDescription: %s \nPriority: %s", taskTitle, taskDescription, taskPriority);
    }
    @Override
    public int compareTo(AddTask o) {
        if (taskPriority != o.taskPriority) {
            return Integer.toString(taskPriority).compareTo(Integer.toString(o.taskPriority));
        }
        else {
            return taskTitle.compareTo(o.taskTitle);
        }
    }
}
class TaskCollection implements Iterable<AddTask>{
    ArrayList<AddTask> tasks = new ArrayList<>();
    public TaskCollection(ArrayList<AddTask> tasks){
        this.tasks = tasks;
    }
    @Override
    public Iterator iterator() {
        return tasks.iterator();
    }
}
public class Main {
    static final String FILENAME = "task.json";
    public static TaskCollection load(String filename) throws IOException{
        Gson gson = new Gson();
        FileReader reader = new FileReader(filename);
        try{
            return gson.fromJson(reader, TaskCollection.class);
        }
        finally{
            reader.close();
        }
    }
    public static void save(String filename, TaskCollection data) throws IOException{
        Gson gson = new Gson();
        FileWriter writer = new FileWriter(filename);
        try{
            gson.toJson(data, writer);
        }
        finally{
            writer.close();
        }
    }
    public static void main(String[] args) {
        try {
            TaskCollection taskList = load(FILENAME);
            ArrayList<AddTask> tasks = new ArrayList<>();
            for (AddTask task: taskList){
                tasks.add(task);
            }
            Scanner input = new Scanner(System.in);
            System.out.println("Please choose an option:\n(1) Add a task.\n(2) Remove a task.\n(3) Update a task.\n(4) List tasks.\n(0) Exit.");
            int choice = input.nextInt();
            while (choice != 0) {
                if (choice == 1) {
                    AddTask task = createTask();
                    tasks.add(task);
                } else if (choice == 2) {
                    String title = remove();
                    tasks.removeIf(task -> task.getTaskTitle().equals(title));

                } else if (choice == 3) {
                    String title = changeTitle();
                    tasks.removeIf(task -> task.getTaskTitle().equals(title));
                    AddTask task = createTask();
                    tasks.add(task);
                } else if (choice == 4) {
                    priorityDisplay(tasks);
                } else {
                    System.out.println("You have entered an incorrect number");
                }
                System.out.println("Please choose an option:\n(1) Add a task.\n(2) Remove a task.\n(3) Update a task.\n(4) List tasks.\n(0) Exit.");
                choice = input.nextInt();
            }
            TaskCollection taskList2 = new TaskCollection(tasks);
            save(FILENAME, taskList2);
            System.out.println("Thank you for using the task list.");
        } catch(IOException e){
            e.printStackTrace();
        }
        catch (InputMismatchException e) {
            System.out.println("That was an incorrect data entry. The list selection and priorities can only be integer data types. Please restart the program!");
        } catch (Exception e) {
            //System.out.println("An unknown error has occurred. Please restart the program.");
        }
    }

    static String addTitle() {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the title of the task you wish to add");
        String title = input.nextLine();
        return title;
    }

    static String addDescription() {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the description of the task you wish to add");
        String description = input.nextLine();
        return description;
    }

    static int addPriority() {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the priority of the task you wish to add from 0-5. 0 being least important and 5 being most important.");
        int priority = input.nextInt();
        return priority;
    }

    static AddTask createTask() {
        String title = addTitle();
        String description = addDescription();
        int priority = addPriority();
        int i = 0;
        i++;
        AddTask task = new AddTask(title, description, priority);
        return task;
    }

    static String remove() {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the title of the task you wish to remove");
        String title = input.nextLine();
        return title;
    }

    static String changeTitle() {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the name of the task you wish to change");
        String title = input.nextLine();
        return title;
    }

    static String priorityInput() {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the priority of the tasks you wish to look up. Enter 'all' to list all priorities");
        String priorityLookup = input.nextLine();
        return priorityLookup;
    }

    static void priorityDisplay(ArrayList<AddTask> tasks) {
        String priorityLookup = priorityInput();
        Collections.sort(tasks);
        TaskCollection taskList = new TaskCollection(tasks);
        if (priorityLookup.equals("all")) {
            for (AddTask task : taskList) {
                task.taskDisplay();
            }
        } else {
            int prilook = Integer.parseInt(priorityLookup);
            for (AddTask task : taskList) {
                if (task.getTaskPriority() == prilook) {
                    task.taskDisplay();
                }
            }
        }
    }
}
