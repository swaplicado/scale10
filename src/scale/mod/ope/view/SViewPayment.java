/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scale.mod.ope.view;

import java.util.ArrayList;
import sa.lib.SLibConsts;
import sa.lib.db.SDbConsts;
import sa.lib.grid.SGridColumnView;
import sa.lib.grid.SGridConsts;
import sa.lib.grid.SGridFilterDate;
import sa.lib.grid.SGridFilterDatePeriod;
import sa.lib.grid.SGridFilterValue;
import sa.lib.grid.SGridPaneSettings;
import sa.lib.grid.SGridPaneView;
import sa.lib.grid.SGridUtils;
import sa.lib.gui.SGuiClient;
import sa.lib.gui.SGuiConsts;
import sa.lib.gui.SGuiDate;
import scale.mod.SModConsts;
import scale.mod.ope.db.SOpeConsts;

/**
 *
 * @author Sergio Flores
 */
public class SViewPayment extends SGridPaneView {

    private boolean mbIsAdministrator;
    private int mnFilterDateType;
    private SGridFilterDate moFilterDate;
    private SGridFilterDatePeriod moFilterDatePeriod;

    public SViewPayment(SGuiClient client, String title) {
        super(client, SGridConsts.GRID_PANE_VIEW, SModConsts.O_PAY, SLibConsts.UNDEFINED, title);
        setRowButtonsEnabled(false);
        initComponetsCustom();
    }

    /*
    * Private methods
    */

    private void initComponetsCustom() {
        mbIsAdministrator = miClient.getSession().getUser().isAdministrator();

        if (mbIsAdministrator) {
            mnFilterDateType = SGridConsts.FILTER_DATE_PERIOD;
            moFilterDatePeriod = new SGridFilterDatePeriod(miClient, this, SGuiConsts.DATE_PICKER_DATE_PERIOD);
            moFilterDatePeriod.initFilter(new SGuiDate(SGuiConsts.GUI_DATE_MONTH, miClient.getSession().getCurrentDate().getTime()));
            getPanelCommandsSys(SGuiConsts.PANEL_CENTER).add(moFilterDatePeriod);
        }
        else {
            mnFilterDateType = SGridConsts.FILTER_DATE;
            moFilterDate = new SGridFilterDate(miClient, this);
            moFilterDate.initFilter(new SGuiDate(SGuiConsts.GUI_DATE_DATE, miClient.getSession().getCurrentDate().getTime()));
            getPanelCommandsSys(SGuiConsts.PANEL_CENTER).add(moFilterDate);
        }
    }

    /*
    * Public methods
    */

    @Override
    public void prepareSqlQuery() {
        String sql = "";
        Object filter = null;

        moPaneSettings = new SGridPaneSettings(1);
        moPaneSettings.setDisabledApplying(true);
        moPaneSettings.setDeletedApplying(true);
        moPaneSettings.setDisabledApplying(true);
        moPaneSettings.setDeletedApplying(true);
        moPaneSettings.setSystemApplying(true);
        moPaneSettings.setUserInsertApplying(true);
        moPaneSettings.setUserUpdateApplying(true);

        filter = ((SGridFilterValue) moFiltersMap.get(SGridConsts.FILTER_DELETED)).getValue();
        if ((Boolean) filter) {
            sql += (sql.isEmpty() ? "" : "AND ") + "v.b_del = 0 ";
        }

        filter = ((SGridFilterValue) moFiltersMap.get(mnFilterDateType)).getValue();
        sql += (sql.isEmpty() ? "" : "AND ") + SGridUtils.getSqlFilterDate("v.dt", (SGuiDate) filter);

        msSql = "SELECT v.id_pay AS " + SDbConsts.FIELD_ID + "1, " +
            "'' AS " + SDbConsts.FIELD_CODE + ", " +
            "v.num AS " + SDbConsts.FIELD_NAME + ", " +
            "v.num, " +
            "v.dt AS " + SDbConsts.FIELD_DATE + ", " +
            "v.pay, " +
            "v.b_wei, " +
            "w.dt, " +
            "w.num, " +
            "w.wei_net_r, " +
            "w.prc_unt, " +
            "w.tot_r, " +
            "w.b_pay, " +
            "i.code, " +
            "i.name, " +
            "s.code, " +
            "s.name, " +
            "l.code, " +
            "l.name, " +
            "v.b_dis AS " + SDbConsts.FIELD_IS_DIS + ", " +
            "v.b_del AS " + SDbConsts.FIELD_IS_DEL + ", " +
            "v.b_sys AS " + SDbConsts.FIELD_IS_SYS + ", " +
            "v.fk_usr_ins AS " + SDbConsts.FIELD_USER_INS_ID + ", " +
            "v.fk_usr_upd AS " + SDbConsts.FIELD_USER_UPD_ID + ", " +
            "v.ts_usr_ins AS " + SDbConsts.FIELD_USER_INS_TS + ", " +
            "v.ts_usr_upd AS " + SDbConsts.FIELD_USER_UPD_TS + ", " +
            "ui.name AS " + SDbConsts.FIELD_USER_INS_NAME + ", " +
            "uu.name AS " + SDbConsts.FIELD_USER_UPD_NAME + " " +
            "FROM " + SModConsts.TablesMap.get(SModConsts.O_PAY) + " AS v " +
            "INNER JOIN " + SModConsts.TablesMap.get(SModConsts.O_WEI) + " AS w ON v.fk_wei = w.id_wei " +
            "INNER JOIN " + SModConsts.TablesMap.get(SModConsts.OU_ITM) + " AS i ON w.fk_itm = i.id_itm " +
            "INNER JOIN " + SModConsts.TablesMap.get(SModConsts.OU_SUP) + " AS s ON w.fk_sup = s.id_sup " +
            "INNER JOIN " + SModConsts.TablesMap.get(SModConsts.OU_LOC) + " AS l ON w.fk_loc = l.id_loc " +
            "INNER JOIN " + SModConsts.TablesMap.get(SModConsts.OU_USR) + " AS ui ON v.fk_usr_ins = ui.id_usr " +
            "INNER JOIN " + SModConsts.TablesMap.get(SModConsts.OU_USR) + " AS uu ON v.fk_usr_upd = uu.id_usr " +
            (sql.isEmpty() ? "" : "WHERE " + sql) +
            "ORDER BY v.num, v.id_pay ";
    }

