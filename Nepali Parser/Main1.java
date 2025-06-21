//Convert from UTF-8 to Unicode
public class Main1 {
  public static void main(String[] argv) throws Exception {
    String string = "लाई";//"abc\u5639";
    String string1 = "लाई";
    byte[] utf8 = string.getBytes("UTF-8");
    byte[] utf81 = string1.getBytes("UTF-8");
    string = new String(utf8, "UTF-8");
    string1 = new String(utf81, "UTF-8");    
    if(string.equals(string1)){
    	System.out.println("Good Job");
    }
    else{
    	System.out.println("Bad Job");
    }
    System.out.println(string);
    System.out.println(string1);
    
  }
}