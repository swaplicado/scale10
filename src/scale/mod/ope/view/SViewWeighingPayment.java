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
import javax.swing.JOptionPane;
import sa.gui.util.SUtilConsts;
import sa.lib.SLibRpnArgument;
import sa.lib.SLibRpnArgumentType;
import sa.lib.SLibRpnOperator;
import sa.lib.SLibUtils;
import sa.lib.db.SDbConsts;
import sa.lib.grid.SGridColumnView;
import sa.lib.grid.SGridConsts;
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
import scale.mod.ope.db.SDbWeighing;
import scale.mod.ope.db.SOpeConsts;

/**
 *
 * @author Sergio Flores
 */
public class SViewWeighingPayment extends SGridPaneView implements ActionListener{

    private SGridFilterDatePeriod moFilterDatePeriod;
    private JButton mjButtonClose;
    private JButton mjButtonOpen;

    /**
     * @param client GUI Client.
     * @param gridSubtype Constants defined in sa.gui.util.SUtilConsts: PAY & PAY_PEND.
     */
    public SViewWeighingPayment(SGuiClient client, int gridSubtype, String title) {
        super(client, SGridConsts.GRID_PANE_VIEW, SModConsts.OX_WEI_PAY, gridSubtype, title);
        setRowButtonsEnabled(false);
        initComponetsCustom();
    }

    /*
    * Private methods
    */

    private void initComponetsCustom() {
        if (mnGridSubtype == SUtilConsts.PAY) {
            moFilterDatePeriod = new SGridFilterDatePeriod(miClient, this, SGuiConsts.DATE_PICKER_DATE_PERIOD);
            moFilterDatePeriod.initFilter(new SGuiDate(SGuiConsts.GUI_DATE_MONTH, miClient.getSession().getCurrentDate().getTime()));
            getPanelCommandsSys(SGuiConsts.PANEL_CENTER).add(moFilterDatePeriod);
        }

        mjButtonClose = SGridUtils.createButton(new ImageIcon(getClass().getResource("/scale/gui/img/ico_std_sta_clo.gif")), "Cerrar para pago", this);
        mjButtonClose.setEnabled(mnGridSubtype != SUtilConsts.PAY);
        getPanelCommandsSys(SGuiConsts.PANEL_CENTER).add(mjButtonClose);

        mjButtonOpen = SGridUtils.createButton(new ImageIcon(getClass().getResource("/scale/gui/img/ico_std_sta_ope.gif")), "Abrir para pago", this);
        mjButtonOpen.setEnabled(mnGridSubtype == SUtilConsts.PAY);
        getPanelCommandsSys(SGuiConsts.PANEL_CENTER).add(mjButtonOpen);
    }

    private void changeStatus(boolean close) {
        if (jtTable.getSelectedRowCount() != 1) {
            miClient.showMsgBoxInformation(SGridConsts.MSG_SELECT_ROW);
        }
        else {
            SDbWeighing weighing = null;
            SGridRowView gridRow = (SGridRowView) getSelectedGridRow();

            if (gridRow.getRowType() != SGridConsts.ROW_TYPE_DATA) {
                miClient.showMsgBoxWarning(SGridConsts.ERR_MSG_ROW_TYPE_DATA);
            }
            else if (miClient.showMsgBoxConfirm("Se cambiará el estatus 'cerrado para pago' del boleto '" + gridRow.getRowName() + "'.\n" + SGuiConsts.MSG_CNF_CONT) == JOptionPane.YES_OPTION) {
                try {
                    weighing = (SDbWeighing) miClient.getSession().readRegistry(SModConsts.O_WEI, gridRow.getRowPrimaryKey());
                    weighing.setPayed(close);
                    weighing.save(miClient.getSession());
                    miClient.getSession().notifySuscriptors(SModConsts.O_WEI);
                }
                catch (Exception e) {
                    SLibUtils.showException(this, e);
                }
            }
        }
    }

    private void actionClose() {
        if (mjButtonClose.isEnabled()) {
            changeStatus(true);
        }
    }

    private void actionOpen() {
        if (mjButtonOpen.isEnabled()) {
            changeStatus(false);
        }
    }

    /*
    * Public methods
    */

