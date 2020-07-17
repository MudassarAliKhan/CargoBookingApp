package com.pasksoftvalley.cargobookingapp.Model;
import android.webkit.MimeTypeMap;

import com.pasksoftvalley.cargobookingapp.Presenter.MainActivity;
import com.pasksoftvalley.cargobookingapp.View.SpinnerTypeArrays;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class BackWorks
{

    public static String beforeAt(String Email)
    {
        String[] split= Email.split("@");
        String splitString= split[0];

        if(splitString.contains("."))
        {
            splitString=splitString.replace('.','_');
        }

        return splitString;
    }

    public static String convertUNameToEmail(String uName)
    {
        return uName+"@gmail.com";
    }
    public static String createFullName(String firstName,String lastName)
    {
        String lName= firstName+" "+lastName;
        return lName;

    }

    public static String getCurrentDateAndTime()
    {
        String dateTime = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        return  dateTime;
    }

    public static String convertDateToStringWithTime(Date d)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return sdf.format(d);
    }

    public static Date convertStringToDate(String d)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Calendar calendar = Calendar.getInstance();
        String formatedDate = sdf.format(calendar.getTime());
        Date dateBirth=null;
        try {
            dateBirth = sdf.parse(formatedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateBirth;
    }


    static int monthsBetween(Date a, Date b) {
        Calendar cal = Calendar.getInstance();
        if (a.before(b)) {
            cal.setTime(a);
        } else {
            cal.setTime(b);
            b = a;
        }
        int c = 0;
        while (cal.getTime().before(b)) {
            cal.add(Calendar.MONTH, 1);
            c++;
        }
        return c - 1;
    }

    public static Date convertTimeStampToDate(long timeStamp)
    {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(timeStamp);
        Date date = c.getTime();
        return date;

    }
    public static double calculatePercentage(double obtMarks,double totalMarks)
    {
        double per=(obtMarks/totalMarks)*100;
        return per;
    }

    public static boolean validateGmail(String email)
    {
        char[] splittedEmail=email.toCharArray();

        boolean check=false;
        if(email.contains("@")&&email.contains("."))
        {
            String afterAtRate=email.substring(email.lastIndexOf("@")+1,email.length());
            if(splittedEmail[splittedEmail.length-4]=='.'&&afterAtRate.equals("gmail.com"))
            {
                check=true;
            }

            if(email.contains("!")||email.contains("&"))
            {
                check=false;
            }

        }

        return check;
    }

    public static boolean checkStringHasNoChar(String s)
    {
        boolean ch=false;
        char[] arr=s.toCharArray();
        for(int i=0;i<s.length();i++)
        {
            if(arr[i]>1&&arr[i]<150)
            {
                ch=true;
            }
        }
        return ch;
    }

    public static boolean versionLessThanLollipop()
    {
        boolean ch=false;
        if (android.os.Build.VERSION.SDK_INT <= android.os.Build.VERSION_CODES.KITKAT)
        {
            ch=true;
        }

        return ch;
    }


    public static int get_count_of_days(Date d1,Date d2) {
        int daysdiff = 0;
        long diff = d2.getTime() - d1.getTime();
        long diffDays = diff / (24 * 60 * 60 * 1000) + 1;
        daysdiff = (int) diffDays;
        return daysdiff;

    }

    public static String convertDateStringWithoutTime(Date d)
    {
        String dateString=convertDateToStringWithTime(d);
        int spIndex=dateString.lastIndexOf(" ");
        String dateConcated=dateString.substring(0,spIndex);
        return dateConcated;
    }

    public static long convertStringDateToTimeStamp(int day,int month, int yr)
    {
        String str_date=month+"-"+day+"-"+yr;
        DateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
        Date date = null;
        try {
            date = (Date)formatter.parse(str_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long output= 0;
        if (date != null) {
            output = date.getTime()/1000L;
        }
        String str=Long.toString(output);
        long timestamp = Long.parseLong(str) * 1000;
        return  timestamp;
    }

    public static long get30DaysOldTimeStamp(Date d)
    {
        long cutoff = new Date().getTime() - TimeUnit.MILLISECONDS.convert(30, TimeUnit.DAYS);
        return cutoff;
    }

    public static String convertLargeTextToShortDescription(String text)
    {
        String desc=null;

        if(text.length()>20)
        {
            desc=text.substring(0,20);
        }

        else
        {
            desc=text;
        }

        return desc;
    }

    public static String createCommonFirebaseKey(ArrayList<String> list)
    {
        Collections.sort(list);
        String commonNode="";
        for(int i=0;i<list.size();i++)
        {
            if(i==0) {
                commonNode += list.get(i);
            }

            else
            {
                commonNode+="-";
                commonNode+=list.get(i);
            }
        }

        return commonNode;
    }

    public static String convertDoubleMessageNodeToUName(String fullNode)
    {
        String[] splitted=fullNode.split("-");
        String targetUName="";

        if(!splitted[0].equals(MainActivity.getUName()))
        {
            targetUName=splitted[0];
        }

        else if(!splitted[1].equals(MainActivity.getUName()))
        {
            targetUName=splitted[1];
        }

        return  targetUName;
    }

    public static String getCountryNameByCode(String code)
    {

        return SpinnerTypeArrays.getCountryFromCountryCode(code);

    }

    public static String getCountryCodeByName(String name)
    {

        return SpinnerTypeArrays.getCodeFromCountryName(name);

    }

    public static String getExtension(String url)
    {
        String fileExt = MimeTypeMap.getFileExtensionFromUrl(url);
        return "."+fileExt;
    }

    public static String getFileNameFromPath(String path)
    {
        String filename=path.substring(path.lastIndexOf("/")+1);
        int dotPosition=filename.indexOf(".");
        String split= filename.substring(0,dotPosition);
        return split;
    }

    public static String getFileNameWithExtension(String path)
    {
        String filename=path.substring(path.lastIndexOf("/")+1);
        return filename;
    }

    public static String createRandomAudioFileLetters(int string){
        String RandomAudioFileLetters = "ABCDEFGHIJKLMNOP";
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder( string );
        int i = 0 ;
        while(i < string ) {
            stringBuilder.append(RandomAudioFileLetters.
                    charAt(random.nextInt(RandomAudioFileLetters.length())));

            i++ ;
        }
        return stringBuilder.toString();
    }

}
