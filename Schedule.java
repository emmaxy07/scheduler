import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;



public class Schedule {
    static ArrayList<ScheduleRecord> newScheduleRecords = new ArrayList<>();


    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Scheduler Application");

        String fullName = "";
        String emailInput = "";
        String email = "";
        String bgInput = "";
        String needsInput = "";
        String futureDate = "";
        String timeInput = "";
        String filePath = "schedules.csv";
        File file = new File(filePath);
        String line = "";
        String[] row;
        ArrayList<String> schedules = new ArrayList<>();
        int startingID = 1;
        String userInput = "";
        String deleteInput = "";
        String headers = "ID,Full Name,Email,Background,Need,ScheduledDate,ScheduledTime";
        ScheduleRecord scheduleRecord = new ScheduleRecord(1, "", "", "", "", "", "");


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

        

        while(true){
            System.out.print("What do you want to do?: ");
            userInput = scanner.nextLine();
            if(userInput.equals("schedule-cli list")){
                listSchedules(newScheduleRecords);
            } else if(userInput.equals("schedule-cli add")){
                do{
                    System.out.print("What is your full name?: ");
                    fullName = scanner.nextLine().trim();
                    scheduleRecord.setFullName(fullName);
        
                        while(true){
                            System.out.print("What is your email address?: ");
                            emailInput = scanner.nextLine().trim();
                            if(emailInput.equals("")){
                                System.out.println("Email cannot be empty");
                            } else {
                            scheduleRecord.setEmailInput(emailInput);
                                break;
                            }
                        }
        
                    System.out.print("What is your professional background?: ");
                    bgInput = scanner.nextLine().trim();
                    scheduleRecord.setBgInput(bgInput);
        
                    System.out.print("Tell us how we can help you today: ");
                    needsInput = scanner.nextLine().trim();
                    scheduleRecord.setNeedsInput(needsInput);
        
                    while(true){
                        System.out.print("What date do you want to pick?: ");
                        futureDate = scanner.nextLine().trim();
                        if(futureDate.equals("")){
                            System.out.println("future date cannot be empty");
                        } else {
                            scheduleRecord.setFutureDate(futureDate);
                            break;
                        }
                    }
        
                    while (true) {   
                        System.out.print("What time do you want to pick between 8am and 5pm?: ");
                        timeInput = scanner.nextLine().trim();
                        if(timeInput.equals("")){
                            System.out.println("time cannot be empty");
                        } else {
                            scheduleRecord.setTimeInput(timeInput);
                            break;
                        }
                    }  

                    if(newScheduleRecords.size() == 0){
                        startingID = 1;
                    } else {
                        ScheduleRecord lastID = newScheduleRecords.get(newScheduleRecords.size() - 1);
                        startingID = lastID.getID();
                        startingID++;
                        scheduleRecord.setID(startingID);
                    }             
    
                    newScheduleRecords.add(scheduleRecord);          

                        if(file.length() == 0){
                            try(FileWriter writer = new FileWriter(file)) {
                            writer.append(headers + "\n");
                                writeNewScheduleRecord(writer, scheduleRecord);
                              } catch (Exception e) {
                                System.out.println("Something went wrong with the header");
                            }
                        } else {
                            try {
                                FileWriter writer = new FileWriter(file, true);
                                writeNewScheduleRecord(writer, scheduleRecord);
                            writer.close();
                            } catch (Exception e) {
                                System.out.println("Something went wrong with the content");
                            }
                        }
                        
        
                System.out.println("You have been signed up!");        
                }
                while(fullName.isEmpty() || emailInput.isEmpty() || bgInput.isEmpty() || needsInput.isEmpty() || futureDate.isEmpty() || timeInput.isEmpty());
            } else if(userInput.equals("schedule-cli delete")){
                int idToBeDeleted = 0;
                if(file.length() == 0){
                    System.out.print("There is no record to be deleted");
                } else {
                    System.out.print("What ID do you want to delete?: ");
                    deleteInput = scanner.nextLine().trim();
                    idToBeDeleted = Integer.parseInt(deleteInput);
                    boolean idExists = false;
                    for(ScheduleRecord s: newScheduleRecords){
                        int idToBeChecked = s.getID();
                        if(idToBeDeleted == idToBeChecked){
                            idExists = true;
                            break;
                        } 
                    }

                    for(int i = newScheduleRecords.size() - 1; i >= 0; i--){
                            if (newScheduleRecords.get(i).getID() == idToBeDeleted){
                                newScheduleRecords.remove(newScheduleRecords.get(i));
                            }
                        }
                    System.out.println("ID " + idToBeDeleted + " successfully deleted.");
                    try(FileWriter writer = new FileWriter(file)) {
                        writer.append(headers + "\n");
                        for(ScheduleRecord s: newScheduleRecords){
                            writeNewScheduleRecord(writer, s);
                        }
                    } catch (Exception e) {
                            System.out.println("Something went wrong with the header");
                        }
                    }
                } else if(userInput.equals("schedule-cli update")){
                listSchedules(newScheduleRecords);
                do { 
        ScheduleRecord updatedScheduleRecord = new ScheduleRecord(1, "", "", "", "", "", "");
                    System.out.print("What ID do you want to update?: ");
                    String idToBeUpdated = scanner.nextLine().trim();
                    boolean idExists = false;
                    for(int i = 0; i < newScheduleRecords.size(); i++){
                        if(newScheduleRecords.get(i).getID() == Integer.parseInt(idToBeUpdated)){
                            idExists = true;
                            break;
                        } 
                    }
                    
                    System.out.print("What is your full name?: ");
                    fullName = scanner.nextLine().trim();
                    updatedScheduleRecord.setFullName(fullName);
        
                        while(true){
                            System.out.print("What is your email address?: ");
                            emailInput = scanner.nextLine().trim();
                            if(emailInput.equals("")){
                                System.out.println("Email cannot be empty");
                            } else {
                            updatedScheduleRecord.setEmailInput(emailInput);
                                break;
                            }
                        }
                    
                    System.out.print("What is your professional background?: ");
                    bgInput = scanner.nextLine().trim();
                    updatedScheduleRecord.setBgInput(bgInput);
        
                    System.out.print("Tell us how we can help you today: ");
                    needsInput = scanner.nextLine().trim();
                    updatedScheduleRecord.setNeedsInput(needsInput);
        
                    while(true){
                        System.out.print("What date do you want to pick?: ");
                        futureDate = scanner.nextLine().trim();
                        if(futureDate.equals("")){
                            System.out.println("future date cannot be empty");
                        } else {
                            updatedScheduleRecord.setFutureDate(futureDate);
                            break;
                        }
                    }
        
                   while (true) {   
                        System.out.print("What time do you want to pick between 8am and 5pm?: ");
                        timeInput = scanner.nextLine().trim();
                        if(timeInput.equals("")){
                            System.out.println("time cannot be empty");
                        } else {
                            updatedScheduleRecord.setTimeInput(timeInput);
                            break;
                        }
                    }  

                    updatedScheduleRecord.setID(Integer.parseInt(idToBeUpdated));
                    
                    for(int i = 0; i < newScheduleRecords.size(); i++){
                        if(newScheduleRecords.get(i).getID() == Integer.parseInt(idToBeUpdated)){
                            newScheduleRecords.set(i, updatedScheduleRecord);
                        }
                    }
        
                    try {
                            FileWriter writer = new FileWriter(file);
                            writer.append(headers + "\n");
                            for(int i = 0; i < newScheduleRecords.size(); i++){
                               writeNewScheduleRecord(writer, newScheduleRecords.get(i));
                            }
                            writer.close();
                            } catch (Exception e) {
                                System.out.println("Something went wrong with the content");
                            }
                    
                } while (fullName.isEmpty() || email.isEmpty() || bgInput.isEmpty() || needsInput.isEmpty() || futureDate.isEmpty() || timeInput.isEmpty());
                listSchedules(newScheduleRecords);
            } if(userInput.equals("close")){
                System.out.println("Goodbye");
                break;
            }
        scanner.close();  
        }
    }

    static void listSchedules (ArrayList<ScheduleRecord> newScheduleRecords){
        System.out.printf("%-5s | %-20s | %-25s | %-15s | %-10s | %-15s | %-10s%n",
                "ID", "Full Name", "Email", "Background", "Need", "Date", "Time");
                System.out.print("--------------------------------------------------------------------------------------------------------------------" + "\n");
        for(int i = 0; i < newScheduleRecords.size(); i++){
                        System.out.printf("%-5s | %-20s | %-25s | %-15s | %-10s | %-15s | %-10s%n",
                       newScheduleRecords.get(i).getID(),
                       newScheduleRecords.get(i).getFullName(),
                       newScheduleRecords.get(i).getEmailInput(),
                       newScheduleRecords.get(i).getBgInput(),
                       newScheduleRecords.get(i).getNeedsInput(),
                       newScheduleRecords.get(i).getFutureDate(),
                       newScheduleRecords.get(i).getTimeInput()
                );
                }
                System.out.print("--------------------------------------------------------------------------------------------------------------------" + "\n");
    }

    static void writeNewScheduleRecord (FileWriter writer, ScheduleRecord scheduleRecord){
        try {
            writer.append(String.valueOf(scheduleRecord.getID())).append(",").append(scheduleRecord.getFullName()).append(",").append(scheduleRecord.getEmailInput()).append(",").append(scheduleRecord.getBgInput()).append(",").append(scheduleRecord.getNeedsInput()).append(",").append(scheduleRecord.getFutureDate()).append(",").append(scheduleRecord.getTimeInput()).append("\n");
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}