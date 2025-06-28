import java.util.Scanner;

public class jjjj {
    static Scanner input = new Scanner(System.in);
    static String defaultPIN = "2025";
    static double balance = 500.0;
    static double salaamBalance = 50.0;
    static String[] transactionLog = new String[500];
    static int logIndex = 0;
    static int failedAttempts = 0;

    public static void main(String[] args) {
        displayWelcome();
        if (handleLogin()) {
            showMainMenu();
        } else {
            System.out.println(" Waxaa dhacay 3 isku day khaldan. Xisaabta waa la xiray.");
        }
    }

    public static void displayWelcome() {
        System.out.println("");
        System.out.println("     WAA KU SOO DHAWEEYNEE EVC PLUS V2      ");
        System.out.println("");
    }

    public static boolean handleLogin() {
        System.out.print("Geli USSD code-ka cusub (*770#): ");
        String code = input.nextLine().trim();
        if (!code.equals("*770#")) {
            System.out.println(" Code-ka waa qalad. Program-ka wuu istaagay.");
            return false;
        }

        while (failedAttempts < 3) {
            System.out.print("Geli PIN-kaaga cusub (4 lambar): ");
            String pin = input.nextLine().trim();
            if (pin.equals(defaultPIN)) {
                System.out.println("✅ Waad ku guuleysatay gelitaanka.");
                return true;
            } else {
                failedAttempts++;
                System.out.println(" PIN-ka waa khaldan.");
                if (failedAttempts == 2) {
                    System.out.println("⚠ Tani waa fursadaada ugu dambeysa!");
                }
            }
        }
        return false;
    }

    public static void showMainMenu() {
        boolean keepGoing = true;
        while (keepGoing) {
            System.out.println("\n======= MENU EVC+ V2 =======");
            System.out.println("1. Haraaga Eeg");
            System.out.println("2. Dir Lacag");
            System.out.println("3. La Bax");
            System.out.println("4. Airtime (Naftaada)");
            System.out.println("5. Airtime (Qof kale)");
            System.out.println("6. Bixi Biil");
            System.out.println("7. Salaam Bank");
            System.out.println("8. Isticmaal Voucher");
            System.out.println("9. Bedel PIN");
            System.out.println("10. Taariikhda");
            System.out.println("11. Ka Bax");
            System.out.print("Dooro (1-11): ");
            String choice = input.nextLine();

            switch (choice) {
                case "1": checkBalance(); break;
                case "2": sendMoney(); break;
                case "3": withdrawMoney(); break;
                case "4": buyAirtimeSelf(); break;
                case "5": buyAirtimeOthers(); break;
                case "6": payBills(); break;
                case "7": manageSalaamBank(); break;
                case "8": useVoucher(); break;
                case "9": changePIN(); break;
                case "10": viewLog(); break;
                case "11":
                    System.out.println("Mahadsanid. Nabad gelyo!");
                    keepGoing = false;
                    break;
                default:
                    System.out.println(" Doorasho aan sax ahayn.");
            }
        }
    }

    public static void checkBalance() {
        System.out.printf("Haraagaaga waa: $%.2f\n", balance);
        saveToLog("Eegay haraag - $" + balance);
    }

    public static void sendMoney() {
        System.out.print("Lambarka aad dirayso: ");
        String number = input.nextLine();
        System.out.print("Qadarka: ");
        double amount = getAmount();
        if (amount <= balance) {
            balance -= amount;
            System.out.println(" Diray $" + amount + " -> " + number);
            saveToLog("Diray $" + amount + " -> " + number);
        } else {
            System.out.println(" Haraaga kuma filna.");
        }
    }

    public static void withdrawMoney() {
        System.out.print("Qadarka la baxayo: ");
        double amount = getAmount();
        if (amount <= balance) {
            balance -= amount;
            System.out.println(" La baxay $" + amount);
            saveToLog("La baxay $" + amount);
        } else {
            System.out.println(" Ma haysid lacag kugu filan.");
        }
    }

    public static void buyAirtimeSelf() {
        System.out.print("Qadarka Airtime ($): ");
        double amount = getAmount();
        if (amount <= balance) {
            balance -= amount;
            System.out.println("Iibsaday Airtime $" + amount + " (naftaada)");
            saveToLog("Airtime self $" + amount);
        } else {
            System.out.println(" Haraaga kuma filna.");
        }
    }

