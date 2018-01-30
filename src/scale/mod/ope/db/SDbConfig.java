/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package scale.mod.ope.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import sa.gui.util.SUtilConsts;
import sa.lib.db.SDbConsts;
import sa.lib.db.SDbRegistryUser;
import sa.lib.gui.SGuiConfigSystem;
import sa.lib.gui.SGuiSession;
import scale.mod.SModConsts;

/**
 *
 * @author Sergio Flores
 */
public class SDbConfig extends SDbRegistryUser implements SGuiConfigSystem {

    protected int mnPkConfigId;
    protected String msCode;
    protected String msName;
    protected double mdWeightCageUnitary;
    protected boolean mbDevelopment;
    /*
    protected boolean mbUpdatable;
    protected boolean mbDisableable;
    protected boolean mbDeletable;
    protected boolean mbDisabled;
    protected boolean mbDeleted;
    protected boolean mbSystem;
    */
    protected int mnFkDefaultItemId_n;
    protected int mnFkDefaultSupplierId_n;
    protected int mnFkDefaultLocalityId_n;
    /*
    protected int mnFkUserInsertId;
    protected int mnFkUserUpdateId;
    protected Date mtTsUserInsert;
    protected Date mtTsUserUpdate;
    */

    public SDbConfig() {
        super(SModConsts.OU_CFG);
    }

    public void setPkConfigId(int n) { mnPkConfigId = n; }
    public void setCode(String s) { msCode = s; }
    public void setName(String s) { msName = s; }
    public void setWeightCageUnitary(double d) { mdWeightCageUnitary = d; }
    public void setDevelopment(boolean b) { mbDevelopment = b; }
    public void setUpdatable(boolean b) { mbUpdatable = b; }
    public void setDisableable(boolean b) { mbDisableable = b; }
    public void setDeletable(boolean b) { mbDeletable = b; }
    public void setDisabled(boolean b) { mbDisabled = b; }
    public void setDeleted(boolean b) { mbDeleted = b; }
    public void setSystem(boolean b) { mbSystem = b; }
    public void setFkDefaultItemId_n(int n) { mnFkDefaultItemId_n = n; }
    public void setFkDefaultSupplierId_n(int n) { mnFkDefaultSupplierId_n = n; }
    public void setFkDefaultLocalityId_n(int n) { mnFkDefaultLocalityId_n = n; }
    public void setFkUserInsertId(int n) { mnFkUserInsertId = n; }
    public void setFkUserUpdateId(int n) { mnFkUserUpdateId = n; }
    public void setTsUserInsert(Date t) { mtTsUserInsert = t; }
    public void setTsUserUpdate(Date t) { mtTsUserUpdate = t; }

    public int getPkConfigId() { return mnPkConfigId; }
    public String getCode() { return msCode; }
    public String getName() { return msName; }
    public double getWeightCageUnitary() { return mdWeightCageUnitary; }
    public boolean isDevelopment() { return mbDevelopment; }
    public boolean isUpdatable() { return mbUpdatable; }
    public boolean isDisableable() { return mbDisableable; }
    public boolean isDeletable() { return mbDeletable; }
    public boolean isDisabled() { return mbDisabled; }
    public boolean isDeleted() { return mbDeleted; }
    public boolean isSystem() { return mbSystem; }
    public int getFkDefaultItemId_n() { return mnFkDefaultItemId_n; }
    public int getFkDefaultSupplierId_n() { return mnFkDefaultSupplierId_n; }
    public int getFkDefaultLocalityId_n() { return mnFkDefaultLocalityId_n; }
    public int getFkUserInsertId() { return mnFkUserInsertId; }
    public int getFkUserUpdateId() { return mnFkUserUpdateId; }
    public Date getTsUserInsert() { return mtTsUserInsert; }
    public Date getTsUserUpdate() { return mtTsUserUpdate; }

    public String getCurrencyCode() { return "MXN"; }

    @Override
    public void setPrimaryKey(int[] pk) {
        mnPkConfigId = pk[0];
    }

