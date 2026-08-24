
package runtime;


public class StringIndexOutOfBoundExceptionHandling {
    public static void main(String args[]){
        try{
        String myName = "Mercy Ben";
        System.out.println(myName.charAt(5));
        }
        catch(StringIndexOutOfBoundsException e){
            System.out.println("Invalid String index");
        }
    }
}
