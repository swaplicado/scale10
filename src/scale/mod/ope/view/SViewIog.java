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
import sa.lib.SLibConsts;
import sa.lib.db.SDbConsts;
import sa.lib.grid.SGridColumnView;
import sa.lib.grid.SGridConsts;
import sa.lib.grid.SGridFilterDatePeriod;
import sa.lib.grid.SGridFilterValue;
import sa.lib.grid.SGridPaneSettings;
import sa.lib.grid.SGridPaneView;
import sa.lib.grid.SGridUtils;
import sa.lib.gui.SGuiClient;
import sa.lib.gui.SGuiConsts;
import sa.lib.gui.SGuiDate;
import sa.lib.gui.SGuiParams;
import scale.mod.SModConsts;

/**
 *
 * @author Sergio Flores
 */
public class SViewIog extends SGridPaneView implements ActionListener {

    private SGridFilterDatePeriod moFilterDatePeriod;
    private SPaneFilter moPaneFilter;

    private JButton moButtonInAdjustment;
    private JButton moButtonInInventory;
    private JButton moButtonOutAdjustment;
    private JButton moButtonOutInventory;
    private JButton moButtonOutTransfer;
    private JButton moButtonOutMixingPasive;
    private JButton moButtonOutMixingActive;
    private JButton moButtonOutConvertion;
    private JButton moButtonInRawMaterialAsc;
    private JButton moButtonInRawMaterialRet;
    private JButton moButtonOutRawMaterialAsc;
    private JButton moButtonOutRawMaterialRet;
    private JButton moButtonInFinishedGoodAsc;
    private JButton moButtonInFinishedGoodRet;
    private JButton moButtonOutFinishedGoodAsc;
    private JButton moButtonOutFinishedGoodRet;

    public SViewIog(SGuiClient client, String title, int subtype) {
        super(client, SGridConsts.GRID_PANE_VIEW, SModConsts.O_IOG, subtype, title);
        setRowButtonsEnabled(false, true, true, false, true);
        initComponetsCustom();
    }

    /*
    * Private methods
    */

