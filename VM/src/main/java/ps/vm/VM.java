/*
Flag 0 e método verificaCarry -> CF (Carry/Borrow): Recebe 1 se uma operação de adição resulta em "carry" (vai-um) ou se uma operação de subtração resulta em "borrow" (vem-um). Se nenhum "carry" ou "borrow" ocorrer após uma adição ou subtração, recebe 0
Endereçamentos e SI
*/
package ps.vm;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import static java.lang.System.exit;
/*
Tamanho da memória: 64 KB (65536 bytes -> 32768 palavras)
Palavra de memória: 16 bits (2 bytes)
Unidade de endereçamento: palavra
*/
public class VM {
    static Memoria mem = new Memoria();
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        Reg2B AX, DX, SP, SI, IP, SR;
        AX = new Reg2B();
        DX = new Reg2B();
        SP = new Reg2B();
        SI = new Reg2B();
        IP = new Reg2B();
        SR = new Reg2B();
        //AX (código C0) e DX (código C2)
        //SP (Stack Pointer) -> Usado pelas instruções push e pop
        //SI (Source Index) -> Usado para indexação de tabelas no endereçamento indireto
        //IP (Instruction Pointer) -> Armazena o endereço da próxima instrução a ser executada
        //SR (Status Register) -> 6 flags de um bit usados para indicar várias condições durante a execução do programa
            //0 -> CF (Carry/Borrow); 6 -> PF (Paridade); 8 -> ZF (Zero); 9 -> SF (Sinal); 12 -> OF (Overflow)
            //7 -> IF (Interrupção) -> Desconsiderada por não utilização
            //Testados após a realização de algumas operações; Os conteúdos podem ser usados para desvios condicionais
        Pilha pilha = new Pilha();
        IP.setRegistrador(13);
            
        FileReader rArquivo = new FileReader("codigofonte.o");
        BufferedReader rBuffer = new BufferedReader(rArquivo);
        
        while(rBuffer.ready()) {
            mem.setLinha(rBuffer.readLine().split(" "));
        }
        rBuffer.close();
        rArquivo.close();
        
