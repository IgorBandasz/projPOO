/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import model.Usuario;
//import utils.Utils;
/**
 *
 * @author iband
 */
public class UsuarioController {
  public boolean autenticar(String usuario, String senha){
    //Montar o comando a ser executado
    //os ? são variáveis que são preenchidas mais adiante
    String sql = "SELECT * from TBUSUARIO "
               + " WHERE email = ? and senha = ? "
               + " and ativo = true";
    
    //Cria uma instância do gerenciador de conexão
    //(conexão com o banco de dados),
    GerenciadorConexao gerenciador = new GerenciadorConexao();
    
    //Declara as variáveis como nulas antes do try 
    //para poder usar no finally
    PreparedStatement comando = null;
    ResultSet resultado = null;
    
    try{
      //prepara o sql, analisando o formato e as váriaveis
      comando = gerenciador.prepararComando(sql);

      //define o valor de cada variável(?) pela posição em que aparece no sql
      comando.setString(1, usuario);
      comando.setString(2, senha);
      
      //executa o comando e guarda o resultado da consulta 
      //o resultado é semelhante a uma grade
      resultado = comando.executeQuery();

      //resultado.next() - tenta avançar para a próxima linha 
      //caso consiga retorna true
      if (resultado.next()) {
        //Se conseguiu avançar para a próxima linha é porque achou um usuário com aquele nome e senha
        return true;
      }
    } catch (SQLException e) {
      //caso ocorra um erro relacionado ao banco de dados
      //exibe popup com o erro
      JOptionPane.showMessageDialog(null, e.getMessage());      
    } finally {
      //depois de executar o try, dando erro ou não executa o finally
      gerenciador.fecharConexao(comando, resultado);
    }  
    return false;
  }
  
  public boolean inserir(Usuario usu){
    //Montar o comando a ser executado
    //os ? são variáveis que são preenchidas mais adiante
    String sql = "INSERT INTO TBUSUARIO (nome, email, senha, datanasc, ativo) "
               + " VALUES (?,?,?,?,?)";
    
    //Cria uma instância do gerenciador de conexão
    //(conexão com o banco de dados),
    GerenciadorConexao gerenciador = new GerenciadorConexao();
    
    //Declara as variáveis como nulas antes do try 
    //para poder usar no finally
    PreparedStatement comando = null;
    
    try{
      //prepara o sql, analisando o formato e as váriaveis
      comando = gerenciador.prepararComando(sql);

      //define o valor de cada variável(?) pela posição em que aparece no sql
      comando.setString(1, usu.getNome());
      comando.setString(2, usu.getEmail());
      comando.setString(3, usu.getSenha());
      comando.setDate(4, new java.sql.Date(usu.getDataNascimento().getTime()));
      comando.setBoolean(5, usu.isAtivo());
      
      //executa o comando 
      comando.executeUpdate();
      return true;
    } catch (SQLException e) {
      //caso ocorra um erro relacionado ao banco de dados
      //exibe popup com o erro
      JOptionPane.showMessageDialog(null, e.getMessage());      
    } finally {
      //depois de executar o try, dando erro ou não executa o finally
      gerenciador.fecharConexao(comando);
    }  
    return false;
  }
  
  public List<Usuario> consultar(){
    //Montar o comando a ser executado
    //os ? são variáveis que são preenchidas mais adiante
    String sql = "SELECT * from TBUSUARIO ";
    
    //Cria uma instância do gerenciador de conexão
    //(conexão com o banco de dados),
    GerenciadorConexao gerenciador = new GerenciadorConexao();
    
    //Declara as variáveis como nulas antes do try 
    //para poder usar no finally
    PreparedStatement comando = null;
    ResultSet resultado = null;
    
    //crio a lista de usuários, vazia ainda
    List<Usuario> lista = new ArrayList<>();
    
    try{
      //prepara o sql, analisando o formato e as váriaveis
      comando = gerenciador.prepararComando(sql);
      
      //executa o comando e guarda o resultado da consulta 
      //o resultado é semelhante a uma grade
      resultado = comando.executeQuery();

      while(resultado.next()){
        Usuario usu = new Usuario();
        
        usu.setPkUsuario(resultado.getInt("pkUsuario"));
        usu.setNome(resultado.getString("nome"));
        usu.setEmail(resultado.getString("email"));
        usu.setSenha(resultado.getString("senha")); //É o HASH
        usu.setDataNascimento(resultado.getDate("dataNasc")); 
        usu.setAtivo(resultado.getBoolean("ativo")); 
        
        lista.add(usu);
      }
    } catch (SQLException e) {
      //caso ocorra um erro relacionado ao banco de dados
      //exibe popup com o erro
      JOptionPane.showMessageDialog(null, e.getMessage());      
    } finally {
      //depois de executar o try, dando erro ou não executa o finally
      gerenciador.fecharConexao(comando, resultado);
    }  
    return lista;
  }
}
