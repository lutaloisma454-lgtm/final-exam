// Question 4(c): Concrete class A
public class A implements X, Y {

    @Override
    public void a(String message) {
        System.out.println("Message: " + message);
    }
    public static void main(String[] args) {
        X.m();
        Y.m();

        System.out.println("W in X = " + X.W);
        System.out.println("W in Y = " + Y.W);

        A obj = new A();
        obj.a("Welcome to Java interfaces");

        System.out.println("Square of 5 = " + obj.b(5));
    }
}
