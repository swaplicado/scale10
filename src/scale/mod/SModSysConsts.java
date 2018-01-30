/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scale.mod;

/**
 *
 * @author Sergio Flores
 */
public abstract class SModSysConsts {

    public static final int OS_USR_TP_USR = 1;
    public static final int OS_USR_TP_ADM = 2;
    public static final int OS_USR_TP_SUP = 3;

    public static final int OS_IOG_CL_IN = 1;
    public static final int OS_IOG_CL_OUT = 2;

    public static final int[] OS_IOG_TP_IN_ADJ = new int[] { 1, 1 };
    public static final int[] OS_IOG_TP_IN_PUR = new int[] { 1, 2 };
    public static final int[] OS_IOG_TP_IN_SAL = new int[] { 1, 3 };
    public static final int[] OS_IOG_TP_OUT_ADJ = new int[] { 2, 1 };
    public static final int[] OS_IOG_TP_OUT_PUR = new int[] { 2, 2 };
    public static final int[] OS_IOG_TP_OUT_SAL = new int[] { 2, 3 };

    public static final int OS_ADJ_TP_NA = 1;
    public static final int OS_ADJ_TP_DIF = 2;
    public static final int OS_ADJ_TP_INV = 3;
}
