package com.test.renminbi;
import java.text.DecimalFormat;
import java.util.Scanner;
 
public class Renminbi {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNextDouble()) {
            double d = in.nextDouble();
            System.out.println(getUppercase(d));
        }
    }
 
    public static String getUppercase(double d) {
        StringBuilder sb = new StringBuilder();
        sb.append("人民币");
        
        //String str = String.valueOf(d);      //String.valueOf(double d) : 将 double 变量 d 转换成字符串 
        String str=new DecimalFormat("0.00").format(d);
        //System.out.println(str);
        
        //处理小数点前的金额                                                                                                                     int indexOf(String str): 返回指定字符在字符串中第一次出现处的索引
        String str1 = str.substring(0, str.indexOf('.'));    //substring() 方法返回字符串的子字符串,原型substring(int beginIndex, int endIndex)
        if (!str1.equals("0")) {
            int n = 1, length = str1.length();
            String[] strs = new String[length];
            
            while (length > 0) {
                String string = convertToUpper(str1.substring(length - 1, length));
                //每4位一组 <个、十、百、千>
                switch (n % 4) {
                
                case 1:
                    //0位加"元"，4位加"万"，8位加"亿"，12位"万"，16位加"亿"。。。。。。
                    int n1 = n / 4;
                    if (n1 == 0) strs[n - 1] = string + "元"; 
                    else {
                        if (n1 % 2 == 0) strs[n - 1] = string + "亿"; 
                        else strs[n - 1] = string + "万";
                    }
                    break;
                    
                case 2:
                    if (!string.equals("零")) strs[n - 1] = string + "拾"; 
                    else strs[n - 1] = "零";
                    break;
                    
                case 3:
                    if (!string.equals("零")) strs[n - 1] = string + "佰"; 
                    else strs[n - 1] = "零";
                    break;
                    
                case 0:
                    if (!string.equals("零")) strs[n - 1] = string + "仟";
                    else strs[n - 1] = "零";
                    break;
                    
                default:
                    break;
                }
                n++;
                length--;
            }
            //将数组倒序显示小数点左边的金额
            for (int i = strs.length - 1; i >= 0; i--) sb.append(strs[i]);
        }
        //处理小数点后的金额
        String str2 = str.substring(str.indexOf('.') + 1);
        if (str2.equals("00")) sb.append("整");   //小数点后若等0，则加"整" 
        else {
            sb.append(convertToUpper(str2.substring(0, 1)) + "角");
            sb.append(convertToUpper(str2.substring(1)) + "分");
        }
 
        String result = sb.toString();
        //将多余的重复的"零"删掉
        while (result.contains("零零")) {
            result = result.replace("零零", "零");
        }
        //将"元"、"万"、"亿"前的"零"删掉
        result = result.replace("零亿", "亿");
        result = result.replace("零万", "万");
        result = result.replace("零元", "元");
        result = result.replace("壹拾", "拾");
        result = result.replace("零角", "");
        result = result.replace("零分", "");
 
        return result;
    }
    
    //阿拉伯数字0-9转换成大写
    public static String convertToUpper(String c) {
        String rString = "";
        switch (Integer.valueOf(c)) {
        case 0:
            rString = "零";
            break;
        case 1:
            rString = "壹";
            break;
        case 2:
            rString = "贰";
            break;
        case 3:
            rString = "叁";
            break;
        case 4:
            rString = "肆";
            break;
        case 5:
            rString = "伍";
            break;
        case 6:
            rString = "陆";
            break;
        case 7:
            rString = "柒";
            break;
        case 8:
            rString = "捌";
            break;
        case 9:
            rString = "玖";
            break;
        default:
            rString = "";
            break;
        }
        return rString;
    }
}