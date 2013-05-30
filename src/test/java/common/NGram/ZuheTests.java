package common.NGram;

import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Dong ai hua
 * Date: 13-5-22
 * Time: 下午6:10
 * To change this template use File | Settings | File Templates.
 */
public class ZuheTests {
    private static int n = 3;   // all zuhe which have n chars
    private static int count = 0;    // just for pretty print

    @Test
    public void testZuhe() {
        find("_-0123456789", "", 0);
    }

    public void find(String str, String firstChar, int position) {
        String temp = firstChar;
        if (firstChar.length() == n) {
            count++;
            System.out.print(firstChar + " ");

            if (count % 10 == 0)
                System.out.println();
            return;
        }
        for (int k =position; k < str.length(); k++) {
            firstChar = temp;
            firstChar += str.charAt(k);
            find(str, firstChar, k+1);
        }

    }

    @Test
    public void allZhuhe() {
        combine(new String("_-0123456789"));
    }

    void combine(String str) {
        char[] in = str.toCharArray();
        StringBuffer out = new StringBuffer();
        allCombine(in, out, 0);
    }

    void allCombine(char[] in, StringBuffer out, int start) {
        for (int i = start; i < in.length; i++) {
            out.append(in[i]);
            System.out.println(out);
            if (i < in.length - 1) {  //如果有下个元素，则递归折行
                allCombine(in, out, i + 1);
            }
            out.setLength(out.length() - 1);  //清空out
        }
    }


    @Test
    public void nZhuhe() {
        List<String> ls = myCprint("abc", 2);
        for(String str : ls){
            System.out.println(str);
        }
    }

    public int c(int n, int k){
        if( n == k || k == 0){
            return 1;
        }else if( k == 1){
            return n;
        }else{
            return c(n - 1, k - 1) + c(n -1 , k);
        }
    }

    public List<String> myCprint(String str, int k){
        List<String> tmp = new ArrayList<String>();
        if(str.length() == k || k == 0){
            tmp.add(str);
        }else if(k == 1){
            for(char c : str.toCharArray()){
                tmp.add(String.valueOf(c));
            }
        }
        else{
            List<String> tmp1 = myCprint(str.substring(0, str.length() - 1), k - 1);
            for(String str1 : tmp1){
                tmp.add(str.substring(str.length() - 1) + str1);
            }
            tmp.addAll(myCprint(str.substring(0, str.length() - 1), k));

        }
        return tmp;
    }



    @Test
    public  void permutation() {
        permutation("", "abc");
    }

    private void permutation(String prefix, String str) {
        int n = str.length();
        if (n == 0) System.out.println(prefix);
        else {
            for (int i = 0; i < n; i++)
                permutation(prefix + str.charAt(i), str.substring(0, i) + str.substring(i+1, n));
        }

    }

    @Test
    public void allZhuhe2() {
        combine("_-0123456789", new StringBuffer(), 0);
    }

    void combine(String instr, StringBuffer outstr, int index)
    {
        for (int i = index; i < instr.length(); i++)
        {
            outstr.append(instr.charAt(i));
            System.out.println(outstr);
            combine(instr, outstr, i + 1);
            outstr.deleteCharAt(outstr.length() - 1);
        }
    }









}