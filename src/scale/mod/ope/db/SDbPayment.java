/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scale.mod.ope.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import sa.gui.util.SUtilConsts;
import sa.lib.SLibUtils;
import sa.lib.db.SDbConsts;
import sa.lib.db.SDbRegistryUser;
import sa.lib.gui.SGuiSession;
import scale.mod.SModConsts;

/**
 *
 * @author Sergio Flores
 */
public class SDbPayment extends SDbRegistryUser {

    protected int mnPkPaymentId;
    protected Date mtDate;
    protected int mnNumber;
    protected double mdPayment;
    protected boolean mbWeighing;
    /*
    protected boolean mbDisabled;
    protected boolean mbDeleted;
    protected boolean mbSystem;
    */
    protected int mnFkWeighingId;
    /*
    protected int mnFkUserInsertId;
    protected int mnFkUserUpdateId;
    protected Date mtTsUserInsert;
    protected Date mtTsUserUpdate;
    */

    public SDbPayment() {
        super(SModConsts.O_PAY);
    }

    /*
     * Public methods
     */

    public void setPkPaymentId(int n) { mnPkPaymentId = n; }
    public void setDate(Date t) { mtDate = t; }
    public void setNumber(int n) { mnNumber = n; }
    public void setPayment(double d) { mdPayment = d; }
    public void setWeighing(boolean b) { mbWeighing = b; }
    public void setDisabled(boolean b) { mbDisabled = b; }
    public void setDeleted(boolean b) { mbDeleted = b; }
    public void setSystem(boolean b) { mbSystem = b; }
    public void setFkWeighingId(int n) { mnFkWeighingId = n; }
    public void setFkUserInsertId(int n) { mnFkUserInsertId = n; }
    public void setFkUserUpdateId(int n) { mnFkUserUpdateId = n; }
    public void setTsUserInsert(Date t) { mtTsUserInsert = t; }
    public void setTsUserUpdate(Date t) { mtTsUserUpdate = t; }

    public int getPkPaymentId() { return mnPkPaymentId; }
    public Date getDate() { return mtDate; }
    public int getNumber() { return mnNumber; }
    public double getPayment() { return mdPayment; }
    public boolean isWeighing() { return mbWeighing; }
    public boolean isDisabled() { return mbDisabled; }
    public boolean isDeleted() { return mbDeleted; }
    public boolean isSystem() { return mbSystem; }
    public int getFkWeighingId() { return mnFkWeighingId; }
    public int getFkUserInsertId() { return mnFkUserInsertId; }
    public int getFkUserUpdateId() { return mnFkUserUpdateId; }
    public Date getTsUserInsert() { return mtTsUserInsert; }
    public Date getTsUserUpdate() { return mtTsUserUpdate; }

    /*
     * Overriden methods
     */

    @Override
    public void setPrimaryKey(int[] pk) {
        mnPkPaymentId = pk[0];
    }

    @Override
    public int[] getPrimaryKey() {
        return new int[] { mnPkPaymentId };
    }

    @Override
    public void initRegistry() {
        initBaseRegistry();

        mnPkPaymentId = 0;
        mtDate = null;
        mnNumber = 0;
        mdPayment = 0;
        mbWeighing = false;
        mbDisabled = false;
        mbDeleted = false;
        mbSystem = false;
        mnFkWeighingId = 0;
        mnFkUserInsertId = 0;
        mnFkUserUpdateId = 0;
        mtTsUserInsert = null;
        mtTsUserUpdate = null;
    }

    @Override
    public String getSqlTable() {
        return SModConsts.TablesMap.get(SModConsts.O_PAY);
    }

    @Override
    public String getSqlWhere() {
        return "WHERE id_pay = " + mnPkPaymentId + " ";
    }

    @Override
    public String getSqlWhere(int[] pk) {
        return "WHERE id_pay = " + pk[0] + " ";
    }

