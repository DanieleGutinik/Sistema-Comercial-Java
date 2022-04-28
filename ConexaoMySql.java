package conexoes;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author Br
 */
public class ConexaoMySql {

    private boolean status = false;
    private String mensagem = "";   //variavel que vai informar o status da conexao
    private Connection con = null;  //variavel para conexao
    private Statement statement;
    private ResultSet resultSet;


    private String servidor = "localhost";
    private String nomeDoBanco = "dbvendasbl";
    private String usuario = "root";
    private String senha = "";
    
    public ConexaoMySql(){}
    
    public ConexaoMySql(String pServidor, String pNomeDoBanco, String pUsuario, String pSenha){
        this.servidor = pServidor;
        this.nomeDoBanco = pNomeDoBanco;
        this.usuario = pUsuario;
        this.senha = pSenha;
    }

    /**
     * Abre uma conexao com o banco
     * @return Connection
     */
    public Connection conectar(){
        try {
            //Driver do PostgreSQL
            Class.forName("com.mysql.jdbc.Driver").newInstance();

            //local do banco, nome do banco, usuario e senha
            String url = "jdbc:mysql://" + servidor + "/" + nomeDoBanco;
            this.setCon((Connection) DriverManager.getConnection(url, usuario, senha));

            //se ocorrer tudo bem, ou seja, se conectar a linha a segui é executada
            this.status = true;
        }
        catch(ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return this.getCon();
    }

    /**
     * Executa consultas SQL
     * @param pSQL
     * @return int
     */
    public boolean executarSQL(String pSQL){
        try {
            //createStatement de con para criar o Statement
            this.setStatement(getCon().createStatement());

            // Definido o Statement, executamos a query no banco de dados
            this.setResultSet(getStatement().executeQuery(pSQL));
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }
    
    public boolean executarUpdateDeleteSQL(String pSQL){
        try {
            
            //createStatement de con para criar o Statement
            this.setStatement(getCon().createStatement());

            // Definido o Statement, executamos a query no banco de dados
            getStatement().executeUpdate(pSQL);
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }
    
    /**
     * Executa insert SQL
     * @param pSQL
     * @return boolean
     */
    public int insertSQL(String pSQL){
        int status = 0;
        try {
            //createStatement de con para criar o Statement
            this.setStatement(getCon().createStatement());

            // Definido o Statement, executamos a query no banco de dados
            this.getStatement().executeUpdate(pSQL);
            
            //consulta o ultimo id inserido
            this.setResultSet(this.getStatement().executeQuery("SELECT last_insert_id();"));
            
            //recupera o ultimo id inserido
            while(this.resultSet.next()){
                status = this.resultSet.getInt(1);
            }
            
            //retorna o ultimo id inserido
            return status;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return status;
        }
    }

    /**
     * encerra a conexão corrente
     * @return boolean
     */
    public boolean fecharConexao(){
       try {
           if((this.getResultSet() != null) && (this.statement != null)){
               this.getResultSet().close();
               this.statement.close();
           }
           this.getCon().close();
           return true;
       } catch(SQLException e) {
           JOptionPane.showMessageDialog(null, e.getMessage());
       }
       return false;
    }

    /**
     * @return the status
     */
    public boolean isStatus() {
        return this.status;
    }

    /**
     * @return the mensagem
     */
    public String getMensagem() {
        return mensagem;
    }

    /**
     * @return the statement
     */
    public Statement getStatement() {
        return statement;
    }

    /**
     * @return the resultSet
     */
    public ResultSet getResultSet() {
        return resultSet;
    }

    /**
     * @param mensagem the mensagem to set
     */
    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    /**
     * @return the con
     */
    public Connection getCon() {
        return con;
    }

    /**
     * @param con the con to set
     */
    public void setCon(Connection con) {
        this.con = con;
    }

    /**
     * @param statement the statement to set
     */
    public void setStatement(Statement statement) {
        this.statement = statement;
    }

    /**
     * @param resultSet the resultSet to set
     */
    public void setResultSet(ResultSet resultSet) {
        this.resultSet = resultSet;
    }

    /**
     * @return the servidor
     */
    public String getServidor() {
        return servidor;
    }

    /**
     * @param servidor the servidor to set
     */
    public void setServidor(String servidor) {
        this.servidor = servidor;
    }

    /**
     * @return the nomeDoBanco
     */
    public String getNomeDoBanco() {
        return nomeDoBanco;
    }

    /**
     * @param nomeDoBanco the nomeDoBanco to set
     */
    public void setNomeDoBanco(String nomeDoBanco) {
        this.nomeDoBanco = nomeDoBanco;
    }

    /**
     * @return the usuario
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /**
     * @return the senha
     */
    public String getSenha() {
        return senha;
    }

    /**
     * @param senha the senha to set
     */
    public void setSenha(String senha) {
        this.senha = senha;
    }

}
