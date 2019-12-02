package com.advent;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Utils {
    public static boolean isPermutation(int num1, int num2) {
        return isPermutation(Integer.toString(num1), Integer.toString(num2));
    }

    public static boolean isPermutation(String str1, String str2) {
        String[] chars1 = str1.split("");
        String[] chars2 = str2.split("");
        if(chars1.length != chars2.length) return false;
        HashMap<String, Integer> charCount = new HashMap<>();
        for (String s: chars1) {
            if (charCount.containsKey(s)) {
                charCount.put(s, charCount.get(s) + 1);
            } else {
                charCount.put(s, 1);
            }
        }
        for (String s: chars2) {
            if (charCount.containsKey(s)) {
                charCount.put(s, charCount.get(s) - 1);
            } else {
                return false;
            }
        }
        for (String key: charCount.keySet()) {
            if (charCount.get(key) != 0) {
                return false;
            }
        }
        return true;
    }

    private static int distinctPrimeFactors(long num) {
        ArrayList<Integer> primeFactors = new ArrayList<Integer>();
        while(!isPrime(num)) {
            int i =2;
            while(num%i!=0) {
                i++;
                while(!isPrime(i)) {
                    i++;
                }
            }
            if(!primeFactors.contains(i)) {
                primeFactors.add(i);
            }
            num = num/i;
        }
        if(!primeFactors.contains((int)num)) {
            primeFactors.add((int) num);
        }
        return primeFactors.size();
    }

    private static boolean isNPandigital(String num,int n) {
        if(num.length() !=n) return false;
        for(int i = 1; i <n+1;i++) {
            if(!num.contains(Integer.toString(i))) {
                return false;
            }
        }
        return true;
    }

    private static boolean isTrunctable(BigInteger i) {
        if(!isPrime(i)) {
            return false;
        }
        BigInteger left = i;
        BigInteger right = i;

        //right trunctable
        while(right.toString().length()>1) {
            right = new BigInteger(right.toString().substring(0,right.toString().length()-1));
            if(!isPrime(right)) {
                return false;
            }
        }
        //left trunctable
        while(left.toString().length()>1) {
            left = new BigInteger(left.toString().substring(1));
            if(!isPrime(left)) {
                return false;
            }
        }
        return true;
    }

    private static boolean isPrime(BigInteger num) {
        if(num.compareTo(new BigInteger("2")) == 0) return true;
        if(num.compareTo(BigInteger.ONE) == 0) return false;
        if(num.compareTo(BigInteger.ZERO) <0) num = num.multiply(new BigInteger("-1"));
        if(num.mod(new BigInteger("2")).compareTo(new BigInteger("0")) == 0)return false;
        for(BigInteger i =new BigInteger("3");i.compareTo(num.divide(new BigInteger("2"))) < 0;i= i.add(new BigInteger("2"))) {
            if(num.mod(i).compareTo(BigInteger.ZERO)==0) {
                return false;
            }
        }
        return true;
    }

    private static boolean isPalindrome(BigInteger bigInteger) {
        String numStr = bigInteger.toString();
        if(numStr.endsWith("0")) return false;
        String reverse = "";
        for(int i = numStr.length()-1;i>=0;i--) {
            reverse += numStr.substring(i, i+1);
        }
        return numStr.equals(reverse);
    }

    private static double[] removeCommon(double numerator, double denominator) {
        String[] numStr = Double.toString(numerator).split("");
        String[] deStr = Double.toString(denominator).split("");
        int numindex = -1;
        int deindex = -1;
        for(int i =0;i< 2;i++) {
            for(int e=0;e<2;e++ ) {
                if(numStr[i].equals(deStr[e]) && !numStr[i].equals("0")) {
                    numindex = i;
                    deindex = e;
                }
            }
        }
        if(numindex == -1) {
            return null;
        }
        double[] rv = {Double.parseDouble(numStr[(numindex+1)%2]), Double.parseDouble(deStr[(deindex+1)%2])};
        return rv;
    }

    private static boolean is9Pandigital(String num) {
        if(num.length() !=9) return false;
        for(int i = 1; i <10;i++) {
            if(!num.contains(Integer.toString(i))) {
                return false;
            }
        }
        return true;
    }

    private static int sumOfFifthPows(int num) {
        String[] digits = Integer.toString(num).split("");
        int total = 0;
        for(String i: digits) {
            total+=Math.pow(Integer.parseInt(i),5);
        }
        return total;
    }

    private static int maxPrimesGenerated(int a, int b) {
        boolean done = false;
        int generated = 0;
        for(int n = 0; !done;n++) {
            if(isPrime((long)Math.pow(n,2) + a*n + b)) {
                generated++;
            }else {
                done = true;
            }
        }
        return generated;
    }

    private static boolean isAPairSum(ArrayList<Integer> abundantNums, int sum) {
        HashSet<Integer> seen = new HashSet<Integer>();
        for(Integer i: abundantNums) {
            if(seen.contains(i) || i+i ==sum) {

                return true;

            }
            seen.add(sum-i);
        }

        return false;
    }

    private static int wordtoNumber(String word) {
        int total = 0;
        String[] letters = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
        String[] chars = word.split("");
        for(String i: chars) {
            for(int e = 0; e < letters.length;e++) {
                if(i.equals(letters[e])) {
                    total += e+1;
                    e=letters.length;
                }
            }
        }
        return total;
    }
    private static int properDivisorsSum(int num) {
        int divisorsSum = 1;
        int maxNum = (int) Math.sqrt(num);
        for(int i = 2;i<=maxNum;i++) {
            if(num%i==0) {
                divisorsSum+=i;
                int d = num/i;
                if(d!=i) {
                    divisorsSum+=d;
                }
            }
        }
        return divisorsSum;
    }

    private static boolean isLeapYear(int year) {
        if(year%4==0 && (year%100!=0 || year%400==0)) {
            return true;
        }
        return false;
    }
    private static String readFromFile(String file) throws IOException {
        String out = "";
        BufferedReader br = new BufferedReader(new FileReader(file));
        try {
            String line = "";
            int row = 0;
            while ((line = br.readLine()) != null && line !="") {
                out +=line + "\n";
            }
        } catch(FileNotFoundException fnfe){
            Logger.log("Invalid File Path");
        }catch(Exception e){
            Logger.log("Encountered Error ");
            Logger.error(e);
        }finally {
            br.close();
        }

        return out;
    }

    private static String numberToWords(int num) {
        String[] ones = {"","one","two","three","four","five","six","seven","eight","nine"};
        String[] tens = {"","ten","twenty","thirty","forty","fifty","sixty","seventy","eighty","ninety"};
        String[] teens = {"ten","eleven","twelve","thirteen","fourteen","fifteen","sixteen","seventeen","eighteen","nineteen"};
        if(num == 1000) {
            return "one thousand";
        }
        String out = "";
        out = out + ones[num/100];
        if(!out.equals("")) {
            out = out + " hundred";
        }
        num = num%100;
        if(num != 0 && !out.equals("")) {
            out = out + " and ";
        }
        if(num/10 == 1) {
            out = out + teens[num%10];
        }else {
            out = out + tens[num/10];
            if(num/10 != 0) {
                out = out + " ";
            }
            out = out + ones[num%10];
        }
        return out;
    }

    private static String removeSpaces(String str) {
        String out = "";
        for(int i = 0; i < str.length();i++) {
            String c = str.substring(i, i+1);
            if(!c.equals(" ")) {
                out = out+c;
            }
        }
        return out;
    }

    public static long numRoutes(int x, int y) {
        if(x == 20 && y == 20) {
            return 1;
        }
        long total = 0;
        if(x <20){
            total+= numRoutes(x+1,y);
        }
        if(y<20) {
            total+=numRoutes(x,y+1);
        }
        return total;
    }
    public static int collatzSeq(long num) {
        int length = 1;

        while(num != 1) {
            if(num%2 ==0) {
                num = num/2;
            }else {
                num = (3*num)+1;
            }
            length++;
        }

        return length;
    }
    private static int divisors(long triNumVal) {
        int divisors = 2;
        for(int i = 2;i<=triNumVal/2;i++) {
            if(triNumVal%i==0) {
                divisors++;
            }
        }
        return divisors;
    }
    public static boolean isPrime(long num) {
        if(num == 1)return false;
        if(num == 2)return true;
        if(num < 0) num*=-1;
        if(num % 2 == 0)return false;
        for(long i = 3;i < num / 2L; i+=2) {
            if(num%i==0) {
                return false;
            }
        }
        return true;
    }

    public static String knotHash(String in) {
        String[] lengths = in.split("");
        int[] ls = new int[lengths.length + 5];
        for (int i = 0; i < lengths.length; i++) {
            ls[i] = (int) lengths[i].charAt(0);
        }
        ls[lengths.length] = 17;
        ls[lengths.length + 1] = 31;
        ls[lengths.length + 2] = 73;
        ls[lengths.length + 3] = 47;
        ls[lengths.length + 4] = 23;

        int[] list = new int[256];
        for (int i = 0; i < list.length; i++) {
            list[i] = i;
        }
        int pos = 0;
        int skip = 0;
        for (int e = 0; e < 64; e++) {
            for (int length : ls) {
                //Reverse
                for (int i = pos; i < pos + (length / 2); i++) {
                    int pos1 = i % list.length;
                    int pos2 = (length + 2 * pos - i - 1) % list.length;
                    int tmp = list[pos1];
                    list[pos1] = list[pos2];
                    list[pos2] = tmp;
                }
                //Prep for next reverse
                pos += length + skip;
                pos %= list.length;
                skip++;
            }
        }

        //XOR
        int[] denseHash = new int[16];
        for (int i = 0; i < denseHash.length; i++) {
            for (int e = denseHash.length*i; e < denseHash.length*(i+1); e++) {
                denseHash[i] ^= list[e];
            }
        }

        //Hex
        String out = "";
        for(int i: denseHash) {
            String hex = Integer.toHexString(i);
            out += (hex.length() > 1 ? "" : "0") + hex;
        }
        return out;
    }

    public static String pad(String toPad, int size, String padding) {
        int times = size - toPad.length();
        for (int i = 0; i < times; i++) {
            toPad = padding + toPad;
        }
        return toPad;
    }
}
