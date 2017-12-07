.class public good05
.super java/lang/Object
.method public <init>()V
aload_0
invokenonvirtual java/lang/Object/&lt;init>()V
return
.end method
.method public static main([Ljava/lang/String;)V
invokestatic good05/main()I
pop
return
.end method
.method public static main()I
.limit locals 100
.limit stack 100
ldc 1
istore 0
iload 0
pop
iload 0
istore 1
iload 1
pop
istore 2
iload 2
pop
pop
WHILE:
ldc 1
iload 1
iload 2
if_icmplt L
pop
ldc 0
L:
ifeq END
pop
iload 0
iload 1
iadd
istore 1
iload 1
pop
iload 1
iload 0
isub
istore 0
iload 0
pop
goto WHILE
END
return
.end method
