// Question 4(b): Interface Y
public interface Y {
    int W = 10;

    static void m() {
        System.out.println("I am from Y");
    }

    default int b(int number) {
        return number * number;
    }
}
