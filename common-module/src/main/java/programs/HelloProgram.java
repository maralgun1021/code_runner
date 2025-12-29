package programs;

public class HelloProgram {

    public static void main(String[] args) {
        System.out.println("Hello from another Java file!");
        
        if (args.length > 0) {
            System.out.println("Argument: " + args[0]);
        }
    }
}
