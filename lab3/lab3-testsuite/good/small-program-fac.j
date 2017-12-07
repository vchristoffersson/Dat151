.class public small-program-fac
.super java/lang/Object
.method public <init>()V
aload_0
invokenonvirtual java/lang/Object/&lt;init>()V
return
.end method
.method public static main([Ljava/lang/String;)V
invokestatic small-program-fac/main()I
pop
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
.method public static fac(I)I
.limit locals 100
.limit stack 100
ldc 1
istore 1
iload 1
pop
iload 0
istore 2
iload 2
pop
WHILE:
ldc 1
iload 2
ldc 0
if_icmpgt L
pop
ldc 0
L:
ifeq END
iload 1
iload 2
imul
istore 1
iload 1
pop
iload 2
ldc 1
isub
istore 2
iload 2
pop
goto WHILE
END
iload 1
ireturn
return
.end method