    @Override
    public int[] getPrimaryKey() {
        return new int[] { mnPkConfigId };
    }

    @Override
    public void initRegistry() {
        initBaseRegistry();

        mnPkConfigId = 0;
        msCode = "";
        msName = "";
        mdWeightCageUnitary = 0;
        mbDevelopment = false;
        mbUpdatable = false;
        mbDisableable = false;
        mbDeletable = false;
        mbDisabled = false;
        mbDeleted = false;
        mbSystem = false;
        mnFkDefaultItemId_n = 0;
        mnFkDefaultSupplierId_n = 0;
        mnFkDefaultLocalityId_n = 0;
        mnFkUserInsertId = 0;
        mnFkUserUpdateId = 0;
        mtTsUserInsert = null;
        mtTsUserUpdate = null;
    }

    @Override
    public String getSqlTable() {
        return SModConsts.TablesMap.get(mnRegistryType);
    }

    @Override
    public String getSqlWhere() {
        return "WHERE id_cfg = " + mnPkConfigId + " ";
    }

    @Override
    public String getSqlWhere(int[] pk) {
        return "WHERE id_cfg = " + pk[0] + " ";
    }

    @Override
    public void computePrimaryKey(SGuiSession session) throws SQLException, Exception {
        ResultSet resultSet = null;

        mnPkConfigId = 0;

        msSql = "SELECT COALESCE(MAX(id_cfg), 0) + 1 FROM " + getSqlTable();
        resultSet = session.getStatement().executeQuery(msSql);
        if (resultSet.next()) {
            mnPkConfigId = resultSet.getInt(1);
        }
    }

    @Override
    public void read(SGuiSession session, int[] pk) throws SQLException, Exception {
        ResultSet resultSet = null;

        initRegistry();
        initQueryMembers();
        mnQueryResultId = SDbConsts.READ_ERROR;

        msSql = "SELECT * " + getSqlFromWhere(pk);
        resultSet = session.getStatement().executeQuery(msSql);
        if (!resultSet.next()) {
            throw new Exception(SDbConsts.ERR_MSG_REG_NOT_FOUND);
        }
        else {
            mnPkConfigId = resultSet.getInt("id_cfg");
            msCode = resultSet.getString("code");
            msName = resultSet.getString("name");
            mdWeightCageUnitary = resultSet.getDouble("wei_cag_unt");
            mbDevelopment = resultSet.getBoolean("b_dev");
            mbUpdatable = resultSet.getBoolean("b_can_upd");
            mbDisableable = resultSet.getBoolean("b_can_dis");
            mbDeletable = resultSet.getBoolean("b_can_del");
            mbDisabled = resultSet.getBoolean("b_dis");
            mbDeleted = resultSet.getBoolean("b_del");
            mbSystem = resultSet.getBoolean("b_sys");
            mnFkDefaultItemId_n = resultSet.getInt("fk_def_itm_n");
            mnFkDefaultSupplierId_n = resultSet.getInt("fk_def_sup_n");
            mnFkDefaultLocalityId_n = resultSet.getInt("fk_def_loc_n");
            mnFkUserInsertId = resultSet.getInt("fk_usr_ins");
            mnFkUserUpdateId = resultSet.getInt("fk_usr_upd");
            mtTsUserInsert = resultSet.getTimestamp("ts_usr_ins");
            mtTsUserUpdate = resultSet.getTimestamp("ts_usr_upd");

            mbRegistryNew = false;
        }

        mnQueryResultId = SDbConsts.READ_OK;
    }

