//name: muneeb israr       roll no: sp25-bcs-103
public class StudentTest {
    public static void main(String[] args) {

        Student s1 = new Student("muneeb", "muneeb@gmail.com", 3.5);

        System.out.println("Name:"+ s1.getName());
        System.out.println("Email:"+ s1.getEmail());
        System.out.println("GPA:"+ s1.getGpa());


        s1.setName("subhan");
        s1.setEmail("subhan@gmail.com");
        s1.setGpa(3.9);

        System.out.println("Updated Info:");
        System.out.println("Name:"+ s1.getName());
        System.out.println("Email:"+ s1.getEmail());
        System.out.println("GPA:"+ s1.getGpa());
    }
}
