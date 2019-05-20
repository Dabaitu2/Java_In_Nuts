import java.util.ArrayList;
import java.util.List;

public class Generic {
    abstract class Live {
        String name;

        public abstract void move();
    }

    class Animal extends Live {
        Animal(String name) {
            this.name = name;
            System.out.println("new Animal born");
        }

        public void move() {
            System.out.println("I can move");
        }
    }

    class Plant extends Live {
        Plant(String name) {
            this.name = name;
            System.out.println("new Plant born");
        }

        public void move() {
            System.out.println("I can't move");
        }
    }

    class Vegetable extends Plant {
        String type = "vegetable";

        Vegetable(String name) {
            super(name);
            System.out.printf("type: %s\n", type);
        }
    }

    class Fruit extends Plant {
        String type = "Fruit";

        Fruit(String name) {
            super(name);
            System.out.printf("type: %s\n", type);
        }
    }

    class Apple extends Fruit {

        Apple(String name) {
            super(name);
        }
    }

    class Orange extends Fruit {

        Orange(String name) {
            super(name);
        }
    }

    class SmallApple extends Apple {
        SmallApple(String name) {
            super(name);
        }
    }

    public void test() {
        List<? super Apple> apples = new ArrayList<Fruit>();
        apples.add(new SmallApple("超级水果"));
    }

    public static void main(String[] args) {

    }
}
