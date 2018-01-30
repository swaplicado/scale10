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
import sa.lib.gui.SGuiSession;
import scale.mod.SModConsts;

/**
 *
 * @author Sergio Flores
 */
public class SDbYear extends SDbRegistryUser {

    protected int mnPkYearId;
    protected boolean mbClosed01;
    protected boolean mbClosed02;
    protected boolean mbClosed03;
    protected boolean mbClosed04;
    protected boolean mbClosed05;
    protected boolean mbClosed06;
    protected boolean mbClosed07;
    protected boolean mbClosed08;
    protected boolean mbClosed09;
    protected boolean mbClosed10;
    protected boolean mbClosed11;
    protected boolean mbClosed12;
    /*
    protected boolean mbDisabled;
    protected boolean mbDeleted;
    protected boolean mbSystem;
    protected int mnFkUserInsertId;
    protected int mnFkUserUpdateId;
    protected Date mtTsUserInsert;
    protected Date mtTsUserUpdate;
    */

    public SDbYear() {
        super(SModConsts.O_YEA);
    }

    /*
     * Public methods
     */

    public void setPkYearId(int n) { mnPkYearId = n; }
    public void setClosed01(boolean b) { mbClosed01 = b; }
    public void setClosed02(boolean b) { mbClosed02 = b; }
    public void setClosed03(boolean b) { mbClosed03 = b; }
    public void setClosed04(boolean b) { mbClosed04 = b; }
    public void setClosed05(boolean b) { mbClosed05 = b; }
    public void setClosed06(boolean b) { mbClosed06 = b; }
    public void setClosed07(boolean b) { mbClosed07 = b; }
    public void setClosed08(boolean b) { mbClosed08 = b; }
    public void setClosed09(boolean b) { mbClosed09 = b; }
    public void setClosed10(boolean b) { mbClosed10 = b; }
    public void setClosed11(boolean b) { mbClosed11 = b; }
    public void setClosed12(boolean b) { mbClosed12 = b; }
    public void setDisabled(boolean b) { mbDisabled = b; }
    public void setDeleted(boolean b) { mbDeleted = b; }
    public void setSystem(boolean b) { mbSystem = b; }
    public void setFkUserInsertId(int n) { mnFkUserInsertId = n; }
    public void setFkUserUpdateId(int n) { mnFkUserUpdateId = n; }
    public void setTsUserInsert(Date t) { mtTsUserInsert = t; }
    public void setTsUserUpdate(Date t) { mtTsUserUpdate = t; }

    public int getPkYearId() { return mnPkYearId; }
    public boolean isClosed01() { return mbClosed01; }
    public boolean isClosed02() { return mbClosed02; }
    public boolean isClosed03() { return mbClosed03; }
    public boolean isClosed04() { return mbClosed04; }
    public boolean isClosed05() { return mbClosed05; }
    public boolean isClosed06() { return mbClosed06; }
    public boolean isClosed07() { return mbClosed07; }
    public boolean isClosed08() { return mbClosed08; }
    public boolean isClosed09() { return mbClosed09; }
    public boolean isClosed10() { return mbClosed10; }
    public boolean isClosed11() { return mbClosed11; }
    public boolean isClosed12() { return mbClosed12; }
    public boolean isDisabled() { return mbDisabled; }
    public boolean isDeleted() { return mbDeleted; }
    public boolean isSystem() { return mbSystem; }
    public int getFkUserInsertId() { return mnFkUserInsertId; }
    public int getFkUserUpdateId() { return mnFkUserUpdateId; }
    public Date getTsUserInsert() { return mtTsUserInsert; }
    public Date getTsUserUpdate() { return mtTsUserUpdate; }

    public boolean isMonthClosed(final int month) {
        boolean closed = true;

        switch (month) {
            case 1:
                closed = mbClosed01;
                break;
            case 2:
                closed = mbClosed02;
                break;
            case 3:
                closed = mbClosed03;
                break;
            case 4:
                closed = mbClosed04;
                break;
            case 5:
                closed = mbClosed05;
                break;
            case 6:
                closed = mbClosed06;
                break;
            case 7:
                closed = mbClosed07;
                break;
            case 8:
                closed = mbClosed08;
                break;
            case 9:
                closed = mbClosed09;
                break;
            case 10:
                closed = mbClosed10;
                break;
            case 11:
                closed = mbClosed11;
                break;
            case 12:
                closed = mbClosed12;
                break;
            default:
        }

        return closed;
    }

    /*
     * Overriden methods
     */

    @Override
    public void setPrimaryKey(int[] pk) {
        mnPkYearId = pk[0];
    }

    @Override
    public int[] getPrimaryKey() {
        return new int[] { mnPkYearId };
    }

    @Override
    public void initRegistry() {
        initBaseRegistry();

        mnPkYearId = 0;
        mbClosed01 = false;
        mbClosed02 = false;
        mbClosed03 = false;
        mbClosed04 = false;
        mbClosed05 = false;
        mbClosed06 = false;
        mbClosed07 = false;
        mbClosed08 = false;
        mbClosed09 = false;
        mbClosed10 = false;
        mbClosed11 = false;
        mbClosed12 = false;
        mbDisabled = false;
        mbDeleted = false;
        mbSystem = false;
        mnFkUserInsertId = 0;
        mnFkUserUpdateId = 0;
        mtTsUserInsert = null;
        mtTsUserUpdate = null;
    }

    @Override
    public String getSqlTable() {
        return SModConsts.TablesMap.get(SModConsts.O_YEA);
    }

    @Override
    public String getSqlWhere() {
        return "WHERE id_yea = " + mnPkYearId + " ";
    }

