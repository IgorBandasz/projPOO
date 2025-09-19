package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * @author iband
 */
public class GerenciadorConexao {
  //final - o valor da variável não mudará
  private final String URL = "jdbc:mysql://127.0.0.1:3306/dbprojeto";
  private final String USER = "root";
  private final String PASSWORD = "root";  
  
  private Connection conexao;
    
  public GerenciadorConexao() {
    //tentar executar esse bloco de código
    try {
      //Se conecta no banco de dados com as informações em URL, USER e PASSWORD
      conexao = DriverManager.getConnection(URL, USER, PASSWORD);
    } catch (SQLException e) { 
      //caso ocora um erro de SQLException irá executar o seguinte tratamento
      JOptionPane.showMessageDialog(null, e.getMessage());
    }
  }
  
  //responsável por analisar o sql a ser executado (select, insert, delete, ...)
  //verifica se há variáveis a serem preenchidas
  public PreparedStatement prepararComando(String sql) {
    PreparedStatement comando = null;

    try {
      comando = conexao.prepareStatement(sql);
    } catch (SQLException erro) {
      JOptionPane.showMessageDialog(null, "Erro ao preparar comando: " + erro);
    }

    return comando;
  }
  
  //Responsável por fechar a conexão com o banco de dados
  //Tem 3 versões do fechamento da conexão
  //1ª definição
  public void fecharConexao() {
    try {
      if (conexao != null) {
        conexao.close();
      }
    } catch (SQLException erro) {
      Logger.getLogger(GerenciadorConexao.class.getName())
              .log(Level.SEVERE, null, erro);
    }
  }
  
  //2ª definição
  public void fecharConexao(PreparedStatement comando) {
    fecharConexao();

    try {
      if (comando != null) {
        comando.close();
      }
    } catch (SQLException erro) {
      Logger.getLogger(GerenciadorConexao.class.getName())
              .log(Level.SEVERE, null, erro);
    }
  }
  
  //3ª definição
  public void fecharConexao(PreparedStatement comando, ResultSet resultado) {
    fecharConexao(comando);

    try {
      if (resultado != null) {
        resultado.close();
      }
    } catch (SQLException erro) {
      Logger.getLogger(GerenciadorConexao.class.getName())
              .log(Level.SEVERE, null, erro);
    }
  }
}
