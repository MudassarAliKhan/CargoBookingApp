package com.pasksoftvalley.cargobookingapp.Model;

public class Validator {
    public static boolean isSignUpValid(String name, String cnic, String phone, String countryCode)
    {
        boolean check=true;
        if(name==null||cnic==null||phone==null||countryCode==null)
        {
            check=false;
        }

        else {
            if (cnic.length() != 13) {
                check=false;

            }

            if (countryCode.length()>3)
            {
                check=false;
            }
            if (phone.length()<8)
            {
                check=false;
            }
        }

        return check;
    }
/*
    public static boolean validateCase(Case c)
    {
        boolean check=true;
        if(c.getTitle()==null||c.getTitle().isEmpty()||c.getTitle().equals(""))
        {
            check=false;
        }

        if (c.getDescription()==null||c.getDescription().isEmpty()||c.getDescription().equals(""))
        {
            check=false;
        }

        return check;
    }

    public static boolean validateNewInformation(ArrayList<HistoryEntity> historyList)
    {
        boolean check=true;

        if(historyList.isEmpty())
        {
            check=false;
        }

        int countEmpty=0;
        for(int i=0;i<historyList.size();i++)
        {

            HistoryEntity entity=historyList.get(i);
            if(entity.getKey().isEmpty()&&entity.getValue().isEmpty())
            {
                countEmpty++;
            }
        }

        if(countEmpty==historyList.size())
        {
            check=false;                  //means all empty
        }

        return check;
    }

    public static ArrayList<HistoryEntity> getFormattedList(ArrayList<HistoryEntity> historyList)
    {
        for(int i=0;i<historyList.size();i++)
        {
            HistoryEntity entity=historyList.get(i);
            if(entity.getKey().isEmpty()&&entity.getValue().isEmpty())
            {
                historyList.remove(i);
            }
        }

        return historyList;
    }
*/
}