    private void initComponetsCustom() {
        moFilterDatePeriod = new SGridFilterDatePeriod(miClient, this, SGuiConsts.DATE_PICKER_DATE_PERIOD);
        moFilterDatePeriod.initFilter(new SGuiDate(SGuiConsts.GUI_DATE_MONTH, miClient.getSession().getCurrentDate().getTime()));
        getPanelCommandsSys(SGuiConsts.PANEL_CENTER).add(moFilterDatePeriod);

        moPaneFilter = new SPaneFilter(this, SModConsts.OS_IOG_TP);
        moPaneFilter.initFilter(null);
        getPanelCommandsSys(SGuiConsts.PANEL_CENTER).add(moPaneFilter);

        switch(mnGridSubtype) {
            case 1:
                moButtonInAdjustment = SGridUtils.createButton(new ImageIcon(getClass().getResource("/som/gui/img/icon_std_stk_adj_in.gif")), "Entrada ajuste", this);
                getPanelCommandsSys(SGuiConsts.PANEL_CENTER).add(moButtonInAdjustment);
                moButtonOutAdjustment = SGridUtils.createButton(new ImageIcon(getClass().getResource("/som/gui/img/icon_std_stk_adj_out.gif")), "Salida ajuste", this);
                getPanelCommandsSys(SGuiConsts.PANEL_CENTER).add(moButtonOutAdjustment);
                moButtonInInventory = SGridUtils.createButton(new ImageIcon(getClass().getResource("/som/gui/img/icon_std_stk_inv_in.gif")), "Entrada inventario inicial", this);
                getPanelCommandsSys(SGuiConsts.PANEL_CENTER).add(moButtonInInventory);
                moButtonOutInventory = SGridUtils.createButton(new ImageIcon(getClass().getResource("/som/gui/img/icon_std_stk_inv_out.gif")), "Salida inventario final", this);
                getPanelCommandsSys(SGuiConsts.PANEL_CENTER).add(moButtonOutInventory);
                moButtonOutTransfer = SGridUtils.createButton(new ImageIcon(getClass().getResource("/som/gui/img/icon_std_stk_tra.gif")), "Salida traspaso", this);
                getPanelCommandsSys(SGuiConsts.PANEL_CENTER).add(moButtonOutTransfer);
                moButtonOutMixingPasive = SGridUtils.createButton(new ImageIcon(getClass().getResource("/som/gui/img/icon_std_stk_mix_pas.gif")), "Salida traspaso mezcla pasiva", this);
                getPanelCommandsSys(SGuiConsts.PANEL_CENTER).add(moButtonOutMixingPasive);
                moButtonOutMixingActive = SGridUtils.createButton(new ImageIcon(getClass().getResource("/som/gui/img/icon_std_stk_mix_act.gif")), "Salida traspaso mezcla activa", this);
                getPanelCommandsSys(SGuiConsts.PANEL_CENTER).add(moButtonOutMixingActive);
                moButtonOutConvertion = SGridUtils.createButton(new ImageIcon(getClass().getResource("/som/gui/img/icon_std_stk_cnv.gif")), "Salida traspaso conversión", this);
                getPanelCommandsSys(SGuiConsts.PANEL_CENTER).add(moButtonOutConvertion);
                break;
            case 2:
                moButtonOutRawMaterialAsc = SGridUtils.createButton(new ImageIcon(getClass().getResource("/som/gui/img/icon_std_mfg_rm_asd.gif")), "Salida entrega MP", this);
                getPanelCommandsSys(SGuiConsts.PANEL_CENTER).add(moButtonOutRawMaterialAsc);
                moButtonOutRawMaterialAsc.setEnabled(true);
                moButtonOutRawMaterialRet = SGridUtils.createButton(new ImageIcon(getClass().getResource("/som/gui/img/icon_std_mfg_rm_ret.gif")), "Salida devolución MP", this);
                getPanelCommandsSys(SGuiConsts.PANEL_CENTER).add(moButtonOutRawMaterialRet);
                moButtonOutRawMaterialRet.setEnabled(false);
                break;
            case 3:
                moButtonInRawMaterialAsc = SGridUtils.createButton(new ImageIcon(getClass().getResource("/som/gui/img/icon_std_mfg_rm_asd.gif")), "Entrada entrega MP", this);
                getPanelCommandsSys(SGuiConsts.PANEL_CENTER).add(moButtonInRawMaterialAsc);
                moButtonInRawMaterialAsc.setEnabled(false);
                moButtonInRawMaterialRet = SGridUtils.createButton(new ImageIcon(getClass().getResource("/som/gui/img/icon_std_mfg_rm_ret.gif")), "Entrada devolución MP", this);
                getPanelCommandsSys(SGuiConsts.PANEL_CENTER).add(moButtonInRawMaterialRet);
                moButtonInRawMaterialRet.setEnabled(true);
                break;
            case 4:
                moButtonInFinishedGoodAsc = SGridUtils.createButton(new ImageIcon(getClass().getResource("/som/gui/img/icon_std_mfg_fg_asd.gif")), "Entrada entrega PT", this);
                getPanelCommandsSys(SGuiConsts.PANEL_CENTER).add(moButtonInFinishedGoodAsc);
                moButtonInFinishedGoodAsc.setEnabled(true);
                moButtonInFinishedGoodRet = SGridUtils.createButton(new ImageIcon(getClass().getResource("/som/gui/img/icon_std_mfg_fg_ret.gif")), "Entrada devolución PT", this);
                getPanelCommandsSys(SGuiConsts.PANEL_CENTER).add(moButtonInFinishedGoodRet);
                moButtonInFinishedGoodRet.setEnabled(false);
                break;
            case 5:
                moButtonOutFinishedGoodAsc = SGridUtils.createButton(new ImageIcon(getClass().getResource("/som/gui/img/icon_std_mfg_fg_asd.gif")), "Salida entrega PT", this);
                getPanelCommandsSys(SGuiConsts.PANEL_CENTER).add(moButtonOutFinishedGoodAsc);
                moButtonOutFinishedGoodAsc.setEnabled(false);
                moButtonOutFinishedGoodRet = SGridUtils.createButton(new ImageIcon(getClass().getResource("/som/gui/img/icon_std_mfg_fg_ret.gif")), "Salida devolución PT", this);
                getPanelCommandsSys(SGuiConsts.PANEL_CENTER).add(moButtonOutFinishedGoodRet);
                moButtonOutFinishedGoodRet.setEnabled(true);
                break;
        }
    }

