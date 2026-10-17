public class Staff {
    private final String staffNo;
    private final String staffName;
    private final int staffAge;

    public Staff(String staffNo, String staffName, int staffAge) {
        if (staffNo == null || staffNo.trim().isEmpty()) {
            throw new IllegalArgumentException("Staff number cannot be blank.");
        }
        if (staffName == null || staffName.trim().isEmpty()) {
            throw new IllegalArgumentException("Staff name cannot be blank.");
        }
        if (staffAge <= 0) {
            throw new IllegalArgumentException("Staff age must be a positive number.");
        }
        this.staffNo = staffNo.trim();
        this.staffName = staffName.trim();
        this.staffAge = staffAge;
    }

    public void printDetails() {
        System.out.println("Staff Number: " + staffNo);
        System.out.println("Staff Name: " + staffName);
        System.out.println("Staff Age: " + staffAge);
    }

    public String getStaffNo() {
        return staffNo;
    }

    public String getStaffName() {
        return staffName;
    }

    public int getStaffAge() {
        return staffAge;
    }
}
