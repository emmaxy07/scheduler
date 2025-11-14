import java.time.LocalDate;
import java.time.LocalTime;
import java.util.regex.Pattern;

public class ScheduleRecord {
        int ID;
        private String fullName;
        private String emailInput;
        private String bgInput;
        private String needsInput;
        private String futureDate;
        private String timeInput;
        
        ScheduleRecord(int ID, String fullName, String emailInput, String bgInput, String needsInput, String futureDate, String timeInput) throws IllegalArgumentException{
            String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
            LocalDate todayDate = LocalDate.now();
            String openingHrs = "08:00:00";
            String closingHrs = "17:00:00";
            LocalTime startTime = LocalTime.parse(openingHrs);
            LocalTime endTime = LocalTime.parse(closingHrs);

            this.ID = ID;
            this.fullName = fullName;
            Pattern pattern = Pattern.compile(emailRegex);
            if(emailInput != null && pattern.matcher(emailInput).matches()){
            this.emailInput = emailInput;
        } else {
            throw new IllegalArgumentException("Email is invalid. Try again");

        }
            this.bgInput = bgInput;
            this.needsInput = needsInput;
            
            LocalDate parsedFutureDate = LocalDate.parse(futureDate);
            if(todayDate.isBefore(parsedFutureDate)){
                this.futureDate = futureDate.toString();
            } else {
                throw new IllegalArgumentException("You chose a date that has been passed. Try a future date.");
            }
            
            LocalTime time = LocalTime.parse(timeInput);
            if(time.isAfter(startTime) && time.isBefore(endTime)){
            this.timeInput = timeInput;
            } else {
               throw new IllegalArgumentException("You selected a time outside our opening hours. Try again");
            }
        }

        public String getFullName(){
            return this.fullName;
        }

        public void setFullName(String fullName){
            this.fullName = fullName;
        }

        public int getID() {
            return ID;
        }

        public void setID(int iD) {
            ID = iD;
        }

        public String getEmailInput() {
            return emailInput;
        }

        public void setEmailInput(String emailInput) {
            this.emailInput = emailInput;
        }

        public String getBgInput() {
            return bgInput;
        }

        public void setBgInput(String bgInput) {
            this.bgInput = bgInput;
        }

        public String getNeedsInput() {
            return needsInput;
        }

        public void setNeedsInput(String needsInput) {
            this.needsInput = needsInput;
        }

        public String getFutureDate(){
            return futureDate;
        }

        public void setFutureDate(String futureDate){   
            this.futureDate = futureDate.toString();
        }

        public String getTimeInput() {
            return timeInput;
        }

        public void setTimeInput(String timeInput) {
            this.timeInput = timeInput;
        }

        public void resetScheduleState(){
            this.ID = 1;
            this.fullName = "";
            this.emailInput = "";
            this.bgInput = "";
            this.needsInput = "";
            this.futureDate = "";
            this.timeInput = "";
        }

        @Override
        public String toString() {
            return "ScheduleRecord [ID=" + ID + ", fullName=" + fullName + ", emailInput=" + emailInput + ",  bgInput=" + bgInput + ", needsInput=" + needsInput + ", futureDate=" + futureDate + ", timeInput=" + timeInput + "]";
        }
    }