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
import sa.lib.gui.SGuiClient;
import sa.lib.gui.SGuiSession;
import scale.mod.SModConsts;

/**
 *
 * @author Sergio Flores
 */
public class SDbWeighing extends SDbRegistryUser {

    protected int mnPkWeighingId;
    protected Date mtDate;
    protected int mnNumber;
    protected double mdQuantityCage;
    protected double mdWeightGrossSystem;
    protected double mdWeightGross;
    protected double mdWeightCage;
    protected double mdWeightPallet;
    protected double mdWeightNet_r;
    protected double mdPriceUnitary;
    protected double mdTotal_r;
    protected String msNotes;
    protected String msNotesOnDelete;
    protected boolean mbPayed;
    /*
    protected boolean mbDisabled;
    protected boolean mbDeleted;
    protected boolean mbSystem;
    */
    protected int mnFkItemId;
    protected int mnFkSupplierId;
    protected int mnFkLocalityId;
    /*
    protected int mnFkUserInsertId;
    protected int mnFkUserUpdateId;
    protected Date mtTsUserInsert;
    protected Date mtTsUserUpdate;
    */

    protected SDbPayment moRegPayment;

    public SDbWeighing() {
        super(SModConsts.O_WEI);
    }

    /*
     * Private methods
     */

    /*
     * Public methods
     */

    public void setPkWeighingId(int n) { mnPkWeighingId = n; }
    public void setDate(Date t) { mtDate = t; }
    public void setNumber(int n) { mnNumber = n; }
    public void setQuantityCage(double d) { mdQuantityCage = d; }
    public void setWeightGrossSystem(double d) { mdWeightGrossSystem = d; }
    public void setWeightGross(double d) { mdWeightGross = d; }
    public void setWeightCage(double d) { mdWeightCage = d; }
    public void setWeightPallet(double d) { mdWeightPallet = d; }
    public void setWeightNet_r(double d) { mdWeightNet_r = d; }
    public void setPriceUnitary(double d) { mdPriceUnitary = d; }
    public void setTotal_r(double d) { mdTotal_r = d; }
    public void setNotes(String s) { msNotes = s; }
    public void setNotesOnDelete(String s) { msNotesOnDelete = s; }
    public void setPayed(boolean b) { mbPayed = b; }
    public void setDisabled(boolean b) { mbDisabled = b; }
    public void setDeleted(boolean b) { mbDeleted = b; }
    public void setSystem(boolean b) { mbSystem = b; }
    public void setFkItemId(int n) { mnFkItemId = n; }
    public void setFkSupplierId(int n) { mnFkSupplierId = n; }
    public void setFkLocalityId(int n) { mnFkLocalityId = n; }
    public void setFkUserInsertId(int n) { mnFkUserInsertId = n; }
    public void setFkUserUpdateId(int n) { mnFkUserUpdateId = n; }
    public void setTsUserInsert(Date t) { mtTsUserInsert = t; }
    public void setTsUserUpdate(Date t) { mtTsUserUpdate = t; }

    public int getPkWeighingId() { return mnPkWeighingId; }
    public Date getDate() { return mtDate; }
    public int getNumber() { return mnNumber; }
    public double getQuantityCage() { return mdQuantityCage; }
    public double getWeightGrossSystem() { return mdWeightGrossSystem; }
    public double getWeightGross() { return mdWeightGross; }
    public double getWeightCage() { return mdWeightCage; }
    public double getWeightPallet() { return mdWeightPallet; }
    public double getWeightNet_r() { return mdWeightNet_r; }
    public double getPriceUnitary() { return mdPriceUnitary; }
    public double getTotal_r() { return mdTotal_r; }
    public String getNotes() { return msNotes; }
    public String getNotesOnDelete() { return msNotesOnDelete; }
    public boolean isPayed() { return mbPayed; }
    public boolean isDisabled() { return mbDisabled; }
    public boolean isDeleted() { return mbDeleted; }
    public boolean isSystem() { return mbSystem; }
    public int getFkItemId() { return mnFkItemId; }
    public int getFkSupplierId() { return mnFkSupplierId; }
    public int getFkLocalityId() { return mnFkLocalityId; }
    public int getFkUserInsertId() { return mnFkUserInsertId; }
    public int getFkUserUpdateId() { return mnFkUserUpdateId; }
    public Date getTsUserInsert() { return mtTsUserInsert; }
    public Date getTsUserUpdate() { return mtTsUserUpdate; }

    public void setRegPayment(SDbPayment o) { moRegPayment = o; }

    public SDbPayment getRegPayment() { return moRegPayment; }

    public void computeTotal() {
        if (mbDisabled) {
            mdWeightNet_r = 0;
            mdTotal_r = 0;
        }
        else {
            mdWeightNet_r = SLibUtils.round(mdWeightGross - mdWeightCage - mdWeightPallet, SLibUtils.getDecimalFormatQuantity().getMaximumFractionDigits());
            mdTotal_r = SLibUtils.round(mdWeightNet_r * mdPriceUnitary, SLibUtils.getDecimalFormatAmount().getMaximumFractionDigits());
        }
    }