    public static void buyAirtimeOthers() {
        System.out.print("Lambarka qofka kale: ");
        String number = input.nextLine();
        System.out.print("Qadarka Airtime: ");
        double amount = getAmount();
        if (amount <= balance) {
            balance -= amount;
            System.out.println(" Diray Airtime $" + amount + " -> " + number);
            saveToLog("Airtime others $" + amount + " -> " + number);
        } else {
            System.out.println(" Haraaga kuma filna.");
        }
    }

    public static void payBills() {
        System.out.println("1. Karti\n2. Biyaha\n3. Internet");
        System.out.print("Dooro nooca biilka: ");
        String type = input.nextLine();
        System.out.print("Qadarka biilka: ");
        double amount = getAmount();
        if (amount <= balance) {
            balance -= amount;
            System.out.println("Biil " + type + " oo la bixiyay: $" + amount);
            saveToLog("Biil " + type + " $" + amount);
        } else {
            System.out.println(" Lacagta kuma filna.");
        }
    }

    public static void manageSalaamBank() {
        System.out.println("1. Ku shub Salaam Bank");
        System.out.println("2. Ka bax Salaam Bank");
        System.out.println("3. Eeg Haraaga Salaam Bank");
        System.out.print("Dooro: ");
        String choice = input.nextLine();
        switch (choice) {
            case "1": depositSalaam(); break;
            case "2": withdrawSalaam(); break;
            case "3": checkSalaam(); break;
            default: System.out.println(" Doorasho khaldan.");
        }
    }

    public static void depositSalaam() {
        System.out.print("Qadarka lagu shubayo: ");
        double amount = getAmount();
        if (amount <= balance) {
            balance -= amount;
            salaamBalance += amount;
            System.out.println("Ku shubtay $" + amount + " Salaam Bank");
            saveToLog("Ku shubay Salaam $" + amount);
        } else {
            System.out.println(" Haraaga kuma filna.");
        }
    }

    public static void withdrawSalaam() {
        System.out.print("Qadarka laga baxayo: ");
        double amount = getAmount();
        if (amount <= salaamBalance) {
            salaamBalance -= amount;
            balance += amount;
            System.out.println(" Ka baxday Salaam Bank $" + amount);
            saveToLog("Ka baxay Salaam $" + amount);
        } else {
            System.out.println(" Lacag kuma filna Salaam Bank.");
        }
    }

    public static void checkSalaam() {
        System.out.printf("Salaam Bank Haraaga: $%.2f\n", salaamBalance);
        saveToLog("Eegay Salaam Bank - $" + salaamBalance);
    }

    public static void useVoucher() {
        System.out.print("Geli Voucher Code: ");
        String code = input.nextLine();
        if (code.equalsIgnoreCase("NEW456")) {
            balance += 100.0;
            System.out.println(" Voucher sax ah. $100 lagu daray.");
            saveToLog("Voucher +$100");
        } else {
            System.out.println(" Voucher-ka waa khaldan.");
        }
    }

    public static void changePIN() {
        System.out.print("PIN hore: ");
        String oldPin = input.nextLine();
        if (oldPin.equals(defaultPIN)) {
            System.out.print("PIN cusub: ");
            String newPin = input.nextLine();
            if (newPin.length() == 4 && newPin.matches("\\d+")) {
                defaultPIN = newPin;
                System.out.println(" PIN waa la bedelay.");
                saveToLog("Bedelay PIN");
            } else {
                System.out.println(" PIN cusub waa khaldan.");
            }
        } else {
            System.out.println(" PIN hore ma saxna.");
        }
    }

    public static void viewLog() {
        System.out.println("\n Taariikhda:");
        if (logIndex == 0) {
            System.out.println("– Waxba laguma sameyn wali.");
        } else {
            for (int i = 0; i < logIndex; i++) {
                System.out.println((i + 1) + ". " + transactionLog[i]);
            }
        }
    }

    public static double getAmount() {
        try {
            return Double.parseDouble(input.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("❌ Qadarka waa khaldan.");
            return 0;
        }
    }

    public static void saveToLog(String action) {
        if (logIndex < transactionLog.length) {
            transactionLog[logIndex++] = action;
        }
    }
}