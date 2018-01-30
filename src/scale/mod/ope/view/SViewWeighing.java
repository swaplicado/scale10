/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scale.mod.ope.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import sa.gui.util.SUtilConsts;
import sa.lib.SLibConsts;
import sa.lib.SLibRpnArgument;
import sa.lib.SLibRpnArgumentType;
import sa.lib.SLibRpnOperator;
import sa.lib.db.SDbConsts;
import sa.lib.grid.SGridColumnView;
import sa.lib.grid.SGridConsts;
import sa.lib.grid.SGridFilterDate;
import sa.lib.grid.SGridFilterDatePeriod;
import sa.lib.grid.SGridFilterValue;
import sa.lib.grid.SGridPaneSettings;
import sa.lib.grid.SGridPaneView;
import sa.lib.grid.SGridRowView;
import sa.lib.grid.SGridUtils;
import sa.lib.gui.SGuiClient;
import sa.lib.gui.SGuiConsts;
import sa.lib.gui.SGuiDate;
import scale.mod.SModConsts;
import scale.mod.ope.db.SOpeConsts;
import scale.mod.ope.db.SOpeUtils;

/**
 *
 * @author Sergio Flores
 */
public class SViewWeighing extends SGridPaneView implements ActionListener {

    private boolean mbIsAdministrator;
    private int mnFilterDateType;
    private SGridFilterDate moFilterDate;
    private SGridFilterDatePeriod moFilterDatePeriod;
    private JButton mjButtonPrint;

    public SViewWeighing(SGuiClient client, String title) {
        super(client, SGridConsts.GRID_PANE_VIEW, SModConsts.O_WEI, SLibConsts.UNDEFINED, title);
        setRowButtonsEnabled(true, false, true, true, false);
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

        mjButtonPrint = SGridUtils.createButton(new ImageIcon(getClass().getResource("/scale/gui/img/ico_std_prt.gif")), SUtilConsts.TXT_PRINT, this);
        getPanelCommandsSys(SGuiConsts.PANEL_CENTER).add(mjButtonPrint);
    }

