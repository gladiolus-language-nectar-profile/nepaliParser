/**
 *
 * @author Pritom K Mondal
 */
public class AsciiString {
    public static void main(String[] args) {
        String inputString = "Hi, THAI(คุณ), HINDI:(तुम मेरी हो), HANGERIO:(تو مال منی), CHINA:(您), ARBI(أنت), FARSI(شما)";
        System.out.println("ORIGINAL:      " + inputString);
        String encoded = AsciiString.encode(inputString);
        System.out.println("ASCII ENCODED: " + encoded);
        String decoded = AsciiString.decode(encoded);
        System.out.println("ASCII DECODED: " + decoded);
    }
    
    public static String encode(String word) {
        String encoded = "";
        for(Integer index = 0; index < word.length(); index++) {
            int ascii = (int) word.charAt(index);
            Boolean keepAscii = true;
            if(ascii >= 48 && ascii <= 57) {
                keepAscii = false;
            }
            if(ascii >= 65 && ascii <= 90) {
                keepAscii = false;
            }
            if(ascii >= 97 && ascii <= 122) {
                keepAscii = false;
            }
            if(ascii == 32 || ascii == 43 || ascii == 45 || ascii == 46) {
                keepAscii = false;
            }
            if(keepAscii) {
                encoded += "&#" + ascii + ";";
            } else {
                encoded += word.charAt(index);
            }
        }
        return encoded;
    }
    
    public static String decode(String word) {
        String decoded = "";
        for(Integer index = 0; index < word.length(); index++) {
            String charAt = "" + word.charAt(index);
            if(charAt.equals("&") && index < word.length() && ("" + word.charAt(index + 1)).equals("#")) {
                try {
                    Integer length = word.indexOf(";", index);
                    String sub = word.substring(index + 2, length);
                    decoded += Character.toString((char) Integer.parseInt(sub));
                    index = length;
                } catch (Exception ex) {
                    decoded += charAt;
                }
            } else {
                decoded += charAt;
            }
        }
        return decoded;
    }
}