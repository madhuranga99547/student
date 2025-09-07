public class Student {
    private int studentId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String major;
    private double gpa;
    
    public Student() {}
    
    public Student(
        String firstName,
        String lastName,
        String email, 
        String phoneNumber,
        String major,
        double gpa
    ) {
        this.firstName   = firstName;
        this.lastName    = lastName;
        this.email       = email;
        this.phoneNumber = phoneNumber;
        this.major       = major;
        this.gpa         = gpa;
    }
    
    public Student(
        int studentId,
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        String major,
        double gpa
    ) {
        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.major = major;
        this.gpa = gpa;
    }
    
    public int getStudentId() {
        return studentId;
    }
    
    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    public String getMajor() {
        return major;
    }
    
    public void setMajor(String major) {
        this.major = major;
    }
    
    public double getGpa() {
        return gpa;
    }
    
    public void setGpa(double gpa) {
        this.gpa = gpa;
    }
    
    @Override
    public String toString() {
        return String.format("Student{ID=%d, Name='%s %s', Email='%s', Phone='%s', Major='%s', GPA=%.2f}",
                studentId, firstName, lastName, email, phoneNumber, major, gpa);
    }
}