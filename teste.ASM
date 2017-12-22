sseg SEGMENT STACK
	byte 10000 DUP(?)
sseg ENDS

dseg SEGMENT PUBLIC
	byte 10000 DUP(?)
	sword ?
	byte 256 DUP(?)
	byte 256 DUP(?)
	byte 256 DUP(?)
	byte ?
	byte 10
dseg ENDS

cseg SEGMENT PUBLIC
ASSUME CS:cseg, DS:dseg

_strt:
	mov ax, dseg
	mov ds, ax

dseg SEGMENT PUBLIC
	byte "Digite seu nome: "
dseg ENDS

cseg ENDS
END strt