    public void printWeighing(final SGuiClient client) {
        SOpeUtils.printWeighing(client, getPrimaryKey());
    }

    /*
     * Overriden methods
     */

    @Override
    public void setPrimaryKey(int[] pk) {
        mnPkWeighingId = pk[0];
    }

    @Override
    public int[] getPrimaryKey() {
        return new int[] { mnPkWeighingId };
    }

    @Override
    public void initRegistry() {
        initBaseRegistry();

        mnPkWeighingId = 0;
        mtDate = null;
        mnNumber = 0;
        mdQuantityCage = 0;
        mdWeightGrossSystem = 0;
        mdWeightGross = 0;
        mdWeightCage = 0;
        mdWeightPallet = 0;
        mdWeightNet_r = 0;
        mdPriceUnitary = 0;
        mdTotal_r = 0;
        msNotes = "";
        msNotesOnDelete = "";
        mbPayed = false;
        mbDisabled = false;
        mbDeleted = false;
        mbSystem = false;
        mnFkItemId = 0;
        mnFkSupplierId = 0;
        mnFkLocalityId = 0;
        mnFkUserInsertId = 0;
        mnFkUserUpdateId = 0;
        mtTsUserInsert = null;
        mtTsUserUpdate = null;

        moRegPayment = null;
    }

    @Override
    public String getSqlTable() {
        return SModConsts.TablesMap.get(SModConsts.O_WEI);
    }

    @Override
    public String getSqlWhere() {
        return "WHERE id_wei = " + mnPkWeighingId + " ";
    }

    @Override
    public String getSqlWhere(int[] pk) {
        return "WHERE id_wei = " + pk[0] + " ";
    }

    @Override
    public void computePrimaryKey(SGuiSession session) throws SQLException, Exception {
        ResultSet resultSet = null;

        mnPkWeighingId = 0;

        msSql = "SELECT COALESCE(MAX(id_wei), 0) + 1 FROM " + getSqlTable();
        resultSet = session.getStatement().executeQuery(msSql);
        if (resultSet.next()) {
            mnPkWeighingId = resultSet.getInt(1);
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
            mnPkWeighingId = resultSet.getInt("id_wei");
            mtDate = resultSet.getDate("dt");
            mnNumber = resultSet.getInt("num");
            mdQuantityCage = resultSet.getDouble("qty_cag");
            mdWeightGrossSystem = resultSet.getDouble("wei_gro_sys");
            mdWeightGross = resultSet.getDouble("wei_gro");
            mdWeightCage = resultSet.getDouble("wei_cag");
            mdWeightPallet = resultSet.getDouble("wei_pal");
            mdWeightNet_r = resultSet.getDouble("wei_net_r");
            mdPriceUnitary = resultSet.getDouble("prc_unt");
            mdTotal_r = resultSet.getDouble("tot_r");
            msNotes = resultSet.getString("nts");
            msNotesOnDelete = resultSet.getString("nts_del");
            mbPayed = resultSet.getBoolean("b_pay");
            mbDisabled = resultSet.getBoolean("b_dis");
            mbDeleted = resultSet.getBoolean("b_del");
            mbSystem = resultSet.getBoolean("b_sys");
            mnFkItemId = resultSet.getInt("fk_itm");
            mnFkSupplierId = resultSet.getInt("fk_sup");
            mnFkLocalityId = resultSet.getInt("fk_loc");
            mnFkUserInsertId = resultSet.getInt("fk_usr_ins");
            mnFkUserUpdateId = resultSet.getInt("fk_usr_upd");
            mtTsUserInsert = resultSet.getTimestamp("ts_usr_ins");
            mtTsUserUpdate = resultSet.getTimestamp("ts_usr_upd");

            // Read aswell payment:

            msSql = "SELECT id_pay FROM " + SModConsts.TablesMap.get(SModConsts.O_PAY) + " " +
                    "WHERE fk_wei = " + mnPkWeighingId + " AND b_wei = 1 ";
            resultSet = session.getStatement().executeQuery(msSql);
            if (resultSet.next()) {
                moRegPayment = new SDbPayment();
                moRegPayment.read(session, new int[] { resultSet.getInt(1) });
            }

            // Finish reading object:

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

            computeTotal();

            msSql = "INSERT INTO " + getSqlTable() + " VALUES (" +
                    mnPkWeighingId + ", " +
                    "'" + SLibUtils.DbmsDateFormatDate.format(mtDate) + "', " +
                    mnNumber + ", " +
                    mdQuantityCage + ", " +
                    mdWeightGrossSystem + ", " +
                    mdWeightGross + ", " +
                    mdWeightCage + ", " +
                    mdWeightPallet + ", " +
                    mdWeightNet_r + ", " +
                    mdPriceUnitary + ", " +
                    mdTotal_r + ", " +
                    "'" + msNotes + "', " +
                    "'" + msNotesOnDelete + "', " +
                    (mbPayed ? 1 : 0) + ", " +
                    (mbDisabled ? 1 : 0) + ", " +
                    (mbDeleted ? 1 : 0) + ", " +
                    (mbSystem ? 1 : 0) + ", " +
                    mnFkItemId + ", " +
                    mnFkSupplierId + ", " +
                    mnFkLocalityId + ", " +
                    mnFkUserInsertId + ", " +
                    mnFkUserUpdateId + ", " +
                    "NOW()" + ", " +
                    "NOW()" + " " +
                    ")";
        }
        else {
            mnFkUserUpdateId = session.getUser().getPkUserId();

            computeTotal();

            msSql = "UPDATE " + getSqlTable() + " SET " +
                    "id_wei = " + mnPkWeighingId + ", " +
                    "dt = '" + SLibUtils.DbmsDateFormatDate.format(mtDate) + "', " +
                    "num = " + mnNumber + ", " +
                    "qty_cag = " + mdQuantityCage + ", " +
                    "wei_gro_sys = " + mdWeightGrossSystem + ", " +
                    "wei_gro = " + mdWeightGross + ", " +
                    "wei_cag = " + mdWeightCage + ", " +
                    "wei_pal = " + mdWeightPallet + ", " +
                    "wei_net_r = " + mdWeightNet_r + ", " +
                    "prc_unt = " + mdPriceUnitary + ", " +
                    "tot_r = " + mdTotal_r + ", " +
                    "nts = '" + msNotes + "', " +
                    "nts_del = '" + msNotesOnDelete + "', " +
                    "b_pay = " + (mbPayed ? 1 : 0) + ", " +
                    "b_dis = " + (mbDisabled ? 1 : 0) + ", " +
                    "b_del = " + (mbDeleted ? 1 : 0) + ", " +
                    "b_sys = " + (mbSystem ? 1 : 0) + ", " +
                    "fk_itm = " + mnFkItemId + ", " +
                    "fk_sup = " + mnFkSupplierId + ", " +
                    "fk_loc = " + mnFkLocalityId + ", " +
                    "fk_usr_ins = " + mnFkUserInsertId + ", " +
                    "fk_usr_upd = " + mnFkUserUpdateId + ", " +
                    "ts_usr_ins = " + "NOW()" + ", " +
                    "ts_usr_upd = " + "NOW()" + " " +
                    getSqlWhere();
        }

        session.getStatement().execute(msSql);

        // Save aswell payment:

        if (moRegPayment != null) {
            moRegPayment.setDate(mtDate);
            moRegPayment.setWeighing(true);
            moRegPayment.setFkWeighingId(mnPkWeighingId);
            moRegPayment.save(session);
        }

        mbRegistryNew = false;
        mnQueryResultId = SDbConsts.SAVE_OK;
    }

