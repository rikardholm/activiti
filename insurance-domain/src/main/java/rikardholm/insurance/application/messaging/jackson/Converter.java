package rikardholm.insurance.application.messaging.jackson;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

public class Converter {
    public static void main(String[] args) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        MyStuff myStuff = objectMapper.readValue("{\"name\": \"Rikard\",\"age\":7}", MyStuff.class);

        System.out.println(myStuff);

        myStuff.setAge(14);
        myStuff.setName("Oskar");

        String json = objectMapper.writeValueAsString(myStuff);

        System.out.println(json);
    }

    public static class MyStuff {
        private String name;
        private Long age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public long getAge() {
            return age;
        }

        public void setAge(long age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "MyStuff{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }

}