    @Override
    public void prepareSqlQuery() {
        String sql = "";
        String having = "";
        Object filter = null;

        moPaneSettings = new SGridPaneSettings(1);

        if (mnGridSubtype == SUtilConsts.PAY) {
            filter = ((SGridFilterValue) moFiltersMap.get(SGridConsts.FILTER_DATE_PERIOD)).getValue();
            sql += (sql.isEmpty() ? "" : "AND ") + SGridUtils.getSqlFilterDate("w.dt", (SGuiDate) filter);
        }

        if (mnGridSubtype == SUtilConsts.PAY) {
            having = "f_pay - w.tot_r = 0 OR w.b_pay = 1 ";
        }
        else {
            having = "f_pay - w.tot_r <> 0 AND w.b_pay = 0 ";
        }

        msSql = "SELECT w.id_wei AS " + SDbConsts.FIELD_ID + "1, " +
            "'' AS " + SDbConsts.FIELD_CODE + ", " +
            "w.num AS " + SDbConsts.FIELD_NAME + ", " +
            "w.num, " +
            "w.dt AS " + SDbConsts.FIELD_DATE + ", " +
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
            "COALESCE(SUM(p.pay), 0) AS f_pay " +
            "FROM " + SModConsts.TablesMap.get(SModConsts.O_WEI) + " AS w " +
            "INNER JOIN " + SModConsts.TablesMap.get(SModConsts.OU_ITM) + " AS i ON w.fk_itm = i.id_itm " +
            "INNER JOIN " + SModConsts.TablesMap.get(SModConsts.OU_SUP) + " AS s ON w.fk_sup = s.id_sup " +
            "INNER JOIN " + SModConsts.TablesMap.get(SModConsts.OU_LOC) + " AS l ON w.fk_loc = l.id_loc " +
            "LEFT OUTER JOIN " + SModConsts.TablesMap.get(SModConsts.O_PAY) + " AS p ON w.id_wei = p.fk_wei AND p.b_del = 0 " +
            "WHERE w.b_dis = 0 AND w.b_del = 0 " + (sql.isEmpty() ? "" : "AND " + sql) +
            "GROUP BY w.id_wei, w.num, w.dt, w.wei_net_r, w.tot_r, w.nts, w.nts_del, " +
            "i.code, i.name, s.code, s.name, l.code, l.name " +
            (having.isEmpty() ? "" : "HAVING " + having) +
            "ORDER BY w.num, w.id_wei ";
    }

    @Override
    public ArrayList<SGridColumnView> createGridColumns() {
        SGridColumnView column = null;
        ArrayList<SGridColumnView> columns = new ArrayList<SGridColumnView>();

        columns.add(new SGridColumnView(SGridConsts.COL_TYPE_TEXT_REG_NUM, "w.num", "Folio"));
        columns.add(new SGridColumnView(SGridConsts.COL_TYPE_DATE, SDbConsts.FIELD_DATE, SGridConsts.COL_TITLE_DATE));
        columns.add(new SGridColumnView(SGridConsts.COL_TYPE_TEXT_NAME_ITM_S, "i.name", "Producto"));
        columns.add(new SGridColumnView(SGridConsts.COL_TYPE_TEXT_CODE_ITM, "i.code", "Código producto"));

        columns.add(new SGridColumnView(SGridConsts.COL_TYPE_DEC_QTY, "w.wei_net_r", "Peso neto (" + SOpeConsts.KG + ")"));
        columns.add(new SGridColumnView(SGridConsts.COL_TYPE_DEC_AMT_UNIT, "w.prc_unt", "Precio unitario $"));
        columns.add(new SGridColumnView(SGridConsts.COL_TYPE_DEC_AMT, "w.tot_r", "Total $"));
        columns.add(new SGridColumnView(SGridConsts.COL_TYPE_DEC_AMT, "f_pay", "Pagos $"));
        column = new SGridColumnView(SGridConsts.COL_TYPE_DEC_AMT, "", "Saldo $");
        column.setSumApplying(true);
        column.getRpnArguments().add(new SLibRpnArgument("f_pay", SLibRpnArgumentType.OPERAND));
        column.getRpnArguments().add(new SLibRpnArgument("w.tot_r", SLibRpnArgumentType.OPERAND));
        column.getRpnArguments().add(new SLibRpnArgument(SLibRpnOperator.SUBTRACTION, SLibRpnArgumentType.OPERATOR));
        columns.add(column);

        columns.add(new SGridColumnView(SGridConsts.COL_TYPE_BOOL_M, "w.b_pay", "Cerrado para pago"));
        columns.add(new SGridColumnView(SGridConsts.COL_TYPE_TEXT_NAME_BPR_S, "s.name", "Proveedor"));
        columns.add(new SGridColumnView(SGridConsts.COL_TYPE_TEXT_NAME_CAT_S, "l.name", "Localidad"));

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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JButton) {
            JButton button = (JButton) e.getSource();

            if (button == mjButtonClose) {
                actionClose();
            }
            else if (button == mjButtonOpen) {
                actionOpen();
            }
        }
    }
}