    @Override
    public SDbWeighing clone() throws CloneNotSupportedException {
        SDbWeighing registry = new SDbWeighing();

        registry.setPkWeighingId(this.getPkWeighingId());
        registry.setDate(this.getDate());
        registry.setNumber(this.getNumber());
        registry.setQuantityCage(this.getQuantityCage());
        registry.setWeightGrossSystem(this.getWeightGrossSystem());
        registry.setWeightGross(this.getWeightGross());
        registry.setWeightCage(this.getWeightCage());
        registry.setWeightPallet(this.getWeightPallet());
        registry.setWeightNet_r(this.getWeightNet_r());
        registry.setPriceUnitary(this.getPriceUnitary());
        registry.setTotal_r(this.getTotal_r());
        registry.setNotes(this.getNotes());
        registry.setNotesOnDelete(this.getNotesOnDelete());
        registry.setPayed(this.isPayed());
        registry.setDisabled(this.isDisabled());
        registry.setDeleted(this.isDeleted());
        registry.setSystem(this.isSystem());
        registry.setFkItemId(this.getFkItemId());
        registry.setFkSupplierId(this.getFkSupplierId());
        registry.setFkLocalityId(this.getFkLocalityId());
        registry.setFkUserInsertId(this.getFkUserInsertId());
        registry.setFkUserUpdateId(this.getFkUserUpdateId());
        registry.setTsUserInsert(this.getTsUserInsert());
        registry.setTsUserUpdate(this.getTsUserUpdate());

        if (moRegPayment != null) {
            registry.setRegPayment(moRegPayment.clone());
        }

        registry.setRegistryNew(this.isRegistryNew());
        return registry;
    }

    @Override
    public void disable(final SGuiSession session) throws SQLException, Exception {
        initQueryMembers();
        mnQueryResultId = SDbConsts.SAVE_ERROR;

        mbDisabled = !mbDisabled;
        mnFkUserUpdateId = session.getUser().getPkUserId();

        computeTotal();

        msSql = "UPDATE " + getSqlTable() + " SET " +
                "wei_net_r = " + mdWeightNet_r + ", " +
                "tot_r = " + mdTotal_r + ", " +
                "b_dis = " + (mbDisabled ? 1 : 0) + ", " +
                "fk_usr_upd = " + mnFkUserUpdateId + ", " +
                "ts_usr_upd = NOW() " +
                getSqlWhere();

        session.getStatement().execute(msSql);

        if (moRegPayment != null) {
            moRegPayment.delete(session);
        }

        mnQueryResultId = SDbConsts.SAVE_OK;
    }

    @Override
    public void delete(final SGuiSession session) throws SQLException, Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
