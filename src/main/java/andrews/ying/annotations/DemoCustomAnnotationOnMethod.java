package andrews.ying.annotations;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public class DemoCustomAnnotationOnMethod {
    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        List<Pet> petList = Arrays.asList(new Cat("michelle"), new Dog("tim"));
        for (Pet pet: petList) {
            System.out.println("----------------------------------");
            System.out.println("Hi, my name is: " + pet.getName());
            System.out.println("I am a " + pet.getClass().getSimpleName());
            for (Method method: pet.getClass().getDeclaredMethods()) {
                if (method.isAnnotationPresent(VeryImportantMethod.class)) {
                    VeryImportantMethod veryImportantMethod = method.getAnnotation(VeryImportantMethod.class);
                    int times = veryImportantMethod.times();
                    for (int i = 0; i < times; i++) {
                        method.invoke(pet);
                    }
                }
            }
        }
    }
}