    @Override
    public ArrayList<SGridColumnView> createGridColumns() {
        SGridColumnView column = null;
        ArrayList<SGridColumnView> columns = new ArrayList<SGridColumnView>();

        columns.add(new SGridColumnView(SGridConsts.COL_TYPE_TEXT_REG_NUM, "v.num", "Folio pago"));
        columns.add(new SGridColumnView(SGridConsts.COL_TYPE_DATE, SDbConsts.FIELD_DATE, SGridConsts.COL_TITLE_DATE + " pago"));

        column = new SGridColumnView(SGridConsts.COL_TYPE_DEC_AMT, "v.pay", "Pago $");
        column.setSumApplying(mbIsAdministrator);
        columns.add(column);

        columns.add(new SGridColumnView(SGridConsts.COL_TYPE_BOOL_M, "v.b_wei", "Pago en boleto"));
        columns.add(new SGridColumnView(SGridConsts.COL_TYPE_TEXT_REG_NUM, "w.num", "Folio boleto"));
        columns.add(new SGridColumnView(SGridConsts.COL_TYPE_DATE, "w.dt", SGridConsts.COL_TITLE_DATE + " boleto"));
        columns.add(new SGridColumnView(SGridConsts.COL_TYPE_TEXT_NAME_ITM_S, "i.name", "Producto"));
        columns.add(new SGridColumnView(SGridConsts.COL_TYPE_TEXT_CODE_ITM, "i.code", "Código producto"));
        columns.add(new SGridColumnView(SGridConsts.COL_TYPE_DEC_QTY, "w.wei_net_r", "Peso neto (" + SOpeConsts.KG + ")"));
        columns.add(new SGridColumnView(SGridConsts.COL_TYPE_DEC_AMT_UNIT, "w.prc_unt", "Precio unitario $"));
        columns.add(new SGridColumnView(SGridConsts.COL_TYPE_DEC_AMT, "w.tot_r", "Total $"));
        columns.add(new SGridColumnView(SGridConsts.COL_TYPE_BOOL_M, "w.b_pay", "Cerrado para pago"));
        columns.add(new SGridColumnView(SGridConsts.COL_TYPE_TEXT_NAME_BPR_S, "s.name", "Proveedor"));
        columns.add(new SGridColumnView(SGridConsts.COL_TYPE_TEXT_NAME_CAT_S, "l.name", "Localidad"));
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
        moSuscriptionsSet.add(SModConsts.O_WEI);
        moSuscriptionsSet.add(SModConsts.O_PAY);
        moSuscriptionsSet.add(SModConsts.OU_ITM);
        moSuscriptionsSet.add(SModConsts.OU_SUP);
        moSuscriptionsSet.add(SModConsts.OU_LOC);
        moSuscriptionsSet.add(SModConsts.OU_USR);
    }
}
