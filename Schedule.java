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

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(System.in);
        LocalDate todayDate = LocalDate.now();
        String futureDate;
        LocalTime time;
        LocalDate parsedFutureDate;
        
        System.out.println("Project Details");

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
        ArrayList<String> newSchedules = new ArrayList<>();
        ArrayList<String> schedules = new ArrayList<>();
        int ID = 1;


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
        
        do{
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


            formattedDate = parsedFutureDate.toString();
            formattedTime = time.toString();

            for(int i = 0; i < newSchedules.size(); i++){
                if(i % 7 == 0){
                    ID++;
                }
            }
        
            newSchedule.addFirst(Integer.toString(ID));
            newSchedule.add(fullName);
            newSchedule.add(email);
            newSchedule.add(bgInput);
            newSchedule.add(needsInput);
            newSchedule.add(formattedDate);
            newSchedule.add(formattedTime);

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


            String headers = "ID,Full Name,Email,Background,Need,ScheduledDate,ScheduledTime";

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


        scanner.close();
    }
}