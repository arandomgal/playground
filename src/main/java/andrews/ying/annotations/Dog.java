package andrews.ying.annotations;

public class Dog implements Pet {
    private String name;

    public Dog(String name) {
        this.name = name;
    }

    @Override
    public void talk() {
        System.out.println("oooof!");
    }

    @VeryImportantMethod
    @Override
    public void performDuty() {
        System.out.println("performing my duty as a dog ...");
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Cat{" +
                "name='" + name + '\'' +
                '}';
    }
}
