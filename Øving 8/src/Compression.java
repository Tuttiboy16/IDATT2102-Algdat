
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Compression {

    public byte[] LZ77(String path) throws IOException{
        DataInputStream inputStream = new DataInputStream(new BufferedInputStream(new FileInputStream(path)));
        DataOutputStream outputStream = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(path +
                "compressed")));

        byte[] inputBytes = inputStream.readAllBytes();
        //String string = new String(inputStream.readAllBytes(), StandardCharsets.US_ASCII);
        //charArr = string.toCharArray();

        // Range is the maximum length of characters to check backwards
        int RANGE = 127;

        // The minimum length of a character range that the program should check
        int MIN_WORD_LENGTH = 4;

        // Stores everything that has been looped through and compressed
//        StringBuilder incompressible = new StringBuilder();
        ArrayList<Integer> incompressible = new ArrayList<>();

        // Stores the combination of characters that the program is checking towards
        ArrayList<Integer> currentDictonary = new ArrayList();

        // Stores the current combination of characters that we try to find a match for
        ArrayList<Integer> currentCombination = new ArrayList();

        currentDictonary.add((int) inputBytes[0]);
        currentDictonary.add((int) inputBytes[1]);
        currentDictonary.add((int) inputBytes[2]);
        currentDictonary.add((int) inputBytes[3]);

        incompressible.addAll(currentDictonary);

        int i = 4;

        outer:
        while(i < inputBytes.length) {
            for(int j = i; j < inputBytes.length; j++) {
                currentCombination.add((int) inputBytes[j]);
                if(j == inputBytes.length-1) {
                    incompressible.addAll(currentCombination);
                    break outer;
                }
                //Makes sure the combination always is the desired length before checking for a match
                if(currentCombination.size() < MIN_WORD_LENGTH) {
                    continue;
                }

                int foundIndex = findPatternInText(currentCombination, currentDictonary);

                // If foundIndex == -1 there is no match
                if(foundIndex == -1) {
                    incompressible.addAll(currentCombination);
                    currentDictonary.addAll(currentCombination);

                    //If currentDictionary is larger than specified range,
                    //the k first characters are removed
                    if(currentDictonary.size() > RANGE){
                        for(int k=0;k<currentDictonary.size()-RANGE;k++){
                            currentDictonary.remove(0);
                        }
                    }

                    //moves i to the end of currentCombination if no match
                    i+=currentCombination.size();
                    //empties currentCombination as we need to restart
                    currentCombination.clear();


                } else {
                    int k = j+1;
                    //end of file
                    if(k >= inputBytes.length){
                        //incompressible to outputstream
                        outputStream.writeByte(incompressible.size());
                        for (int c = 0; c < incompressible.size(); c++){
                            outputStream.writeByte(incompressible.indexOf(c));
                        }
                        //compressed to outputstream
                        int distanceToMatch = (currentDictonary.size() - foundIndex);
                        outputStream.writeByte((byte)~(distanceToMatch) +1);
                        outputStream.writeByte((byte)currentCombination.size());
                        break outer;
                    }

                    int foundIndexLoop = foundIndex;

                    //If there is a match with the next character, loop until it hits a no-match
                    while(foundIndexLoop != -1){
                        if(k>=inputBytes.length) break;
                        currentCombination.add((int)inputBytes[k]);
                        k++;
                        foundIndexLoop = findPatternInText(currentCombination, currentDictonary);
                    }

                    currentCombination.remove(currentCombination.size()-1);

                    //Writes the length of incompressible data
                    outputStream.writeByte(incompressible.size());
                    //Writes incompressible data to outputstream
                    for (int c = 0; c < incompressible.size(); c++){
                        outputStream.writeByte(incompressible.get(c));
                    }

                    //writes the compressed data to outputstream on the format
                    // 1ddd dddd llll llll where d represents relative distance to match from end of dictionary
                    //and l represents length of match
                    int distanceToMatch = (currentDictonary.size() - foundIndex);
                    outputStream.writeByte(~(distanceToMatch) +1);
                    outputStream.writeByte(currentCombination.size());

                    i += currentCombination.size();

                    currentCombination.clear();
                    incompressible.clear();
                }

            }
        }
        //writes incompressible to stream when end is reached
        outputStream.writeByte(incompressible.size());
        for (int c = 0; c < incompressible.size(); c++){
            outputStream.writeByte(incompressible.get(c));
        }

        inputStream.close();
        outputStream.flush();
        outputStream.close();
        return inputBytes;
    }

    public byte[] readStream(String path) throws IOException{
        DataInputStream inputStream = new DataInputStream(new BufferedInputStream(new FileInputStream(path +
                "compressed")));
        byte[] bytes = new byte[inputStream.available()];
        inputStream.readFully(bytes);
        return bytes;
    }

    public ArrayList<Byte> decompress(String path) throws  IOException{
        DataInputStream inputStream = new DataInputStream(new BufferedInputStream(new FileInputStream(path)));
        DataOutputStream outputStream = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(path +
                "decompressed")));
        byte[] bytes = new byte[inputStream.available()];
        inputStream.readFully(bytes);

        ArrayList<Byte> byteList = new ArrayList<>();


        int i = 0;
        outer:
        while(i < bytes.length){
            //posOrNeg either represents a positive length of uncompressed data
            //or a negative number, which indicates a pointer to existing data
            int posOrNeg = bytes[i];

            //if the byte is larger than zero, uncompressed data follows
            if(posOrNeg >= 0){
                byte[] copy = new byte[bytes.length - 1];

                //removes the byte telling how much uncompressed data follows
                for (int d = 0, e = 0; d < bytes.length; d++) {
                    if (d != i) {
                        copy[e++] = bytes[d];
                    }
                }
                bytes = copy;

                for(int l = 0; l < posOrNeg; l++){
                    if(i+l>=bytes.length) break outer;
                    byteList.add(bytes[i+l]);
                }
                i += posOrNeg;


            }
            //If the byte is negative, that means it is a pointer
            else{
                int distance = ~(posOrNeg-1);
                int length = bytes[i+1];

                //removes pointer bytes from array
                byte[] copy = new byte[bytes.length - 1];
                for (int d = 0, e = 0; d < bytes.length; d++) {
                    if (d != i) {
                        copy[e++] = bytes[d];
                    }
                }
                bytes = copy;

                copy = new byte[bytes.length - 1];
                for (int d = 0, e = 0; d < bytes.length; d++) {
                    if (d != i) {
                        copy[e++] = bytes[d];
                    }
                }
                bytes = copy;



                for(int k = 0; k < length; k++){
                    byteList.add(byteList.get(i-distance+k));
                }
            }
        }

        for (Byte aByte : byteList) {
            outputStream.write(aByte);
        }

        inputStream.close();
        outputStream.flush();
        outputStream.close();
        return byteList;
    }

    private int findPatternInText(ArrayList<Integer> pattern, ArrayList<Integer> text) {
        int index = -1;
        int patternLength = pattern.size();
        int textLength = text.size();
        char[] patternArray = new char[patternLength];
        char[] textCharArray = new char[textLength];

        for(int i = 0; i < patternLength; i++) { patternArray[i] = (char) pattern.get(i).byteValue();}
        for(int i = 0; i < textLength; i++) textCharArray[i] = (char) text.get(i).byteValue();

        int[] suffixArray = generateSuffixArray(patternLength, patternArray);

        int i = 0;
        boolean isMatched = true;
        int j = 0;
        while(j < patternLength && i < textLength){
            if(textCharArray[i] == patternArray[j]){
                j = j + 1;
                i = i + 1;
            } else{
                int newIndex = i-j+1;
                isMatched = false;
                j -= 1;

                if (j < 0){
                    j = 0;
                }
                j = suffixArray[j];

                if(j == 0){
                    i = newIndex;
                } else {
                    i = i + 1;
                }

            }

            if(j == patternLength) {
                isMatched = true;
            }

        }
        if(isMatched){
            index = i-patternLength;
        }

        return index;
    }

    private int[] generateSuffixArray(int patternLength, char[] pattern) {
        int[] suffixArray = new int[patternLength];
//        char[] patternArray = pattern.toCharArray();
        int i = 1;
        while(i < patternLength){
            for(int j = 0; j < patternLength; j++){
                if((i < patternLength) && (pattern[i] == pattern[j])){
                    suffixArray[i] = j + 1;
                    i = i + 1;

                } else{
                    i = i + 1;
                    break;
                }

                if(i == patternLength-1)
                    break;
            }

        }

        return  suffixArray;
    }

}