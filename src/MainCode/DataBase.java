package MainCode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DataBase {
    Connection c;

    Statement query;
    String user, password, databaseName;
    boolean connected = false;

    public DataBase(String user, String password, String databaseName) {
        this.user = user;
        this.password = password;
        this.databaseName = databaseName;
    }

    public void connect() {
        try {
            c = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + databaseName, user, password);
            query = c.createStatement();
            connected = true;
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public int getNumRowsTable(String tableName) {
        try {
            ResultSet rset = query.executeQuery("SELECT COUNT(*) AS n FROM " + tableName);
            rset.next();
            int numRows = rset.getInt("n");
            return numRows;
        } catch (Exception e) {
            System.out.println(e);
            return 0;
        }
    }

    public Object[][] getInfoTable(String tableName) {
        try {
            int numRows = getNumRowsTable(tableName);
            int numCols = GUI.infoSchema.getColumnCount(tableName);
            Object[][] info = new Object[numRows][numCols];
            ResultSet rs = query.executeQuery("SELECT * FROM " + tableName);
            int nr = 0;
            while (rs.next()) {
                for (int i = 0; i < numCols; i++) {
                    info[nr][i] = rs.getObject(i + 1);
                }
                nr++;
            }
            return info;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public Object[][] getInfoTable(String tableName, String columnChecked, String where) {
        try {
            int numRows = getNumRowsTable(tableName);
            DataBase dataBase = new DataBase(user, password, "information_schema");
            dataBase.connect();
            int numCols = dataBase.getColumnCount(tableName);
            Object[][] info = new Object[numRows][numCols];
            ResultSet rs = query.executeQuery("SELECT * FROM " + tableName + " WHERE " + columnChecked + " = '" + where + "'");
            int nr = 0;
            while (rs.next()) {
                for (int i = 0; i < numCols; i++) {
                    info[nr][i] = rs.getObject(i + 1);
                }
                nr++;
            }
            return info;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public Object[][] getInfoTable(String select, String tableName, int numCols) {
        try {
            List<Object[]> info = new ArrayList<>() {};
            ResultSet rs = query.executeQuery(select);

            while (rs.next()) {
                Object[] row = new Object[numCols];
                for (int i = 0; i < numCols; i++) {
                    row[i] = rs.getObject(i + 1);
                }
                info.add(row);
            }
            Object[][] inf = new Object[info.size()][numCols];
            for(int i = 0; i< inf.length; i++){
                inf[i] = info.get(i);
            }
            return inf;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public Object[] getColumnFrom(int column, String tableName) {
        Object[] infoColumn;
        try {
            Object[][] info = getInfoTable(tableName);
            infoColumn = new Object[info.length];
            for(int i =0; i< info.length;i++){
                infoColumn[i] = info[i][column];
            }
            return infoColumn;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
    public Object[] getColumnFrom(String column, String tableName) {
        Object[] infoColumn;
        try {
            Object[][] info = getInfoTable("SELECT " + column + " FROM " + tableName, tableName, 1);
            infoColumn = new Object[info.length];
            for(int i =0; i< info.length;i++){
                infoColumn[i] = info[i][0];
            }
            return infoColumn;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public Object[] getColumnFrom(String column, String tableName, String columnChecked, String where) {
        List<Object> infoColumn = new ArrayList<>();
        try {
            ResultSet rs = query.executeQuery("SELECT " + column + " FROM " + tableName + " WHERE " + columnChecked + " = " + where);
            int i = 0;
            while (rs.next()) {
                infoColumn.add(rs.getObject(1));
                i++;
            }
            return infoColumn.toArray();
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public double[] getDoubleColumnFrom(String column, String tableName, String columnChecked, String where) {
        List<Double> infoColumn = new ArrayList<>();
        try {
            ResultSet rs = query.executeQuery("SELECT " + column + " FROM " + tableName + " WHERE " + columnChecked + " = " + where);
            int i = 0;
            while (rs.next()) {
                infoColumn.add(rs.getDouble(1));
                i++;
            }
            return infoColumn.stream().mapToDouble(Double::doubleValue).toArray();

        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
    boolean insert(String tableName, String[] values){
        try{
            String vs = "(";
            vs = vs.concat(bigConcat(values));
            vs = vs.concat(")");
            System.out.println(vs);
            String cols = "(";
            cols = cols.concat(bigConcat(GUI.infoSchema.getColumnNames(tableName)));
            cols = cols.concat(")");
            System.out.println(cols);
            query.execute("INSERT INTO " + tableName + " " + cols + " VALUES " + vs);
            System.out.println("Inserted correctly values " + vs);
            return true;
        } catch(Exception e){
            System.out.println("Failure to insert");
            System.out.println(e);
            return false;
        }
    }

    public int getColumnCount(String tableName) {
        try {
            ResultSet rs = query.executeQuery("SELECT COUNT(*) AS cols FROM columns WHERE table_name = '" + tableName + "'");
            rs.next();
            return rs.getInt("cols");
        } catch (Exception e) {
            System.out.println(e);
            return 0;
        }
    }

    public String[] getColumnNames(String tableName) {
        List<String> colNames = new ArrayList<>();
        try {
            ResultSet rs = query.executeQuery("SELECT column_name FROM columns WHERE table_name = '" + tableName + "'");

            while (rs.next()) {
                colNames.add(rs.getString(1));
            }

            String[] str = new String[colNames.size()];
            for (int i = 0; i < colNames.size(); i++) {
                str[i] = colNames.get(i);
            }
            return str;

        } catch (Exception e) {
            System.out.println(e);

            return null;
        }

    }

    public String bigConcat(String[] values){
        String vs = "";
        for(int i = 0;i< values.length;i++){
            if(!values[i].equals("gameId") && !values[i].equals("playerId")) {
                vs = vs.concat(values[i]);
                if (i < values.length - 1) {
                    vs = vs.concat(", ");
                }
            }
        }
        return vs;
    }
}