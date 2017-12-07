.class public return-in-while
.super java/lang/Object
.method public <init>()V
aload_0
invokenonvirtual java/lang/Object/&lt;init>()V
return
.end method
.method public static main([Ljava/lang/String;)V
invokestatic return-in-while/main()I
pop
return
.end method
.method public static rRrrrRrrrReturn()I
.limit locals 100
.limit stack 100
ldc 0
istore_0
WHILE:
ldc 1
iload 0
dup
ldc 1
iadd
istore 0
ldc 5
if_icmplt L
pop
ldc 0
L:
ifeq END
ldc 71
ireturn
goto WHILE
END
ldc 52
ireturn
return
.end method
.method public static main()I
.limit locals 100
.limit stack 100
pop
ldc 0
ireturn
return
.end method
