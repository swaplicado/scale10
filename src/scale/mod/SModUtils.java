/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scale.mod;

import sa.lib.SLibConsts;
import sa.lib.SLibUtils;
import sa.lib.gui.SGuiModuleUtils;

/**
 *
 * @author Sergio Flores
 */
public class SModUtils implements SGuiModuleUtils {

    public SModUtils() {

    }

    private boolean belongsToCfg(final int type) {
        return SLibUtils.belongsTo(type, new int[] {
            SModConsts.OS_USR_TP,
            SModConsts.OS_IOG_CL,
            SModConsts.OS_IOG_TP,
            SModConsts.OS_ADJ_TP,
            SModConsts.OU_FIX,
            SModConsts.OU_CFG,
            SModConsts.OU_USR,
            SModConsts.O_YEA
        });
    }

    private boolean belongsToOpe(final int type) {
        return SLibUtils.belongsTo(type, new int[] {
            SModConsts.OU_LOC,
            SModConsts.OU_SUP,
            SModConsts.OU_ITM,
            SModConsts.O_WEI,
            SModConsts.O_PAY,
            SModConsts.O_SHI,
            SModConsts.O_IOG,
            SModConsts.O_STK,
            SModConsts.O_USR_GUI,
            SModConsts.OX_WEI_PAY,
            SModConsts.OR_WEI,
            SModConsts.OR_WEI_REP
        });
    }

    @Override
    public int getModuleTypeByType(final int type) {
        int module = SLibConsts.UNDEFINED;

        if (belongsToCfg(type)) {
            module = SModConsts.MOD_CFG;
        }
        else if (belongsToOpe(type)) {
            module = SModConsts.MOD_OPE;
        }

        return module;
    }

    @Override
    public int getModuleSubtypeBySubtype(final int type, final int subtype) {
        return SLibConsts.UNDEFINED;
    }
}
