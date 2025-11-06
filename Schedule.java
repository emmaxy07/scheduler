import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Pattern;



public class Schedule {
    static ArrayList<String> newSchedules = new ArrayList<>();

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(System.in);
        LocalDate todayDate = LocalDate.now();
        String futureDate;
        LocalTime time;
        LocalDate parsedFutureDate;
        
        System.out.println("Scheduler Application");

        String fullName = "";
        String emailInput = "";
        String email = "";
        String bgInput = "";
        String needsInput = "";
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        String timeInput = "";
        String filePath = "schedules.csv";
        File file = new File(filePath);
        String line = "";
        String[] row;
        String formattedDate;
        String formattedTime;
        ArrayList<String> newSchedule = new ArrayList<>();
        ArrayList<String> schedules = new ArrayList<>();
        int ID = 1;
        String userInput = "";
        String deleteInput = "";
        String headers = "ID,Full Name,Email,Background,Need,ScheduledDate,ScheduledTime";



        if(file.exists()){
            try(BufferedReader reader = new BufferedReader(new FileReader(file))) {
                while ((line = reader.readLine()) != null) {
                    if(line.startsWith("ID")){
                        continue;
                    }
                    row = line.split(",");
                    schedules.addAll(Arrays.asList(row)); 
                }
            } catch(IOException e){
                System.out.println("Something went wrong");
            }
        }

        newSchedules.addAll(schedules);

