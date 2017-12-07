.class public core017
.super java/lang/Object
.method public <init>()V
aload_0
invokenonvirtual java/lang/Object/&lt;init>()V
return
.end method
.method public static main([Ljava/lang/String;)V
invokestatic core017/main()I
pop
return
.end method
.method public static main()I
.limit locals 100
.limit stack 100
ldc 4
istore_0
ldc 1
ldc 3
iload 0
if_icmple L
pop
ldc 0
L:
ifeq Lf
ldc 1
ldc 4
ldc 2
if_icmpne L
pop
ldc 0
L:
ifeq Lf
ldc 1
goto LEnd
Lf:
ldc 0
LEnd:
ifeq Lf
ldc 1
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
TRUE:
pop
pop
pop
pop
pop
pop
pop
ldc 0
ireturn
return
.end method
.method public static dontCallMe(I)Z
.limit locals 100
.limit stack 100
pop
ldc 1
ireturn
return
.end method
.method public static printBool(Z)V
.limit locals 100
.limit stack 100
iload 2
ifeq END
pop
goto TRUE
FALSE:
pop
TRUE:
return
.end method
.method public static implies(ZZ)Z
.limit locals 100
.limit stack 100
ifne LTrue
ifne LTrue
ldc 0
goto LEnd
LTrue:
ldc 1
LEnd:
ireturn
return
.end method
.method public static not(Z)Z
.limit locals 100
.limit stack 100
iload 5
ifeq END
ldc 0
istore 6
iload 6
pop
goto TRUE
FALSE:
ldc 1
istore 6
iload 6
pop
TRUE:
iload 6
ireturn
return
.end method
.method public static eq_bool(ZZ)Z
.limit locals 100
.limit stack 100
iload 7
ifeq END
iload 8
istore 9
iload 9
pop
goto TRUE
FALSE:
istore 9
iload 9
pop
TRUE:
iload 9
ireturn
return
.end method
