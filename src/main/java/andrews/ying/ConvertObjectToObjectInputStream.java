package andrews.ying;

import java.io.*;

public class ConvertObjectToObjectInputStream {
    public static void main(String[] args) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);

        Dog dog = new Dog();
        dog.setName("Henry");
        oos.writeObject(dog);

        oos.flush();
        oos.close();

        InputStream is = new ByteArrayInputStream(baos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(is);

        System.out.println(ois);
    }
}

class Dog implements Serializable{
    String name;
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