    private void actionPrint() {
        if (mjButtonPrint.isEnabled()) {
            if (jtTable.getSelectedRowCount() != 1) {
                miClient.showMsgBoxInformation(SGridConsts.MSG_SELECT_ROW);
            }
            else {
                SGridRowView gridRow = (SGridRowView) getSelectedGridRow();

                if (gridRow.getRowType() != SGridConsts.ROW_TYPE_DATA) {
                    miClient.showMsgBoxWarning(SGridConsts.ERR_MSG_ROW_TYPE_DATA);
                }
                else {
                    SOpeUtils.printWeighing(miClient, gridRow.getRowPrimaryKey());
                }
            }
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
        moPaneSettings.setSystemApplying(true);
        moPaneSettings.setUserInsertApplying(true);
        moPaneSettings.setUserUpdateApplying(true);

        filter = ((SGridFilterValue) moFiltersMap.get(SGridConsts.FILTER_DELETED)).getValue();
        if ((Boolean) filter) {
            sql += (sql.isEmpty() ? "" : "AND ") + "v.b_del = 0 ";
        }

        filter = ((SGridFilterValue) moFiltersMap.get(mnFilterDateType)).getValue();
        sql += (sql.isEmpty() ? "" : "AND ") + SGridUtils.getSqlFilterDate("v.dt", (SGuiDate) filter);

        msSql = "SELECT v.id_wei AS " + SDbConsts.FIELD_ID + "1, " +
            "'' AS " + SDbConsts.FIELD_CODE + ", " +
            "v.num AS " + SDbConsts.FIELD_NAME + ", " +
            "v.num, " +
            "v.dt AS " + SDbConsts.FIELD_DATE + ", " +
            "v.wei_gro_sys, " +
            "v.wei_gro, " +
            "v.qty_cag, " +
            "v.wei_cag, " +
            "v.wei_pal, " +
            "v.wei_net_r, " +
            "v.prc_unt, " +
            "v.tot_r, " +
            "v.b_pay, " +
            "v.nts, " +
            "v.nts_del, " +
            "i.code, " +
            "i.name, " +
            "s.code, " +
            "s.name, " +
            "l.code, " +
            "l.name, " +
            "(SELECT COALESCE(SUM(p.pay), 0) FROM " + SModConsts.TablesMap.get(SModConsts.O_PAY) + " AS p " +
            "WHERE p.fk_wei = v.id_wei AND p.b_del = 0) AS f_pay, " +
            "IF(v.b_dis, " + SGridConsts.ICON_ANNUL + ", " + SGridConsts.ICON_NULL + ") AS f_sta, " +
            "v.b_dis AS " + SDbConsts.FIELD_IS_DIS + ", " +
            "v.b_del AS " + SDbConsts.FIELD_IS_DEL + ", " +
            "v.b_sys AS " + SDbConsts.FIELD_IS_SYS + ", " +
            "v.fk_usr_ins AS " + SDbConsts.FIELD_USER_INS_ID + ", " +
            "v.fk_usr_upd AS " + SDbConsts.FIELD_USER_UPD_ID + ", " +
            "v.ts_usr_ins AS " + SDbConsts.FIELD_USER_INS_TS + ", " +
            "v.ts_usr_upd AS " + SDbConsts.FIELD_USER_UPD_TS + ", " +
            "ui.name AS " + SDbConsts.FIELD_USER_INS_NAME + ", " +
            "uu.name AS " + SDbConsts.FIELD_USER_UPD_NAME + " " +
            "FROM " + SModConsts.TablesMap.get(SModConsts.O_WEI) + " AS v " +
            "INNER JOIN " + SModConsts.TablesMap.get(SModConsts.OU_ITM) + " AS i ON v.fk_itm = i.id_itm " +
            "INNER JOIN " + SModConsts.TablesMap.get(SModConsts.OU_SUP) + " AS s ON v.fk_sup = s.id_sup " +
            "INNER JOIN " + SModConsts.TablesMap.get(SModConsts.OU_LOC) + " AS l ON v.fk_loc = l.id_loc " +
            "INNER JOIN " + SModConsts.TablesMap.get(SModConsts.OU_USR) + " AS ui ON v.fk_usr_ins = ui.id_usr " +
            "INNER JOIN " + SModConsts.TablesMap.get(SModConsts.OU_USR) + " AS uu ON v.fk_usr_upd = uu.id_usr " +
            (sql.isEmpty() ? "" : "WHERE " + sql) +
            "ORDER BY v.num, v.id_wei ";
    }

    @Override
    public ArrayList<SGridColumnView> createGridColumns() {
        SGridColumnView column = null;
        ArrayList<SGridColumnView> columns = new ArrayList<SGridColumnView>();

        columns.add(new SGridColumnView(SGridConsts.COL_TYPE_TEXT_REG_NUM, "v.num", "Folio"));
        columns.add(new SGridColumnView(SGridConsts.COL_TYPE_DATE, SDbConsts.FIELD_DATE, SGridConsts.COL_TITLE_DATE));
        columns.add(new SGridColumnView(SGridConsts.COL_TYPE_INT_ICON, "f_sta", "Estatus"));
        columns.add(new SGridColumnView(SGridConsts.COL_TYPE_TEXT_NAME_ITM_S, "i.name", "Producto"));
        columns.add(new SGridColumnView(SGridConsts.COL_TYPE_TEXT_CODE_ITM, "i.code", "Código producto"));

        column = new SGridColumnView(SGridConsts.COL_TYPE_DEC_QTY, "v.wei_net_r", "Peso neto (" + SOpeConsts.KG + ")");
        column.setSumApplying(mbIsAdministrator);
        columns.add(column);
        columns.add(new SGridColumnView(SGridConsts.COL_TYPE_DEC_AMT_UNIT, "v.prc_unt", "Precio unitario $"));
        column = new SGridColumnView(SGridConsts.COL_TYPE_DEC_AMT, "v.tot_r", "Total $");
        column.setSumApplying(mbIsAdministrator);
        columns.add(column);
        columns.add(new SGridColumnView(SGridConsts.COL_TYPE_DEC_AMT, "f_pay", "Pagos $"));
        column = new SGridColumnView(SGridConsts.COL_TYPE_DEC_AMT, "", "Saldo $");
        column.getRpnArguments().add(new SLibRpnArgument("f_pay", SLibRpnArgumentType.OPERAND));
        column.getRpnArguments().add(new SLibRpnArgument("v.tot_r", SLibRpnArgumentType.OPERAND));
        column.getRpnArguments().add(new SLibRpnArgument(SLibRpnOperator.SUBTRACTION, SLibRpnArgumentType.OPERATOR));
        columns.add(column);

        columns.add(new SGridColumnView(SGridConsts.COL_TYPE_BOOL_M, "v.b_pay", "Cerrado para pago"));
        columns.add(new SGridColumnView(SGridConsts.COL_TYPE_TEXT_NAME_BPR_S, "s.name", "Proveedor"));
        columns.add(new SGridColumnView(SGridConsts.COL_TYPE_TEXT_NAME_CAT_S, "l.name", "Localidad"));

        column = new SGridColumnView(SGridConsts.COL_TYPE_DEC_QTY, "v.wei_gro_sys", "Peso báscula (" + SOpeConsts.KG + ")");
        column.setSumApplying(mbIsAdministrator);
        columns.add(column);
        column = new SGridColumnView(SGridConsts.COL_TYPE_DEC_QTY, "v.wei_gro", "Peso bruto (" + SOpeConsts.KG + ")");
        column.setSumApplying(mbIsAdministrator);
        columns.add(column);
        column = new SGridColumnView(SGridConsts.COL_TYPE_DEC_QTY, "v.qty_cag", "Núm. cajas (" + SOpeConsts.PCE + ")");
        column.setSumApplying(mbIsAdministrator);
        columns.add(column);
        column = new SGridColumnView(SGridConsts.COL_TYPE_DEC_QTY, "v.wei_cag", "Peso cajas (" + SOpeConsts.KG + ")");
        column.setSumApplying(mbIsAdministrator);
        columns.add(column);
        column = new SGridColumnView(SGridConsts.COL_TYPE_DEC_QTY, "v.wei_pal", "Peso tarimas (" + SOpeConsts.KG + ")");
        column.setSumApplying(mbIsAdministrator);
        columns.add(column);

        columns.add(new SGridColumnView(SGridConsts.COL_TYPE_TEXT_NAME_CAT_M, "v.nts", "Notas"));
        columns.add(new SGridColumnView(SGridConsts.COL_TYPE_TEXT_NAME_CAT_M, "v.nts_del", "Notas eliminación"));
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
        moSuscriptionsSet.add(SModConsts.O_PAY);
        moSuscriptionsSet.add(SModConsts.OU_ITM);
        moSuscriptionsSet.add(SModConsts.OU_SUP);
        moSuscriptionsSet.add(SModConsts.OU_LOC);
        moSuscriptionsSet.add(SModConsts.OU_USR);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JButton) {
            JButton button = (JButton) e.getSource();

            if (button == mjButtonPrint) {
                actionPrint();
            }
        }
    }
}
