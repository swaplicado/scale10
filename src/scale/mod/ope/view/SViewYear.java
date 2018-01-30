/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scale.mod.ope.view;

import java.util.ArrayList;
import java.util.Calendar;
import sa.lib.SLibConsts;
import sa.lib.SLibTimeUtils;
import sa.lib.db.SDbConsts;
import sa.lib.grid.SGridColumnView;
import sa.lib.grid.SGridConsts;
import sa.lib.grid.SGridFilterValue;
import sa.lib.grid.SGridPaneSettings;
import sa.lib.grid.SGridPaneView;
import sa.lib.gui.SGuiClient;
import scale.mod.SModConsts;

/**
 *
 * @author Sergio Flores
 */
public class SViewYear extends SGridPaneView {

    public SViewYear(SGuiClient client, String title) {
        super(client, SGridConsts.GRID_PANE_VIEW, SModConsts.O_YEA, SLibConsts.UNDEFINED, title);
    }

    @Override
    public void prepareSqlQuery() {
        String sql = "";
        Object filter = null;

        moPaneSettings = new SGridPaneSettings(1);
        moPaneSettings.setSystemApplying(true);
        moPaneSettings.setUserInsertApplying(true);
        moPaneSettings.setUserUpdateApplying(true);

        filter = ((SGridFilterValue) moFiltersMap.get(SGridConsts.FILTER_DELETED)).getValue();
        if ((Boolean) filter) {
            sql += (sql.isEmpty() ? "" : "AND ") + "v.b_del = 0 ";
        }

        msSql = "SELECT "
                + "v.id_yea AS " + SDbConsts.FIELD_ID + "1, "
                + "'' AS " + SDbConsts.FIELD_CODE + ", "
                + "v.id_yea AS " + SDbConsts.FIELD_NAME + ", "
                + "v.b_clo_01, "
                + "v.b_clo_02, "
                + "v.b_clo_03, "
                + "v.b_clo_04, "
                + "v.b_clo_05, "
                + "v.b_clo_06, "
                + "v.b_clo_07, "
                + "v.b_clo_08, "
                + "v.b_clo_09, "
                + "v.b_clo_10, "
                + "v.b_clo_11, "
                + "v.b_clo_12, "
                + "v.b_dis AS " + SDbConsts.FIELD_IS_DIS + ", "
                + "v.b_del AS " + SDbConsts.FIELD_IS_DEL + ", "
                + "v.b_sys AS " + SDbConsts.FIELD_IS_SYS + ", "
                + "v.fk_usr_ins AS " + SDbConsts.FIELD_USER_INS_ID + ", "
                + "v.fk_usr_upd AS " + SDbConsts.FIELD_USER_UPD_ID + ", "
                + "v.ts_usr_ins AS " + SDbConsts.FIELD_USER_INS_TS + ", "
                + "v.ts_usr_upd AS " + SDbConsts.FIELD_USER_UPD_TS + ", "
                + "ui.name AS " + SDbConsts.FIELD_USER_INS_NAME + ", "
                + "uu.name AS " + SDbConsts.FIELD_USER_UPD_NAME + " "
                + "FROM " + SModConsts.TablesMap.get(SModConsts.O_YEA) + " AS v "
                + "INNER JOIN " + SModConsts.TablesMap.get(SModConsts.OU_USR) + " AS ui ON "
                + "v.fk_usr_ins = ui.id_usr "
                + "INNER JOIN " + SModConsts.TablesMap.get(SModConsts.OU_USR) + " AS uu ON "
                + "v.fk_usr_upd = uu.id_usr "
                + (sql.isEmpty() ? "" : "WHERE " + sql)
                + "ORDER BY v.id_yea ";
    }

    @Override
    public ArrayList<SGridColumnView> createGridColumns() {
        String[] months = SLibTimeUtils.createMonthsOfYearStd(Calendar.SHORT);
        ArrayList<SGridColumnView> columns = new ArrayList<SGridColumnView>();

        columns.add(new SGridColumnView(SGridConsts.COL_TYPE_INT_CAL_YEAR, SDbConsts.FIELD_NAME, "Año"));
        columns.add(new SGridColumnView(SGridConsts.COL_TYPE_BOOL_S, "v.b_clo_01", months[0]));
        columns.add(new SGridColumnView(SGridConsts.COL_TYPE_BOOL_S, "v.b_clo_02", months[1]));
        columns.add(new SGridColumnView(SGridConsts.COL_TYPE_BOOL_S, "v.b_clo_03", months[2]));
        columns.add(new SGridColumnView(SGridConsts.COL_TYPE_BOOL_S, "v.b_clo_04", months[3]));
        columns.add(new SGridColumnView(SGridConsts.COL_TYPE_BOOL_S, "v.b_clo_05", months[4]));
        columns.add(new SGridColumnView(SGridConsts.COL_TYPE_BOOL_S, "v.b_clo_06", months[5]));
        columns.add(new SGridColumnView(SGridConsts.COL_TYPE_BOOL_S, "v.b_clo_07", months[6]));
        columns.add(new SGridColumnView(SGridConsts.COL_TYPE_BOOL_S, "v.b_clo_08", months[7]));
        columns.add(new SGridColumnView(SGridConsts.COL_TYPE_BOOL_S, "v.b_clo_09", months[8]));
        columns.add(new SGridColumnView(SGridConsts.COL_TYPE_BOOL_S, "v.b_clo_10", months[9]));
        columns.add(new SGridColumnView(SGridConsts.COL_TYPE_BOOL_S, "v.b_clo_11", months[10]));
        columns.add(new SGridColumnView(SGridConsts.COL_TYPE_BOOL_S, "v.b_clo_12", months[11]));
        columns.add(new SGridColumnView(SGridConsts.COL_TYPE_BOOL_S, SDbConsts.FIELD_IS_DIS, SGridConsts.COL_TITLE_IS_DIS));
        columns.add(new SGridColumnView(SGridConsts.COL_TYPE_BOOL_S, SDbConsts.FIELD_IS_DEL, SGridConsts.COL_TITLE_IS_DEL));
        columns.add(new SGridColumnView(SGridConsts.COL_TYPE_BOOL_S, SDbConsts.FIELD_IS_SYS, SGridConsts.COL_TITLE_IS_SYS));
        columns.add(new SGridColumnView(SGridConsts.COL_TYPE_TEXT_NAME_USR, SDbConsts.FIELD_USER_INS_NAME, SGridConsts.COL_TITLE_USER_INS_NAME));
        columns.add(new SGridColumnView(SGridConsts.COL_TYPE_DATE_DATETIME, SDbConsts.FIELD_USER_INS_TS, SGridConsts.COL_TITLE_USER_INS_TS));
        columns.add(new SGridColumnView(SGridConsts.COL_TYPE_TEXT_NAME_USR, SDbConsts.FIELD_USER_UPD_NAME, SGridConsts.COL_TITLE_USER_UPD_NAME));
        columns.add(new SGridColumnView(SGridConsts.COL_TYPE_DATE_DATETIME, SDbConsts.FIELD_USER_UPD_TS, SGridConsts.COL_TITLE_USER_UPD_TS));

        return columns;
    }

    @Override
    public void defineSuscriptions() {
        moSuscriptionsSet.add(mnGridType);
        moSuscriptionsSet.add(SModConsts.OU_USR);
    }
}
