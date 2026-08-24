
package aTM;

import java.util.Scanner;

public class AtmMachine {
    public static void main(String[] args){
        
        Scanner scan = new Scanner(System.in);
        
        System.out.println("Enter yuor account name");
        String accountName = scan.nextLine();
        
        System.out.println("Enter yuor account type");
        String accountType = scan.nextLine();
        
        System.out.println("Enter yuor account pin");
        int accountPin = scan.nextInt();
    }
}
