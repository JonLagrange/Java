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
        sb.append("�����");
        
        //String str = String.valueOf(d);      //String.valueOf(double d) : �� double ���� d ת�����ַ��� 
        String str=new DecimalFormat("0.00").format(d);
        //System.out.println(str);
        
        //����С����ǰ�Ľ��                                                                                                                     int indexOf(String str): ����ָ���ַ����ַ����е�һ�γ��ִ�������
        String str1 = str.substring(0, str.indexOf('.'));    //substring() ���������ַ��������ַ���,ԭ��substring(int beginIndex, int endIndex)
        if (!str1.equals("0")) {
            int n = 1, length = str1.length();
            String[] strs = new String[length];
            
            while (length > 0) {
                String string = convertToUpper(str1.substring(length - 1, length));
                //ÿ4λһ�� <����ʮ���١�ǧ>
                switch (n % 4) {
                
                case 1:
                    //0λ��"Ԫ"��4λ��"��"��8λ��"��"��12λ"��"��16λ��"��"������������
                    int n1 = n / 4;
                    if (n1 == 0) strs[n - 1] = string + "Ԫ"; 
                    else {
                        if (n1 % 2 == 0) strs[n - 1] = string + "��"; 
                        else strs[n - 1] = string + "��";
                    }
                    break;
                    
                case 2:
                    if (!string.equals("��")) strs[n - 1] = string + "ʰ"; 
                    else strs[n - 1] = "��";
                    break;
                    
                case 3:
                    if (!string.equals("��")) strs[n - 1] = string + "��"; 
                    else strs[n - 1] = "��";
                    break;
                    
                case 0:
                    if (!string.equals("��")) strs[n - 1] = string + "Ǫ";
                    else strs[n - 1] = "��";
                    break;
                    
                default:
                    break;
                }
                n++;
                length--;
            }
            //�����鵹����ʾС������ߵĽ��
            for (int i = strs.length - 1; i >= 0; i--) sb.append(strs[i]);
        }
        //����С�����Ľ��
        String str2 = str.substring(str.indexOf('.') + 1);
        if (str2.equals("00")) sb.append("��");   //С���������0�����"��" 
        else {
            sb.append(convertToUpper(str2.substring(0, 1)) + "��");
            sb.append(convertToUpper(str2.substring(1)) + "��");
        }
 
        String result = sb.toString();
        //��������ظ���"��"ɾ��
        while (result.contains("����")) {
            result = result.replace("����", "��");
        }
        //��"Ԫ"��"��"��"��"ǰ��"��"ɾ��
        result = result.replace("����", "��");
        result = result.replace("����", "��");
        result = result.replace("��Ԫ", "Ԫ");
        result = result.replace("Ҽʰ", "ʰ");
        result = result.replace("���", "");
        result = result.replace("���", "");
 
        return result;
    }
    
    //����������0-9ת���ɴ�д
    public static String convertToUpper(String c) {
        String rString = "";
        switch (Integer.valueOf(c)) {
        case 0:
            rString = "��";
            break;
        case 1:
            rString = "Ҽ";
            break;
        case 2:
            rString = "��";
            break;
        case 3:
            rString = "��";
            break;
        case 4:
            rString = "��";
            break;
        case 5:
            rString = "��";
            break;
        case 6:
            rString = "½";
            break;
        case 7:
            rString = "��";
            break;
        case 8:
            rString = "��";
            break;
        case 9:
            rString = "��";
            break;
        default:
            rString = "";
            break;
        }
        return rString;
    }
}