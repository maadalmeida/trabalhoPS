/*
Flag 0 e método verificaCarry -> CF (Carry/Borrow): Recebe 1 se uma operação de adição resulta em "carry" (vai-um) ou se uma operação de subtração resulta em "borrow" (vem-um). Se nenhum "carry" ou "borrow" ocorrer após uma adição ou subtração, recebe 0
Endereçamentos e SI
*/
package ps.vm;
import java.io.IOException;

/*
Tamanho da memória: 64 KB (65536 bytes -> 32768 palavras)
Palavra de memória: 16 bits (2 bytes)
Unidade de endereçamento: palavra
*/

public class VM {
    static Memoria mem = new Memoria();
    
    public static void main(String[] args) throws IOException {
        VMUI janela = new VMUI();
        janela.setVisible(true);
    }
}
