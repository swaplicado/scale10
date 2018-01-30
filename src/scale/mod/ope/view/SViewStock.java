/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scale.mod.ope.view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JButton;
import sa.lib.SLibTimeUtils;
import sa.lib.SLibUtils;
import sa.lib.db.SDbConsts;
import sa.lib.grid.SGridColumnView;
import sa.lib.grid.SGridConsts;
import sa.lib.grid.SGridFilterDate;
import sa.lib.grid.SGridFilterValue;
import sa.lib.grid.SGridPaneSettings;
import sa.lib.grid.SGridPaneView;
import sa.lib.grid.SGridRowView;
import sa.lib.gui.SGuiClient;
import sa.lib.gui.SGuiConsts;
import sa.lib.gui.SGuiDate;
import scale.mod.SModConsts;
import scale.mod.ope.form.SDialogStockCardex;

/**
 *
 * @author Sergio Flores
 */
public class SViewStock extends SGridPaneView implements ActionListener {

    private javax.swing.JButton jbCardex;

    private SGridFilterDate moFilterDate;
    private SDialogStockCardex moDialogStockCardex;

    private Date mtDateCutOff;

    public SViewStock(SGuiClient client, int gridSubtype, String title) {
        super(client, SGridConsts.GRID_PANE_VIEW, SModConsts.O_STK, gridSubtype, title);
        setRowButtonsEnabled(false);
        initComponetsCustom();
    }

    private void initComponetsCustom() {
        jbCardex = new JButton(new javax.swing.ImageIcon(getClass().getResource("/som/gui/img/icon_std_kardex.gif")));
        jbCardex.setPreferredSize(new Dimension(23, 23));
        jbCardex.addActionListener(this);
        jbCardex.setToolTipText("Ver tarjeta auxiliar de almacén");

        moFilterDate = new SGridFilterDate(miClient, this);
        moFilterDate.initFilter(new SGuiDate(SGuiConsts.GUI_DATE_DATE, SLibTimeUtils.getEndOfYear(miClient.getSession().getCurrentDate()).getTime()));

        moDialogStockCardex = new SDialogStockCardex(miClient);

        getPanelCommandsSys(SGuiConsts.PANEL_CENTER).add(moFilterDate);
        getPanelCommandsSys(SGuiConsts.PANEL_CENTER).add(jbCardex);

    }

    private void actionShowCardex() {
        if (jbCardex.isEnabled()) {
            if (jtTable.getSelectedRowCount() != 1) {
                miClient.showMsgBoxInformation(SGridConsts.MSG_SELECT_ROW);
            }
            else {
                SGridRowView gridRow = (SGridRowView) getSelectedGridRow();

                if (gridRow.getRowType() != SGridConsts.ROW_TYPE_DATA) {
                    miClient.showMsgBoxWarning(SGridConsts.ERR_MSG_ROW_TYPE_DATA);
                }
                else {
                    int[] key = (int[]) gridRow.getRowPrimaryKey();
                    int itemId = key[1];
                    int unitId = key[2];

                    moDialogStockCardex.formReset();
                    moDialogStockCardex.setFormParams(mtDateCutOff, itemId, unitId, null);
                    moDialogStockCardex.setVisible(true);
                }
            }
        }
    }

    @Override
    public void prepareSqlQuery() {
        String sql = "";
        String sqlHaving = "";
        Object filter = null;

        moPaneSettings = new SGridPaneSettings(3);

        filter = ((SGridFilterValue) moFiltersMap.get(SGridConsts.FILTER_DELETED)).getValue();
        if ((Boolean) filter) {
            sqlHaving = "HAVING f_stk <> 0 ";
        }

        filter = ((SGridFilterValue) moFiltersMap.get(SGridConsts.FILTER_DATE)).getValue();
        if (filter != null) {
            sql += (sql.length() == 0 ? "" : "AND ") + "v.id_year = " + SLibTimeUtils.digestYear((SGuiDate) filter)[0] + " AND "
                    + " v.dt <= '" + SLibUtils.DbmsDateFormatDate.format((SGuiDate) filter) + "' ";
        }
        else {
            sql += (sql.length() == 0 ? "" : "AND ")  + "v.id_year = " + SLibTimeUtils.digestYear(miClient.getSession().getCurrentDate())[0] + " AND "
                    + " v.dt <= '" + SLibUtils.DbmsDateFormatDate.format(SLibTimeUtils.getEndOfYear(miClient.getSession().getCurrentDate())) + "' ";
        }
        mtDateCutOff = filter != null ? (SGuiDate) filter : miClient.getSession().getCurrentDate();

        msSql = "SELECT "
                + "v.id_year AS " + SDbConsts.FIELD_ID + "1, "
                + "v.id_item AS " + SDbConsts.FIELD_ID + "2, "
                + "v.id_unit AS " + SDbConsts.FIELD_ID + "3, "
                + "vi.code AS " + SDbConsts.FIELD_CODE + ", "
                + "vi.name AS " + SDbConsts.FIELD_NAME + ", "
                + "SUM(v.mov_in) AS f_mov_i, "
                + "SUM(v.mov_out) AS f_mov_o, "
                + "SUM(v.mov_in - v.mov_out) AS f_stk, "
                + "vu.code "
                + "FROM " + SModConsts.TablesMap.get(SModConsts.O_STK) + " AS v "
                + "INNER JOIN " + SModConsts.TablesMap.get(SModConsts.OU_ITM) + " AS vi ON "
                + "v.id_item = vi.id_item "
                + "WHERE v.b_del = 0 " +
                (sql.isEmpty() ? "" : "AND " + sql)
                + "GROUP BY v.id_item, v.id_unit, "
                + "vi.code, vi.name, vu.code "
                + sqlHaving
                + "ORDER BY vi.name, vi.code, v.id_item, vu.code, v.id_unit ";
    }

    @Override
    public ArrayList<SGridColumnView> createGridColumns() {
        ArrayList<SGridColumnView> columns = new ArrayList<SGridColumnView>();

        columns.add(new SGridColumnView(SGridConsts.COL_TYPE_TEXT_NAME_CAT_M, SDbConsts.FIELD_NAME, "Ítem"));
        columns.add(new SGridColumnView(SGridConsts.COL_TYPE_TEXT_CODE_ITM, SDbConsts.FIELD_CODE, "Ítem código"));
        columns.add(new SGridColumnView(SGridConsts.COL_TYPE_DEC_4D, "f_mov_i", "Entradas"));
        columns.add(new SGridColumnView(SGridConsts.COL_TYPE_DEC_4D, "f_mov_o", "Salidas"));
        columns.add(new SGridColumnView(SGridConsts.COL_TYPE_DEC_4D, "f_stk", "Existencias"));
        columns.add(new SGridColumnView(SGridConsts.COL_TYPE_TEXT_CODE_UNT, "vu.code", "Unidad"));

        return columns;
    }

    @Override
    public void defineSuscriptions() {
        moSuscriptionsSet.add(mnGridType);
        moSuscriptionsSet.add(SModConsts.OU_ITM);
    }

    @Override
    public void actionMouseClicked() {
        actionShowCardex();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JButton) {
            JButton button = (JButton) e.getSource();

            if (button == jbCardex) {
                actionShowCardex();
            }
        }
    }
}
