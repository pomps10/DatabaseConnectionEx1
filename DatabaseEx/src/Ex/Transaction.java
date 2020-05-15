package Ex;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Transaction {

    public static void main(String[] args) {

        Connection con = null;
        Statement st = null;
        ResultSet rs = null;


        String url = "jdbc:mariadb://18.216.149.81:54321/testdb?autoReconnect=true&useSSL=false";
		String user = "alunos";
		String password = "senhadealunos";
        
        try {


            con = DriverManager.getConnection(url, user, password);
            st = con.createStatement();

            con.setAutoCommit(false);

            st.executeUpdate("INSERT INTO Authors(Id, Name) VALUES(20, 'Felipe Pomps');");
            st.executeUpdate("INSERT INTO Books(Id, AuthorId, Title) VALUES(21, 20, '1984');");
            st.executeUpdate("INSERT INTO Books(Id, AuthorId, Title) VALUES(22, 20	, 'DescriçãoAnimal Farm');");
			

			rs = st.executeQuery("SELECT Name, Title From Authors, Books WHERE Authors.Id=Books.AuthorId;");

			while (rs.next()) {
				System.out.println(rs.getString(1));
			}

            con.commit();
            
            

        } catch (SQLException ex) {

            if (con != null) {
                
                try {
                    
                    con.rollback();
                } catch (SQLException ex1) {
                    
                    Logger lgr = Logger.getLogger(Transaction.class.getName());
                    lgr.log(Level.WARNING, ex1.getMessage(), ex1);
                }
            }

            Logger lgr = Logger.getLogger(Transaction.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
            
        } finally {

            try {
                if (st != null) {
                    st.close();
                }
                if (con != null) {
                    con.close();
                }

            } catch (SQLException ex) {

                Logger lgr = Logger.getLogger(Transaction.class.getName());
                lgr.log(Level.WARNING, ex.getMessage(), ex);
            }
        }
    }
}
