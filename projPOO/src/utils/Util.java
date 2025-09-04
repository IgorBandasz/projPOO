/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.net.URL;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author iband
 */
public class Util {
  
  public static Image getIcone(){
    URL caminhoImagem = Util.class.getResource("/images/logo_mini.png");

    ImageIcon icon = new ImageIcon(caminhoImagem);
    
    return icon.getImage();
  }
  
  public static Date converterStringToDate(String texto) {
    //Construo o formato que quero transformar o texto
    SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
    
    //crio minha variável data que será o retorno do método
    Date data = null;

    try {
      //tenta converter a String em Date baseado no formato 
      //contruido anteriomente
      data = formato.parse(texto);
    } catch (ParseException ex) {
      JOptionPane.showMessageDialog(null,
              "Erro ao converter a data");
    }
    //retorna a data convertida
    return data;
  }
  
  public static String calcularHash(String senha) {
    String hashSHA1 = "";
    try {
      // Crie uma instância do MessageDigest 
      //com o algoritmo SHA1
      MessageDigest sha1 = MessageDigest.getInstance("SHA1");

      // Atualize o digest com os bytes do texto
      sha1.update(senha.getBytes());

      // Calcule o hash SHA1
      byte[] digest = sha1.digest();

      // Converta o hash de bytes para 
      //uma representação hexadecimal
      for (byte b : digest) {
        hashSHA1 = hashSHA1 + String.format("%02x", b);
      }
      
    } catch (NoSuchAlgorithmException e) {
      System.err.println("Algoritmo SHA1 não encontrado");
    }

    return hashSHA1;
  }
}
