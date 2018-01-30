/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package scale.mod;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import sa.gui.util.SUtilConsts;
import sa.lib.SLibConsts;
import sa.lib.db.SDbConsts;
import sa.lib.db.SDbRegistry;
import sa.lib.db.SDbRegistrySysFly;
import sa.lib.grid.SGridPaneView;
import sa.lib.gui.SGuiCatalogueSettings;
import sa.lib.gui.SGuiClient;
import sa.lib.gui.SGuiForm;
import sa.lib.gui.SGuiModule;
import sa.lib.gui.SGuiOptionPicker;
import sa.lib.gui.SGuiParams;
import sa.lib.gui.SGuiReport;
import scale.mod.ope.db.SDbItem;
import scale.mod.ope.db.SDbLocality;
import scale.mod.ope.db.SDbSupplier;
import scale.mod.ope.db.SDbWeighing;
import scale.mod.ope.form.SDialogReportWeighing;
import scale.mod.ope.form.SFormItem;
import scale.mod.ope.form.SFormLocality;
import scale.mod.ope.form.SFormSupplier;
import scale.mod.ope.form.SFormWeighing;
import scale.mod.ope.view.SViewItem;
import scale.mod.ope.view.SViewLocality;
import scale.mod.ope.view.SViewPayment;
import scale.mod.ope.view.SViewSupplier;
import scale.mod.ope.view.SViewWeighing;
import scale.mod.ope.view.SViewWeighingPayment;

/**
 *
 * @author Sergio Flores
 */
public class SModModuleOpe extends SGuiModule implements ActionListener {

    private JMenu mjCat;
    private JMenuItem mjCatItem;
    private JMenuItem mjCatSupplier;
    private JMenuItem mjCatLocality;
    private JMenu mjWei;
    private JMenuItem mjWeiWeighing;
    private JMenuItem mjWeiWeighingPaymentPend;
    private JMenuItem mjWeiWeighingPayment;
    private JMenuItem mjWeiPayment;
    private JMenu mjRep;
    private JMenuItem mjRepWeighing;

    private SFormItem moFormItem;
    private SFormSupplier moFormSupplier;
    private SFormLocality moFormLocality;
    private SFormWeighing moFormWeighing;

    public SModModuleOpe(SGuiClient client) {
        super(client, SModConsts.MOD_OPE, SLibConsts.UNDEFINED);
        initComponents();
    }

    private void initComponents() {
        mjCat = new JMenu("Catálogos");

        mjCatItem = new JMenuItem("Productos");
        mjCatSupplier = new JMenuItem("Proveedores");
        mjCatLocality = new JMenuItem("Localidades");

        mjCat.add(mjCatItem);
        mjCat.add(mjCatSupplier);
        mjCat.add(mjCatLocality);

        mjCatItem.addActionListener(this);
        mjCatSupplier.addActionListener(this);
        mjCatLocality.addActionListener(this);

        mjWei = new JMenu("Boletos");

        mjWeiWeighing = new JMenuItem("Boletos");
        mjWeiWeighingPaymentPend = new JMenuItem("Boletos por pagar");
        mjWeiWeighingPayment = new JMenuItem("Boletos pagados");
        mjWeiPayment = new JMenuItem("Pagos");

        mjWei.add(mjWeiWeighing);
        mjWei.addSeparator();
        mjWei.add(mjWeiWeighingPaymentPend);
        mjWei.add(mjWeiWeighingPayment);
        mjWei.addSeparator();
        mjWei.add(mjWeiPayment);

        mjWeiWeighingPaymentPend.setEnabled(miClient.getSession().getUser().isAdministrator());
        mjWeiWeighingPayment.setEnabled(miClient.getSession().getUser().isAdministrator());

        mjWeiWeighing.addActionListener(this);
        mjWeiWeighingPaymentPend.addActionListener(this);
        mjWeiWeighingPayment.addActionListener(this);
        mjWeiPayment.addActionListener(this);

        mjRep = new JMenu("Reportes");

        mjRepWeighing = new JMenuItem("Reporte de boletos");

        mjRep.add(mjRepWeighing);

        mjRepWeighing.addActionListener(this);

        mjCatItem.setEnabled(miClient.getSession().getUser().isAdministrator());
        mjRep.setEnabled(miClient.getSession().getUser().isAdministrator());
    }

