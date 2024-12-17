public class ArithmeticCalculation {
    ArithmeticOperationsService arithmeticOperationsService = new ArithmeticOperationsServiceImpl();
    public static void main(String[] args) {
        ArithmeticDemo arithmeticDemo = new ArithmeticDemo();
        System.out.println("Hello world!");
        String number1 = "123,658,435,876";
        String number2 = "23,658,435,876";
        String sum = null;
        try {
            sum = arithmeticDemo.arithmeticOperationsService.addNumbers(number1, number2);
            System.out.println(sum);
        } catch (IllegalAccessException e) {
            System.out.println(e.getMessage());
        }
        try {
            sum = arithmeticDemo.arithmeticOperationsService.addNumber2(number1, number2);
            System.out.println(sum);
        } catch (IllegalAccessException e) {
            System.out.println(e.getMessage());
        }
        try {
            sum = arithmeticDemo.arithmeticOperationsService.getNthFibonacciNumber(100);
            System.out.println(sum);
            System.out.println(String.join("", sum.split(",")));
        } catch (IllegalAccessException e) {
            System.out.println(e.getMessage());
        }
        //System.out.println(Integer.parseInt(number1) + Integer.parseInt(number2));
    }
}
interface ArithmeticOperationsService {
    String addNumbers(String number1, String number2) throws IllegalAccessException;
    String addNumber2(String number1, String number2) throws IllegalAccessException;
    String getNthFibonacciNumber(int n) throws IllegalAccessException;
}

class ArithmeticOperationsServiceImpl implements ArithmeticOperationsService {

    @Override
    public String addNumbers(String number1, String number2) throws IllegalAccessException {
        int i = number1.length()-1;
        int j = number2.length()-1;
        StringBuilder sum = new StringBuilder();
        int carry = 0;
        while (i>=0 || j>=0) {
            int curr;
            if (i>=0 && j>=0) {
                boolean skipChar = false;
                if (skipCharacter(String.valueOf(number1.charAt(i)))) {
                    skipChar = true;
                    i-=1;
                }
                if (skipCharacter(String.valueOf(number2.charAt(j)))) {
                    skipChar = true;
                    j-=1;
                }
                if (skipChar)
                    continue;
                curr = validateAndParseChar(String.valueOf(number1.charAt(i))) + validateAndParseChar(String.valueOf(number2.charAt(j))) + carry;
            }
            else if (i>=0) {
                if (skipCharacter(String.valueOf(number1.charAt(i)))) {
                    i-=1;
                    continue;
                }
                curr = validateAndParseChar(String.valueOf(number1.charAt(i))) + carry;
            }
            else {
                if (skipCharacter(String.valueOf(number2.charAt(j)))) {
                    j-=1;
                    continue;
                }
                curr = validateAndParseChar(String.valueOf(number2.charAt(j))) + carry;
            }
            carry = Math.floorDiv(curr, 10);
            curr = curr%10;
            sum.insert(0, String.valueOf(curr));
            i-=1;
            j-=1;
            //System.out.println(sum);
        }
        if (carry>0) {
            sum.insert(0, String.valueOf(carry));
        }
        return sum.toString();
    }

    @Override
    public String addNumber2(String number1, String number2) throws IllegalAccessException {
        int i = number1.length()-1;
        int j = number2.length()-1;
        StringBuilder sum = new StringBuilder();
        int carry = 0;
        int count = 0;
        while (i>=0 || j>=0) {
            if (count == 3) {
                count = 0;
                i-=1;
                j-=1;
                sum.insert(0, ",");
                continue;
            }
            int curr;
            if (i>=0 && j>=0) {
                curr = validateAndParseChar(String.valueOf(number1.charAt(i))) + validateAndParseChar(String.valueOf(number2.charAt(j))) + carry;
            }
            else if (i>=0) {
                curr = validateAndParseChar(String.valueOf(number1.charAt(i))) + carry;
            }
            else {
                curr = validateAndParseChar(String.valueOf(number2.charAt(j))) + carry;
            }
            carry = Math.floorDiv(curr, 10);
            curr = curr%10;
            sum.insert(0, String.valueOf(curr));
            i-=1;
            j-=1;
            count+=1;
            //System.out.println(sum);
        }
        if (carry>0) {
            if (count==3)
                sum.insert(0, ",");
            sum.insert(0, String.valueOf(carry));
        }
        return sum.toString();
    }

    @Override
    public String getNthFibonacciNumber(int n) throws IllegalAccessException {
        String num1 = "0";
        String num2 = "1";
        if (n==0)
            return num1;
        if (n==1)
            return num2;
        while (n>1) {
            String temp = num2;
            num2 = addNumber2(num1, num2);
            num1 = temp;
            n-=1;
        }
        return num2;
    }

    private boolean skipCharacter(String str) {
        return str.equalsIgnoreCase(",");
    }

    private Integer validateAndParseChar(String string) throws IllegalAccessException {
        try {
            return Integer.parseInt(string);
        }
        catch (Exception exception) {
            throw new IllegalAccessException("Only integer strings Allowed");
        }
    }
}