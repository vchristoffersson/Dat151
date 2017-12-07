.class public good13
.super java/lang/Object
.method public <init>()V
aload_0
invokenonvirtual java/lang/Object/&lt;init>()V
return
.end method
.method public static main([Ljava/lang/String;)V
invokestatic good13/main()I
pop
return
.end method
.method public static main()I
.limit locals 100
.limit stack 100
istore 0
iload 0
pop
ldc 2
istore 1
iload 1
pop
WHILE:
ldc 1
iload 1
iload 0
if_icmple L
pop
ldc 0
L:
ifeq END
ldc 1
istore_2
ldc 2
istore_3
WHILE:
ldc 1
iload 3
iload 3
imul
iload 1
if_icmple L
pop
ldc 0
L:
ifeq Lf
iload 2
ifeq Lf
ldc 1
goto LEnd
Lf:
ldc 0
LEnd:
ifeq END
ldc 1
iload 1
iload 3
idiv
iload 3
imul
iload 1
if_icmpeq L
pop
ldc 0
L:
ifeq END
ldc 0
istore 2
iload 2
pop
goto TRUE
FALSE:
TRUE:
iload 3
dup
ldc 1
iadd
istore 3
pop
goto WHILE
END
iload 2
ifeq Lf
ldc 1
iload 0
iload 1
idiv
iload 1
imul
iload 0
if_icmpeq L
pop
ldc 0
L:
ifeq Lf
ldc 1
goto LEnd
Lf:
ldc 0
LEnd:
ifeq END
pop
iload 0
iload 1
idiv
istore 0
iload 0
pop
goto TRUE
FALSE:
iload 1
dup
ldc 1
iadd
istore 1
pop
TRUE:
goto WHILE
END
ldc 0
ireturn
return
.end method