    private void actionInventoryMove(int[] anIogTp) {
        SGuiParams params = null;

        params = new SGuiParams();
        params.getParamsMap().put(SModConsts.OS_IOG_TP, anIogTp);

        miClient.getSession().getModule(SModConsts.MOD_OPE, SLibConsts.UNDEFINED).showForm(SModConsts.O_IOG, SLibConsts.UNDEFINED, params);
        miClient.getSession().notifySuscriptors(mnGridType);
    }

    /*
    * Public methods
    */

    @Override
    public void prepareSqlQuery() {
        String sql = "";
        Object filter = null;

        moPaneSettings = new SGridPaneSettings(1);
        moPaneSettings.setUpdatableApplying(false);
        moPaneSettings.setDisableableApplying(false);
        moPaneSettings.setDeletableApplying(false);
        moPaneSettings.setDisabledApplying(false);
        moPaneSettings.setDeletedApplying(true);
        moPaneSettings.setSystemApplying(true);
        moPaneSettings.setUserInsertApplying(true);
        moPaneSettings.setUserUpdateApplying(true);

        switch(mnGridSubtype) {
            /*
            case SModConsts.SX_INV:
                sql += "v.fk_iog_ct IN(" +
                        SModSysConsts.SS_IOG_TP_IN_EXT_ADJ[0] + ", " +
                        SModSysConsts.SS_IOG_TP_IN_EXT_INV[0] + ", " +
                        SModSysConsts.SS_IOG_TP_OUT_EXT_ADJ[0] + ", " +
                        SModSysConsts.SS_IOG_TP_OUT_EXT_INV[0] + ", " +
                        SModSysConsts.SS_IOG_TP_IN_INT_TRA[0] + ", " +
                        SModSysConsts.SS_IOG_TP_OUT_INT_TRA[0] + ", " +
                        SModSysConsts.SS_IOG_TP_IN_INT_MIX_PAS[0] + ", " +
                        SModSysConsts.SS_IOG_TP_OUT_INT_MIX_PAS[0] + ", " +
                        SModSysConsts.SS_IOG_TP_IN_INT_MIX_ACT[0] + ", " +
                        SModSysConsts.SS_IOG_TP_OUT_INT_MIX_ACT[0] + ", " +
                        SModSysConsts.SS_IOG_TP_IN_INT_CNV[0] + ", " +
                        SModSysConsts.SS_IOG_TP_OUT_INT_CNV[0] + ", " +
                        SModSysConsts.SS_IOG_TP_IN_PUR_PUR[0] + ", " +
                        SModSysConsts.SS_IOG_TP_OUT_SAL_SAL[0] + ") AND " +
                       "v.fk_iog_cl IN(" +
                        SModSysConsts.SS_IOG_TP_IN_EXT_ADJ[1] + ", " +
                        SModSysConsts.SS_IOG_TP_IN_EXT_INV[1] + ", " +
                        SModSysConsts.SS_IOG_TP_IN_INT_TRA[1] + ", " +
                        SModSysConsts.SS_IOG_TP_OUT_INT_TRA[1] + ", " +
                        SModSysConsts.SS_IOG_TP_IN_INT_MIX_PAS[1] + ", " +
                        SModSysConsts.SS_IOG_TP_OUT_INT_MIX_PAS[1] + ", " +
                        SModSysConsts.SS_IOG_TP_IN_INT_MIX_ACT[1] + ", " +
                        SModSysConsts.SS_IOG_TP_OUT_INT_MIX_ACT[1] + ", " +
                        SModSysConsts.SS_IOG_TP_IN_INT_CNV[1] + ", " +
                        SModSysConsts.SS_IOG_TP_OUT_INT_CNV[1] + ", " +
                        SModSysConsts.SS_IOG_TP_OUT_EXT_ADJ[1] + ", " +
                        SModSysConsts.SS_IOG_TP_OUT_EXT_INV[1] + ", " +
                        SModSysConsts.SS_IOG_TP_IN_PUR_PUR[1] + ", " +
                        SModSysConsts.SS_IOG_TP_OUT_SAL_SAL[1] + ") AND " +
                       "v.fk_iog_tp IN(" +
                        SModSysConsts.SS_IOG_TP_IN_EXT_ADJ[2] + ", " +
                        SModSysConsts.SS_IOG_TP_IN_EXT_INV[2] + ", " +
                        SModSysConsts.SS_IOG_TP_IN_INT_TRA[2] + ", " +
                        SModSysConsts.SS_IOG_TP_OUT_INT_TRA[2] + ", " +
                        SModSysConsts.SS_IOG_TP_IN_INT_MIX_PAS[2] + ", " +
                        SModSysConsts.SS_IOG_TP_OUT_INT_MIX_PAS[2] + ", " +
                        SModSysConsts.SS_IOG_TP_IN_INT_MIX_ACT[2] + ", " +
                        SModSysConsts.SS_IOG_TP_OUT_INT_MIX_ACT[2] + ", " +
                        SModSysConsts.SS_IOG_TP_IN_INT_CNV[2] + ", " +
                        SModSysConsts.SS_IOG_TP_OUT_INT_CNV[2] + ", " +
                        SModSysConsts.SS_IOG_TP_OUT_EXT_ADJ[2] + ", " +
                        SModSysConsts.SS_IOG_TP_OUT_EXT_INV[2] + ", " +
                        SModSysConsts.SS_IOG_TP_IN_PUR_PUR[2] + ", " +
                        SModSysConsts.SS_IOG_TP_OUT_SAL_SAL[2] + ") ";
                break;
            case SModConsts.SX_INV_IN_RM:
                sql += "v.fk_iog_ct IN(" + SModSysConsts.SS_IOG_TP_OUT_MFG_RM_ASD[0] + ") AND " +
                       "v.fk_iog_cl IN(" + SModSysConsts.SS_IOG_TP_OUT_MFG_RM_ASD[1] + ") AND " +
                       "v.fk_iog_tp IN(" + SModSysConsts.SS_IOG_TP_OUT_MFG_RM_ASD[2] + ") ";
                break;
            case SModConsts.SX_INV_OUT_RM:
                sql += "v.fk_iog_ct IN(" + SModSysConsts.SS_IOG_TP_IN_MFG_RM_RET[0] + ") AND " +
                       "v.fk_iog_cl IN(" + SModSysConsts.SS_IOG_TP_IN_MFG_RM_RET[1] + ") AND " +
                       "v.fk_iog_tp IN(" + SModSysConsts.SS_IOG_TP_IN_MFG_RM_RET[2] + ") ";
                break;
            case SModConsts.SX_INV_IN_FG:
                sql += "v.fk_iog_ct IN(" + SModSysConsts.SS_IOG_TP_IN_MFG_FG_ASD[0] + ") AND " +
                       "v.fk_iog_cl IN(" + SModSysConsts.SS_IOG_TP_IN_MFG_FG_ASD[1] + ") AND " +
                       "v.fk_iog_tp IN(" + SModSysConsts.SS_IOG_TP_IN_MFG_FG_ASD[2] + ") ";
                break;
            case SModConsts.SX_INV_OUT_FG:
                sql += "v.fk_iog_ct IN(" + SModSysConsts.SS_IOG_TP_OUT_MFG_FG_RET[0] + ") AND " +
                       "v.fk_iog_cl IN(" + SModSysConsts.SS_IOG_TP_OUT_MFG_FG_RET[1] + ") AND " +
                       "v.fk_iog_tp IN(" + SModSysConsts.SS_IOG_TP_OUT_MFG_FG_RET[2] + ") ";
                break;*
            */
        }

        filter = ((SGridFilterValue) moFiltersMap.get(SGridConsts.FILTER_DELETED)).getValue();
        if ((Boolean) filter) {
            sql += (sql.isEmpty() ? "" : "AND ") + "v.b_del = 0 ";
        }

        filter = ((SGridFilterValue) moFiltersMap.get(SGridConsts.FILTER_DATE_PERIOD)).getValue();
        sql += (sql.isEmpty() ? "" : "AND ") + SGridUtils.getSqlFilterDate("v.dt", (SGuiDate) filter);

        filter = ((SGridFilterValue) moFiltersMap.get(SModConsts.OS_IOG_TP)).getValue();
        if (filter != null) {
            sql += (sql.length() == 0 ? "" : "AND ") + SGridUtils.getSqlFilterKey(new String[] { "v.fk_iog_ct", "v.fk_iog_cl", "v.fk_iog_tp", }, (int[]) filter);
        }

        msSql = "SELECT v.id_iog AS " + SDbConsts.FIELD_ID + "1, " +
            "v.num AS number, " +
            "v.dt AS " + SDbConsts.FIELD_DATE + ", " +
            "v.qty AS f_qty, " +
            "v.b_del AS " + SDbConsts.FIELD_IS_DEL + ", " +
            "v.b_sys AS " + SDbConsts.FIELD_IS_SYS + ", " +
            "v.fk_iog_ct, " +
            "v.fk_iog_cl, " +
            "v.fk_iog_tp, " +
            "v.fk_iog_adj_tp, " +
            "v.fk_item, " +
            "v.fk_unit, " +
            "v.fk_wah_co, " +
            "v.fk_wah_cob, " +
            "v.fk_wah_wah, " +
            "v.fk_ext_dps_year_n, " +
            "v.fk_ext_dps_doc_n, " +
            "v.fk_ext_dps_ety_n, " +
            "v.fk_ext_iog_year_n, " +
            "v.fk_ext_iog_doc_n, " +
            "v.fk_ext_iog_ety_n, " +
            "v.fk_usr_ins AS " + SDbConsts.FIELD_USER_INS_ID + ", " +
            "v.fk_usr_upd AS " + SDbConsts.FIELD_USER_UPD_ID + ", " +
            "v.ts_usr_ins AS " + SDbConsts.FIELD_USER_INS_TS + ", " +
            "v.ts_usr_upd AS " + SDbConsts.FIELD_USER_UPD_TS + ", " +
            "ct.code AS ct_code, " +
            "ct.name AS ct_name, " +
            "cl.code AS cl_code, " +
            "cl.name AS cl_name, " +
            "tp.code AS tp_code, " +
            "tp.name AS tp_name, " +
            "adj.code AS adj_code, " +
            "adj.name AS adj_name, " +
            "co.code AS co_code, " +
            "co.name AS co_name, " +
            "cob.code AS cob_code, " +
            "cob.name AS cob_name, " +
            "wah.code AS wah_code, " +
            "wah.name AS wah_name, " +
            "i.code AS i_code, " +
            "i.name AS i_name, " +
            "u.code AS u_code, " +
            "u.name AS u_name, " +
            "CONCAT(tp.code, '-', v.num) AS f_num, " +
            "ui.name AS " + SDbConsts.FIELD_USER_INS_NAME + ", " +
            "uu.name AS " + SDbConsts.FIELD_USER_UPD_NAME + ", " +
            "tp.code AS " + SDbConsts.FIELD_CODE + ", " +
            "CONCAT(tp.code, '-', v.num) AS " + SDbConsts.FIELD_NAME + " " +
            "FROM " + SModConsts.TablesMap.get(SModConsts.O_IOG) + " AS v " +
            "INNER JOIN " + SModConsts.TablesMap.get(SModConsts.OS_IOG_CL) + " AS cl ON v.fk_iog_ct = cl.id_iog_ct AND v.fk_iog_cl = cl.id_iog_cl " +
            "INNER JOIN " + SModConsts.TablesMap.get(SModConsts.OS_IOG_TP) + " AS tp ON v.fk_iog_ct = tp.id_iog_ct AND v.fk_iog_cl = tp.id_iog_cl AND v.fk_iog_tp = tp.id_iog_tp " +
            "INNER JOIN " + SModConsts.TablesMap.get(SModConsts.OS_ADJ_TP) + " AS adj ON v.fk_iog_adj_tp = adj.id_iog_adj_tp " +
            "INNER JOIN " + SModConsts.TablesMap.get(SModConsts.OU_ITM) + " AS i ON v.fk_item = i.id_item " +
            "INNER JOIN " + SModConsts.TablesMap.get(SModConsts.OU_USR) + " AS ui ON v.fk_usr_ins = ui.id_usr " +
            "INNER JOIN " + SModConsts.TablesMap.get(SModConsts.OU_USR) + " AS uu ON v.fk_usr_upd = uu.id_usr " +
            (sql.isEmpty() ? "" : "WHERE " + sql) +
            "ORDER BY v.num, co.name, co.code, cob.name, cob.code, wah.name, wah.code ";
    }

