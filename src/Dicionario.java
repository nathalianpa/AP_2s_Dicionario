
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @author Nathalia
 * 
 * Entrega do Trabalho 2 - Algoritmos e Programação II
 * 
Eu,

Nathalia Pereira Alves

declaro que

todas as respostas são fruto de meu próprio trabalho,
não copiei respostas de colegas externos à equipe,
não disponibilizei minhas respostas para colegas externos ao grupo e
não realizei quaisquer outras atividades desonestas para me beneficiar ou
prejudicar outros.
 */
public class Dicionario {
    private String[] palavras = new String[1000];
    private String livro;
    private String[] letras = {
        "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", 
        "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"
    };    
    
    public Dicionario(String livro) {
       this.livro = livro;
    }
    
    // Este metodo le o arquivo livro.txt e adiciona as palavras deste arquivo
    // ao Array de palavras da classe.
    public void escrever() {
        try {
            FileReader arquivo = new FileReader(livro);
            BufferedReader lerArquivo = new BufferedReader(arquivo);
            
            boolean repetir = true;
            
            String linha = lerArquivo.readLine();
            
            while(repetir) {               
                String[] algumasPalavras = converteFrasePalavras(linha);
                escreverDicionario(algumasPalavras);
                
                linha = lerArquivo.readLine();
                
                if(linha.equals(".")) {
                    repetir = false;
                }
            }      
        } catch (IOException e) {
        System.err.printf("Erro na abertura do arquivo: %s.\n",
          e.getMessage());
        }
    }    
    
    // Este metodo escreve no arquivo dicionario.txt o Array de palavras da 
    // classe.
    public void imprimir() {
        try {
            FileWriter arq = new FileWriter("./src/dicionario.txt");
            PrintWriter gravarArq = new PrintWriter(arq);
            
            for(int i = 0; i < posicaoFinal(); i++) {
              gravarArq.println(palavras[i]);
            }
            
            gravarArq.printf("total de palavras diferentes no dicionario=%d", posicaoFinal());

            arq.close();
        } catch (IOException e) {
            System.err.printf("Erro na escrita do arquivo: %s.\n",
          e.getMessage());
        }
    }
    
    // Este metodo pega uma linha do arquivo .txt convertido em String e 
    // converte em um Array de Strings.
    private String[] converteFrasePalavras(String frase){
        return frase.toLowerCase().split(" ");
    }
    
    // Este metodo recebe um Array com as palavras de uma linha do arquivo 
    // livro.txt e adiciona ao Array de palavras da classe 
    // (atraves de outros metodos da classe).
    private void escreverDicionario(String[] algumasPalavras) {
        for(int i = 0; i < algumasPalavras.length; i++) {
            if(algumasPalavras[i].equals("")) {
                continue;
            }
            
            if(palavras[0] == null) {
                palavras[0] = algumasPalavras[i];
                continue;
            }
            
            if(palavraRepetida(algumasPalavras[i])) {
                continue;
            }
            
            adicionarPalavra(algumasPalavras[i]);
        }
    }    
    
    // Este metodo verifica se a palavra informada já existe no Array de 
    // palavras da classe.
    private boolean palavraRepetida(String palavra) {
        boolean resultado = false;
        
        for(int i = 0; i < palavras.length; i++) {
            if(palavras[i] == null){
                i = palavras.length + 1;
                continue;
            }
            
            if(palavras[i].equals(palavra)) {
                resultado = true;
                i = palavras.length + 1;
            }
        }
        
        return resultado;
    }
    
    // Este metodo recebe duas palavras e verifica se a primeira palavra 
    // informada vem antes da segunda na ordem alfabetica.
    private boolean palavra1Menor(String palavra1, String palavra2) {
        boolean resultado = false;
        
        for(int i = 0; i < palavra2.length(); i++) {
            int peso1 = pesoPalavra(palavra1, i);
            int peso2 = pesoPalavra(palavra2, i);
            
            if(peso1 < peso2) {
                resultado = true;
                i = palavra2.length() + 1;
            } else if(peso1 > peso2) {
                resultado = false;
                i = palavra2.length() + 1;
            }
        }
        
        return resultado;
    }
    
    // Este metodo recebe uma palavra e um numero. Utiliza o numero para buscar 
    // a posição de uma letra na palavra recebida e converte isso em um numero.
    private int pesoPalavra(String palavra, int posicao) {
        int resultado = 0;
        
        if(posicao >= palavra.length()){
            return 0;
        }
        
        String letra = String.valueOf(palavra.charAt(posicao));
        
        for(int i = 1; i < letras.length; i++) {
            if(letras[i].equals(letra)) {
                resultado = i;
                i = letras.length + 1;
            }
        }
        
        return resultado;
    }
    
    // Este metodo recebe uma palavra e um numero. Utiliza o numero para buscar
    // uma posicao no Array de palavras da classe, adiciona a palavra recebida
    // nesta posicao e altera a posicao de todas as palavras apos a posicao do
    // numero informado.
    private void reordenar(String palavra, int posicao) {
        for(int i = posicaoFinal(); i > posicao; i--) {
            palavras[i] = palavras[i - 1];
        }
        
        palavras[posicao] = palavra;
    }
    
    
    // Este metodo adiciona uma palavra no Array de palavras, respeitando a ordem
    // alfabetica e reordenando o Array se for necessario.
    private void adicionarPalavra(String palavra) {
        boolean reordenado = false;
        
        for(int i = 0; i < posicaoFinal(); i++) {
            if(palavra1Menor(palavra, palavras[i])) {
                reordenar(palavra, i);
                reordenado = true;
                i = posicaoFinal() + 1;
            }
        }
        
        if(!reordenado) {
            palavras[posicaoFinal()] = palavra;
        }
    }
    
    
    // Este metodo descobre a posicao do primeiro null do Array de palavras da 
    // classe.
    private int posicaoFinal() {
        int posicaoFinal = palavras.length - 1;
        
        for(int i = 0; i < palavras.length; i++) {
            if(palavras[i] == null) {
                posicaoFinal = i;
                i = palavras.length + 1;
            }
        }
        
        return posicaoFinal;
    }
}