    @Override
    public void computePrimaryKey(SGuiSession session) throws SQLException, Exception {
        ResultSet resultSet = null;

        mnPkPaymentId = 0;

        msSql = "SELECT COALESCE(MAX(id_pay), 0) + 1 FROM " + getSqlTable();
        resultSet = session.getStatement().executeQuery(msSql);
        if (resultSet.next()) {
            mnPkPaymentId = resultSet.getInt(1);
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
            mnPkPaymentId = resultSet.getInt("id_pay");
            mtDate = resultSet.getDate("dt");
            mnNumber = resultSet.getInt("num");
            mdPayment = resultSet.getDouble("pay");
            mbWeighing = resultSet.getBoolean("b_wei");
            mbDisabled = resultSet.getBoolean("b_dis");
            mbDeleted = resultSet.getBoolean("b_del");
            mbSystem = resultSet.getBoolean("b_sys");
            mnFkWeighingId = resultSet.getInt("fk_wei");
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
        mnQueryResultId = SDbConsts.READ_ERROR;

        if (mbRegistryNew) {
            computePrimaryKey(session);

            if (mnNumber == 0) {
                mnNumber = SOpeUtils.getNextNumber(session, mnRegistryType);
            }

            mbDisabled = false;
            mbDeleted = false;
            mbSystem = false;
            mnFkUserInsertId = session.getUser().getPkUserId();
            mnFkUserUpdateId = SUtilConsts.USR_NA_ID;

            msSql = "INSERT INTO " + getSqlTable() + " VALUES (" +
                    mnPkPaymentId + ", " +
                    "'" + SLibUtils.DbmsDateFormatDate.format(mtDate) + "', " +
                    mnNumber + ", " +
                    mdPayment + ", " +
                    (mbWeighing ? 1 : 0) + ", " +
                    (mbDisabled ? 1 : 0) + ", " +
                    (mbDeleted ? 1 : 0) + ", " +
                    (mbSystem ? 1 : 0) + ", " +
                    mnFkWeighingId + ", " +
                    mnFkUserInsertId + ", " +
                    mnFkUserUpdateId + ", " +
                    "NOW()" + ", " +
                    "NOW()" + " " +
                    ")";
        }
        else {
            mnFkUserUpdateId = session.getUser().getPkUserId();

            msSql = "UPDATE " + getSqlTable() + " SET " +
                    "id_pay = " + mnPkPaymentId + ", " +
                    "dt = '" + SLibUtils.DbmsDateFormatDate.format(mtDate) + "', " +
                    "num = " + mnNumber + ", " +
                    "pay = " + mdPayment + ", " +
                    "b_wei = " + (mbWeighing ? 1 : 0) + ", " +
                    "b_dis = " + (mbDisabled ? 1 : 0) + ", " +
                    "b_del = " + (mbDeleted ? 1 : 0) + ", " +
                    "b_sys = " + (mbSystem ? 1 : 0) + ", " +
                    "fk_wei = " + mnFkWeighingId + ", " +
                    "fk_usr_ins = " + mnFkUserInsertId + ", " +
                    "fk_usr_upd = " + mnFkUserUpdateId + ", " +
                    "ts_usr_ins = " + "NOW()" + ", " +
                    "ts_usr_upd = " + "NOW()" + " " +
                    getSqlWhere();
        }

        session.getStatement().execute(msSql);
        mbRegistryNew = false;
        mnQueryResultId = SDbConsts.SAVE_OK;
    }

    @Override
    public SDbPayment clone() throws CloneNotSupportedException {
        SDbPayment registry = new SDbPayment();

        registry.setPkPaymentId(this.getPkPaymentId());
        registry.setDate(this.getDate());
        registry.setNumber(this.getNumber());
        registry.setPayment(this.getPayment());
        registry.setWeighing(this.isWeighing());
        registry.setDisabled(this.isDisabled());
        registry.setDeleted(this.isDeleted());
        registry.setSystem(this.isSystem());
        registry.setFkWeighingId(this.getFkWeighingId());
        registry.setFkUserInsertId(this.getFkUserInsertId());
        registry.setFkUserUpdateId(this.getFkUserUpdateId());
        registry.setTsUserInsert(this.getTsUserInsert());
        registry.setTsUserUpdate(this.getTsUserUpdate());

        registry.setRegistryNew(this.isRegistryNew());
        return registry;
    }
}
