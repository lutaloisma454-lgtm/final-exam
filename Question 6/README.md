# Question 6 — Staff Salary Calculation System

## How to run the system.
mkdir -p out
javac -d out src/*.java

# Run the full-time staff program:
java -cp out FullTimeStaff

# Run the part-time staff program:
java -cp out PartTimeStaff

## OOP concepts demonstrated
- Inheritance: both subclasses extend `Staff`.
- Encapsulation: fields are private and accessed through constructors/getters.
- Method overriding: both subclasses override `printDetails()`.
- Code reuse: `super.printDetails()` prints shared staff details.
