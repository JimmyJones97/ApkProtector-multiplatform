package mph.trunksku.apps.dexpro.utils;
import java.util.Calendar;
import java.util.TimeZone;

public class DexProMod
{

    private StringBuffer sb;
    
    public DexProMod(StringBuffer sb) {
        this.sb = sb;
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        sb.append("#This file is automatically generated by DexProtector to uniquely");
        sb.append("\n#identify individual builds of your Android application.");
        sb.append("\n#\n#Do NOT modify, delete, or commit to source control!\n#");
        sb.append("\n#" + calendar.getTime());
    }
    
    public DexProMod setHeaderMsg(String msg){
        sb.append("\n#\n" + msg + "\n#");
        return this;
    }
    
    public DexProMod setFooterMsg(String msg){
        sb.append("\n" + msg);
        return this;
    }
    
    public DexProMod addMessage(String msg) {
        sb.append("\n" + msg);
        return this;
    }
    
    public DexProMod addBuildMessage(String msg) {
        sb.append("\nbuild." + msg);
        return this;
    }
    
    public String output() {
        return sb.toString();
    }
}
