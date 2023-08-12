import java.util.Scanner;

public class LinearGrammars {

    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);

        int n= s.nextInt();  //تعداد متغیر
        int t= s.nextInt(); // اندیس متغیر شروع
        int m= s.nextInt(); String extra=s.nextLine(); //تعداد قواعد

        String[] grammars=new String[m];

        for (int i = 0; i < m; i++) {
            grammars[i]=s.nextLine();
        }


        String[] newGrammars1=new String[m+1];
        for (int i = 0; i < m; i++) {
            newGrammars1[i]= grammars[i];
        }
        newGrammars1[m]= n+1+" "+t+" "+"-";




        String[] newGrammars2=new String[m+1];



        for (int i = 0; i < m+1; i++) {
            String grammar= newGrammars1[i];
            String string= grammar.substring(grammar.indexOf(' ')+1);

            String s1= grammar.substring(0,grammar.indexOf(' '));
            String s2= string.substring(0,string.indexOf(' '));
            String s3= string.substring(string.indexOf(' ')+1);

            boolean equals = s1.equals(String.valueOf(n+1));

            if (equals && s2.equals("0")){
                newGrammars2[i]=grammar;
            }

            if (!equals && s2.equals("0")){
                newGrammars2[i]=n+1+" "+s1+" "+s3;
            }

            if (!equals && !s2.equals(String.valueOf(n+1)) && !s2.equals("0")){
                newGrammars2[i]=s2+" "+s1+" "+s3;
            }

            if (equals && !s2.equals(String.valueOf(n+1)) && !s2.equals("0")){
                newGrammars2[i]=s2+" 0 "+s3;
            }
        }









        System.out.println(n+1);
        System.out.println(n+1);
        System.out.println(m+1);
        for (int i = 0; i < m+1; i++) {
            System.out.println(newGrammars2[i]);
        }
    }

}
