package resumebuilder;

public class Education {
    private String graduateYear;
    private String schoolName;
    private String major;
    private String graduationStatus;

    public Education() {
    }

    public Education(String graduateYear, String schoolName, String major, String graduationStatus) {
        this.graduateYear = graduateYear;
        this.schoolName = schoolName;
        this.major = major;
        this.graduationStatus = graduationStatus;
    }

    public String getGraduateYear() {
        return graduateYear;
    }

    public void setGraduateYear(String graduateYear) {
        this.graduateYear = graduateYear;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getGraduationStatus() {
        return graduationStatus;
    }

    public void setGraduationStatus(String graduationStatus) {
        this.graduationStatus = graduationStatus;
    }

    @Override
    public String toString() {
        return "Education{" +
                "graduateYear=" + graduateYear +
                ", schoolName='" + schoolName + '\'' +
                ", major='" + major + '\'' +
                ", graduationStatus=" + graduationStatus +
                '}';
    }
}