    @Override
    public ArrayList<SGridColumnView> createGridColumns() {
        ArrayList<SGridColumnView> columns = new ArrayList<SGridColumnView>();

        columns.add(new SGridColumnView(SGridConsts.COL_TYPE_TEXT_REG_NUM, "f_num", "Folio"));
        columns.add(new SGridColumnView(SGridConsts.COL_TYPE_DATE, SDbConsts.FIELD_DATE, SGridConsts.COL_TITLE_DATE));
        columns.add(new SGridColumnView(SGridConsts.COL_TYPE_TEXT_CODE_CO, "cob_code", "Sucursal"));
        columns.add(new SGridColumnView(SGridConsts.COL_TYPE_TEXT_CODE_CO, "wah_code", "Almacén"));
        columns.add(new SGridColumnView(SGridConsts.COL_TYPE_TEXT_NAME_CAT_S, "tp_name", "Tipo movimiento"));
        columns.add(new SGridColumnView(SGridConsts.COL_TYPE_TEXT_CODE_CAT, "adj_code", "Tipo ajuste"));
        columns.add(new SGridColumnView(SGridConsts.COL_TYPE_TEXT_NAME_CAT_L, "i_name", "Ítem"));
        columns.add(new SGridColumnView(SGridConsts.COL_TYPE_TEXT_CODE_ITM, "i_code", "Ítem código"));
        columns.add(new SGridColumnView(SGridConsts.COL_TYPE_DEC_2D, "f_qty", "Cantidad"));
        columns.add(new SGridColumnView(SGridConsts.COL_TYPE_TEXT_CODE_UNT, "u_code", "Unidad"));
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
        moSuscriptionsSet.add(SModConsts.O_STK);
        moSuscriptionsSet.add(SModConsts.OU_ITM);
        moSuscriptionsSet.add(SModConsts.OU_USR);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JButton) {
            JButton button = (JButton) e.getSource();
            /*
            if (button == moButtonInAdjustment && moButtonInAdjustment.isEnabled()) {
                actionInventoryMove(SModSysConsts.SS_IOG_TP_IN_EXT_ADJ);
            }
            else if (button == moButtonOutAdjustment && moButtonOutAdjustment.isEnabled()) {
                actionInventoryMove(SModSysConsts.SS_IOG_TP_OUT_EXT_ADJ);
            }
            else if (button == moButtonInInventory && moButtonInInventory.isEnabled()) {
                actionInventoryMove(SModSysConsts.SS_IOG_TP_IN_EXT_INV);
            }
            else if (button == moButtonOutInventory && moButtonOutInventory.isEnabled()) {
                actionInventoryMove(SModSysConsts.SS_IOG_TP_OUT_EXT_INV);
            }
            else if (button == moButtonOutTransfer && moButtonOutTransfer.isEnabled()) {
                actionInventoryMove(SModSysConsts.SS_IOG_TP_OUT_INT_TRA);
            }
            else if (button == moButtonOutMixingPasive && moButtonOutMixingPasive.isEnabled()) {
                actionInventoryMove(SModSysConsts.SS_IOG_TP_OUT_INT_MIX_PAS);
            }
            else if (button == moButtonOutMixingActive && moButtonOutMixingActive.isEnabled()) {
                actionInventoryMove(SModSysConsts.SS_IOG_TP_OUT_INT_MIX_ACT);
            }
            else if (button == moButtonOutConvertion && moButtonOutConvertion.isEnabled()) {
                actionInventoryMove(SModSysConsts.SS_IOG_TP_OUT_INT_CNV);
            }
            else if (button == moButtonInRawMaterialRet && moButtonInRawMaterialRet.isEnabled()) {
                actionInventoryMove(SModSysConsts.SS_IOG_TP_IN_MFG_RM_RET);
            }
            else if (button == moButtonOutRawMaterialAsc && moButtonOutRawMaterialAsc.isEnabled()) {
                actionInventoryMove(SModSysConsts.SS_IOG_TP_OUT_MFG_RM_ASD);
            }
            else if (button == moButtonInFinishedGoodAsc && moButtonInFinishedGoodAsc.isEnabled()) {
                actionInventoryMove(SModSysConsts.SS_IOG_TP_IN_MFG_FG_ASD);
            }
            else if (button == moButtonOutFinishedGoodRet && moButtonOutFinishedGoodRet.isEnabled()) {
                actionInventoryMove(SModSysConsts.SS_IOG_TP_OUT_MFG_FG_RET);
            }
            */
        }
    }
}
