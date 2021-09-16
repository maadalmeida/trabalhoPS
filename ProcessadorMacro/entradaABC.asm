Dados	SEGMENT
Var1	DW	5
Var2	DW	8
Var3	DW	3
Dados	ENDS
       
Codigo	SEGMENT
ASSUME CS:	Codigo
ASSUME DS:	Dados
ABC	MACRO	Mem1,	Mem2 ;; Soma duas variáveis
mov 	Mem2,	Mem1
push	AX
mov 	Mem2,Mem2
mov	DX,AX
pop	AX
add	AX,DX
mov	Mem1,	Mem2
ENDM
       
Inicio:
mov	AX, Dados
mov	DS, AX
mov	AX,2
mov	AX, DX
mul	DX
ABCD	Var1,V
mov	AX, Var1
add	AX, DX
mov	Var2, AX
ABC	Var1, 3
ABC	Var1,	Var3
CODIGO	ENDS
END	Inicio