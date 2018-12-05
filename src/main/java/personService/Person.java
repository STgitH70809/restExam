package personService;

public class Person {
    private String id;
    private  String name;
    private int age;

    public Person(){
    }

    public Person(String id, String name , int Age){
        this.id = id;
        this.name = name;
        this.age = Age;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }
}