    @Override
    public void save(SGuiSession session) throws SQLException, Exception {
        initQueryMembers();
        mnQueryResultId = SDbConsts.SAVE_ERROR;

        if (mbRegistryNew) {
            computePrimaryKey(session);
            mbUpdatable = true;
            mbDisableable = true;
            mbDeletable = true;
            mbDisabled = false;
            mbDeleted = false;
            mbSystem = false;
            mnFkUserInsertId = session.getUser().getPkUserId();
            mnFkUserUpdateId = SUtilConsts.USR_NA_ID;

            msSql = "INSERT INTO " + getSqlTable() + " VALUES (" +
                    mnPkConfigId + ", " +
                    "'" + msCode + "', " +
                    "'" + msName + "', " +
                    mdWeightCageUnitary + ", " +
                    (mbDevelopment ? 1 : 0) + ", " +
                    (mbUpdatable ? 1 : 0) + ", " +
                    (mbDisableable ? 1 : 0) + ", " +
                    (mbDeletable ? 1 : 0) + ", " +
                    (mbDisabled ? 1 : 0) + ", " +
                    (mbDeleted ? 1 : 0) + ", " +
                    (mbSystem ? 1 : 0) + ", " +
                    mnFkDefaultItemId_n + ", " +
                    mnFkDefaultSupplierId_n + ", " +
                    mnFkDefaultLocalityId_n + ", " +
                    mnFkUserInsertId + ", " +
                    mnFkUserUpdateId + ", " +
                    "NOW()" + ", " +
                    "NOW()" + " " +
                    ")";
        }
        else {
            mnFkUserUpdateId = session.getUser().getPkUserId();

            msSql = "UPDATE " + getSqlTable() + " SET " +
                    //"id_cfg = " + mnPkConfigId + ", " +
                    "code = '" + msCode + "', " +
                    "name = '" + msName + "', " +
                    "wei_cag_unt = " + mdWeightCageUnitary + ", " +
                    "b_dev = " + (mbDevelopment ? 1 : 0) + ", " +
                    "b_can_upd = " + (mbUpdatable ? 1 : 0) + ", " +
                    "b_can_dis = " + (mbDisableable ? 1 : 0) + ", " +
                    "b_can_del = " + (mbDeletable ? 1 : 0) + ", " +
                    "b_dis = " + (mbDisabled ? 1 : 0) + ", " +
                    "b_del = " + (mbDeleted ? 1 : 0) + ", " +
                    "b_sys = " + (mbSystem ? 1 : 0) + ", " +
                    "fk_def_itm_n = " + mnFkDefaultItemId_n + ", " +
                    "fk_def_sup_n = " + mnFkDefaultSupplierId_n + ", " +
                    "fk_def_loc_n = " + mnFkDefaultLocalityId_n + ", " +
                    //"fk_usr_ins = " + mnFkUserInsertId + ", " +
                    "fk_usr_upd = " + mnFkUserUpdateId + ", " +
                    //"ts_usr_ins = " + "NOW()" + ", " +
                    "ts_usr_upd = " + "NOW()" + " " +
                    getSqlWhere();
        }

        session.getStatement().execute(msSql);
        mbRegistryNew = false;
        mnQueryResultId = SDbConsts.SAVE_OK;
    }

    @Override
    public SDbConfig clone() throws CloneNotSupportedException {
        SDbConfig registry = new SDbConfig();

        registry.setPkConfigId(this.getPkConfigId());
        registry.setCode(this.getCode());
        registry.setName(this.getName());
        registry.setWeightCageUnitary(this.getWeightCageUnitary());
        registry.setDevelopment(this.isDevelopment());
        registry.setUpdatable(this.isUpdatable());
        registry.setDisableable(this.isDisableable());
        registry.setDeletable(this.isDeletable());
        registry.setDisabled(this.isDisabled());
        registry.setDeleted(this.isDeleted());
        registry.setSystem(this.isSystem());
        registry.setFkDefaultItemId_n(this.getFkDefaultItemId_n());
        registry.setFkDefaultSupplierId_n(this.getFkDefaultSupplierId_n());
        registry.setFkDefaultLocalityId_n(this.getFkDefaultLocalityId_n());
        registry.setFkUserInsertId(this.getFkUserInsertId());
        registry.setFkUserUpdateId(this.getFkUserUpdateId());
        registry.setTsUserInsert(this.getTsUserInsert());
        registry.setTsUserUpdate(this.getTsUserUpdate());

        registry.setRegistryNew(this.isRegistryNew());
        return registry;
    }

    @Override
    public int getSystemId() {
        return getPkConfigId();
    }
}
