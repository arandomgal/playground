package andrews.ying.annotations;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public class DemoCustomAnnotationOnField {
    public static void main(String[] args) throws IllegalAccessException {
        List<Pet> petList = Arrays.asList(new Cat("michelle"), new Dog("tim"));
        for (Pet pet: petList) {
            System.out.println("----------------------------------");
            System.out.println("Hi, my name is: " + pet.getName());
            System.out.println("I am a " + pet.getClass().getSimpleName());

            for (Field field: pet.getClass().getDeclaredFields()) {
                if (field.isAnnotationPresent(VeryImportantField.class)) {
                    field.setAccessible(true);
                    Object objectValue = field.get(pet);
                    if (objectValue instanceof String stringValue) {
                        System.out.println(stringValue.toUpperCase());
                    }
                }
            }
        }
    }
}
