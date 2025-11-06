 public class ScheduleRecord {
        int ID;
        private String fullName;
        private String emailInput;
        private String email;
        private String bgInput;
        private String needsInput;
        private String timeInput;

        ScheduleRecord(int ID, String fullName, String emailInput, String email, String bgInput, String needsInput, String timeInput){
            this.ID = ID;
            this.fullName = fullName;
            this.emailInput = emailInput;
            this.email = email;
            this.bgInput = bgInput;
            this.needsInput = needsInput;
            this.timeInput = timeInput;
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

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
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

        public String getTimeInput() {
            return timeInput;
        }

        public void setTimeInput(String timeInput) {
            this.timeInput = timeInput;
        }

        @Override
        public String toString() {
            return "ScheduleRecord [ID=" + ID + ", fullName=" + fullName + ", emailInput=" + emailInput + ", email="
                    + email + ", bgInput=" + bgInput + ", needsInput=" + needsInput + ", timeInput=" + timeInput + "]";
        }
    }