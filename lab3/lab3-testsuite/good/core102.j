.class public core102
.super java/lang/Object
.method public <init>()V
aload_0
invokenonvirtual java/lang/Object/&lt;init>()V
return
.end method
.method public static main([Ljava/lang/String;)V
invokestatic core102/main()I
pop
return
.end method
.method public static main()I
.limit locals 100
.limit stack 100
WHILE:
ldc 1
ldc 2
ldc 5
ldc 6
imul
ldc 5
idiv
iadd
ldc 67
isub
ldc 5
if_icmpgt L
pop
ldc 0
L:
ifeq END
goto WHILE
END
ldc 0
ireturn
return
.end method