        int cont;
        for(;;) {
            cont = IP.getRepresentacaoInt();
            if(mem.getPalavra(cont).contentEquals("EF") || mem.getPalavra(cont).contentEquals("EE") || 
                    mem.getPalavra(cont).contentEquals("9D") || mem.getPalavra(cont).contentEquals("9C")) {
                IP.setRegistrador(cont + 1);
            } else if(mem.getPalavra(cont).contentEquals("07")) {
                IP.setRegistrador(cont + 3);
            } else {
                IP.setRegistrador(cont + 2);
            }
            switch(mem.getPalavra(cont++)) {
                case "03":
                    if(mem.getPalavra(cont).equals("C0")) {
                        addReg(AX, AX, SR);
                    } else if(mem.getPalavra(cont).equals("C2")) {
                        addReg(AX, DX, SR);
                    }
                    break;
                case "05":
                    addOpd(AX, getOperando(cont), SR);
                    break;
                case "F7":
                    switch (mem.getPalavra(cont)) {
                        case "F6":
                            div(AX, SI, DX, SR);
                            break;
                        case "F0":
                            div(AX, AX, DX, SR);
                            break;
                        case "E6":
                            mul(AX, SI, DX, SR);
                            break;
                        case "E0":
                            mul(AX, AX, DX, SR);
                            break;
                        default:
                            break;
                    }
                    break;
                case "2B":
                    if(mem.getPalavra(cont).equals("C0")) {
                        subReg(AX, AX, SR);
                    } else if(mem.getPalavra(cont).equals("C2")) {
                        subReg(AX, DX, SR);
                    }
                    break;
                case "2D":
                    subOpd(AX, getOperando(cont), SR);
                    break;
                case "3D":
                    cmpOpd(AX, getOperando(cont), SR);
                    break;
                case "3B":
                    cmpReg(AX, DX, SR);
                    break;
                case "23":
                    if(mem.getPalavra(cont).equals("C2")) {
                        andReg(AX, DX, SR);
                    }
                    break;
                case "25":
                    andOpd(AX, getOperando(cont), SR);
                    break;
                case "F8":
                    not(AX, SR);
                    break;
                case "0B":
                    if(mem.getPalavra(cont).equals("C2")) {
                        orReg(AX, DX, SR);
                    }
                    break;
                case "0D":
                    orOpd(AX, getOperando(cont), SR);
                    break;
                case "33":
                    if(mem.getPalavra(cont).equals("C0")) {
                        xorReg(AX, AX, SR);
                    } else if(mem.getPalavra(cont).equals("C2")) {
                        xorReg(AX, DX, SR);
                    }
                    break;
                case "35":
                    xorOpd(AX, getOperando(cont), SR);
                    break;
                case "EB":
                    jmp(IP, getOperando(cont));
                    break;
                case "74":
                    jz(IP, getOperando(cont), SR);
                    break;
                case "75":
                    jnz(IP, getOperando(cont), SR);
                    break;
                case "7A":
                    jp(IP, getOperando(cont), SR);
                    break;
                case "E8":
                    call(SP, IP, cont, pilha);
                    break;
                case "EF":
                    ret(SP, IP, pilha);
                    break;
                case "EE":
                    hlt();
                    break;
                case "58":
                    if(mem.getPalavra(cont).equals("C0")) {
                        popReg(SP, AX, pilha);
                    } else if(mem.getPalavra(cont).equals("C2")) {
                        popReg(SP, DX, pilha);
                    }
                    break;
                case "59":                                                      
                    //opd<-[SP]
                    //pega valor do topo da pilha e armazena no endereço dado por opd
                    //falta opd no opcode
                    //instrução possui tamanho 2 -> opcode corrigido: 59 opd
                    popOpd(SP, Integer.parseInt(mem.getPalavra(cont)), pilha);
                    break;
                case "9D":
                    popF(SP, SR, pilha);
                    break;
                case "50":
                    if(mem.getPalavra(cont).equals("C0")) {
                        pushReg(SP, AX, pilha);
                    } else if(mem.getPalavra(cont).equals("C2")) {
                        pushReg(SP, DX, pilha);
                    }
                    break;
                case "9C":
                    pushF(SP, SR, pilha);
                    break;
                case "07":
                    //opd<-AX/DX
                    //falta opd no opcode
                    //instrução possui tamanho 3 -> opcode corrigido: 07 C0/C2 opd
                    if(mem.getPalavra(cont).equals("C0")) {
                        store(AX, Integer.parseInt(mem.getPalavra(cont)));
                    } else if(mem.getPalavra(cont).equals("C2")) {
                        store(DX, Integer.parseInt(mem.getPalavra(cont)));
                    }
                    break;
                case "12":
                    read(Integer.parseInt(mem.getPalavra(cont)));
                    break;
                case "08":
                    write(Integer.parseInt(mem.getPalavra(cont)));
                    break;
                default:
                    break;
            }
        }
    }
    
    private static int getOperando(int index) {
        return Integer.parseInt(mem.getPalavra(Integer.parseInt(mem.getPalavra(index)) + 13));
    }
    
    private static boolean verificaCarry(String conteudo) {                     ///////
        return false;
    }
    
    private static boolean verificaParidade(String conteudo) {
        char[] aux = conteudo.toCharArray();
        int cont, cont1 = 0;
        for(cont = 0; cont < aux.length; cont++) {
            if(aux[cont] == '1') {
                cont1++;
            }
        }
        if((cont1 % 2) == 0) {
            return true;
        }
        return false;
    }
    
    private static boolean verificaZero(int conteudo) {
        if(conteudo == 0) {
            return true;
        }
        return false;
    }
    
    private static boolean verificaSinal(int conteudo) {
        if(conteudo < 0) {
            return true;
        }
        return false;
    }
    
    private static boolean verificaEAtualizaOverflow(int valor, Reg2B flags) {
        if(valor > 65535) {
            flags.atualiza1Bit(12, true);
            return true;
        }
        flags.atualiza1Bit(12, false);
        return false;
    }
    
    private static void atualizaFlagsCPZS(Reg2B reg, Reg2B flags) {
        flags.atualiza1Bit(0, verificaCarry(reg.getRepresentacaoString()));
        flags.atualiza1Bit(6, verificaParidade(reg.getRepresentacaoString()));
        flags.atualiza1Bit(8, verificaZero(reg.getRepresentacaoInt()));
        flags.atualiza1Bit(9, verificaSinal(reg.getRepresentacaoInt()));
    }
    
    private static void addReg(Reg2B reg1, Reg2B reg2, Reg2B flags) {
        int soma = reg1.getRepresentacaoInt() + reg2.getRepresentacaoInt();
        if(verificaEAtualizaOverflow(soma, flags)) {
            System.out.println("OVERFLOW!");
            return;
        }
        reg1.setRegistrador(soma);
        atualizaFlagsCPZS(reg1, flags);
    }
    
    private static void addOpd(Reg2B reg, int opd, Reg2B flags) {
        int soma = reg.getRepresentacaoInt() + opd;
        if(verificaEAtualizaOverflow(soma, flags)) {
            System.out.println("OVERFLOW!");
            return;
        }
        reg.setRegistrador(soma);
        atualizaFlagsCPZS(reg, flags);
    }
    
    private static void div(Reg2B reg1, Reg2B reg2, Reg2B reg3, Reg2B flags) {
        int div = reg1.getRepresentacaoInt() / reg2.getRepresentacaoInt();
        int mod = reg1.getRepresentacaoInt() % reg2.getRepresentacaoInt();
        if(verificaEAtualizaOverflow(div, flags)) {
            System.out.println("OVERFLOW!");
            return;
        }
        reg1.setRegistrador(div);
        reg3.setRegistrador(mod);
        atualizaFlagsCPZS(reg1, flags);
    }
    
    private static void subReg(Reg2B reg1, Reg2B reg2, Reg2B flags) {
        int subtracao = reg1.getRepresentacaoInt() - reg2.getRepresentacaoInt();
        if(verificaEAtualizaOverflow(subtracao, flags)) {
            System.out.println("OVERFLOW!");
            return;
        }
        reg1.setRegistrador(subtracao);
        atualizaFlagsCPZS(reg1, flags);
    }
    
    private static void subOpd(Reg2B reg, int opd, Reg2B flags) {
        int subtracao = reg.getRepresentacaoInt() - opd;
        if(verificaEAtualizaOverflow(subtracao, flags)) {
            System.out.println("OVERFLOW!");
            return;
        }
        reg.setRegistrador(subtracao);
        atualizaFlagsCPZS(reg, flags);
    }
    
    private static void mul(Reg2B reg1, Reg2B reg2, Reg2B reg3, Reg2B flags) {
        int mul = reg1.getRepresentacaoInt() * reg2.getRepresentacaoInt();
        int mulOf = reg1.getRepresentacaoInt() * reg2.getRepresentacaoInt();    //
        if(verificaEAtualizaOverflow(mul, flags)) {
            System.out.println("OVERFLOW!");
            return;
        }
        reg1.setRegistrador(mul);
        reg3.setRegistrador(mulOf);                                             //
        atualizaFlagsCPZS(reg1, flags);
    }
    
    private static void cmpOpd(Reg2B reg, int opd, Reg2B flags) {
        flags.atualiza1Bit(8, (reg.getRepresentacaoInt() == opd));
    }
    
    private static void cmpReg(Reg2B reg1, Reg2B reg2, Reg2B flags) {
        flags.atualiza1Bit(8, (reg1.getRepresentacaoInt() == reg2.getRepresentacaoInt().intValue()));
    }
    
    private static void andReg(Reg2B reg1, Reg2B reg2, Reg2B flags) {
        for(int cont = 0; cont < 16; cont++) {
            if((reg1.get1Bit(cont) && !reg2.get1Bit(cont)) || (!reg1.get1Bit(cont) && reg2.get1Bit(cont))) {
                reg1.atualiza1Bit(cont, false);
            }
        }
        atualizaFlagsCPZS(reg1, flags);
    }
    
    private static void andOpd(Reg2B reg, int opd, Reg2B flags) {
        int cont, aux = 0;
        Boolean[] opdBin = new Boolean[16];
        for(cont = 32768; aux++ < 16; cont /= 2) {
            if(opd < cont*2 && opd >= cont) {
                opdBin[opdBin.length] = true;
                opd -= cont;
            } else{
                opdBin[opdBin.length] = false;
            }
        }
        for(cont = 0; cont < 16; cont++) {
            if((reg.get1Bit(cont) && !opdBin[cont]) || (!reg.get1Bit(cont) && opdBin[cont])) {
                reg.atualiza1Bit(cont, false);
            }
        }
        atualizaFlagsCPZS(reg, flags);
    }
    
    private static void not(Reg2B reg, Reg2B flags) {
        for(int cont = 0; cont < 16; cont++) {
            reg.atualiza1Bit(cont, !reg.get1Bit(cont));
        }
        atualizaFlagsCPZS(reg, flags);
    }
    
    private static void orReg(Reg2B reg1, Reg2B reg2, Reg2B flags) {
        for(int cont = 0; cont < 16; cont++) {
            if((reg1.get1Bit(cont) && !reg2.get1Bit(cont)) || (!reg1.get1Bit(cont) && reg2.get1Bit(cont))) {
                reg1.atualiza1Bit(cont, true);
            }
        }
        atualizaFlagsCPZS(reg1, flags);
    }
    
    private static void orOpd(Reg2B reg, int opd, Reg2B flags) {
        int cont, aux = 0;
        Boolean[] opdBin = new Boolean[16];
        for(cont = 32768; aux++ < 16; cont /= 2) {
            if(opd < cont*2 && opd >= cont) {
                opdBin[opdBin.length] = true;
                opd -= cont;
            } else{
                opdBin[opdBin.length] = false;
            }
        }
        for(cont = 0; cont < 16; cont++) {
            if((reg.get1Bit(cont) && !opdBin[cont]) || (!reg.get1Bit(cont) && opdBin[cont])) {
                reg.atualiza1Bit(cont, true);
            }
        }
        atualizaFlagsCPZS(reg, flags);
    }
    
    private static void xorReg(Reg2B reg1, Reg2B reg2, Reg2B flags) {
        for(int cont = 0; cont < 16; cont++) {
            if((reg1.get1Bit(cont) && !reg2.get1Bit(cont)) || (!reg1.get1Bit(cont) && reg2.get1Bit(cont))) {
                reg1.atualiza1Bit(cont, true);
            } else {
                reg1.atualiza1Bit(cont, false);
            }
        }
        atualizaFlagsCPZS(reg1, flags);
    }
    
    private static void xorOpd(Reg2B reg, int opd, Reg2B flags) {
        int cont, aux = 0;
        Boolean[] opdBin = new Boolean[16];
        for(cont = 32768; aux++ < 16; cont /= 2) {
            if(opd < cont*2 && opd >= cont) {
                opdBin[opdBin.length] = true;
                opd -= cont;
            } else{
                opdBin[opdBin.length] = false;
            }
        }
        for(cont = 0; cont < 16; cont++) {
            if((reg.get1Bit(cont) && !opdBin[cont]) || (!reg.get1Bit(cont) && opdBin[cont])) {
                reg.atualiza1Bit(cont, true);
            } else {
                reg.atualiza1Bit(cont, false);
            }
        }
        atualizaFlagsCPZS(reg, flags);
    }
    
    private static void jmp(Reg2B reg, int opd) {
        reg.setRegistrador(opd);
    }
    
    private static void jz(Reg2B reg, int opd, Reg2B flags) {
        if(flags.get1Bit(8)) {
            reg.setRegistrador(opd);
        }
    }
    
    private static void jnz(Reg2B reg, int opd, Reg2B flags) {
        if(!flags.get1Bit(8)) {
            reg.setRegistrador(opd);
        }
    }
    
    private static void jp(Reg2B reg, int opd, Reg2B flags) {
        if(!flags.get1Bit(9)) {
            reg.setRegistrador(opd);
        }
    }
    
    private static void call(Reg2B regPilha, Reg2B IP, int index, Pilha pilha) {
        pilha.push(IP.getRepresentacaoString());
        IP.setRegistrador(Integer.parseInt(mem.getPalavra(index)) + 13);
        regPilha.setRegistrador(pilha.getEndTopoPilha());
    }
    
    private static void ret(Reg2B regPilha, Reg2B IP, Pilha pilha) {
        IP.setRegistrador(pilha.pop());
        regPilha.setRegistrador(pilha.getEndTopoPilha());
    }
    
    private static void hlt() {
        exit(0);
    }
    
    private static void popReg(Reg2B regPilha, Reg2B reg, Pilha pilha) {
        reg.setRegistrador(pilha.pop());
        regPilha.setRegistrador(pilha.getEndTopoPilha());
    }
    
    private static void popOpd(Reg2B regPilha, int opd, Pilha pilha) {
        mem.setPalavra(pilha.pop(), Integer.parseInt(mem.getPalavra(opd)));
        regPilha.setRegistrador(pilha.getEndTopoPilha());
    }
    
    private static void popF(Reg2B regPilha, Reg2B flags, Pilha pilha) {
        flags.setRegistrador(pilha.pop());
        regPilha.setRegistrador(pilha.getEndTopoPilha());
    }
    
    private static void pushReg(Reg2B regPilha, Reg2B reg, Pilha pilha) {
        pilha.push(reg.getRepresentacaoString());
        regPilha.setRegistrador(pilha.getEndTopoPilha());
    }
    
    private static void pushF(Reg2B regPilha, Reg2B flags, Pilha pilha) {
        pilha.push(flags.getRepresentacaoString());
        regPilha.setRegistrador(pilha.getEndTopoPilha());
    }
    
    private static void store(Reg2B reg, int opd) {
        mem.setPalavra(reg.getRepresentacaoString(), opd);
    }
    
    private static void read(int opd) {
        System.out.println("ENTRADA: ");
        Scanner entrada = new Scanner(System.in);
        mem.setPalavra(entrada.nextLine(), Integer.parseInt(mem.getPalavra(opd)));
    }
    
    private static void write(int opd) {
        System.out.println("SAIDA: ");
        System.out.println(mem.getPalavra(opd));
    }
}