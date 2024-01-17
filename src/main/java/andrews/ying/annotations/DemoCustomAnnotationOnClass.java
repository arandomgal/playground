package andrews.ying.annotations;

import java.util.Arrays;
import java.util.List;

public class DemoCustomAnnotationOnClass {
    public static void main(String[] args) {
        List<Pet> petList = Arrays.asList(new Cat("michelle"), new Dog("tim"));
        petList.stream().forEach(pet -> {
            System.out.println("----------------------------------");
            System.out.println("Hi, my name is: " + pet.getName());
            System.out.println("I am a " + pet.getClass().getSimpleName());
            System.out.print("This is how I talk ==> ");
            pet.talk();
            if (pet.getClass().isAnnotationPresent(VeryImportantClass.class)) {
                System.out.println("I'm very important, so I talk a lot!!!");
                pet.talk();
                pet.talk();
                pet.talk();
            }
        });
    }
}
