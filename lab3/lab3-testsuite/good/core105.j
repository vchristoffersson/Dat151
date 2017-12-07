.class public core105
.super java/lang/Object
.method public <init>()V
aload_0
invokenonvirtual java/lang/Object/&lt;init>()V
return
.end method
.method public static main([Ljava/lang/String;)V
invokestatic core105/main()I
pop
return
.end method
.method public static main()I
.limit locals 100
.limit stack 100
ldc 5
istore_0
WHILE:
ldc 1
iload 0
ldc 3
if_icmpgt L
pop
ldc 0
L:
ifeq END
pop
goto WHILE
END
iload 0
ireturn
return
.end method