    @Override
    public JMenu[] getMenus() {
        return new JMenu[] { mjCat, mjWei, mjRep };
    }

    @Override
    public SDbRegistry getRegistry(final int type, final SGuiParams params) {
        SDbRegistry registry = null;

        switch (type) {
            case SModConsts.OS_USR_TP:
                registry = new SDbRegistrySysFly(type) {
                    public void initRegistry() { }
                    public String getSqlTable() { return SModConsts.TablesMap.get(mnRegistryType); }
                    public String getSqlWhere(int[] pk) { return "WHERE id_usr_tp = " + pk[0] + " "; }
                };
                break;
            case SModConsts.OS_IOG_CL:
                registry = new SDbRegistrySysFly(type) {
                    public void initRegistry() { }
                    public String getSqlTable() { return SModConsts.TablesMap.get(mnRegistryType); }
                    public String getSqlWhere(int[] pk) { return "WHERE id_iog_cl = " + pk[0] + " "; }
                };
                break;
            case SModConsts.OS_IOG_TP:
                registry = new SDbRegistrySysFly(type) {
                    public void initRegistry() { }
                    public String getSqlTable() { return SModConsts.TablesMap.get(mnRegistryType); }
                    public String getSqlWhere(int[] pk) { return "WHERE id_iog_cl = " + pk[0] + " AND id_iog_tp = " + pk[1] + " "; }
                };
                break;
            case SModConsts.OS_ADJ_TP:
                registry = new SDbRegistrySysFly(type) {
                    public void initRegistry() { }
                    public String getSqlTable() { return SModConsts.TablesMap.get(mnRegistryType); }
                    public String getSqlWhere(int[] pk) { return "WHERE id_adj_tp = " + pk[0] + " "; }
                };
                break;
            case SModConsts.OU_LOC:
                registry = new SDbLocality();
                break;
            case SModConsts.OU_SUP:
                registry = new SDbSupplier();
                break;
            case SModConsts.OU_ITM:
                registry = new SDbItem();
                break;
            case SModConsts.O_WEI:
                registry = new SDbWeighing();
                break;
            default:
                miClient.showMsgBoxError(SLibConsts.ERR_MSG_OPTION_UNKNOWN);
        }

        return registry;
    }

    @Override
    public SGuiCatalogueSettings getCatalogueSettings(final int type, final int subtype, final SGuiParams params) {
        String sql = "";
        SGuiCatalogueSettings settings = null;

        switch (type) {
            case SModConsts.OU_LOC:
                settings = new SGuiCatalogueSettings("Localidad", 1);
                sql = "SELECT id_loc AS " + SDbConsts.FIELD_ID + "1, name AS " + SDbConsts.FIELD_ITEM + " "
                        + "FROM " + SModConsts.TablesMap.get(type) + " WHERE b_dis = 0 AND b_del = 0 ORDER BY name, id_loc ";
                break;
            case SModConsts.OU_SUP:
                settings = new SGuiCatalogueSettings("Proveedor", 1);
                sql = "SELECT id_sup AS " + SDbConsts.FIELD_ID + "1, name AS " + SDbConsts.FIELD_ITEM + " "
                        + "FROM " + SModConsts.TablesMap.get(type) + " WHERE b_dis = 0 AND b_del = 0 ORDER BY name, id_sup ";
                break;
            case SModConsts.OU_ITM:
                settings = new SGuiCatalogueSettings("Producto", 1);
                sql = "SELECT id_itm AS " + SDbConsts.FIELD_ID + "1, name AS " + SDbConsts.FIELD_ITEM + " "
                        + "FROM " + SModConsts.TablesMap.get(type) + " WHERE b_dis = 0 AND b_del = 0 ORDER BY name, id_itm ";
                break;
            default:
                miClient.showMsgBoxError(SLibConsts.ERR_MSG_OPTION_UNKNOWN);
        }

        if (settings != null) {
            settings.setSql(sql);
        }

        return settings;
    }

