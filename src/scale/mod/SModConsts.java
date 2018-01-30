/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scale.mod;

import java.util.HashMap;

/**
 *
 * @author Sergio Flores
 */
public abstract class SModConsts {

    public static final int MOD_CFG = 1;
    public static final int MOD_OPE = 2;

    public static final int SU_SYS = 110001;
    public static final int SU_COM = 110002;

    public static final int OS_USR_TP = 210001;
    public static final int OS_IOG_CL = 210002;
    public static final int OS_IOG_TP = 210003;
    public static final int OS_ADJ_TP = 210004;

    public static final int OU_FIX = 220001;
    public static final int OU_CFG = 220002;
    public static final int OU_USR = 220003;
    public static final int OU_LOC = 220004;
    public static final int OU_SUP = 220005;
    public static final int OU_ITM = 220006;

    public static final int O_YEA = 230001;
    public static final int O_WEI = 230002;
    public static final int O_PAY = 230003;
    public static final int O_SHI = 230004;
    public static final int O_IOG = 230005;
    public static final int O_STK = 230006;
    public static final int O_USR_GUI = 231001;

    public static final int OX_WEI_PAY = 240001;

    public static final int OR_WEI = 250001;
    public static final int OR_WEI_REP = 250002;

    public static final HashMap<Integer, String> TablesMap = new HashMap<Integer, String>();

    static {
        TablesMap.put(SU_SYS, "su_sys");
        TablesMap.put(SU_COM, "su_com");

        TablesMap.put(OS_USR_TP, "os_usr_tp");
        TablesMap.put(OS_IOG_CL, "os_iog_cl");
        TablesMap.put(OS_IOG_TP, "os_iog_tp");
        TablesMap.put(OS_ADJ_TP, "os_adj_tp");

        TablesMap.put(OU_FIX, "ou_fix");
        TablesMap.put(OU_CFG, "ou_cfg");
        TablesMap.put(OU_USR, "ou_usr");
        TablesMap.put(OU_LOC, "ou_loc");
        TablesMap.put(OU_SUP, "ou_sup");
        TablesMap.put(OU_ITM, "ou_itm");

        TablesMap.put(O_YEA, "o_yea");
        TablesMap.put(O_WEI, "o_wei");
        TablesMap.put(O_PAY, "o_pay");
        TablesMap.put(O_SHI, "o_shi");
        TablesMap.put(O_IOG, "o_iog");
        TablesMap.put(O_STK, "o_stk");
        TablesMap.put(O_USR_GUI, "o_usr_gui");
    }
}
