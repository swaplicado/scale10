/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package scale.gui.prt;

import java.util.HashMap;
import sa.lib.SLibUtils;
import sa.lib.gui.SGuiSession;
import scale.gui.SGuiClientApp;
import scale.gui.SGuiClientSessionCustom;
import scale.mod.ope.db.SDbConfig;

/**
 *
 * @author Sergio Flores
 */
public class SPrtUtils {

    /*
    private static String getReportFileName(final int type) {
        String fileName = "";

        switch (type) {
            case SModConsts.SR_MY_REP:
                fileName = "reps/sr_my_rep.jasper";
                break;
            default:
        }

        return fileName;
    }
    */

    public static HashMap<String, Object> createReportParamsMap(final SGuiSession session) {
        HashMap<String, Object> map = new HashMap<String, Object>();

        // Parameters that need to be declared in reports:

        map.put("sCompanyName", ((SDbConfig) session.getConfigSystem()).getName());
        map.put("sUserName", session.getUser().getName());
        map.put("sAppName", SGuiClientApp.APP_NAME);
        map.put("sAppCopyright", SGuiClientApp.APP_COPYRIGHT);
        map.put("sAppProvider", SGuiClientApp.APP_PROVIDER);

        // Optional parameters:

        map.put("oFormatDate", SLibUtils.DateFormatDate);
        map.put("oFormatDateShort", SLibUtils.DateFormatDateShort);
        map.put("oFormatDatetime", SLibUtils.DateFormatDatetime);
        map.put("oFormatTime", SLibUtils.DateFormatTime);
        map.put("oFormatValue0D", SLibUtils.DecimalFormatValue0D);
        map.put("oFormatValue2D", SLibUtils.DecimalFormatValue2D);
        map.put("oFormatValue4D", SLibUtils.DecimalFormatValue4D);
        map.put("oFormatValue8D", SLibUtils.DecimalFormatValue8D);

        return map;
    }

    /*
    public static void printReport(final SGuiSession session, final int type, final HashMap<String, Object> hashMap) throws Exception, JRException {
        File file = null;
        JasperReport jasperReport = null;
        JasperPrint jasperPrint = null;
        JasperViewer jasperViewer = null;

        try {
            file = new File(getReportFileName(type));

            jasperReport = (JasperReport) JRLoader.loadObject(file);
            jasperPrint = JasperFillManager.fillReport(jasperReport, hashMap, session.getStatement().getConnection());

            jasperViewer = new JasperViewer(jasperPrint, false);
            jasperViewer.setTitle(SGuiClientApp.APP_NAME);
            jasperViewer.setVisible(true);
        }
        catch (Exception e) {
            SLibUtils.showException(SPrtUtils.class.getName(), e);
        }
    }

    public static void exportReportToPdfFile(final SGuiSession session, final int type, final HashMap<String, Object> hashMap, String destFileName) throws Exception, JRException {
        File file = null;
        JasperReport jasperReport = null;
        JasperPrint jasperPrint = null;

        try {
            file = new File(getReportFileName(type));

            jasperReport = (JasperReport) JRLoader.loadObject(file);
            jasperPrint = JasperFillManager.fillReport(jasperReport, hashMap, session.getStatement().getConnection());

            JasperExportManager.exportReportToPdfFile(jasperPrint, destFileName);
        }
        catch (Exception e) {
            SLibUtils.showException(SPrtUtils.class.getName(), e);
        }
    }
    */
}