    @Override
    public SGridPaneView getView(final int type, final int subtype, final SGuiParams params) {
        SGridPaneView view = null;

        switch (type) {
            case SModConsts.OU_LOC:
                view = new SViewLocality(miClient, "Localidades");
                break;
            case SModConsts.OU_SUP:
                view = new SViewSupplier(miClient, "Proveedores");
                break;
            case SModConsts.OU_ITM:
                view = new SViewItem(miClient, "Productos");
                break;
            case SModConsts.O_WEI:
                view = new SViewWeighing(miClient, "Boletos");
                break;
            case SModConsts.OX_WEI_PAY:
                view = new SViewWeighingPayment(miClient, subtype, "Boletos " + (subtype == SUtilConsts.PAY ? "pagados" : "por pagar"));
                break;
            case SModConsts.O_PAY:
                view = new SViewPayment(miClient, "Pagos");
                break;
            default:
                miClient.showMsgBoxError(SLibConsts.ERR_MSG_OPTION_UNKNOWN);
        }

        return view;
    }

    @Override
    public SGuiOptionPicker getOptionPicker(final int type, final int subtype, final SGuiParams params) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public SGuiForm getForm(final int type, final int subtype, final SGuiParams params) {
        SGuiForm form = null;

        switch (type) {
            case SModConsts.OU_LOC:
                if (moFormLocality == null) moFormLocality = new SFormLocality(miClient, "Localidad");
                form = moFormLocality;
                break;
            case SModConsts.OU_SUP:
                if (moFormSupplier == null) moFormSupplier = new SFormSupplier(miClient, "Proveedor");
                form = moFormSupplier;
                break;
            case SModConsts.OU_ITM:
                if (moFormItem == null) moFormItem = new SFormItem(miClient, "Producto");
                form = moFormItem;
                break;
            case SModConsts.O_WEI:
                if (moFormWeighing == null) moFormWeighing = new SFormWeighing(miClient, "Boleto");
                form = moFormWeighing;
                break;
            default:
                miClient.showMsgBoxError(SLibConsts.ERR_MSG_OPTION_UNKNOWN);
        }

        return form;
    }

    @Override
    public SGuiReport getReport(final int type, final int subtype, final SGuiParams params) {
        SGuiReport report = null;

        switch (type) {
            case SModConsts.OR_WEI:
                report = new SGuiReport("reps/wei_prt.jasper", "Boleto");
                break;
            case SModConsts.OR_WEI_REP:
                report = new SGuiReport("reps/wei_rep.jasper", "Reporte de boletos");
                break;
            default:
                miClient.showMsgBoxError(SLibConsts.ERR_MSG_OPTION_UNKNOWN);
        }

        return report;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JMenuItem) {
            JMenuItem menuItem = (JMenuItem) e.getSource();

            if (menuItem == mjCatItem) {
                showView(SModConsts.OU_ITM, SLibConsts.UNDEFINED, null);
            }
            else if (menuItem == mjCatSupplier) {
                showView(SModConsts.OU_SUP, SLibConsts.UNDEFINED, null);
            }
            else if (menuItem == mjCatLocality) {
                showView(SModConsts.OU_LOC, SLibConsts.UNDEFINED, null);
            }
            else if (menuItem == mjWeiWeighing) {
                showView(SModConsts.O_WEI, SLibConsts.UNDEFINED, null);
            }
            else if (menuItem == mjWeiWeighingPaymentPend) {
                showView(SModConsts.OX_WEI_PAY, SUtilConsts.PAY_PEND, null);
            }
            else if (menuItem == mjWeiWeighingPayment) {
                showView(SModConsts.OX_WEI_PAY, SUtilConsts.PAY, null);
            }
            else if (menuItem == mjWeiPayment) {
                showView(SModConsts.O_PAY, SLibConsts.UNDEFINED, null);
            }
            else if (menuItem == mjRepWeighing) {
                new SDialogReportWeighing(miClient, "Reporte de boletos").setVisible(true);
            }
        }
    }
}