        while(true){
            System.out.print("What do you want to do?: ");
            userInput = scanner.nextLine();
            if(userInput.equals("schedule-cli list")){
                listSchedules(newSchedules);
            } else if(userInput.equals("schedule-cli add")){
                do{
                    System.out.print("What is your full name?: ");
                    // fullName = scanner.nextLine();
                    String inputName = scanner.nextLine();
        
                        while(true){
                            System.out.print("What is your email address?: ");
                            emailInput = scanner.nextLine();
                            if(emailInput != null && pattern.matcher(emailInput).matches()){
                                email = emailInput;
                                break;
                            } else {
                                System.out.println("Your email: " + emailInput + " , is not correct. Try again");
                            }
                        }
        
        
                    System.out.print("What is your professional background?: ");
                    bgInput = scanner.nextLine();
        
                    System.out.print("Tell us how we can help you today: ");
                    needsInput = scanner.nextLine();
        
                    // add validation of date input
                    while(true){
                        System.out.print("What date do you want to pick?: ");
                        futureDate = scanner.nextLine();
                        parsedFutureDate = LocalDate.parse(futureDate);
                        if(todayDate.isBefore(parsedFutureDate)){
                            break;
                        } else {
                            System.out.println("You chose a date that has been passed. Try a future date.");
                        }
                    }
        
                    // add validation of time input
                    while (true) {   
                        System.out.print("What time do you want to pick between 8am and 5pm?: ");
                        timeInput = scanner.nextLine();
                        time = LocalTime.parse(timeInput);
                        String openingHrs = "08:00:00";
                        String closingHrs = "17:00:00";
                        LocalTime startTime = LocalTime.parse(openingHrs);
                        LocalTime endTime = LocalTime.parse(closingHrs);
            
                        if(time.isAfter(startTime) && time.isBefore(endTime)){
                            break;
                        } else {
                            System.out.println("You selected a time outside our opening hours. Try again");
                        }
                    }  
        
                    formattedDate = parsedFutureDate.toString();
                    formattedTime = time.toString();

                    if(newSchedules.size() == 0){
                        ID = 1;
                    } else {
                            ID = Integer.parseInt(newSchedules.get(newSchedules.size() - 7));
                            ID++;
                    }
    //  ScheduleRecord scheduleRecord = new ScheduleRecord(ID, fullName, emailInput, email, bgInput, needsInput, timeInput);
    //  newSchedules.add(scheduleRecord);

                
                    newSchedule.addFirst(Integer.toString(ID));
                    newSchedule.add(fullName.trim());
                    newSchedule.add(email.trim());
                    newSchedule.add(bgInput.trim());
                    newSchedule.add(needsInput.trim());
                    newSchedule.add(formattedDate.trim());
                    newSchedule.add(formattedTime.trim());
        
                    for(String s: newSchedule){
                    System.out.println(s);
                }
        
                    newSchedules.addAll(newSchedule);
                    fullName = "";
                    email = "";
                    bgInput = "";
                    needsInput = "";
                    formattedDate = "";
                    formattedTime = "";
        
                        if(file.length() == 0){
                            try(FileWriter writer = new FileWriter(file)) {
                            writer.append(headers + "\n");
                            writer.append(String.join(",", newSchedule) + "\n");
                              } catch (Exception e) {
                                System.out.println("Something went wrong with the header");
                            }
                        } else {
                            try {
                                FileWriter writer = new FileWriter(file, true);
                            writer.append(String.join(",", newSchedule) + "\n");
                            writer.close();
                            } catch (Exception e) {
                                System.out.println("Something went wrong with the content");
                            }
                        }
                        
        
                System.out.println("You have been signed up!");
                newSchedule.clear();
        
                }while(fullName.isEmpty() || email.isEmpty() || bgInput.isEmpty() || needsInput.isEmpty() || formattedDate.isEmpty() || formattedTime.isEmpty());
            } else if(userInput.equals("schedule-cli delete")){
                if(file.length() == 0){
                    System.out.print("There is no record to be deleted");
                } else {
                    listSchedules(newSchedules);
                    System.out.print("What ID do you want to delete?: ");
                    deleteInput = scanner.nextLine();
                    int idToBeDeleted = Integer.parseInt(deleteInput);
    
                    for(int i = 0; i < newSchedules.size(); i+=7){
                        if(i+6 < newSchedules.size()){
                            if(Integer.parseInt(newSchedules.get(i)) == idToBeDeleted){
                                newSchedules.remove(i + 6);
                                newSchedules.remove(i + 5);
                                newSchedules.remove(i + 4);
                                newSchedules.remove(i + 3);
                                newSchedules.remove(i + 2);
                                newSchedules.remove(i + 1);
                                newSchedules.remove(i);
                            }
                        }
                    }
                    System.out.println("ID " + idToBeDeleted + " successfully deleted.");
                        try(FileWriter writer = new FileWriter(file)) {
                            writer.append(headers + "\n");
                            for(int i = 0; i < newSchedules.size(); i+=7){
                                    newSchedule.add(newSchedules.get(i));
                                    newSchedule.add(newSchedules.get(i + 1));
                                    newSchedule.add(newSchedules.get(i + 2));
                                    newSchedule.add(newSchedules.get(i + 3));
                                    newSchedule.add(newSchedules.get(i + 4));
                                    newSchedule.add(newSchedules.get(i + 5));
                                    newSchedule.add(newSchedules.get(i + 6));
                                    writer.append(String.join(",", newSchedule) + "\n");
                                    newSchedule.clear();
                            }
                        } catch (Exception e) {
                                System.out.println("Something went wrong with the header");
                            }
                listSchedules(newSchedules);
                }
            } else if(userInput.equals("schedule-cli update")){
                String updatedFormattedTime = "";
                String updatedFormattedDate = "";
                listSchedules(newSchedules);
                do {       
                    System.out.print("What ID do you want to update?: ");
                    String idToBeUpdated = scanner.nextLine().trim();
                    if(!newSchedules.contains(idToBeUpdated)){
                        System.out.println("This ID does not exist. Try again: ");
                    } else {
                    System.out.print("What is your full name?: ");
                    fullName = scanner.nextLine();
        
                        while(true){
                            System.out.print("What is your email address?: ");
                            emailInput = scanner.nextLine();
                            if(emailInput != null && pattern.matcher(emailInput).matches()){
                                email = emailInput;
                                break;
                            } else {
                                System.out.println("Your email: " + emailInput + " , is not correct. Try again");
                            }
                        }
                    
                    System.out.print("What is your professional background?: ");
                    bgInput = scanner.nextLine();
        
                    System.out.print("Tell us how we can help you today: ");
                    needsInput = scanner.nextLine();
        
                    // add validation of date input
                    while(true){
                        System.out.print("What date do you want to pick?: ");
                        futureDate = scanner.nextLine();
                        parsedFutureDate = LocalDate.parse(futureDate);
                        if(todayDate.isBefore(parsedFutureDate)){
                            break;
                        } else {
                            System.out.println("You chose a date that has been passed. Try a future date.");
                        }
                    }
        
                    // add validation of time input
                    while (true) {   
                        System.out.print("What time do you want to pick between 8am and 5pm?: ");
                        timeInput = scanner.nextLine();
                        time = LocalTime.parse(timeInput);
                        String openingHrs = "08:00:00";
                        String closingHrs = "17:00:00";
                        LocalTime startTime = LocalTime.parse(openingHrs);
                        LocalTime endTime = LocalTime.parse(closingHrs);
            
                        if(time.isAfter(startTime) && time.isBefore(endTime)){
                            break;
                        } else {
                            System.out.println("You selected a time outside our opening hours. Try again");
                        }
                    }  
        
                    updatedFormattedDate = parsedFutureDate.toString();
                    updatedFormattedTime = time.toString();

                    for(int i = 0; i < newSchedules.size(); i+=7){
                        if(i + 6 < newSchedules.size()){
                            if(newSchedules.get(i).equals(idToBeUpdated)){
                                System.out.println(i);
                                System.out.println(newSchedules.get(i));
                                newSchedules.set(i + 1, fullName);
                                newSchedules.set(i + 2, email);
                                newSchedules.set(i + 3, bgInput);
                                newSchedules.set(i + 4, needsInput);
                                newSchedules.set(i + 5, updatedFormattedDate);
                                newSchedules.set(i + 6, updatedFormattedTime);
                            }
                        }
                    }
                    ArrayList<String> updatedSchedulesList = new ArrayList<>();
                    try {
                            FileWriter writer = new FileWriter(file);
                            writer.append(headers + "\n");
                            for(int i = 0; i < newSchedules.size(); i+=7){
                                if(i + 6 < newSchedules.size()){
                                    updatedSchedulesList.add(newSchedules.get(i));
                                    updatedSchedulesList.add(newSchedules.get(i + 1));
                                    updatedSchedulesList.add(newSchedules.get(i + 2));
                                    updatedSchedulesList.add(newSchedules.get(i + 3));
                                    updatedSchedulesList.add(newSchedules.get(i + 4));
                                    updatedSchedulesList.add(newSchedules.get(i + 5));
                                    updatedSchedulesList.add(newSchedules.get(i + 6));
                                    writer.append(String.join(",", updatedSchedulesList) + "\n");
                                    updatedSchedulesList.clear();
                                }
                            }
                            writer.close();
                            } catch (Exception e) {
                                System.out.println("Something went wrong with the content");
                            }
                    }
                listSchedules(newSchedules);
                } while (fullName.isEmpty() || email.isEmpty() || bgInput.isEmpty() || needsInput.isEmpty() || updatedFormattedDate.isEmpty() || updatedFormattedTime.isEmpty());
            }
            if(userInput.equals("close")){
                System.out.println("Goodbye");
                break;
            }
        }
        scanner.close();

    }

    static void listSchedules (ArrayList<String> newSchedules){
        System.out.printf("%-5s | %-20s | %-25s | %-15s | %-10s | %-15s | %-10s%n",
                "ID", "Full Name", "Email", "Background", "Need", "Date", "Time");
                System.out.print("--------------------------------------------------------------------------------------------------------------------" + "\n");
        for(int i = 0; i < newSchedules.size(); i+=7){
                    if(i + 6 < newSchedules.size()){
                        System.out.printf("%-5s | %-20s | %-25s | %-15s | %-10s | %-15s | %-10s%n",
                        newSchedules.get(i),     
                        newSchedules.get(i + 1), 
                        newSchedules.get(i + 2), 
                        newSchedules.get(i + 3), 
                        newSchedules.get(i + 4), 
                        newSchedules.get(i + 5), 
                        newSchedules.get(i + 6)  
                );
                    }
                }
                System.out.print("--------------------------------------------------------------------------------------------------------------------" + "\n");
    }

    static void addSchedules (){
        
    }
}