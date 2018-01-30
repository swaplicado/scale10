/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scale.mod.ope.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import sa.lib.SLibConsts;
import sa.lib.SLibTimeUtils;
import sa.lib.gui.SGuiClient;
import sa.lib.gui.SGuiSession;
import scale.mod.SModConsts;

/**
 *
 * @author Sergio Flores
 */
public abstract class SOpeUtils {

    /**
     * @param session GUI session.
     * @param type Constants defined in scale.mod.SModConsts.
     */
    public static int getNextNumber(final SGuiSession session, final int type) throws SQLException, Exception {
        int num = 0;
        String sql = "";
        ResultSet resultSet = null;

        switch (type) {
            case SModConsts.O_WEI:
            case SModConsts.O_PAY:
            case SModConsts.O_SHI:
            case SModConsts.O_IOG:
                sql = "SELECT COALESCE(MAX(num), 0) + 1 FROM " + SModConsts.TablesMap.get(type) + " WHERE b_del = 0; ";
                break;
            default:
                throw new Exception(SLibConsts.ERR_MSG_OPTION_UNKNOWN);
        }

        resultSet = session.getStatement().executeQuery(sql);
        if (resultSet.next()) {
            num = resultSet.getInt(1);
        }

        return num;
    }

    public static double getWeighingOtherPayments(final SGuiSession session, final int idWeighing) throws SQLException, Exception {
        double payments = 0;
        String sql = "";
        ResultSet resultSet = null;

        sql = "SELECT COALESCE(SUM(pay), 0) FROM " + SModConsts.TablesMap.get(SModConsts.O_PAY) + " "
                + "WHERE fk_wei = " + idWeighing + " AND b_wei = 0 AND b_del = 0 ";
        resultSet = session.getStatement().executeQuery(sql);
        if (resultSet.next()) {
            payments = resultSet.getDouble(1);
        }

        return payments;
    }

    public static double getWeighingOtherPayments(final SGuiSession session, final int idWeighing, final int idPayment) throws SQLException, Exception {
        double payments = 0;
        String sql = "";
        ResultSet resultSet = null;

        sql = "SELECT COALESCE(SUM(pay), 0) FROM " + SModConsts.TablesMap.get(SModConsts.O_PAY) + " "
                + "WHERE id_pay <> " + idPayment + " AND fk_wei = " + idWeighing + " AND b_del = 0 ";
        resultSet = session.getStatement().executeQuery(sql);
        if (resultSet.next()) {
            payments = resultSet.getDouble(1);
        }

        return payments;
    }

    public static void printWeighing(final SGuiClient client, final int[] keyWeighing) {
        HashMap<String, Object> paramsMap = client.createReportParams();
        paramsMap.put("nWei", keyWeighing[0]);
        client.getSession().printReportNow(SModConsts.OR_WEI, SLibConsts.UNDEFINED, null, paramsMap, false);
    }

    public static boolean isPeriodOpen(final SGuiSession session, final Date date) {
        int[] period = SLibTimeUtils.digestMonth(date);
        return isPeriodOpen(session, period[0], period[1]);
    }

    public static boolean isPeriodOpen(final SGuiSession session, final int year, final int month) {
        return !((SDbYear) session.readRegistry(SModConsts.O_YEA, new int[] { year })).isMonthClosed(month);
    }
}
