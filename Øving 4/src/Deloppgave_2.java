import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;

public class Deloppgave_2 {
    static Deque<Character> stack = new ArrayDeque<Character>();

    public static boolean parenthesesChecker(String st) {
        for (int j = 0; j < st.length(); j++) {
            char ch = st.charAt(j);

            if (ch == '(' || ch == '{' || ch == '[') {
                stack.add(ch);
                continue;
            }

            char charToCheck;

            switch (ch) {
                case ')':
                    charToCheck = stack.removeLast();
                    if (charToCheck == '{' || charToCheck == '[') {
                        return false;
                    }
                    break;
                case '}':
                    charToCheck = stack.removeLast();
                    if (charToCheck == '(' || charToCheck == '[') {
                        return false;
                    }
                    break;
                case ']':
                    charToCheck = stack.removeLast();
                    if (charToCheck == '(' || charToCheck == '{') {
                        return false;
                    }
                    break;
            }
        }
        return stack.isEmpty();
    }

    public static String removeCharAndSpaces(ArrayList<String> list) {
        String newSt = "";

        for (String st: list) {
            // removes everything after a line comment, until it reaches a new line
            st = st.replaceAll("//[^\n]*", "");
            // removes everything inside a block comment
            st = st.replaceAll("/\\*(.|\\n)*?\\*/", "");
            // removes everything inside a string
            st = st.replaceAll("\"[^\"]*", "");
            // removes everything except parentheses
            st = st.replaceAll("[^{\\[\\]}()]", "");
            if (!st.isBlank()) {
                newSt += st;
            }
        }
        return newSt;
    }

    public static ArrayList<String> readFile(String filename) throws IOException {
        ArrayList<String> fileAsStrings = new ArrayList<String>();

        try {
            File file = new File(filename);

            BufferedReader br = new BufferedReader(new FileReader(file));

            String input;
            while((input = br.readLine()) != null) {
                fileAsStrings.add(input);
            }

            br.close();
        } catch (IOException e) {
            throw new IOException(e);
        }

        return fileAsStrings;
    }
}
