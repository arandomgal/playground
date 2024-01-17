package andrews.ying.annotations;

@VeryImportantClass
public class Cat implements Pet {
    @VeryImportantField
    private String name;

    public Cat(String name) {
        this.name = name;
    }

    @Override
    public void talk() {
        System.out.println("meow!");
    }

    @VeryImportantMethod(times = 10)
    @Override
    public void performDuty() {
        System.out.println("performing my duty as a cat ...");
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
