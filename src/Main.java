import controller.Controller;
import visual.View;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        new Controller(new View(in.nextInt()));
    }
}
