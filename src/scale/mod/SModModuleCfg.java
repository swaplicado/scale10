/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package scale.mod;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
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
import scale.mod.ope.db.SDbConfig;
import scale.mod.ope.db.SDbUser;
import scale.mod.ope.db.SDbYear;
import scale.mod.ope.form.SFormUser;
import scale.mod.ope.form.SFormYear;
import scale.mod.ope.view.SViewConfig;
import scale.mod.ope.view.SViewUser;
import scale.mod.ope.view.SViewYear;

/**
 *
 * @author Sergio Flores
 */
public class SModModuleCfg extends SGuiModule implements ActionListener {

    private JMenu mjCfg;
    private JMenuItem mjCfgConfig;
    private JMenuItem mjCfgUser;
    private JMenu mjCtr;
    private JMenuItem mjCtrYear;

    private SFormUser moFormUser;
    private SFormYear moFormYear;

    public SModModuleCfg(SGuiClient client) {
        super(client, SModConsts.MOD_CFG, SLibConsts.UNDEFINED);
        initComponents();
    }

    private void initComponents() {
        mjCfg = new JMenu("Configuración");

        mjCfgConfig = new JMenuItem("Configuración");
        mjCfgUser = new JMenuItem("Usuarios");

        mjCfg.add(mjCfgConfig);
        mjCfg.add(mjCfgUser);

        mjCfgConfig.addActionListener(this);
        mjCfgUser.addActionListener(this);

        mjCtr = new JMenu("Control interno");

        mjCtrYear = new JMenuItem("Apertura/cierre meses");

        mjCtr.add(mjCtrYear);

        mjCtrYear.addActionListener(this);

        mjCfg.setEnabled(miClient.getSession().getUser().isSupervisor());
        mjCtr.setEnabled(miClient.getSession().getUser().isAdministrator());
    }

    @Override
    public JMenu[] getMenus() {
        return new JMenu[] { mjCfg, mjCtr };
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
            case SModConsts.OU_CFG:
                registry = new SDbConfig();
                break;
            case SModConsts.OU_USR:
                registry = new SDbUser();
                break;
            case SModConsts.O_YEA:
                registry = new SDbYear();
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
            case SModConsts.OS_USR_TP:
                settings = new SGuiCatalogueSettings("Tipo usuario", 1);
                sql = "SELECT id_usr_tp AS " + SDbConsts.FIELD_ID + "1, name AS " + SDbConsts.FIELD_ITEM + " "
                        + "FROM " + SModConsts.TablesMap.get(type) + " WHERE b_del = 0 ORDER BY sort ";
                break;
            case SModConsts.OU_USR:
                settings = new SGuiCatalogueSettings("Usuario", 1);
                sql = "SELECT id_usr AS " + SDbConsts.FIELD_ID + "1, name AS " + SDbConsts.FIELD_ITEM + " "
                        + "FROM " + SModConsts.TablesMap.get(type) + " WHERE b_dis = 0 AND b_del = 0 ORDER BY name, id_usr ";
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
            case SModConsts.OU_CFG:
                view = new SViewConfig(miClient, "Configuración");
                break;
            case SModConsts.OU_USR:
                view = new SViewUser(miClient, "Usuarios");
                break;
            case SModConsts.O_YEA:
                view = new SViewYear(miClient, "Apertura/cierre meses");
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
            case SModConsts.OU_USR:
                if (moFormUser == null) moFormUser = new SFormUser(miClient, "Usuario");
                form = moFormUser;
                break;
            case SModConsts.O_YEA:
                if (moFormYear == null) moFormYear = new SFormYear(miClient, "Año");
                form = moFormYear;
                break;
            default:
                miClient.showMsgBoxError(SLibConsts.ERR_MSG_OPTION_UNKNOWN);
        }

        return form;
    }

    @Override
    public SGuiReport getReport(final int type, final int subtype, final SGuiParams params) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JMenuItem) {
            JMenuItem menuItem = (JMenuItem) e.getSource();

            if (menuItem == mjCfgConfig) {
                showView(SModConsts.OU_CFG, SLibConsts.UNDEFINED, null);
            }
            else if (menuItem == mjCfgUser) {
                showView(SModConsts.OU_USR, SLibConsts.UNDEFINED, null);
            }
            else if (menuItem == mjCtrYear) {
                showView(SModConsts.O_YEA, SLibConsts.UNDEFINED, null);
            }
        }
    }
}
