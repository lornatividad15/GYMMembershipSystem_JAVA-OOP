
package gym.membership.management.system;

public class UIUtils {
    
    public static final String INDENT = "                                                                                                              ";
    
    public static void clearScreen(){
        for(int i = 0; i < 15; i++){
            System.out.println();
        }
    }
    
    public static void pause(){
        System.out.println("\n" + INDENT + "Press [ENTER] to continue...");
        try{
            System.in.read();
        }catch(Exception e){
            
        }
    }
}
