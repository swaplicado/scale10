/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scale.mod.ope.form;

import java.util.Calendar;
import sa.lib.SLibConsts;
import sa.lib.SLibTimeUtils;
import sa.lib.SLibUtils;
import sa.lib.db.SDbRegistry;
import sa.lib.gui.SGuiClient;
import sa.lib.gui.SGuiConsts;
import sa.lib.gui.SGuiUtils;
import sa.lib.gui.SGuiValidation;
import sa.lib.gui.bean.SBeanForm;
import scale.mod.SModConsts;
import scale.mod.ope.db.SDbYear;

/**
 *
 * @author Sergio Flores
 */
public class SFormYear extends SBeanForm {

    private SDbYear moRegistry;

    /**
     * Creates new form SFormLocality
     */
    public SFormYear(SGuiClient client, String title) {
        setFormSettings(client, SGuiConsts.BEAN_FORM_EDIT, SModConsts.O_YEA, SLibConsts.UNDEFINED, title);
        initComponents();
        initComponentsCustom();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jlYear = new javax.swing.JLabel();
        moIntYear = new sa.lib.gui.bean.SBeanFieldInteger();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        moBoolClosed01 = new sa.lib.gui.bean.SBeanFieldBoolean();
        moBoolClosed05 = new sa.lib.gui.bean.SBeanFieldBoolean();
        moBoolClosed09 = new sa.lib.gui.bean.SBeanFieldBoolean();
        moBoolClosed02 = new sa.lib.gui.bean.SBeanFieldBoolean();
        moBoolClosed06 = new sa.lib.gui.bean.SBeanFieldBoolean();
        moBoolClosed10 = new sa.lib.gui.bean.SBeanFieldBoolean();
        moBoolClosed03 = new sa.lib.gui.bean.SBeanFieldBoolean();
        moBoolClosed07 = new sa.lib.gui.bean.SBeanFieldBoolean();
        moBoolClosed11 = new sa.lib.gui.bean.SBeanFieldBoolean();
        moBoolClosed04 = new sa.lib.gui.bean.SBeanFieldBoolean();
        moBoolClosed08 = new sa.lib.gui.bean.SBeanFieldBoolean();
        moBoolClosed12 = new sa.lib.gui.bean.SBeanFieldBoolean();

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos del registro:"));
        jPanel1.setLayout(new java.awt.BorderLayout(0, 5));

        jPanel2.setLayout(new java.awt.GridLayout(1, 0, 0, 5));

        jPanel3.setLayout(new java.awt.FlowLayout(3, 5, 0));

        jlYear.setForeground(new java.awt.Color(0, 0, 255));
        jlYear.setText("Año:*");
        jlYear.setPreferredSize(new java.awt.Dimension(100, 23));
        jPanel3.add(jlYear);

        moIntYear.setPreferredSize(new java.awt.Dimension(50, 23));
        jPanel3.add(moIntYear);

        jPanel2.add(jPanel3);

        jPanel1.add(jPanel2, java.awt.BorderLayout.PAGE_START);

        jPanel4.setLayout(new java.awt.BorderLayout());

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Meses cerrados:"));
        jPanel5.setLayout(new java.awt.GridLayout(4, 3, 0, 5));

        moBoolClosed01.setText("Month");
        jPanel5.add(moBoolClosed01);

        moBoolClosed05.setText("Month");
        jPanel5.add(moBoolClosed05);

        moBoolClosed09.setText("Month");
        jPanel5.add(moBoolClosed09);

        moBoolClosed02.setText("Month");
        jPanel5.add(moBoolClosed02);

        moBoolClosed06.setText("Month");
        jPanel5.add(moBoolClosed06);

        moBoolClosed10.setText("Month");
        jPanel5.add(moBoolClosed10);

        moBoolClosed03.setText("Month");
        jPanel5.add(moBoolClosed03);

        moBoolClosed07.setText("Month");
        jPanel5.add(moBoolClosed07);

        moBoolClosed11.setText("Month");
        jPanel5.add(moBoolClosed11);

        moBoolClosed04.setText("Month");
        jPanel5.add(moBoolClosed04);

        moBoolClosed08.setText("Month");
        jPanel5.add(moBoolClosed08);

        moBoolClosed12.setText("Month");
        jPanel5.add(moBoolClosed12);

        jPanel4.add(jPanel5, java.awt.BorderLayout.NORTH);

        jPanel1.add(jPanel4, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JLabel jlYear;
    private sa.lib.gui.bean.SBeanFieldBoolean moBoolClosed01;
    private sa.lib.gui.bean.SBeanFieldBoolean moBoolClosed02;
    private sa.lib.gui.bean.SBeanFieldBoolean moBoolClosed03;
    private sa.lib.gui.bean.SBeanFieldBoolean moBoolClosed04;
    private sa.lib.gui.bean.SBeanFieldBoolean moBoolClosed05;
    private sa.lib.gui.bean.SBeanFieldBoolean moBoolClosed06;
    private sa.lib.gui.bean.SBeanFieldBoolean moBoolClosed07;
    private sa.lib.gui.bean.SBeanFieldBoolean moBoolClosed08;
    private sa.lib.gui.bean.SBeanFieldBoolean moBoolClosed09;
    private sa.lib.gui.bean.SBeanFieldBoolean moBoolClosed10;
    private sa.lib.gui.bean.SBeanFieldBoolean moBoolClosed11;
    private sa.lib.gui.bean.SBeanFieldBoolean moBoolClosed12;
    private sa.lib.gui.bean.SBeanFieldInteger moIntYear;
    // End of variables declaration//GEN-END:variables

    /*
     * Private methods
     */

    private void initComponentsCustom() {
        String[] months = SLibTimeUtils.createMonthsOfYearStd(Calendar.LONG);

        SGuiUtils.setWindowBounds(this, 400, 250);

        moIntYear.setIntegerSettings(SGuiUtils.getLabelName(jlYear), SGuiConsts.GUI_TYPE_INT_CAL_YEAR, true);
        moBoolClosed01.setBooleanSettings(SGuiUtils.getLabelName(moBoolClosed01.getText()), true);
        moBoolClosed02.setBooleanSettings(SGuiUtils.getLabelName(moBoolClosed02.getText()), true);
        moBoolClosed03.setBooleanSettings(SGuiUtils.getLabelName(moBoolClosed03.getText()), true);
        moBoolClosed04.setBooleanSettings(SGuiUtils.getLabelName(moBoolClosed04.getText()), true);
        moBoolClosed05.setBooleanSettings(SGuiUtils.getLabelName(moBoolClosed05.getText()), true);
        moBoolClosed06.setBooleanSettings(SGuiUtils.getLabelName(moBoolClosed06.getText()), true);
        moBoolClosed07.setBooleanSettings(SGuiUtils.getLabelName(moBoolClosed07.getText()), true);
        moBoolClosed08.setBooleanSettings(SGuiUtils.getLabelName(moBoolClosed08.getText()), true);
        moBoolClosed09.setBooleanSettings(SGuiUtils.getLabelName(moBoolClosed09.getText()), true);
        moBoolClosed10.setBooleanSettings(SGuiUtils.getLabelName(moBoolClosed10.getText()), true);
        moBoolClosed11.setBooleanSettings(SGuiUtils.getLabelName(moBoolClosed11.getText()), true);
        moBoolClosed12.setBooleanSettings(SGuiUtils.getLabelName(moBoolClosed12.getText()), true);

        moFields.addField(moIntYear);
        moFields.addField(moBoolClosed01);
        moFields.addField(moBoolClosed02);
        moFields.addField(moBoolClosed03);
        moFields.addField(moBoolClosed04);
        moFields.addField(moBoolClosed05);
        moFields.addField(moBoolClosed06);
        moFields.addField(moBoolClosed07);
        moFields.addField(moBoolClosed08);
        moFields.addField(moBoolClosed09);
        moFields.addField(moBoolClosed10);
        moFields.addField(moBoolClosed11);
        moFields.addField(moBoolClosed12);

        moFields.setFormButton(jbSave);

        moBoolClosed01.setText(months[0]);
        moBoolClosed02.setText(months[1]);
        moBoolClosed03.setText(months[2]);
        moBoolClosed04.setText(months[3]);
        moBoolClosed05.setText(months[4]);
        moBoolClosed06.setText(months[5]);
        moBoolClosed07.setText(months[6]);
        moBoolClosed08.setText(months[7]);
        moBoolClosed09.setText(months[8]);
        moBoolClosed10.setText(months[9]);
        moBoolClosed11.setText(months[10]);
        moBoolClosed12.setText(months[11]);
    }

    /*
     * Public methods
     */

    /*
     * Overriden methods
     */

    @Override
    public void addAllListeners() {

    }

    @Override
    public void removeAllListeners() {

    }

    @Override
    public void reloadCatalogues() {

    }

    @Override
    public void setRegistry(SDbRegistry registry) throws Exception {
        moRegistry = (SDbYear) registry;

        mnFormResult = SLibConsts.UNDEFINED;
        mbFirstActivation = true;

        removeAllListeners();
        reloadCatalogues();

        if (moRegistry.isRegistryNew()) {
            moRegistry.initPrimaryKey();
            moRegistry.setPkYearId(miClient.getSession().getCurrentYear());
            moRegistry.setClosed01(true);
            moRegistry.setClosed02(true);
            moRegistry.setClosed03(true);
            moRegistry.setClosed04(true);
            moRegistry.setClosed05(true);
            moRegistry.setClosed06(true);
            moRegistry.setClosed07(true);
            moRegistry.setClosed08(true);
            moRegistry.setClosed09(true);
            moRegistry.setClosed10(true);
            moRegistry.setClosed11(true);
            moRegistry.setClosed12(true);
            jtfRegistryKey.setText("");
        }
        else {
            jtfRegistryKey.setText(SLibUtils.textKey(moRegistry.getPrimaryKey()));
        }

        moIntYear.setValue(moRegistry.getPkYearId());
        moBoolClosed01.setValue(moRegistry.isClosed01());
        moBoolClosed02.setValue(moRegistry.isClosed02());
        moBoolClosed03.setValue(moRegistry.isClosed03());
        moBoolClosed04.setValue(moRegistry.isClosed04());
        moBoolClosed05.setValue(moRegistry.isClosed05());
        moBoolClosed06.setValue(moRegistry.isClosed06());
        moBoolClosed07.setValue(moRegistry.isClosed07());
        moBoolClosed08.setValue(moRegistry.isClosed08());
        moBoolClosed09.setValue(moRegistry.isClosed09());
        moBoolClosed10.setValue(moRegistry.isClosed10());
        moBoolClosed11.setValue(moRegistry.isClosed11());
        moBoolClosed12.setValue(moRegistry.isClosed12());

        setFormEditable(true);

        moIntYear.setEditable(moRegistry.isRegistryNew());

        addAllListeners();
    }

    @Override
    public SDbYear getRegistry() throws Exception {
        SDbYear registry = moRegistry.clone();

        if (registry.isRegistryNew()) {
            moRegistry.setPkYearId(moIntYear.getValue());
        }

        moRegistry.setClosed01(moBoolClosed01.getValue());
        moRegistry.setClosed02(moBoolClosed02.getValue());
        moRegistry.setClosed03(moBoolClosed03.getValue());
        moRegistry.setClosed04(moBoolClosed04.getValue());
        moRegistry.setClosed05(moBoolClosed05.getValue());
        moRegistry.setClosed06(moBoolClosed06.getValue());
        moRegistry.setClosed07(moBoolClosed07.getValue());
        moRegistry.setClosed08(moBoolClosed08.getValue());
        moRegistry.setClosed09(moBoolClosed09.getValue());
        moRegistry.setClosed10(moBoolClosed10.getValue());
        moRegistry.setClosed11(moBoolClosed11.getValue());
        moRegistry.setClosed12(moBoolClosed12.getValue());

        return registry;
    }

    @Override
    public SGuiValidation validateForm() {
        return moFields.validateFields();
    }
}
