.class public good11
.super java/lang/Object
.method public <init>()V
aload_0
invokenonvirtual java/lang/Object/&lt;init>()V
return
.end method
.method public static main([Ljava/lang/String;)V
invokestatic good11/main()I
pop
return
.end method
.method public static main()I
.limit locals 100
.limit stack 100
ldc 0
istore_0
ldc 0
istore_1
WHILE:
ldc 1
istore 2
iload 2
ldc 0
if_icmpne L
pop
ldc 0
L:
ifeq END
iload 0
iload 2
iadd
istore 0
iload 0
pop
iload 1
dup
ldc 1
iadd
istore 1
pop
goto WHILE
END
pop
return
.end method