    @Override
    public String getSqlWhere(int[] pk) {
        return "WHERE id_yea = " + pk[0] + " ";
    }

    @Override
    public void computePrimaryKey(SGuiSession session) throws SQLException, Exception {
        throw new UnsupportedOperationException("Not supported yet.");
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
            mnPkYearId = resultSet.getInt("id_yea");
            mbClosed01 = resultSet.getBoolean("b_clo_01");
            mbClosed02 = resultSet.getBoolean("b_clo_02");
            mbClosed03 = resultSet.getBoolean("b_clo_03");
            mbClosed04 = resultSet.getBoolean("b_clo_04");
            mbClosed05 = resultSet.getBoolean("b_clo_05");
            mbClosed06 = resultSet.getBoolean("b_clo_06");
            mbClosed07 = resultSet.getBoolean("b_clo_07");
            mbClosed08 = resultSet.getBoolean("b_clo_08");
            mbClosed09 = resultSet.getBoolean("b_clo_09");
            mbClosed10 = resultSet.getBoolean("b_clo_10");
            mbClosed11 = resultSet.getBoolean("b_clo_11");
            mbClosed12 = resultSet.getBoolean("b_clo_12");
            mbDisabled = resultSet.getBoolean("b_dis");
            mbDeleted = resultSet.getBoolean("b_del");
            mbSystem = resultSet.getBoolean("b_sys");
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

        verifyRegistryNew(session);

        if (mbRegistryNew) {
            mbDisabled = false;
            mbDeleted = false;
            mbSystem = false;
            mnFkUserInsertId = session.getUser().getPkUserId();
            mnFkUserUpdateId = SUtilConsts.USR_NA_ID;

            msSql = "INSERT INTO " + getSqlTable() + " VALUES (" +
                    mnPkYearId + ", " +
                    (mbClosed01 ? 1 : 0) + ", " +
                    (mbClosed02 ? 1 : 0) + ", " +
                    (mbClosed03 ? 1 : 0) + ", " +
                    (mbClosed04 ? 1 : 0) + ", " +
                    (mbClosed05 ? 1 : 0) + ", " +
                    (mbClosed06 ? 1 : 0) + ", " +
                    (mbClosed07 ? 1 : 0) + ", " +
                    (mbClosed08 ? 1 : 0) + ", " +
                    (mbClosed09 ? 1 : 0) + ", " +
                    (mbClosed10 ? 1 : 0) + ", " +
                    (mbClosed11 ? 1 : 0) + ", " +
                    (mbClosed12 ? 1 : 0) + ", " +
                    (mbDisabled ? 1 : 0) + ", " +
                    (mbDeleted ? 1 : 0) + ", " +
                    (mbSystem ? 1 : 0) + ", " +
                    mnFkUserInsertId + ", " +
                    mnFkUserUpdateId + ", " +
                    "NOW()" + ", " +
                    "NOW()" + " " +
                    ")";
        }
        else {
            mnFkUserUpdateId = session.getUser().getPkUserId();

            msSql = "UPDATE " + getSqlTable() + " SET " +
                    //"id_yea = " + mnPkYearId + ", " +
                    "b_clo_01 = " + (mbClosed01 ? 1 : 0) + ", " +
                    "b_clo_02 = " + (mbClosed02 ? 1 : 0) + ", " +
                    "b_clo_03 = " + (mbClosed03 ? 1 : 0) + ", " +
                    "b_clo_04 = " + (mbClosed04 ? 1 : 0) + ", " +
                    "b_clo_05 = " + (mbClosed05 ? 1 : 0) + ", " +
                    "b_clo_06 = " + (mbClosed06 ? 1 : 0) + ", " +
                    "b_clo_07 = " + (mbClosed07 ? 1 : 0) + ", " +
                    "b_clo_08 = " + (mbClosed08 ? 1 : 0) + ", " +
                    "b_clo_09 = " + (mbClosed09 ? 1 : 0) + ", " +
                    "b_clo_10 = " + (mbClosed10 ? 1 : 0) + ", " +
                    "b_clo_11 = " + (mbClosed11 ? 1 : 0) + ", " +
                    "b_clo_12 = " + (mbClosed12 ? 1 : 0) + ", " +
                    "b_dis = " + (mbDisabled ? 1 : 0) + ", " +
                    "b_del = " + (mbDeleted ? 1 : 0) + ", " +
                    "b_sys = " + (mbSystem ? 1 : 0) + ", " +
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
    public SDbYear clone() throws CloneNotSupportedException {
        SDbYear registry = new SDbYear();

        registry.setPkYearId(this.getPkYearId());
        registry.setClosed01(this.isClosed01());
        registry.setClosed02(this.isClosed02());
        registry.setClosed03(this.isClosed03());
        registry.setClosed04(this.isClosed04());
        registry.setClosed05(this.isClosed05());
        registry.setClosed06(this.isClosed06());
        registry.setClosed07(this.isClosed07());
        registry.setClosed08(this.isClosed08());
        registry.setClosed09(this.isClosed09());
        registry.setClosed10(this.isClosed10());
        registry.setClosed11(this.isClosed11());
        registry.setClosed12(this.isClosed12());
        registry.setDisabled(this.isDisabled());
        registry.setDeleted(this.isDeleted());
        registry.setSystem(this.isSystem());
        registry.setFkUserInsertId(this.getFkUserInsertId());
        registry.setFkUserUpdateId(this.getFkUserUpdateId());
        registry.setTsUserInsert(this.getTsUserInsert());
        registry.setTsUserUpdate(this.getTsUserUpdate());

        registry.setRegistryNew(this.isRegistryNew());
        return registry;
    }
}
