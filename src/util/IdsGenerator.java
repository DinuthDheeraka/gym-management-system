package util;

import controller.MemberCrudController;
import controller.Mods;

import java.util.ArrayList;

public class IdsGenerator {

    public static String genarate(Mods mod,ArrayList<String> id){
        switch (mod){
            case MEMBER:
                if(id.size()==0){
                    return "MMB-000";
                }else{
                    String lastId = id.get(0);
                    int intValue = Integer.parseInt(id.get(0).substring(4,lastId.length()));
                    String incrementLastValue = getValidIntValue(String.valueOf(intValue));
                    return "MMB-"+incrementLastValue;
                }

            case TRAINER:
                if(id.size()==0){
                    return "TRA-000";
                }else{
                    String lastId = id.get(0);
                    int intValue = Integer.parseInt(id.get(0).substring(4,lastId.length()));
                    String incrementLastValue = getValidIntValue(String.valueOf(intValue));
                    return "TRA-"+incrementLastValue;
                }

            case SCHEDULE:

                if(id.size()==0){
                    return "SCH-000";
                }else{
                    String lastId = id.get(0);
                    int intValue = Integer.parseInt(id.get(0).substring(4,lastId.length()));
                    String incrementLastValue = getValidIntValue(String.valueOf(intValue));
                    return "SCH-"+incrementLastValue;
                }

            case EXERCISE:

                if(id.size()==0){
                    return "EXE-000";
                }else{
                    String lastId = id.get(0);
                    int intValue = Integer.parseInt(id.get(0).substring(4,lastId.length()));
                    String incrementLastValue = getValidIntValue(String.valueOf(intValue));
                    return "EXE-"+incrementLastValue;
                }

            case MEAL_PLAN:

                if(id.size()==0){
                    return "MLP-000";
                }else{
                    String lastId = id.get(0);
                    int intValue = Integer.parseInt(id.get(0).substring(4,lastId.length()));
                    String incrementLastValue = getValidIntValue(String.valueOf(intValue));
                    return "MLP-"+incrementLastValue;
                }

            case SUPPLIER:

                if(id.size()==0){
                    return "SPL-000";
                }else{
                    String lastId = id.get(0);
                    int intValue = Integer.parseInt(id.get(0).substring(4,lastId.length()));
                    String incrementLastValue = getValidIntValue(String.valueOf(intValue));
                    return "SPL-"+incrementLastValue;
                }

            case MY_ORDER:

                if(id.size()==0){
                    return "MOR-000";
                }else{
                    String lastId = id.get(0);
                    int intValue = Integer.parseInt(id.get(0).substring(4,lastId.length()));
                    String incrementLastValue = getValidIntValue(String.valueOf(intValue));
                    return "MOR-"+incrementLastValue;
                }

            case MEMBER_FITNESS_REPORT:

                if(id.size()==0){
                    return "MFR-000";
                }else{
                    String lastId = id.get(0);
                    int intValue = Integer.parseInt(id.get(0).substring(4,lastId.length()));
                    String incrementLastValue = getValidIntValue(String.valueOf(intValue));
                    return "MFR-"+incrementLastValue;
                }

            case MEMBER_MONTHLY_PAYEMENT:
                if(id.size()==0){
                    return "MMP-000";
                }else{
                    String lastId = id.get(0);
                    int intValue = Integer.parseInt(id.get(0).substring(4,lastId.length()));
                    String incrementLastValue = getValidIntValue(String.valueOf(intValue));
                    return "MMP-"+incrementLastValue;
                }

            case EQUIPMENT:
                if(id.size()==0){
                    return "EQP-000";
                }else{
                    String lastId = id.get(0);
                    int intValue = Integer.parseInt(id.get(0).substring(4,lastId.length()));
                    String incrementLastValue = getValidIntValue(String.valueOf(intValue));
                    return "EQP-"+incrementLastValue;
                }

            case SUPPLIMENT:

                if(id.size()==0){
                    return "SUP-000";
                }else{
                    String lastId = id.get(0);
                    int intValue = Integer.parseInt(id.get(0).substring(4,lastId.length()));
                    String incrementLastValue = getValidIntValue(String.valueOf(intValue));
                    return "SUP-"+incrementLastValue;
                }

            default:return null;
        }
    }

    public static String getValidIntValue(String value){
        int i = Integer.parseInt(value)+1;
        switch (String.valueOf(i).length()){
            case 1 : return "00"+ i;
            case 2 : return "0"+ i;
            default:return String.valueOf(i);
        }
    }




    public static String genarateId(String prefix,ArrayList<String> arrayList){
        if(arrayList.size()==0){
            return prefix+"000";
        }else{
            int intValue = Integer.parseInt(arrayList.get(0).substring(4,arrayList.get(0).length()));
            String incrementLastValue = getValidIntValue(String.valueOf(intValue));
            return prefix+incrementLastValue;
        }
    }
}
