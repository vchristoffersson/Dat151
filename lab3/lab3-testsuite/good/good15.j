.class public good15
.super java/lang/Object
.method public <init>()V
aload_0
invokenonvirtual java/lang/Object/&lt;init>()V
return
.end method
.method public static main([Ljava/lang/String;)V
invokestatic good15/main()I
pop
return
.end method
.method public static main()I
.limit locals 100
.limit stack 100
ldc 1
istore_0
pop
ldc 1
ifne LTrue
ldc 1
iload 0
dup
ldc 1
iadd
istore 0
ldc 45
if_icmpne L
pop
ldc 0
L:
ifne LTrue
ldc 0
goto LEnd
LTrue:
ldc 1
LEnd:
pop
pop
ldc 0
ifne LTrue
ldc 1
iload 0
dup
ldc 1
iadd
istore 0
ldc 0
if_icmpge L
pop
ldc 0
L:
ifne LTrue
ldc 0
goto LEnd
LTrue:
ldc 1
LEnd:
pop
pop
ldc 1
ifeq Lf
ldc 1
iload 0
dup
ldc 1
iadd
istore 0
ldc 0
if_icmplt L
pop
ldc 0
L:
ifeq Lf
ldc 1
goto LEnd
Lf:
ldc 0
LEnd:
pop
pop
ldc 0
ifeq Lf
ldc 1
iload 0
dup
ldc 1
iadd
istore 0
ldc 0
if_icmpgt L
pop
ldc 0
L:
ifeq Lf
ldc 1
goto LEnd
Lf:
ldc 0
LEnd:
pop
pop
ldc 0
istore_1
ldc 1
ldc 34
ldc 6
if_icmplt L
pop
ldc 0
L:
ifeq Lf
ldc 1
iload 1
ldc 0
if_icmplt L
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
goto TRUE
FALSE:
pop
TRUE:
return
.end method
